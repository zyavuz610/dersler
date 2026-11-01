# 1. Java'da Hata ve İstisna Yönetimi (Exception Handling)

Bu doküman; Java uygulamalarında çalışma zamanında (runtime) meydana gelen hataların programı çökertmesini önlemek için kullanılan **İstisna Yönetimi (Exception Handling)** mekanizmasını, **`try-catch-finally`** bloklarını, **`Throwable`** hata hiyerarşisini, **Denetimli (Checked) vs. Denetimsiz (Unchecked)** istisna farkını, **`throw`** ve **`throws`** anahtar kelimelerini, **kendi özel hata sınıflarımızı (Custom Exceptions)** yazmayı ve **`try-with-resources`** ile otomatik kaynak kapatmayı ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Modül: Paketler ve Modüller (7_paketler)](../7_paketler/readme.md)
> - 🔜 [Sonraki Bölüm: En Yaygın Hazır Sınıflar ve Tarih-Saat API (2_hazir_siniflar_ve_tarih_saat.md)](./2_hazir_siniflar_ve_tarih_saat.md)

---

## 📌 İçindekiler
1. [İstisna (Exception) Nedir ve Neden Yönetilmelidir?](#1-istisna-exception-nedir-ve-neden-yönetilmelidir)
   - [Çalışma Zamanı Hataları (Runtime Errors)](#çalışma-zamanı-hataları-runtime-errors)
   - [Java'nın Hata Fırlatma ve Yakalama Felsefesi](#javanın-hata-fırlatma-ve-yakalama-felsefesi)
2. [Java Hata Hiyerarşisi (`Throwable`)](#2-java-hata-hiyerarşisi-throwable)
   - [`Error` (Geri Dönüşü Olmayan Sistem Hataları)](#error-geri-dönüşü-olmayan-sistem-hataları)
   - [`Exception` (Kurtarılabilir Program Hataları)](#exception-kurtarılabilir-program-hataları)
3. [Denetimli (Checked) vs. Denetimsiz (Unchecked) İstisnalar](#3-denetimli-checked-vs-denetimsiz-unchecked-istisnalar)
   - [Denetimsiz İstisnalar (`RuntimeException` ve Alt Sınıfları)](#denetimsiz-istisnalar-runtimeexception)
   - [Denetimli İstisnalar (`IOException`, `SQLException` vb.)](#denetimli-istisnalar)
   - [Karşılaştırma Tablosu](#karşılaştırma-tablosu)
4. [Temel Bloklar: `try`, `catch`, `finally`](#4-temel-bloklar-try-catch-finally)
   - [Çoklu Catch (Multiple Catch) Blokları ve Hiyerarşi Sırası Kuralı](#çoklu-catch-blokları)
   - [Java 7 Çoklu Yakalama (Multi-Catch: `ExceptionA | ExceptionB`)](#java-7-çoklu-yakalama)
   - [`finally` Bloğunun Önemi ve Çalışma Garantisi](#finally-bloğunun-önemi)
5. [`throw` ve `throws` Anahtar Kelimeleri](#5-throw-ve-throws-anahtar-kelimeleri)
   - [`throw`: Bilerek ve İsteyerek Hata Fırlatmak](#throw-bilerek-ve-isteyerek-hata-fırlatmak)
   - [`throws`: Sorumluluğu Çağıran Metoda Devretmek](#throws-sorumluluğu-çağıran-metoda-devretmek)
6. [Kendi Özel Hata Sınıflarımızı Yazmak (Custom Exceptions)](#6-kendi-özel-hata-sınıflarımızı-yazmak-custom-exceptions)
   - [Özel Checked Exception Tanımlama](#özel-checked-exception-tanımlama)
7. [Modern Kaynak Yönetimi: `try-with-resources`](#7-modern-kaynak-yönetimi-try-with-resources)
   - [`AutoCloseable` Arayüzü ve `close()` Metodu](#autocloseable-arayüzü)
8. [Adım Adım Çalıştırılabilir Kod Örnekleri](#8-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Temel `try-catch-finally` ve Aritmetik Hata (`BolmeIslemi.java`)](#örnek-1-temel-try-catch-finally)
   - [Örnek 2: Özel İstisna ile Banka Para Çekme Kontrolü (`BankaTest.java`)](#örnek-2-özel-istisna-ile-banka-para-çekme-kontrolü)
9. [Sık Yapılan Hatalar ve Önemli Kurallar](#9-sık-yapılan-hatalar-ve-önemli-kurallar)
10. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#10-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
11. [Ödev Konuları ve Mini Projeler](#11-ödev-konuları-ve-mini-projeler)

---

## 1. İstisna (Exception) Nedir ve Neden Yönetilmelidir?

Bir program yazarken sözdizimi (syntax) hatası yaparsanız derleyici (`javac`) sizi uyarır ve program derlenmez. 
Ancak kodunuz kusursuz derlense bile, çalışma anında (runtime) **öngörülemeyen dış etkenler** ortaya çıkabilir:
- Kullanıcı bir sayıyı `0`'a bölmeye çalışabilir.
- Açılmak istenen dosya diskte bulunamayabilir.
- İnternet bağlantısı aniden kopabilir.
- Olmayan bir dizi indeksine (`dizi[99]`) erişilmeye çalışılabilir.

İşte programın normal akışını bozan bu tür beklenmedik durumlara **İstisna (Exception)** denir.

> 💡 **İstisna Yönetiminin Amacı:** Programın beklenmedik bir hatayla karşılaşınca kullanıcıya çirkin bir hata yığını (*Stack Trace*) basıp "aniden kapanmasını (crash)" önlemek; hatayı yakalayıp güvenli bir kurtarma planı işletmektir.

---

## 2. Java Hata Hiyerarşisi (`Throwable`)

Java'da tüm hata ve istisna sınıfları `java.lang.Throwable` sınıfından türer:

```
                         [ java.lang.Throwable ]
                                   |
                 +-----------------+-----------------+
                 |                                   |
           [ java.lang.Error ]             [ java.lang.Exception ]
        (Kurtarılamaz Donanım/JVM)            (Yazılım İstisnaları)
        • OutOfMemoryError                             |
        • StackOverflowError              +------------+------------+
                                          |                         |
                                 [ RuntimeException ]      [ Diğer Exception'lar ]
                                  (Denetimsiz Hatalar)       (Denetimli Hatalar)
                                  • NullPointerException     • IOException
                                  • ArithmeticException      • SQLException
                                  • ArrayIndexOutOfBounds    • ClassNotFoundException
```

### `Error` vs. `Exception`
- **`Error`:** JVM seviyesinde meydana gelen, programın çözemeyeceği devasa felaketlerdir (örn: `OutOfMemoryError` - bilgisayarın RAM'i bitti, `StackOverflowError` - sonsuz döngü yığını patlattı). **`try-catch` ile yakalanmaya çalışılmaz.**
- **`Exception`:** Bizim kodumuzdan veya dış kaynaklardan kaynaklanan, programcının öngörüp yakalayabileceği ve kurtarabileceği durumlardır.

---

## 3. Denetimli (Checked) vs. Denetimsiz (Unchecked) İstisnalar

Java'yı C# veya Python'dan ayıran en belirgin mimari özellik **Checked Exception** kavramıdır:

### 1. Denetimsiz İstisnalar (`RuntimeException` ve Alt Sınıfları)
- Tamamen **yazılımcının mantık veya kodlama hatalarından** kaynaklanır.
- Derleyici bunları kontrol etmez; yazılımcıyı `try-catch` yazmaya **zorlamaz**.
- İyi kod yazılarak engellenmelidir (örn: `if (nesne != null)` yazarak `NullPointerException` önlenmelidir).

### 2. Denetimli İstisnalar (`Checked Exception`)
- `RuntimeException` sınıfından **türemeyen** tüm diğer `Exception` sınıflarıdır (`IOException`, `SQLException` vb.).
- Programın dış dünyayla (dosya, ağ, veritabanı) iletişiminde her an ortaya çıkabilecek risklerdir.
- **Derleyici (`javac`) bu hataları bilir ve sizi ZORLAR:** *"Bu kodu ya bir `try-catch` bloğuna alacaksın ya da metodun başına `throws` yazarak sorumluluğu devredeceksin! Yoksa kodunu derlemem!"*

---

### Karşılaştırma Tablosu

| Kriter | Denetimli (Checked) | Denetimsiz (Unchecked) |
| :--- | :--- | :--- |
| **Kök Sınıfı** | `java.lang.Exception` | `java.lang.RuntimeException` |
| **Derleyici Kontrolü** | **ZORUNLUDUR** (Derleme hatası verir) | Kontrol edilmez (İsteğe bağlıdır) |
| **Örnekler** | `FileNotFoundException`, `SQLException` | `NullPointerException`, `ArithmeticException` |
| **Sebebi** | Dış dünya şartları (dosya yok, ağ koptu) | Kötü yazılmış kod / Mantık hatası |

---

## 4. Temel Bloklar: `try`, `catch`, `finally`

```java
try {
    // 1. Riskli kodlar buraya yazılır. Hata çıkarsa anında durur ve catch'e atlar.
} catch (HataTuru e) {
    // 2. Hata çıktığında çalışacak kurtarma kodu (Fallback / Error Handling).
} finally {
    // 3. Hata çıksa da çıkmasa da HER DURUMDA kesinlikle çalışacak temizlik kodu!
}
```

### Çoklu Catch ve Sıralama Kuralı
Birden fazla farklı hata türü ayrı ayrı yakalanabilir.

> 🔴 **ALTIN KURAL:** Alt sınıf olan spesifik hatalar **ÜSTE**, genel ata olan `Exception` sınıfı ise en **ALTA** yazılmalıdır! Genel hatayı üste yazarsanız alttaki spesifik catch blokları ulaşılamaz (*unreachable code*) hale gelir.

```java
try {
    // Riskli işlemler
} catch (ArithmeticException e) {
    System.out.println("Sıfıra bölme hatası!");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Dizi boyutu aşıldı!");
} catch (Exception e) {
    System.out.println("Beklenmedik genel bir hata oluştu: " + e.getMessage());
}
```

---

### Java 7 Çoklu Yakalama (Multi-Catch)
Aynı kurtarma kodunu çalıştıracak birden fazla hata türü pipe (`|`) karakteriyle tek bir blokta birleştirilebilir:

```java
try {
    // İşlem
} catch (ArithmeticException | NullPointerException e) {
    System.out.println("Hesaplama veya boş referans hatası!");
}
```

---

### `finally` Bloğunun Önemi
`finally` bloğu, `try` içinde `return` komutu çalışsa bile, hata fırlatılsa bile **MUTLAKA ÇALIŞIR**. Genellikle açık kalan veritabanı bağlantılarını, soketleri ve dosyaları kapatmak için kullanılır.

---

## 5. `throw` ve `throws` Anahtar Kelimeleri

Bu iki kelime sıkça karıştırılır:

| Anahtar Kelime | Görevi | Nerede Yazılır? |
| :--- | :--- | :--- |
| **`throw`** | Belirli bir şartta **bizzat hata fırlatmak (tetiklemek)** için kullanılır. | Metot gövdesi içinde (`throw new Exception()`) |
| **`throws`** | Bu metodun içinden fırlatılabilecek denetimli hataları **metot imzasında deklare eder**. | Metot başlığında (`public void oku() throws IOException`) |

```java
public void paraCek(double miktar) throws YetersizBakiyeException {
    if (miktar > bakiye) {
        // Hata nesnesi üretip bilerek fırlatıyoruz:
        throw new YetersizBakiyeException("Bakiyeniz yetersiz!");
    }
    bakiye -= miktar;
}
```

---

## 6. Kendi Özel Hata Sınıflarımızı Yazmak (Custom Exceptions)

Java'nın standart hata sınıfları iş mantığınızı (business logic) tam anlatamayabilir. Kendi domain hatalarınızı üretmek için `Exception` (Checked) veya `RuntimeException` (Unchecked) sınıfından miras alırsınız:

```java
// Kendi Checked Hata Sınıfımız:
public class YetersizBakiyeException extends Exception {
    private double eksikMiktar;

    public YetersizBakiyeException(String mesaj, double eksikMiktar) {
        super(mesaj);
        this.eksikMiktar = eksikMiktar;
    }

    public double getEksikMiktar() {
        return eksikMiktar;
    }
}
```

---

## 7. Modern Kaynak Yönetimi: `try-with-resources`

Java 7 öncesinde açılan dosyaları `finally` içinde kapatmak için satırlarca `null` kontrolü ve iç içe `try-catch` yazmak gerekirdi.

Java 7 ile gelen **`try-with-resources`** yapısı sayesinde, `AutoCloseable` arayüzünü uygulayan tüm nesneler `try` parantezinde açıldığında, blok bittiği an **otomatik olarak kapatılır (`close()` tetiklenir)**:

```java
// try parantezi içinde açılan dosya kendiliğinden kapanır (finally yazmaya gerek yoktur!):
try (Scanner scanner = new Scanner(new File("veri.txt"))) {
    while (scanner.hasNextLine()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException e) {
    System.out.println("Dosya bulunamadı: " + e.getMessage());
}
```

---

## 8. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Temel `try-catch-finally` ve Aritmetik Hata

#### `BolmeIslemi.java`
```java
public class BolmeIslemi {
    public static int bol(int bolunen, int bolen) {
        return bolunen / bolen;
    }

    public static void main(String[] args) {
        int a = 20;
        int b = 0;

        System.out.println("Hesaplama başlatılıyor...");

        try {
            int sonuc = bol(a, b);
            System.out.println("Sonuç: " + sonuc); // Çalışmayacak
        } catch (ArithmeticException e) {
            System.err.println("HATA YAKALANDI: Bir sayı sıfıra bölünemez!");
            System.err.println("Hata Detayı: " + e.getMessage());
        } finally {
            System.out.println("Temizlik İşlemi: finally bloğu her koşulda çalıştı.");
        }

        System.out.println("Program çökmeksizin normal akışına devam ediyor.");
    }
}
```

#### Konsol Çıktısı:
```text
Hesaplama başlatılıyor...
HATA YAKALANDI: Bir sayı sıfıra bölünemez!
Hata Detayı: / by zero
Temizlik İşlemi: finally bloğu her koşulda çalıştı.
Program çökmeksizin normal akışına devam ediyor.
```

---

### Örnek 2: Özel İstisna ile Banka Para Çekme Kontrolü

#### `YetersizBakiyeException.java` & `BankaHesabi.java` & `BankaTest.java`
```java
// 1. Özel İstisna Sınıfı
class YetersizBakiyeException extends Exception {
    public YetersizBakiyeException(String mesaj) {
        super(mesaj);
    }
}

// 2. Banka Hesabı Sınıfı
class BankaHesabi {
    private String hesapNo;
    private double bakiye;

    public BankaHesabi(String hesapNo, double bakiye) {
        this.hesapNo = hesapNo;
        this.bakiye = bakiye;
    }

    public void paraCek(double miktar) throws YetersizBakiyeException {
        if (miktar > bakiye) {
            double eksik = miktar - bakiye;
            throw new YetersizBakiyeException("Yetersiz Bakiye! Eksik Miktar: " + eksik + " TL");
        }
        bakiye -= miktar;
        System.out.println(miktar + " TL çekildi. Kalan Bakiye: " + bakiye + " TL");
    }
}

// 3. Test Sınıfı
public class BankaTest {
    public static void main(String[] args) {
        BankaHesabi hesap = new BankaHesabi("TR12345", 500.0);

        try {
            System.out.println("1. İşlem: 300 TL çekiliyor...");
            hesap.paraCek(300.0);

            System.out.println("\n2. İşlem: 400 TL çekiliyor...");
            hesap.paraCek(400.0); // Hata fırlatılacak!

        } catch (YetersizBakiyeException e) {
            System.err.println("[İŞLEM REDDEDİLDİ] " + e.getMessage());
        }
    }
}
```

#### Konsol Çıktısı:
```text
1. İşlem: 300 TL çekiliyor...
300.0 TL çekildi. Kalan Bakiye: 200.0 TL

2. İşlem: 400 TL çekiliyor...
[İŞLEM REDDEDİLDİ] Yetersiz Bakiye! Eksik Miktar: 200.0 TL
```

---

## 9. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. Boş Catch Bloğu Bırakmak (Hataları Yutmak - Silent Failure)
```java
try {
    dosyayiOku();
} catch (Exception e) {
    // ❌ ASLA YAPMAYIN! Hatanın neden çıktığını kimse anlayamaz.
}
```
En azından `e.printStackTrace();` veya bir log kaydı basılmalıdır.

---

### 2. Üst Sınıf `Exception`'ı Üst Sıraya Yazmak
```java
try { ... }
catch (Exception e) { ... } // ❌ Alttakiler ulaşılamaz olur!
catch (IOException e) { ... } // DERLEME HATASI!
```

---

## 10. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Çıktı Tahmini
Aşağıdaki kod çalıştığında konsola tam olarak hangi sırayla mesajlar yazılır?

```java
public class SoruTest {
    public static void main(String[] args) {
        try {
            System.out.print("1 ");
            int x = 10 / 0;
            System.out.print("2 ");
        } catch (ArithmeticException e) {
            System.out.print("3 ");
        } finally {
            System.out.print("4 ");
        }
        System.out.print("5");
    }
}
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:** `1 3 4 5`
**Açıklama:**
1. `"1 "` basılır.
2. `10 / 0` anında hata verir, `"2 "` satırı atlanır.
3. `catch` bloğu yakalar, `"3 "` basılır.
4. `finally` bloğu zorunlu çalışır, `"4 "` basılır.
5. `try-catch` sonrası normal kod devam eder, `"5"` basılır.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kodda derleyici neden hata vermektedir?

```java
public class DosyaOkuyucu {
    public void oku() {
        throw new java.io.IOException("Dosya bozuk!");
    }
}
```

<details>
<summary>🔍 <b>Hata ve Çözümü Görmek İçin Tıklayın</b></summary>

**Hata:** `IOException` bir **Checked (Denetimli)** hatadır. Metodun içinde fırlatıldığı halde ya `try-catch` içine alınmamış ya da metodun imzasına `public void oku() throws IOException` deklare edilmemiştir.
</details>

---

### Alıştırma 3: Yaş Kontrol İstisnası
0'dan küçük veya 120'den büyük bir yaş girildiğinde fırlatılacak bir `GecersizYasException` sınıfı yazıp test ediniz.

---

## 11. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Güvenli Sayı Girişi ve Hesap Makinesi (`GuvenliHesapMakinesi.java`)

**Gereksinimler:**
1. Kullanıcıdan `Scanner` ile iki tamsayı ve bir işlem operatörü (+, -, *, /) alınız.
2. `InputMismatchException` (kullanıcı sayı yerine harf girerse) ve `ArithmeticException` (sıfıra bölme) hatalarını yakalayınız.
3. Hata olduğunda program kapanmasın, kullanıcıdan doğru giriş yapılana kadar tekrar sayı istensin.

---

### 🟡 Ödev 2 (Orta Seviye): E-Posta Formatı Doğrulama ve Özel Hata (`EpostaKontrol.java`)

**Gereksinimler:**
1. `GecersizEpostaException` adında bir Checked Exception sınıfı tasarlayınız.
2. `EpostaKayit` sınıfı:
   - `void kaydet(String eposta)` metodu:
     - E-posta `@` içermiyorsa veya `.` ile bitmiyorsa `throw new GecersizEpostaException("Hatalı format!")` fırlatsın.
3. Test sınıfında geçerli ve geçersiz e-postalar girerek sistemin hata yönetimini test ediniz.

---

### 🔴 Ödev 3 (İleri Seviye): Kendi `AutoCloseable` Veritabanı Oturum Yöneticiniz (`OturumYonetici.java`)

**Gereksinimler:**
1. `VeritabaniOturumu` adında bir sınıf oluşturunuz ve `AutoCloseable` arayüzünü uygulayınız (`implements AutoCloseable`).
2. `sorguCalistir(String sql)` metodu: Eğer SQL içinde `"HATA"` kelimesi geçerse özel bir `SorguHatasiException` fırlatsın.
3. `try-with-resources` bloğu içinde oturumu açınız, kasıtlı olarak bir hata fırlatınız ve hata fırlatılsa dahi `close()` metodunun oturumu güvenle kapattığını konsol çıktılarıyla kanıtlayınız.
