# 3. Java'da `static` Anahtar Kelimesi ve Sınıf Üyeleri

Bu doküman; Java'da **nesneye ait üyeler (instance members)** ile **sınıfa ait üyeler (static / class members)** arasındaki temel farkı, **`static`** anahtar kelimesinin bellek çalışma mantığını (`Metaspace` / `Method Area`), statik değişkenleri, statik metotları, statik blokları ve en sık karşılaşılan `"non-static variable cannot be referenced from a static context"` derleme hatasının çözümünü detaylıca ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Kurucu ve Yıkıcı Metotlar, this (2_class-constructor.md)](./2_class-constructor.md)
> - 🔜 [Sonraki Bölüm: Kapsülleme (Encapsulation) ve Getter/Setter (4_kapsulleme.md)](./4_kapsulleme.md)

---

## 📌 İçindekiler
1. [`static` Kavramı Nedir ve Neden Kullanılır?](#1-static-kavramı-nedir-ve-neden-kullanılır)
   - [Gerçek Dünya Analojisi: Şahsi Kimlik vs. Ortak Okul Panosu](#gerçek-dünya-analojisi-şahsi-kimlik-vs-ortak-okul-panosu)
   - [Bellek Mimarisi: Metaspace vs. Heap vs. Stack](#bellek-mimarisi-metaspace-vs-heap-vs-stack)
2. [`static` Değişkenler (Sınıf Değişkenleri - Class Variables)](#2-static-değişkenler-sınıf-değişkenleri---class-variables)
   - [Tanımlama ve Doğru Erişim Standardı (`SinifAdi.degisken`)](#tanımlama-ve-doğru-erişim-standardı)
   - [Tüm Nesnelerin Paylaştığı Ortak Sayaç Mantığı](#tüm-nesnelerin-paylaştığı-ortak-sayaç-mantığı)
   - [`static final` ile Küresel Sabitler (Constants)](#static-final-ile-küresel-sabitler-constants)
3. [`static` Metotlar (Sınıf Metotları - Class Methods)](#3-static-metotlar-sınıf-metotları---class-methods)
   - [Neden Nesne Üretmeden Çağrılabilir?](#neden-nesne-üretmeden-çağrılabilir)
   - [Java'nın `Math` Sınıfı Analizi](#javanın-math-sınıfı-analizi)
   - [`main` Metodu Neden `static` Olmak Zorundadır?](#main-metodu-neden-static-olmak-zorundadır)
   - [Kritik Kural: `static` İçinden `non-static` Üyelere ve `this`'e Ulaşılamaz!](#kritik-kural-static-içinden-non-static-üyelere-ve-thise-ulaşılamaz)
4. [`static` Başlatma Blokları (Static Initialization Blocks)](#4-static-başlatma-blokları-static-initialization-blocks)
   - [Sınıfın Belleğe Yüklenme Anı (Class Loading)](#sınıfın-belleğe-yüklenme-anı-class-loading)
   - [Karmaşık Statik Verilerin İlk Değerlenmesi](#karmaşık-statik-verilerin-ilk-değerlenmesi)
5. [`static` Dahili Sınıflar (Static Nested Classes)](#5-static-dahili-sınıflar-static-nested-classes)
6. [Nesne Üyeleri (Instance) vs. Sınıf Üyeleri (Static) Karşılaştırma Tablosu](#6-nesne-üyeleri-instance-vs-sınıf-üyeleri-static-karşılaştırma-tablosu)
7. [Adım Adım Çalıştırılabilir Kod Örnekleri](#7-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Otomatik ID Üretimi ve Nesne Sayacı (`Ogrenci.java` & `Test.java`)](#örnek-1-otomatik-id-üretimi-ve-nesne-sayacı)
   - [Örnek 2: Yardımcı / Araç Sınıfı Tasarımı (`MatematikAraclari.java`)](#örnek-2-yardımcı--araç-sınıfı-tasarımı-utility-class)
   - [Örnek 3: Statik Başlatma Bloğu ve Konfigürasyon (`UygulamaAyarlari.java`)](#örnek-3-statik-başlatma-bloğu-ve-konfigürasyon)
8. [Sık Yapılan Hatalar ve Önemli Kurallar](#8-sık-yapılan-hatalar-ve-önemli-kurallar)
9. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#9-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
10. [Ödev Konuları ve Mini Projeler](#10-ödev-konuları-ve-mini-projeler)

---

## 1. `static` Kavramı Nedir ve Neden Kullanılır?

Şimdiye kadar yazdığımız sınıflarda her alan ve metot **nesneye özeldi (instance member)**. 
Örneğin `new Araba()` dediğimizde her arabanın kendi bağımsız `marka` ve `hiz` değişkeni oluyordu; araba1 hızlandığında araba2 bundan etkilenmiyordu.

Ancak bazı durumlarda:
1. Değerin **tüm nesneler tarafından ortaklaşa paylaşılması** gerekir (Örn: "Şu ana kadar kaç araba üretildi?", "Okulun adı nedir?").
2. Bir metodun çalışması için hiçbir nesneye, hiçbir nesne alanına ihtiyaç yoktur (Örn: İki sayının karekökünü alan bir metot için `new Math()` demeye gerek yoktur).

İşte bu tür durumlarda **`static`** anahtar kelimesi kullanılır.

> 💡 **Tanım:** `static` anahtar kelimesi ile işaretlenen bir değişken veya metot; tekil nesnelere değil, **doğrudan sınıfın kendisine aittir**. Sınıftan 1000 tane de nesne üretseniz, `static` değişken bellekte **yalnızca 1 adet** oluşturulur ve tüm nesneler aynı adresi paylaşır.

---

### Gerçek Dünya Analojisi: Şahsi Kimlik vs. Ortak Okul Panosu

```
                      +------------------------------------------+
                      |         SINIF BELLEĞİ (METASPACE)        |
                      |  class Ogrenci                           |
                      |  static String okulAdi = "Fen Lisesi";   |
                      |  static int toplamOgrenci = 3;           |
                      +------------------------------------------+
                                    ^        ^        ^
                     (Ortak Görür)  |        |        |  (Ortak Görür)
            +-----------------------+        |        +-----------------------+
            |                                |                                |
+-----------------------+        +-----------------------+        +-----------------------+
|   HEAP: Nesne 1       |        |   HEAP: Nesne 2       |        |   HEAP: Nesne 3       |
|   ad: "Ahmet"         |        |   ad: "Zeynep"        |        |   ad: "Burak"         |
|   ogrenciNo: 101      |        |   ogrenciNo: 102      |        |   ogrenciNo: 103      |
+-----------------------+        +-----------------------+        +-----------------------+
```

- **Nesne Alanları (Instance Fields - `ad`, `ogrenciNo`):** Her öğrencinin şahsi cebindeki kimlik kartıdır. Ahmet'in adı değişirse Zeynep'inki değişmez.
- **Statik Alanlar (Static Fields - `okulAdi`, `toplamOgrenci`):** Okulun girişindeki duyuru panosudur. Panodaki yazı değişirse okuldaki tüm öğrenciler aynı güncel yazıyı görür.

---

### Bellek Mimarisi: Metaspace vs. Heap vs. Stack

1. **Stack:** Referans değişkenleri ve yerel metot değişkenleri yaşar.
2. **Heap:** `new` ile açılan nesneler yaşar. Nesne sayısı kadar bellek ayrılır.
3. **Metaspace (Eski adıyla Method Area):** `static` değişkenler, metot bayt kodları ve sınıf tanımları burada saklanır. 
   - `static` alanlar sınıf JVM tarafından belleğe ilk yüklendiğinde (*Class Loading*) oluşturulur.
   - Program kapanana kadar bellekte tek kopya olarak kalır.

---

## 2. `static` Değişkenler (Sınıf Değişkenleri - Class Variables)

Sınıf düzeyinde paylaşılan değişkenlerdir. Tanımına `static` kelimesi eklenir:

```java
public class Dukkan {
    public String musteriAdi;     // Her nesneye özel (Instance)
    public static int musteriSayisi = 0; // Tüm dükkan için ortak (Static)
}
```

### Tanımlama ve Doğru Erişim Standardı
Statik bir değişkene erişmek için nesne üretmeye gerek yoktur. **Doğrudan sınıf ismi** üzerinden erişilir:

```java
// ✅ EN DOĞRU VE TAVSİYE EDİLEN KULLANIM (Sınıf Adı ile):
Dukkan.musteriSayisi = 5;

// ⚠️ ÇALIŞIR FAKAT KÖTÜ BİR KODLAMA ALIŞKANLIĞIDIR:
Dukkan d = new Dukkan();
d.musteriSayisi = 5; // Uyarı (Warning): Static member accessed via instance reference
```

---

### Tüm Nesnelerin Paylaştığı Ortak Sayaç Mantığı

Bir sınıftan kaç adet nesne üretildiğini saymanın en temiz yolu statik değişkendir:

```java
public class Araba {
    public String model;
    public static int uretilenArabaSayisi = 0;

    public Araba(String model) {
        this.model = model;
        uretilenArabaSayisi++; // Her kurucu çalıştığında ortak sayaç 1 artar!
    }
}
```

```java
Araba a1 = new Araba("Toyota");
Araba a2 = new Araba("BMW");
Araba a3 = new Araba("Tesla");

System.out.println("Toplam Üretilen: " + Araba.uretilenArabaSayisi); // Çıktı: 3
```

---

### `static final` ile Küresel Sabitler (Constants)

Java'da program genelinde değişmeyecek sabit değerler tanımlanırken `static` ve `final` kelimeleri birlikte kullanılır.
- `static`: Hafızada tek bir kopya tutulsun.
- `final`: Değeri sonradan asla değiştirilemesin.

> 📝 **İsimlendirme Standardı:** `static final` sabitlerinin tüm harfleri **BÜYÜK YAZILIR** ve kelimeler alt tire (`_`) ile ayrılır (**SCREAMING_SNAKE_CASE**).

```java
public class MatematikSabitleri {
    public static final double PI = 3.14159265359;
    public static final int MAKSIMUM_HIZ_LIMITI = 140;
    public static final String VARSAYILAN_DIL = "TR";
}
```

Java standart kütüphanesindeki `Math.PI`, `Integer.MAX_VALUE` gibi sabitler bu yapının en bilinen örnekleridir.

---

## 3. `static` Metotlar (Sınıf Metotları - Class Methods)

Bir metodun başına `static` yazıldığında, o metot artık bir nesneye ait olmaktan çıkar ve **sınıfın genel bir hizmetine (fonksiyonuna)** dönüşür.

### Neden Nesne Üretmeden Çağrılabilir?
Çünkü `static` bir metot, çalışmak için herhangi bir nesnenin özel durumuna (`this.alan`) bağımlı değildir. Girdilerini parametre olarak alır, hesaplar ve sonucu döndürür.

```java
public class Hesaplayici {
    // Statik Metot
    public static int topla(int a, int b) {
        return a + b;
    }
}

// Kullanım: new demeden doğrudan sınıf adıyla çağrılır
int sonuc = Hesaplayici.topla(15, 25); // 40
```

---

### Java'nın `Math` Sınıfı Analizi
Java'da hiçbir zaman şunu yazmazsınız:
```java
Math m = new Math(); // ❌ Gerek yok! (Zaten Math kurucusu private'tır)
double karekok = m.sqrt(16);
```
Bunun yerine doğrudan şunu yazarsınız:
```java
double karekok = Math.sqrt(16); // ✅ 4.0
int buyuk = Math.max(10, 25);    // ✅ 25
```
Çünkü `Math` sınıfındaki tüm metotlar `static` olarak tasarlanmıştır. Bu tür sınıflara **Araç / Yardımcı Sınıflar (Utility / Helper Classes)** denir.

---

### `main` Metodu Neden `static` Olmak Zorundadır?

Her Java programının giriş kapısı:
```java
public static void main(String[] args)
```
**Nedeni:** Program ilk çalıştırıldığında JVM belleğinde henüz hiçbir nesne yoktur (`new` yapılmamıştır). Eğer `main` metodu `static` olmasaydı, JVM'in `main`'i çalıştırabilmesi için önce o sınıfın bir nesnesini üretmesi gerekirdi; fakat nesneyi üretecek başlangıç kodu da henüz çalışmamış olurdu (tavuk-yumurta ikilemi!). 

Bu yüzden `main` metodu `static` yapılarak JVM'in doğrudan `SınıfAdı.main()` diyebilmesi sağlanmıştır.

---

### Kritik Kural: `static` İçinden `non-static` Üyelere ve `this`'e Ulaşılamaz!

Bu kural, nesneye yönelimli programlamada yeni başlayanların **en çok karşılaştığı 1 numaralı hatadır**:

```java
public class Deneme {
    int sayi = 10; // Nesne değişkeni (non-static)

    public static void test() {
        // ❌ DERLEME HATASI: non-static variable sayi cannot be referenced from a static context
        System.out.println(sayi); 
        
        // ❌ DERLEME HATASI: non-static variable this cannot be referenced from a static context
        System.out.println(this.sayi);
    }
}
```

#### Neden Yasaktır?
Çünkü `test()` metodu `Deneme.test()` dendiğinde çağrılabilir; o anda ortalıkta henüz hiçbir `Deneme` nesnesi üretilmemiş olabilir! Olmayan bir nesnenin `sayi` değişkeni de bellekte henüz var olamaz.

```
       +---------------------------------------------+
       | static Metotlar                             |
       |  -> Sadece static değişkenlere erişebilir   |
       |  -> Sadece static metotları çağırabilir     |
       |  -> "this" veya "super" KULLANAMAZ!         |
       +---------------------------------------------+
                            vs
       +---------------------------------------------+
       | Normal (Instance) Metotlar                  |
       |  -> Hem normal değişkenlere erişebilir      |
       |  -> Hem static değişkenlere erişebilir      |
       |  -> "this" serbestçe kullanılabilir         |
       +---------------------------------------------+
```

> 💡 **Çözüm:** Eğer `static` bir metot içinden `non-static` bir değişkene ulaşmak istiyorsanız, o metot içinde önce `new` ile nesne oluşturmanız gerekir:
> ```java
> public static void test() {
>     Deneme d = new Deneme(); // Nesne üret
>     System.out.println(d.sayi); // ✅ Artık nesne üzerinden erişilebilir!
> }
> ```

---

## 4. `static` Başlatma Blokları (Static Initialization Blocks)

Bir kurucu metot (`Constructor`), her nesne `new` yapıldığında çalışır.
Peki **sınıf düzeyindeki statik değişkenlerin ilk değerlerini** karmaşık mantıklarla (döngüler, dosya okuma, if-else) yüklemek istersek ne kullanırız?

Cevap: **`static { ... }` bloğu**.

```java
public class VeritabaniYapilandirma {
    public static String baglantiMetni;
    public static int port;

    // Statik Başlatma Bloğu
    static {
        System.out.println(">> Statik blok çalıştı: Sınıf belleğe yüklendi!");
        port = 3306;
        baglantiMetni = "jdbc:mysql://localhost:" + port + "/eticaret";
    }

    public VeritabaniYapilandirma() {
        System.out.println(">> Kurucu metot çalıştı (Nesne üretildi).");
    }
}
```

### Çalışma Kuralları:
1. `static` blok, sınıf belleğe yüklendiğinde **tam 1 kez** çalışır.
2. Sınıftan 100 tane nesne üretseniz bile `static` blok ikinci kez **ASLA çalışmaz**.
3. Kurucu metottan (`Constructor`) **önce** devreye girer.

---

## 5. `static` Dahili Sınıflar (Static Nested Classes)

Bir sınıfın içine yardımcı olması amacıyla başka bir sınıf tanımlanabilir. Eğer içteki sınıf `static` olarak tanımlanırsa:
- Dış sınıfın bir nesnesine ihtiyaç duymadan doğrudan oluşturulabilir:

```java
public class DisSinif {
    public static class IcSinif {
        public void selamVer() {
            System.out.println("Statik iç sınıftan merhaba!");
        }
    }
}

// Kullanım:
DisSinif.IcSinif icNesne = new DisSinif.IcSinif();
icNesne.selamVer();
```

---

## 6. Nesne Üyeleri (Instance) vs. Sınıf Üyeleri (Static) Karşılaştırma Tablosu

| Kriter | Nesne Üyesi (Instance Member) | Sınıf Üyesi (Static Member) |
| :--- | :--- | :--- |
| **Anahtar Kelime** | Belirtilmez (`int sayi;`) | `static` (`static int sayi;`) |
| **Bellek Yeri** | **Heap** (Her nesnenin kendi alanı) | **Metaspace** (Tüm sınıf için ortak tek kopya) |
| **Oluşma Zamanı** | `new` ile nesne üretildiği anda | Sınıf JVM tarafından belleğe yüklendiğinde |
| **Erişim Yolu** | `nesneReferansi.alan` | `SinifAdi.alan` (Tavsiye edilen) |
| **`this` Kullanımı** | ✅ Kullanılabilir (`this.ad`) | ❌ Kullanılamaz (Derleme hatası verir) |
| **Kullanım Amacı** | Nesneye özgü durumlar (yaş, bakiye) | Ortak sayaçlar, sabitler, araç fonksiyonları |

---

## 7. Adım Adım Çalıştırılabilir Kod Örnekleri

Aşağıdaki örnekleri bilgisayarınızda derleyip çalıştırabilirsiniz.

---

### Örnek 1: Otomatik ID Üretimi ve Nesne Sayacı

Bu örnekte statik bir sayaç yardımıyla, her yeni öğrenci oluşturulduğunda otomatik artan bir `ogrenciNo` atayacağız.

#### `Ogrenci.java`
```java
public class Ogrenci {
    // Sınıf düzeyi (Static): Tüm öğrenciler bu sayacı ortak artırır
    private static int sayac = 1000;
    private static String okulAdi = "Atatürk Fen Lisesi";

    // Nesne düzeyi (Instance): Her öğrencinin numarası ve adı kendine özeldir
    private int ogrenciNo;
    private String ad;

    public Ogrenci(String ad) {
        this.ad = ad;
        sayac++;                // Ortak sayacı 1 artır
        this.ogrenciNo = sayac; // Yeni numarayı bu öğrenciye zimmetle
    }

    public void kartYazdir() {
        System.out.println("[" + okulAdi + "] No: " + ogrenciNo + " - İsim: " + ad);
    }

    // Statik metot: Toplam kayıtlı öğrenci sayısını döner
    public static int getToplamOgrenciSayisi() {
        return sayac - 1000;
    }

    // Okul adını değiştiren statik metot (Değişirse herkes için değişir)
    public static void setOkulAdi(String yeniOkulAdi) {
        okulAdi = yeniOkulAdi;
    }
}
```

#### `OgrenciSayacTest.java`
```java
public class OgrenciSayacTest {
    public static void main(String[] args) {
        System.out.println("Başlangıçta Öğrenci Sayısı: " + Ogrenci.getToplamOgrenciSayisi());

        Ogrenci o1 = new Ogrenci("Ahmet Kaya");
        Ogrenci o2 = new Ogrenci("Zeynep Çelik");
        Ogrenci o3 = new Ogrenci("Mehmet Demir");

        o1.kartYazdir();
        o2.kartYazdir();
        o3.kartYazdir();

        System.out.println("\nGüncel Toplam Öğrenci Sayısı: " + Ogrenci.getToplamOgrenciSayisi());

        // Okul adı değişirse ne olur?
        System.out.println("\n-- Okul Adı Değiştiriliyor --");
        Ogrenci.setOkulAdi("Anadolu Lisesi");

        // Tüm öğrencilerin okul adı güncellenmiş olur!
        o1.kartYazdir();
        o2.kartYazdir();
    }
}
```

#### Konsol Çıktısı:
```text
Başlangıçta Öğrenci Sayısı: 0
[Atatürk Fen Lisesi] No: 1001 - İsim: Ahmet Kaya
[Atatürk Fen Lisesi] No: 1002 - İsim: Zeynep Çelik
[Atatürk Fen Lisesi] No: 1003 - İsim: Mehmet Demir

Güncel Toplam Öğrenci Sayısı: 3

-- Okul Adı Değiştiriliyor --
[Anadolu Lisesi] No: 1001 - İsim: Ahmet Kaya
[Anadolu Lisesi] No: 1002 - İsim: Zeynep Çelik
```

---

### Örnek 2: Yardımcı / Araç Sınıfı Tasarımı (Utility Class)

Genel matematiksel ve geometrik hesaplamaları nesne üretmeden sunan bir araç sınıfı:

#### `GeometriAraclari.java`
```java
public class GeometriAraclari {
    // Sabit
    public static final double PI = 3.14159;

    // Nesne üretilmesini engellemek için kurucuyu private yapıyoruz (Best Practice!)
    private GeometriAraclari() {
        // Bu sınıftan new yapılamaz!
    }

    public static double daireAlani(double yaricap) {
        return PI * yaricap * yaricap;
    }

    public static double daireCevresi(double yaricap) {
        return 2 * PI * yaricap;
    }

    public static double ucgenAlani(double taban, double yukseklik) {
        return (taban * yukseklik) / 2.0;
    }

    public static boolean dikUcgenMi(int a, int b, int c) {
        // Pisagor teoremi: a^2 + b^2 == c^2
        return (a * a + b * b) == (c * c);
    }
}
```

#### `GeometriTest.java`
```java
public class GeometriTest {
    public static void main(String[] args) {
        // Nesne üretmeden doğrudan hesaplama:
        double alan = GeometriAraclari.daireAlani(5.0);
        double cevre = GeometriAraclari.daireCevresi(5.0);

        System.out.println("Daire Alanı  : " + alan);
        System.out.println("Daire Çevresi: " + cevre);

        boolean ucgen345 = GeometriAraclari.dikUcgenMi(3, 4, 5);
        System.out.println("3-4-5 bir dik üçgen midir? -> " + (ucgen345 ? "EVET" : "HAYIR"));
    }
}
```

---

### Örnek 3: Statik Başlatma Bloğu ve Konfigürasyon

#### `SunucuAyarlari.java`
```java
import java.util.ArrayList;
import java.util.List;

public class SunucuAyarlari {
    public static final String SUNUCU_ADI;
    public static final List<String> AKTIF_MODULLER = new ArrayList<>();

    // Statik blok ile sabitlerin ilk değerlenmesi
    static {
        System.out.println("[SİSTEM] Statik blok çalıştı: Sistem ayarları yükleniyor...");
        SUNUCU_ADI = "PROD-SERVER-01";
        
        AKTIF_MODULLER.add("Kimlik Doğrulama Servisi");
        AKTIF_MODULLER.add("Ödeme Geçidi");
        AKTIF_MODULLER.add("E-Posta Bildirim Servisi");
        System.out.println("[SİSTEM] Modüller hazırlandı.\n");
    }

    public static void durumuListele() {
        System.out.println("Sunucu: " + SUNUCU_ADI);
        System.out.println("Yüklü Modüller: " + AKTIF_MODULLER);
    }
}
```

#### `AyarTest.java`
```java
public class AyarTest {
    public static void main(String[] args) {
        System.out.println("Main metodu başladı.");
        SunucuAyarlari.durumuListele();
        System.out.println("Main metodu bitti.");
    }
}
```

#### Konsol Çıktısı:
```text
Main metodu başladı.
[SİSTEM] Statik blok çalıştı: Sistem ayarları yükleniyor...
[SİSTEM] Modüller hazırlandı.

Sunucu: PROD-SERVER-01
Yüklü Modüller: [Kimlik Doğrulama Servisi, Ödeme Geçidi, E-Posta Bildirim Servisi]
Main metodu bitti.
```

---

## 8. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `non-static variable ... cannot be referenced from a static context`
Yeni başlayanların en sık yaptığı hata: `main` metodunun içinden doğrudan sınıf değişkenine erişmeye çalışmaktır.
```java
public class Main {
    int sayi = 42; // Instance değişken

    public static void main(String[] args) {
        System.out.println(sayi); // ❌ HATA! main statiktir, sayi nesneye aittir.
    }
}
```
✅ **Çözüm:** Ya `sayi` değişkenini `static int sayi = 42;` yapmalısınız ya da `Main m = new Main(); System.out.println(m.sayi);` şeklinde nesne üzerinden erişmelisiniz.

---

### 2. Statik Metot İçinde `this` Kullanmaya Çalışmak
```java
public class Kullanici {
    private static String rol;

    public static void setRol(String rol) {
        this.rol = rol; // ❌ HATA: non-static variable this cannot be referenced from a static context
    }
}
```
✅ **Çözüm:** `static` metotlarda `this` nesnesi yoktur. Bunun yerine sınıf adı kullanılır:
```java
Kullanici.rol = rol; // ✅ Doğru
```

---

### 3. Statik Değişkene Nesne Referansı Üzerinden Erişmek
```java
Ogrenci o = new Ogrenci("Ali");
System.out.println(o.okulAdi); // ⚠️ Çalışır ama KÖTÜ KOD!
```
Bu kod okuyucuda sanki `okulAdi` sadece Ali'ye özel bir değişkenmiş gibi yanıltıcı bir izlenim bırakır.
✅ **Çözüm:** Her zaman `Ogrenci.okulAdi` şeklinde sınıf adı ile çağrılmalıdır.

---

### 4. Her Şeyi `static` Yapma Hastalığı (Procedural Trap)
Nesne yönelimli programlamaya yeni başlayanlar `new` ile uğraşmamak için her değişkeni ve metodu `static` yapma eğilimindedir. 
- Bu durum Java'yı C gibi prosedürel bir dile dönüştürür.
- Tüm veriler küresel (global) hale gelir, bellek yönetimi bozulur ve nesnelerin bağımsız durum tutma özelliği yok olur.
- **Kural:** Sadece gerçekten tüm nesneler için ortak olan verileri ve bağımsız matematik/yardımcı metotları `static` yapınız!

---

## 9. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Kod Okuma ve Çıktı Analizi
Aşağıdaki program çalıştığında ekranda ne görürsünüz? Sırayı ve değerleri takip ediniz:

```java
class Sayac {
    static int s = 0;
    int n = 0;

    public Sayac() {
        s++;
        n++;
    }

    public void yazdir() {
        System.out.println("Static s: " + s + " | Instance n: " + n);
    }
}

public class Test {
    public static void main(String[] args) {
        Sayac c1 = new Sayac();
        Sayac c2 = new Sayac();
        Sayac c3 = new Sayac();

        c1.yazdir();
        c3.yazdir();
    }
}
```

<details>
<summary>🔍 <b>Çözümü ve Açıklamayı Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
```text
Static s: 3 | Instance n: 1
Static s: 3 | Instance n: 1
```
**Açıklama:**
- `s` statik olduğu için bellekte tek bir adrestedir. Her `new Sayac()` çağrıldığında aynı değişken 1 artar ($0 \to 1 \to 2 \to 3$). 3 nesne üretildiğinde `s = 3` olur.
- `n` nesneye özeldir (`instance`). Her nesne doğduğunda kendi `n` değişkenini 0'dan 1'e çıkarır. Bu yüzden `c1.n` de 1'dir, `c3.n` de 1'dir.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kodda derleme hatasına sebep olan **2 noktayı** tespit ediniz:

```java
public class Sirket {
    public static String sirketAdi = "Yazılım A.Ş.";
    public String calisanAdi;

    public static void bilgiVer() {
        System.out.println("Şirket: " + sirketAdi);
        System.out.println("Çalışan: " + calisanAdi);
    }

    public void calisanSelamla() {
        System.out.println("Merhaba " + this.calisanAdi + ", " + sirketAdi + "'ne hoş geldin!");
    }
}
```

<details>
<summary>🔍 <b>Hataları ve Çözümü Görmek İçin Tıklayın</b></summary>

1. **Hata 1:** `bilgiVer()` metodu `static`'tir. İçerisinde `non-static` olan `calisanAdi` değişkenine doğrudan erişemez (`non-static variable calisanAdi cannot be referenced from a static context`).
2. **Doğru Olan Yer:** `calisanSelamla()` metodu `non-static` bir metottur ve hem `this.calisanAdi` nesne alanına hem de `sirketAdi` statik alanına rahatlıkla erişebilir (Burada hata yoktur).
3. **Düzeltme:** `calisanAdi` ya `bilgiVer()` metoduna parametre olarak verilmeli ya da `bilgiVer()` metodu `non-static` yapılmalıdır.
</details>

---

### Alıştırma 3: Utility Sınıfı Yazma
`MetinAraclari` adında bir sınıf tasarlayınız:
- Kurucusu `private` olsun.
- `static boolean palindromMu(String metin)`: Verilen metnin tersten okunuşu ile düz okunuşunun aynı olup olmadığını kontrol etsin (`"kayak"` -> `true`).
- `static int sesliHarfSayisi(String metin)`: Metindeki Türkçe sesli harflerin (`a, e, ı, i, o, ö, u, ü`) toplam sayısını dönsün.
`main` içinde nesne üretmeden test ediniz.

---

## 10. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Banka Müşterisi ve Toplam Kasa Takibi (`Musteri.java` & `BankaTest.java`)

**Gereksinimler:**
1. `Musteri` sınıfı oluşturunuz.
2. Statik Alanlar:
   - `private static int musteriSayaci = 0;`
   - `private static double bankaToplamKasasi = 0.0;`
3. Nesne Alanları:
   - `private int musteriId;`
   - `private String adSoyad;`
   - `private double bakiye;`
4. Kurucu Metot:
   - `adSoyad` ve `ilkBakiye` alsın.
   - Her yeni müşteride `musteriSayaci` 1 artsın ve bu değer müşterinin `musteriId`'si olsun (1, 2, 3...).
   - Müşterinin yatırdığı `ilkBakiye` hem kendi bakiyesine hem de ortak `bankaToplamKasasi`na eklensin.
5. Statik Metotlar:
   - `public static void bankaRaporuGoster()`: Toplam kaç müşteri olduğunu ve bankanın kasasındaki toplam parayı yazdırsın.
6. 3 müşteri oluşturup para yatırma simülasyonu yapınız ve genel banka raporunu ekrana basınız.

---

### 🟡 Ödev 2 (Orta Seviye): Kapsamlı Birim Dönüştürücü Araç Sınıfı (`BirimDonusturucu.java`)

**Gereksinimler:**
1. Bir Utility sınıfı olarak `BirimDonusturucu` tasarlayınız (`new` yapılamasın).
2. `static final` Sabitler:
   - `INCH_TO_CM = 2.54;`
   - `MIL_TO_KM = 1.60934;`
   - `POUND_TO_KG = 0.453592;`
3. Statik Metotlar:
   - `celsiusToFahrenheit(double c)` ve `fahrenheitToCelsius(double f)`
   - `kmToMil(double km)` ve `milToKm(double mil)`
   - `kgToPound(double kg)` ve `poundToKg(double pound)`
4. `main` metodu içinde kullanıcıya bir menü göstererek veya doğrudan test çağrılarıyla tüm birim dönüşümlerini hassas ondalıklı olarak ekrana basınız.

---

### 🔴 Ödev 3 (İleri Seviye): Çok Oyunculu Oyun Odası Simülasyonu (`OyunOdasi.java` & `Oyuncu.java`)

**Gereksinimler:**
1. `OyunOdasi` sınıfı:
   - `public static final int MAKSIMUM_KAPASITE = 4;`
   - `private static int aktifOyuncuSayisi = 0;`
   - `private static final List<String> oyuncuListesi = new ArrayList<>();`
   - `static` blok ile sunucu adı (`"TR-ISTANBUL-LOBBY"`) ve lobi başlangıç mesajı konsola basılsın.
   - `public static boolean odayaKatil(String oyuncuAdi)`: Kapasite dolmadıysa oyuncuyu listeye eklesin, sayacı artırsın ve `true` dönsün; dolduysa "Oda dolu!" uyarısı verip `false` dönsün.
   - `public static void odadanAyril(String oyuncuAdi)`: Oyuncuyu listeden silsin ve sayacı azaltsın.
   - `public static void lobiDurumu()`: Odadaki aktif oyuncuları ve kalan kontenjanı yazdırsın.
2. `OyunTest.java` dosyasında:
   - 5 farklı oyuncuyu odaya sokmaya çalışınız (5. oyuncuda hata verip kabul etmediğini gösteriniz).
   - Bir oyuncuyu odadan çıkarıp yeni bir oyuncunun başarıyla girebildiğini test ediniz.

