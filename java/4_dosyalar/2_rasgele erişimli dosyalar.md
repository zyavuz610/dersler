# 2. Java'da Rastgele Erişimli Dosyalar (`RandomAccessFile`) Kılavuzu

Bu rehber; Java programlama dilinde dosyaları baştan sona sırayla okumak yerine dosyanın istenen herhangi bir baytına doğrudan atlamayı sağlayan **Rastgele Erişimli Dosyaları (`RandomAccessFile`)**, dosya imleci (**File Pointer / Cursor**) yönetimini (`seek()`, `getFilePointer()`, `length()`), açma modlarını (`"r"`, `"rw"`), ikili (*binary*) veri okuma/yazma metotlarını, **Sabit Uzunluklu Kayıtlar (Fixed-Length Records)** ile mini bir dosya veritabanı kurma mantığını, yerinde güncellemeyi (*in-place update*), sık düşülen tuzakları ve öğrencilerin çözebileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 4_dosyalar**  
> İlgili Önceki Doküman: [1_dosya oku-yaz.md](./1_dosya%20oku-yaz.md)

---

## 📌 İçindekiler
1. [Rastgele Erişim (Random Access) Nedir ve Sıralı Erişimden Farkı?](#1-rastgele-erişim-random-access-nedir-ve-sıralı-erişimden-farkı)
2. [`RandomAccessFile` Sınıfı ve Açma Modları (`"r"`, `"rw"`)](#2-randomaccessfile-sınıfı-ve-açma-modları-r-rw)
3. [Dosya İmleci (File Pointer / Cursor) Yönetimi](#3-dosya-imleci-file-pointer--cursor-yönetimi)
4. [Primitif Veri Türleri ile Bayt Düzeyinde Okuma ve Yazma](#4-primitif-veri-türleri-ile-bayt-düzeyinde-okuma-ve-yazma)
5. [Sabit Uzunluklu Kayıtlar (Fixed-Length Records) ile Veritabanı Mimarisi](#5-sabit-uzunluklu-kayıtlar-fixed-length-records-ile-veritabanı-mimarisi)
6. [Yerinde Güncelleme (In-Place Update) Mucizesi](#6-yerinde-güncelleme-in-place-update-mucizesi)
7. [En Sık Yapılan Hatalar ve Tuzaklar](#7-en-sık-yapılan-hatalar-ve-tuzaklar)
8. [Kapsamlı Örnek Konsol Uygulaması: `BankaHesapDosyaVeritabani`](#8-kapsamlı-örnek-konsol-uygulaması-bankahesapdosyaveritabani)
9. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#9-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
10. [Ödev Projeleri](#10-ödev-projeleri)

---

## 1. Rastgele Erişim (Random Access) Nedir ve Sıralı Erişimden Farkı?

Önceki derste gördüğümüz `BufferedReader` ve `FileWriter` gibi sınıflar **Sıralı Erişim (Sequential Access)** ilkesiyle çalışır:
- Bir kaset kasetini düşünün: 10. şarkıyı dinlemek istiyorsanız ilk 9 şarkıyı baştan sona ileri sarmak zorundasınızdır.
- 1 milyon satırlık bir metin dosyasında 950.000. satırdaki bir bilgiyi okumak için ilk 949.999 satırı tek tek okuyup belleğe almak zorundasınızdır.

**Rastgele Erişim (Random Access)** ise bir gramofon plağı veya modern bir sabit disk gibidir:
- İğneyi (imleci) plağın istediğiniz saniyesine **doğrudan koyabilir** ve oradan dinlemeye başlayabilirsiniz.
- 10 GB'lık dev bir dosyanın tam ortasındaki 5 baytlık bir veriyi okumak veya değiştirmek için dosyanın tamamını taramanız gerekmez.
- İmleci anında o adrese ışınlayabilir, eski verinin üzerine yenisini yazabilir (*in-place update*) ve dosyayı kapatabilirsiniz.

```text
SIRALI ERİŞİM (Sequential):
[1. Bayt] -> [2. Bayt] -> [3. Bayt] -> ... -> [Hedef Bayt] (Zaman alır!)

RASTGELE ERİŞİM (Random Access):
İmleç ========================================> [Hedef Bayt] (Anında O(1) sıçrama!)
```

---

## 2. `RandomAccessFile` Sınıfı ve Açma Modları (`"r"`, `"rw"`)

Java'da rastgele erişim işlemlerini yürüten sınıf `java.io.RandomAccessFile` sınıfıdır. Bu sınıf Java G/Ç ailesinde çok özel bir yere sahiptir:
- Hem `DataInput` hem de `DataOutput` arayüzlerini uygular.
- Yani **aynı nesne üzerinden HEM OKUMA HEM DE YAZMA** yapılabilir! (Ayrı okuyucu ve yazıcı açmaya gerek yoktur).

### 📌 Açma Modları (Access Modes):

```java
RandomAccessFile raf = new RandomAccessFile("veritabani.dat", "rw");
```

| Mod Kodu | Adı | Açıklama |
| :---: | :--- | :--- |
| **`"r"`** | Salt Okuma (*Read-Only*) | Dosyayı sadece okumak için açar. Yazma metodu çağrılırsa `IOException` fırlatır. Dosya diskte yoksa `FileNotFoundException` verir. |
| **`"rw"`** | Okuma ve Yazma (*Read-Write*) | Hem okuma hem yazma yapılabilir. **Dosya diskte yoksa Java dosyayı otomatik olarak sıfırdan oluşturur!** |
| **`"rws"`** | Eşzamanlı Senkronize | Her yazma işleminde hem dosya içeriğini hem de meta verilerini (değiştirilme tarihi vb.) anında fiziksel diske yazar. |
| **`"rwd"`** | İçerik Senkronize | Her yazma işleminde sadece dosya içeriğini anında diske senkronize eder. |

---

## 3. Dosya İmleci (File Pointer / Cursor) Yönetimi

Bir dosya açıldığında Java dosyanın içinde o an hangi baytta bulunduğunu takip eden görünmez bir işaretçi tutar. Buna **Dosya İmleci (File Pointer)** denir.

İmleç bir bayt okunduğunda veya yazıldığında **otomatik olarak o bayt kadar ileriye kayar**.

### 🛠️ İmleç Kontrol Metotları:

| Metot | Görevi ve Dönüş Tipi | Açıklama |
| :--- | :--- | :--- |
| **`seek(long pos)`** | `void` | İmleci dosyanın $pos$. baytına doğrudan taşır ($0$ tabanlıdır). |
| **`getFilePointer()`** | `long` | İmlecin şu anda dosya içinde kaçıncı baytta olduğunu döndürür. |
| **`length()`** | `long` | Dosyanın toplam boyutunu bayt cinsinden verir. |
| **`skipBytes(int n)`**| `int` | İmleci mevcut konumundan $n$ bayt ileriye atlatır. |

### 🧭 Kritik İmleç Konumları:
- **Dosyanın Başına Gitmek:** `raf.seek(0);`
- **Dosyanın Sonuna Gitmek (Append Yapmak):** `raf.seek(raf.length());`
- **100. Bayta Gitmek:** `raf.seek(100);`

---

## 4. Primitif Veri Türleri ile Bayt Düzeyinde Okuma ve Yazma

`RandomAccessFile` ikili (*binary*) veri transferi yapar. Java'nın her bir primitif türünün bellekte kaç bayt kapladığını bilmek imleci doğru hareket ettirmek için şarttır:

| Veri Tipi | Bayt Boyutu | Yazma Metodu | Okuma Metodu |
| :--- | :---: | :--- | :--- |
| **`byte`** | 1 Bayt | `writeByte(int v)` | `readByte()` |
| **`boolean`** | 1 Bayt | `writeBoolean(boolean v)` | `readBoolean()` |
| **`short`** | 2 Bayt | `writeShort(int v)` | `readShort()` |
| **`char`** | 2 Bayt | `writeChar(int v)` | `readChar()` |
| **`int`** | 4 Bayt | `writeInt(int v)` | `readInt()` |
| **`float`** | 4 Bayt | `writeFloat(float v)` | `readFloat()` |
| **`long`** | 8 Bayt | `writeLong(long v)` | `readLong()` |
| **`double`** | 8 Bayt | `writeDouble(double v)` | `readDouble()` |
| **`String`** | Değişken | `writeUTF(String str)` | `readUTF()` |

### 💻 Basit Örnek: Yazma, Geri Sarma ve Okuma

```java
import java.io.RandomAccessFile;
import java.io.IOException;

public class RafTemelKullanim {
    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile("sayilar.bin", "rw")) {
            // 1. Dosyaya 3 adet int sayı yazalım (Her int 4 bayttır):
            raf.writeInt(100); // 0-3 baytlar (İmleç 4'e geldi)
            raf.writeInt(200); // 4-7 baytlar (İmleç 8'e geldi)
            raf.writeInt(300); // 8-11 baytlar (İmleç 12'ye geldi)

            System.out.println("Yazma bittiğinde imleç konumu : " + raf.getFilePointer()); // 12
            System.out.println("Dosyanın toplam boyutu         : " + raf.length() + " Bayt"); // 12

            // 2. Doğrudan 2. sayıyı (200) okumak istiyoruz:
            // 200 sayısı 4. bayttan başlar:
            raf.seek(4); // İmleci 4. bayta ışınla!
            int okunanSayi = raf.readInt();
            System.out.println("4. bayttan okunan sayı        : " + okunanSayi); // 200

            // 3. Dosyanın en başındaki ilk sayıyı okuyalım:
            raf.seek(0);
            System.out.println("0. bayttan okunan ilk sayı     : " + raf.readInt()); // 100

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## 5. Sabit Uzunluklu Kayıtlar (Fixed-Length Records) ile Veritabanı Mimarisi

Rastgele erişimin en büyük gücü, ilişkisel veritabanlarının (*SQL motorları, Oracle, PostgreSQL*) temelinde yatan **Sabit Uzunluklu Kayıt (Fixed-Length Record)** tekniğidir.

### ❓ Problem Nedir?
Eğer bir dosyada isimleri değişken uzunlukta yazarsanız (`"Ali"`, `"Abdullah"`), 50. öğrencinin dosyanın kaçıncı baytında başladığını bilemezsiniz; yine baştan okumak zorunda kalırsınız.

### 💡 Çözüm: Sabit Boyutlu Şablon Tasarlamak
Her öğrenci kaydının diskte **tam olarak 52 Bayt** yer kaplayacağını garanti edersek:

```text
[ ÖĞRENCİ KAYIT ŞABLONU (TOPLAM 52 BAYT) ]
+-------------------+-----------------------------------+--------------------+
| Öğrenci No (int)  | İsim (Sabit 20 Karakter = 40 Bayt)| Not Ortalaması(dbl)|
| 4 Bayt            | 40 Bayt                           | 8 Bayt             |
+-------------------+-----------------------------------+--------------------+
```

### 📐 Matematiksel Konum Formülü:
$k$. kaydın ($0$ tabanlı) başladığı bayt adresi tek bir çarpma işlemiyle bulunur:
$$\text{Bayt Konumu} = k \times \text{KAYIT\_BOYUTU}$$
- 0. Kayıt $\rightarrow 0 \times 52 =$ **0. bayt**
- 1. Kayıt $\rightarrow 1 \times 52 =$ **52. bayt**
- 10. Kayıt $\rightarrow 10 \times 52 =$ **520. bayt**

Tek bir `raf.seek(520);` komutuyla önceki hiçbir kaydı okumadan **10. öğrenciye anında ulaşabilirsiniz!**

---

## 6. Yerinde Güncelleme (In-Place Update) Mucizesi

Normal sıralı dosyalarda bir öğrencinin notunu değiştirmek için:
1. Tüm dosyayı satır satır okumanız,
2. İlgili satırı bellekte değiştirmeniz,
3. Dosyayı baştan sona tekrar diske yazmanız gerekir.

`RandomAccessFile` ile ise:
1. `raf.seek(hedefKonum);` ile sadece o öğrencinin notunun başladığı bayta gidilir,
2. `raf.writeDouble(yeniNot);` denir,
3. **Dosyadaki diğer hiçbir veriye dokunulmadan sadece o 8 bayt güncellenir!**

---

## 7. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: Yazdıktan Hemen Sonra `seek(0)` Yapmadan Okumaya Çalışmak
Dosyaya veri yazdığınızda imleç dosyanın en sonuna ilerler. Eğer `seek(0)` ile imleci başa almadan `readInt()` çağırırsanız dosyanın sonunda veri kalmadığı için **`EOFException` (End of File)** patlar!

```java
raf.writeInt(50);
// ❌ HATA: İmleç şu an 4. baytta, arkasında veri yok!
// int x = raf.readInt(); // EOFException!

// ✅ DOĞRU: Önce imleci başa çekiniz:
raf.seek(0);
int x = raf.readInt();
```

---

### 2. Tuzak: `writeUTF()` Metodunun Gizli 2 Baytı
`writeUTF("Ali")` metodu sadece 3 harf (3 bayt) yazmaz. Java, okurken metnin nerede bittiğini anlamak için metnin başına **2 baytlık uzunluk bilgisi** ekler (Toplam $3 + 2 = 5$ bayt yazar). Sabit boyutlu hesaplamalarda bu 2 bayt unutulursa tüm kayıt ofsetleri kayar!

---

### 3. Tuzak: Farklı Tipte Yazıp Farklı Tipte Okumak
Dosyaya `writeInt(100)` (4 bayt) yazıp okurken `readDouble()` (8 bayt) çağırırsanız derleyici hata vermez; ancak anlamsız, bozuk sayılar okursunuz. Hangi sırayla yazıldıysa aynı sırayla okunmalıdır.

---

## 8. Kapsamlı Örnek Konsol Uygulaması: `BankaHesapDosyaVeritabani`

Aşağıdaki program; banka hesaplarını ikili bir dosyada (`banka.dat`) **sabit 52 baytlık kayıtlar** halinde tutan, doğrudan hesap numarasına göre $O(1)$ sürede atlayıp bakiye sorgulayan, para yatırıp çeken ve yerinde bakiye güncellemesi (*in-place update*) yapan profesyonel bir konsol uygulamasıdır:

```java
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Scanner;

public class BankaHesapDosyaVeritabani {
    private static final String DOSYA_ADI = "banka.dat";

    // Sabit Kayıt Boyutu:
    // Hesap No (int)      : 4 Bayt
    // Müşteri Adı (20 char): 40 Bayt (Her char 2 bayt)
    // Bakiye (double)     : 8 Bayt
    // TOPLAM              : 52 Bayt
    private static final int AD_UZUNLUK = 20;
    private static final int KAYIT_BOYUTU = 4 + (AD_UZUNLUK * 2) + 8; // 52 Bayt

    // İsimleri sabit 20 karaktere tamamlayan yardımcı metot:
    private static String sabitBoyutluMetin(String metin) {
        StringBuilder sb = new StringBuilder(metin);
        if (sb.length() > AD_UZUNLUK) {
            return sb.substring(0, AD_UZUNLUK);
        }
        while (sb.length() < AD_UZUNLUK) {
            sb.append(' '); // Boşluklarla 20 karaktere tamamla
        }
        return sb.toString();
    }

    // 1. Yeni Hesap Açma (Dosyanın sonuna ekler)
    public static void hesapAc(int hesapNo, String adSoyad, double ilkBakiye) {
        try (RandomAccessFile raf = new RandomAccessFile(DOSYA_ADI, "rw")) {
            // İmleci dosyanın en sonuna taşı (Append):
            raf.seek(raf.length());

            raf.writeInt(hesapNo);
            raf.writeChars(sabitBoyutluMetin(adSoyad));
            raf.writeDouble(ilkBakiye);

            System.out.println("✅ Hesap başarıyla açıldı ve dosyaya işlendi.");
        } catch (IOException e) {
            System.err.println("Yazma hatası: " + e.getMessage());
        }
    }

    // 2. Hesap Numarasına Göre Doğrudan Atlayarak Arama ve Görüntüleme
    public static void hesapSorgula(int arananHesapNo) {
        try (RandomAccessFile raf = new RandomAccessFile(DOSYA_ADI, "r")) {
            long toplamKayit = raf.length() / KAYIT_BOYUTU;
            boolean bulundu = false;

            for (int i = 0; i < toplamKayit; i++) {
                // Her kaydın başlangıç baytına atla:
                raf.seek((long) i * KAYIT_BOYUTU);

                int no = raf.readInt();
                if (no == arananHesapNo) {
                    // İsim karakterlerini oku:
                    char[] isimDizi = new char[AD_UZUNLUK];
                    for (int k = 0; k < AD_UZUNLUK; k++) {
                        isimDizi[k] = raf.readChar();
                    }
                    String ad = new String(isimDizi).trim();
                    double bakiye = raf.readDouble();

                    System.out.println("\n--- HESAP BİLGİSİ ---");
                    System.out.println("Hesap No : " + no);
                    System.out.println("Ad Soyad : " + ad);
                    System.out.printf("Bakiye   : %.2f TL\n", bakiye);
                    bulundu = true;
                    break;
                }
            }

            if (!bulundu) {
                System.out.println("❌ " + arananHesapNo + " numaralı hesap bulunamadı!");
            }

        } catch (IOException e) {
            System.err.println("Okuma hatası: " + e.getMessage());
        }
    }

    // 3. Yerinde Bakiye Güncelleme (In-Place Update)
    public static void bakiyeGuncelle(int hesapNo, double eklenecekTutar) {
        try (RandomAccessFile raf = new RandomAccessFile(DOSYA_ADI, "rw")) {
            long toplamKayit = raf.length() / KAYIT_BOYUTU;
            boolean bulundu = false;

            for (int i = 0; i < toplamKayit; i++) {
                long kayitBasi = (long) i * KAYIT_BOYUTU;
                raf.seek(kayitBasi);

                int no = raf.readInt();
                if (no == hesapNo) {
                    // İsmi atla (20 char = 40 bayt):
                    raf.skipBytes(AD_UZUNLUK * 2);

                    // Şu an tam bakiyenin üstündeyiz!
                    long bakiyeKonumu = raf.getFilePointer();
                    double mevcutBakiye = raf.readDouble();
                    double yeniBakiye = mevcutBakiye + eklenecekTutar;

                    if (yeniBakiye < 0) {
                        System.out.println("❌ Yetersiz bakiye! İşlem iptal edildi.");
                        return;
                    }

                    // İmleci tekrar bakiyenin başladığı yere alıp YERİNDE ÜZERİNE YAZIYORUZ:
                    raf.seek(bakiyeKonumu);
                    raf.writeDouble(yeniBakiye);

                    System.out.printf("✅ Bakiye yerinde güncellendi! Eski: %.2f TL | Yeni: %.2f TL\n", mevcutBakiye, yeniBakiye);
                    bulundu = true;
                    break;
                }
            }

            if (!bulundu) {
                System.out.println("❌ Hesap bulunamadı!");
            }

        } catch (IOException e) {
            System.err.println("Güncelleme hatası: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int secim;

        do {
            System.out.println("\n==========================================");
            System.out.println("   KTÜ RANDOM ACCESS DOSYA VERİTABANI     ");
            System.out.println("==========================================");
            System.out.println("1 - Yeni Hesap Oluştur");
            System.out.println("2 - Hesap Bilgisi Sorgula (seek ile)");
            System.out.println("3 - Para Yatır / Çek (In-Place Güncelleme)");
            System.out.println("0 - Çıkış");
            System.out.print("İşlem seçiniz: ");
            secim = scanner.nextInt();

            switch (secim) {
                case 1 -> {
                    System.out.print("Hesap No: ");
                    int no = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Müşteri Ad Soyad: ");
                    String ad = scanner.nextLine();
                    System.out.print("İlk Bakiye Tutarı: ");
                    double bakiye = scanner.nextDouble();

                    hesapAc(no, ad, bakiye);
                }
                case 2 -> {
                    System.out.print("Sorgulanacak Hesap No: ");
                    int no = scanner.nextInt();
                    hesapSorgula(no);
                }
                case 3 -> {
                    System.out.print("Hesap No: ");
                    int no = scanner.nextInt();
                    System.out.print("İşlem Tutarı (+ Yatır / - Çek): ");
                    double tutar = scanner.nextDouble();
                    bakiyeGuncelle(no, tutar);
                }
                case 0 -> System.out.println("-> Veritabanı kapatıldı.");
                default -> System.out.println("❌ Hatalı seçim!");
            }
        } while (secim != 0);

        scanner.close();
    }
}
```

---

## 9. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - İmleç Konumu Hesaplama)
Aşağıdaki kod parçası çalıştıktan sonra ekrana ne yazdırılır?

```java
RandomAccessFile raf = new RandomAccessFile("test.bin", "rw");
raf.writeInt(10);       // 4 bayt
raf.writeDouble(20.5);   // 8 bayt
raf.writeBoolean(true);  // 1 bayt

System.out.println("Konum 1: " + raf.getFilePointer());

raf.seek(4);
double d = raf.readDouble();
System.out.println("Okunan double: " + d);
System.out.println("Konum 2: " + raf.getFilePointer());
raf.close();
```
*İpucu: `readDouble()` çağrıldıktan sonra imleç kaç bayt daha ileri kayar?*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kodda `EOFException` alınmasının sebebini bulunuz ve düzeltiniz:

```java
RandomAccessFile raf = new RandomAccessFile("data.bin", "rw");
for (int i = 1; i <= 5; i++) {
    raf.writeInt(i * 10);
}

// Dosyadaki ilk sayıyı okumak istiyoruz:
int ilkSayi = raf.readInt(); // Hata nerede?
System.out.println("İlk: " + ilkSayi);
raf.close();
```

---

### 🔄 Alıştırma 3: Dosyanın Son 4 Baytını Okuma
Boyutu ne olursa olsun, bir tamsayı dosyasının en sonuna yazılmış olan `int` değerini dosyanın tamamını okumadan, doğrudan `raf.seek(...)` kullanarak okuyup ekrana basan kodu yazınız.  
*(İpucu: `raf.length() - 4`)*

---

### ✏️ Alıştırma 4: Yerinde Karakter Değiştirme
İçinde `"JAVA"` yazan bir dosyanın 3. karakterini ('V') doğrudan `seek()` kullanarak `'K'` harfi ile değiştirip dosyayı `"JAKA"` haline getiren kodu yazınız.

---

## 10. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. `seek()` formüllerini ve sabit kayıt boyutlarını dikkatle hesaplayınız.

---

### 📋 Ödev 1: Kütüphane Kitap Katalog Veritabanı
**Dosya Adı:** `KutuphaneRastgeleErisim.java`

**Problem Tanımı:**  
Kitapların sabit uzunluklu ikili kayıtlarda tutulduğu bir kütüphane otomasyonu yazınız:
- **Kayıt Yapısı (Toplam 50 Bayt):**
  - `Kitap ID (int)`: 4 Bayt
  - `Kitap Adı (char[20])`: 40 Bayt
  - `Sayfa Sayısı (int)`: 4 Bayt
  - `Ödünç Verildi mi? (boolean)`: 2 Bayt
- **Fonksiyonlar:**
  1. Yeni kitap ekleme (dosya sonuna),
  2. Kitap ID'sine göre $O(1)$ atlayıp detayları gösterme,
  3. Kitabın ödünç durumunu yerinde değiştirme (`oduncVer()` / `iadeAl()`).

---

### 📋 Ödev 2: Market Stok ve Fiyat Güncelleme Motoru
**Dosya Adı:** `MarketStokVeritabani.java`

**Problem Tanımı:**  
Ürünlerin barkod numarasına göre doğrudan erişildiği bir stok yönetim sistemi kodlayınız:
- Barkod Numarası: `0` ile `99` arasında sıralı tamsayı olsun.
- Her ürün kaydı: `Ürün Adı (char[15] = 30 bayt)`, `Fiyat (double = 8 bayt)`, `Stok Adedi (int = 4 bayt)`.
- Bir ürünün fiyatı değiştiğinde, sadece barkod indeksine atlayarak (`barkod * 42 + 30`) eski fiyatın üzerine yeni fiyatı yazan yerinde güncelleme metodunu geliştiriniz.

---

### 📋 Ödev 3: Büyük Dosyalar İçin "Tail" Komutu Simülatörü
**Dosya Adı:** `DosyaKuyrukOkuyucu.java`

**Problem Tanımı:**  
Linux terminalindeki `tail` komutunu modelleyiniz:
- Milyonlarca bayttan oluşan devasa bir ikili veya metin dosyasının dosya sonundan geriye doğru son 100 baytını `raf.seek(raf.length() - 100)` ile doğrudan okuyup ekrana basan bir yardımcı araç yazınız.
- Dosya boyutu 100 bayttan küçükse tüm dosyayı baştan okuyacak güvenlik kontrolünü ekleyiniz.

---

### 📋 Ödev 4: Çok Dilli Mini Sözlük İndeksi
**Dosya Adı:** `RastgeleErisimliSozluk.java`

**Problem Tanımı:**  
İngilizce-Türkçe kelime çiftlerini sabit 60 baytlık bloklarda (`İngilizce: 15 char = 30 bayt`, `Türkçe: 15 char = 30 bayt`) tutan bir dosya hazırlayınız.
- İkili arama (*Binary Search*) algoritmasını `RandomAccessFile` üzerinde uygulayarak, sıralı kaydedilmiş kelimeler arasında dosyanın ortasına atlayıp ($seek(\text{orta} \times 60)$) logaritmik hızda kelime anlamını bulan motoru yazınız.
