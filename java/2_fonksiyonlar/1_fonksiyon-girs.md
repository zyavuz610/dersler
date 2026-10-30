# 1. Java'da Fonksiyonlar (Metotlar): Giriş ve Temeller

Bu rehber; Java programlama dilinde kodların modüler, okunabilir, yeniden kullanılabilir ve yönetilebilir olmasını sağlayan **fonksiyon (metot) kavramını**, bir metodun anatomisini, parametre ve argüman mekanizmasını, **değer döndürme (`return`)** mantığını, **Java'da varsayılan (*default*) parametrelerin nasıl kurgulandığını** (Metot Aşırı Yükleme / *Method Overloading*), sık düşülen tuzakları ve öğrencilerin pratik yapabileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 2_fonksiyonlar**  
> Bu modül, şimdiye kadar tek bir `main` metodu içerisine yazdığımız onlarca satırlık kodları bağımsız görev birimlerine bölerek profesyonel yazılım mimarisine geçişimizi sağlar.

---

## 📌 İçindekiler
1. [Fonksiyon (Metot) Nedir ve Neden Hayatidir?](#1-fonksiyon-metot-nedir-ve-neden-hayatidir)
2. [Fonksiyon ile Metot Arasındaki Terim Farkı](#2-fonksiyon-ile-metot-arasındaki-terim-farkı)
3. [Bir Metodun Anatomisi ve Sözdizimi](#3-bir-metodun-anatomisi-ve-sözdizimi)
4. [Geriye Değer Döndürmeyen Metotlar (`void`)](#4-geriye-değer-döndürmeyen-metotlar-void)
5. [Geriye Değer Döndüren Metotlar ve `return` İfadesi](#5-geriye-değer-döndüren-metotlar-ve-return-ifadesi)
6. [Parametre ve Argüman Kavramları (Basit Parametreler)](#6-parametre-ve-argüman-kavramları-basit-parametreler)
7. [Java'da Varsayılan (Default) Parametre Mantığı](#7-javada-varsayılan-default-parametre-mantığı)
8. [Değer ile Çağırma (Pass-by-Value) İlkesi](#8-değer-ile-çağırma-pass-by-value-ilkesi)
9. [En Sık Yapılan Hatalar ve Tuzaklar](#9-en-sık-yapılan-hatalar-ve-tuzaklar)
10. [Kapsamlı Örnek Konsol Uygulaması: `HesapVeFaturaServisi`](#10-kapsamlı-örnek-konsol-uygulaması-hesapvefaturaservisi)
11. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#11-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
12. [Ödev Projeleri](#12-ödev-projeleri)

---

## 1. Fonksiyon (Metot) Nedir ve Neden Hayatidir?

Şimdiye kadar yazdığımız tüm Java programlarında bütün değişkenleri, döngüleri ve `if-else` kontrollerini tek bir `public static void main(String[] args)` bloğu içerisine yığdık. Küçük örneklerde bu sorun teşkil etmese de, binlerce satırlık gerçek dünya projelerinde her şeyi tek bir yere yazmak yazılımı yönetilemez hale getirir.

Bir **Fonksiyon (Metot)**; belirli bir görevi yerine getirmek üzere tasarlanmış, bir isim altında toplanmış, gerektiğinde girdi (*parametre*) alabilen ve işlem sonucunda çıktı (*dönüş değeri*) üretebilen **bağımsız kod bloklarıdır**.

### 🌟 Neden Metot Kullanmalıyız? (5 Temel Gerekçe)

1. **DRY İlkesi (Don't Repeat Yourself - Kendini Tekrar Etme):**  
   Bir dairenin alanını veya bir faturanın KDV tutarını programın 15 farklı yerinde hesaplamanız gerekiyorsa, aynı matematik formülünü 15 kez kopyalamak yerine tek bir `kdvHesapla()` metodu yazıp 15 yerden bu metodu çağırırsınız.
2. **Modülerlik ve Temiz Kod (Clean Code):**  
   500 satırlık karmaşık bir `main` metodu yerine; `kullaniciGirdisiniAl()`, `verileriDogrula()`, `veritabaninaKaydet()` ve `epostaGonder()` gibi küçük, amaca yönelik parçalarla çalışmak kodu roman gibi okunabilir kılar.
3. **Yeniden Kullanılabilirlik (Reusability):**  
   Yazdığınız bir metodu sadece o anki sınıfta değil, başka dosyalarda, hatta başka projelerde bile defalarca kullanabilirsiniz (Tıpkı `Math.sqrt()` veya `System.out.println()` metotlarını kullandığımız gibi).
4. **Bakım ve Güncelleme Kolaylığı (Maintainability):**  
   KDV oranı %18'den %20'ye çıktığında kodunuzdaki 50 satırı tek tek aramak zorunda kalmazsınız; sadece ilgili metodun içindeki tek bir satırı güncellersiniz.
5. **Hata Ayıklama (Debugging) Kolaylığı:**  
   Programda bir hata oluştuğunda tüm projeyi incelemek yerine sadece o görevi yapan metodu izole edip test edebilirsiniz.

---

## 2. Fonksiyon ile Metot Arasındaki Terim Farkı

Programlama dünyasında bu iki terim sıklıkla birbirinin yerine kullanılır:
- **Fonksiyon (Function):** C, Python veya JavaScript gibi dillerde herhangi bir sınıfa ait olmadan, dosyanın en üst seviyesinde bağımsız olarak tanımlanabilen kod bloklarıdır.
- **Metot (Method):** **Nesneye Yönelik Programlama (OOP)** dillerinde bir sınıfın (*class*) veya nesnenin (*object*) bünyesinde yer alan fonksiyonlara verilen addır.

> ☕ **Java Gerçeği:** Java saf bir Nesneye Dayalı dildir. Java'da hiçbir kod bir sınıfın dışında tek başına var olamaz. Dolayısıyla Java'daki tüm fonksiyonlar teknik olarak birer **Metottur (Method)**.

---

## 3. Bir Metodun Anatomisi ve Sözdizimi

Java'da bir metot tanımlanırken aşağıdaki bileşenler sırasıyla yazılır:

```text
[Erişim Belirteci] [static] [Dönüş Tipi] [Metot Adı] ( [Parametre Listesi] ) {
    // Metot Gövdesi (Çalışacak Kodlar)
    // [return ifadesi]
}
```

```java
public static int topla(int sayi1, int sayi2) {
    int sonuc = sayi1 + sayi2;
    return sonuc;
}
```

### 🔍 Parçaların Anlamları:
1. **Erişim Belirteci (`Access Modifier`):** Metoda kimlerin erişebileceğini belirler (`public`, `private`, `protected`). Başlangıç için her yerden erişilebilen `public` kullanacağız.
2. **`static` Anahtar Kelimesi:** Bir metodun, sınıftan yeni bir nesne (`new`) üretmeden doğrudan sınıf adı üzerinden veya `main` içinden çağrılabilmesini sağlar. `main` metodu `static` olduğu için, `main` içerisinden doğrudan çağrılacak yardımcı metotların da **`static`** olması gerekir.
3. **Dönüş Tipi (`Return Type`):** Metodun işini bitirdikten sonra kendisini çağıran yere ne tür bir veri vereceğini belirtir (`int`, `double`, `String`, `boolean`). Eğer metot geriye hiçbir değer döndürmeyecek, sadece bir işlem yapacaksa buraya **`void`** yazılır.
4. **Metot Adı (`Method Name`):** Metodun yaptığı işi anlatan, küçük harfle başlayan ve deve hörgücü kuralına (`camelCase`) uyan bir isimdir (`faturaKes()`, `asalMi()`, `bilgiYazdir()`).
5. **Parametre Listesi (`Parameter List`):** Metodun çalışabilmesi için dışarıdan ihtiyaç duyduğu verilerdir. Parantez içine virgülle yazılarak belirtilir (`int a, double b`). İhtiyaç yoksa parantez içi boş bırakılır `()`.
6. **Metot Gövdesi (`Method Body`):** Metot çağrıldığında çalışacak kodların bulunduğu süslü parantezler `{ }` arasıdır.

---

## 4. Geriye Değer Döndürmeyen Metotlar (`void`)

Bazı metotlar bir hesaplama yapıp sonuç üretmek yerine sadece bir eylem gerçekleştirir: Ekrana mesaj basmak, veritabanına yazmak veya sistem uyarısı vermek gibi. Bu tür metotların dönüş tipi **`void`** (anlamı: *boş / hükümsüz*) olarak tanımlanır.

### 💻 Örnek 1: Parametresiz ve Değer Döndürmeyen Metot

```java
public class Selamlama {

    // Metot Tanımı:
    public static void karsilamaMesajiYazdir() {
        System.out.println("==========================================");
        System.out.println("  KTÜ BİLGİSAYAR MÜHENDİSLİĞİNE HOŞ GELDİNİZ ");
        System.out.println("==========================================");
    }

    public static void main(String[] args) {
        // Metot Çağrısı (Call / Invoke):
        karsilamaMesajiYazdir();
        
        System.out.println("Ders: Java ile Programlamaya Giriş");
        
        // İstediğimiz zaman tekrar çağırabiliriz:
        karsilamaMesajiYazdir();
    }
}
```

---

## 5. Geriye Değer Döndüren Metotlar ve `return` İfadesi

Bir metodun yaptığı işlemin sonucunu `main` metodunda veya başka bir yerde bir değişkene kaydetmek veya başka bir hesaplamada kullanmak istiyorsak, metodun geriye bir değer vermesi gerekir.

Bu işlem **`return`** anahtar kelimesi ile yapılır.

### 📌 `return` Kuralları:
- `return` ifadesi, hesaplanan değeri metodun çağrıldığı noktaya fırlatır.
- `return` çalıştığı anda metot **derhal sonlanır**. `return` satırının altına yazılan kodlar asla çalışmaz (*Unreachable Code*).
- Döndürülen değerin türü, metodun başında belirtilen **Dönüş Tipi** ile birebir uyumlu olmalıdır.

### 💻 Örnek 2: Değer Döndüren Basit Metot

```java
public class MatematikServisi {

    // İki tamsayıyı toplayıp sonucunu geriye 'int' olarak döndüren metot:
    public static int topla(int a, int b) {
        int toplam = a + b;
        return toplam; // Sonucu geriye gönder
    }

    // Bir sayının karesini geriye 'double' olarak döndüren metot:
    public static double kareAl(double sayi) {
        return sayi * sayi; // Doğrudan formülü de return edebiliriz
    }

    public static void main(String[] args) {
        // Metottan dönen değeri bir değişkene atayabiliriz:
        int sonuc1 = topla(15, 25);
        System.out.println("15 + 25 = " + sonuc1);

        // Metottan dönen değeri doğrudan ekrana yazdırabiliriz:
        System.out.println("7.5 sayısının karesi: " + kareAl(7.5));

        // Metot sonucunu başka bir matematik işleminde kullanabiliriz:
        double toplamKare = kareAl(3) + kareAl(4); // 9 + 16 = 25
        System.out.println("Kareler toplamı: " + toplamKare);
    }
}
```

---

## 6. Parametre ve Argüman Kavramları (Basit Parametreler)

Bu iki terim sıklıkla karıştırılır:
- **Parametre (Formal Parameter):** Metot **tanımlanırken** parantez içinde bildirilen değişkenlerdir. Metodun şablonunu oluşturur (`int x`, `String ad`).
- **Argüman (Actual Argument):** Metot **çağrılırken** parantez içine gönderilen gerçek değerlerdir (`10`, `"Ahmet"`).

```java
public static void selamVer(String isim, int yas) { // 'isim' ve 'yas' PARAMETREDİR
    System.out.println("Merhaba " + isim + ", yaşınız: " + yas);
}

public static void main(String[] args) {
    selamVer("Zeynep", 21); // "Zeynep" ve 21 ARGÜMANDIR
}
```

### 💻 Örnek 3: Mantıksal Değer Döndüren Metot (`boolean`)
Yazılım mühendisliğinde bir durumun kontrolünü yapan metotlar genellikle `is...` veya `...Mi` şeklinde isimlendirilir ve geriye `boolean` döner:

```java
public class SayiKontrol {

    // Bir sayının çift olup olmadığını test eden metot:
    public static boolean ciftMi(int sayi) {
        return (sayi % 2 == 0); // Şart doğruysa true, yanlışsa false döner
    }

    // Bir sayının pozitif olup olmadığını test eden metot:
    public static boolean pozitifMi(int sayi) {
        return sayi > 0;
    }

    public static void main(String[] args) {
        int testSayisi = 18;

        if (ciftMi(testSayisi)) {
            System.out.println(testSayisi + " bir ÇİFT sayıdır.");
        } else {
            System.out.println(testSayisi + " bir TEK sayıdır.");
        }
    }
}
```

---

## 7. Java'da Varsayılan (Default) Parametre Mantığı

Python, C++ veya Kotlin gibi dillerde bir fonksiyona varsayılan değer tanımlamak çok kolaydır:
```python
# Python örneği:
def selamla(isim="Misafir"):
    print("Merhaba " + isim)
```

### ❓ Java'da Neden `void selamla(String isim = "Misafir")` Yazılamaz?
Java dili, tip güvenliğini ve derleme zamanı belirliliğini en üst seviyede tutmak amacıyla doğrudan parametre parantezinde varsayılan değer atama sözdizimini **desteklemez**.

### 💡 Java'da Varsayılan Parametre Çözümü: Metot Aşırı Yükleme (Method Overloading)

Java'da varsayılan parametre davranışı elde etmenin standart ve en zarif yolu **Metot Aşırı Yükleme (Method Overloading)** tekniğidir.

> 📌 **Metot Aşırı Yükleme (Overloading) Nedir?**  
> Aynı sınıf içerisinde **aynı isme sahip**, fakat **parametre sayısı veya parametre türleri farklı olan** birden fazla metot tanımlayabilme özelliğidir.

Biz parametresi eksik olan metoda varsayılan değeri verip, asıl tam parametreli metodu çağırırız (*Method Delegation / Yönlendirme*):

```java
public class VarsayilanParametreSimulasyonu {

    // 1. TAM METOT: Tüm parametreleri alır
    public static void kullaniciSelamla(String isim, String unvan) {
        System.out.println("Hoş geldiniz, " + unvan + " " + isim + "!");
    }

    // 2. AŞIRI YÜKLENMİŞ METOT (Varsayılan Unvan: "Sayın"):
    // Eğer unvan belirtilmezse otomatik olarak "Sayın" kabul edilir:
    public static void kullaniciSelamla(String isim) {
        kullaniciSelamla(isim, "Sayın"); // Tam metoda varsayılan değerle paslar
    }

    // 3. AŞIRI YÜKLENMİŞ METOT (Varsayılan İsim ve Unvan):
    // Hiçbir parametre verilmezse "Sayın Misafir" kabul edilir:
    public static void kullaniciSelamla() {
        kullaniciSelamla("Misafir", "Sayın");
    }

    public static void main(String[] args) {
        // Durum 1: İki parametre de verildi
        kullaniciSelamla("Ahmet Yılmaz", "Prof. Dr.");

        // Durum 2: Sadece isim verildi (Unvan varsayılan "Sayın" oldu)
        kullaniciSelamla("Mehmet");

        // Durum 3: Hiçbir şey verilmedi (Varsayılan "Sayın Misafir" oldu)
        kullaniciSelamla();
    }
}
```

**Ekran Çıktısı:**
```text
Hoş geldiniz, Prof. Dr. Ahmet Yılmaz!
Hoş geldiniz, Sayın Mehmet!
Hoş geldiniz, Sayın Misafir!
```

---

### 💻 Varsayılan Parametre ile KDV Hesaplama Örneği:

Bir e-ticaret sisteminde standart KDV oranı **%20**'dir. Ancak özel ürünlerde (örneğin gıda için %1, lüks tüketim için %30) farklı oranlar uygulanabilmelidir:

```java
public class KdvHesaplayici {

    // 1. Dışarıdan özel KDV oranı verilen genel metot:
    public static double kdvliFiyatHesapla(double hamFiyat, double kdvOrani) {
        return hamFiyat + (hamFiyat * (kdvOrani / 100.0));
    }

    // 2. Varsayılan Parametreli Metot (Varsayılan KDV = %20):
    public static double kdvliFiyatHesapla(double hamFiyat) {
        return kdvliFiyatHesapla(hamFiyat, 20.0); // %20'yi varsayılan olarak gönderir
    }

    public static void main(String[] args) {
        double urun1 = 500.0;
        double urun2 = 200.0;

        // Standart KDV (%20) uygulansın:
        double toplam1 = kdvliFiyatHesapla(urun1);
        System.out.println("Standart Ürün (Varsayılan %20 KDV): " + toplam1 + " TL");

        // Özel KDV (%1 temel gıda) uygulansın:
        double toplam2 = kdvliFiyatHesapla(urun2, 1.0);
        System.out.println("Gıda Ürünü (Özel %1 KDV): " + toplam2 + " TL");
    }
}
```

---

## 8. Değer ile Çağırma (Pass-by-Value) İlkesi

Öğrencilerin en çok yanılgıya düştüğü konulardan biri, metoda parametre olarak gönderilen bir değişkenin metot içinde değiştirildiğinde `main` metodundaki orijinalinin değişip değişmeyeceğidir.

> 🚨 **Kritik Kural:** Java **KESİNLİKLE VE HER ZAMAN "Değer ile Çağırma" (Pass-by-Value)** prensibiyle çalışır!  
> Bir metoda bir ilkel (*primitive*) değişken (`int`, `double` vb.) gönderildiğinde, o değişkenin kendisi değil, **o anki değerinin bir kopyası** metoda gider. Metot içinde yapılan hiçbir değişiklik çağıran yerdeki asıl değişkeni ETKİLEMEZ!

### 💻 Örnek Kanıt:

```java
public class DegerIleCagirma {

    public static void sayiyiDegistir(int x) {
        x = x + 50; // Sadece metodun kendi kopyası değişir
        System.out.println("Metodun içindeki x: " + x);
    }

    public static void main(String[] args) {
        int asilSayi = 10;
        System.out.println("Metot çağrılmadan önce asilSayi: " + asilSayi); // 10

        sayiyiDegistir(asilSayi); // 10 değeri kopyalanarak gönderildi

        System.out.println("Metot çağrıldıktan sonra asilSayi: " + asilSayi); // HALA 10!
    }
}
```

**Ekran Çıktısı:**
```text
Metot çağrılmadan önce asilSayi: 10
Metodun içindeki x: 60
Metot çağrıldıktan sonra asilSayi: 10
```

---

## 9. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: `static` Olmayan Metodu `main` İçinden Çağırmaya Çalışmak
`main` metodu `static` olduğu için, sınıftan bir nesne üretilmeden çağrılacak metotların başına da mutlaka **`static`** yazılmalıdır:

```java
// ❌ DERLEME HATASI: Non-static method cannot be referenced from a static context
void selamla() { System.out.println("Selam"); }

public static void main(String[] args) {
    selamla(); // HATA! 'static' olmayan metot doğrudan çağrılamaz!
}

// ✅ DOĞRU:
public static void selamla() { ... }
```

---

### 2. Tuzak: Metot İçinde Metot Tanımlamaya Çalışmak
Java'da metotlar bir sınıfın gövdesinde tanımlanır. Bir metodun süslü parantezleri içinde başka bir metot **YAZILAMAZ**:

```java
public static void main(String[] args) {
    // ❌ DERLEME HATASI: Metot içinde metot tanımlanamaz!
    public static void topla(int a, int b) { ... }
}
```

---

### 3. Tuzak: `return` Satırının Altına Kod Yazmak (*Unreachable Code*)
`return` ifadesi metodu o anda bitirir. Altına yazılan kodlar asla çalışamayacağı için derleyici hata verir:

```java
public static int carp(int a, int b) {
    return a * b;
    System.out.println("İşlem bitti"); // ❌ DERLEME HATASI: Unreachable code!
}
```

---

### 4. Tuzak: Dönüş Tipi Olan Metotta Her Yoldan `return` Sağlamamak
Metot geriye bir değer döndürecekse (`int`, `String` vb.), tüm olası `if-else` dallarının bir `return` ile sonlanması zorunludur:

```java
// ❌ DERLEME HATASI: Missing return statement (Sayı <= 0 ise ne dönecek?)
public static String durumBelirle(int sayi) {
    if (sayi > 0) {
        return "Pozitif";
    }
    // HATA: else durumu unutulmuş!
}

// ✅ DOĞRU:
public static String durumBelirle(int sayi) {
    if (sayi > 0) {
        return "Pozitif";
    } else {
        return "Pozitif Değil";
    }
}
```

---

## 10. Kapsamlı Örnek Konsol Uygulaması: `HesapVeFaturaServisi`

Aşağıdaki program; parametresiz, tek parametreli, çok parametreli, `void` ve değer döndüren metotları, ayrıca Metot Aşırı Yükleme ile varsayılan parametre simülasyonunu tek bir sistemde birleştiren eksiksiz ve çalıştırılabilir bir uygulamadır:

```java
import java.util.Scanner;

public class HesapVeFaturaServisi {

    // 1. Ekrana Başlık Basan Yardımcı Metot (Parametresiz, void)
    public static void baslikYazdir() {
        System.out.println("==========================================");
        System.out.println("   KTÜ DİJİTAL HESAP VE FATURA SERVİSİ    ");
        System.out.println("==========================================");
    }

    // 2. Daire Alanı Hesaplayan Metot (double döner)
    public static double daireAlaniHesapla(double yaricap) {
        final double PI = 3.14159;
        return PI * yaricap * yaricap;
    }

    // 3. Dikdörtgen Alanı Hesaplayan Metot (Aşırı Yükleme - 2 Parametreli)
    public static double alanHesapla(double kisaKenar, double uzunKenar) {
        return kisaKenar * uzunKenar;
    }

    // 4. Kare Alanı Hesaplayan Metot (Aşırı Yükleme - Tek Parametreli)
    public static double alanHesapla(double kenar) {
        return alanHesapla(kenar, kenar); // 2 parametreli metoda yönlendirir
    }

    // 5. İndirimli Fatura Hesaplayıcı - Tam Parametreli:
    public static double faturaHesapla(double tutar, double kdvOrani, double indirimTutari) {
        double kdvliTutar = tutar + (tutar * (kdvOrani / 100.0));
        double nihaiTutar = kdvliTutar - indirimTutari;
        return (nihaiTutar < 0) ? 0 : nihaiTutar;
    }

    // 6. İndirimli Fatura Hesaplayıcı - Varsayılan Parametreli (İndirim yok = 0 TL):
    public static double faturaHesapla(double tutar, double kdvOrani) {
        return faturaHesapla(tutar, kdvOrani, 0.0);
    }

    // 7. İndirimli Fatura Hesaplayıcı - Tam Varsayılan (KDV = %20, İndirim = 0 TL):
    public static double faturaHesapla(double tutar) {
        return faturaHesapla(tutar, 20.0, 0.0);
    }

    // 8. Fatura Fişi Basan Metot (void)
    public static void fisiEkranaBas(String musteriAdi, double tutar) {
        System.out.println("\n---------------- FATURA FİŞİ ----------------");
        System.out.println("Müşteri Adı   : " + musteriAdi);
        System.out.printf("Ödenecek Tutar: %.2f TL\n", tutar);
        System.out.println("---------------------------------------------");
    }

    public static void main(String[] args) {
        baslikYazdir();

        // Alan hesaplama fonksiyonlarının testi:
        System.out.println("\n--- 1. GEOMETRİK HESAPLAMALAR ---");
        System.out.printf("Yarıçapı 4 olan dairenin alanı : %.2f\n", daireAlaniHesapla(4.0));
        System.out.println("Kenarları 5 ve 8 olan dikdörtgen alanı: " + alanHesapla(5.0, 8.0));
        System.out.println("Kenarı 6 olan karenin alanı           : " + alanHesapla(6.0));

        // Varsayılan parametreli fatura hesaplama testleri:
        System.out.println("\n--- 2. FATURA HESAPLAMA ÇEŞİTLERİ ---");

        // Durum A: Sadece tutar verildi (KDV varsayılan %20, indirim 0)
        double fatura1 = faturaHesapla(1000.0);
        fisiEkranaBas("Ahmet Kaya", fatura1);

        // Durum B: Tutar ve özel KDV verildi (%10), indirim varsayılan 0
        double fatura2 = faturaHesapla(1000.0, 10.0);
        fisiEkranaBas("Zeynep Demir", fatura2);

        // Durum C: Tutar, özel KDV (%20) ve 150 TL kupon indirimi uygulandı
        double fatura3 = faturaHesapla(1000.0, 20.0, 150.0);
        fisiEkranaBas("Murat Çelik", fatura3);
    }
}
```

---

## 11. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Pass-by-Value ve Çağrı Takibi)
Aşağıdaki programın konsol çıktısı ne olur? Adım adım takip ediniz:

```java
public class IzlemeSorusu {
    public static int islem(int a, int b) {
        a = a * 2;
        b = b + 5;
        return a + b;
    }

    public static void main(String[] args) {
        int x = 4;
        int y = 6;
        int z = islem(x, y);

        System.out.println("x: " + x + ", y: " + y + ", z: " + z);
    }
}
```
*İpucu: `islem` metodunda `a` ve `b` değiştiğinde `main`'deki `x` ve `y` değişir mi?*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kodda bulunan **3 farklı derleme hatasını** tespit ediniz ve düzeltiniz:

```java
public class HataBul {
    public static void main(String[] args) {
        int sonuc = topla(5, 10);
        System.out.println(sonuc);

        public static int topla(int a, int b) { // Hata 1 nerede?
            return a + b;
            System.out.println("Toplam bulundu"); // Hata 2 nerede?
        }
    }

    int kare(int n) { // Hata 3 nerede?
        return n * n;
    }
}
```

---

### 🌡️ Alıştırma 3: Sıcaklık Dönüştürücü Fonksiyon
Aşağıdaki iki metodu yazınız:
1. `celsiusToFahrenheit(double c)`: Santigrat dereceyi Fahrenhayt'a çevirsin (`F = C * 1.8 + 32`).
2. `fahrenheitToCelsius(double f)`: Fahrenhayt'ı Santigrat'a çevirsin (`C = (F - 32) / 1.8`).  
`main` metodunda 25 °C ve 98.6 °F değerleri için bu fonksiyonları çağırarak test ediniz.

---

### 🔁 Alıştırma 4: Asal Sayı Test Metodu (`boolean`)
Girdi olarak bir tamsayı alan ve bu sayı asal ise `true`, değilse `false` döndüren `public static boolean asalMi(int sayi)` metodunu yazınız. `main` içinde 1 ile 50 arasındaki tüm sayıları bu metotla tarayarak sadece asalları ekrana bastırınız.

---

## 12. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Her projede `main` metodunu sade tutmaya, iş yapan mantıkları bağımsız fonksiyonlara bölmeye ve varsayılan parametre ihtiyaçlarını **Method Overloading** ile çözmeye özen gösteriniz.

---

### 📋 Ödev 1: Akıllı Selamlama ve Profil Kartı Motoru
**Dosya Adı:** `ProfilKartiMotoru.java`

**Problem Tanımı:**  
Kullanıcılar için konsola biçimli profil kartları basan bir metot kümesi hazırlayınız:
1. `profilYazdir(String isim, String unvan, String sehir, int yas)`:
   - Dört bilgiyi de alıp etrafı çerçeveli şık bir kart şeklinde konsola yazar.
2. **Aşırı Yüklemeler ile Varsayılan Değerler:**
   - `profilYazdir(String isim, String unvan, String sehir)` ➡️ Yaş belirtilmezse varsayılan `-1` (Bilinmiyor) olarak ana metoda yönlendirilsin.
   - `profilYazdir(String isim, String unvan)` ➡️ Şehir belirtilmezse varsayılan `"Belirtilmedi"`, yaş `-1` olsun.
   - `profilYazdir(String isim)` ➡️ Unvan `"Kullanıcı"`, şehir `"Belirtilmedi"`, yaş `-1` olsun.
   - `profilYazdir()` ➡️ İsim `"Misafir"`, unvan `"Ziyaretçi"` olsun.
3. `main` metodunda bu 5 farklı metot varyasyonunu da çağırarak test ediniz.

---

### 📋 Ödev 2: Kapsamlı Matematik ve Geometri Kütüphanesi
**Dosya Adı:** `MatematikKutuphanesi.java`

**Problem Tanımı:**  
Java'nın hazır `Math` kütüphanesini kullanmadan kendi matematiksel fonksiyonlarınızı yazınız:
1. `public static long faktoriyel(int n)`: Sayının faktöriyelini hesaplasın.
2. `public static double usAl(double taban, int us)`: Pozitif üsler için döngüyle üs hesaplasın.
3. `public static int mutlakDeger(int sayi)`: Sayının mutlak değerini döndürsün.
4. `public static int ebob(int a, int b)`: İki sayının en büyük ortak bölenini bulsun.
5. `public static int ekok(int a, int b)`: İki sayının en küçük ortak katını formülle bulsun (`ekok = (a * b) / ebob(a, b)` - yazılan `ebob` metodunu çağırmalıdır!).

---

### 📋 Ödev 3: E-Ticaret Kargo ve Paketleme Ücret Motoru
**Dosya Adı:** `KargoHesaplayici.java`

**Problem Tanımı:**  
Bir alışveriş sitesinin sipariş tutarını hesaplayan metot sistemini kurunuz:
1. `kargoUcretiHesapla(double sepetTutari, double desi, boolean hizliTeslimat)`:
   - Eğer `sepetTutari >= 500 TL` ise standart kargo **0 TL (Ücretsiz)** olsun.
   - 500 TL altı ise kargo ücreti: `30 TL + (desi * 5 TL)`.
   - Eğer `hizliTeslimat == true` ise kargo tutarına **+40 TL ek ekspres ücreti** ilave edilsin.
2. **Varsayılan Parametre Simülasyonu (Overloading):**
   - `kargoUcretiHesapla(double sepetTutari, double desi)` ➡️ Hızlı teslimat belirtilmezse varsayılan `false` kabul edilsin.
   - `kargoUcretiHesapla(double sepetTutari)` ➡️ Desi belirtilmezse varsayılan `1.0 Desi`, hızlı teslimat `false` olsun.
3. Farklı sepet tutarları ve desi değerleriyle `main` içinde sonuçları kıyaslayınız.

---

### 📋 Ödev 4: Güçlü Parola Denetleme Servisi
**Dosya Adı:** `ParolaDenetleyici.java`

**Problem Tanımı:**  
Gelişmiş bir kayıt formu için şifre güvenliğini puanlayan metotlar tasarlayınız:
1. `public static boolean uzunlukYeterliMi(String parola, int minUzunluk)`: Parolanın en az `minUzunluk` kadar karaktere sahip olup olmadığını test etsin.
2. `uzunlukYeterliMi(String parola)`: Aşırı yükleme ile varsayılan minimum uzunluğu **8 karakter** kabul etsin.
3. `public static boolean rakamIceriyorMu(String parola)`: Parolanın içinde en az 1 rakam (`'0'` - `'9'`) olup olmadığını bir döngüyle karakterleri inceleyerek kontrol etsin.
4. `public static boolean buyukHarfIceriyorMu(String parola)`: En az 1 büyük harf içerip içermediğini kontrol etsin.
5. `public static String parolaGuvenlikSeviyesi(String parola)`:
   - Yukarıdaki 3 metodu çağırarak;
   - 3 şartı da sağlıyorsa: `"GÜÇLÜ"`
   - 2 şartı sağlıyorsa: `"ORTA"`
   - 1 veya daha az şartı sağlıyorsa: `"ZAYIF"` metnini döndürsün.
