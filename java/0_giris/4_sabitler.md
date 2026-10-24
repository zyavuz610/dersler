# 4. Java'da Sabitler ve Literaller (Literals) Rehberi

Bu rehber, Java programlama dilinde **literal (değer sabiti)** kavramını, kaynak kod içerisinde doğrudan yazılan sabit değerlerin veri türlerini, kaçış karakterlerini, farklı sayı tabanlarının kullanımını, `final` anahtar kelimesiyle değişmez sabit tanımlamayı ve literallerle çalışırken düşülen yaygın tuzakları ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - ☕ [1. Java Dünyasına Giriş ve OOP (1_java_giris.md)](./1_java_giris.md)
> - 🐧 [2. Temel Linux Komutları (2_linux.md)](./2_linux.md)
> - 📦 [3. Değişkenler ve Veri Tipleri (3_degisken.md)](./3_degisken.md)

---

## 📌 İçindekiler
1. [Literal (Sabit Değer) Nedir?](#1-literal-sabit-değer-nedir)
2. [Java'da Literal Türleri](#2-javada-literal-türleri)
   - [Tam Sayı (Integer) Literalleri ve Sayı Tabanları](#tam-sayı-integer-literalleri-ve-sayı-tabanları)
   - [Ondalıklı Sayı (Floating-Point) Literalleri](#ondalıklı-sayı-floating-point-literalleri)
   - [Karakter (Character) Literalleri ve Kaçış Dizileri](#karakter-character-literalleri-ve-kaçış-dizileri)
   - [Mantıksal (Boolean) Literaller](#mantıksal-boolean-literaller)
   - [Metin (String) Literalleri](#metin-string-literalleri)
   - [Null Literali](#null-literali)
3. [İsimlendirilmiş Sabitler (`final` Anahtar Kelimesi)](#3-isimlendirilmiş-sabitler-final-anahtar-kelimesi)
4. [Örnek Java Programı: Literaller ve Sabitler Gösterimi](#4-örnek-java-programı-literaller-ve-sabitler-gösterimi)
5. [En Çok Yapılan Hatalar ve Tuzaklar](#5-en-çok-yapılan-hatalar-ve-tuzaklar)
6. [Alıştırmalar ve Örnek Ödev Konuları](#6-alıştırmalar-ve-örnek-ödev-konuları)

---

## 1. Literal (Sabit Değer) Nedir?

Programlamada **Literal**, herhangi bir hesaplama veya işlem gerektirmeden kaynak kodun içerisine **doğrudan yazılan sabit değerlere** verilen addır.

```java
int yas = 20;            // Burada '20' bir tamsayı literalidir.
double pi = 3.14;        // Burada '3.14' bir ondalıklı sayı literalidir.
char harf = 'A';         // Burada ''A'' bir karakter literalidir.
String mesaj = "Selam";  // Burada '"Selam"' bir String literalidir.
boolean aktif = true;    // Burada 'true' bir boolean literalidir.
```

- Değişken (`yas`), bellekteki kutunun adıdır.
- Literal (`20`), o kutunun içine koyduğumuz **sabit değerin bizzat kendisidir**.

---

## 2. Java'da Literal Türleri

### Tam Sayı (Integer) Literalleri ve Sayı Tabanları

Java'da tam sayı literalleri varsayılan olarak **`int`** türündedir. Kod içerisinde tam sayıları yalnızca günlük hayattaki onluk sistemde değil, bilgisayar bilimlerinde yaygın olan 4 farklı tabanda yazabiliriz:

| Sayı Tabanı | Ön Ek (Prefix) | Örnek Yazım | Onluk (Decimal) Değeri |
| :--- | :--- | :--- | :--- |
| **Onluk (Decimal)** | Ön ek yok | `42` | `42` |
| **İkilik (Binary)** | `0b` veya `0B` | `0b101010` | `42` |
| **Sekizlik (Octal)** | `0` (sıfır) | `052` | `42` *(5×8¹ + 2×8⁰ = 42)* |
| **Onaltılık (Hexadecimal)** | `0x` veya `0X` | `0x2A` | `42` *(2×16¹ + 10×16⁰ = 42)* |

#### `long` Türündeki Literaller:
`int` sınırlarını (~2 milyar) aşan sayılar için sayının sonuna mutlaka büyük **`L`** harfi konulmalıdır:
```java
long buyukSayi = 9000000000L; // 'L' konmazsa derleyici int sınır hatası verir
```

#### Okunabilirlik İçin Alt Tire (`_`) Kullanımı:
Büyük sayıların basamaklarını insan gözünün kolay okuması için Java 7 ile birlikte rakamların arasına alt tire koyma özelliği getirilmiştir:
```java
int birMilyon = 1_000_000;      // 1000000 ile tamamen aynıdır
long krediKarti = 5544_2211_9988_3322L;
```

---

### Ondalıklı Sayı (Floating-Point) Literalleri

Java'da nokta ile yazılan her ondalıklı sayı varsayılan olarak **`double`** türündedir:

```java
double oran = 12.5;     // double türünde varsayılan literal
float faiz = 12.5f;     // 'f' veya 'F' soneki ile float literali
double d = 12.5d;       // 'd' veya 'D' isteğe bağlıdır, yazılmasa da double'dır
```

#### Bilimsel Gösterim (E-Notasyonu):
Çok büyük veya çok küçük sayılar için 10'un kuvvetlerini temsil eden `e` veya `E` harfi kullanılabilir:
```java
double avogadro = 6.022e23; // 6.022 x 10^23
double kucukSayi = 1.5e-3;   // 1.5 x 10^-3 = 0.0015
```

---

### Karakter (Character) Literalleri ve Kaçış Dizileri

Karakter literalleri **tek tırnak (`' '`)** içerisinde yazılan tek bir Unicode karakteridir:
```java
char harf = 'A';
char rakamKarakteri = '9';
char sembol = '#';
```

#### Kaçış Dizileri (Escape Sequences):
Bazı özel karakterleri (yeni satır, sekme, tırnak işareti vb.) doğrudan yazamayız. Bunları ifade etmek için ters bölü (`\`) ile başlayan kaçış karakterleri kullanılır:

| Kaçış Dizisi | Anlamı | Açıklama |
| :---: | :--- | :--- |
| **`\n`** | Newline | Yeni satıra geçer (Enter etkisi). |
| **`\t`** | Tab | Bir sekme boşluğu (genellikle 4 veya 8 boşluk) bırakır. |
| **`\'`** | Single Quote | Tek tırnak karakteri basar. |
| **`\"`** | Double Quote | Metin içinde çift tırnak karakteri basar. |
| **`\\`** | Backslash | Ters bölü karakterinin kendisini basar. |
| **`\b`** | Backspace | İmleci bir karakter geri alır. |
| **`\uXXXX`** | Unicode | 4 haneli onaltılık Unicode karakter kodu basar (Örn: `\u0041` = 'A'). |

---

### Mantıksal (Boolean) Literaller

Mantıksal literaller yalnızca **iki** kelimeden ibarettir:
```java
boolean durum1 = true;   // Doğru
boolean durum2 = false;  // Yanlış
```
> ⚠️ **Not:** C/C++ dillerinin aksine Java'da `1` veya `0` sayısı boolean olarak kullanılamaz! Sadece `true` veya `false` literalleri geçerlidir.

---

### Metin (String) Literalleri

Birden fazla karakterden oluşan metin dizileridir. **Çift tırnak (`" "`)** arasına yazılır:
```java
String ders = "Java Programlama";
String bosMetin = "";
```

#### String Sabit Havuzu (String Constant Pool):
Java'da çift tırnak ile oluşturulan her String literali, belleğin özel bir alanı olan **String Pool** içine kaydedilir. Kodun başka bir yerinde aynı String literali tekrar yazılırsa Java yeni bir nesne üretmek yerine bellekteki mevcut sabit havuzundaki aynı adresi işaret eder.

---

### Null Literali

Referans türündeki değişkenlerin şu an bellekte hiçbir nesneyi göstermediğini ifade eden özel sabittir:
```java
String metin = null; // Henüz bellekte bir metin nesnesi yok
```
> ⚠️ **Not:** `null` sabiti ilkel (*primitive*) türlere (`int`, `double`, `boolean`) atanamaz!

---

## 3. İsimlendirilmiş Sabitler (`final` Anahtar Kelimesi)

Kod yazarken bazı değerlerin programın çalışması boyunca **asla değiştirilmemesini** isteriz (örneğin Pi sayısı, KDV oranı, maksimum hız vb.).

Java'da bir değişkenin değerini sabit kılmak için **`final`** anahtar kelimesi kullanılır:

```java
final double PI = 3.14159265359;
final int MAKSIMUM_HIZ = 120;
final double KDV_ORANI = 0.20;
```

### Sabit Tanımlama Kuralları ve Standartları:
1. `final` ile tanımlanan değişkene ilk değer verildikten sonra tekrar değer atanamaz:
   ```java
   final int YIL = 2026;
   YIL = 2027; // ❌ DERLEME HATASI: cannot assign a value to final variable YIL
   ```
2. **Yazım Standardı (Convention):** Sabit değişken isimleri tamamen **BÜYÜK HARFLERLE** yazılır. Birden fazla kelime varsa aralarına alt tire (`_`) konur (Örn: `VARSAYILAN_PORT`, `MAX_DENEME_SAYISI`).

---

## 4. Örnek Java Programı: Literaller ve Sabitler Gösterimi

Aşağıdaki örnek kod, Java'daki tüm literal türlerini, sayı tabanlarını ve `final` sabit kullanımını konsola yazdırarak gösterir:

```java
public class SabitlerDemo {
    public static void main(String[] args) {
        // 1. İsimlendirilmiş Sabitler (final)
        final double KDV_ORANI = 0.20;
        final String KURUM_ADI = "Geleceğin Yazılımcıları Akademisi";

        // 2. Tam Sayı Literalleri (Farklı Sayı Tabanları)
        int onlukSayi = 42;           // Decimal (10'luk)
        int ikilikSayi = 0b101010;     // Binary (2'lik taban: 42)
        int sekizlikSayi = 052;        // Octal (8'lik taban: 42)
        int onaltilikSayi = 0x2A;      // Hexadecimal (16'lık taban: 42)
        long nufus = 8_500_000_000L;  // Alt tireli long literali

        // 3. Ondalıklı Sayı Literalleri
        float urunFiyati = 99.90f;     // 'f' sonekli float
        double pi = 3.14159;          // Varsayılan double
        double kutle = 5.972e24;       // Dünya'nın kütlesi (kg) - Bilimsel gösterim

        // 4. Karakter ve Kaçış Karakterleri
        char derece = '\u00B0';        // Unicode: Derece simgesi (°)
        char basHarf = 'A';

        // 5. Mantıksal ve Metin Literalleri
        boolean satistaMi = true;
        String etiket = "Kitap: \"Java Başlangıç Rehberi\""; // Çift tırnak kaçışı

        // 6. Çıktıların Ekrana Basılması
        System.out.println("=== " + KURUM_ADI + " ===");
        System.out.println("Onluk Sistem       : " + onlukSayi);
        System.out.println("İkilik Sistem (0b) : " + ikilikSayi);
        System.out.println("Sekizlik Sistem (0): " + sekizlikSayi);
        System.out.println("Onaltılık (0x)     : " + onaltilikSayi);
        System.out.println("Dünya Nüfusu       : " + nufus);
        System.out.println("----------------------------------------");
        System.out.println(etiket);
        System.out.println("Fiyat (Ham)        : " + urunFiyati + " TL");
        System.out.println("KDV Oranı          : %" + (KDV_ORANI * 100));
        System.out.println("KDV Dahil Fiyat    : " + (urunFiyati * (1 + KDV_ORANI)) + " TL");
        System.out.println("Hava Sıcaklığı     : 24" + derece + "C");
        System.out.println("Satış Durumu       : " + satistaMi);
        System.out.println("Kaçış Karakteri    :\n\tSatır 1\n\tSatır 2");
    }
}
```

### Program Çıktısı:
```text
=== Geleceğin Yazılımcıları Akademisi ===
Onluk Sistem       : 42
İkilik Sistem (0b) : 42
Sekizlik Sistem (0): 42
Onaltılık (0x)     : 42
Dünya Nüfusu       : 8500000000
----------------------------------------
Kitap: "Java Başlangıç Rehberi"
Fiyat (Ham)        : 99.9 TL
KDV Oranı          : %20.0
KDV Dahil Fiyat    : 119.88 TL
Hava Sıcaklığı     : 24°C
Satış Durumu       : true
Kaçış Karakteri    :
	Satır 1
	Satır 2
```

---

## 5. En Çok Yapılan Hatalar ve Tuzaklar

### 1. Başında Sıfır (`0`) Olan Sayıların Sekizlik (Octal) Algılanması Tuzağı
Yeni başlayanlar bazen sayıları hizalamak veya ay/gün yazmak için başına `0` koyarlar:
```java
// ❌ HATA & TUZAK:
int ay = 09; // Derleme Hatası: integer number too large
```
* **Neden Hata Verir?** Başındaki `0` yüzünden Java bunu **sekizlik (octal)** tabanda bir sayı zanneder. Sekizlik tabanda sadece `0-7` arası rakamlar bulunabilir; `8` ve `9` rakamı olmadığı için kod derlenmez!
* Ayrıca `int sayi = 012;` yazdığınızda Java bunu sekizlik okur ve ekrana `10` basar!

---

### 2. `float` Değerlerde `f` Harfini Unutmak
Java'da ondalıklı bir sayı yazdığınız anda türü otomatik olarak `double` olur. `double` (64-bit) veriyi `float` (32-bit) kutusuna sığdıramazsınız:
```java
// ❌ HATA:
float oran = 4.5; // Derleme Hatası: possible lossy conversion from double to float

// ✅ DOĞRU:
float oran = 4.5f;
```

---

### 3. `long` Literallerinde `L` Harfini Unutmak
Java'da tam sayılar varsayılan olarak `int` (en fazla ~2.14 milyar) kabul edilir. `long` değişkeni tanımlasanız bile yazdığınız literal `L` harfi içermiyorsa derleyici onu `int` olarak değerlendirir:
```java
// ❌ HATA:
long nufus = 5000000000; // Derleme Hatası: integer number too large

// ✅ DOĞRU:
long nufus = 5000000000L;
```

---

### 4. `final` Sabitin Değerini Değiştirmeye Çalışmak
`final` anahtar kelimesi ile kilitlenen bir değişken sadece okunabilir, üzerine tekrar yazılamaz:
```java
final double PI = 3.14;
PI = 3.1415; // ❌ DERLEME HATASI: cannot assign a value to final variable PI
```

---

## 6. Alıştırmalar ve Örnek Ödev Konuları

### ✍️ Pratik Sınıf İçi Alıştırmalar:
1. **Sayı Tabanı Eşleştirme:** `int a = 0b1101;`, `int b = 017;` ve `int c = 0x2F;` değişkenlerini tanımlayınız. Her birini `System.out.println` ile ekrana yazdırarak onluk sistemdeki karşılıklarını bulunuz.
2. **Kaçış Karakteri Deneyi:** Tek bir `System.out.println` komutu içinde `\n`, `\t` ve `\"` kullanarak iki satırlı ve tırnak içinde bir atasözü yazdırınız.
3. **Octal Tuzağı Deneyimi:** Kodunuza `int ay = 08;` yazınız ve derleyicinin verdiği hatayı görerek nedenini açıklayınız.

### 📋 Ödev Programları ve Proje Konuları:

#### Ödev 1: Kafe / Restoran Kasa Fişi Simülasyonu (`KasaFisi.java`)
- **Gereksinimler:**
  - `final double KDV_ORANI = 0.20;` ve `final String RESTORAN_ADI = "Java Kafe & Bistro";` sabitlerini tanımlayınız.
  - 3 farklı sipariş kalemini (Örn: Çorba, Ana Yemek, İçecek) sabit fiyatlarıyla kod içinde tanımlayınız.
  - Ara toplamı, uygulanan KDV tutarını ve toplam ödenecek hesabı hesaplayınız.
  - Çıktıyı `\t` (sekme) ve `\n` kaçış karakterleriyle hizalayarak gerçek bir rulo fiş görünümünde konsola basınız:
    ```text
    ==================================
           JAVA KAFE & BISTRO         
    ==================================
    Ürün            Adet    Fiyat     
    ----------------------------------
    Mercimek Çorba   1      60.00 TL
    Tavuk Sote       1     140.00 TL
    Ayran            1      25.00 TL
    ----------------------------------
    Ara Toplam            : 225.00 TL
    KDV (%20)             :  45.00 TL
    GENEL TOPLAM          : 270.00 TL
    ==================================
    ```

#### Ödev 2: Bilimsel Değerler ve Sabitler Tablosu (`FizikSabitleri.java`)
- **Gereksinimler:**
  - Evrensel fiziksel sabitleri `final` anahtar kelimesiyle ve bilimsel E-notasyonuyla tanımlayınız:
    - Işık Hızı: $c = 3.0 \times 10^8$ m/s (`3.0e8`)
    - Planck Sabiti: $h = 6.626 \times 10^{-34}$ (`6.626e-34`)
    - Avogadro Sayısı: $N_A = 6.022 \times 10^{23}$ (`6.022e23`)
  - Değerleri Unicode simgeleriyle birlikte (örn: derece işareti veya özel birimler) ekrana basınız.
