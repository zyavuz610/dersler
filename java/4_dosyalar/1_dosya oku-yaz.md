# 1. Java'da Dosya Yönetimi: Sıralı Okuma ve Yazma Kılavuzu

Bu rehber; Java programlama dilinde program kapandığında verilerin kaybolmasını engelleyip kalıcı olarak diskte saklanmasını sağlayan **Dosya Yönetimini (File I/O)**, `File` sınıfını, dosya açma modlarını (**Üzerine Yazma vs Sonuna Ekleme / Append**), sıralı okuma ve yazma sınıflarını (**`Scanner`, `BufferedReader`, `FileWriter`, `PrintWriter`**), modern kaynak yönetimini (**`try-with-resources`**), istisna yönetimini ve öğrencilerin çözebileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 4_dosyalar**  
> Bu modül, verilerimizi geçici RAM belleğinden çıkarıp sabit diske (HDD/SSD) kalıcı olarak kaydetmemizi ve daha sonra bu dosyaları satır satır okuyup işlememizi sağlar.

---

## 📌 İçindekiler
1. [Dosya İşlemlerine Giriş ve Kalıcılık (Persistence) Kavramı](#1-dosya-işlemlerine-giriş-ve-kalıcılık-persistence-kavramı)
2. [Java `File` Sınıfı ve Temel Dosya İşlemleri](#2-java-file-sınıfı-ve-temel-dosya-işlemleri)
3. [Dosya Açma Modları: Üzerine Yazma vs Sonuna Ekleme (Append)](#3-dosya-açma-modları-üzerine-yazma-vs-sonuna-ekleme-append)
4. [Sıralı Dosya Yazma Yöntemleri (`FileWriter`, `BufferedWriter`, `PrintWriter`)](#4-sıralı-dosya-yazma-yöntemleri-filewriter-bufferedwriter-printwriter)
5. [Sıralı Dosya Okuma Yöntemleri (`Scanner` ve `BufferedReader`)](#5-sıralı-dosya-okuma-yöntemleri-scanner-ve-bufferedreader)
6. [Modern Kaynak Yönetimi: `try-with-resources` Mimarisi](#6-modern-kaynak-yönetimi-try-with-resources-mimarisi)
7. [G/Ç İstisnaları (I/O Exceptions) ve Hata Yönetimi](#7-gç-istisnaları-io-exceptions-ve-hata-yönetimi)
8. [En Sık Yapılan Hatalar ve Tuzaklar](#8-en-sık-yapılan-hatalar-ve-tuzaklar)
9. [Kapsamlı Örnek Konsol Uygulaması: `OgrenciKayitVeRaporSistemi`](#9-kapsamlı-örnek-konsol-uygulaması-ogrencikayitveraporsistemi)
10. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#10-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
11. [Ödev Projeleri](#11-ödev-projeleri)

---

## 1. Dosya İşlemlerine Giriş ve Kalıcılık (Persistence) Kavramı

Şimdiye kadar yazdığımız tüm programlarda tanımladığımız değişkenler, diziler ve nesneler bilgisayarın **Geçici Belleğinde (RAM - Random Access Memory)** yaşadı. Program çalıştığı sürece veriler bellekteydi; ancak program kapandığı veya elektrik kesildiği anda tüm veriler buharlaşıp kayboldu.

Gerçek dünya yazılımlarında (Bankacılık, e-ticaret, oyun kayıtları, öğrenci bilgi sistemleri) verilerin kalıcı olması gerekir.

- **Kalıcılık (Data Persistence):** Verilerin programın çalışma ömründen bağımsız olarak ikincil depolama birimlerinde (Hard Disk, SSD) dosya veya veritabanı şeklinde saklanmasıdır.
- **Sıralı Erişim (Sequential Access):** Tıpkı eski kaset bantları gibi, dosyanın en başından başlanarak verilerin sırasıyla satır satır veya karakter karakter okunması ya da yazılması işlemidir.

---

## 2. Java `File` Sınıfı ve Temel Dosya İşlemleri

`java.io.File` sınıfı, diskteki bir dosya veya dizinin (klasörün) kendisini temsil eder. Bu sınıf doğrudan dosyanın içine yazı yazmaz veya içini okumaz; dosyanın **var olup olmadığını, boyutunu, adını, yolunu ve izinlerini** denetler.

### 💻 Temel `File` Metotları ve Kullanımı:

```java
import java.io.File;
import java.io.IOException;

public class DosyaIslemleriGiris {
    public static void main(String[] args) {
        // Dosya nesnesi oluşturulur (Henüz diskte oluşturulmadı! Sadece bir yol temsil eder):
        File dosya = new File("ogrenciler.txt");

        try {
            // 1. Dosya diskte var mı kontrolü:
            if (dosya.exists()) {
                System.out.println("Dosya zaten mevcut.");
            } else {
                // Diskte yeni ve boş bir dosya oluşturur:
                boolean olustuMu = dosya.createNewFile();
                if (olustuMu) {
                    System.out.println("✅ Dosya başarıyla oluşturuldu: " + dosya.getName());
                }
            }

            // 2. Dosya Meta Verilerini Okuma:
            System.out.println("Dosya Adı        : " + dosya.getName());
            System.out.println("Tam Dosya Yolu   : " + dosya.getAbsolutePath());
            System.out.println("Dosya Boyutu     : " + dosya.length() + " Bayt");
            System.out.println("Okunabilir mi?   : " + dosya.canRead());
            System.out.println("Yazılabilir mi?  : " + dosya.canWrite());
            System.out.println("Bu bir klasör mü?: " + dosya.isDirectory());

        } catch (IOException e) {
            System.out.println("❌ Dosya işlemi sırasında hata oluştu: " + e.getMessage());
        }
    }
}
```

---

## 3. Dosya Açma Modları: Üzerine Yazma vs Sonuna Ekleme (Append)

Java'da bir dosyaya yazma amacıyla nesne açarken en kritik karar şudur: **Eski veriler silinsin mi, yoksa altına yeni veriler mi eklensin?**

### 1. Üzerine Yazma Modu (Overwrite Mode - Varsayılan):
Eğer dosya zaten mevcutsa, içindeki tüm eski veriler **tamamen silinir (sıfırlanır)** ve dosya en baştan yazılmaya başlanır.
```java
// İkisi de aynıdır: Eski içerik tamamen silinir!
FileWriter yazici1 = new FileWriter("rapor.txt");
FileWriter yazici2 = new FileWriter("rapor.txt", false); // append = false
```

### 2. Sonuna Ekleme Modu (Append Mode):
Dosya mevcutsa eski verilere **asla dokunulmaz**. Yeni yazılan her şey dosyanın en son satırının altına eklenir (Log kayıtları, chat geçmişi vb. için kullanılır).
```java
// İkinci parametre 'true' verilirse dosya sonuna ekleme modunda açılır:
FileWriter yazici = new FileWriter("gunluk.txt", true); // append = true
```

---

## 4. Sıralı Dosya Yazma Yöntemleri

Java'da metin dosyalarına yazmak için hiyerarşik 3 temel sınıf kullanılır:

```text
FileWriter (Karakter tabanlı temel yazıcı)
    └── BufferedWriter (Tampon bellekli, hızlı satır yazıcı)
            └── PrintWriter (println, printf destekli konforlu yazıcı)
```

### 💻 1. `FileWriter` ile Temel Yazma
Karakterleri doğrudan diske yazar (Küçük veriler için uygundur):
```java
FileWriter fw = new FileWriter("notlar.txt", true); // append modu
fw.write("Java Programlama Dersi\n");
fw.close();
```

---

### 💻 2. `BufferedWriter` ile Yüksek Performanslı Yazma
Büyük dosyalarda her harf için diske gitmek sistemi yavaşlatır. `BufferedWriter`, verileri önce RAM'deki bir tampon bellekte (*buffer*) biriktirir, dolunca tek seferde diske boşaltır.

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HizliYazici {
    public static void main(String[] args) {
        // try-with-resources ile otomatik dosya kapatma:
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hizli_kayit.txt", true))) {
            bw.write("1. Satır: Veritabanı yedeği alındı.");
            bw.newLine(); // İşletim sistemine uygun alt satıra geçiş (\n veya \r\n)
            bw.write("2. Satır: Kullanıcı oturumu kapatıldı.");
            bw.newLine();
            System.out.println("✅ Veriler tampon bellek üzerinden başarıyla yazıldı.");
        } catch (IOException e) {
            System.err.println("Yazma hatası: " + e.getMessage());
        }
    }
}
```

---

### 💻 3. `PrintWriter` ile Biçimli Yazma (En Kolay ve Tercih Edilen)
Konsola `System.out.println()` veya `printf()` yazar gibi doğrudan dosyaya yazmamızı sağlar:

```java
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class PrintYaziciDemo {
    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileWriter("faturalar.txt", true))) {
            out.println("=========== FATURA DETAYI ===========");
            out.printf("Müşteri: %-15s | Tutar: %10.2f TL\n", "Ahmet Yılmaz", 1450.75);
            out.printf("Müşteri: %-15s | Tutar: %10.2f TL\n", "Ayşe Demir", 890.00);
            out.println("======================================");
            System.out.println("✅ Biçimli fatura dosyaya kaydedildi.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## 5. Sıralı Dosya Okuma Yöntemleri

### 💻 Yöntem 1: `Scanner` Sınıfı ile Okuma (Basit ve Tipli Okuma)
`Scanner`'ı klavyeden veri almak yerine bir `File` nesnesine bağlayarak dosyanın başından sonuna kadar kolayca okuyabiliriz:

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerDosyaOkuma {
    public static void main(String[] args) {
        File dosya = new File("sayilar.txt");

        try (Scanner okuyucu = new Scanner(dosya)) {
            // Dosyada okunacak satır kaldığı sürece döner:
            while (okuyucu.hasNextLine()) {
                String satir = okuyucu.nextLine();
                System.out.println("Okunan: " + satir);
            }
        } catch (FileNotFoundException e) {
            System.out.println("❌ Hata: Okunacak dosya bulunamadı! " + e.getMessage());
        }
    }
}
```

---

### 💻 Yöntem 2: `BufferedReader` ile Okuma (Standart ve Yüksek Performanslı)
Büyük boyutlu metin dosyalarını satır satır okumanın en hızlı ve endüstri standardı yoludur:

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderOkuma {
    public static void main(String[] args) {
        // Klasik while ((satir = br.readLine()) != null) Kalıbı:
        try (BufferedReader br = new BufferedReader(new FileReader("metin.txt"))) {
            String satir;
            int satirNo = 1;

            // readLine() dosya sonuna (EOF - End of File) geldiğinde 'null' döner:
            while ((satir = br.readLine()) != null) {
                System.out.println(satirNo + ". Satır: " + satir);
                satirNo++;
            }
        } catch (IOException e) {
            System.err.println("Dosya okunamadı: " + e.getMessage());
        }
    }
}
```

---

## 6. Modern Kaynak Yönetimi: `try-with-resources` Mimarisi

Eski Java sürümlerinde açılan bir dosyayı kapatmak için karmaşık `try-catch-finally` blokları yazılırdı. Eğer dosya kapatılmazsa (*close edilmezse*):
- Dosya işletim sisteminde kilitli kalır (*File Lock*),
- Başka bir program dosyayı silemez veya değiştiremez,
- Bellek sızıntısı (*Resource Leak*) oluşur.

Java 7 ile gelen **`try-with-resources`** sözdizimi, `AutoCloseable` arayüzünü uygulayan tüm dosya nesnelerini blok bitiminde (hata çıksa bile) **otomatik olarak kapatır**:

```java
// Parantez içinde açılan kaynaklar otomatik olarak close() edilir:
try (BufferedReader br = new BufferedReader(new FileReader("veri.txt"));
     BufferedWriter bw = new BufferedWriter(new FileWriter("kopya.txt"))) {

    String satir;
    while ((satir = br.readLine()) != null) {
        bw.write(satir);
        bw.newLine();
    }
} catch (IOException e) {
    e.printStackTrace();
}
// 'br' ve 'bw' burada kesinlikle güvenle kapatılmıştır! Manuel close() yazmaya gerek yoktur.
```

---

## 7. G/Ç İstisnaları (I/O Exceptions) ve Hata Yönetimi

Dosya işlemleri doğrudan harici donanımla (disk) konuştuğu için Java'da **Denetimli İstisna (Checked Exception)** sınıfındadır. Yani derleyici bu hataları `try-catch` içine almanızı veya metoda `throws IOException` eklemenizi **zorunlu tutar**.

| İstisna Türü | Ne Zaman Fırlatılır? |
| :--- | :--- |
| **`FileNotFoundException`** | Okunmak istenen dosya diskte belirtilen yolda bulunamadığında. |
| **`IOException`** | Dosya okuma/yazma sırasında diskin dolması, iznin olmaması, dosyanın bozulması durumunda. |
| **`SecurityException`** | İşletim sisteminin o klasöre yazma izni vermediği durumlarda. |

---

## 8. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: Dosya Yolunda Ters Bölü (`\`) Karakteri Hatası
Windows dosya yollarında `\` sembolü kaçış (*escape*) karakteridir. Tek başına yazılırsa derleme hatası verir:

```java
// ❌ DERLEME HATASI (Geçersiz kaçış dizisi):
// File f = new File("C:\dosyalar\notlar.txt");

// ✅ DOĞRU YOL 1 (Çift ters bölü):
File f1 = new File("C:\\dosyalar\\notlar.txt");

// ✅ DOĞRU YOL 2 (Evrensel düz bölü - Java Windows'ta da destekler!):
File f2 = new File("C:/dosyalar/notlar.txt");
```

---

### 2. Tuzak: `close()` veya `flush()` Çağırmayı Unutmak
`BufferedWriter` gibi tamponlu sınıflar veriyi önce RAM'de tutar. Eğer dosyayı kapatmazsanız veya `flush()` demezseniz, program sonlandığında RAM'deki veriler diske aktarılmadan **kaybolur** ve dosya boş kalır! `try-with-resources` kullanmak bu sorunu kökten çözer.

---

### 3. Tuzak: Append Modunu Unutup Eski Dosyayı Yanlışlıkla Silmek

```java
// ❌ FELAKET: append parametresi unutulduğu için eski 10.000 satırlık veri silinir!
FileWriter fw = new FileWriter("musteriler.txt"); 

// ✅ DOĞRU: İkinci parametreye 'true' yazılmalıdır:
FileWriter fw = new FileWriter("musteriler.txt", true);
```

---

## 9. Kapsamlı Örnek Konsol Uygulaması: `OgrenciKayitVeRaporSistemi`

Aşağıdaki program; öğrenci bilgilerini (No, Ad Soyad, Vize, Final) konsoldan alıp bir CSV metin dosyasına (`ogrenciler.csv`) **append modunda** ekleyen, ardından bu dosyayı baştan sona sıralı okuyarak her öğrencinin başarı ortalamasını hesaplayan ve detaylı bir karne raporunu (`karne_raporu.txt`) dosyasına yazdıran eksiksiz bir uygulamadır:

```java
import java.io.*;
import java.util.Scanner;

public class OgrenciKayitVeRaporSistemi {
    private static final String VERI_DOSYASI = "ogrenciler.csv";
    private static final String RAPOR_DOSYASI = "karne_raporu.txt";

    // 1. Dosyaya Yeni Öğrenci Ekleyen Metot (Append Modu)
    public static void ogrenciEkle(int no, String adSoyad, double vize, double finalNotu) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(VERI_DOSYASI, true))) {
            // CSV Formatı: No,Ad Soyad,Vize,Final
            pw.println(no + "," + adSoyad + "," + vize + "," + finalNotu);
            System.out.println("✅ Öğrenci başarıyla dosyaya kaydedildi.");
        } catch (IOException e) {
            System.err.println("❌ Dosyaya yazma hatası: " + e.getMessage());
        }
    }

    // 2. Dosyayı Baştan Sona Sıralı Okuyup Konsola Basan Metot
    public static void ogrencileriListele() {
        File dosya = new File(VERI_DOSYASI);
        if (!dosya.exists()) {
            System.out.println("ℹ️ Henüz kayıtlı öğrenci dosyası bulunmuyor.");
            return;
        }

        System.out.println("\n---------------- DOSYADAKİ ÖĞRENCİ LİSTESİ ----------------");
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(",");
                if (p.length == 4) {
                    System.out.printf("No: %-5s | Ad: %-15s | Vize: %-5s | Final: %-5s\n", p[0], p[1], p[2], p[3]);
                }
            }
        } catch (IOException e) {
            System.err.println("Okuma hatası: " + e.getMessage());
        }
        System.out.println("----------------------------------------------------------");
    }

    // 3. Dosyayı Okuyup Hesaplama Yapan ve Yeni Rapor Dosyasına Yazan Metot
    public static void karneRaporuUret() {
        File kaynak = new File(VERI_DOSYASI);
        if (!kaynak.exists()) {
            System.out.println("❌ Rapor üretilecek veri dosyası yok!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(kaynak));
             PrintWriter out = new PrintWriter(new FileWriter(RAPOR_DOSYASI, false))) {

            out.println("===============================================================");
            out.println("          KTÜ BİLGİSAYAR MÜHENDİSLİĞİ DÖNEM KARNESİ            ");
            out.println("===============================================================");
            out.printf("%-6s %-18s %-7s %-7s %-9s %-8s\n", "NO", "AD SOYAD", "VİZE", "FİNAL", "ORTALAMA", "DURUM");
            out.println("---------------------------------------------------------------");

            String satir;
            int toplamOgrenci = 0;
            double sinifNotToplami = 0.0;

            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(",");
                if (p.length == 4) {
                    int no = Integer.parseInt(p[0].trim());
                    String ad = p[1].trim();
                    double vize = Double.parseDouble(p[2].trim());
                    double finalNotu = Double.parseDouble(p[3].trim());

                    double ortalama = (vize * 0.4) + (finalNotu * 0.6);
                    String durum = (ortalama >= 50.0 && finalNotu >= 50.0) ? "GEÇTİ" : "KALDI";

                    out.printf("%-6d %-18s %-7.1f %-7.1f %-9.2f %-8s\n", no, ad, vize, finalNotu, ortalama, durum);

                    toplamOgrenci++;
                    sinifNotToplami += ortalama;
                }
            }

            out.println("---------------------------------------------------------------");
            if (toplamOgrenci > 0) {
                out.printf("Toplam Öğrenci: %d | Sınıf Genel Ortalaması: %.2f\n", toplamOgrenci, (sinifNotToplami / toplamOgrenci));
            }
            out.println("===============================================================");

            System.out.println("✅ Karne raporu başarıyla üretildi: '" + RAPOR_DOSYASI + "'");

        } catch (IOException e) {
            System.err.println("Rapor üretim hatası: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int secim;

        do {
            System.out.println("\n==========================================");
            System.out.println("     ÖĞRENCİ DOSYA YÖNETİM SİSTEMİ        ");
            System.out.println("==========================================");
            System.out.println("1 - Yeni Öğrenci Kaydı Ekle (Append)");
            System.out.println("2 - Kayıtlı Öğrencileri Oku ve Listele");
            System.out.println("3 - Başarı Raporu / Karne Dosyası Üret");
            System.out.println("0 - Çıkış Yap");
            System.out.print("Seçiminiz: ");
            secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {
                case 1 -> {
                    System.out.print("Öğrenci No: ");
                    int no = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ad Soyad: ");
                    String ad = scanner.nextLine();
                    System.out.print("Vize Notu: ");
                    double vize = scanner.nextDouble();
                    System.out.print("Final Notu: ");
                    double fin = scanner.nextDouble();

                    ogrenciEkle(no, ad, vize, fin);
                }
                case 2 -> ogrencileriListele();
                case 3 -> karneRaporuUret();
                case 0 -> System.out.println("-> Sistem kapatılıyor.");
                default -> System.out.println("❌ Geçersiz seçim!");
            }
        } while (secim != 0);

        scanner.close();
    }
}
```

---

## 10. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Dosya İçeriği Tahmini)
Aşağıdaki kod parçası sırasıyla çalıştığında `deneme.txt` dosyasının en son içeriği ne olur?

```java
FileWriter fw1 = new FileWriter("deneme.txt", false);
fw1.write("Elma\n");
fw1.write("Armut\n");
fw1.close();

FileWriter fw2 = new FileWriter("deneme.txt", false);
fw2.write("Muz\n");
fw2.close();

FileWriter fw3 = new FileWriter("deneme.txt", true);
fw3.write("Çilek\n");
fw3.close();
```
*İpucu: `fw2` açılırken `append` modunun `false` olduğuna dikkat ediniz!*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında dosyanın boş kalmasına veya derlenememesine yol açan **2 hatayı** bulunuz:

```java
public static void logYaz(String mesaj) {
    BufferedWriter bw = new BufferedWriter(new FileWriter("C:\kayitlar\app.log"));
    bw.write(mesaj);
    bw.newLine();
    // 2 Hata nerede?
}
```

---

### 📊 Alıştırma 3: Dosyadaki Satır ve Kelime Sayacı
Bir metin dosyasının adını parametre olarak alıp dosyadaki **toplam satır sayısını**, **toplam kelime sayısını** (`split(" ")` kullanarak) ve **toplam karakter sayısını** hesaplayıp ekrana basan metodu kodlayınız.

---

### 🔍 Alıştırma 4: Dosya İçinde Kelime Arama
Bir dosya yolu ve aranacak bir kelime alan metot; bu kelimenin dosyanın **hangi satır numaralarında geçtiğini** konsola bastırsın (Örn: *"Kelime 3. ve 12. satırda bulundu"*).

---

## 11. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Kaynak yönetiminde mutlaka `try-with-resources` kullanınız ve istisnaları yakalayınız.

---

### 📋 Ödev 1: Şifreli Günlük / Not Defteri Uygulaması
**Dosya Adı:** `SifreliGunluk.java`

**Problem Tanımı:**  
Kullanıcının girdiği kişisel günlük yazılarını diske şifreleyerek kaydeden ve okurken şifresini çözen bir uygulama yazınız:
1. Kullanıcıdan günün tarihini ve günlük notunu alınız.
2. Yazıyı dosyaya kaydederken her karakterin ASCII değerini +3 artırarak (Basit Sezar Şifrelemesi) `gunluk.enc` dosyasına **append** modunda kaydediniz.
3. Menüden *"Günlüğü Oku"* seçildiğinde dosya satır satır okunup her karakter -3 azaltılarak orijinal metin konsola dökülmelidir.

---

### 📋 Ödev 2: Web Sunucusu Erişim Günlüğü (Access Log) Analizörü
**Dosya Adı:** `LogAnalizMotoru.java`

**Problem Tanımı:**  
Bir web sunucusunun `access.log` dosyasında satırlar şu formatta tutulmaktadır:  
`IP_ADRESI - [TARIH] "METOT URL" DURUM_KODU YANIT_BOYUTU`  
Örnek Satır: `192.168.1.5 - [30/Oct/2026:10:15:30] "GET /index.html" 200 4520`

1. Örnek 20 satırlık bir log dosyasını kodla oluşturunuz.
2. Dosyayı sıralı okuyarak:
   - Başarılı istek sayısı (`200 OK`),
   - Hatalı istek sayısı (`404 Not Found` ve `500 Server Error`),
   - Sunucudan transfer edilen toplam veri miktarını (Bayt cinsinden yanıt boyutlarının toplamı) hesaplayıp özet bir rapor dosyasına yazdırınız.

---

### 📋 Ödev 3: İki Metin Dosyasını Karşılaştırıcı (File Diff Aracı)
**Dosya Adı:** `DosyaKarsilastirici.java`

**Problem Tanımı:**  
Parametre olarak verilen iki metin dosyasını (`dosya1.txt` ve `dosya2.txt`) satır satır eşzamanlı olarak okuyunuz:
1. Hangi satırların birebir aynı olduğunu,
2. Hangi satır numaralarında farklılık bulunduğunu ve iki dosyadaki farklı satır içeriklerini ekrana basınız.
3. Dosyaların satır sayıları eşit değilse uyarı veriniz.

---

### 📋 Ödev 4: CSV Tabanlı Telefon Rehberi ve VCard Dönüştürücü
**Dosya Adı:** `RehberCsvVcard.java`

**Problem Tanımı:**  
`rehber.csv` dosyasında kayıtlı kişileri (`Ad,Soyad,Telefon,Email`) okuyup, her kişi için akıllı telefonların tanıdığı standart bir `.vcf` (vCard) dosyası üreten programı yazınız.
