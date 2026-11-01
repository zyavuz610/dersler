# 6. Iterator, Sıralama Araçları ve Collections Sınıfı

Bu doküman; Java Koleksiyon Çerçevesi'nde elemanlar üzerinde gezinirken programların çökmesini önleyen **`Iterator` ve `ListIterator`** mekanizmalarını, meşhur **`ConcurrentModificationException`** hatasını ve çözümünü, nesneleri sıralamak için kullanılan **`Comparable` ve `Comparator`** arayüzlerini, **`Collections`** yardımcı sınıfının metotlarını ve modern Java'nın **`List.of()` / `Set.of()`** değiştirilemez koleksiyonlarını ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Map Arayüzü ve Anahtar-Değer Çiftleri (5_map_ve_anahtar_deger.md)](./5_map_ve_anahtar_deger.md)
> - 🔝 [Modül Başlangıcı: Koleksiyonlara Giriş ve Jenerikler (1_generics_ve_koleksiyonlar_giris.md)](./1_generics_ve_koleksiyonlar_giris.md)

---

## 📌 İçindekiler
1. [`Iterator` Arayüzü ve Güvenli Gezinme](#1-iterator-arayüzü-ve-güvenli-gezinme)
   - [`for-each` Döngüsünün Arka Planı ve `Iterable`](#for-each-döngüsünün-arka-planı-ve-iterable)
   - [`Iterator` Temel Metotları (`hasNext`, `next`, `remove`)](#iterator-temel-metotları)
   - [`ListIterator`: Çift Yönlü Gezinme Yeteneği](#listiterator-çift-yönlü-gezinme-yeteneği)
2. [Büyük Tuzak: `ConcurrentModificationException`](#2-büyük-tuzak-concurrentmodificationexception)
   - [Döngü Sırasında `list.remove()` Çağrılırsa Neden Çöker?](#döngü-sırasında-listremove-çağrılırsa-neden-çöker)
   - [Doğru Çözüm: `iterator.remove()`](#doğru-çözüm-iteratorremove)
3. [Nesneleri Sıralama: `Comparable` vs. `Comparator`](#3-nesneleri-sıralama-comparable-vs-comparator)
   - [`Comparable<T>`: Doğal Sıralama (`compareTo`)](#1-comparablet-doğal-sıralama)
   - [`Comparator<T>`: Esnek / Çoklu Sıralama (`compare`)](#2-comparatort-esnek--çoklu-sıralama)
   - [Büyük Karşılaştırma Tablosu](#comparable-vs-comparator-karşılaştırma-tablosu)
   - [Modern Java 8 Lambda ve `Comparator.comparing()`](#modern-java-8-lambda-ve-comparatorcomparing)
4. [`java.util.Collections` Yardımcı Sınıfı](#4-javautilcollections-yardımcı-sınıfı)
   - [Sıralama ve Karıştırma: `sort()`, `reverse()`, `shuffle()`](#sıralama-ve-karıştırma)
   - [Arama ve İstatistik: `binarySearch()`, `min()`, `max()`, `frequency()`](#arama-ve-istatistik)
   - [Değiştirilemez Koleksiyonlar: `unmodifiableList()`](#değiştirilemez-koleksiyonlar)
5. [Modern Java (Java 9+) Değiştirilemez Koleksiyon Fabrikaları](#5-modern-java-değiştirilemez-koleksiyon-fabrikaları)
   - [`List.of()`, `Set.of()`, `Map.of()` Kuralları](#listof-setof-mapof-kuralları)
6. [Adım Adım Çalıştırılabilir Kod Örnekleri](#6-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: `Iterator` ile Güvenli Eleman Filtreleme (`IteratorDemo.java`)](#örnek-1-iterator-ile-güvenli-eleman-filtreleme)
   - [Örnek 2: `Comparable` ve `Comparator` ile Çok Yönlü Sıralama (`OgrenciSiralamaTest.java`)](#örnek-2-comparable-ve-comparator-ile-çok-yönlü-sıralama)
   - [Örnek 3: `Collections` Araç Sınıfı Yetenekleri (`CollectionsAraclariTest.java`)](#örnek-3-collections-araç-sınıfı-yetenekleri)
7. [Sık Yapılan Hatalar ve Önemli Kurallar](#7-sık-yapılan-hatalar-ve-önemli-kurallar)
8. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
9. [Ödev Konuları ve Mini Projeler](#9-ödev-konuları-ve-mini-projeler)

---

## 1. `Iterator` Arayüzü ve Güvenli Gezinme

Bir koleksiyon içindeki tüm elemanları sırayla ziyaret etme işlemine **İterasyon (Iteration / Gezinme)** denir.

### `for-each` Döngüsünün Arka Planı
Java'da yazdığımız her `for-each` döngüsü:
```java
for (String isim : liste) {
    System.out.println(isim);
}
```
Aslında derleyici tarafından arka planda bir **`Iterator` nesnesine** dönüştürülür:
```java
Iterator<String> it = liste.iterator();
while (it.hasNext()) {
    String isim = it.next();
    System.out.println(isim);
}
```

### `Iterator` Temel Metotları
- `hasNext()`: Kuyrukta veya listede okunacak bir sonraki eleman var mı? (`true`/`false`).
- `next()`: Sıradaki elemanı döndürür ve imleci bir adım ileri kaydırır.
- `remove()`: `next()` ile az önce okunan elemanı koleksiyondan **güvenli bir şekilde** siler!

### `ListIterator`: Çift Yönlü Gezinme
Yalnızca `List` sınıflarında çalışan `ListIterator`, hem ileri hem geri hareket edebilir:
- `hasPrevious()` ve `previous()` ile geriye doğru tarama.
- `set(E e)` ile elemanı güncelleyebilme.

---

## 2. Büyük Tuzak: `ConcurrentModificationException`

Geliştiricilerin en sık düştüğü sinsi tuzak: **Döngü içinde gezinirken doğrudan `liste.remove()` çağırmaktır!**

```java
List<String> sehirler = new ArrayList<>(List.of("Ankara", "İstanbul", "İzmir"));

// ❌ PROGRAM ANINDA ÇÖKER:
for (String s : sehirler) {
    if (s.equals("İstanbul")) {
        sehirler.remove(s); // 💣 RUNTIME CRASH: ConcurrentModificationException!
    }
}
```

### Neden Çöker?
Java koleksiyonları içlerinde bir sayaç tutar: **`modCount` (Modification Count)**. 
Döngü dönerken siz el altından `sehirler.remove()` derseniz, listenin eleman sayısı değişir fakat döngünün imleci bunu bilmez. Java veri tutarlılığını korumak için işlemi anında keser ve istisna fırlatır!

### Doğru Çözüm: `iterator.remove()`
Koleksiyondan eleman silerken listenin kendisinden değil, gezginin kendi `remove()` metodundan silinmelidir:

```java
Iterator<String> it = sehirler.iterator();
while (it.hasNext()) {
    String s = it.next();
    if (s.equals("İstanbul")) {
        it.remove(); // ✅ TAMAMEN GÜVENLİ VE DOĞRU YOL!
    }
}
```

---

## 3. Nesneleri Sıralama: `Comparable` vs. `Comparator`

İlkel sayıları veya metinleri (`Integer`, `String`) sıralamak kolaydır; çünkü Java onların nasıl sıralanacağını bilir ($1 < 2$, $"A" < "B"$). 
Fakat kendi oluşturduğumuz `Ogrenci` veya `Urun` sınıflarını neye göre sıralayacak? Numaraya göre mi, ada göre mi, fiyata göre mi?

İşte bu karar iki arayüzle verilir:

---

### 1. `Comparable<T>`: Doğal Sıralama
Sınıfın bizzat **kendi içine** uygulanır (`implements Comparable<T>`).
- Sınıfa **tek bir varsayılan (doğal) sıralama kuralı** kazandırır.
- Tek bir metodu vardır: `public int compareTo(T diger)`.

```java
public class Ogrenci implements Comparable<Ogrenci> {
    private int no;
    private String ad;

    @Override
    public int compareTo(Ogrenci diger) {
        // Küçükten büyüğe numara sıralaması:
        return Integer.compare(this.no, diger.no);
    }
}
```

---

### 2. `Comparator<T>`: Esnek / Çoklu Sıralama
Sınıfın dışından, bağımsız bir sınıf veya lambda olarak yazılır.
- Bir sınıfa **birden fazla farklı sıralama mantığı** kazandırmak için kullanılır (örn: İster ada göre, ister nota göre, ister tarihe göre).
- Metodu: `public int compare(T o1, T o2)`.

```java
// Ada göre sıralayan bağımsız karşılaştırıcı:
Comparator<Ogrenci> adaGoreSirala = (o1, o2) -> o1.getAd().compareTo(o2.getAd());
```

---

### `Comparable` vs. `Comparator` Karşılaştırma Tablosu

| Kriter | `Comparable` | `Comparator` |
| :--- | :--- | :--- |
| **Paket** | `java.lang` | `java.util` |
| **Metot** | `compareTo(T o)` (Tek parametre) | `compare(T o1, T o2)` (İki parametre) |
| **Sıralama Sayısı** | **Yalnızca 1 adet** (Doğal sıralama) | **İstenildiği kadar farklı** kriter yazılabilir |
| **Sınıfı Değiştirme** | Orijinal sınıfın kaynak kodunu değiştirmek şarttır | Orijinal sınıfa dokunmadan dışarıdan yazılır |
| **Çağrı Şekli** | `Collections.sort(liste)` | `Collections.sort(liste, comparator)` |

---

### Modern Java 8 Lambda ve `Comparator.comparing()`
Java 8 ile birlikte `Comparator` tanımlamak son derece zarif bir hale gelmiştir:

```java
// Not ortalamasına göre büyükten küçüğe sırala:
ogrenciler.sort(Comparator.comparing(Ogrenci::getGpa).reversed());
```

---

## 4. `java.util.Collections` Yardımcı Sınıfı

`Collections` (sonunda 's' olan), tıpkı `Math` sınıfı gibi tüm metotları `static` olan dev bir araç kutusudur.

### En Popüler Metotlar
- `Collections.sort(liste)`: Listeyi sıralar.
- `Collections.reverse(liste)`: Listeyi tersten yazar.
- `Collections.shuffle(liste)`: Elemanları rastgele karıştırır (İskambil kağıdı karmak gibi).
- `Collections.min(koleksiyon)` / `Collections.max(koleksiyon)`: En küçük ve en büyük elemanı bulur.
- `Collections.frequency(koleksiyon, eleman)`: O elemanın koleksiyonda kaç defa geçtiğini sayar.
- `Collections.swap(liste, i, j)`: İki indeksteki elemanın yerini takas eder.
- `Collections.unmodifiableList(liste)`: Listenin değiştirilemez (salt okunur) bir kopyasını sunar.

---

## 5. Modern Java (Java 9+) Değiştirilemez Koleksiyon Fabrikaları

Java 9 ile birlikte tek satırda koleksiyon oluşturmak inanılmaz kolaylaşmıştır:

```java
List<String> gunler = List.of("Pzt", "Sal", "Çar");
Set<Integer> sayilar = Set.of(10, 20, 30);
Map<String, Integer> yaslar = Map.of("Ali", 20, "Ayşe", 25);
```

### 🔴 Bu Fabrika Metotlarının Kesin Kuralları:
1. **Değiştirilemezdir (Immutable):** Bu listelere sonradan `add()`, `remove()` veya `set()` yapamazsınız; yaparsanız **`UnsupportedOperationException`** alırsınız!
2. **`null` YASAKTIR:** `List.of(null)` yazarsanız derhal çöker.
3. **`Set.of` içinde tekrar eden eleman YASAKTIR:** `Set.of("A", "A")` yazarsanız derleme anında veya çalışma anında `IllegalArgumentException: duplicate element` verir!

---

## 6. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: `Iterator` ile Güvenli Eleman Filtreleme

Koleksiyondan 50'nin altındaki notları güvenli biçimde silme:

#### `IteratorDemo.java`
```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {
    public static void main(String[] args) {
        List<Integer> notlar = new ArrayList<>();
        notlar.add(45);
        notlar.add(85);
        notlar.add(30);
        notlar.add(90);
        notlar.add(50);

        System.out.println("İlk Not Listesi: " + notlar);

        // Güvenli Silme Yolu: Iterator kullanımı
        Iterator<Integer> it = notlar.iterator();
        while (it.hasNext()) {
            int notDegeri = it.next();
            if (notDegeri < 50) {
                it.remove(); // Güvenli silme!
            }
        }

        System.out.println("50 ve Üzeri Kalan Notlar: " + notlar);
    }
}
```

---

### Örnek 2: `Comparable` ve `Comparator` ile Çok Yönlü Sıralama

#### `Ogrenci.java`
```java
public class Ogrenci implements Comparable<Ogrenci> {
    private int no;
    private String ad;
    private double gpa;

    public Ogrenci(int no, String ad, double gpa) {
        this.no = no;
        this.ad = ad;
        this.gpa = gpa;
    }

    public int getNo() { return no; }
    public String getAd() { return ad; }
    public double getGpa() { return gpa; }

    // 1. DOĞAL SIRALAMA (Comparable): Numaraya göre küçükten büyüğe
    @Override
    public int compareTo(Ogrenci diger) {
        return Integer.compare(this.no, diger.no);
    }

    @Override
    public String toString() {
        return String.format("[%d] %-10s (GPA: %.2f)", no, ad, gpa);
    }
}
```

#### `OgrenciSiralamaTest.java`
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OgrenciSiralamaTest {
    public static void main(String[] args) {
        List<Ogrenci> sinif = new ArrayList<>();
        sinif.add(new Ogrenci(105, "Zeynep", 3.45));
        sinif.add(new Ogrenci(101, "Ahmet", 2.80));
        sinif.add(new Ogrenci(108, "Burak", 3.90));
        sinif.add(new Ogrenci(102, "Canan", 3.10));

        // 1. Doğal Sıralama (Numaraya göre - Comparable):
        Collections.sort(sinif);
        System.out.println("=== 1. NUMARAYA GÖRE SIRALI (Comparable) ===");
        sinif.forEach(System.out::println);

        // 2. Özel Sıralama: İsme göre alfabetik (Comparator):
        sinif.sort(Comparator.comparing(Ogrenci::getAd));
        System.out.println("\n=== 2. İSME GÖRE ALFABETİK SIRALI (Comparator) ===");
        sinif.forEach(System.out::println);

        // 3. Özel Sıralama: Nota (GPA) göre yüksekten düşüğe:
        sinif.sort(Comparator.comparing(Ogrenci::getGpa).reversed());
        System.out.println("\n=== 3. NOT ORTALAMASINA GÖRE ZİRVEDEN DİBE (Reversed) ===");
        sinif.forEach(System.out::println);
    }
}
```

---

### Örnek 3: `Collections` Araç Sınıfı Yetenekleri

#### `CollectionsAraclariTest.java`
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsAraclariTest {
    public static void main(String[] args) {
        List<Integer> sayilar = new ArrayList<>(List.of(10, 40, 20, 50, 30, 20));

        System.out.println("Orijinal Liste: " + sayilar);

        // 1. Sıralama ve Ters Çevirme
        Collections.sort(sayilar);
        System.out.println("Sıralı        : " + sayilar);

        Collections.reverse(sayilar);
        System.out.println("Ters Çevrilmiş: " + sayilar);

        // 2. Karıştırma (Shuffle)
        Collections.shuffle(sayilar);
        System.out.println("Karıştırılmış : " + sayilar);

        // 3. İstatistik ve Frekans
        System.out.println("En Küçük (min): " + Collections.min(sayilar));
        System.out.println("En Büyük (max): " + Collections.max(sayilar));
        System.out.println("20 sayısının tekrar adedi: " + Collections.frequency(sayilar, 20));
    }
}
```

---

## 7. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `for-each` İçinde `remove()` Çağırmak
`for-each` döngüsü içinde koleksiyonun `remove()` metodunu çağırırsanız `ConcurrentModificationException` fırlatılır. Çözüm: **`iterator.remove()`** veya Java 8+ **`liste.removeIf(p -> ...)`** kullanmaktır.

---

### 2. `List.of()` ile Oluşturulan Değişmez Listeye Eleman Eklemeye Çalışmak
```java
List<String> sabitler = List.of("A", "B");
sabitler.add("C"); // ❌ RUNTIME CRASH: UnsupportedOperationException!
```
Eğer sonradan eleman ekleyecekseniz `new ArrayList<>(List.of("A", "B"))` şeklinde sarmalamalısınız.

---

## 8. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Çıktı Tahmini
Aşağıdaki kod çalıştığında konsola ne yazdırılır?

```java
List<String> list = new ArrayList<>(List.of("kedi", "kopek", "kus"));
Iterator<String> it = list.iterator();

while(it.hasNext()) {
    String s = it.next();
    if(s.startsWith("k")) {
        it.remove();
    }
}

System.out.println(list);
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
`[]` (Boş liste)
**Açıklama:**
Listedeki tüm kelimeler ("kedi", "kopek", "kus") "k" harfi ile başladığı için `it.remove()` hepsi için çalışır ve liste tamamen boşalır.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında neden `UnsupportedOperationException` hatası alınmaktadır?

```java
List<Integer> rakamlar = List.of(1, 2, 3);
Collections.sort(rakamlar);
```

<details>
<summary>🔍 <b>Hata ve Çözümü Görmek İçin Tıklayın</b></summary>

**Hata:** `List.of()` metodu değiştirilemez (*Immutable*) bir liste üretir. `Collections.sort()` metodu ise listeyi yerinde (*in-place*) değiştirerek sıralamaya çalışır. Değiştirilemez listeler üzerinde değişiklik yapılamayacağı için **`UnsupportedOperationException`** fırlatılır.
**Düzeltme:** `List<Integer> rakamlar = new ArrayList<>(List.of(1, 2, 3));`
</details>

---

### Alıştırma 3: Uzunluğa Göre Kelime Sıralama
Parametre olarak verilen bir `List<String>` kelime listesini alfabetik sıraya göre değil, **kelimelerin harf uzunluklarına göre (en kısadan en uzuna)** sıralayan bir `Comparator` yazınız.

---

## 9. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Kitap Sıralama Motoru (`KitapSiralamaTest.java`)

**Gereksinimler:**
1. `Kitap` sınıfı (`baslik`, `yazar`, `sayfaSayisi`, `fiyat`).
2. Sınıf `Comparable<Kitap>` arayüzünü uygulasın: Doğal sıralaması **sayfa sayısına göre küçükten büyüğe** olsun.
3. 2 farklı `Comparator` yazınız:
   - `FiyataGoreSirala`: Fiyata göre en pahalıdan en ucuza sıralasın.
   - `IsmeGoreSirala`: Başlığa göre A'dan Z'ye sıralasın.
4. 5 kitaplık bir listeyi bu 3 farklı kritere göre sıralayıp ekrana basınız.

---

### 🟡 Ödev 2 (Orta Seviye): Akıllı Öğrenci Not Filtreleme ve İstatistik (`NotAnaliz.java`)

**Gereksinimler:**
1. 10 kişilik bir öğrenci listesi oluşturunuz.
2. `Iterator` veya `removeIf` kullanarak:
   - Devamsızlığı 5 günden fazla olan öğrencileri listeden güvenli şekilde temizleyiniz.
3. `Collections` sınıfı araçlarını kullanarak:
   - Sınıfın en yüksek notunu (`Collections.max`) ve en düşük notunu (`Collections.min`) bulunuz.
   - Not listesini karıştırıp (`Collections.shuffle`) rastgele bir sözlü sırası oluşturunuz.

---

### 🔴 Ödev 3 (İleri Seviye): E-Ticaret Çok Kriterli Ürün Filtreleme Motoru (`UrunFiltrelemeMotoru.java`)

**Gereksinimler:**
1. `Urun` sınıfı (`ad`, `kategori`, `fiyat`, `stokAdedi`, `puan` [1-5]).
2. Kullanıcının web sitesindeki filtreleme seçeneklerini simüle ediniz (Zincirleme `Comparator` kullanarak):
   - Kural 1: Önce kategoriye göre alfabetik sıralansın,
   - Kural 2: Aynı kategorideki ürünler fiyata göre artan sırada olsun,
   - Kural 3: Fiyatı aynı olanlar kullanıcı puanına (`puan`) göre azalan sırada dizilsin!
   (`Comparator.comparing(...).thenComparing(...).thenComparing(...)`).
3. Bu esnek sıralama motorunu 8 farklı ürün üzerinde test edip konsola profesyonel bir katalog çıktısı basınız.

