# 5. Java'da Sınıflar, Fonksiyonlar ve Nesne Etkileşimi

Bu doküman; Java'da nesnelerin metotlara **argüman (parametre)** olarak gönderilmesini, Java'nın dünyaca ünlü **"Değer ile Aktarım (Pass-by-Value)"** kuralının nesneler üzerindeki etkisini, **alanların varsayılan (default) değerleri** ile yerel değişken farklarını, metotlarda varsayılan parametre simülasyonunu ve **metotlardan nesne döndürme (return object)** tekniklerini kapsamlı biçimde ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Kapsülleme (Encapsulation) ve Getter/Setter (4_kapsulleme.md)](./4_kapsulleme.md)
> - 🔜 [Sonraki Bölüm: Nesne Dizileri ve Dizi İşlemleri (6_class_ve_diziler.md)](./6_class_ve_diziler.md)

---

## 📌 İçindekiler
1. [Java'da Parametre Aktarımı: "Pass-by-Value" Kuralı](#1-javada-parametre-aktarımı-pass-by-value-kuralı)
   - [İlkel (Primitive) Tiplerde Değer Aktarımı](#ilkel-primitive-tiplerde-değer-aktarımı)
   - [Nesnelerde (Referans Tiplerde) Değer Aktarımı](#nesnelerde-referans-tiplerde-değer-aktarımı)
   - [İki Kritik Senaryo: Alan Değiştirme vs. Referansı Yeniden Atama](#iki-kritik-senaryo-alan-değiştirme-vs-referansı-yeniden-atama)
   - [Stack ve Heap Bellek Görselleştirmesi](#stack-ve-heap-bellek-görselleştirmesi)
2. [Java'da Varsayılan (Default) Değerler](#2-javada-varsayılan-default-değerler)
   - [Sınıf Alanlarının (Fields) Otomatik Varsayılan Değerleri Tablosu](#sınıf-alanlarının-fields-otomatik-varsayılan-değerleri-tablosu)
   - [Yerel Değişkenlerin (Local Variables) Farkı: Başlatma Zorunluluğu](#yerel-değişkenlerin-local-variables-farkı-başlatma-zorunluluğu)
   - [Java'da Varsayılan Parametre (Default Argument) Var mıdır?](#javada-varsayılan-parametre-default-argument-var-mıdır)
   - [Method Overloading ile Varsayılan Değer Simülasyonu](#method-overloading-ile-varsayılan-değer-simülasyonu)
3. [Nesnelerin Metotlardan Geri Döndürülmesi (Returning Objects)](#3-nesnelerin-metotlardan-geri-döndürülmesi-returning-objects)
   - [Metot Dönüş Tipi Olarak Sınıf Belirtme](#metot-dönüş-tipi-olarak-sınıf-belirtme)
   - [Fabrika Metotları (Factory Methods) Mantığı](#fabrika-metotları-factory-methods-mantığı)
   - [Dönen Nesneyi Doğrudan Kullanma ve Zincirleme Çağrılar](#dönen-nesneyi-doğrudan-kullanma-ve-zincirleme-çağrılar)
   - [`null` Dönme Riski ve Dikkat Edilmesi Gerekenler](#null-dönme-riski-ve-dikkat-edilmesi-gerekenler)
4. [Nesneler Arası Etkileşim: Bir Nesneyi Başka Nesneye Parametre Verme](#4-nesneler-arası-etkileşim-bir-nesneyi-başka-nesneye-parametre-verme)
   - [İki Nokta Arası Mesafe Örneği](#iki-nokta-arası-mesafe-örneği)
   - [Hesaplar Arası Havale / Transfer Mantığı](#hesaplar-arası-havale--transfer-mantığı)
5. [Adım Adım Çalıştırılabilir Kod Örnekleri](#5-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Değer Aktarımı ve Nesne Durumu Değişimi (`PassByValueDemo.java`)](#örnek-1-değer-aktarımı-ve-nesne-durumu-değişimi)
   - [Örnek 2: Overloading ile Varsayılan Argüman Mantığı (`EpostaServisi.java`)](#örnek-2-overloading-ile-varsayılan-argüman-mantığı)
   - [Örnek 3: Nesne Alan ve Yeni Nesne Döndüren Metot (`Zaman.java`)](#örnek-3-nesne-alan-ve-yeni-nesne-döndüren-metot)
6. [Sık Yapılan Hatalar ve Önemli Kurallar](#6-sık-yapılan-hatalar-ve-önemli-kurallar)
7. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#7-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
8. [Ödev Konuları ve Mini Projeler](#8-ödev-konuları-ve-mini-projeler)

---

## 1. Java'da Parametre Aktarımı: "Pass-by-Value" Kuralı

Programlama dillerinde fonksiyonlara parametre geçerken iki temel yaklaşım vardır:
1. **Pass-by-Value (Değer ile Aktarım):** Değişkenin bir kopyası fonksiyona gönderilir.
2. **Pass-by-Reference (Referans ile Aktarım):** Değişkenin orijinal bellek adresi doğrudan fonksiyona bağlanır.

> 🔴 **JAVA'NIN DEĞİŞMEZ KURALI:**
> **Java'da istisnasız HER ŞEY "Pass-by-Value" (Değer ile Aktarım) yöntemiyle aktarılır.**
> Ancak aktarılan şeyin bir ilkel tip mi yoksa bir nesne referansı mı olduğu çok büyük bir davranış farkı doğurur!

---

### İlkel (Primitive) Tiplerde Değer Aktarımı
`int`, `double`, `boolean` gibi ilkel tipler metoda aktarıldığında, değerin **birebir kopyası** çıkarılır. Metot içinde bu kopya üzerinde yapılan hiçbir değişiklik çağıran yerdeki orijinal değişkeni **etkilemez**:

```java
public static void sayiArtir(int x) {
    x = x + 10; // Sadece x parametresi (kopya) değişir
}

public static void main(String[] args) {
    int sayi = 5;
    sayiArtir(sayi);
    System.out.println(sayi); // Çıktı: 5 (DEĞİŞMEDİ!)
}
```

---

### Nesnelerde (Referans Tiplerde) Değer Aktarımı

Bir nesneyi metoda parametre olarak verdiğinizde, **nesnenin kendisi değil; nesneye işaret eden REFERANSIN (bellek adresinin) kopyası** metoda geçer!

Bu durum şu anlama gelir:
- Elinizde aynı Heap adresini gösteren iki farklı uzaktan kumanda vardır.
- Metot bu kumandayı kullanarak nesnenin düğmelerine basarsa (alanını değiştirirse), **orijinal nesne değişir!**

---

### İki Kritik Senaryo: Alan Değiştirme vs. Referansı Yeniden Atama

#### Senaryo 1: Metot içinde nesnenin alanını değiştirmek
```java
public static void puanVer(Ogrenci o) {
    o.not = 100; // Kumandayla nesnenin içini değiştirdik!
}

Ogrenci ogr = new Ogrenci();
ogr.not = 40;
puanVer(ogr);
System.out.println(ogr.not); // ÇIKTI: 100 (Orijinal nesne değişti! ✅)
```

#### Senaryo 2: Metot içinde parametreye `new` ile yeni nesne bağlamak
```java
public static void yeniOgrenciAta(Ogrenci o) {
    // Dikkat: o parametresine yepyeni bir adres verdik!
    o = new Ogrenci(); 
    o.not = 100;
}

Ogrenci ogr = new Ogrenci();
ogr.not = 40;
yeniOgrenciAta(ogr);
System.out.println(ogr.not); // ÇIKTI: 40 (Orijinal referans ETKİLENMEDİ! ❌)
```

---

### Stack ve Heap Bellek Görselleştirmesi

```
[MAIN METODU STACK]                 [HEAP BELLEK]
ogr: (Adres: 0x1A) --------+-------> [ Ogrenci Nesnesi ]
                           |         [ not: 40 -> 100  ]
[METOT ÇAĞRISI STACK]      |
o: (Adres: 0x1A) ----------+ (Aynı Heap adresine işaret eder!)

* Eğer 'o.not = 100' derseniz: Ortak nesne güncellenir.
* Eğer 'o = new Ogrenci()' derseniz: 'o' başka bir adrese (0x2B) bakar, 'ogr' ise 0x1A'da kalır!
```

---

## 2. Java'da Varsayılan (Default) Değerler

### Sınıf Alanlarının (Fields) Otomatik Varsayılan Değerleri Tablosu

Bir sınıf içinde tanımlanan alanlara (ister `instance` ister `static` olsun) ilk değer vermeseniz bile Java onları **otomatik olarak güvenli varsayılan değerlerle** başlatır:

| Veri Tipi | Varsayılan Değer (Default Value) |
| :--- | :--- |
| `byte`, `short`, `int`, `long` | `0` |
| `float`, `double` | `0.0` |
| `char` | `'\u0000'` (Boş karakter - null char) |
| `boolean` | `false` |
| **Referans Tipler** (`String`, diziler `[]`, özel sınıflar) | **`null`** (Hiçbir nesneyi göstermiyor) |

Örnek:
```java
public class Deneme {
    int sayi;        // Otomatik 0
    double oran;     // Otomatik 0.0
    boolean aktif;   // Otomatik false
    String metin;    // Otomatik null
}
```

---

### Yerel Değişkenlerin (Local Variables) Farkı: Başlatma Zorunluluğu

> ⚠️ **EN ÇOK KARIŞTIRILAN FARK:**
> Sınıf alanları otomatik varsayılan değer alırken; bir **metodun içinde tanımlanan yerel değişkenler (local variables) ASLA varsayılan değer almaz!**

```java
public void test() {
    int x; // Yerel değişken
    // ❌ DERLEME HATASI: variable x might not have been initialized
    System.out.println(x); 
}
```
Metot içindeki yerel değişkenleri kullanmadan önce mutlaka `int x = 0;` şeklinde ilk değerini elinizle vermelisiniz!

---

### Java'da Varsayılan Parametre (Default Argument) Var mıdır?

Python veya C++ yazmış geliştiricilerin en sık sorduğu soru:
```python
# Python'da mümkündür:
def selamla(isim="Misafir"):
    print(f"Merhaba {isim}")
```

```cpp
// C++ dilinde mümkündür:
void selamla(string isim = "Misafir");
```

> ❌ **JAVA'DA METOTLARDA BU ŞEKİLDE VARSAYILAN PARAMETRE DESTEĞİ YOKTUR!**
> Java sözdiziminde `public void selamla(String isim = "Misafir")` yazamazsınız; derleyici sözdizimi hatası verir.

---

### Method Overloading ile Varsayılan Değer Simülasyonu

Java'da varsayılan parametre ihtiyacı **Metot Aşırı Yükleme (Method Overloading)** yöntemiyle çözülür. Parametresiz olan metot, varsayılan değeri vererek asıl metodu çağırır:

```java
public class BildirimServisi {
    // 1. Asıl Metot (Tüm parametreleri alır)
    public void bildirimGonder(String mesaj, String kanal) {
        System.out.println("[" + kanal + "] " + mesaj);
    }

    // 2. Varsayılan Parametreli Simülasyon (Kanal girilmezse "SMS" kabul et)
    public void bildirimGonder(String mesaj) {
        bildirimGonder(mesaj, "SMS"); // Asıl metodu varsayılan değerle çağırır!
    }
}

// Kullanım:
BildirimServisi servis = new BildirimServisi();
servis.bildirimGonder("Siparişiniz kargoya verildi.");          // [SMS] Siparişiniz kargoya verildi.
servis.bildirimGonder("Hesap şifreniz güncellendi.", "E-POSTA"); // [E-POSTA] Hesap şifreniz güncellendi.
```

---

## 3. Nesnelerin Metotlardan Geri Döndürülmesi (Returning Objects)

Bir metot ilkel tipler (`int`, `double`) döndürebildiği gibi, **kendi tanımladığımız bir sınıfın nesnesini de** döndürebilir.

### Metot Dönüş Tipi Olarak Sınıf Belirtme

Metodun dönüş tipi yerine sınıfın adı yazılır:

```java
public Ogrenci basariliOgrenciBul(Ogrenci o1, Ogrenci o2) {
    if (o1.getNot() > o2.getNot()) {
        return o1; // o1 nesnesinin referansını döner
    } else {
        return o2; // o2 nesnesinin referansını döner
    }
}
```

---

### Fabrika Metotları (Factory Methods) Mantığı

Bir metot kendi içinde `new` ile yeni bir nesne oluşturup, hazırladıktan sonra çağıran yere teslim edebilir:

```java
public class ArabaFabrikasi {
    public static Araba standartTaksiUret() {
        Araba taksi = new Araba();
        taksi.setRenk("Sarı");
        taksi.setKoltukSayisi(4);
        taksi.setTicari(true);
        return taksi; // Heap'te üretilen yeni nesnenin referansı döner!
    }
}

// Kullanım:
Araba yeniTaksi = ArabaFabrikasi.standartTaksiUret();
```

---

### Dönen Nesneyi Doğrudan Kullanma ve Zincirleme Çağrılar

Metottan bir nesne döndüğünde, dönen nesneyi önce bir değişkene atayabilir veya **nokta (`.`) operatörüyle anında** tüketebilirsiniz:

```java
// 1. Yol: Değişkene atama
Araba a = ArabaFabrikasi.standartTaksiUret();
a.calistir();

// 2. Yol: Doğrudan zincirleme (One-liner)
ArabaFabrikasi.standartTaksiUret().calistir();
```

---

### `null` Dönme Riski ve Dikkat Edilmesi Gerekenler

Eğer aranan nesne bulunamazsa metot `null` dönebilir. Bu durumda dönen değeri kontrol etmeden nokta (`.`) ile kullanırsanız **`NullPointerException`** patlar:

```java
Ogrenci bulunan = ogrenciAra(105);

// ❌ HATA RİSKİ: bulunan null ise çöker!
// bulunan.bilgiYazdir();

// ✅ GÜVENLİ YAKLAŞIM:
if (bulunan != null) {
    bulunan.bilgiYazdir();
} else {
    System.out.println("Öğrenci bulunamadı.");
}
```

---

## 4. Nesneler Arası Etkileşim: Bir Nesneyi Başka Nesneye Parametre Verme

Nesneye yönelimli dünyada gerçek hayat simülasyonları, **nesnelerin birbirlerine parametre olarak gönderilmesiyle** inşa edilir.

### İki Nokta Arası Mesafe Örneği

2 boyutlu uzayda bir `Nokta(x, y)` sınıfı düşünelim. Bir noktanın başka bir noktaya olan Öklid mesafesini hesaplamak için diğer nokta parametre olarak alınır:

$$\text{Mesafe} = \sqrt{(x_2 - x_1)^2 + (y_2 - y_1)^2}$$

```java
public class Nokta {
    private int x;
    private int y;

    public Nokta(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Başka bir Nokta nesnesini parametre olarak alır!
    public double mesafeHesapla(Nokta digerNokta) {
        int dx = this.x - digerNokta.x;
        int dy = this.y - digerNokta.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
```

---

### Hesaplar Arası Havale / Transfer Mantığı

Bir `BankaHesabi` nesnesinden başka bir `BankaHesabi` nesnesine para transferi:

```java
public class BankaHesabi {
    private String iban;
    private double bakiye;

    // Alıcı hesabı (hedefHesap) nesne olarak parametre alır!
    public boolean havaleYap(BankaHesabi hedefHesap, double miktar) {
        if (miktar <= 0 || this.bakiye < miktar) {
            System.out.println("Havale başarısız: Yetersiz bakiye veya geçersiz miktar.");
            return false;
        }

        this.bakiye -= miktar;          // Bu hesaptan düş
        hedefHesap.bakiye += miktar;     // Hedef nesnenin bakiyesini artır!
        System.out.println(this.iban + " -> " + hedefHesap.iban + " hesabına " + miktar + " TL aktarıldı.");
        return true;
    }
}
```

---

## 5. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Değer Aktarımı ve Nesne Durumu Değişimi

Java'da ilkel tipler ile nesnelerin metoda aktarılırken nasıl farklı davrandığını gösteren kesin kanıt:

#### `PassByValueDemo.java`
```java
class Skor {
    int puan;
    public Skor(int puan) {
        this.puan = puan;
    }
}

public class PassByValueDemo {
    // 1. İlkel tip alan metot
    public static void degistirIlkel(int x) {
        x = 999;
    }

    // 2. Nesne alanı güncelleyen metot
    public static void degistirNesneAlani(Skor s) {
        s.puan = 999; // Heap'teki orijinal nesne güncellenir
    }

    // 3. Parametreye yeni nesne atayan metot
    public static void yeniNesneAta(Skor s) {
        s = new Skor(5555); // Sadece yerel parametre yeni adrese bakar
    }

    public static void main(String[] args) {
        // Test 1: İlkel tip
        int sayi = 10;
        degistirIlkel(sayi);
        System.out.println("Test 1 (İlkel Tip)   : " + sayi); // 10

        // Test 2: Nesne alanını değiştirme
        Skor skor1 = new Skor(100);
        degistirNesneAlani(skor1);
        System.out.println("Test 2 (Nesne Alanı) : " + skor1.puan); // 999

        // Test 3: Referansı yeniden atama
        Skor skor2 = new Skor(200);
        yeniNesneAta(skor2);
        System.out.println("Test 3 (Yeni Nesne)  : " + skor2.puan); // 200 (Etkilenmedi!)
    }
}
```

#### Konsol Çıktısı:
```text
Test 1 (İlkel Tip)   : 10
Test 2 (Nesne Alanı) : 999
Test 3 (Yeni Nesne)  : 200
```

---

### Örnek 2: Overloading ile Varsayılan Argüman Mantığı

E-posta gönderme servisinde konu veya öncelik belirtilmediğinde varsayılan değerleri dolduran metot tasarımı:

#### `EpostaServisi.java`
```java
public class EpostaServisi {
    // Ana metot: 4 parametreli tam sürüm
    public static void epostaGonder(String alici, String icerik, String konu, boolean onemliMi) {
        System.out.println("----------------------------------------------");
        System.out.println("Kime     : " + alici);
        System.out.println("Konu     : " + konu + (onemliMi ? " [ÖNEMLİ 🔴]" : ""));
        System.out.println("İçerik   : " + icerik);
        System.out.println("----------------------------------------------");
    }

    // Aşırı Yükleme 1: 'onemliMi' girilmezse varsayılan false olsun
    public static void epostaGonder(String alici, String icerik, String konu) {
        epostaGonder(alici, icerik, konu, false);
    }

    // Aşırı Yükleme 2: 'konu' ve 'onemliMi' girilmezse: konu="Bilgilendirme", onemli=false
    public static void epostaGonder(String alici, String icerik) {
        epostaGonder(alici, icerik, "Genel Bilgilendirme", false);
    }

    public static void main(String[] args) {
        // En sade çağrı (Varsayılan konu ve öncelik devreye girer)
        epostaGonder("ogrenci@universite.edu.tr", "Ders notları yüklendi.");

        // Konulu çağrı
        epostaGonder("veli@okul.com", "Yarın veli toplantısı yapılacaktır.", "Veli Toplantısı Duyurusu");

        // Tam parametreli çağrı
        epostaGonder("admin@sirket.com", "Sunucu disk alanı doldu!", "SİSTEM UYARISI", true);
    }
}
```

---

### Örnek 3: Nesne Alan ve Yeni Nesne Döndüren Metot

Bir `Zaman(saat, dakika)` sınıfı üzerinde saat toplama işlemi yapıp geriye **yeni bir `Zaman` nesnesi** döndürme:

#### `Zaman.java`
```java
public class Zaman {
    private int saat;
    private int dakika;

    public Zaman(int saat, int dakika) {
        this.saat = (saat + (dakika / 60)) % 24;
        this.dakika = dakika % 60;
    }

    // Bir Zaman nesnesi alır, mevcut nesneyle toplayıp YENİ bir Zaman nesnesi DÖNDÜRÜR!
    public Zaman topla(Zaman digerZaman) {
        int toplamDakika = this.dakika + digerZaman.dakika;
        int toplamSaat = this.saat + digerZaman.saat + (toplamDakika / 60);

        // Yeni nesne üretip referansını return ediyoruz
        return new Zaman(toplamSaat % 24, toplamDakika % 60);
    }

    // Dakika ekleyip yeni bir nesne döndüren metot
    public Zaman dakikaEkle(int eklenenDakika) {
        return this.topla(new Zaman(0, eklenenDakika));
    }

    public void yazdir() {
        System.out.printf("%02d:%02d\n", saat, dakika);
    }
}
```

#### `ZamanTest.java`
```java
public class ZamanTest {
    public static void main(String[] args) {
        Zaman baslangic = new Zaman(14, 45);
        Zaman dersSuresi = new Zaman(1, 30); // 1 saat 30 dakika

        System.out.print("Ders Başlangıcı : ");
        baslangic.yazdir();

        // İki nesneyi topla ve dönen yeni nesneyi al
        Zaman bitis = baslangic.topla(dersSuresi);

        System.out.print("Ders Bitişi     : ");
        bitis.yazdir();

        // Zincirleme kullanım: 45 dakika mola ekle
        System.out.print("Mola Sonrası    : ");
        bitis.dakikaEkle(45).yazdir();
    }
}
```

#### Konsol Çıktısı:
```text
Ders Başlangıcı : 14:45
Ders Bitişi     : 16:15
Mola Sonrası    : 17:00
```

---

## 6. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. Metot İçinde `parametre = new ...` Yapınca Dışarıyı Değiştirdiğini Sanmak
En yaygın yanılgıdır:
```java
public static void sifirla(Ogrenci o) {
    o = null; // ❌ Dışarıdaki nesne silinmez! Sadece metodun elindeki yerel referans null olur.
}
```
Eğer nesnenin durumunu sıfırlamak istiyorsanız nesnenin alanlarını temizlemelisiniz: `o.setNot(0);`.

---

### 2. Metot Tanımında Varsayılan Değer Atamaya Çalışmak
```java
// ❌ SÖZDİZİMİ HATASI: Java bunu kabul etmez!
public void kaydet(String ad, int seviye = 1) { ... }
```
✅ **Çözüm:** Metot aşırı yükleme (Method Overloading) kullanınız.

---

### 3. Sınıf Alanı ile Yerel Değişkenin İlk Değer Alma Farkını Unutmak
- Sınıf seviyesindeki değişken `int sayi;` $\rightarrow$ otomatik `0` olur.
- Metot içindeki `int sayi;` $\rightarrow$ ilk değer almaz, doğrudan ekrana basmaya çalışırsanız derlenmez!

---

### 4. Metottan Dönen `null` Kontrolünü Atlamak
Arama metotları bir nesne bulamadığında `null` dönebilir:
```java
Kitap k = kutuphane.kitapBul("Bilinmeyen Kitap");
System.out.println(k.getYazar()); // ❌ k null ise NullPointerException!
```
Her zaman `if (k != null)` kontrolü yapınız.

---

## 7. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Stack & Heap İzleme ve Çıktı Tahmini
Aşağıdaki kod çalıştığında konsola ne yazdırılır? Adım adım takip ediniz:

```java
class Kutu {
    int boyut;
    public Kutu(int b) { this.boyut = b; }
}

public class Soru1 {
    public static void islem(Kutu k1, Kutu k2) {
        k1.boyut += 10;
        k2 = k1;
        k2.boyut += 5;
    }

    public static void main(String[] args) {
        Kutu a = new Kutu(20);
        Kutu b = new Kutu(50);

        islem(a, b);

        System.out.println("a.boyut = " + a.boyut);
        System.out.println("b.boyut = " + b.boyut);
    }
}
```

<details>
<summary>🔍 <b>Çözümü ve Bellek Açıklamasını Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
```text
a.boyut = 35
b.boyut = 50
```
**Açıklama:**
1. `islem` metoduna `a` (20) ve `b` (50) gönderilir.
2. `k1.boyut += 10;` $\rightarrow$ `a` nesnesinin boyutu $20 + 10 = 30$ olur.
3. `k2 = k1;` $\rightarrow$ `k2` parametresi artık `b`'yi değil, `a` nesnesini göstermeye başlar!
4. `k2.boyut += 5;` $\rightarrow$ `k2` (`a` nesnesi) boyutu $30 + 5 = 35$ olur.
5. `b` nesnesine hiç dokunulmamıştır; boyutu 50 olarak kalır!
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında bulunan derleme hatasını tespit edip düzeltiniz:

```java
public class Hesaplayici {
    public static void alanYazdir(int en = 5, int boy = 10) {
        System.out.println("Alan: " + (en * boy));
    }
}
```

<details>
<summary>🔍 <b>Hata ve Düzeltme Kodunu Görmek İçin Tıklayın</b></summary>

**Hata:** Java'da parametre listesinde `int en = 5` şeklinde varsayılan değer tanımlanamaz.
**Düzeltilmiş Hali (Overloading ile):**
```java
public class Hesaplayici {
    public static void alanYazdir(int en, int boy) {
        System.out.println("Alan: " + (en * boy));
    }

    public static void alanYazdir() {
        alanYazdir(5, 10); // Varsayılan değerler paslanır
    }
}
```
</details>

---

### Alıştırma 3: İki Nesneyi Karşılaştıran Metot
Bir `Karpuz` sınıfı yazınız:
- Alan: `private double agirlik;` (kg cinsinden) ve kurucusu.
- Metot: `public Karpuz dahaAgirOlaniSec(Karpuz digerKarpuz)`
  - Çağrılan karpuz ile parametre gelen karpuzun ağırlıklarını karşılaştırsın ve **daha ağır olan Karpuz nesnesini geri döndürsün**.

---

## 8. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): RPG Karakter Düellosu (`Karakter.java` & `ArenaTest.java`)

**Gereksinimler:**
1. `Karakter` adında bir sınıf tasarlayınız.
2. Alanlar: `private String isim;`, `private int can;`, `private int saldiriGucu;`.
3. Metotlar:
   - `public void saldir(Karakter hedef)`:
     - Çağrılan karakter, parametre olarak gelen `hedef` karakterin canını kendi `saldiriGucu` kadar azaltsın.
     - Ekrana `"[İsim], [Hedef]'e saldırdı! [Hedef]'in kalan canı: X"` yazdırsın.
     - Eğer hedef karakterin canı 0 veya altına düşerse `"[Hedef] mağlup oldu!"` mesajı basılsın ve canı 0'a sabitlensin.
   - `public boolean hayattaMi()`: Can > 0 ise `true` dönsün.
4. `ArenaTest.java` içinde iki karakter oluşturup bir döngü içinde sırayla birbirlerine saldırmalarını sağlayarak kazananı belirleyiniz.

---

### 🟡 Ödev 2 (Orta Seviye): Tarih İşlemleri ve Nesne Döndürme (`Tarih.java`)

**Gereksinimler:**
1. `Tarih` sınıfı oluşturunuz (`gun`, `ay`, `yil`).
2. Parametre Varsayılanlaştırma (Overloading):
   - `Tarih(int gun, int ay, int yil)`: Tam parametreli kurucu.
   - `Tarih(int gun, int ay)`: Yıl girilmezse otomatik 2026 alsın.
   - `Tarih()`: Hiçbir şey girilmezse 01.01.2026 alsın.
3. Yeni Nesne Döndüren Metot:
   - `public Tarih gunEkle(int eklenecekGun)`:
     - Mevcut tarihe gün eklesin (basitlik adına her ayı 30 gün kabul edebilirsiniz).
     - **Mevcut nesneyi değiştirmesin**, eklenmiş yeni tarihi içeren **yeni bir `Tarih` nesnesi üretip döndürsün** (*Immutable Pattern*).
4. `tarihYazdir()` metodu ile tarih formatını `GG/AA/YYYY` şeklinde basıp test ediniz.

---

### 🔴 Ödev 3 (İleri Seviye): Banka Transfer ve Dekont Üretim Sistemi (`BankaHesabi.java` & `Dekont.java`)

**Gereksinimler:**
1. `Dekont` sınıfı tasarlayınız:
   - Alanlar: `String islemNo`, `String gonderenIban`, `String aliciIban`, `double tutar`, `String durum` ("BAŞARILI" / "BAŞARISIZ").
   - `dekontBas()` metodu ile şık bir dekont çıktısı versin.
2. `BankaHesabi` sınıfı tasarlayınız:
   - Alanlar: `iban`, `sahip`, `bakiye`.
   - **Metot:** `public Dekont paraTransferiYap(BankaHesabi aliciHesap, double miktar)`
     - Metot başka bir `BankaHesabi` nesnesi ve `double miktar` alsın.
     - Yeterli bakiye varsa gönderenden düşüp alıcıya eklesin.
     - İşlem sonucunda bilgileri içeren **yeni bir `Dekont` nesnesi üretip döndürsün**!
     - Eğer bakiye yetersizse bakiyelere dokunmasın ve durumu "BAŞARISIZ" olan bir `Dekont` nesnesi üretip dönsün.
3. `HavaleTest.java` dosyasında iki hesap açıp transfer yaptırınız ve dönen `Dekont` nesnesi üzerinden `dekont.dekontBas()` çağrısını test ediniz.

