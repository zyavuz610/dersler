# 5. Java'da Döngüler: `do-while` Kılavuzu

Bu rehber; Java programlama dilinde koşul ne olursa olsun kod bloğunun **en az bir kez kesinlikle çalışmasını** garanti eden **`do-while`** döngüsünü, çalışma mantığını, `while` döngüsü ile farklarını, menü ve veri doğrulama senaryolarını, değişken kapsamı (*scope*) tuzaklarını ve öğrencilerin çözebileceği kapsamlı alıştırmalar ile ödevleri içermektedir.

---

> 📖 **Modül: 1_koşul ve döngü**  
> İlgili Önceki Dokümanlar:  
> • [1_if-else.md](./1_if-else.md)  
> • [2_switch-case.md](./2_switch-case.md)  
> • [3_for.md](./3_for.md)  
> • [4_while.md](./4_while.md)

---

## 📌 İçindekiler
1. [`do-while` Döngüsü Nedir ve Felsefesi](#1-do-while-döngüsü-nedir-ve-felsefesi)
2. [`do-while` Sözdizimi ve Çalışma Mantığı](#2-do-while-sözdizimi-ve-çalışma-mantığı)
3. [`while` ile `do-while` Arasındaki Temel ve Pratik Farklar](#3-while-ile-do-while-arasındaki-temel-ve-pratik-farklar)
4. [En Yaygın Kullanım Alanları ve Senaryolar](#4-en-yaygın-kullanım-alanları-ve-senaryolar)
5. [`do-while` İçinde `break` ve `continue` Davranışı](#5-do-while-içinde-break-ve-continue-davranışı)
6. [En Sık Yapılan Hatalar ve Tuzaklar](#6-en-sık-yapılan-hatalar-ve-tuzaklar)
7. [Kapsamlı Örnek Konsol Uygulaması: `OgrenciNotOtomasyonu`](#7-kapsamlı-örnek-konsol-uygulaması-ogrencinototomasyonu)
8. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
9. [Ödev Projeleri](#9-ödev-projeleri)

---

## 1. `do-while` Döngüsü Nedir ve Felsefesi?

Şimdiye kadar gördüğümüz `for` ve `while` döngüleri **Ön Koşullu (Pre-test / Entry-controlled)** döngülerdir. Yani kapıdaki bekçi gibidirler; içeriye girmeden önce koşulu denetlerler. Eğer koşul en başta `false` ise döngü gövdesi **hiç (0 kez)** çalışmaz.

Ancak bazı durumlarda mantık tam tersini gerektirir:
- Bir kullanıcıya menüyü göstermeden *"kullanıcı menüden çıkmak istedi mi?"* diye soramazsınız. Önce menü ekrana basılmalı ve kullanıcıdan bir seçim alınmalıdır.
- Bir kullanıcıdan veri almadan *"girilen veri geçerli mi?"* diye kontrol edemezsiniz. Önce veri girilmeli, ardından doğruluk sınanmalıdır.
- Bir oyunu başlatmadan *"tekrar oynamak ister misiniz?"* sorusunu soramazsınız. Önce oyun en az bir el oynanmalıdır.

İşte bu tür, **"Önce işlemi yap, sonra koşulu test et!"** yaklaşımını benimseyen mekanizmaya **`do-while` Döngüsü** denir.

> 💡 **Altın Kural:** `do-while` döngüsünde koşul baştan yanlış (`false`) olsa dahi, gövde içerisindeki kodlar **EN AZ BİR KEZ** kesinlikle çalışır!

---

## 2. `do-while` Sözdizimi ve Çalışma Mantığı

`do-while` döngüsü bir **Son Koşullu (Post-test / Exit-controlled)** döngüdür.

### 📌 Genel Sözdizimi:

```java
do {
    // 1. Koşul ne olursa olsun en az 1 kez çalışacak kodlar
    // 2. Sayaç / durum güncellemesi
} while (koşul_ifadesi); // ⚠️ DİKKAT: Sonundaki ';' ZORUNLUDUR!
```

### ⏱️ Adım Adım Yürütme Sırası:
1. Program `do` bloğuna geldiğinde hiçbir koşul sorgulamadan doğrudan süslü parantezin içine girer.
2. Gövdedeki tüm satırlar sırayla yürütülür.
3. Gövdenin sonuna gelindiğinde `while (koşul)` parantezindeki ifade test edilir:
   - Eğer koşul **`true`** ise: Akış tekrar `do` bloğunun başına döner ve süreç tekrarlanır.
   - Eğer koşul **`false`** ise: Döngü sonlandırılır ve program noktalı virgülden sonraki satırdan devam eder.

---

## 3. `while` ile `do-while` Arasındaki Temel ve Pratik Farklar

Aşağıdaki basit örnek, iki döngü arasındaki zihniyet farkını en net şekilde ortaya koyar:

```java
int sayi = 50;

// ==========================================
// 1. WHILE DÖNGÜSÜ (Ön Koşullu)
// ==========================================
while (sayi < 10) {
    // (50 < 10) ifadesi FALSE olduğu için Java kapıdan içeri sokmaz!
    System.out.println("WHILE: Bu satır ASLA çalışmaz!");
}

// ==========================================
// 2. DO-WHILE DÖNGÜSÜ (Son Koşullu)
// ==========================================
do {
    // Koşul yanlış olsa da gövdeye önce girilir!
    System.out.println("DO-WHILE: Koşul yanlış olsa da ben 1 KEZ ÇALIŞTIM!");
} while (sayi < 10); // Kontrol çıkışta yapılır ve döngü biter.
```

**Ekran Çıktısı:**
```text
DO-WHILE: Koşul yanlış olsa da ben 1 KEZ ÇALIŞTIM!
```

---

### 📊 Detaylı Karşılaştırma Tablosu:

| Özellik | `while` Döngüsü | `do-while` Döngüsü |
| :--- | :--- | :--- |
| **Koşulun Konumu** | Başlangıçta (`while(kosul) { ... }`) | Sonda (`do { ... } while(kosul);`) |
| **Kontrol Tipi** | Ön Koşullu (*Entry-controlled*) | Son Koşullu (*Exit-controlled*) |
| **Minimum Çalışma Sayısı** | **0 kez** (Koşul baştan yanlışsa hiç çalışmaz) | **1 kez** (Koşul baştan yanlış olsa bile 1 tur döner) |
| **Sözdiziminde Noktalı Virgül**| Parantezden sonra `;` konulmaz (konulursa hata olur). | Parantezden sonra `;` **koymak zorunludur**. |
| **Değişken Tanımlama Yeri** | Döngüden önce tanımlanır. | Döngüden önce tanımlanmalıdır (Kapsam kuralı). |
| **En Güçlü Olduğu Alan** | Koşul sağlanmadığı sürece hiç başlamaması gereken işler. | Kullanıcı menüleri, PIN sorma ve girdi doğrulama. |

---

## 4. En Yaygın Kullanım Alanları ve Senaryolar

### 💻 Senaryo 1: İnteraktif Konsol Menüsü
Kullanıcıya menüyü göstermek ve kullanıcı geçerli bir çıkış seçeneği seçene kadar menüyü ekranda tutmak:

```java
import java.util.Scanner;

public class KonsolMenusu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int secim;

        do {
            System.out.println("\n--- ANA YÖNETİM MENÜSÜ ---");
            System.out.println("1 - Raporları Görüntüle");
            System.out.println("2 - Yeni Kayıt Ekle");
            System.out.println("3 - Sistem Ayarları");
            System.out.println("0 - Çıkış Yap");
            System.out.print("Seçiminiz: ");
            secim = scanner.nextInt();

            switch (secim) {
                case 1 -> System.out.println("-> Raporlar yükleniyor...");
                case 2 -> System.out.println("-> Yeni kayıt formu açıldı.");
                case 3 -> System.out.println("-> Ayarlar menüsü.");
                case 0 -> System.out.println("-> Sistemden çıkılıyor. İyi günler!");
                default -> System.out.println("❌ Hatalı seçim! Lütfen 0-3 arası değer giriniz.");
            }
        } while (secim != 0); // Kullanıcı 0 tuşlamadığı sürece menü dönmeye devam eder

        scanner.close();
    }
}
```

---

### 💻 Senaryo 2: Girdi Doğrulama (Input Validation)
Kullanıcıdan yalnızca **1 ile 100 arasında bir sınav notu** girmesini zorunlu kılalım. Kullanıcı hatalı girdikçe soru tekrar sorulacaktır:

```java
import java.util.Scanner;

public class NotDogrulama {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int not;

        do {
            System.out.print("Lütfen öğrenci sınav notunu giriniz (0 - 100): ");
            not = scanner.nextInt();

            if (not < 0 || not > 100) {
                System.out.println("❌ Hata: Sınav notu 0'dan küçük veya 100'den büyük olamaz!");
            }
        } while (not < 0 || not > 100); // Şart bozuk olduğu sürece TEKRAR İSTE!

        System.out.println("✅ Not sisteme başarıyla kaydedildi: " + not);
        scanner.close();
    }
}
```

---

### 💻 Senaryo 3: Oyun Döngüsü ("Yeniden Oynamak İster misiniz?")
Kullanıcıya bir zar atma oyunu oynatıp oyun sonunda tekrar oynamak isteyip istemediğini soralım:

```java
import java.util.Scanner;

public class ZarOyunu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char cevap;

        do {
            // 1 ile 6 arasında rastgele iki zar at:
            int zar1 = (int)(Math.random() * 6) + 1;
            int zar2 = (int)(Math.random() * 6) + 1;

            System.out.println("\n🎲 Zarlar atıldı: [" + zar1 + "] ve [" + zar2 + "]");
            if (zar1 == zar2) {
                System.out.println("🎉 TEBRİKLER! Çift zar attınız!");
            } else {
                System.out.println("Toplam: " + (zar1 + zar2));
            }

            System.out.print("Tekrar zar atmak ister misiniz? (E/H): ");
            cevap = scanner.next().charAt(0);

        } while (cevap == 'E' || cevap == 'e'); // 'E' dendiği sürece oyunu yeniden başlat

        System.out.println("Oyun bitti. Teşekkürler!");
        scanner.close();
    }
}
```

---

## 5. `do-while` İçinde `break` ve `continue` Davranışı

### 🛑 1. `break`: Döngüyü Koşul Satırına Gitmeden Anında Kırar
Gövde içerisinde belirli bir acil durum oluştuğunda döngüyü o saniye sonlandırır:

```java
int sayac = 1;

do {
    if (sayac == 3) {
        System.out.println("Kritik eşik (3) aşıldı, çıkılıyor.");
        break; // while koşulunu test etmeden ANINDA döngüden çıkar!
    }
    System.out.println("Sayaç: " + sayac);
    sayac++;
} while (sayac <= 10);
```

---

### ⏭️ 2. `continue`: Doğrudan `while (koşul)` Kontrolüne Atlar!
`do-while` bloğu içinde `continue` komutu çalıştırıldığında, o turun kalan kodları atlanır ve doğrudan **en alttaki `while (koşul);` satırına sıçranarak koşul test edilir**:

```java
int i = 0;

do {
    i++;
    if (i % 2 == 0) {
        continue; // Çift ise alttaki satırı atla, doğrudan 'while (i < 6)' koşuluna sıçra!
    }
    System.out.println("Tek sayı: " + i);
} while (i < 6);

// Çıktı:
// Tek sayı: 1
// Tek sayı: 3
// Tek sayı: 5
```

---

## 6. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: Değişken Kapsamı (Variable Scope) Hatası (En Yaygın Derleme Hatası!)
`do` süslü parantezleri `{ }` içinde tanımlanan (*declare* edilen) bir değişken, bloğun kapanış parantezinde ölür. Dolayısıyla alttaki `while (...)` parantezi o değişkeni **göremez**!

```java
// ❌ DERLEME HATASI: 'secim' değişkeni bulunamıyor (cannot find symbol):
do {
    int secim = scanner.nextInt(); // Değişken 'do' içinde doğdu!
} while (secim != 0); // HATA: 'secim' burada görünmez!

// ✅ DOĞRU KULLANIM: Değişkeni mutlaka döngünün DIŞINDA tanımlayınız:
int secim; // Döngü dışında tanımlandı
do {
    secim = scanner.nextInt(); // Değer atandı
} while (secim != 0); // Sorunsuz çalışır!
```

---

### 2. Tuzak: Sonundaki Noktalı Virgülü (`;`) Unutmak
`while` ve `for` döngülerinin sonuna noktalı virgül koymak bir mantık hatasıyken; **`do-while` döngüsünün `while (koşul);` kısmının sonuna noktalı virgül koymamak doğrudan bir DERLEME HATASIDIR**:

```java
do {
    System.out.println("Test");
} while (kosul) // ❌ DERLEME HATASI: ';' expected
```

---

### 3. Tuzak: Koşul Mantığını Ters Kurmak
Öğrenciler sıklıkla *"çıkış şartı"* ile *"devam etme şartını"* karıştırırlar:
- `while` parantezi içine **DÖNGÜNÜN ÇIKMA ŞARTI DEĞİL, DEVAM ETME ŞARTI** yazılmalıdır!

```java
int sifre;

// Kullanıcı 1234 girdiğinde programın devam etmesini (çıkmasını) istiyoruz:

// ❌ HATALI MANTIK:
// do { ... } while (sifre == 1234); 
// (Bu, şifre doğru olduğu sürece tekrar sor anlamına gelir!)

// ✅ DOĞRU MANTIK:
do {
    System.out.print("PIN giriniz: ");
    sifre = scanner.nextInt();
} while (sifre != 1234); // Şifre doğru OLMADIĞI SÜRECE sormaya devam et!
```

---

## 7. Kapsamlı Örnek Konsol Uygulaması: `OgrenciNotOtomasyonu`

Aşağıdaki program; öğretmenlerin sınıftaki öğrencilerin sınav notlarını girdiği, hem ana menüde hem de her bir öğrencinin not girişinde iç içe `do-while` döngüleriyle veri doğrulama yapan eksiksiz bir konsol uygulamasıdır:

```java
import java.util.Scanner;

public class OgrenciNotOtomasyonu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int toplamOgrenci = 0;
        double toplamPuan = 0.0;
        int enYuksekNot = -1;
        int enDusukNot = 101;
        char devamCevap;

        System.out.println("==========================================");
        System.out.println("   KTÜ BİLGİSAYAR MÜH. SINAV NOT SİSTEMİ  ");
        System.out.println("==========================================");

        // Dış do-while: Yeni öğrenci girilmek istendiği sürece döner
        do {
            int not;

            // İç do-while (Girdi Doğrulama): Not 0-100 arasında girilene kadar zorlar
            do {
                System.out.print((toplamOgrenci + 1) + ". Öğrencinin notunu giriniz (0-100): ");
                not = scanner.nextInt();

                if (not < 0 || not > 100) {
                    System.out.println("❌ Geçersiz not! Sınav notu 0 ile 100 arasında olmalıdır.");
                }
            } while (not < 0 || not > 100);

            // İstatistikleri Güncelleme
            toplamOgrenci++;
            toplamPuan += not;

            if (not > enYuksekNot) {
                enYuksekNot = not;
            }
            if (not < enDusukNot) {
                enDusukNot = not;
            }

            // Başka öğrenci var mı kontrolü (İç do-while ile E/H kontrolü):
            do {
                System.out.print("Başka öğrenci notu girmek istiyor musunuz? (E/H): ");
                devamCevap = scanner.next().charAt(0);
                devamCevap = Character.toUpperCase(devamCevap);

                if (devamCevap != 'E' && devamCevap != 'H') {
                    System.out.println("❌ Lütfen sadece 'E' (Evet) veya 'H' (Hayır) giriniz!");
                }
            } while (devamCevap != 'E' && devamCevap != 'H');

        } while (devamCevap == 'E');

        // Sonuç Raporu
        System.out.println("\n------------------------------------------");
        System.out.println("               SINIF RAPORU               ");
        System.out.println("------------------------------------------");
        System.out.println("Toplam Girilen Öğrenci Sayısı : " + toplamOgrenci);
        System.out.println("Sınıfın En Yüksek Notu        : " + enYuksekNot);
        System.out.println("Sınıfın En Düşük Notu         : " + enDusukNot);

        if (toplamOgrenci > 0) {
            double sinifOrtalamasi = toplamPuan / toplamOgrenci;
            System.out.printf("Sınıf Not Ortalaması          : %.2f\n", sinifOrtalamasi);
        }

        System.out.println("==========================================");
        scanner.close();
    }
}
```

---

## 8. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Koşul Başlangıçta Yanlış)
Aşağıdaki kod parçasının konsol çıktısını kağıt üzerinde adım adım hesaplayınız:

```java
int a = 10;
int b = 5;

do {
    a += b;
    b *= 2;
    System.out.println("a=" + a + ", b=" + b);
} while (a < 10);

System.out.println("Döngü Sonrası: a=" + a + ", b=" + b);
```
*İpucu: `while (a < 10)` koşulu döngünün 1. turunun sonunda ne değer üretir?*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında yer alan **2 derleme hatasını** ve **1 mantık hatasını** tespit ediniz:

```java
// Kullanıcıdan 0 girilene kadar pozitif sayı toplayan kod:
int genelToplam = 0;

do {
    System.out.print("Sayı girin: ");
    int girilenSayi = scanner.nextInt();
    genelToplam += girilenSayi;
} while (girilenSayi == 0) // Hatalar nerede?

System.out.println("Toplam: " + genelToplam);
```

---

### 🎲 Alıştırma 3: Çift Zar Gelene Kadar Zar Atma
Birbirinden bağımsız iki zar atan ve **zarlar çift gelene kadar (`zar1 == zar2`)** zar atmaya devam eden programı `do-while` kullanarak yazınız. Döngü bittiğinde kaçıncı denemede çift zar geldiğini ekrana yazdırınız.

---

### 🔄 Alıştırma 4: `while` Döngüsünü `do-while`'a Dönüştürme
Aşağıdaki `while` kodunu birebir aynı davranışı sergileyecek şekilde `do-while` döngüsüne dönüştürünüz:

```java
int sayi = 1;
while (sayi <= 5) {
    System.out.print((sayi * sayi) + " ");
    sayi++;
}
```

---

## 9. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Girdi doğrulamalarında ve kullanıcı menülerinde mutlaka `do-while` döngüsünü kullanınız.

---

### 📋 Ödev 1: 3 Haklı Güvenli ATM PIN Giriş ve Bloke Simülasyonu
**Dosya Adı:** `GuvenliPinGiris.java`

**Problem Tanımı:**  
Bir banka kartının PIN kodu doğrulama ekranını kodlayınız:
- Sistemde tanımlı sabit PIN: `int dogruPin = 6161;`
- Kullanıcıya en fazla **3 deneme hakkı** tanınmalıdır.
- Kullanıcıdan PIN kodu `do-while` döngüsü içinde istenmelidir.
- **Akış Kuralları:**
  1. Kullanıcı doğru PIN girerse: *"Giriş başarılı! Hoş geldiniz."* mesajı basılıp döngüden çıkılmalıdır.
  2. Kullanıcı yanlış PIN girerse: Kaç hakkının kaldığı gösterilmeli (`kalanHak`), hak varsa tekrar sorulmalıdır.
  3. 3 hakkında da yanlış girerse döngü sonlanmalı ve *"3 kez hatalı PIN girdiniz! Kartınız güvenlik nedeniyle bloke edilmiştir. Lütfen bankanızla iletişime geçiniz."* uyarısı verilmelidir.

---

### 📋 Ödev 2: Matematik Pratik Antrenman Motoru
**Dosya Adı:** `MatematikAntrenmani.java`

**Problem Tanımı:**  
Öğrencilerin 4 işlem yeteneğini geliştiren bir alıştırma yazılımı hazırlayınız:
1. Bilgisayar 1 ile 20 arasında rastgele iki sayı ve rastgele bir işlem (`+`, `-`, `*`) üretsin (Örn: `14 * 7 = ?`).
2. Kullanıcıya soru sorulsun.
3. Kullanıcı **doğru cevabı verene kadar** `do-while` döngüsü ile aynı soru tekrar tekrar sorulmalıdır.
4. Yanlış cevap verildiğinde: *"Yanlış cevap! Tekrar deneyiniz."* uyarısı basılmalı ve o soru için yapılan deneme sayısı sayılmalıdır.
5. Kullanıcı doğru cevabı verdiğinde: *"Tebrikler! {deneme}. denemenizde doğru bildiniz."* denmeli ve *"Yeni bir soru çözmek ister misiniz? (E/H)"* üst `do-while` döngüsüne geçilmelidir.

---

### 📋 Ödev 3: Otopark Ücret Hesaplayıcı ve Kasa Otomasyonu
**Dosya Adı:** `OtoparkOtomasyonu.java`

**Problem Tanımı:**  
Bir katlı otoparkın ücretlendirme yazılımını geliştiriniz. Program `do-while` menüsü ile sürekli açık kalmalıdır.

**Tarife Bilgileri:**
- **Araç Türü:**
  - `1`: Otomobil (Açılış: 40 TL, Saat Başı: 20 TL)
  - `2`: SUV / Minibüs (Açılış: 60 TL, Saat Başı: 30 TL)
  - `3`: Kamyonet / Otobüs (Açılış: 90 TL, Saat Başı: 50 TL)
- Kullanıcıdan araç türü ve kalınan süre (saat) `do-while` ile pozitif veri kontrolü yapılarak alınmalıdır.
- Gün boyunca çıkan araçların toplam sayısı ve otopark kasasında biriken **toplam ciro** bellekte tutulmalıdır.
- Kasiyer ana menüden `0` (Gün Sonu Kapanış) seçtiğinde o günkü toplam araç sayısı ve toplam hasılat ekrana dökülmelidir.

---

### 📋 Ödev 4: Tahmin Sayacılı Kelime / Harf Tahmin Simülasyonu
**Dosya Adı:** `HarfTahminOyunu.java`

**Problem Tanımı:**  
Bilgisayar 'A' ile 'Z' arasında rastgele gizli bir büyük harf seçsin:  
`char gizliHarf = (char)('A' + (int)(Math.random() * 26));`

1. Kullanıcıdan `do-while` döngüsü içinde harf tahmini alınız (`scanner.next().toUpperCase().charAt(0)`).
2. Eğer kullanıcının tahmini alfabetik olarak gizli harften önce geliyorsa: *"Daha SONRAKİ bir harf deneyiniz (Alfabetik olarak ileri)."*
3. Eğer tahmin gizli harften sonra geliyorsa: *"Daha ÖNCEKİ bir harf deneyiniz (Alfabetik olarak geri)."*
4. Kullanıcı harfi doğru tahmin edene kadar döngü devam etmeli; doğru bildiğinde kaç tahminde bildiği ekrana basılmalıdır.
