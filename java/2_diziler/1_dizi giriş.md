# 1. Java'da Tek Boyutlu (1D) Diziler ve Primitif Tipler Kılavuzu

Bu rehber; Java programlama dilinde aynı türden çok sayıda veriyi tek bir yapı altında saklamamızı sağlayan **Tek Boyutlu (1D) Dizileri (Arrays)**, ilkel (*primitif*) veri tipleriyle (`int`, `double`, `boolean`, `char`) dizi kullanımını, bellek mimarisini (**Stack vs Heap**), varsayılan değerleri, dizilerde dolaşma yöntemlerini, temel arama/istatistik algoritmalarını, sık düşülen tuzakları ve öğrencilerin çözebileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 2_diziler**  
> Bu modül, hafızada tek tek değişkenler tanımlamak yerine verileri organize bir blok halinde yöneterek algoritma ve veri yapılarına sağlam bir temel atmamızı sağlar.

---

## 📌 İçindekiler
1. [Dizi (Array) Nedir ve Neden İhtiyaç Duyulur?](#1-dizi-array-nedir-ve-neden-ihtiyaç-duyulur)
2. [Dizi Tanımlama, Bellek Tahsisi ve İlklendirme](#2-dizi-tanımlama-bellek-tahsisi-ve-ilklendirme)
3. [Primitif Veri Tipleri ve Otomatik Varsayılan Değerler](#3-primitif-veri-tipleri-ve-otomatik-varsayılan-değerler)
4. [Dizilerin Bellek Mimarisi (Stack vs Heap)](#4-dizilerin-bellek-mimarisi-stack-vs-heap)
5. [Dizi Elemanlarına Erişim, Güncelleme ve `length` Özelliği](#5-dizi-elemanlarına-erişim-güncelleme-ve-length-özelliği)
6. [1D Dizilerde Dolaşma (Traversal) Yöntemleri](#6-1d-dizilerde-dolaşma-traversal-yöntemleri)
7. [Primitif Dizilerde Temel Algoritmik İşlemler](#7-primitif-dizilerde-temel-algoritmik-işlemler)
8. [Dizi Kopyalama: Referans Bağlama vs Gerçek Klonlama](#8-dizi-kopyalama-referans-bağlama-vs-gerçek-klonlama)
9. [En Sık Yapılan Hatalar ve Tuzaklar](#9-en-sık-yapılan-hatalar-ve-tuzaklar)
10. [Kapsamlı Örnek Konsol Uygulaması: `SinifNotAnalizLaboratuvari`](#10-kapsamlı-örnek-konsol-uygulaması-sinifnotanalizlaboratuvari)
11. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#11-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
12. [Ödev Projeleri](#12-ödev-projeleri)

---

## 1. Dizi (Array) Nedir ve Neden İhtiyaç Duyulur?

Bir sınıftaki 40 öğrencinin sınav notunu bilgisayarda saklamak istediğinizi düşünün. Şimdiye kadar öğrendiğimiz değişken mantığıyla bunu yapmaya çalışırsak:
```java
int not1 = 75;
int not2 = 82;
int not3 = 64;
// ...
int not40 = 90;
```
Bu yaklaşım; 40 farklı değişken ismi bulmayı, ortalama hesaplarken 40 değişkeni tek tek `+` ile toplamayı gerektirir. 10.000 veri olduğunda ise bu yöntem tamamen imkansızdır.

İşte aynı türden birden fazla veriyi tek bir isim altında, bellekte yan yana (**ardışık / contiguous**) bloklar halinde saklayan veri yapısına **Dizi (Array)** denir.

### 🌟 Dizilerin 3 Temel Özelliği:
1. **Homojendir (Aynı Tür):** Bir dizi tanımlanırken türü ne seçildiyse (`int`, `double`, `boolean`), tüm elemanları o türde olmak zorundadır. Bir `int` dizisinin içine metin (`String`) koyamazsınız.
2. **Sabit Boyutludur (Fixed Size):** Dizi oluşturulurken boyutu belirlenir ve bu boyut program çalışırken **asla büyütülemez veya küçültülemez**.
3. **0 Tabanlı İndekslenir (0-Indexed):** Elemanlara sıra numarasıyla erişilir. İlk elemanın indeksi daima **0**'dır.

---

## 2. Dizi Tanımlama, Bellek Tahsisi ve İlklendirme

Java'da bir diziyi kullanabilmek için 3 temel aşamadan geçiririz:
1. **Bildirim (Declaration):** Dizinin türünü ve adını belirleme.
2. **Bellek Tahsisi (Instantiation / Allocation):** `new` anahtar kelimesiyle Heap bellekte kaç elemanlık yer açılacağını söyleme.
3. **İlklendirme (Initialization):** Elemanlara ilk değerlerini atama.

---

### Yöntem 1: Boyutu Belirleyip Boş Dizi Açma (Daha Sonra Doldurma)

```java
// 1. Bildirim: 'sayilar' adında bir int dizisi olacağını duyuruyoruz
int[] sayilar;

// 2. Bellek Tahsisi: Bellekte 5 adet int saklayacak yer açıyoruz
sayilar = new int[5];

// Kısa ve Standart Yazım (Tek Satırda):
int[] puanlar = new int[5];

// 3. Değer Atama:
puanlar[0] = 85;
puanlar[1] = 92;
puanlar[2] = 78;
puanlar[3] = 64;
puanlar[4] = 99;
```

> 💡 **Sözdizimi İpucu:** Java'da `int[] sayilar;` ile `int sayilar[];` yazımı aynı anlama gelir. Ancak C/C++ alışkanlığı olan ikinci yazım yerine, köşeli parantezin tipin yanında olduğu **`int[] sayilar;`** biçimi Java'da endüstri standardıdır.

---

### Yöntem 2: Eleman Değerleriyle Birlikte Doğrudan Tanımlama (Dizi Literali)

Eğer dizinin içine koyacağımız değerleri kod yazarken zaten biliyorsak `new` ve boyut yazmadan süslü parantez `{ }` ile doğrudan ilklendirebiliriz. Java eleman sayısını sayarak boyutu otomatik belirler:

```java
// 4 elemanlı bir double dizisi (Boyut otomatikman 4 olur):
double[] sicakliklar = { 21.5, 23.0, 19.8, 25.4 };

// 3 elemanlı bir char dizisi:
char[] sesliHarfler = { 'A', 'E', 'I', 'O', 'U' };

// Anonim Dizi Tanımı:
int[] asalSayilar = new int[]{ 2, 3, 5, 7, 11 };
```

---

## 3. Primitif Veri Tipleri ve Otomatik Varsayılan Değerler

Java'da `new` anahtar kelimesiyle bir dizi oluşturulduğunda, siz henüz hiçbir elemana değer atamamış olsanız bile Java bellek güvenliği için tüm elemanlara **otomatik varsayılan (*default*) değerler** atar:

| Primitif Veri Türü | Bellekte Kapladığı Alan | Otomatik Varsayılan Değer | Örnek Tanımlama |
| :--- | :---: | :---: | :--- |
| **`byte`** | 1 byte | `0` | `byte[] b = new byte[3]; // [0, 0, 0]` |
| **`short`** | 2 byte | `0` | `short[] s = new short[3]; // [0, 0, 0]` |
| **`int`** | 4 byte | `0` | `int[] sayilar = new int[3]; // [0, 0, 0]` |
| **`long`** | 8 byte | `0L` | `long[] l = new long[3]; // [0, 0, 0]` |
| **`float`** | 4 byte | `0.0f` | `float[] f = new float[3]; // [0.0, 0.0, 0.0]` |
| **`double`** | 8 byte | `0.0` | `double[] d = new double[3]; // [0.0, 0.0, 0.0]` |
| **`char`** | 2 byte | `'\u0000'` *(Boş karakter)* | `char[] c = new char[3]; // [\0, \0, \0]` |
| **`boolean`** | 1 bit (JVM bağlı) | **`false`** | `boolean[] durumlar = new boolean[3]; // [false, false, false]` |

```java
public class VarsayilanDegerlerDemo {
    public static void main(String[] args) {
        int[] sayilar = new int[2];
        boolean[] bayraklar = new boolean[2];
        double[] oranlar = new double[2];

        System.out.println("int varsayılan     : " + sayilar[0]);   // 0
        System.out.println("boolean varsayılan : " + bayraklar[0]); // false
        System.out.println("double varsayılan  : " + oranlar[0]);   // 0.0
    }
}
```

---

## 4. Dizilerin Bellek Mimarisi (Stack vs Heap)

Java'da diziler ilkel bir değişken gibi doğrudan Stack'te yaşamaz. Diziler birer **Nesnedir (Object)**:

1. **Stack Bellek:** Sadece dizinin adını tutan referans değişkeni yer alır (`sayilar`). Bu değişken, gerçek dizinin Heap'teki başlangıç adresini işaret eder.
2. **Heap Bellek:** `new int[4]` ifadesi çalıştırıldığında Heap'te 4 adet 4-byte'lık (toplam 16 byte) kesintisiz, yan yana bir bellek bloğu ayrılır.

```text
[ STACK BELLEK ]                                [ HEAP BELLEK ]
+---------------+                      +-------------------------------+
| sayilar       | -------------------> | İndeks: [0] | [1] | [2] | [3] |
| (0x7FFE adres)|                      | Değer :  10 |  20 |  30 |  40 |
+---------------+                      +-------------------------------+
                                        (Ardışık 16 Byte'lık Blok)
```

### ❓ İndeks Neden 0'dan Başlar? (Donanımsal Formül)
Bilgisayar bir elemanın adresini şu basit ofset formülüyle tek işlemde bulur:
$$\text{Elemanın Adresi} = \text{Başlangıç Adresi} + (\text{İndeks} \times \text{Eleman Boyutu})$$
- 0. Eleman için: $\text{Başlangıç} + (0 \times 4) = \text{Başlangıç Adresi}$ (Sıfır kayma / ofset).  
Bu yüzden dizilerde ilk elemana erişim hızı ile 1 milyonuncu elemana erişim hızı aynıdır ($O(1)$ süper hızlı doğrudan erişim).

---

## 5. Dizi Elemanlarına Erişim, Güncelleme ve `length` Özelliği

- **Okuma:** `int ilk = puanlar[0];`
- **Yazma / Güncelleme:** `puanlar[2] = 95;`
- **Boyut Sorgulama:** `dizi.length` özelliği dizinin toplam kaç eleman barındırdığını verir.

> ⚠️ **Hayati Fark:** `String` sınıfında uzunluk bulurken parantez kullanılır (`metin.length()`), çünkü o bir metottur. Dizilerde ise parantez **KULLANILMAZ** (`dizi.length`), çünkü o doğrudan dizinin `final` bir özelliğidir (*property*).

```java
public class DiziErisim {
    public static void main(String[] args) {
        int[] asal = {2, 3, 5, 7, 11};

        System.out.println("Dizinin Eleman Sayısı : " + asal.length); // 5
        System.out.println("İlk Eleman (İndeks 0) : " + asal[0]);      // 2
        System.out.println("Son Eleman            : " + asal[asal.length - 1]); // 11

        // Güncelleme:
        asal[0] = 99;
        System.out.println("Güncellenen İlk Eleman: " + asal[0]);      // 99
    }
}
```

---

## 6. 1D Dizilerde Dolaşma (Traversal) Yöntemleri

### Yöntem 1: Klasik `for` Döngüsü (İndekse İhtiyaç Varsa)
Elemanların indeks numarasını yazdırmak, belirli indeksleri değiştirmek veya diziyi tersten dolaşmak için kullanılır:

```java
int[] notlar = {80, 95, 70, 85};

// Baştan sona:
for (int i = 0; i < notlar.length; i++) {
    System.out.println(i + ". indisteki not: " + notlar[i]);
}

// Sondan başa (Tersten Gezinme):
for (int i = notlar.length - 1; i >= 0; i--) {
    System.out.print(notlar[i] + " ");
}
```

---

### Yöntem 2: Modern `for-each` Döngüsü (Sadece Değerleri Okumak İçin)
İndeks sayacıyla uğraşmadan, baştan sona tüm elemanları temiz bir şekilde okumak için kullanılır:

```java
double[] fiyatlar = {19.99, 49.50, 99.00};

for (double fiyat : fiyatlar) {
    System.out.println("Ürün Fiyatı: " + fiyat + " TL");
}
```

> 📌 **Kural:** `for-each` döngüsü dizinin elemanlarını salt okunur (*read-only*) gezer. Döngü değişkenini değiştirmek (`fiyat = 0;`) dizideki gerçek değeri **değiştirmez**!

---

## 7. Primitif Dizilerde Temel Algoritmik İşlemler

### 💻 1. Toplam ve Aritmetik Ortalama Bulma

```java
int[] sayilar = {10, 20, 30, 40, 50};
int toplam = 0;

for (int s : sayilar) {
    toplam += s;
}

double ortalama = (double) toplam / sayilar.length;
System.out.println("Toplam: " + toplam + ", Ortalama: " + ortalama);
```

---

### 💻 2. En Büyük (Max) ve En Küçük (Min) Elemanı Bulma

```java
int[] veriler = {45, 12, 89, 5, 77, 23};

int enKucuk = veriler[0]; // İlk elemanı şampiyon ilan et
int enBuyuk = veriler[0];
int enBuyukIndeks = 0;

for (int i = 1; i < veriler.length; i++) {
    if (veriler[i] > enBuyuk) {
        enBuyuk = veriler[i];
        enBuyukIndeks = i;
    }
    if (veriler[i] < enKucuk) {
        enKucuk = veriler[i];
    }
}

System.out.println("En Büyük Değer: " + enBuyuk + " (İndeks: " + enBuyukIndeks + ")");
System.out.println("En Küçük Değer: " + enKucuk);
```

---

### 💻 3. Doğrusal Arama (Linear Search)

```java
int[] sayilar = {15, 28, 42, 61, 99};
int aranan = 42;
int bulunanIndeks = -1;

for (int i = 0; i < sayilar.length; i++) {
    if (sayilar[i] == aranan) {
        bulunanIndeks = i;
        break; // Bulundu, aramayı bitir
    }
}

if (bulunanIndeks != -1) {
    System.out.println(aranan + " sayısı " + bulunanIndeks + ". indekste bulundu!");
} else {
    System.out.println(aranan + " sayısı dizide mevcut değil.");
}
```

---

## 8. Dizi Kopyalama: Referans Bağlama vs Gerçek Klonlama

Java'ya yeni başlayanların en sık düştüğü yanılgılardan biri iki diziyi eşittir (`=`) ile birbirine atamaktır:

```java
int[] a = {1, 2, 3};
int[] b = a; // ❌ DİKKAT: Yeni bir dizi OLUŞTURULMADI!

b[0] = 999;
System.out.println(a[0]); // Ekrana 999 basar! Çünkü ikisi de Heap'teki AYNI diziye bakar!
```

### ✅ Gerçek (Bağımsız) Kopya Nasıl Yapılır?

#### 1. Yöntem: Manuel `for` Döngüsü ile
```java
int[] a = {1, 2, 3};
int[] kopya = new int[a.length];
for (int i = 0; i < a.length; i++) {
    kopya[i] = a[i];
}
```

#### 2. Yöntem: `clone()` Metodu ile (Tek Satırda)
```java
int[] kopya = a.clone(); // Tamamen bağımsız yeni bir Heap bloğu açar
```

---

## 9. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: `ArrayIndexOutOfBoundsException` (Dizi Sınırını Aşma)
Java'da var olmayan bir indekse erişmeye çalışmak anında istisna fırlatır ve program çöker:

```java
int[] dizi = new int[5]; // Geçerli indeksler: 0, 1, 2, 3, 4

// ❌ HATA: 5. indeks mevcut değildir!
// System.out.println(dizi[5]); 

// ❌ HATA (Döngü sınırında <= kullanmak):
for (int i = 0; i <= dizi.length; i++) { ... } // i=5 olduğunda çöker!
```

---

### 2. Tuzak: Diziyi Doğrudan `println` ile Yazdırmaya Çalışmak
Bir diziyi doğrudan `System.out.println(dizi)` ile yazdırırsanız ekrana elemanlar değil, dizinin tipini ve bellek karma kodunu gösteren anlamsız bir metin basılır:

```java
int[] sayilar = {10, 20, 30};

System.out.println(sayilar); // ❌ Çıktı: [I@6acbcfc0 (Bellek adres kodu)

// ✅ DOĞRU: Döngüyle tek tek yazdırmak veya Arrays.toString() kullanmak:
import java.util.Arrays;
System.out.println(Arrays.toString(sayilar)); // Çıktı: [10, 20, 30]
```

---

### 3. Tuzak: Negatif Boyut Belirleme Hatası
Dizinin eleman adedi negatif bir sayı olamaz:

```java
int boyut = -5;
int[] dizi = new int[boyut]; // ❌ ÇÖKER: NegativeArraySizeException
```

---

## 10. Kapsamlı Örnek Konsol Uygulaması: `SinifNotAnalizLaboratuvari`

Aşağıdaki program; kullanıcıdan öğrenci sayısını ve her öğrencinin sınav notunu `double[]` dizisine alan, en yüksek, en düşük, sınıf ortalaması, geçen/kalan sayısı ve harf aralıklarını hesaplayan eksiksiz ve çalıştırılabilir bir uygulamadır:

```java
import java.util.Scanner;
import java.util.Arrays;

public class SinifNotAnalizLaboratuvari {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   KTÜ BİLGİSAYAR MÜH. DİZİ NOT ANALİZİ   ");
        System.out.println("==========================================");

        System.out.print("Sınıftaki toplam öğrenci sayısını giriniz: ");
        int ogrenciSayisi = scanner.nextInt();

        if (ogrenciSayisi <= 0) {
            System.out.println("❌ Hata: Öğrenci sayısı en az 1 olmalıdır!");
            scanner.close();
            return;
        }

        // 1. Dizi Tanımlama ve Bellek Tahsisi
        double[] notlar = new double[ogrenciSayisi];

        // 2. Dizi Elemanlarını Kullanıcıdan Alma
        System.out.println("\nLütfen notları sırayla giriniz (0 - 100):");
        for (int i = 0; i < notlar.length; i++) {
            System.out.print((i + 1) + ". Öğrencinin Notu: ");
            double girilen = scanner.nextDouble();

            // Girdi Doğrulama
            while (girilen < 0 || girilen > 100) {
                System.out.print("❌ Geçersiz! Not 0-100 arasında olmalıdır. Tekrar girin: ");
                girilen = scanner.nextDouble();
            }
            notlar[i] = girilen;
        }

        // 3. İstatistiksel Hesaplamalar
        double toplam = 0.0;
        double enYuksek = notlar[0];
        double enDusuk = notlar[0];
        int gecenSayisi = 0;
        final double GECME_NOTU = 50.0;

        for (int i = 0; i < notlar.length; i++) {
            double notDegeri = notlar[i];
            toplam += notDegeri;

            if (notDegeri > enYuksek) enYuksek = notDegeri;
            if (notDegeri < enDusuk)  enDusuk = notDegeri;
            if (notDegeri >= GECME_NOTU) gecenSayisi++;
        }

        double ortalama = toplam / notlar.length;
        int kalanSayisi = notlar.length - gecenSayisi;
        double basariOrani = ((double) gecenSayisi / notlar.length) * 100.0;

        // 4. Sonuç Raporu
        System.out.println("\n----------------- SINIF ANALİZ RAPORU -----------------");
        System.out.println("Girilen Notlar Seti     : " + Arrays.toString(notlar));
        System.out.println("Toplam Öğrenci Sayısı   : " + notlar.length);
        System.out.printf("Sınıf Not Ortalaması    : %.2f\n", ortalama);
        System.out.println("En Yüksek Puan (Zirve)  : " + enYuksek);
        System.out.println("En Düşük Puan (Taban)   : " + enDusuk);
        System.out.println("Dersi Geçen Öğrenci     : " + gecenSayisi);
        System.out.println("Dersten Kalan Öğrenci   : " + kalanSayisi);
        System.out.printf("Sınıf Başarı Yüzdesi    : %%%.1f\n", basariOrani);

        // 5. Ortalamanın Üstündeki Başarılı Öğrencileri Listeleme
        System.out.println("\nOrtalamanın ( " + String.format("%.2f", ortalama) + " ) Üzerindeki Öğrenciler:");
        for (int i = 0; i < notlar.length; i++) {
            if (notlar[i] > ortalama) {
                System.out.println("-> Öğrenci No #" + (i + 1) + " : " + notlar[i] + " puan");
            }
        }

        System.out.println("=======================================================");
        scanner.close();
    }
}
```

---

## 11. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Varsayılan Değerler ve İndeks)
Aşağıdaki programın konsol çıktısı ne olur? Adım adım takip ediniz:

```java
public class DiziIzleme1 {
    public static void main(String[] args) {
        int[] veriler = new int[4];
        veriler[1] = 10;
        veriler[3] = 30;

        for (int i = 0; i < veriler.length; i++) {
            veriler[i] += i * 2;
        }

        System.out.println(veriler[0] + " - " + veriler[1] + " - " + veriler[2] + " - " + veriler[3]);
    }
}
```
*İpucu: `veriler[0]` ve `veriler[2]` başlangıçta hangi varsayılan değere sahiptir?*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında yer alan **3 farklı derleme/çalışma hatasını** bulunuz:

```java
int sayi = 5;
int[] liste = new int[-sayi]; // Hata 1 nerede?

for (int i = 0; i <= liste.length; i++) { // Hata 2 nerede?
    liste[i] = i * 10;
}

System.out.println("Dizi Boyutu: " + liste.length()); // Hata 3 nerede?
```

---

### 🔄 Alıştırma 3: Diziyi Ters Çevirme (Yerinde - In-Place)
Kullanıcının tanımladığı bir `int[] dizi = {1, 2, 3, 4, 5, 6};` dizisinin elemanlarını **ikinci bir dizi oluşturmadan**, iki işaretçi (*pointer: sol ve sağ*) mantığıyla kendi içinde ters çeviren kodu yazınız (`{6, 5, 4, 3, 2, 1}`).

---

### 🔍 Alıştırma 4: Pozitif ve Negatif Sayı Sayacı
10 elemanlı rastgele tamsayılardan oluşan bir dizide kaç adet pozitif, kaç adet negatif ve kaç adet sıfır değeri olduğunu sayıp ekrana basan algoritmayı kodlayınız.

---

## 12. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Döngü sınırlarına, `ArrayIndexOutOfBoundsException` kontrollerine ve biçimli konsol çıktılarına özen gösteriniz.

---

### 📋 Ödev 1: Haftalık Hava Sıcaklık Takip ve Uyarı Sistemi
**Dosya Adı:** `HaftalikSicaklik.java`

**Problem Tanımı:**  
Bir meteoroloji istasyonu için 7 günlük hava sıcaklıklarını (`double[]`) saklayan bir sistem yazınız.
1. Haftanın gün isimlerini bir diziye tanımlayınız:  
   `String[] gunler = {"Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar"};`
2. Kullanıcıdan her gün için sıcaklık değerini (°C) alınız.
3. **Hesaplamalar:**
   - Haftanın ortalama sıcaklığı,
   - Haftanın en sıcak günü (Günün ismi ve derecesi),
   - Haftanın en soğuk günü (Günün ismi ve derecesi),
   - Don riski olan günler (Sıcaklık <= 0 °C olan günler listelenmelidir).

---

### 📋 Ödev 2: Dizideki Elemanları Sağa Döndürme (Right Shift / Rotate)
**Dosya Adı:** `DiziDondurme.java`

**Problem Tanımı:**  
Verilen bir dizinin tüm elemanlarını 1 pozisyon sağa kaydıran, en sondaki elemanı ise dizinin en başına alan programı yazınız.
- **Örnek Girdi:** `[10, 20, 30, 40, 50]`
- **1 Kez Sağa Döndürülmüş Hali:** `[50, 10, 20, 30, 40]`
- **Ek Görev:** Kullanıcıdan kaydırma adedini (`k` adım) alarak diziyi `k` kez sağa kaydıran genel algoritmayı kurunuz.

---

### 📋 Ödev 3: Dizideki En Çok Tekrar Eden Elemanı (Mod) Bulma
**Dosya Adı:** `DiziModBulucu.java`

**Problem Tanımı:**  
20 elemanlı, 1 ile 10 arasında rastgele sayılardan oluşan bir `int[]` dizisi üretiniz.
1. Diziyi ekrana yazdırınız.
2. Dizide **en çok tekrar eden sayıyı (frekansı en yüksek olan mod değerini)** ve bu sayının kaç kez tekrarlandığını iç içe döngülerle tespit ediniz.
- **Örnek Çıktı:** *"En çok tekrar eden sayı: 7 (Toplam 5 kez geçti)"*

---

### 📋 Ödev 4: Mükemmel Sayısal Loto Kupon Doğrulayıcısı
**Dosya Adı:** `LotoKuponuDogrulayici.java`

**Problem Tanımı:**  
1. Bilgisayar 1 ile 49 arasında rastgele 6 farklı kazanan sayı belirleyip `int[] kazananNumaralar` dizisine atsın.
2. Kullanıcıdan kendi oynadığı 6 numarayı alıp `int[] kupon` dizisine kaydediniz. (Kullanıcının 1-49 aralığı dışında veya mükerrer sayı girmesini engelleyiniz).
3. İki diziyi karşılaştırarak kullanıcının **kaç adet sayıyı doğru bildiğini** ve **tutturduğu sayıları** listeleyiniz.
4. Kazanç Tablosu:
   - 3 Bilen: *"Amorti Kazandınız (50 TL)"*
   - 4 Bilen: *"4. Kategori İkramiyesi (500 TL)"*
   - 5 Bilen: *"Büyük İkramiyeye Çok Yaklaştınız (15.000 TL)"*
   - 6 Bilen: *"Tebrikler! BÜYÜK İKRAMİYEYİ KAZANDINIZ! (5.000.000 TL)"*
   - 3'ten az: *"Maalesef ikramiye kazanamadınız. Bir sonraki çekilişe!"*
