# 1. Java'da Sınıf (Class) ve Nesne (Object) Temelleri

Bu doküman; Java'da **Nesne Yönelimli Programlama (Object-Oriented Programming - OOP)** paradigmasının en temel yapı taşı olan **Sınıf (Class)** ve **Nesne (Object)** kavramlarını detaylıca ele almak için hazırlanmıştır.

Burada bir sınıfın nasıl tanımlanacağını, sınıftan nasıl nesneler üretileceğini, bellekte (Stack & Heap) nelerin gerçekleştiğini, **`public`**, **`private`**, **`protected`** ve **varsayılan (default)** erişim belirleyicilerini ve nesnelerin birbiriyle nasıl etkileşime girdiğini öğreneceksiniz.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Fonksiyonlar ve Metotlar (3_fonksiyonlar)](../3_fonksiyonlar/)
> - 🔜 [Sonraki Bölüm: Kurucu ve Yıkıcı Metotlar, this (2_class-constructor.md)](./2_class-constructor.md)

---

## 📌 İçindekiler
1. [Sınıf (Class) ve Nesne (Object) Nedir?](#1-sınıf-class-ve-nesne-object-nedir)
   - [Gerçek Dünya Analojisi: Mimari Plan ve İnşa Edilen Evler](#gerçek-dünya-analojisi-mimari-plan-ve-inşa-edilen-evler)
   - [Stack ve Heap Bellek Mantığı](#stack-ve-heap-bellek-mantığı)
2. [Java'da Sınıf Tanımlama Sözdizimi](#2-javada-sınıf-tanımlama-sözdizimi)
   - [Sınıf Bileşenleri: Alanlar (Fields) ve Metotlar (Methods)](#sınıf-bileşenleri-alanlar-fields-ve-metotlar-methods)
   - [İsimlendirme Standartları (PascalCase)](#isimlendirme-standartları-pascalcase)
3. [Nesne Oluşturma ve Kullanımı (`new` ve `.` Operatörleri)](#3-nesne-oluşturma-ve-kullanımı-new-ve--operatörleri)
   - [Referans Değişkeni Tanımlama](#referans-değişkeni-tanımlama)
   - [`new` Operatörü ile Örnekleme (Instantiation)](#new-operatörü-ile-örnekleme-instantiation)
   - [Nokta (`.`) Operatörü ile Alanlara ve Metotlara Erişim](#nokta--operatörü-ile-alanlara-ve-metotlara-erişim)
4. [Erişim Belirleyiciler (Access Modifiers)](#4-erişim-belirleyiciler-access-modifiers)
   - [Neden Erişim Belirleyicilere İhtiyaç Duyarız?](#neden-erişim-belirleyicilere-ihtiyaç-duyarız)
   - [`public` (Açık Erişim)](#1-public-herkese-açık)
   - [`private` (Özel / Gizli Erişim)](#2-private-yalnızca-sınıf-içi)
   - [`protected` (Korumalı Erişim)](#3-protected-korumalı-erişim)
   - [Varsayılan / Default (Package-Private - Paket Düzeyi)](#4-varsayılan-default--package-private)
   - [Karşılaştırma ve Özet Tablosu](#karşılaştırma-ve-özet-tablosu)
   - [Kapsülleme (Encapsulation) ve Getter/Setter Mantığı](#kapsülleme-encapsulation-ve-gettersetter-mantığı)
5. [Adım Adım Kod Örnekleri](#5-adım-adım-kod-örnekleri)
   - [Örnek 1: Temel Sınıf ve Nesne Kullanımı (`Araba.java` & `Main.java`)](#örnek-1-temel-sınıf-ve-nesne-kullanımı)
   - [Örnek 2: Erişim Belirleyicileri ile Veri Koruma (`BankaHesabi.java`)](#örnek-2-erişim-belirleyicileri-ile-veri-koruma)
   - [Örnek 3: Birden Fazla Nesne ve Bağımsız Durumlar (`Ogrenci.java`)](#örnek-3-birden-fazla-nesne-ve-bağımsız-durumlar)
6. [Sık Yapılan Hatalar ve Önemli Kurallar](#6-sık-yapılan-hatalar-ve-önemli-kurallar)
7. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#7-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
8. [Ödev Konuları ve Mini Projeler](#8-ödev-konuları-ve-mini-projeler)

---

## 1. Sınıf (Class) ve Nesne (Object) Nedir?

Java'nın en temel sloganı: **"Her şey bir sınıf içinde yer alır ve nesneler aracılığıyla çalışır."**

- **Sınıf (Class):** Bir nesnenin hangi özelliklere (verilere) ve hangi yeteneklere (metotlara) sahip olacağını belirleyen **şablon**, **taslak** veya **kalıptır**.
- **Nesne (Object / Instance):** Bu şablondan yola çıkılarak hafızada üretilmiş olan **somut, yaşayan örnektir**.

### Gerçek Dünya Analojisi: Mimari Plan ve İnşa Edilen Evler

| Kavram | Gerçek Hayat Karşılığı | Açıklama |
| :--- | :--- | :--- |
| **Sınıf (Class)** | Mimari Ev Projesi (Blueprint) | Kağıt üzerindeki plandır. İçinde kaç oda olacağı, muslukların nerede duracağı yazılıdır. Kendisi bir bina değildir, içine girip oturamazsınız; sadece tanımdır. |
| **Nesne (Object)** | İnşa Edilen Gerçek Ev | Projeden üretilen gerçek mülktür. Adresi vardır, boyası kırmızı veya mavi olabilir. Bir plandan binlerce farklı ev inşa edebilirsiniz. |

```
        +-----------------------------------+
        |          SINIF (CLASS)            |
        |        class Araba { ... }        |
        |  [Şablon: marka, model, hizSur()] |
        +-----------------------------------+
             /           |           \
   new Araba()      new Araba()      new Araba()
           /             |             \
          v              v              v
+------------------+ +------------------+ +------------------+
|  NESNE 1 (Heap)  | |  NESNE 2 (Heap)  | |  NESNE 3 (Heap)  |
| Marka: "Toyota"  | | Marka: "BMW"     | | Marka: "Tesla"   |
| Model: "Corolla" | | Model: "320i"    | | Model: "Model 3" |
| Hiz: 90          | | Hiz: 120         | | Hiz: 0           |
+------------------+ +------------------+ +------------------+
```

### Stack ve Heap Bellek Mantığı

Bir Java programı çalıştığında iki ana bellek bölgesi kullanılır:
1. **Stack (Yığın):** Metot çağrıları, yerel değişkenler ve nesnelerin **referansları (bellek adresleri)** burada tutulur.
2. **Heap (Öbek):** `new` kelimesiyle oluşturulan gerçek nesnelerin tüm verileri (alan değerleri) burada saklanır.

Örneğin:
```java
Araba a1 = new Araba();
```
- `a1`: Stack bellekte yaşayan bir **referans değişkenidir** (Heap'teki nesnenin adresini tutar).
- `new Araba()`: Heap bellekte yeni bir nesne gövdesi tahsis eder. `a1` bu alana işaret eder.

---

## 2. Java'da Sınıf Tanımlama Sözdizimi

Java'da bir sınıf oluşturmak için `class` anahtar kelimesi kullanılır:

```java
[erişim_belirleyici] class SınıfAdı {
    // 1. Özellikler / Nitelikler (Fields / Attributes / Instance Variables)
    [erişim_belirleyici] tip degiskenAdi;

    // 2. Davranışlar / Metotlar (Methods)
    [erişim_belirleyici] donusTipi metotAdi(parametreler) {
        // Metot gövdesi
    }
}
```

### Sınıf Bileşenleri: Alanlar (Fields) ve Metotlar (Methods)

1. **Alanlar (Fields / Instance Variables):** Nesnenin durumunu (state) tutan değişkenlerdir (örneğin: `ad`, `yas`, `bakiye`).
2. **Metotlar (Methods):** Nesnenin yapabileceği eylemleri veya davranışları belirten fonksiyonlardır (örneğin: `bilgiYazdir()`, `paraYatir()`, `frenYap()`).

### İsimlendirme Standartları (PascalCase)

- Sınıf isimleri her zaman **büyük harfle başlar** ve birden fazla kelimeden oluşuyorsa her kelimenin ilk harfi büyük yazılır (**PascalCase**):
  - ✅ `Ogrenci`, `BankaHesabi`, `ArabaGaleriYonetimi`
  - ❌ `ogrenci`, `banka_hesabi`, `BANKAHESABI`
- Değişken ve metot isimleri ise küçük harfle başlar ve sonraki kelimeler büyük harfle devam eder (**camelCase**):
  - ✅ `ogrenciAdi`, `hesapBakiyesi`, `notOrtalamasiHesapla()`

---

## 3. Nesne Oluşturma ve Kullanımı (`new` ve `.` Operatörleri)

Bir sınıf tanımlandıktan sonra ondan bir nesne türetmek üç adımdan oluşur:

### 1. Referans Değişkeni Tanımlama
Hangi sınıftan bir nesne tutulacağını belirtiriz:
```java
Ogrenci ogr; // Henüz bellekte nesne yok, ogr = null değerindedir.
```

### 2. `new` Operatörü ile Örnekleme (Instantiation)
`new` anahtar kelimesi ile Heap bellekte o sınıfa ait alanlar için yer ayrılır:
```java
ogr = new Ogrenci(); // Artık bellekte somut bir Ogrenci nesnesi var.
```
Genellikle bu iki işlem tek bir satırda birleştirilir:
```java
Ogrenci ogr = new Ogrenci();
```

### 3. Nokta (`.`) Operatörü ile Alanlara ve Metotlara Erişim
Oluşturulan nesnenin alanlarına değer atamak ya da metotlarını çalıştırmak için referans adının ardına nokta (`.`) konur:
```java
ogr.ad = "Ahmet";            // Alana değer atama
ogr.numara = 101;            // Alana değer atama
ogr.bilgileriGoster();       // Metodu çağırma
```

> 💡 **Önemli Kural:** `static` olmayan alan ve metotlara erişebilmek için **mutlaka** `new` ile bir nesne oluşturulmalıdır.

---

## 4. Erişim Belirleyiciler (Access Modifiers)

Erişim belirleyiciler; bir sınıfın, bir değişkenin veya bir metodun programın hangi bölümlerinden görülebileceğini ve erişilebileceğini kontrol eden anahtar kelimelerdir.

Java'da **4 farklı erişim düzeyi** bulunur:

### Neden Erişim Belirleyicilere İhtiyaç Duyarız?
Düşünün ki bir banka hesabınız var (`bakiye`). Eğer bu değişken herkese açık olursa, programın herhangi bir yerinden `hesap.bakiye = -999999;` yazılarak veriniz bozulabilir. Erişim belirleyiciler veriyi korur, sistemin güvenliğini ve kontrol edilebilirliğini sağlar (**Veri Gizleme / Kapsülleme**).

---

### 1. `public` (Herkese Açık)
- **Kapsam:** Projenin her yerinden, tüm paket ve sınıflardan doğrudan erişilebilir.
- **Kullanım Yeri:** Dış dünyayla etkileşim kurması gereken metotlar (örneğin `main` metodu, `public void bakiyeGoster()`) ve genel sınıflar.
- **Örnek:**
  ```java
  public class Kisi {
      public String ad; // Herkes doğrudan okuyabilir ve değiştirebilir
  }
  ```

---

### 2. `private` (Yalnızca Sınıf İçi)
- **Kapsam:** Yalnızca **tanımlandığı sınıfın içinden** erişilebilir. Sınıf dışından (aynı pakette veya alt sınıfta olsa bile) görünmez!
- **Kullanım Yeri:** Sınıfın iç durumu, kritik veriler, hassas değişkenler (şifre, bakiye, kimlik no) ve yalnızca içeride kullanılan yardımcı metotlar.
- **Örnek:**
  ```java
  public class BankaHesabi {
      private double bakiye; // Sadece BankaHesabi sınıfındaki metotlar erişebilir!
  }
  ```
  Eğer dışarıdan `hesap.bakiye` yazmaya çalışırsanız Java derleyicisi hata verir:
  `bakiye has private access in BankaHesabi`.

---

### 3. `protected` (Korumalı Erişim)
- **Kapsam:** 
  1. Aynı paket (package) içerisindeki tüm sınıflar erişebilir.
  2. Farklı pakette olsa dahi bu sınıftan türetilen **alt sınıflar (subclasses / inheritance / miras alanlar)** erişebilir.
- **Kullanım Yeri:** Kalıtım (inheritance) hiyerarşisinde çocuk sınıfların erişmesine izin verilen ancak dış dünyaya kapatılmak istenen alan ve metotlar.
- **Örnek:**
  ```java
  public class Hayvan {
      protected String turAdi; // Miras alan Kopek veya Kedi sınıfları erişebilir
  }
  ```

---

### 4. Varsayılan / Default (Package-Private)
- **Kapsam:** Eğer değişken veya metodun önüne hiçbir belirleyici yazılmazsa (`public`, `private` veya `protected` konmazsa), varsayılan erişim geçerli olur.
- Sadece **aynı paket (package) içerisindeki sınıflar** erişebilir. Farklı paketteki sınıflar (miras alsalar dahi) erişemez.
- **Örnek:**
  ```java
  class Depo {
      int stokAdedi; // Paket içi erişim (package-private)
  }
  ```

---

### Karşılaştırma ve Özet Tablosu

| Belirleyici (Modifier) | Aynı Sınıf (Same Class) | Aynı Paket (Same Package) | Alt Sınıf (Subclass - Miras) | Tüm Dünya (Everywhere / Diğer Paket) |
| :--- | :---: | :---: | :---: | :---: |
| **`public`** | ✅ Evet | ✅ Evet | ✅ Evet | ✅ Evet |
| **`protected`** | ✅ Evet | ✅ Evet | ✅ Evet | ❌ Hayır |
| **`default` (belirtilmemiş)** | ✅ Evet | ✅ Evet | ❌ Hayır | ❌ Hayır |
| **`private`** | ✅ Evet | ❌ Hayır | ❌ Hayır | ❌ Hayır |

> 📌 **En Genişten En Dara Doğru Sıralama:**
> `public` > `protected` > `default (package-private)` > `private`

---

### Kapsülleme (Encapsulation) ve Getter/Setter Mantığı

`private` yaptığımız bir değişkene dış dünyadan güvenli bir şekilde nasıl ulaşırız? 
Cevap: **Getter** (okuma) ve **Setter** (yazma / denetleme) metotları.

```java
public class Kisi {
    private int yas; // Dışarıya kapalı

    // Getter: Değeri dışarıya kontrollü okutur
    public int getYas() {
        return this.yas;
    }

    // Setter: Değeri dışarıdan alırken mantık kontrolü (validasyon) yapar
    public void setYas(int yeniYas) {
        if (yeniYas >= 0 && yeniYas <= 150) {
            this.yas = yeniYas;
        } else {
            System.out.println("Hata: Geçersiz yaş girdisi (" + yeniYas + ")!");
        }
    }
}
```

Bu sayede bir kullanıcı `kisi.yas = -25;` yaparak verimizi bozamaz!

---

## 5. Adım Adım Kod Örnekleri

Aşağıdaki örnekleri bilgisayarınızda bir Java projesinde veya terminalde derleyip çalıştırabilirsiniz.

---

### Örnek 1: Temel Sınıf ve Nesne Kullanımı

Bu örnekte bir `Araba` sınıfı tanımlayıp, `Main` sınıfı içerisinden iki farklı araba nesnesi üreteceğiz.

#### `Araba.java`
```java
// Araba sınıfı (Kalıp / Taslak)
public class Araba {
    // Alanlar (Attributes)
    public String marka;
    public String model;
    public int yil;
    public int hiz;

    // Metot: Hızlanma davranışı
    public void hizlan(int artis) {
        hiz += artis;
        System.out.println(marka + " " + model + " hızlandı. Güncel hız: " + hiz + " km/s");
    }

    // Metot: Fren yapma davranışı
    public void frenYap(int azalis) {
        if (hiz - azalis >= 0) {
            hiz -= azalis;
        } else {
            hiz = 0;
        }
        System.out.println(marka + " " + model + " yavaşladı. Güncel hız: " + hiz + " km/s");
    }

    // Metot: Bilgileri ekrana yazdırma
    public void bilgileriYazdir() {
        System.out.println("----------------------------------");
        System.out.println("Araç Bilgisi: " + yil + " " + marka + " " + model);
        System.out.println("Mevcut Hız  : " + hiz + " km/s");
        System.out.println("----------------------------------");
    }
}
```

#### `Main.java`
```java
public class Main {
    public static void main(String[] args) {
        // 1. Nesneyi oluşturma (Araba 1)
        Araba araba1 = new Araba();
        araba1.marka = "Toyota";
        araba1.model = "Corolla";
        araba1.yil = 2022;
        araba1.hiz = 0;

        // 2. Nesneyi oluşturma (Araba 2)
        Araba araba2 = new Araba();
        araba2.marka = "Honda";
        araba2.model = "Civic";
        araba2.yil = 2024;
        araba2.hiz = 50;

        // Nesnelerin metotlarını çağırma
        araba1.bilgileriYazdir();
        araba1.hizlan(40);
        araba1.hizlan(30);
        araba1.frenYap(20);

        System.out.println(); // Boşluk

        araba2.bilgileriYazdir();
        araba2.hizlan(20);
        araba2.frenYap(80);
    }
}
```

#### Konsol Çıktısı:
```text
----------------------------------
Araç Bilgisi: 2022 Toyota Corolla
Mevcut Hız  : 0 km/s
----------------------------------
Toyota Corolla hızlandı. Güncel hız: 40 km/s
Toyota Corolla hızlandı. Güncel hız: 70 km/s
Toyota Corolla yavaşladı. Güncel hız: 50 km/s

----------------------------------
Araç Bilgisi: 2024 Honda Civic
Mevcut Hız  : 50 km/s
----------------------------------
Honda Civic hızlandı. Güncel hız: 70 km/s
Honda Civic yavaşladı. Güncel hız: 0 km/s
```

---

### Örnek 2: Erişim Belirleyicileri ile Veri Koruma

Bu örnekte `private`, `public` ve `protected` belirleyicilerinin birlikte nasıl kullanıldığını ve `private` alanların nasıl korunduğunu göreceğiz.

#### `BankaHesabi.java`
```java
public class BankaHesabi {
    // public: Hesap numarası dışarıdan okunabilir
    public String hesapNo;

    // private: Bakiye gizlidir! Doğrudan dışarıdan değiştirilemez
    private double bakiye;

    // protected: Hesap sahibi bilgisi miras alacak alt sınıflara açıktır
    protected String hesapSahibi;

    // Hesabı ilk açarken başlangıç bakiyesi yükleme
    public void hesapAc(String sahip, String no, double ilkBakiye) {
        hesapSahibi = sahip;
        hesapNo = no;
        if (ilkBakiye > 0) {
            bakiye = ilkBakiye;
        } else {
            bakiye = 0;
        }
    }

    // Para yatırma metodu (kontrollü işlem)
    public void paraYatir(double miktar) {
        if (miktar > 0) {
            bakiye += miktar;
            System.out.println(miktar + " TL başarıyla yatırıldı.");
        } else {
            System.out.println("Hata: Yatırılacak miktar pozitif olmalıdır!");
        }
    }

    // Para çekme metodu (kontrollü işlem)
    public void paraCek(double miktar) {
        if (miktar <= 0) {
            System.out.println("Hata: Çekilecek miktar pozitif olmalıdır!");
        } else if (miktar > bakiye) {
            System.out.println("Hata: Yetersiz bakiye! Mevcut bakiye: " + bakiye + " TL");
        } else {
            bakiye -= miktar;
            System.out.println(miktar + " TL çekildi. Kalan bakiye: " + bakiye + " TL");
        }
    }

    // Getter metodu: Bakiyeyi dışarıya güvenle okutur
    public double getBakiye() {
        return bakiye;
    }

    public void hesapOzeti() {
        System.out.println("--- HESAP ÖZETİ ---");
        System.out.println("Hesap Sahibi : " + hesapSahibi);
        System.out.println("Hesap No     : " + hesapNo);
        System.out.println("Bakiye       : " + bakiye + " TL");
        System.out.println("-------------------");
    }
}
```

#### `BankaTest.java`
```java
public class BankaTest {
    public static void main(String[] args) {
        BankaHesabi hesap = new BankaHesabi();
        hesap.hesapAc("Zeynep Kaya", "TR12345678", 1000.0);

        hesap.hesapOzeti();

        // Para yatıralım
        hesap.paraYatir(500);

        // Para çekmeyi deneyelim
        hesap.paraCek(300);

        // Bakiyeden fazlasını çekmeyi deneyelim
        hesap.paraCek(2000);

        // ⚠️ DİKKAT: Aşağıdaki satırı açarsanız derleme hatası alırsınız!
        // hesap.bakiye = 9999999; // HATA: bakiye has private access in BankaHesabi

        // Güvenli okuma yöntemi (Getter ile)
        System.out.println("Getter ile okunan güncel bakiye: " + hesap.getBakiye() + " TL");
    }
}
```

---

### Örnek 3: Birden Fazla Nesne ve Bağımsız Durumlar

Bu örnek, her nesnenin bellekte (Heap) tamamen bağımsız değişken kopyalarına sahip olduğunu kanıtlar.

#### `Ogrenci.java`
```java
public class Ogrenci {
    public String ad;
    public int numara;
    public double vizeNotu;
    public double finalNotu;

    // Ortalama hesaplama davranışı
    public double ortalamaHesapla() {
        return (vizeNotu * 0.4) + (finalNotu * 0.6);
    }

    // Durumu ekrana yazdırma
    public void durumYazdir() {
        double ort = ortalamaHesapla();
        System.out.print("[" + numara + "] " + ad + " - Ort: " + ort + " -> ");
        if (ort >= 50.0) {
            System.out.println("GEÇTİ ✅");
        } else {
            System.out.println("KALDI ❌");
        }
    }
}
```

#### `OkulMain.java`
```java
public class OkulMain {
    public static void main(String[] args) {
        // 1. Öğrenci
        Ogrenci ogr1 = new Ogrenci();
        ogr1.ad = "Ali Yılmaz";
        ogr1.numara = 201;
        ogr1.vizeNotu = 70;
        ogr1.finalNotu = 80;

        // 2. Öğrenci
        Ogrenci ogr2 = new Ogrenci();
        ogr2.ad = "Ayşe Demir";
        ogr2.numara = 202;
        ogr2.vizeNotu = 40;
        ogr2.finalNotu = 45;

        // Her nesne kendi durumunu saklar
        ogr1.durumYazdir();
        ogr2.durumYazdir();
    }
}
```

#### Konsol Çıktısı:
```text
[201] Ali Yılmaz - Ort: 76.0 -> GEÇTİ ✅
[202] Ayşe Demir - Ort: 43.0 -> KALDI ❌
```

---

## 6. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `NullPointerException` (Nesne Oluşturmadan Referans Kullanma)
Bir referans değişkeni tanımlayıp ona `new` ile nesne atamadan alanlarına erişirseniz programınız çöker:
```java
Ogrenci ogr; // null
ogr.ad = "Mehmet"; // ❌ RUNTIME ERROR: NullPointerException!
```
✅ **Çözüm:** Mutlaka `new` ile başlatın:
```java
Ogrenci ogr = new Ogrenci();
ogr.ad = "Mehmet"; // ✅ Sorunsuz çalışır.
```

### 2. `private` Alana Dışarıdan Doğrudan Erişmeye Çalışmak
```java
BankaHesabi b = new BankaHesabi();
b.bakiye = 5000; // ❌ COMPILE ERROR: bakiye has private access in BankaHesabi
```
✅ **Çözüm:** Değeri doğrudan atamak yerine `public` metotlar (`paraYatir()`, `setBakiye()`) kullanın.

### 3. Dosya Adı ile Sınıf Adının Farklı Olması
Eğer sınıfınız `public class Ogrenci` ise, dosyanızın adı harfi harfine **`Ogrenci.java`** olmalıdır.
- `ogrenci.java` ❌
- `Ogrenci.java` ✅

### 4. Bir Dosyada Birden Fazla `public class` Bulunması
Bir `.java` kaynak dosyasında **en fazla bir tane** `public class` bulunabilir ve dosya ismi o sınıfla aynı olmalıdır. Diğer sınıflar varsa ya varsayılan (`class DigerSinif { }`) tanımlanmalı ya da ayrı `.java` dosyalarına bölünmelidir.

### 5. Metot Çağrılarında Parantez `()` Unutulması
Metotlar parametre almasa bile çağrılırken parantezleri yazılmalıdır:
```java
ogr.durumYazdir;   // ❌ HATA: not a statement
ogr.durumYazdir(); // ✅ DOĞRU
```

---

## 7. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Kod Analizi ve Çıktı Tahmini
Aşağıdaki Java kodunu derlemeden inceleyiniz. Konsola ne yazdıracağını tahmin ediniz:

```java
class Dikdortgen {
    int en;
    int boy;

    int alanHesapla() {
        return en * boy;
    }

    int cevreHesapla() {
        return 2 * (en + boy);
    }
}

public class Test {
    public static void main(String[] args) {
        Dikdortgen d1 = new Dikdortgen();
        d1.en = 4;
        d1.boy = 5;

        Dikdortgen d2 = d1; // DİKKAT: Burada ne kopyalandı?
        d2.en = 10;

        System.out.println("d1 Alan : " + d1.alanHesapla());
        System.out.println("d2 Çevre: " + d2.cevreHesapla());
    }
}
```

<details>
<summary>🔍 <b>Çözümü ve Açıklamayı Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
```text
d1 Alan : 50
d2 Çevre: 30
```
**Açıklama:**
`Dikdortgen d2 = d1;` ifadesi Heap'te yeni bir dikdörtgen üretmez! Sadece `d1`'in tuttuğu nesne adresini `d2`'ye de verir. Yani hem `d1` hem de `d2` Heap bellekteki **aynı nesneye** işaret eder. Bu nedenle `d2.en = 10;` yapıldığında `d1.en` değeri de 10 olur. `d1.alanHesapla()` -> $10 \times 5 = 50$ hesaplanır.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kodda derleme veya çalışma anında hata verecek **3 noktayı** bulunuz ve düzeltiniz:

```java
public class Kisi {
    private String isim;
    private int yas;
}

public class Main {
    public static void main(String[] args) {
        Kisi k;
        k.isim = "Can";
        k.yas = 20;
        System.out.println(k.isim + " - " + k.yas);
    }
}
```

<details>
<summary>🔍 <b>Hataları ve Çözümü Görmek İçin Tıklayın</b></summary>

1. **Hata 1 (`Null / Başlatılmamış Değişken`):** `Kisi k;` tanımlanmış fakat `new Kisi()` ile nesne üretilmemiştir.
2. **Hata 2 & 3 (`private` Erişim Engeli):** `isim` ve `yas` alanları `private` olarak tanımlanmıştır. `Main` sınıfı içinden doğrudan `k.isim` veya `k.yas` yazılamaz.
3. **Düzeltilmiş Hali:**
   - `Kisi` sınıfına `public` getter/setter metotları veya `public` alanlar tanımlanmalı.
   - `Kisi k = new Kisi();` şeklinde başlatılmalıdır.
</details>

---

### Alıştırma 3: Küçük Bir Sınıf Tasarımı
Bir `AkilliTelefon` sınıfı oluşturmanız istenmektedir:
- Özellikler: `marka` (String), `model` (String), `pilYuzdesi` (int, 0 ile 100 arasında).
- Metotlar:
  - `fotoCek()`: Her fotoğraf çekildiğinde pil %5 azalmalı. Pil 5'ten küçükse "Pil yetersiz!" uyarısı vermeli.
  - `sarjEt(int miktar)`: Pili verilen miktar kadar artırmalı, ancak pil %100'ü geçememeli.
  - `durumGoster()`: Telefonun güncel pilini ekrana basmalı.
Bu sınıfı yazıp `main` metodunda test ediniz.

---

## 8. Ödev Konuları ve Mini Projeler

Öğrencilerin sınıf ve nesne yapısını kavramalarını pekiştirmek amacıyla seviyelere ayrılmış ödev projeleri aşağıdadır:

---

### 🟢 Ödev 1 (Temel Seviye): Kütüphane Kitap Takip Sistemi (`Kitap.java` & `KutuphaneTest.java`)

**Gereksinimler:**
1. `Kitap` adında bir sınıf oluşturunuz.
2. Sınıfın aşağıdaki nitelikleri olmalıdır:
   - `String baslik`
   - `String yazar`
   - `int sayfaSayisi`
   - `boolean oduncVerildiMi` (başlangıçta `false`)
3. Sınıfın aşağıdaki davranışları (metotları) olmalıdır:
   - `oduncAl()`: Eğer kitap ödünç verilmemişse `oduncVerildiMi` değerini `true` yapsın ve "Kitap ödünç verildi" mesajı bassın. Zaten ödünçteyse uyarı versin.
   - `iadeEt()`: Eğer kitap ödünçteyse `oduncVerildiMi` değerini `false` yapsın.
   - `bilgiGoster()`: Kitabın başlığını, yazarını, sayfa sayısını ve ödünç durumunu ("Rafta" veya "Ödünç Alındı") ekrana yazdırsın.
4. `KutuphaneTest` sınıfının `main` metodu içinde en az 2 farklı kitap oluşturup ödünç alma ve iade etme senaryolarını test ediniz.

---

### 🟡 Ödev 2 (Orta Seviye): Kapsüllemeli Öğrenci Not Sistemi (`OgrenciNot.java`)

**Gereksinimler:**
1. `OgrenciNot` adında bir sınıf tasarlayınız.
2. Değişkenler:
   - `private String adSoyad;`
   - `private int vizeNotu;` (0 ile 100 arasında olmalı)
   - `private int finalNotu;` (0 ile 100 arasında olmalı)
3. Metotlar:
   - `setAdSoyad(String ad)` ve `getAdSoyad()`
   - `setVizeNotu(int not)`: Not 0-100 aralığında değilse hata mesajı vermeli ve değeri değiştirmemelidir.
   - `setFinalNotu(int not)`: Not 0-100 aralığında değilse hata mesajı vermeli ve değeri değiştirmemelidir.
   - `ortalama()`: Vizenin %40'ını, finalin %60'ını alarak ortalamayı döndürmelidir.
   - `harfNotu()`: Ortalamaya göre `AA`, `BA`, `BB`, `CB`, `CC`, `DC`, `DD`, `FF` döndürmelidir.
4. Bir test sınıfı yazarak bilerek hatalı notlar (-10, 150 vb.) girmeye çalışınız ve sistemin hatalı veri girişini engellediğini konsolda gösteriniz.

---

### 🔴 Ödev 3 (İleri Seviye Mini Proje): ATM & Müşteri Hesap Yönetimi (`MusteriHesabi.java`)

**Gereksinimler:**
1. Bir `MusteriHesabi` sınıfı oluşturunuz.
2. Alanlar:
   - `private String iban;`
   - `private String musteriAdi;`
   - `private double bakiye;`
   - `private double gunlukCekilenToplam;`
   - `private final double GUNLUK_LIMIT = 5000.0;`
3. Fonksiyonellikler:
   - `paraYatir(double miktar)`: Pozitif miktarlar için bakiyeyi artırmalı.
   - `paraCek(double miktar)`:
     - Çekilmek istenen miktar pozitif mi?
     - Yeterli bakiye var mı?
     - Günlük çekim limiti ($5000\text{ TL}$) aşılıyor mu? Kontrolleri yapmalı; uygunsa parayı verip bakiyeyi ve günlük çekilen toplamı güncellemeli.
   - `gunuSifirla()`: Yeni bir güne geçildiğinde günlük çekilen toplamı 0 yapmalı.
   - `dekontYazdir()`: Güncel bakiyeyi, günlük kalan para çekme limitini ve müşteri bilgilerini düzenli bir fiş formatında yazdırmalıdır.
4. `AtmMain.java` dosyasında bir menü veya simülasyon akışıyla para yatırma, limit aşımı denemesi, başarılı çekim ve gün sonu işlemlerini simüle ediniz.
