# 1. Java Koleksiyonlarına Giriş ve Jenerikler (Generics)

Bu doküman; Java'da sabit boyutlu dizilerin (`Array`) sınırlarını aşmak için tasarlanan **Java Koleksiyon Çerçevesi'ne (Java Collections Framework)** giriş yapmakta; koleksiyonların belkemiği olan **Jenerik Tipleri (Generics - `<T>`)**, ilkel tipler ile sarmalayıcı sınıflar arasındaki **Autoboxing / Unboxing** mekanizmasını ve **`Collection`** ile **`Iterable`** kök arayüzlerini ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Modül: Nesne Yönelimli Programlama (5_class giriş)](../5_class%20giriş/readme.md)
> - 🔜 [Sonraki Bölüm: List Arayüzü ve Sınıfları (2_list_arayuzu_ve_siniflari.md)](./2_list_arayuzu_ve_siniflari.md)

---

## 📌 İçindekiler
1. [Neden Koleksiyonlara İhtiyaç Duyarız?](#1-neden-koleksiyonlara-ihtiyaç-duyarız)
   - [Sabit Boyutlu Dizilerin (`Array`) Yetersizlikleri](#sabit-boyutlu-dizilerin-array-yetersizlikleri)
   - [Koleksiyonların Sunduğu Özgürlükler](#koleksiyonların-sunduğu-özgürlükler)
2. [Sarmalayıcı Sınıflar (Wrapper Classes) ve Autoboxing](#2-sarmalayıcı-sınıflar-wrapper-classes-ve-autoboxing)
   - [İlkel Tipler (Primitives) vs. Nesne Tipleri (Wrappers)](#ilkel-tipler-primitives-vs-nesne-tipleri-wrappers)
   - [Autoboxing ve Unboxing Mekanizması](#autoboxing-ve-unboxing-mekanizması)
3. [Jenerikler (Generics) Nedir ve Neden Hayatidir?](#3-jenerikler-generics-nedir-ve-neden-hayatidir)
   - [Eski Java (Java 1.4 Öncesi): Ham Tipler ve `ClassCastException` Felaketi](#eski-java-ham-tipler-ve-classcastexception-felaketi)
   - [Tip Güvenliği (Type Safety) İlkesi](#tip-güvenliği-type-safety-ilkesi)
   - [Elmas Operatörü (Diamond Operator `<>`)](#elmas-operatörü-diamond-operator-)
4. [Kendi Jenerik Sınıf ve Metotlarımızı Yazmak](#4-kendi-jenerik-sınıf-ve-metotlarımızı-yazmak)
   - [Jenerik Sınıf Tanımlama (`Kutu<T>`)](#jenerik-sınıf-tanımlama-kutut)
   - [Jenerik Metot Tanımlama](#jenerik-metot-tanımlama)
5. [Koleksiyon Hiyerarşisinin Zirvesi: `Iterable` ve `Collection`](#5-koleksiyon-hiyerarşisinin-zirvesi-iterable-ve-collection)
   - [`Collection` Arayüzünün Ortak Temel Metotları](#collection-arayüzünün-ortak-temel-metotları)
6. [Adım Adım Çalıştırılabilir Kod Örnekleri](#6-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Tip Güvenliği ve Jenerik Farkı (`GenericsTest.java`)](#örnek-1-tip-güvenliği-ve-jenerik-farkı)
   - [Örnek 2: Özel Jenerik Depo Sınıfı (`Depo.java` & `DepoTest.java`)](#örnek-2-özel-jenerik-depo-sınıfı)
7. [Sık Yapılan Hatalar ve Önemli Kurallar](#7-sık-yapılan-hatalar-ve-önemli-kurallar)
8. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
9. [Ödev Konuları ve Mini Projeler](#9-ödev-konuları-ve-mini-projeler)

---

## 1. Neden Koleksiyonlara İhtiyaç Duyarız?

Şimdiye kadar birden fazla veriyi saklamak için **Dizileri (`int[]`, `String[]`, `Ogrenci[]`)** kullandık. Diziler hızlıdır ve belleğe bitişik yerleşir; ancak gerçek hayat yazılımlarında çok ciddi engellere takılırlar.

### Sabit Boyutlu Dizilerin Yetersizlikleri

```
DİZİ (ARRAY): Sabit Otel Odaları
[0] [1] [2] [3] [4]  --> Sadece 5 oda var! 6. müşteri geldiğinde otel büyüyemez.
                         Araya müşteri eklemek için herkesi sağa kaydırmak gerekir!
```

1. **Sabit Boyut (Fixed Size):** Dizi bir kez `new int[10]` diye tanımlandıktan sonra boyutu artırılamaz veya azaltılamaz. Eğer 11. eleman gelirse yeni ve daha büyük bir dizi açıp tüm elemanları oraya tek tek kopyalamak zorundasınız.
2. **Eleman Silme/Araya Ekleme Zorluğu:** Dizinin 2. elemanını sildiğinizde orada boş bir delik (`0` veya `null`) kalır. Tüm elemanları elinizle sola kaydırmanız gerekir.
3. **Hazır Veri Yapısı Eksikliği:** Dizi doğrudan bir kuyruk (FIFO), yığın (LIFO), anahtar-değer haritası (Map) veya benzersiz küme (Set) yeteneğine sahip değildir.

### Koleksiyonların Sunduğu Özgürlükler
Java Koleksiyonları (`Collections Framework`):
- **Dinamik Boyutlanır:** Eleman eklendikçe kendiliğinden büyür, silindikçe küçülür.
- **Hazır Metotlar Sunar:** `add()`, `remove()`, `contains()`, `isEmpty()`, `clear()`, `sort()` gibi yüzlerce hazır ve optimize edilmiş operasyonla gelir.
- **Standart Mimari:** Tüm veri yapıları ortak arayüzlerle (`List`, `Set`, `Queue`) birbiriyle uyumlu çalışır.

---

## 2. Sarmalayıcı Sınıflar (Wrapper Classes) ve Autoboxing

Java'da koleksiyonlar **YALNIZCA NESNELERİ** saklayabilir. İlkel veri tipleri (`int`, `double`, `char`, `boolean`) doğrudan koleksiyonlara konulamaz:

```java
// ❌ DERLEME HATASI! Koleksiyonlar ilkel tiplerle çalışamaz:
List<int> sayilar = new ArrayList<int>(); 
```

Bu sorunu çözmek için Java, 8 ilkel tipin her biri için bir **Sarmalayıcı Sınıf (Wrapper Class)** sunar:

| İlkel Tip (Primitive) | Sarmalayıcı Nesne Sınıfı (Wrapper Class) |
| :--- | :--- |
| `byte` | `java.lang.Byte` |
| `short` | `java.lang.Short` |
| `int` | **`java.lang.Integer`** |
| `long` | `java.lang.Long` |
| `float` | `java.lang.Float` |
| `double` | **`java.lang.Double`** |
| `char` | **`java.lang.Character`** |
| `boolean` | **`java.lang.Boolean`** |

Doğru tanım:
```java
List<Integer> sayilar = new ArrayList<Integer>(); // ✅ Mükemmel!
```

---

### Autoboxing ve Unboxing Mekanizması

Java 5 ile gelen bu özellik sayesinde geliştirici ilkel tipi sarmalayıcıya elle çevirmekle uğraşmaz. Java bunu arka planda otomatik yapar:

- **Autoboxing:** İlkel bir değerin otomatik olarak Wrapper nesnesine sarılması:
  ```java
  Integer sayiNesnesi = 42; // Arka planda: Integer.valueOf(42)
  ```
- **Unboxing:** Wrapper nesnesindeki değerin otomatik olarak ilkel tipe çözülmesi:
  ```java
  int ilkelSayi = sayiNesnesi; // Arka planda: sayiNesnesi.intValue()
  ```

---

## 3. Jenerikler (Generics) Nedir ve Neden Hayatidir?

### Eski Java (Ham Tipler ve `ClassCastException` Felaketi)

Java 5 öncesinde koleksiyonlar her şeyi `Object` olarak saklardı:

```java
// ❌ ESKİ JAVA YAKLAŞIMI (Java 1.4 ve öncesi - Ham Liste):
ArrayList liste = new ArrayList();
liste.add("Ahmet");
liste.add(123); // Farklı tipte eleman eklenebiliyordu!

// Listeden okurken her şeyi zorla cast etmek zorundaydık:
String isim = (String) liste.get(0);
String sayi = (String) liste.get(1); // 💣 RUNTIME CRASH: ClassCastException! (Integer String'e cast edilemez!)
```
Bu kod derleme anında hiçbir hata vermezdi; ancak program canlıya alınıp çalıştığı anda çökerdi!

---

### Tip Güvenliği (Type Safety) İlkesi

Java 5 ile birlikte **Jenerikler (Generics - `<T>`)** dile eklendi. Jenerikler sayesinde bir koleksiyonun **içinde yalnızca hangi türde nesnelerin bulunabileceğini** derleme anında şart koşarız:

```java
// ✅ JENERİK İLE GÜVENLİ LİSTE:
ArrayList<String> isimler = new ArrayList<String>();
isimler.add("Ahmet");
// isimler.add(123); // ❌ DERLEYİCİ ANINDA ENGEL KOR: int kabul edilmez!

String ad = isimler.get(0); // Cast gerekmez, doğrudan String gelir!
```

> 🌟 **Jeneriklerin Faydaları:**
> 1. **Tip Güvenliği (Type Safety):** Hatalar çalışma anında (`Runtime`) değil, kod yazarken derleme anında (`Compile-time`) yakalanır.
> 2. **Cast İhtiyacını Kaldırır:** Kod daha temiz ve okunaklı hale gelir.
> 3. **Yeniden Kullanılabilirlik:** Aynı sınıfı veya metodu onlarca farklı tip için tekrar tekrar yazmak gerekmez.

---

### Elmas Operatörü (Diamond Operator `<>`)

Java 7 ile birlikte sağ taraftaki jenerik tip tekrarı kaldırılmıştır. Boş elmas parantez `<>` yazmak yeterlidir:

```java
// Java 7 ve sonrası:
ArrayList<String> liste = new ArrayList<>(); // Derleyici sol taraftan tipi anlar
```

---

## 4. Kendi Jenerik Sınıf ve Metotlarımızı Yazmak

Jenerikler sadece koleksiyonlara özel değildir; kendi sınıflarımızda da tip bağımsız şablonlar oluşturabiliriz.

### Jenerik Sınıf Tanımlama (`Kutu<T>`)

Harf Standartları:
- `T`: Type (Genel Tip)
- `E`: Element (Koleksiyon elemanları için)
- `K`: Key (Anahtar)
- `V`: Value (Değer)

```java
public class Kutu<T> {
    private T icerik;

    public void koy(T icerik) {
        this.icerik = icerik;
    }

    public T al() {
        return icerik;
    }
}
```

Kullanımı:
```java
// Kitap saklayan kutu:
Kutu<Kitap> kitapKutusu = new Kutu<>();
kitapKutusu.koy(new Kitap("Nutuk"));
Kitap k = kitapKutusu.al();

// Sayı saklayan kutu:
Kutu<Integer> sayiKutusu = new Kutu<>();
sayiKutusu.koy(100);
int s = sayiKutusu.al();
```

---

### Jenerik Metot Tanımlama

Bir metot, ait olduğu sınıf jenerik olmasa bile kendi başına jenerik parametre alabilir:

```java
public class DiziAraclari {
    // Her türden diziyi ekrana yazdıran jenerik metot:
    public static <E> void diziYazdir(E[] dizi) {
        for (E eleman : dizi) {
            System.out.print(eleman + " ");
        }
        System.out.println();
    }
}
```

---

## 5. Koleksiyon Hiyerarşisinin Zirvesi: `Iterable` ve `Collection`

Java'da tüm standart koleksiyonların (Map hariç) en tepesinde iki temel arayüz oturur:

```
           [ java.lang.Iterable ]  ---> for-each döngüsünde gezilebilme yeteneği
                     ^
                     | (extends)
          [ java.util.Collection ] ---> Tüm koleksiyonların ortak sözleşmesi
           /         |          \
          /          |           \
     [ List ]     [ Set ]     [ Queue ]
```

### `Collection` Arayüzünün Ortak Metotları
Bir `List`, `Set` veya `Queue` kullandığınızda, aşağıdaki metotların hepsini koşulsuz şartsız kullanabilirsiniz:

| Metot | Görevi |
| :--- | :--- |
| `add(E e)` | Koleksiyona yeni eleman ekler (Başarılıysa `true` döner). |
| `remove(Object o)` | Belirtilen nesneyi koleksiyondan siler. |
| `contains(Object o)` | Koleksiyonda o nesne var mı kontrol eder (`true`/`false`). |
| `size()` | Koleksiyondaki toplam eleman sayısını verir. |
| `isEmpty()` | Koleksiyon boş mu sorgular (`size() == 0`). |
| `clear()` | Koleksiyondaki tüm elemanları temizler / sıfırlar. |
| `toArray()` | Koleksiyonu standart bir diziye (`Object[]`) dönüştürür. |

---

## 6. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Tip Güvenliği ve Jenerik Farkı

#### `GenericsTest.java`
```java
import java.util.ArrayList;

public class GenericsTest {
    public static void main(String[] args) {
        // 1. Tip Güvensiz (Ham) Liste Kullanımı:
        System.out.println("=== 1. Ham Liste Denemesi (Riskli) ===");
        ArrayList hamListe = new ArrayList();
        hamListe.add("Java Dersi");
        hamListe.add(2026); // Integer ekledik

        for (Object obj : hamListe) {
            System.out.println("Eleman: " + obj);
        }

        // 2. Tip Güvenli Jenerik Liste:
        System.out.println("\n=== 2. Jenerik Liste (Güvenli) ===");
        ArrayList<String> diller = new ArrayList<>();
        diller.add("Java");
        diller.add("Python");
        diller.add("C++");
        // diller.add(100); // ❌ Derleme hatası verir, kodu korur!

        for (String dil : diller) {
            System.out.println("Yazılım Dili: " + dil.toUpperCase());
        }

        // 3. Autoboxing ile Sayı Listesi:
        ArrayList<Double> notlar = new ArrayList<>();
        notlar.add(85.5); // 85.5 ilkel double değeri otomatik Double nesnesine sarılır
        notlar.add(92.0);
        
        double ilkNot = notlar.get(0); // Unboxing: Double -> double
        System.out.println("\nİlk Not (Unboxed): " + ilkNot);
    }
}
```

---

### Örnek 2: Özel Jenerik Depo Sınıfı

İki farklı tipte veriyi bir çift olarak saklayan jenerik `Ikili<K, V>` sınıfı:

#### `Ikili.java` & `DepoTest.java`
```java
// İki farklı tipi birleştiren jenerik sınıf
class Ikili<K, V> {
    private K anahtar;
    private V deger;

    public Ikili(K anahtar, V deger) {
        this.anahtar = anahtar;
        this.deger = deger;
    }

    public K getAnahtar() { return anahtar; }
    public V getDeger() { return deger; }

    public void yazdir() {
        System.out.println("[" + anahtar + " => " + deger + "]");
    }
}

public class DepoTest {
    public static void main(String[] args) {
        // TC No (String) -> İsim (String)
        Ikili<String, String> vatandas = new Ikili<>("12345678901", "Ahmet Yılmaz");
        vatandas.yazdir();

        // Ürün Kodu (Integer) -> Fiyat (Double)
        Ikili<Integer, Double> urunFiyat = new Ikili<>(101, 249.99);
        urunFiyat.yazdir();

        // Öğrenci No (Integer) -> Başarı Durumu (Boolean)
        Ikili<Integer, Boolean> ogrenciDurum = new Ikili<>(2023005, true);
        ogrenciDurum.yazdir();
    }
}
```

---

## 7. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. Jenerik Parantezine İlkel Tip Yazmaya Çalışmak
```java
ArrayList<int> sayilar = new ArrayList<>(); // ❌ HATA: unexpected type; found: int, required: reference
```
✅ **Çözüm:** Daima Wrapper karşılığını kullanınız: `ArrayList<Integer> sayilar = new ArrayList<>();`.

---

### 2. Ham Tip (`Raw Type`) Kullanarak Uyarıları Görmezden Gelmek
```java
List liste = new ArrayList(); // ⚠️ Derleyici uyarısı: uses unchecked or unsafe operations
```
Koleksiyon tanımlarken daima tipi belirtiniz: `List<String> liste = new ArrayList<>();`.

---

### 3. Jenerik Dizisi Üretmeye Çalışmak
Java'da doğrudan `new T[10]` şeklinde jenerik dizisi oluşturulamaz:
```java
public class Kutu<T> {
    // T[] dizi = new T[10]; // ❌ HATA: generic array creation
}
```
✅ **Çözüm:** Dizi yerine `ArrayList<T>` veya `(T[]) new Object[10]` cast yöntemi kullanılır.

---

## 8. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Autoboxing / Unboxing Analizi
Aşağıdaki kodda kaçıncı satırlarda **Autoboxing**, kaçıncı satırlarda **Unboxing** gerçekleşmektedir?

```java
Integer x = 10;          // Satır 1
int y = x + 5;           // Satır 2
Double d = 3.14;         // Satır 3
double sonuc = d * 2;    // Satır 4
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

- **Satır 1:** Autoboxing (`int 10` $\to$ `Integer` nesnesi)
- **Satır 2:** Unboxing (`x` nesnesi ilkel `int`'e açılır ve 5 ile toplanır)
- **Satır 3:** Autoboxing (`double 3.14` $\to$ `Double` nesnesi)
- **Satır 4:** Unboxing (`d` nesnesi ilkel `double`'a açılır ve 2 ile çarpılır)
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında derleme hatası veren 2 yeri tespit ediniz:

```java
public class Test {
    public static void main(String[] args) {
        ArrayList<boolean> sonuclar = new ArrayList<>();
        sonuclar.add(true);
        sonuclar.add("Evet");
    }
}
```

<details>
<summary>🔍 <b>Hataları ve Çözümü Görmek İçin Tıklayın</b></summary>

1. **Hata 1:** `ArrayList<boolean>` yazılmıştır. Jenerikler ilkel tip alamaz. `ArrayList<Boolean>` olmalıdır.
2. **Hata 2:** `sonuclar.add("Evet")` ifadesi String eklemeye çalışmaktadır. Boolean listesine String eklenemez.
</details>

---

### Alıştırma 3: Jenerik Takas (Swap) Metodu
İki elemanlı bir jenerik dizi alan ve bu iki elemanın yerini birbiriyle değiştiren `public static <T> void takas(T[] dizi, int i, int j)` metodunu yazınız.

---

## 9. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Jenerik Yığın Mantığı (`BasitYigin<T>`)

**Gereksinimler:**
1. `BasitYigin<T>` adında bir jenerik sınıf tasarlayınız.
2. İçeride verileri saklamak için bir `ArrayList<T>` kullanınız.
3. Metotlar:
   - `push(T eleman)`: Yığının tepesine eleman eklesin.
   - `pop()`: Yığının en üstündeki elemanı listeden çıkarıp geri döndürsün. Liste boşsa uyarı verip `null` dönsün.
   - `peek()`: En üstteki elemanı silmeden sadece okusun.
   - `bosMu()`: Yığın boşsa `true` dönsün.
4. Hem `String` (metin geçmişi) hem de `Integer` (sayı yığını) ile test ediniz.

---

### 🟡 Ödev 2 (Orta Seviye): Filtrelenebilir Jenerik Depo (`FiltreliKoleksiyon<T>`)

**Gereksinimler:**
1. Bir `Filtreleyici<T>` arayüzü tanımlayınız: `boolean test(T eleman);`.
2. Bir `JenerikDepo<T>` sınıfı yazınız:
   - Elemanları tutan `ArrayList<T>` barındırsın.
   - `ekle(T eleman)` metodu.
   - `filtrele(Filtreleyici<T> filtre)`: Şartı sağlayan elemanları filtreleyip yeni bir `ArrayList<T>` olarak döndürsün!
3. Bir `Ogrenci(ad, not)` sınıfı oluşturup, notu 60'tan büyük olan öğrencileri bu sistemle filtreleyen test sınıfını kodlayınız.

---

### 🔴 Ödev 3 (İleri Seviye): Jenerik İki Yönlü Sözlük Sistemi (`CiftYonluSozluk<K, V>`)

**Gereksinimler:**
1. İki yönlü arama yapabilen bir `CiftYonluSozluk<K, V>` sınıfı oluşturunuz.
2. Örneğin hem İngilizce kelimeden Türkçe karşılığına, hem de Türkçe kelimeden İngilizce karşılığına erişilebilmelidir:
   - `ekle(K anahtar, V deger)`
   - `degerGetir(K anahtar)`: Anahtara karşılık gelen değeri dönsün.
   - `anahtarGetir(V deger)`: Verilen değere sahip olan orijinal anahtarı bulup dönsün!
3. İngilizce-Türkçe kelime eşleştirmeleri ve Personel ID-Personel Nesnesi eşleştirmeleri ile sistemi kapsamlı biçimde test ediniz.

