# 2. List Arayüzü ve Uygulamaları (ArrayList, LinkedList, Vector, Stack)

Bu doküman; Java Koleksiyon Çerçevesi'nde en çok kullanılan ve **sıralı (ordered)**, **indeks tabanlı** ve **tekrarlı (duplicate)** eleman barındırabilen **`List`** arayüzünü, en popüler iki uygulaması olan **`ArrayList`** ve **`LinkedList`** sınıflarının bellek ve çalışma mimarisini, eski nesil **`Vector`** ve **`Stack`** yapılarını ve hangisinin ne zaman seçilmesi gerektiğini ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Koleksiyonlara Giriş ve Jenerikler (1_generics_ve_koleksiyonlar_giris.md)](./1_generics_ve_koleksiyonlar_giris.md)
> - 🔜 [Sonraki Bölüm: Set Arayüzü ve Kümeler (3_set_ve_kumeler.md)](./3_set_ve_kumeler.md)

---

## 📌 İçindekiler
1. [`List` Arayüzünün Temel Özellikleri](#1-list-arayüzünün-temel-özellikleri)
   - [Sıralılık (Insertion Order), İndeks Tabanlı Erişim ve Tekrara İzin Verme](#sıralılık-indeks-tabanlı-erişim-ve-tekrara-izin-verme)
   - [`List` Arayüzüne Özgü Ekstra Metotlar](#list-arayüzüne-özgü-ekstra-metotlar)
2. [`ArrayList`: Dinamik Dizi Mimarisi](#2-arraylist-dinamik-dizi-mimarisi)
   - [Arka Plandaki Çalışma Mantığı ve Büyüme Formülü ($1.5\times$)](#arka-plandaki-çalışma-mantığı-ve-büyüme-formülü)
   - [Avantajları ve Zayıf Yönleri (Zaman Karmaşıklığı $O(1)$ vs $O(n)$)](#avantajları-ve-zayıf-yönleri)
3. [`LinkedList`: Çift Yönlü Bağlı Liste (Doubly Linked List)](#3-linkedlist-çift-yönlü-bağlı-liste)
   - [Düğüm (Node), Önceki (Prev) ve Sonraki (Next) Referans Mantığı](#düğüm-node-önceki-prev-ve-sonraki-next-referans-mantığı)
   - [Neden Başa ve Ortaya Eklemede Çok Hızlıdır?](#neden-başa-ve-ortaya-eklemede-çok-hızlıdır)
4. [`ArrayList` vs. `LinkedList` Karşılaştırması](#4-arraylist-vs-linkedlist-karşılaştırması)
   - [Büyük Performans ve Bellek Karşılaştırma Tablosu](#büyük-performans-ve-bellek-karşılaştırma-tablosu)
   - [Seçim Rehberi: Hangisini Ne Zaman Kullanmalıyım?](#seçim-rehberi-hangisini-ne-zaman-kullanmalıyım)
5. [Eski Nesil Listeler: `Vector` ve `Stack`](#5-eski-nesil-listeler-vector-ve-stack)
   - [`Vector` Neden Artık Tercih Edilmez? (Senkronizasyon Maliyeti)](#vector-neden-artık-tercih-edilmez)
   - [`Stack` Sınıfı ve LIFO (Last-In-First-Out) Mantığı](#stack-sınıfı-ve-lifo-mantığı)
6. [Adım Adım Çalıştırılabilir Kod Örnekleri](#6-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: `ArrayList` ile CRUD İşlemleri ve Dinamik Büyüme (`OgrenciListesi.java`)](#örnek-1-arraylist-ile-crud-işlemleri)
   - [Örnek 2: `LinkedList` ile Hızlı Başa/Araya Ekleme (`GorevKuyrugu.java`)](#örnek-2-linkedlist-ile-hızlı-başaaraya-ekleme)
   - [Örnek 3: `Stack` ile Tarayıcı Geçmişi / Geri Al (Undo) Simülasyonu (`GeriAlSimulasyon.java`)](#örnek-3-stack-ile-tarayıcı-geçmişi-simülasyonu)
7. [Sık Yapılan Hatalar ve Önemli Kurallar](#7-sık-yapılan-hatalar-ve-önemli-kurallar)
   - [Sayısal Listelerde `remove(int)` İndeks vs. `remove(Object)` Değer Tuzağı](#sayısal-listelerde-remove-tuzağı)
8. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
9. [Ödev Konuları ve Mini Projeler](#9-ödev-konuları-ve-mini-projeler)

---

## 1. `List` Arayüzünün Temel Özellikleri

`List`, Java'da en çok başvurulan koleksiyon türüdür. Onu diğer yapılardan ayıran **3 temel kural** vardır:

1. **Sıralıdır (Ordered / Sequence):** Elemanlar listeye hangi sırayla eklendiyse o sırada tutulur (*Insertion Order* korunur).
2. **İndeks Tabanlıdır (Zero-based Index):** Tıpkı diziler gibi ilk eleman `0`, ikinci eleman `1` numaralı indekstir. İstenilen indekse doğrudan erişilebilir (`list.get(2)`).
3. **Tekrara İzin Verir (Duplicates Allowed):** Aynı nesneden veya değerden listeye defalarca eklenebilir (`["Ali", "Veli", "Ali"]`).

```
                [ List Arayüzü (java.util.List) ]
                 /              |              \
                /               |               \
        [ ArrayList ]    [ LinkedList ]     [ Vector ]
                                                |
                                            [ Stack ]
```

### `List` Arayüzüne Özgü Ekstra Metotlar
`Collection`'dan gelen metotlara ek olarak `List`, indeks tabanlı özel metotlar sunar:

- `get(int index)`: Belirtilen indeksteki elemanı okur.
- `set(int index, E element)`: Belirtilen indeksteki elemanı günceller/değiştirir.
- `add(int index, E element)`: Araya eleman sıkıştırır (sağdakiler otomatik bir basamak kayar).
- `remove(int index)`: Belirtilen indeksteki elemanı listeden çıkarır.
- `indexOf(Object o)`: Elemanın ilk görüldüğü indeksi döner (yoksa `-1`).
- `subList(int fromIndex, int toIndex)`: Belirli aralıktaki alt listeyi döner.

---

## 2. `ArrayList`: Dinamik Dizi Mimarisi

`ArrayList`, arka planda **standart bir Java dizisi (`Object[]`)** barındıran akıllı bir sınıftır.

```
İçerideki Gizli Dizi:
Başlangıç: [ "A" | "B" | "C" | null | null | null | null | null | null | null ] (Kapasite: 10, Eleman Sayısı: 3)
```

### Büyüme Formülü ($1.5\times$)
1. Siz hiçbir şey belirtmezseniz varsayılan kapasite **`10`** olarak başlar.
2. 10 eleman dolup 11. eleman eklendiğinde Java arka planda sessizce şu adımları işletir:
   - Eski dizinin yaklaşık **1.5 katı** büyüklüğünde yeni bir dizi açar ($10 \to 15 \to 22 \to 33 \dots$).
   - Eski dizideki tüm elemanları yeni diziye kopyalar (`System.arraycopy`).
   - Yeni elemanı ekler.

### Avantajları ve Zayıf Yönleri
- ✅ **Mükemmel Okuma Hızı ($O(1)$):** Dizinin bellek adresine doğrudan formülle (`tabanAdres + indeks * boyut`) gidildiği için 1.000.000 elemanlı bir listede `liste.get(500000)` işlemi anında biter.
- ❌ **Yavaş Araya Ekleme/Silme ($O(n)$):** Listenin en başına yeni eleman eklerseniz, mevcut tüm elemanların sağa kaydırılması gerekir.

---

## 3. `LinkedList`: Çift Yönlü Bağlı Liste

`LinkedList`, bellekte bitişik bir dizi tutmaz. Bunun yerine birbirine adres referanslarıyla bağlı **Düğümlerden (Node)** oluşur.

```
           +--------------------+      +--------------------+      +--------------------+
null <--- | Prev | Düğüm 1 | Next |--->| Prev | Düğüm 2 | Next |--->| Prev | Düğüm 3 | Next |---> null
           +--------------------+<---|  +--------------------+<---|  +--------------------+
                "Elma"                       "Armut"                      "Muz"
```

Her düğüm (Node) 3 parça bilgi taşır:
1. **Data:** Elemanın kendisi (örn. "Elma").
2. **Next:** Bir sonraki düğümün bellekteki referansı.
3. **Prev:** Bir önceki düğümün bellekteki referansı.

### Neden Başa ve Ortaya Eklemede Çok Hızlıdır?
Yeni bir düğüm eklerken hiçbir elemanı sağa-sola kaydırmak gerekmez! Yalnızca iki düğüm arasındaki okların (referansların) yönünü yeni düğüme bağlamak yeterlidir ($O(1)$).

---

## 4. `ArrayList` vs. `LinkedList` Karşılaştırması

### Performans ve Bellek Karşılaştırma Tablosu

| Operasyon | `ArrayList` Zaman Karmaşıklığı | `LinkedList` Zaman Karmaşıklığı | Kazanan & Neden |
| :--- | :---: | :---: | :--- |
| **Rastgele Okuma (`get(i)`)** | **$O(1)$ (Anında)** | $O(n)$ (Baştan sayarak arar) | 🏆 **ArrayList** (Açık ara) |
| **Sona Ekleme (`add(e)`)** | $O(1)$ (Amortized) | $O(1)$ | 🤝 **Berabere** |
| **Başa Ekleme / Silme** | $O(n)$ (Herkesi kaydırır) | **$O(1)$ (Sadece bağ değiştirir)** | 🏆 **LinkedList** |
| **Araya Eleman Ekleme** | $O(n)$ (Kaydırma maliyeti) | $O(n)$ (Elemanı bulma) + $O(1)$ (Bağlama) | ⚖️ Duruma bağlı |
| **Bellek Tüketimi** | **Düşük** (Sadece veriyi tutar) | **Yüksek** (Veri + 2 adet referans pointer) | 🏆 **ArrayList** |

---

### Seçim Rehberi: Hangisini Ne Zaman Kullanmalıyım?

```
   İhtiyacınız sık sık eleman OKUMAK ve listelemek mi?
   (Örn: Ürün kataloğu, kullanıcı listesi)
                     |
            +--------+--------+
            |                 |
         [ EVET ]          [ HAYIR ]
            |                 |
     ArrayList Kullan!        Sık sık BAŞA veya ARAYA eleman eklenip siliniyor mu?
                              (Örn: Müzik çalma sırası, anlık işlem kuyruğu)
                                       |
                              +--------+--------+
                              |                 |
                           [ EVET ]          [ HAYIR ]
                              |                 |
                       LinkedList Kullan!   ArrayList Kullan!
```

> 💡 **Sektör Gerçeği:** Endüstriyel projelerde geliştiriciler vakaların **%95'inde `ArrayList`** tercih eder; çünkü modern CPU mimarilerinde ardışık bellek dizileri (cache locality), dağınık referanslı düğümlerden çok daha verimli çalışır.

---

## 5. Eski Nesil Listeler: `Vector` ve `Stack`

### `Vector` Neden Artık Tercih Edilmez?
Java 1.0 ile gelen `Vector`, tıpkı `ArrayList` gibi dinamik bir dizidir. Ancak içindeki hemen hemen her metot **`synchronized` (iş parçacığı kilitli)** tanımlanmıştır. 
- Bu durum tek kanallı standart programlarda bile devasa bir performans kaybına yol açar.
- Çok kanallı (*multithreading*) ortamlarda ise modern `CopyOnWriteArrayList` veya `Collections.synchronizedList()` tercih edilir.

---

### `Stack` Sınıfı ve LIFO Mantığı

`Vector`'den türeyen `Stack`, **LIFO (Last-In-First-Out / Son Giren İlk Çıkar)** kuralıyla çalışan bir yığındır.
Gerçek hayat analojisi: Üst üste dizilmiş yemek tabakları. En son konan tabak en önce alınır.

Temel Metotları:
- `push(E item)`: Yığının tepesine eleman koyar.
- `pop()`: En tepedeki elemanı yığından çıkarır ve döndürür.
- `peek()`: En tepedeki elemana bakar ama silmez.
- `empty()`: Yığın boş mu sorgular.

---

## 6. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: `ArrayList` ile CRUD İşlemleri

#### `OgrenciListesi.java`
```java
import java.util.ArrayList;
import java.util.List;

public class OgrenciListesi {
    public static void main(String[] args) {
        // En iyi pratik: Sol taraf arayüz (List), sağ taraf somut sınıf (ArrayList)
        List<String> ogrenciler = new ArrayList<>();

        // 1. CREATE (Ekleme)
        ogrenciler.add("Ahmet Kaya");
        ogrenciler.add("Zeynep Demir");
        ogrenciler.add("Burak Çelik");
        ogrenciler.add(1, "Elif Aydın"); // 1. indekse araya sıkıştırma

        System.out.println("Oluşturulan Liste: " + ogrenciler);

        // 2. READ (Okuma)
        String ilkOgrenci = ogrenciler.get(0);
        System.out.println("İlk Sıradaki: " + ilkOgrenci);

        // 3. UPDATE (Güncelleme)
        ogrenciler.set(2, "Zeynep Demir (Temsilci)");
        System.out.println("Güncelleme Sonrası: " + ogrenciler);

        // 4. DELETE (Silme)
        ogrenciler.remove("Burak Çelik"); // İsme göre silme
        ogrenciler.remove(0);             // İndekse göre silme (Ahmet silinir)

        System.out.println("Silme Sonrası Liste: " + ogrenciler);
        System.out.println("Mevcut Öğrenci Sayısı: " + ogrenciler.size());
    }
}
```

#### Konsol Çıktısı:
```text
Oluşturulan Liste: [Ahmet Kaya, Elif Aydın, Zeynep Demir, Burak Çelik]
İlk Sıradaki: Ahmet Kaya
Güncelleme Sonrası: [Ahmet Kaya, Elif Aydın, Zeynep Demir (Temsilci), Burak Çelik]
Silme Sonrası Liste: [Elif Aydın, Zeynep Demir (Temsilci)]
Mevcut Öğrenci Sayısı: 2
```

---

### Örnek 2: `LinkedList` ile Başa/Araya Hızlı Ekleme

#### `GorevKuyrugu.java`
```java
import java.util.LinkedList;

public class GorevKuyrugu {
    public static void main(String[] args) {
        // LinkedList'in kendine has başa/sona ekleme metotlarını kullanmak için:
        LinkedList<String> gorevler = new LinkedList<>();

        gorevler.add("E-postaları cevapla");
        gorevler.add("Raporu hazırla");

        // Başa çok acil görev ekleme (O(1)):
        gorevler.addFirst("🚨 KRİTİK: Sunucu hatasını düzelt!");

        // Sona görev ekleme (O(1)):
        gorevler.addLast("Ofisten çıkarken ışıkları kapat");

        System.out.println("=== GÜNLÜK GÖREV LİSTESİ ===");
        for (int i = 0; i < gorevler.size(); i++) {
            System.out.println((i + 1) + ". " + gorevler.get(i));
        }

        // İlk görevi bitirip listeden çıkaralım:
        String tamamlanan = gorevler.removeFirst();
        System.out.println("\nTamamlanan Görev: " + tamamlanan);
        System.out.println("Kalan İlk Görev: " + gorevler.getFirst());
    }
}
```

---

### Örnek 3: `Stack` ile Tarayıcı Geçmişi / Geri Al (Undo)

#### `GeriAlSimulasyon.java`
```java
import java.util.Stack;

public class GeriAlSimulasyon {
    public static void main(String[] args) {
        Stack<String> tarayiciGecmisi = new Stack<>();

        // Web sayfalarında gezinelim (push):
        tarayiciGecmisi.push("google.com");
        tarayiciGecmisi.push("github.com");
        tarayiciGecmisi.push("stackoverflow.com");

        System.out.println("Şu anki açık sayfa (peek): " + tarayiciGecmisi.peek());

        // Kullanıcı 2 defa 'Geri' (Back) butonuna bastı (pop):
        System.out.println("\n[GERİ BUTONUNA BASILDI] Sayfadan çıkılıyor: " + tarayiciGecmisi.pop());
        System.out.println("Aktif Sayfa: " + tarayiciGecmisi.peek());

        System.out.println("\n[GERİ BUTONUNA BASILDI] Sayfadan çıkılıyor: " + tarayiciGecmisi.pop());
        System.out.println("Aktif Sayfa: " + tarayiciGecmisi.peek());
    }
}
```

---

## 7. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. Sayısal Listelerde `remove(int)` Tuzağı (Çok Popüler Hata!)
`List` arayüzünde iki tane `remove` metodu vardır:
1. `remove(int index)` $\rightarrow$ O indeksteki elemanı siler.
2. `remove(Object o)` $\rightarrow$ O değere eşit olan nesneyi siler.

Eğer listeniz `List<Integer>` ise, `sayilar.remove(2)` yazdığınızda Java **`2` değerini değil, 2. indeksteki elemanı siler!**

```java
List<Integer> sayilar = new ArrayList<>();
sayilar.add(10);
sayilar.add(20);
sayilar.add(30);

sayilar.remove(1); // ❌ 1 değerini değil, 1. indeksteki 20'yi siler!
System.out.println(sayilar); // [10, 30]

// ✅ 10 DEĞERİNİ SİLMEK İÇİN:
sayilar.remove(Integer.valueOf(10)); // Nesneye sararak gönderilmelidir!
```

---

### 2. Liste Boyutunu Aşmak (`IndexOutOfBoundsException`)
Boyutu 3 olan bir listede son indeks `2`'dir. `liste.get(3)` programı anında çökertecektir.

---

### 3. Sol Tarafı Somut Sınıf Tanımlamak
```java
ArrayList<String> liste = new ArrayList<>(); // ⚠️ Kötü tasarım alışkanlığı
```
✅ **En İyi Pratik:** Kodunuzu somut sınıfa değil arayüze bağımlı kılın (*Code to Interface*):
```java
List<String> liste = new ArrayList<>(); // ✅ Yarın LinkedList yapmak isterseniz sadece sağı değiştirirsiniz!
```

---

## 8. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Kod Analizi ve Çıktı Tahmini
Aşağıdaki kod çalıştığında konsola ne basılır?

```java
List<String> harfler = new ArrayList<>();
harfler.add("A");
harfler.add("B");
harfler.add("C");
harfler.add(1, "X");
harfler.remove("B");

System.out.println(harfler);
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
`[A, X, C]`
**Açıklama:**
1. `A, B, C` eklenir.
2. `add(1, "X")` ile 1. indekse `X` konur: `[A, X, B, C]`.
3. `remove("B")` ile `B` silinir: `[A, X, C]`.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında `3` sayısını listeden silmek isteyen öğrenci neden hata almaktadır ve nasıl düzeltilir?

```java
List<Integer> puanlar = new ArrayList<>();
puanlar.add(5);
puanlar.add(3);
puanlar.add(8);

puanlar.remove(3); // Program IndexOutOfBoundsException verip çöküyor!
```

<details>
<summary>🔍 <b>Hata ve Çözümü Görmek İçin Tıklayın</b></summary>

**Sebep:** `puanlar.remove(3)` çağrısı 3. indeksi aramaktadır; oysa listenin son indeksi 2'dir.
**Düzeltme:** Değer olarak 3'ü silmek için `puanlar.remove(Integer.valueOf(3));` yazılmalıdır.
</details>

---

### Alıştırma 3: Küçük Tasarım (Metin Ters Çevirme)
Bir `Stack<Character>` kullanarak parametre olarak verilen bir `String` metni tersine çeviren `public static String tersCevir(String metin)` metodunu yazınız.

---

## 9. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Alışveriş Sepeti Yönetimi (`SepetTest.java`)

**Gereksinimler:**
1. `Urun` sınıfı (`String ad`, `double fiyat`).
2. `Sepet` sınıfı:
   - İçerisinde `List<Urun> urunler = new ArrayList<>();` barındırsın.
   - `urunEkle(Urun u)`: Sepete ürün eklesin.
   - `urunCikar(String urunAdi)`: İsme göre arayıp bulduğu ilk ürünü sepetten silsin.
   - `toplamFiyat()`: Sepetteki ürünlerin toplam tutarını hesaplasın.
   - `sepetiYazdir()`: Tüm ürünleri alt alta fiyatlarıyla basıp en alta genel toplamı yazsın.
3. Test sınıfında 4 farklı ürünle sepeti doldurup birini silerek simülasyonu çalıştırınız.

---

### 🟡 Ödev 2 (Orta Seviye): Müzik Çalma Listesi Simülatörü (`Playlist.java`)

**Gereksinimler:**
1. `Sarki` sınıfı (`baslik`, `sanatci`, `sureSaniye`).
2. `Playlist` sınıfı (`LinkedList<Sarki>` kullanarak):
   - `sarkiEkle(Sarki s)`: Listenin sonuna eklesin.
   - `basaTuttur(Sarki s)`: Şarkıyı listenin en başına alsın (`addFirst`).
   - `siradakiSarkiyiCal()`: Listenin ilk şarkısını çalsın ve listeden kaldırsın (`removeFirst`).
   - `karistir()`: Şarkıların sırasını karıştırsın.
3. Bir test sınıfı ile şarkı ekleme, araya alma ve oynatma senaryolarını test ediniz.

---

### 🔴 Ödev 3 (İleri Seviye): Parantez Dengeleme Denetleyicisi (`ParantezKontrol.java`)

**Gereksinimler:**
1. Yazılımcıların kod editörlerindeki parantez kontrol mekanizmasını bir `Stack<Character>` kullanarak kodlayınız.
2. Bir matematiksel ifade veya kod satırında açılan parantezlerin doğru sırayla kapatılıp kapatılmadığını kontrol eden `public static boolean dengeliMi(String ifade)` metodunu yazınız.
3. Test Senaryoları:
   - `"({[]})"` $\rightarrow$ `true` (Dengeli)
   - `"({[}])"` $\rightarrow$ `false` (Dengesiz: `[` açılmış ama `}` ile kapatılmış)
   - `"((()"` $\rightarrow$ `false` (Kapatılmamış parantez kalmış)
   - `"){("` $\rightarrow$ `false` (Açılmadan kapatılmış)

