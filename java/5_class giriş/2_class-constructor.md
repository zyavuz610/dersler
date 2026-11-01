# 2. Java'da Kurucu Metotlar (Constructors), Yıkıcı Mekanizmalar ve `this` Anahtar Kelimesi

Bu doküman; Java'da nesnelerin yaşam döngüsünün başlangıcı olan **Kurucu Metotlar (Constructors)**, nesnelerin bellekten temizlenmesini sağlayan **Yıkıcı Mekanizmalar (Garbage Collector & Resource Management)** ve nesnenin kendisine işaret eden **`this` anahtar kelimesi** konularını kapsamlı biçimde ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Sınıf ve Nesne Temelleri, Erişim Belirleyiciler (1_class-tanım.md)](./1_class-tanım.md)
> - 🔜 [Sonraki Bölüm: static Anahtar Kelimesi ve Sınıf Üyeleri (3_static-class.md)](./3_static-class.md)

---

## 📌 İçindekiler
1. [Kurucu Metot (Constructor) Nedir ve Neden Gerekir?](#1-kurucu-metot-constructor-nedir-ve-neden-gerekir)
   - [Nesnenin Doğum Anı ve İlk Değerleme (Initialization)](#nesnenin-doğum-anı-ve-ilk-değerleme-initialization)
   - [Kurucu Metot ile Standart Metot Arasındaki Farklar](#kurucu-metot-ile-standart-metot-arasındaki-farklar)
2. [Kurucu Metot Türleri](#2-kurucu-metot-türleri)
   - [Varsayılan Kurucu (Default Constructor)](#1-varsayılan-kurucu-default-constructor)
   - [Parametresiz Kurucu (No-Arg Constructor)](#2-parametresiz-kurucu-no-arg-constructor)
   - [Parametreli Kurucular (Parameterized Constructors)](#3-parametreli-kurucular-parameterized-constructors)
   - [Kurucu Metot Aşırı Yükleme (Constructor Overloading)](#4-kurucu-metot-aşırı-yükleme-constructor-overloading)
3. [`this` Anahtar Kelimesi ve Kullanım Alanları](#3-this-anahtar-kelimesi-ve-kullanım-alanları)
   - [Değişken Gölgeleme (Shadowing) Çözümü (`this.alan = parametre`)](#1-değişken-gölgeleme-shadowing-çözümü)
   - [Kurucu Zincirleme (Constructor Chaining - `this(...)`)](#2-kurucu-zincirleme-constructor-chaining---this)
   - [Metot Zincirleme (Method Chaining - `return this;`)](#3-metot-zincirleme-fluent-tasarım)
4. [Java'da Yıkıcı Metotlar (Destructors) ve Bellek Yönetimi](#4-javada-yıkıcı-metotlar-destructors-ve-bellek-yönetimi)
   - [C++ Destructor (`~Sinif()`) ile Java Farkı](#c-destructor-ile-java-farkı)
   - [Çöp Toplayıcı (Garbage Collector - GC) Mantığı](#çöp-toplayıcı-garbage-collector---gc-mantığı)
   - [Eski Yaklaşım: `finalize()` Metodu ve Neden Terk Edildi?](#eski-yaklaşım-finalize-metodu-ve-neden-terk-edildi)
   - [Modern Yaklaşım: `AutoCloseable` Arayüzü ve `close()` Metodu](#modern-yaklaşım-autocloseable-arayüzü-ve-close-metodu)
5. [Adım Adım Çalıştırılabilir Kod Örnekleri](#5-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Parametreli Kurucu ve Overloading (`Ogrenci.java` & `OgrenciTest.java`)](#örnek-1-parametreli-kurucu-ve-overloading)
   - [Örnek 2: `this(...)` ile Kurucu Zincirleme (`Kitap.java` & `KitapTest.java`)](#örnek-2-this-ile-kurucu-zincirleme)
   - [Örnek 3: Bellek Temizliği ve Kaynak Kapatma (`DosyaKaynagi.java` & `Main.java`)](#örnek-3-bellek-temizliği-ve-kaynak-kapatma)
6. [Sık Yapılan Hatalar ve Önemli Kurallar](#6-sık-yapılan-hatalar-ve-önemli-kurallar)
7. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#7-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
8. [Ödev Konuları ve Mini Projeler](#8-ödev-konuları-ve-mini-projeler)

---

## 1. Kurucu Metot (Constructor) Nedir ve Neden Gerekir?

Bir önceki dersimizde nesneler oluşturup alanlarına tek tek değer atamıştık:
```java
Araba a = new Araba();
a.marka = "Honda";
a.model = "Civic";
a.yil = 2023;
```

Bu yöntem iki büyük soruna yol açar:
1. **Unutulma Riski:** Geliştirici nesneyi üretip alanlarına değer atamayı unutursa, alanlar varsayılan (`null`, `0`, `false`) kalır ve program beklenmedik mantık hatalarına düşer.
2. **Kod Kalabalığı:** Her yeni nesne oluşturulduğunda satırlarca atama kodu yazmak gerekir.

İşte bu noktada **Kurucu Metot (Constructor)** devreye girer.

> 💡 **Tanım:** Kurucu metot, `new` anahtar kelimesi ile sınıftan bir nesne örneği üretildiği anda **otomatik olarak çalışan** özel bir bloktur. Temel görevi, nesnenin alanlarına **ilk değerlerini (initial state)** vermek ve nesneyi güvenli biçimde hayata başlatmaktır.

```java
// Kurucu metot sayesinde tek satırda temiz başlatma:
Araba a = new Araba("Honda", "Civic", 2023);
```

---

### Kurucu Metot ile Standart Metot Arasındaki Farklar

Bir kurucu metodu standart bir metottan ayıran **3 kesin kural** vardır:

| Özellik | Kurucu Metot (Constructor) | Standart Metot (Method) |
| :--- | :--- | :--- |
| **İsim Kuralı** | **Sınıf adı ile birebir aynı** olmak zorundadır. | İstenilen isim verilebilir (genelde `camelCase`). |
| **Dönüş Tipi** | **Dönüş tipi YOKTUR!** `void` dahi yazılamaz. | Mutlaka bir dönüş tipi (`int`, `String` veya `void`) vardır. |
| **Çağrılma Şekli** | Yalnızca nesne üretilirken `new` ile otomatik çağrılır. | Nesne referansı üzerinden nokta ile (`nesne.metot()`) çağrılır. |

> ⚠️ **KRİTİK UYARI:** Kurucu metodun başına kazara `void` yazarsanız (örneğin: `public void Ogrenci()`), Java bunu kurucu metot olarak görmez; normal bir metot zanneder. Derleme hatası vermez ancak nesne oluşturulurken otomatik çalışmaz!

---

## 2. Kurucu Metot Türleri

Java'da kurucu metotlar 4 farklı kullanım şekline sahiptir:

### 1. Varsayılan Kurucu (Default Constructor)
Eğer sınıfınızın içine **hiçbir kurucu metot yazmazsanız**, Java derleyicisi arka planda sizin için görünmez, parametresiz ve boş gövdeli bir kurucu üretir:

```java
public class Kalem {
    String renk;
    // Biz hiçbir kurucu yazmadık!
    // Java arka planda şunu üretir:
    // public Kalem() { }
}
```

> ⚠️ **Kayıp Varsayılan Kurucu Kuralı:** Eğer sınıfınıza **en az bir tane parametreli kurucu yazarsanız**, Java artık varsayılan kurucuyu **otomatik üretmeyi bırakır!** Eğer parametresiz kullanım da istiyorsanız, elinizle açıkça yazmanız gerekir.

---

### 2. Parametresiz Kurucu (No-Arg Constructor)
Bizim kodlayarak tanımladığımız, parametre almayan kurucudur. Genellikle varsayılan başlangıç değerleri vermek için kullanılır:

```java
public class Oyuncu {
    String takmaAd;
    int can;
    int seviye;

    // Parametresiz kurucu
    public Oyuncu() {
        takmaAd = "Misafir Oyuncu";
        can = 100;
        seviye = 1;
    }
}
```

---

### 3. Parametreli Kurucular (Parameterized Constructors)
Dışarıdan parametre alarak nesnenin alanlarına bu değerleri yükleyen kuruculardır:

```java
public class Oyuncu {
    String takmaAd;
    int can;
    int seviye;

    // Parametreli kurucu
    public Oyuncu(String ad, int baslangicCani, int baslangicSeviyesi) {
        takmaAd = ad;
        can = baslangicCani;
        seviye = baslangicSeviyesi;
    }
}
```

Kullanımı:
```java
Oyuncu p1 = new Oyuncu("Savaşçı_01", 150, 5);
```

---

### 4. Kurucu Metot Aşırı Yükleme (Constructor Overloading)
Bir sınıf içerisinde **aynı isimde (sınıf adında) birden fazla kurucu metot** bulunabilir. Buna **Constructor Overloading** denir.
Java, hangi kurucunun çalışacağını gönderilen **parametre sayısı, türü ve sırasına** bakarak anlar.

```java
public class Oyuncu {
    String takmaAd;
    int can;

    // 1. Kurucu: Parametresiz (Varsayılan değerlerle)
    public Oyuncu() {
        this.takmaAd = "Bilinmeyen";
        this.can = 100;
    }

    // 2. Kurucu: Sadece isim alır, canı 100 yapar
    public Oyuncu(String takmaAd) {
        this.takmaAd = takmaAd;
        this.can = 100;
    }

    // 3. Kurucu: Hem isim hem can alır
    public Oyuncu(String takmaAd, int can) {
        this.takmaAd = takmaAd;
        this.can = can;
    }
}
```

---

## 3. `this` Anahtar Kelimesi ve Kullanım Alanları

Java'da `this` anahtar kelimesi, **o an üzerinde çalışılan nesnenin kendisini** temsil eden bir referanstır. En yaygın 3 kullanım alanı vardır:

---

### 1. Değişken Gölgeleme (Shadowing) Çözümü
Kurucu metoda parametre olarak gelen değişkenin adıyla, sınıfın kendi alanının adı aynı olduğunda; parametre, sınıf alanını "gölgeler" (gizler).

```java
public class Ogrenci {
    String ad; // Sınıf alanı (Field)

    public Ogrenci(String ad) {
        // Hatalı Atama: ad = ad; (Parametre kendi kendine atanır, sınıf alanı boş kalır!)
        this.ad = ad; // ✅ this.ad -> Sınıf alanını, sağdaki ad -> Parametreyi temsil eder.
    }
}
```

---

### 2. Kurucu Zincirleme (Constructor Chaining - `this(...)`)
Bir kurucunun içinden aynı sınıftaki **başka bir kurucu metodu çağırmak** için `this(...)` kullanılır. Bu sayede kod tekrarı önlenir.

> 🔴 **ALTIN KURAL:** `this(...)` çağrısı, bulunduğu kurucu metodun **mutlaka ve mutlaka İLK SATIRINDA** yer almak zorundadır! Aksi halde derleme hatası (`call to this must be first statement in constructor`) alırsınız.

```java
public class Urun {
    String ad;
    double fiyat;
    int stok;

    // 1. Ana (Tam) Kurucu
    public Urun(String ad, double fiyat, int stok) {
        this.ad = ad;
        this.fiyat = fiyat;
        this.stok = stok;
    }

    // 2. Stok girilmezse varsayılan 0 olsun: Diğer kurucuyu çağırır
    public Urun(String ad, double fiyat) {
        this(ad, fiyat, 0); // 1. Kurucuya yönlendirir!
    }

    // 3. Hiçbir şey girilmezse: 2. Kurucuyu çağırır
    public Urun() {
        this("Tanımsız Ürün", 0.0, 0);
    }
}
```

---

### 3. Metot Zincirleme (Fluent Tasarım)
Bir metot `return this;` ifadesiyle üzerinde çalıştığı nesneyi geri döndürürse, metotlar arka arkaya zincirleme çağrılabilir:

```java
public class Siparis {
    private String yemek;
    private String icecek;

    public Siparis yemekSec(String y) {
        this.yemek = y;
        return this; // Nesnenin kendisini döndürür
    }

    public Siparis icecekSec(String i) {
        this.icecek = i;
        return this;
    }
}

// Kullanım:
Siparis s = new Siparis();
s.yemekSec("Pizza").icecekSec("Kola"); // Zincirleme çağrı!
```

---

## 4. Java'da Yıkıcı Metotlar (Destructors) ve Bellek Yönetimi

C++ gibi alt seviye diller öğrenen öğrencilerin en sık sorduğu soru şudur:
> *"C++ dilinde `~Sinif()` şeklinde yıkıcı metot (destructor) vardı. Nesne bellekten silinirken çalışıyordu. Java'da bu nasıl yapılıyor?"*

### C++ Destructor ile Java Farkı

```
+-------------------------------------------------------------+
| C++ Yaklaşımı: Manuel Bellek Yönetimi                       |
|   new Araba()  ---> Nesne belleğe alınır.                   |
|   delete a;    ---> ~Araba() YIKICI METOT ÇALIŞIR & SİLİNİR |
+-------------------------------------------------------------+

+-------------------------------------------------------------+
| Java Yaklaşımı: Otomatik Çöp Toplayıcı (Garbage Collector)  |
|   new Araba()  ---> Nesne Heap belleğe alınır.              |
|   Referans yok ---> GC nesneyi tespit eder ve belleği siler |
|   (Geliştirici delete yazamaz, belleği doğrudan silemez!)   |
+-------------------------------------------------------------+
```

Java'da nesnelerin yok edilmesi geliştiricinin elinde değildir. Bu işi arka planda bağımsız bir iş parçacığı (daemon thread) olarak çalışan **Garbage Collector (Çöp Toplayıcı)** yürütür.

---

### Çöp Toplayıcı (Garbage Collector - GC) Mantığı

Bir nesne ne zaman "çöp" (garbage) haline gelir?
Bir nesneye işaret eden **hiçbir canlı referans (işaretçi) kalmadığında**, nesne Heap bellekte öksüz (unreachable) duruma düşer.

```java
Araba a1 = new Araba("Toyota");
a1 = null; // Eski nesneye artık kimse ulaşamaz -> GC için çöptür!

Araba a2 = new Araba("BMW");
Araba a3 = new Araba("Audi");
a2 = a3; // "BMW" nesnesi referanssız kaldı -> GC için çöptür!
```

`System.gc()` komutu ile JVM'e çöp toplayıcıyı çalıştırması için ricada bulunabilirsiniz; fakat JVM'in bunu hemen çalıştırıp çalıştırmayacağı garanti değildir.

---

### Eski Yaklaşım: `finalize()` Metodu ve Neden Terk Edildi?

Java'nın ilk sürümlerinde `Object` sınıfından gelen `protected void finalize()` metodu bulunuyordu. Sözde nesne GC tarafından silinmeden hemen önce çalışacaktı.

**Neden Kullanımdan Kaldırıldı (Deprecated)?**
1. **Zaman Garantisi Yok:** GC'nin ne zaman çalışacağı, hatta program kapanana kadar hiç çalışıp çalışmayacağı belirsizdir.
2. **Performans Sorunları:** JVM üzerinde ciddi yavaşlamalara ve bellek sızıntılarına yol açtı.
3. Bu nedenle **Java 9 ile birlikte `finalize()` resmi olarak "deprecated" (kullanımdan kaldırılmış)** ilan edilmiştir ve kullanılmamalıdır.

---

### Modern Yaklaşım: `AutoCloseable` Arayüzü ve `close()` Metodu

Peki bir nesne yok edilirken dosya kapatma, veritabanı bağlantısını kesme veya ağ soketini sonlandırma gibi kritik "temizlik" işlemlerini nasıl yaparız?

Modern Java'da standart yöntem **`AutoCloseable`** arayüzünü (interface) uygulamak ve **`close()`** metodunu yazmaktır:

```java
public class DosyaYonetici implements AutoCloseable {
    public void dosyaOku() {
        System.out.println("Dosyadan veri okunuyor...");
    }

    // Nesnenin işi bittiğinde çağrılacak yıkıcı/temizleyici metot:
    @Override
    public void close() {
        System.out.println("Dosya güvenli bir şekilde kapatıldı ve kaynaklar serbest bırakıldı.");
    }
}
```

Bu yapı `try-with-resources` bloğu ile kullanıldığında, blok bittiği an `close()` metodu **otomatik olarak** çalıştırılır:

```java
try (DosyaYonetici dy = new DosyaYonetici()) {
    dy.dosyaOku();
} // <-- Blok bittiği an close() metodu kendiliğinden tetiklenir!
```

---

## 5. Adım Adım Çalıştırılabilir Kod Örnekleri

Aşağıdaki örnekleri bilgisayarınızda derleyip çalıştırabilirsiniz.

---

### Örnek 1: Parametreli Kurucu ve Overloading

#### `Ogrenci.java`
```java
public class Ogrenci {
    private String ad;
    private int numara;
    private String bolum;

    // 1. Kurucu: Tam Parametreli
    public Ogrenci(String ad, int numara, String bolum) {
        this.ad = ad;
        this.numara = numara;
        this.bolum = bolum;
        System.out.println(">> Tam parametreli kurucu çalıştı: " + ad);
    }

    // 2. Kurucu: Bölüm bilinmiyorsa "Hazırlık" sayılsın (Overloading)
    public Ogrenci(String ad, int numara) {
        this.ad = ad;
        this.numara = numara;
        this.bolum = "Hazırlık Sınıfı";
        System.out.println(">> 2 parametreli kurucu çalıştı: " + ad);
    }

    // Bilgileri yazdırma metodu
    public void bilgileriYazdir() {
        System.out.println("Öğrenci: " + ad + " | No: " + numara + " | Bölüm: " + bolum);
    }
}
```

#### `OgrenciTest.java`
```java
public class OgrenciTest {
    public static void main(String[] args) {
        // Farklı kurucularla nesneler oluşturalım:
        Ogrenci ogr1 = new Ogrenci("Mert Yılmaz", 1001, "Bilgisayar Mühendisliği");
        Ogrenci ogr2 = new Ogrenci("Elif Şahin", 1002);

        System.out.println("\n--- Kayıtlı Öğrenci Listesi ---");
        ogr1.bilgileriYazdir();
        ogr2.bilgileriYazdir();
    }
}
```

#### Konsol Çıktısı:
```text
>> Tam parametreli kurucu çalıştı: Mert Yılmaz
>> 2 parametreli kurucu çalıştı: Elif Şahin

--- Kayıtlı Öğrenci Listesi ---
Öğrenci: Mert Yılmaz | No: 1001 | Bölüm: Bilgisayar Mühendisliği
Öğrenci: Elif Şahin | No: 1002 | Bölüm: Hazırlık Sınıfı
```

---

### Örnek 2: `this(...)` ile Kurucu Zincirleme

Bu örnekte, kurucuların birbirini nasıl çağırdığını (`this(...)`) ve kod tekrarını nasıl önlediğimizi göreceğiz.

#### `Kitap.java`
```java
public class Kitap {
    private String baslik;
    private String yazar;
    private double fiyat;

    // 1. Ana Kurucu (Tüm alanları dolduran tek yer)
    public Kitap(String baslik, String yazar, double fiyat) {
        this.baslik = baslik;
        this.yazar = yazar;
        this.fiyat = fiyat;
        System.out.println("Ana Kurucu Devrede: [" + baslik + "]");
    }

    // 2. İki Parametreli Kurucu: Fiyat bilinmiyorsa 0.0 gönder
    public Kitap(String baslik, String yazar) {
        this(baslik, yazar, 0.0); // 1. Kurucuyu çağırır (İlk satırda olmalı!)
    }

    // 3. Tek Parametreli Kurucu: Yazar bilinmiyorsa "Anonim" gönder
    public Kitap(String baslik) {
        this(baslik, "Anonim", 0.0); // 1. Kurucuyu çağırır
    }

    public void kartYazdir() {
        System.out.println("Kitap: \"" + baslik + "\" | Yazar: " + yazar + " | Fiyat: " + fiyat + " TL");
    }
}
```

#### `KitapTest.java`
```java
public class KitapTest {
    public static void main(String[] args) {
        Kitap k1 = new Kitap("Suç ve Ceza", "Dostoyevski", 120.0);
        Kitap k2 = new Kitap("Nutuk", "Mustafa Kemal Atatürk");
        Kitap k3 = new Kitap("Dede Korkut Hikayeleri");

        System.out.println("\n--- Kütüphane Kartları ---");
        k1.kartYazdir();
        k2.kartYazdir();
        k3.kartYazdir();
    }
}
```

#### Konsol Çıktısı:
```text
Ana Kurucu Devrede: [Suç ve Ceza]
Ana Kurucu Devrede: [Nutuk]
Ana Kurucu Devrede: [Dede Korkut Hikayeleri]

--- Kütüphane Kartları ---
Kitap: "Suç ve Ceza" | Yazar: Dostoyevski | Fiyat: 120.0 TL
Kitap: "Nutuk" | Yazar: Mustafa Kemal Atatürk | Fiyat: 0.0 TL
Kitap: "Dede Korkut Hikayeleri" | Yazar: Anonim | Fiyat: 0.0 TL
```

---

### Örnek 3: Bellek Temizliği ve Kaynak Kapatma

Bu örnekte nesnenin işi bittiğinde kaynakların `AutoCloseable` ile güvenle nasıl serbest bırakıldığını göreceğiz.

#### `VeritabaniBaglantisi.java`
```java
public class VeritabaniBaglantisi implements AutoCloseable {
    private String veritabaniAdi;

    public VeritabaniBaglantisi(String veritabaniAdi) {
        this.veritabaniAdi = veritabaniAdi;
        System.out.println(">> [" + veritabaniAdi + "] veritabanına BAĞLANTI AÇILDI. (Kaynak rezerve edildi)");
    }

    public void sorguCalistir(String sql) {
        System.out.println("Sorgu yürütülüyor: " + sql);
    }

    // Yıkıcı/Kapatıcı Metot
    @Override
    public void close() {
        System.out.println("<< [" + veritabaniAdi + "] veritabanı BAĞLANTISI KAPATILDI. (Bellek/port serbest)");
    }
}
```

#### `BaglantiTest.java`
```java
public class BaglantiTest {
    public static void main(String[] args) {
        System.out.println("--- try-with-resources Başlangıcı ---");

        // try parantezi içinde açılan kaynak, blok bitiminde otomatik close() edilir
        try (VeritabaniBaglantisi baglanti = new VeritabaniBaglantisi("OkulDB")) {
            baglanti.sorguCalistir("SELECT * FROM Ogrenciler");
            System.out.println("İşlemler tamamlandı, bloktan çıkılıyor...");
        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        }

        System.out.println("--- try-with-resources Bitişi ---");
    }
}
```

#### Konsol Çıktısı:
```text
--- try-with-resources Başlangıcı ---
>> [OkulDB] veritabanına BAĞLANTI AÇILDI. (Kaynak rezerve edildi)
Sorgu yürütülüyor: SELECT * FROM Ogrenciler
İşlemler tamamlandı, bloktan çıkılıyor...
<< [OkulDB] veritabanı BAĞLANTISI KAPATILDI. (Bellek/port serbest)
--- try-with-resources Bitişi ---
```

---

## 6. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. Kurucu Metoda `void` Yazmak (Büyük Tuzak!)
```java
public class Araba {
    // ❌ HATA: void yazıldığı an bu artık bir kurucu değil, sıradan bir metottur!
    public void Araba() {
        System.out.println("Araba üretildi");
    }
}

// Main içinde:
Araba a = new Araba(); // Ekrana hiçbir şey basmaz! Çünkü Java gizli boş kurucuyu çalıştırır.
```
✅ **Çözüm:** Kurucu metotların **hiçbir dönüş tipi** olmaz:
```java
public Araba() { // ✅ Doğru
    System.out.println("Araba üretildi");
}
```

---

### 2. Parametreli Kurucu Yazınca Varsayılan Kurucuyu Aramak
```java
public class Kedi {
    String ad;
    public Kedi(String ad) {
        this.ad = ad;
    }
}

// Main içinde:
Kedi k = new Kedi(); // ❌ DERLEME HATASI: constructor Kedi in class Kedi cannot be applied to given types
```
✅ **Çözüm:** Parametreli kurucu yazıldığında Java boş kurucuyu üretmeyi bırakır. Eğer `new Kedi()` şeklinde de nesne oluşturmak istiyorsanız, elinizle `public Kedi() { }` yazmalısınız.

---

### 3. `this(...)` Çağrısını İlk Satıra Koymamak
```java
public Urun(String ad) {
    System.out.println("Nesne oluşturuluyor...");
    this(ad, 0.0); // ❌ DERLEME HATASI: call to this must be first statement in constructor
}
```
✅ **Çözüm:** `this(...)` çağrısı istisnasız **ilk satırda** olmalıdır:
```java
public Urun(String ad) {
    this(ad, 0.0); // ✅ İlk satırda
    System.out.println("Nesne oluşturuldu.");
}
```

---

### 4. Döngüsel Kurucu Çağrısı (Infinite Recursion)
Bir kurucunun doğrudan veya dolaylı olarak kendisini çağırması yasaktır:
```java
public class Test {
    public Test() {
        this(5);
    }
    public Test(int x) {
        this(); // ❌ HATA: Recursive constructor invocation
    }
}
```

---

## 7. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Kod Okuma ve Çalışma Sırası Analizi
Aşağıdaki program çalıştırıldığında konsol çıktısı tam olarak ne olur? Sırayı adım adım takip ediniz:

```java
class A {
    public A() {
        this(10);
        System.out.println("1. Parametresiz A Kurucusu");
    }

    public A(int x) {
        this(x, 20);
        System.out.println("2. Tek Parametreli A Kurucusu: " + x);
    }

    public A(int x, int y) {
        System.out.println("3. İki Parametreli A Kurucusu: " + (x + y));
    }
}

public class ZincirTest {
    public static void main(String[] args) {
        A nesne = new A();
    }
}
```

<details>
<summary>🔍 <b>Çözümü ve Açıklamayı Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
```text
3. İki Parametreli A Kurucusu: 30
2. Tek Parametreli A Kurucusu: 10
1. Parametresiz A Kurucusu
```
**Açıklama:**
1. `new A()` çağrılır -> İlk satırda `this(10)` çağrılır (beklemeye geçer).
2. `A(int x)` kurucusu açılır -> İlk satırda `this(10, 20)` çağrılır (beklemeye geçer).
3. `A(int x, int y)` kurucusu açılır -> İlk olarak bu kurucunun gövdesi biter ve `3. İki Parametreli A Kurucusu: 30` basılır.
4. Geriye dönülür ve `A(10)` tamamlanır: `2. Tek Parametreli A Kurucusu: 10` basılır.
5. En son ilk çağrılan `A()` tamamlanır: `1. Parametresiz A Kurucusu` basılır.
*(Çağrı yığını / Stack mantığı!)*
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçalarında 3 adet derleme hatası vardır. Hataları bulunuz:

```java
class Bilgisayar {
    private String marka;
    private int ram;

    public void Bilgisayar(String marka, int ram) {
        this.marka = marka;
        this.ram = ram;
    }

    public Bilgisayar(String marka) {
        this.marka = marka;
        this(marka, 8);
    }
}

public class Main {
    public static void main(String[] args) {
        Bilgisayar b = new Bilgisayar();
    }
}
```

<details>
<summary>🔍 <b>Hataları ve Çözümü Görmek İçin Tıklayın</b></summary>

1. **Hata 1 (`void` yazılmış):** `public void Bilgisayar(...)` bir kurucu değil sıradan metottur. Başındaki `void` silinmelidir.
2. **Hata 2 (`this` ilk satırda değil):** İkinci kurucuda `this(marka, 8);` ifadesinden önce `this.marka = marka;` yazılmıştır. `this(...)` mutlaka ilk satırda olmalıdır.
3. **Hata 3 (Varsayılan kurucu yok):** `main` içinde `new Bilgisayar()` çağrılmıştır; ancak sınıfta parametresiz kurucu tanımlı değildir.
</details>

---

### Alıştırma 3: Küçük Tasarım (Banka Kartı)
Aşağıdaki isterlere göre bir `BankaKarti` sınıfı yazınız:
- Alanlar: `kartNo` (String), `sahip` (String), `cvv` (int), `bakiye` (double).
- Kurucu 1: Tüm alanları (`kartNo`, `sahip`, `cvv`, `bakiye`) alsın.
- Kurucu 2: `bakiye` verilmezse `this(...)` kullanarak bakiyeyi `0.0` olarak ilk kurucuya paslasın.
- Metot: `bilgiYazdir()` ile kart bilgilerini ekrana yazdırınız (güvenlik için CVV'yi `***` yazdırınız).

---

## 8. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Akıllı Saat Tasarımı (`AkilliSaat.java` & `SaatTest.java`)

**Gereksinimler:**
1. `AkilliSaat` adında bir sınıf tasarlayınız.
2. Nitelikler: `String marka`, `String model`, `int pilYuzdesi`, `int adimSayisi`.
3. Kurucu Metotlar (Constructor Overloading):
   - **Kurucu 1 (Boş):** Hiçbir parametre almasın; `this(...)` ile markayı "Genel", modeli "V1", pili 100 ve adımı 0 olarak başlatsın.
   - **Kurucu 2 (Kısmi):** Sadece `marka` ve `model` alsın; pil=100, adim=0 olsun.
   - **Kurucu 3 (Tam):** 4 alanın hepsini alsın (`this.marka = marka` vb.).
4. Davranışlar:
   - `adimAt(int adim)`: Adım sayısını artırsın ve her 1000 adımda pili %1 azaltsın.
   - `ekranGoster()`: Saatin tüm güncel durumunu terminale yazdırsın.
5. `SaatTest.java` dosyasında 3 farklı kurucuyla da birer nesne üretip adımları test ediniz.

---

### 🟡 Ödev 2 (Orta Seviye): Otel Rezervasyon Sistemi (`Rezervasyon.java`)

**Gereksinimler:**
1. `Rezervasyon` sınıfı oluşturunuz.
2. Alanlar:
   - `private String musteriAdi;`
   - `private int geceSayisi;`
   - `private String odaTipi;` ("Standart", "Deluxe", "Suit")
   - `private double toplamUcret;`
3. Kurucu Zincirleme:
   - Standart oda gecelik 1000 TL, Deluxe 2000 TL, Suit 3500 TL olsun.
   - Kullanıcı oda tipini belirtmezse varsayılan olarak "Standart" kabul edilmelidir (Kurucu zincirleme `this(...)` kullanarak).
   - Kurucu çalışırken müşterinin `toplamUcret` tutarını `geceSayisi * odaFiyati` formülüyle otomatik hesaplayıp alana atamalıdır.
4. Kurallara Uygunluk:
   - Gece sayısı 0 veya negatif girilirse uyarı basıp gece sayısını en az 1 yapmalıdır.
5. Rezervasyon bilgilerini ve toplam faturayı gösteren `faturaBas()` metodunu yazıp test ediniz.

---

### 🔴 Ödev 3 (İleri Seviye): Güvenli Kasa ve Kaynak Temizliği Simülasyonu (`CelikKasa.java`)

**Gereksinimler:**
1. `CelikKasa` sınıfı `AutoCloseable` arayüzünü uygulasın (`implements AutoCloseable`).
2. Alanlar:
   - `private String kasaNo;`
   - `private int sifre;`
   - `private boolean acikMi;`
   - `private double nakitIcerik;`
3. Kurucu:
   - `public CelikKasa(String kasaNo, int sifre, double baslangicPara)`
   - Kurucu çalıştığında kasa kapalı (`acikMi = false`) başlamalıdır.
4. Metotlar:
   - `kasaAc(int girilenSifre)`: Şifre doğruysa kasayı açar (`acikMi = true`), yanlışsa uyarı verir.
   - `paraEkle(double miktar)`: Sadece kasa açıksa para ekler; kapalıysa "Kasa kapalı, işlem yapılamaz!" hatası verir.
   - `paraCek(double miktar)`: Sadece kasa açıksa ve bakiye yetiyorsa para çeker.
   - `@Override public void close()`: Kasanın otomatik olarak kilitlenmesini sağlar (`acikMi = false`) ve "Kasa güvenli bir şekilde KİLİTLENDİ, oturum sonlandırıldı." mesajı basar.
5. `KasaTest.java` içinde **`try-with-resources`** yapısını kullanarak kasayı açınız, para ekleyip çekiniz ve blok bittiğinde kasanın kendiliğinden kilitlendiğini konsol çıktısıyla kanıtlayınız.

