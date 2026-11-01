# 3. Set Arayüzü ve Kümeler (HashSet, LinkedHashSet, TreeSet)

Bu doküman; Java Koleksiyon Çerçevesi'nde **tekrarsız (benzersiz / unique)** eleman saklamak için kullanılan **`Set`** arayüzünü, en hızlı küme yapısı olan **`HashSet`**'i, ekleme sırasını koruyan **`LinkedHashSet`**'i, elemanları otomatik sıralı tutan **`TreeSet`**'i ve nesnelerin küme içinde doğru çalışabilmesi için **`equals()` ve `hashCode()`** metotlarının hayati önemini ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: List Arayüzü ve Sınıfları (2_list_arayuzu_ve_siniflari.md)](./2_list_arayuzu_ve_siniflari.md)
> - 🔜 [Sonraki Bölüm: Queue ve Deque Kuyruk Yapıları (4_queue_ve_kuyruklar.md)](./4_queue_ve_kuyruklar.md)

---

## 📌 İçindekiler
1. [`Set` Arayüzü Nedir ve Matematiksel Mantığı](#1-set-arayüzü-nedir-ve-matematiksel-mantığı)
   - [Benzersizlik İlkesi (No Duplicates)](#benzersizlik-ilkesi-no-duplicates)
   - [İndeks Yokluğu: Neden `get(i)` Metodu Yoktur?](#indeks-yokluğu-neden-geti-metodu-yoktur)
2. [`HashSet`: Hashing Algoritması ve Çalışma Mantığı](#2-hashset-hashing-algoritması-ve-çalışma-mantığı)
   - [Hash Fonksiyonu ve Kova (Bucket) Mimarisi](#hash-fonksiyonu-ve-kova-bucket-mimarisi)
   - [Neden Eleman Sıralaması Garanti Edilmez?](#neden-eleman-sıralaması-garanti-edilmez)
   - [Zaman Karmaşıklığı: $O(1)$ Ekleme ve Arama Hızı](#zaman-karmaşıklığı-o1-ekleme-ve-arama-hızı)
3. [Hayati Kural: `equals()` ve `hashCode()` Sözleşmesi](#3-hayati-kural-equals-ve-hashcode-sözleşmesi)
   - [Kendi Nesnelerimizi Kümeye Eklerken Bu İki Metodu Ezmezsek Ne Olur?](#kendi-nesnelerimizi-kümeye-eklerken-bu-iki-metodu-ezmezsek-ne-olur)
   - [İki Aşamalı Kontrol Mekanizması](#iki-aşamalı-kontrol-mekanizması)
4. [`LinkedHashSet`: Ekleme Sırasını Koruyan Kümeler](#4-linkedhashset-ekleme-sırasını-koruyan-kümeler)
5. [`TreeSet`: Otomatik Sıralı Kümeler (Red-Black Tree)](#5-treeset-otomatik-sıralı-kümeler-red-black-tree)
   - [Doğal Sıralama (Natural Ordering) ve `Comparable` Şartı](#doğal-sıralama-ve-comparable-şartı)
   - [`null` Kabul Etmeme Kuralı](#null-kabul-etmeme-kuralı)
6. [`HashSet` vs. `LinkedHashSet` vs. `TreeSet` Karşılaştırma Tablosu](#6-hashset-vs-linkedhashset-vs-treeset-karşılaştırma-tablosu)
7. [Matematiksel Küme Operasyonları (Birleşim, Kesişim, Fark)](#7-matematiksel-küme-operasyonları)
8. [Adım Adım Çalıştırılabilir Kod Örnekleri](#8-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: `HashSet` ve `equals`/`hashCode` Ezilmesinin Gücü (`OgrenciSetTest.java`)](#örnek-1-hashset-ve-equalshashcode-ezilmesinin-gücü)
   - [Örnek 2: `TreeSet` ile Otomatik Sıralı Liste (`TreeSetDemo.java`)](#örnek-2-treeset-ile-otomatik-sıralı-liste)
   - [Örnek 3: Matematiksel Küme İşlemleri (`KumeOperasyonlari.java`)](#örnek-3-matematiksel-küme-işlemleri)
9. [Sık Yapılan Hatalar ve Önemli Kurallar](#9-sık-yapılan-hatalar-ve-önemli-kurallar)
10. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#10-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
11. [Ödev Konuları ve Mini Projeler](#11-ödev-konuları-ve-mini-projeler)

---

## 1. `Set` Arayüzü Nedir ve Matematiksel Mantığı?

Matematikte bir kümede $\{1, 2, 3\}$ aynı elemandan birden fazla bulunamaz; $\{1, 2, 2, 3\}$ yazdığınızda küme yine $\{1, 2, 3\}$ olarak kabul edilir.

Java'da **`Set`** arayüzü tam olarak bu matematiksel küme kuralını uygular:
- **Tekrarsızdır (Unique):** Bir kümeye var olan bir elemanı ikinci kez eklemeye çalışırsanız, Java hata fırlatmaz; ancak elemanı eklemez ve `add()` metodu geriye **`false`** döndürür.
- **İndeks Yoktur:** Kümeler torbaya benzer. Bir torbanın içine atılmış bilyelerin "0. indeksi" veya "3. indeksi" yoktur. Bu yüzden `Set` arayüzünde **`get(int index)` metodu BULUNMAZ!**

```
              [ Set Arayüzü (java.util.Set) ]
               /             |             \
              /              |              \
       [ HashSet ]   [ LinkedHashSet ]   [ TreeSet ]
        (En Hızlı)    (Ekleme Sıralı)     (Sıralı Ağaç)
```

---

## 2. `HashSet`: Hashing Algoritması ve Çalışma Mantığı

`HashSet`, Java'da en sık kullanılan küme sınıfıdır. Arka planda bir **Hash Tablosu (Hash Table)** mimarisi kullanır.

```
Anahtar: "Ahmet" ---> hashCode() ---> 104523 ---> % 16 (Dizi Boyutu) ---> Kova 7 (Bucket 7)
```

1. Kümeye bir eleman eklemek istediğinizde (`set.add("Ahmet")`), Java elemanın **`hashCode()`** metodunu çağırır.
2. Bu kod bir sayı üretir ve eleman o sayının denk geldiği kovanın (*bucket*) içine bırakılır.
3. Bir elemanın kümede olup olmadığı sorgulandığında (`set.contains("Ahmet")`), Java tüm kümeyi tek tek gezmez! Doğrudan 7 numaralı kovaya bakar ($O(1)$ anlık erişim hızı).

> ⚠️ **Sıralama Garantisi Yoktur:** `HashSet` elemanları eklediğiniz sırayla tutmaz. Kendi hash algoritmasına göre rastgele gibi görünen bir sırada dizer.

---

## 3. Hayati Kural: `equals()` ve `hashCode()` Sözleşmesi

Java'da kendi oluşturduğumuz sınıfları (`Ogrenci`, `Urun`, `Musteri`) bir `Set` içine koyacaksak, hayatımızın en önemli kuralı devreye girer:

```
+-----------------------------------------------------------------+
|  EĞER İKİ NESNE equals() İLE EŞİT KABUL EDİLİYORSA,             |
|  BU İKİ NESNENİN hashCode() DEĞERLERİ DE AYNI OLMAK ZORUNDADIR! |
+-----------------------------------------------------------------+
```

### Bu İki Metodu Ezmezsek Ne Olur?
Eğer `equals()` ve `hashCode()` metotlarını ezmezseniz, `Object` sınıfından gelen varsayılan metotlar çalışır:
- Varsayılan `hashCode()` her nesneye bellekteki (Heap) fiziksel adresine göre bir kod verir.
- Bu durumda aynı TC numarasına sahip iki farklı `Vatandas` nesnesi açarsanız (`new Vatandas("111")` ve `new Vatandas("111")`), bellek adresleri farklı olduğu için **ikisi de kümeye eklenir ve benzersizlik kuralı çiğnenmiş olur!**

```java
// İki aşamalı kontrol:
1. Adım: İki nesnenin hashCode'ları aynı mı? 
         HAYIR -> Kesinlikle farklıdır, kümeye ekle!
         EVET  -> 2. Adıma geç!
2. Adım: equals() ile içerik kontrolü yap.
         true ise -> ZATEN VAR, KÜMEYE EKLEME!
         false ise -> Çakışma (Collision), aynı kovaya ekle.
```

---

## 4. `LinkedHashSet`: Ekleme Sırasını Koruyan Kümeler

`HashSet`'in tek zayıf noktası eleman sırasını korumamasıdır.
Eğer hem **benzersizlik (tekrarsızlık)** istiyor hem de **elemanların eklenme sırasıyla (Insertion Order)** korunmasını istiyorsanız çözüm: **`LinkedHashSet`**'tir.

- Arka planda Hash Tablosuna ek olarak çift yönlü bağlı liste referansları tutar.
- `HashSet`'ten çok hafif daha yavaştır; ancak ekleme sırasını garanti eder.

```java
Set<String> gunler = new LinkedHashSet<>();
gunler.add("Pazartesi");
gunler.add("Salı");
gunler.add("Çarşamba");
// Ekrana basıldığında garanti olarak Pazartesi, Salı, Çarşamba sırasında gelir!
```

---

## 5. `TreeSet`: Otomatik Sıralı Kümeler (Red-Black Tree)

`TreeSet`, arka planda dengeli bir ikili arama ağacı (**Kırmızı-Siyah Ağaç / Red-Black Tree**) kullanır.

- **Otomatik Sıralama:** Elemanları eklediğiniz anda **küçükten büyüğe (alfabetik / sayısal)** otomatik olarak dizer.
- **Zaman Karmaşıklığı:** Arama ve ekleme hızı $O(\log n)$'dir (`HashSet` kadar hızlı değildir ama her zaman sıralıdır).
- **Zorunlu Kural:** `TreeSet` içine eklenecek nesneler birbiriyle kıyaslanabilir olmalıdır; yani sınıf **`Comparable`** arayüzünü uygulamış olmalıdır!
- ❌ **`null` YASAKTIR:** `TreeSet` içine `null` ekleyemezsiniz; çünkü `null` ile diğer elemanlar arasında büyüktür/küçüktür kıyaslaması yapılamaz (`NullPointerException`).

---

## 6. `HashSet` vs. `LinkedHashSet` vs. `TreeSet` Karşılaştırma Tablosu

| Kriter | `HashSet` | `LinkedHashSet` | `TreeSet` |
| :--- | :---: | :---: | :---: |
| **Tekrara İzin Verir mi?** | ❌ Hayır | ❌ Hayır | ❌ Hayır |
| **Sıralama Davranışı** | **Sırasız (Rastgele)** | **Ekleme Sırası (Insertion Order)** | **Doğal Sıralı (A-Z, 0-9)** |
| **Zaman Karmaşıklığı** | **$O(1)$ (En Hızlı)** | $O(1)$ (Hızlı) | $O(\log n)$ (Ağaç maliyeti) |
| **Arka Plandaki Veri Yapısı** | Hash Table | Hash Table + Bağlı Liste | Red-Black Tree (Ağaç) |
| **`null` Eleman Desteği**| 1 adet `null` olabilir | 1 adet `null` olabilir | ❌ **`null` kabul etmez!** |

---

## 7. Matematiksel Küme Operasyonları

`Collection` arayüzünün metotları sayesinde iki küme arasında tüm matematiksel işlemler tek satırda yapılır:

```java
Set<Integer> kumeA = new HashSet<>(Set.of(1, 2, 3, 4));
Set<Integer> kumeB = new HashSet<>(Set.of(3, 4, 5, 6));

// 1. BİRLEŞİM (Union): A U B
Set<Integer> birlesim = new HashSet<>(kumeA);
birlesim.addAll(kumeB); // [1, 2, 3, 4, 5, 6]

// 2. KESİŞİM (Intersection): A n B
Set<Integer> kesisim = new HashSet<>(kumeA);
kesisim.retainAll(kumeB); // [3, 4]

// 3. FARK (Difference): A \ B
Set<Integer> fark = new HashSet<>(kumeA);
fark.removeAll(kumeB); // [1, 2]
```

---

## 8. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: `HashSet` ve `equals`/`hashCode` Ezilmesinin Gücü

#### `Ogrenci.java`
```java
import java.util.Objects;

public class Ogrenci {
    private int numara;
    private String ad;

    public Ogrenci(int numara, String ad) {
        this.numara = numara;
        this.ad = ad;
    }

    // İki öğrenci numaraları aynıysa EŞİTTİR kuralı:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ogrenci ogrenci = (Ogrenci) o;
        return numara == ogrenci.numara;
    }

    // Numaraya göre hash kodu üretimi (Hayati!):
    @Override
    public int hashCode() {
        return Objects.hash(numara);
    }

    @Override
    public String toString() {
        return "[" + numara + " - " + ad + "]";
    }
}
```

#### `OgrenciSetTest.java`
```java
import java.util.HashSet;
import java.util.Set;

public class OgrenciSetTest {
    public static void main(String[] args) {
        Set<Ogrenci> sinif = new HashSet<>();

        Ogrenci o1 = new Ogrenci(101, "Ali Yılmaz");
        Ogrenci o2 = new Ogrenci(102, "Zeynep Kaya");
        // Aynı numaraya (101) sahip yeni bir nesne üretiyoruz:
        Ogrenci o3 = new Ogrenci(101, "Ali Yılmaz (Tekrar)");

        sinif.add(o1);
        sinif.add(o2);
        boolean eklendiMi = sinif.add(o3); // equals & hashCode sayesinde reddedilir!

        System.out.println("o3 Kümeye eklendi mi? -> " + eklendiMi);
        System.out.println("Kümedeki Toplam Öğrenci Sayısı: " + sinif.size());
        System.out.println("Küme İçeriği: " + sinif);
    }
}
```

#### Konsol Çıktısı:
```text
o3 Kümeye eklendi mi? -> false
Kümedeki Toplam Öğrenci Sayısı: 2
Küme İçeriği: [[101 - Ali Yılmaz], [102 - Zeynep Kaya]]
```

---

### Örnek 2: `TreeSet` ile Otomatik Sıralı Liste

#### `TreeSetDemo.java`
```java
import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> sayilar = new TreeSet<>();

        sayilar.add(45);
        sayilar.add(12);
        sayilar.add(89);
        sayilar.add(23);
        sayilar.add(12); // Tekrar eden elenir

        // Otomatik küçükten büyüğe sıralı gelecektir:
        System.out.println("TreeSet (Doğal Sıralı): " + sayilar);

        // TreeSet'e özel navigasyon metotları:
        System.out.println("En Küçük Eleman (first): " + sayilar.first());
        System.out.println("En Büyük Eleman (last)  : " + sayilar.last());
        System.out.println("30'dan küçük en büyük (lower): " + sayilar.lower(30));
        System.out.println("30'dan büyük en küçük (higher): " + sayilar.higher(30));
    }
}
```

#### Konsol Çıktısı:
```text
TreeSet (Doğal Sıralı): [12, 23, 45, 89]
En Küçük Eleman (first): 12
En Büyük Eleman (last)  : 89
30'dan küçük en büyük (lower): 23
30'dan büyük en küçük (higher): 45
```

---

## 9. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `Set` İçinde İndeksle Eleman Aramaya Çalışmak
```java
Set<String> kume = new HashSet<>();
kume.add("Java");
// String s = kume.get(0); // ❌ DERLEME HATASI! Set'te get metodu yoktur!
```
✅ **Çözüm:** Ya `for-each` döngüsüyle gezin ya da `contains()` ile varlığını sorgulayın.

---

### 2. `equals()` Metodunu Ezip `hashCode()` Metodunu Unutmak
Eğer bir sınıfta `equals()` ezilip `hashCode()` ezilmezse, nesneler farklı kovalara gideceği için `Set` aynı nesneden birden fazla eklemeye başlar ve sistem çöker. **İkisi her zaman birlikte ezilmelidir.**

---

### 3. `TreeSet` İçine `null` Koymaya Çalışmak
`HashSet` içine bir tane `null` koyabilirsiniz; ancak `TreeSet.add(null)` yazarsanız anında **`NullPointerException`** alırsınız.

---

## 10. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Çıktı ve Boyut Tahmini
Aşağıdaki kod çalıştığında `sayilar.size()` değeri kaç olur?

```java
Set<Integer> sayilar = new HashSet<>();
sayilar.add(10);
sayilar.add(20);
sayilar.add(10);
sayilar.add(30);
sayilar.add(20);

System.out.println(sayilar.size());
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Cevap:** `3`
**Açıklama:** Kümeler tekrarlı elemanları reddeder. Kümede yalnızca `10, 20, 30` kalır.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında neden derleme veya çalışma anı hatası alınmaktadır?

```java
TreeSet<String> sehirler = new TreeSet<>();
sehirler.add("Ankara");
sehirler.add(null);
sehirler.add("İzmir");
```

<details>
<summary>🔍 <b>Hata ve Çözümü Görmek İçin Tıklayın</b></summary>

**Hata:** `TreeSet` elemanları sıralamak için `compareTo()` metodunu kullanır. `null` nesnesi kıyaslanamayacağı için `sehirler.add(null)` satırında **`NullPointerException`** fırlatılır.
</details>

---

### Alıştırma 3: Tekrarlı Kelimeleri Temizleme
Bir metin cümlesindeki tüm kelimeleri boşluklardan parçalayıp (`split(" ")`), tekrarsız ve alfabetik sıralı olarak ekrana basan bir `TreeSet` metodu yazınız.

---

## 11. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Piyango Çekiliş Simülatörü (`PiyangoTest.java`)

**Gereksinimler:**
1. 1 ile 49 arasında rastgele 6 adet benzersiz sayı üreten bir loto çekiliş programı yazınız.
2. Sayıların benzersiz olmasını sağlamak için bir `HashSet<Integer>` kullanınız.
3. Çekilen sayıları küçükten büyüğe sıralı şekilde ekrana basmak için bir `TreeSet<Integer>`'e aktarınız.
4. Konsola çekiliş sonuçlarını düzenli olarak yazdırınız.

---

### 🟡 Ödev 2 (Orta Seviye): Ortak Arkadaş Bulucu (`SosyalAgTest.java`)

**Gereksinimler:**
1. `Kullanici` sınıfı (`kullaniciAdi`, `Set<String> arkadasListesi`).
2. İki kullanıcının arkadaş listeleri üzerinde matematiksel küme metotlarını kullanarak:
   - `ortakArkadaslar(Kullanici k1, Kullanici k2)`: Kesişim (`retainAll`) kullanarak ortak arkadaşları dönsün.
   - `arkadasOnerileri(Kullanici k1, Kullanici k2)`: Fark (`removeAll`) kullanarak k1'de olmayan ama k2'de olan kişileri önersin.
3. Test sınıfında 5 kişilik arkadaş grupları ile sistemi test ediniz.

---

### 🔴 Ödev 3 (İleri Seviye): Arama Motoru Kelime İndeksleyici (`KelimeIndeksleyici.java`)

**Gereksinimler:**
1. Bir web sayfası veya kitap metnindeki kelimeleri analiz eden bir sistem tasarlayınız.
2. `stopWords` (etkisiz kelimeler: "ve", "veya", "ile", "için", "bir") içeren bir `HashSet` tanımlayınız.
3. Verilen uzun bir paragraftaki kelimeleri ayıklayınız:
   - Noktalama işaretlerini temizleyiniz.
   - Etkisiz kelimeleri (`stopWords`) kümeden çıkartınız (`removeAll`).
   - Kalan anlamlı kelimeleri bir `TreeSet<String>` içinde alfabetik olarak indeksleyip toplam benzersiz kelime sayısını raporlayınız.

