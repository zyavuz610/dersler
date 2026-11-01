# 6. Java'da Nesne Dizileri (Arrays of Objects) ve Dizi İşlemleri

Bu doküman; Java'da aynı sınıftan üretilen birden fazla nesneyi bir arada tutmayı sağlayan **Nesne Dizilerini (Arrays of Objects)**, nesne dizilerinin **iki aşamalı başlatma mantığını**, bellek yerleşimini (Stack & Heap), nesne dizileri üzerinde döngülerle gezinmeyi, **nesne dizilerini metotlara parametre olarak göndermeyi** ve **metotlardan nesne dizisi döndürmeyi** ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Sınıflar, Fonksiyonlar ve Nesne Etkileşimi (5_class_ve_fonksiyonlar.md)](./5_class_ve_fonksiyonlar.md)
> - 🔜 [Sonraki Bölüm: Kompozisyon (HAS-A İlişkisi) ve İç Sınıflar (7_composition.md)](./7_composition.md)

---

## 📌 İçindekiler
1. [Nesne Dizisi (Array of Objects) Nedir?](#1-nesne-dizisi-array-of-objects-nedir)
   - [İlkel Tip Dizisi (`int[]`) ile Nesne Dizisi (`Ogrenci[]`) Farkı](#ilkel-tip-dizisi-ile-nesne-dizisi-farkı)
   - [Gerçek Dünya Analojisi: Boş Otopark Parselleri](#gerçek-dünya-analojisi-boş-otopark-parselleri)
   - [Stack ve Heap Bellek Mimarisi](#stack-ve-heap-bellek-mimarisi)
2. [Nesne Dizisi Tanımlama ve İki Aşamalı Başlatma](#2-nesne-dizisi-tanımlama-ve-iki-aşamalı-başlatma)
   - [1. Aşama: Referans Dizisini Ayırmak (`new Ogrenci[n]`)](#1-aşama-referans-dizisini-ayırmak)
   - [2. Aşama: Her Bir Elemanı Tek Tek Üretmek (`new Ogrenci(...)`)](#2-aşama-her-bir-elemanı-tek-tek-üretmek)
   - [Kısa Yoldan Başlatma (Array Initializer `{ ... }`)](#kısa-yoldan-başlatma-array-initializer)
3. [Nesne Dizilerinde Gezinme (Döngüler)](#3-nesne-dizilerinde-gezinme-döngüler)
   - [Klasik İndeksli `for` Döngüsü](#klasik-indeksli-for-döngüsü)
   - [Gelişmiş `for-each` Döngüsü](#gelişmiş-for-each-döngüsü)
   - [Hayati Önlem: `null` Kontrolü](#hayati-önlem-null-kontrolü)
4. [Nesne Dizilerini Metotlara Parametre Olarak Göndermek](#4-nesne-dizilerini-metotlara-parametre-olarak-göndermek)
   - [Metot İçi Dizi Güncellemelerinin Çağırana Etkisi](#metot-içi-dizi-güncellemelerinin-çağırana-etkisi)
   - [Değişken Sayıda Nesne Alma: Varargs (`Sinif... dizi`)](#değişken-sayıda-nesne-alma-varargs)
5. [Metotlardan Nesne Dizisi Döndürmek (Returning Array of Objects)](#5-metotlardan-nesne-dizisi-döndürmek-returning-array-of-objects)
   - [Filtreleme Senaryosu: Şartı Sağlayan Nesneleri Dizi Olarak Döndürme](#filtreleme-senaryosu-şartı-sağlayan-nesneleri-dizi-olarak-döndürme)
6. [Nesne Dizileri Üzerinde Temel Algoritmalar](#6-nesne-dizileri-üzerinde-temel-algoritmalar)
   - [1. Arama (Linear Search - Nesne Bulma)](#1-arama-linear-search)
   - [2. En Büyük / En Küçük Nesneyi Bulma (Max / Min)](#2-en-büyük--en-küçük-nesneyi-bulma-max--min)
   - [3. İstatistik ve Ortalama Hesaplama](#3-istatistik-ve-ortalama-hesaplama)
7. [Adım Adım Çalıştırılabilir Kod Örnekleri](#7-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Temel Nesne Dizisi ve For-Each Gezinimi (`Kitap.java` & `KutuphaneTest.java`)](#örnek-1-temel-nesne-dizisi-ve-for-each-gezinimi)
   - [Örnek 2: Diziyi Parametre Alan ve Dizi Döndüren Metotlar (`Ogrenci.java` & `SinifYonetimi.java`)](#örnek-2-diziyi-parametre-alan-ve-dizi-döndüren-metotlar)
   - [Örnek 3: Mağaza Stok Arama ve En Pahalı Ürünü Bulma (`Urun.java` & `MagazaTest.java`)](#örnek-3-mağaza-stok-arama-ve-en-pahalı-ürünü-bulma)
8. [Sık Yapılan Hatalar ve Önemli Kurallar](#8-sık-yapılan-hatalar-ve-önemli-kurallar)
9. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#9-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
10. [Ödev Konuları ve Mini Projeler](#10-ödev-konuları-ve-mini-projeler)

---

## 1. Nesne Dizisi (Array of Objects) Nedir?

Java'da tek bir tamsayı için `int sayi`, birden fazla tamsayı için `int[] sayilar` kullanıyorduk.
Aynı mantıkla tek bir öğrenci nesnesi için `Ogrenci ogr`, birden çok öğrenci nesnesini tek bir değişkende toplamak için **`Ogrenci[] ogrenciler`** kullanılır.

### İlkel Tip Dizisi ile Nesne Dizisi Farkı

| Özellik | İlkel Tip Dizisi (`int[]`) | Nesne Dizisi (`Ogrenci[]`) |
| :--- | :--- | :--- |
| **Dizinin İçinde Saklanan** | Sayısal verilerin doğrudan kendisi ($10, 25, 40$) | Nesnelerin Heap bellekteki **referans adresleri** |
| **İlk Ayrıldığındaki Varsayılan Değer** | Sayılar için `0`, boolean için `false` | Tüm elemanlar **`null`** olur! |
| **Nesne Üretim Zorunluluğu** | `new int[5]` demek yeterlidir | `new Ogrenci[5]` dedikten sonra her göze ayrıca `new Ogrenci()` yapılmalıdır! |

---

### Gerçek Dünya Analojisi: Boş Otopark Parselleri

```
1. ADIM: Otopark Alanı Tahsis Edildi (new Araba[3])
+--------------------+--------------------+--------------------+
|  [0] = null        |  [1] = null        |  [2] = null        |
+--------------------+--------------------+--------------------+
(Yerler hazır, fakat henüz otoparkta hiçbir araba yok!)

2. ADIM: Arabalar Park Edildi (otopark[0] = new Araba("Toyota"); ...)
+--------------------+--------------------+--------------------+
|  [0] = 0xAA11      |  [1] = 0xBB22      |  [2] = 0xCC33      |
+--------------------+--------------------+--------------------+
          |                    |                    |
          v                    v                    v
    [Toyota Nesnesi]      [BMW Nesnesi]       [Tesla Nesnesi]
```

- `new Araba[3]` dediğinizde bir fabrika kurup 3 tane araba üretmiş **olmazsınız**. Sadece 3 arabanın park edebileceği **3 adet boş park yeri (referans alanı)** ayırırsınız.
- Park yerleri başlangıçta boştur (`null`). Arabaları tek tek satın alıp (`new Araba(...)`) bu yerlere park etmeniz gerekir.

---

### Stack ve Heap Bellek Mimarisi

```
[STACK BELLEK]                 [HEAP BELLEK]
ogrenciler (Referans) --------> [0] : 0x111  --------> [ Ogrenci: "Ali", 85 ]
                                [1] : 0x222  --------> [ Ogrenci: "Ayşe", 95 ]
                                [2] : null   --------> (Henüz nesne yok!)
```

---

## 2. Nesne Dizisi Tanımlama ve İki Aşamalı Başlatma

### 1. Aşama: Referans Dizisini Ayırmak
Dizinin boyutunu belirterek referans alanlarını oluştururuz:
```java
Ogrenci[] sinif = new Ogrenci[3]; // 3 elemanlı dizi: [null, null, null]
```
> ⚠️ Eğer burada durup `sinif[0].bilgiYazdir()` yazmaya kalkarsanız Java çöker: **`NullPointerException`**! Çünkü `sinif[0]` şu an `null`'dır.

---

### 2. Aşama: Her Bir Elemanı Tek Tek Üretmek
Dizinin her bir indeksine `new` ile gerçek nesneleri yerleştiririz:
```java
sinif[0] = new Ogrenci("Ali Yılmaz", 101);
sinif[1] = new Ogrenci("Zeynep Kaya", 102);
sinif[2] = new Ogrenci("Burak Demir", 103);
```
Artık `sinif[0].bilgiYazdir()` güvenle çağrılabilir.

---

### Kısa Yoldan Başlatma (Array Initializer)
Eğer nesnelerin ilk değerleri kod yazılırken belliyse, tek satırda süslü parantez `{ ... }` ile hem dizi hem de nesneler üretilebilir:

```java
Ogrenci[] sinif = {
    new Ogrenci("Ali Yılmaz", 101),
    new Ogrenci("Zeynep Kaya", 102),
    new Ogrenci("Burak Demir", 103)
};
```

---

## 3. Nesne Dizilerinde Gezinme (Döngüler)

### Klasik İndeksli `for` Döngüsü
Dizinin indeks numarasına ihtiyaç duyulduğunda veya dizi elemanlarına yeni nesne atanırken kullanılır:

```java
for (int i = 0; i < sinif.length; i++) {
    System.out.println((i + 1) + ". Öğrenci:");
    sinif[i].bilgiYazdir();
}
```

---

### Gelişmiş `for-each` Döngüsü
Sadece nesneleri okumak ve metotlarını çağırmak için en temiz, en okunaklı yoldur:

```java
for (Ogrenci ogr : sinif) {
    ogr.bilgiYazdir();
}
```

---

### Hayati Önlem: `null` Kontrolü

Dizinin bazı indeksleri henüz doldurulmamış olabilir (örneğin 10 kişilik kontenjan açtınız ama sadece 3 öğrenci kaydoldu):

```java
for (Ogrenci ogr : sinif) {
    if (ogr != null) { // ✅ NullPointerException engellenir!
        ogr.bilgiYazdir();
    }
}
```

---

## 4. Nesne Dizilerini Metotlara Parametre Olarak Göndermek

Bir nesne dizisi, standart bir parametre gibi metotlara aktarılabilir.

```java
public static void ogrencileriListele(Ogrenci[] ogrenciDizisi) {
    for (Ogrenci ogr : ogrenciDizisi) {
        if (ogr != null) {
            System.out.println(ogr.getAd() + " - Not: " + ogr.getNot());
        }
    }
}
```

### Metot İçi Dizi Güncellemelerinin Çağırana Etkisi
Java'da nesne dizileri de birer referans nesnesidir. Metot içinde dizi elemanlarının alanlarını değiştirirseniz (örneğin herkese 10 puan eklemek), **çağıran yerdeki orijinal dizi elemanları da güncellenir!**

```java
public static void herkeseBonusPuanVer(Ogrenci[] liste, int bonus) {
    for (Ogrenci ogr : liste) {
        if (ogr != null) {
            ogr.setNot(ogr.getNot() + bonus); // Orijinal nesne güncellenir!
        }
    }
}
```

---

### Değişken Sayıda Nesne Alma: Varargs (`Sinif... dizi`)

Java'da **Varargs (`...`)** sözdizimi sayesinde bir metot hem dizi alabilir hem de virgülle ayrılmış birden çok bağımsız nesne alabilir:

```java
// Ogrenci... ifadesi arka planda Ogrenci[] dizisine dönüşür
public static void topluKayit(Ogrenci... ogrenciler) {
    System.out.println("Gelen öğrenci sayısı: " + ogrenciler.length);
    for (Ogrenci o : ogrenciler) {
        System.out.println("Kayıt yapıldı: " + o.getAd());
    }
}

// Kullanım alternatifleri:
topluKayit(ogr1);                    // 1 nesne
topluKayit(ogr1, ogr2, ogr3);        // 3 nesne
topluKayit(ogrenciDizisi);           // Doğrudan dizi!
```

---

## 5. Metotlardan Nesne Dizisi Döndürmek (Returning Array of Objects)

Bir metot, kendi içinde oluşturduğu veya filtrelediği bir nesne dizisini geriye döndürebilir:

### Filtreleme Senaryosu: Şartı Sağlayan Nesneleri Dizi Olarak Döndürme

Örneğin bir sınıfta notu 50'nin üzerinde olan **başarılı öğrencileri filtreleyip yeni bir dizi** olarak dönelim:

```
[ Tüm Sınıf (5 Kişi) ]  --------> [ Filtreleme Metodu ] --------> [ Başarılılar Dizisi (3 Kişi) ]
```

```java
public static Ogrenci[] basarililariFiltrele(Ogrenci[] tumOgrenciler) {
    // 1. Adım: Kaç kişinin geçtiğini say
    int basariliSayisi = 0;
    for (Ogrenci o : tumOgrenciler) {
        if (o != null && o.getNot() >= 50) {
            basariliSayisi++;
        }
    }

    // 2. Adım: Tam gereken boyutta yeni bir dizi tahsis et
    Ogrenci[] basarililar = new Ogrenci[basariliSayisi];

    // 3. Adım: Başarılı olanların referanslarını yeni diziye aktar
    int indeks = 0;
    for (Ogrenci o : tumOgrenciler) {
        if (o != null && o.getNot() >= 50) {
            basarililar[indeks] = o;
            indeks++;
        }
    }

    return basarililar; // Yeni diziyi döndür
}
```

---

## 6. Nesne Dizileri Üzerinde Temel Algoritmalar

### 1. Arama (Linear Search)
Belirli bir kritere (örneğin öğrenci numarasına) sahip nesneyi bulup döndürme:

```java
public static Ogrenci noyaGoreOgrenciBul(Ogrenci[] liste, int arananNo) {
    for (Ogrenci ogr : liste) {
        if (ogr != null && ogr.getOgrenciNo() == arananNo) {
            return ogr; // Bulunduğu an nesneyi dön
        }
    }
    return null; // Bulunamazsa null dön
}
```

---

### 2. En Büyük / En Küçük Nesneyi Bulma (Max / Min)
Sınıfın en yüksek notuna sahip öğrencisini bulma:

```java
public static Ogrenci enYuksekNotluOgrenci(Ogrenci[] liste) {
    if (liste == null || liste.length == 0) return null;

    Ogrenci enIyi = liste[0]; // İlk elemanı şampiyon varsay

    for (int i = 1; i < liste.length; i++) {
        if (liste[i] != null && liste[i].getNot() > enIyi.getNot()) {
            enIyi = liste[i]; // Daha yüksek notlu bulundu!
        }
    }
    return enIyi;
}
```

---

### 3. İstatistik ve Ortalama Hesaplama
Dizideki tüm öğrencilerin not ortalamasını hesaplama:

```java
public static double sinifOrtalamasi(Ogrenci[] liste) {
    double toplam = 0;
    int sayac = 0;

    for (Ogrenci ogr : liste) {
        if (ogr != null) {
            toplam += ogr.getNot();
            sayac++;
        }
    }

    return sayac > 0 ? (toplam / sayac) : 0.0;
}
```

---

## 7. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Temel Nesne Dizisi ve For-Each Gezinimi

#### `Kitap.java`
```java
public class Kitap {
    private String baslik;
    private String yazar;
    private double fiyat;

    public Kitap(String baslik, String yazar, double fiyat) {
        this.baslik = baslik;
        this.yazar = yazar;
        this.fiyat = fiyat;
    }

    public void bilgiBas() {
        System.out.printf("📖 %-25s | Yazar: %-15s | Fiyat: %.2f TL\n", baslik, yazar, fiyat);
    }

    public double getFiyat() {
        return fiyat;
    }
}
```

#### `KutuphaneTest.java`
```java
public class KutuphaneTest {
    public static void main(String[] args) {
        // Süslü parantez ile 4 elemanlı Kitap dizisi
        Kitap[] raf = {
            new Kitap("Simyacı", "Paulo Coelho", 95.0),
            new Kitap("1984", "George Orwell", 110.0),
            new Kitap("Kürk Mantolu Madonna", "Sabahattin Ali", 75.0),
            new Kitap("Dönüşüm", "Franz Kafka", 60.0)
        };

        System.out.println("=== KÜTÜPHANE RAFI LİSTESİ ===");
        double toplamTutar = 0.0;

        for (Kitap k : raf) {
            k.bilgiBas();
            toplamTutar += k.getFiyat();
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.printf("Raftaki Toplam Kitap Sayısı : %d\n", raf.length);
        System.out.printf("Raftaki Kitapların Değeri   : %.2f TL\n", toplamTutar);
    }
}
```

#### Konsol Çıktısı:
```text
=== KÜTÜPHANE RAFI LİSTESİ ===
📖 Simyacı                   | Yazar: Paulo Coelho    | Fiyat: 95,00 TL
📖 1984                      | Yazar: George Orwell   | Fiyat: 110,00 TL
📖 Kürk Mantolu Madonna      | Yazar: Sabahattin Ali  | Fiyat: 75,00 TL
📖 Dönüşüm                   | Yazar: Franz Kafka     | Fiyat: 60,00 TL
-----------------------------------------------------------------
Raftaki Toplam Kitap Sayısı : 4
Raftaki Kitapların Değeri   : 340,00 TL
```

---

### Örnek 2: Diziyi Parametre Alan ve Dizi Döndüren Metotlar

#### `Ogrenci.java`
```java
public class Ogrenci {
    private int no;
    private String ad;
    private double notOrtalamasi;

    public Ogrenci(int no, String ad, double notOrtalamasi) {
        this.no = no;
        this.ad = ad;
        this.notOrtalamasi = notOrtalamasi;
    }

    public int getNo() { return no; }
    public String getAd() { return ad; }
    public double getNotOrtalamasi() { return notOrtalamasi; }

    public void setNotOrtalamasi(double notOrtalamasi) {
        this.notOrtalamasi = notOrtalamasi;
    }

    @Override
    public String toString() {
        return "[" + no + "] " + ad + " - Ortalama: " + notOrtalamasi;
    }
}
```

#### `SinifYonetimi.java`
```java
public class SinifYonetimi {

    // 1. Metot: Nesne dizisini parametre alıp ekrana basar
    public static void sinifListele(Ogrenci[] ogrenciler, String baslik) {
        System.out.println("\n--- " + baslik + " ---");
        for (Ogrenci o : ogrenciler) {
            System.out.println(o);
        }
    }

    // 2. Metot: Nesne dizisi alır, filtreler ve YENİ BİR NESNE DİZİSİ DÖNDÜRÜR
    public static Ogrenci[] onurBelgesiAlanlar(Ogrenci[] tumSinif, double barajNotu) {
        int sayac = 0;
        for (Ogrenci o : tumSinif) {
            if (o.getNotOrtalamasi() >= barajNotu) {
                sayac++;
            }
        }

        Ogrenci[] onurListesi = new Ogrenci[sayac];
        int i = 0;
        for (Ogrenci o : tumSinif) {
            if (o.getNotOrtalamasi() >= barajNotu) {
                onurListesi[i++] = o;
            }
        }
        return onurListesi;
    }

    public static void main(String[] args) {
        Ogrenci[] sinif = {
            new Ogrenci(101, "Ece Yılmaz", 88.5),
            new Ogrenci(102, "Kaan Demir", 64.0),
            new Ogrenci(103, "Damla Kurt", 92.0),
            new Ogrenci(104, "Caner Akın", 45.5),
            new Ogrenci(105, "Büşra Çelik", 85.0)
        };

        // Tüm sınıfı yazdır
        sinifListele(sinif, "TÜM MEVCUT");

        // 85 ve üzeri alanları filtreleyip yeni dizi alalım
        Ogrenci[] onurOgrencileri = onurBelgesiAlanlar(sinif, 85.0);

        // Dönen diziyi yazdıralım
        sinifListele(onurOgrencileri, "ONUR BELGESİ ALAN ÖĞRENCİLER (>= 85)");
    }
}
```

---

### Örnek 3: Mağaza Stok Arama ve En Pahalı Ürünü Bulma

#### `Urun.java`
```java
public class Urun {
    private String kod;
    private String ad;
    private double fiyat;
    private int stok;

    public Urun(String kod, String ad, double fiyat, int stok) {
        this.kod = kod;
        this.ad = ad;
        this.fiyat = fiyat;
        this.stok = stok;
    }

    public String getKod() { return kod; }
    public String getAd() { return ad; }
    public double getFiyat() { return fiyat; }
    public int getStok() { return stok; }

    public void bilgileriGoster() {
        System.out.println("Kod: " + kod + " | Ürün: " + ad + " | Fiyat: " + fiyat + " TL | Stok: " + stok);
    }
}
```

#### `MagazaTest.java`
```java
public class MagazaTest {

    // Linear Search: Koda göre ürün arama
    public static Urun urunAra(Urun[] katalog, String arananKod) {
        for (Urun u : katalog) {
            if (u != null && u.getKod().equalsIgnoreCase(arananKod)) {
                return u; // Bulundu
            }
        }
        return null; // Bulunamadı
    }

    // Max Değer Algoritması: En pahalı ürünü bulma
    public static Urun enPahaliUrunuGetir(Urun[] katalog) {
        if (katalog == null || katalog.length == 0) return null;

        Urun enPahali = katalog[0];
        for (int i = 1; i < katalog.length; i++) {
            if (katalog[i] != null && katalog[i].getFiyat() > enPahali.getFiyat()) {
                enPahali = katalog[i];
            }
        }
        return enPahali;
    }

    public static void main(String[] args) {
        Urun[] envanter = {
            new Urun("U01", "Kablosuz Mouse", 350.0, 40),
            new Urun("U02", "Mekanik Klavye", 1250.0, 15),
            new Urun("U03", "27 inç Monitör", 6200.0, 8),
            new Urun("U04", "Kulaklık Standı", 180.0, 50)
        };

        // 1. Arama Testi
        System.out.println("=== 1. Ürün Arama Testi ===");
        Urun aranan = urunAra(envanter, "U03");
        if (aranan != null) {
            System.out.print("Bulunan Ürün -> ");
            aranan.bilgileriGoster();
        } else {
            System.out.println("Ürün bulunamadı!");
        }

        // 2. En Pahalı Ürün Testi
        System.out.println("\n=== 2. En Pahalı Ürün ===");
        Urun enPahali = enPahaliUrunuGetir(envanter);
        if (enPahali != null) {
            System.out.print("Zirvedeki Ürün -> ");
            enPahali.bilgileriGoster();
        }
    }
}
```

#### Konsol Çıktısı:
```text
=== 1. Ürün Arama Testi ===
Bulunan Ürün -> Kod: U03 | Ürün: 27 inç Monitör | Fiyat: 6200.0 TL | Stok: 8

=== 2. En Pahalı Ürün ===
Zirvedeki Ürün -> Kod: U03 | Ürün: 27 inç Monitör | Fiyat: 6200.0 TL | Stok: 8
```

---

## 8. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `NullPointerException` (1 Numaralı Nesne Dizisi Hatası!)
```java
Ogrenci[] liste = new Ogrenci[5];
liste[0].setNot(80); // ❌ ÇÖKER: NullPointerException!
```
`new Ogrenci[5]` ifadesi **öğrenci üretmez**, sadece 5 tane boş referans kutusu açar.
✅ **Çözüm:** Önce nesneyi üretin:
```java
liste[0] = new Ogrenci("Ali", 101); // ✅ Şimdi kullanılabilir!
liste[0].setNot(80);
```

---

### 2. `for-each` İçinde Diziye Yeni Nesne Atamaya Çalışmak
```java
Ogrenci[] liste = new Ogrenci[3];
for (Ogrenci o : liste) {
    o = new Ogrenci("Test", 1); // ❌ DİZİ DOLMAZ!
}
```
`for-each` döngüsündeki `o` değişkeni geçici bir kopyadır. Ona `new` atamak dizinin o indeksini değiştirmez; dizi hâlâ `null` kalır!
✅ **Çözüm:** Diziye atama yaparken mutlaka klasik indeksli döngü (`liste[i] = new ...`) kullanılmalıdır.

---

### 3. Dizi Boyutunu Aşmak (`ArrayIndexOutOfBoundsException`)
Boyutu 5 olan bir dizinin geçerli indeksleri `0, 1, 2, 3, 4`'tür:
```java
Ogrenci[] liste = new Ogrenci[5];
liste[5] = new Ogrenci("Hata", 100); // ❌ ÇÖKER: Index 5 out of bounds for length 5
```

---

## 9. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Kod Analizi ve Çıktı Tahmini
Aşağıdaki kod çalıştığında konsol çıktısı tam olarak ne olur?

```java
class SayiKutusu {
    int deger;
    public SayiKutusu(int d) { this.deger = d; }
}

public class DiziTest {
    public static void main(String[] args) {
        SayiKutusu[] dizi = new SayiKutusu[3];
        dizi[0] = new SayiKutusu(10);
        dizi[1] = new SayiKutusu(20);
        dizi[2] = dizi[0]; // DİKKAT: Burada ne oldu?

        dizi[2].deger = 99;

        for (SayiKutusu k : dizi) {
            System.out.print(k.deger + " ");
        }
    }
}
```

<details>
<summary>🔍 <b>Çözümü ve Bellek Açıklamasını Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
```text
99 20 99 
```
**Açıklama:**
`dizi[2] = dizi[0];` satırında yeni bir nesne üretilmemiştir. `dizi[2]`, `dizi[0]`'ın işaret ettiği Heap nesnesinin adresini almıştır. Dolayısıyla `dizi[2].deger = 99;` yapıldığında `dizi[0].deger` de 99 olur!
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kodda çalışma zamanında `NullPointerException` hatası verdirecek satırı bulunuz ve çözümü yazınız:

```java
public class Otopark {
    public static void main(String[] args) {
        Araba[] garaj = new Araba[4];
        garaj[0] = new Araba("Renault");
        garaj[1] = new Araba("Fiat");

        for (int i = 0; i < garaj.length; i++) {
            System.out.println(garaj[i].getMarka());
        }
    }
}
```

<details>
<summary>🔍 <b>Hata ve Çözümü Görmek İçin Tıklayın</b></summary>

**Hata:** Döngü `garaj.length` (4) kadar döner. Ancak `garaj[2]` ve `garaj[3]` indekslerine nesne atanmamıştır (`null`'dır). `i = 2` olduğunda `garaj[2].getMarka()` çağrısı `NullPointerException` fırlatır!
**Çözüm:** Döngü içinde `if (garaj[i] != null)` kontrolü yapılmalıdır.
</details>

---

### Alıştırma 3: Küçük Algoritma (Stok Sayma)
`Urun` nesneleri dizisini parametre alan ve **stok miktarı 0 olan (tükenmiş) ürünlerin sayısını** hesaplayıp döndüren `tukenmisUrunSayisi(Urun[] dizi)` metodunu yazınız.

---

## 10. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Personel Maaş İstatistikleri (`Calisan.java` & `SirketTest.java`)

**Gereksinimler:**
1. `Calisan` sınıfı oluşturunuz (`ad`, `departman`, `maas`).
2. `SirketTest.java` içinde 5 kişilik bir `Calisan[]` dizisi tanımlayınız.
3. Aşağıdaki statik metotları yazıp test ediniz:
   - `ortalamaMaas(Calisan[] liste)`: Şirketteki çalışanların ortalama maaşını dönsün.
   - `enYuksekMaasliCalisan(Calisan[] liste)`: En yüksek maaşı alan `Calisan` nesnesini dönsün ve bilgilerini yazdırsın.
   - `departmandakileriListele(Calisan[] liste, String arananDepartman)`: Sadece parametre verilen departmanda (örn: "IT") çalışanları ekrana bassın.

---

### 🟡 Ödev 2 (Orta Seviye): Öğrenci Not Değerlendirme ve Filtreleme (`OgrenciNot.java` & `RektorlukTest.java`)

**Gereksinimler:**
1. `Ogrenci` sınıfı tasarlayınız (`no`, `ad`, `vize`, `finalNotu`).
   - `getOrtalama()` metodu vizenin %40'ı ve finalin %60'ını hesaplasın.
2. Bir sınıfta 6 öğrenci tanımlayınız.
3. Aşağıdaki metotları kodlayınız:
   - `kalanOgrenciler(Ogrenci[] sinif)`: Ortalaması 50'nin altında olan öğrencileri filtreleyip **yeni bir `Ogrenci[]` dizisi olarak döndürsün**.
   - `sinifBirincisi(Ogrenci[] sinif)`: En yüksek ortalamaya sahip `Ogrenci` nesnesini geri döndürsün.
   - `notlaraBonusEkle(Ogrenci[] sinif, double bonus)`: Tüm öğrencilerin final notuna verilen bonusu eklesin (100'ü geçmeyecek şekilde).

---

### 🔴 Ödev 3 (İleri Seviye): Uçak Koltuk Rezervasyon Sistemi (`Koltuk.java` & `UcakTest.java`)

**Gereksinimler:**
1. `Koltuk` sınıfı:
   - `private int koltukNo;` (1'den 20'ye kadar)
   - `private String yolcuAdi;` (Başlangıçta `null`)
   - `private boolean doluMu;` (Başlangıçta `false`)
   - `koltukRezerveEt(String yolcu)`: Koltuk boşsa yolcuyu kaydetsin ve `doluMu = true` yapsın.
   - `rezervasyonIptal()`: Koltuğu boşaltsın.
2. `Ucak` sınıfı:
   - 20 elemanlı bir `Koltuk[] koltuklar` dizisi barındırsın.
   - Kurucu metot çalıştığında 1'den 20'ye kadar 20 tane `Koltuk` nesnesi döngüyle diziye üretilsin.
   - `bosKoltuklariGoster()`: Yalnızca boş olan koltuk numaralarını yan yana yazdırsın.
   - `biletSat(int koltukNo, String yolcuAdi)`: İlgili koltuğu bulup rezerve etsin.
   - `dolulukOrani()`: Uçağın yüzde kaçının dolu olduğunu hesaplasın (`%45` vb.).
3. `UcakTest.java` içinde menü tarzında simülasyon akışı kurarak koltuk satışı, doluluk oranı ve iptal senaryolarını test ediniz.

