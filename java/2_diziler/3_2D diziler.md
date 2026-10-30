# 2. Java'da İki Boyutlu (2D) Diziler ve Matrisler Kılavuzu

Bu rehber; Java programlama dilinde verileri satır ve sütunlardan oluşan tablolar, ızgaralar ve matrisler şeklinde saklamamızı sağlayan **İki Boyutlu (2D) Dizileri (Multidimensional Arrays)**, Java'nın *"Dizilerin Dizisi"* (*Array of Arrays*) felsefesini, düzensiz (**Jagged / Ragged**) dizileri, bellek mimarisini, matris algoritmalarını (köşegenler, transpoz, satır/sütun toplamları), sık düşülen tuzakları ve öğrencilerin çözebileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 2_diziler**  
> İlgili Önceki Dokümanlar:  
> • [1_dizi giriş.md](./1_dizi%20giriş.md)  
> • [2_string dizi.md](./2_string%20dizi.md)

---

## 📌 İçindekiler
1. [İki Boyutlu (2D) Dizi Nedir ve Neden İhtiyaç Duyulur?](#1-iki-boyutlu-2d-dizi-nedir-ve-neden-ihtiyaç-duyulur)
2. [2D Dizi Tanımlama, Bellek Tahsisi ve İlklendirme](#2-2d-dizi-tanımlama-bellek-tahsisi-ve-ilklendirme)
3. [Java'nın Dizi Felsefesi: "Dizilerin Dizisi" ve Bellek Mimarisi](#3-javanın-dizi-felsefesi-dizilerin-dizisi-ve-bellek-mimarisi)
4. [Düzensiz / Pürüzlü Diziler (Jagged / Ragged Arrays)](#4-düzensiz--pürüzlü-diziler-jagged--ragged-arrays)
5. [2D Dizilerde Boyut Mantığı: `length` ve `[i].length`](#5-2d-dizilerde-boyut-mantığı-length-ve-ilength)
6. [2D Dizilerde Dolaşma (İç İçe Döngüler ve Biçimli Yazdırma)](#6-2d-dizilerde-dolaşma-iç-içe-döngüler-ve-biçimli-yazdırma)
7. [Temel ve İleri Matris Algoritmaları](#7-temel-ve-ileri-matris-algoritmaları)
8. [En Sık Yapılan Hatalar ve Tuzaklar](#8-en-sık-yapılan-hatalar-ve-tuzaklar)
9. [Kapsamlı Örnek Konsol Uygulaması: `SinemaSalonuRezervasyonu`](#9-kapsamlı-örnek-konsol-uygulaması-sinemasalonurezervasyonu)
10. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#10-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
11. [Ödev Projeleri](#11-ödev-projeleri)

---

## 1. İki Boyutlu (2D) Dizi Nedir ve Neden İhtiyaç Duyulur?

Tek boyutlu (1D) diziler verileri düz bir hat/liste üzerinde saklamak için harikadır. Ancak gerçek dünyadaki pek çok problem tek bir satıra sığmaz, **Satır (Row)** ve **Sütun (Column)** ekseninde iki boyutlu bir koordinat düzlemi gerektirir:

- **Satranç / Dama Tahtası:** 8 satır ve 8 sütundan oluşan 64 kare.
- **Sinema / Tiyatro Salonu:** 10 sıra (satır) ve her sırada 15 koltuk (sütun).
- **Dijital Fotoğraflar / Görseller:** Piksellerden oluşan satır ve sütun ızgarası.
- **Excel Tabloları:** Bir sınıftaki 30 öğrencinin 4 farklı dersine ait not çizelgesi.
- **Haritalar ve Labirent Oyunları:** X ve Y koordinat düzlemi.

Matematikte ve programlamada bu tür tablolara **Matris (Matrix)**, Java'daki karşılığına ise **İki Boyutlu Dizi (2D Array)** denir.

---

## 2. 2D Dizi Tanımlama, Bellek Tahsisi ve İlklendirme

2D dizi tanımlarken iki çift köşeli parantez `[][]` kullanılır:
- Birinci parantez: **Satır İndeksini (`row`)**,
- İkinci parantez: **Sütun İndeksini (`column`)** temsil eder.

```text
matris[satirIndeksi][sutunIndeksi]
```

---

### Yöntem 1: Boyutları Belirleyip Boş Matris Açma
```java
// 3 satır ve 4 sütundan oluşan (toplam 3 x 4 = 12 hücreli) int matrisi:
int[][] tablo = new int[3][4];

// Hücrelere tek tek değer atama:
tablo[0][0] = 10; // 0. satır, 0. sütun (Sol üst köşe)
tablo[0][1] = 20; // 0. satır, 1. sütun
tablo[2][3] = 99; // 2. satır, 3. sütun (Sağ alt köşe)
```

---

### Yöntem 2: Dizi Literalleri ile Doğrudan Değer Atama
Eğer matrisin elemanları önceden belliyse iç içe süslü parantezlerle doğrudan tanımlayabiliriz:

```java
int[][] matris = {
    { 1,  2,  3 },  // 0. Satır
    { 4,  5,  6 },  // 1. Satır
    { 7,  8,  9 }   // 2. Satır
};
```
*Görsel Tablo Temsili:*
| İndeks | Sütun 0 (`[j=0]`) | Sütun 1 (`[j=1]`) | Sütun 2 (`[j=2]`) |
| :---: | :---: | :---: | :---: |
| **Satır 0 (`[i=0]`)** | `1` | `2` | `3` |
| **Satır 1 (`[i=1]`)** | `4` | `5` | `6` |
| **Satır 2 (`[i=2]`)** | `7` | `8` | `9` |

---

## 3. Java'nın Dizi Felsefesi: "Dizilerin Dizisi" ve Bellek Mimarisi

C veya C++ gibi dillerde iki boyutlu bir dizi bellekte kesintisiz tek bir devasa blok olarak tahsis edilir. **Ancak Java'da durum tamamen farklıdır!**

> 💡 **Java Felsefesi:** Java'da gerçek anlamda çok boyutlu dizi yoktur!  
> Java'da 2D bir dizi, **elemanları birer 1D dizi referansı olan tek boyutlu bir ana dizidir** (*Array of Arrays*).

### 🧠 Bellek Şeması:
1. **Stack:** `matris` referans değişkeni yer alır.
2. **Heap (Ana Satır Dizisi):** 3 elemanlı bir ana dizi açılır (`matris[0]`, `matris[1]`, `matris[2]`). Bu dizinin hücrelerinde sayı değil, **alt dizilerin bellek adresleri** tutulur!
3. **Heap (Bağımsız Sütun Dizileri):** Her satır için belleğin farklı yerlerinde 3 adet bağımsız 1D dizi açılır.

```text
[ STACK ]                   [ HEAP: ANA SATIR DİZİSİ ]             [ HEAP: SÜTUN DİZİLERİ ]
matris ----(Adres 0x1)----> [0] (Adres 0xA) --------------------> [ 1 | 2 | 3 ]
                            [1] (Adres 0xB) --------------------> [ 4 | 5 | 6 ]
                            [2] (Adres 0xC) --------------------> [ 7 | 8 | 9 ]
```

---

## 4. Düzensiz / Pürüzlü Diziler (Jagged / Ragged Arrays)

Java'da 2D dizi "dizilerin dizisi" olduğu için **her satırın aynı sayıda sütuna sahip olma zorunluluğu yoktur!**  
Her satırın uzunluğunun birbirinden farklı olduğu bu yapılara **Düzensiz Dizi (Jagged Array)** denir.

### 💻 Adım Adım Düzensiz Dizi Oluşturma:

```java
public class DuzensizDiziDemo {
    public static void main(String[] args) {
        // 1. Aşama: Satır sayısını (3) belirtiyoruz, sütun parantezini BOŞ bırakıyoruz:
        int[][] piramit = new int[3][];

        // 2. Aşama: Her satır için farklı boyutta bağımsız 1D dizi bağlıyoruz:
        piramit[0] = new int[1]; // 0. satır 1 elemanlı
        piramit[1] = new int[3]; // 1. satır 3 elemanlı
        piramit[2] = new int[2]; // 2. satır 2 elemanlı

        // Değer atayalım:
        piramit[0][0] = 5;
        piramit[1][0] = 10; piramit[1][1] = 20; piramit[1][2] = 30;
        piramit[2][0] = 40; piramit[2][1] = 50;

        // Ekrana basalım:
        for (int i = 0; i < piramit.length; i++) {
            for (int j = 0; j < piramit[i].length; j++) {
                System.out.print(piramit[i][j] + " ");
            }
            System.out.println();
        }
    }
}
```

**Ekran Çıktısı:**
```text
5 
10 20 30 
40 50 
```

> 🌟 **Nerede Kullanılır?** Bir şirketteki departmanların çalışan sayıları farklı olduğunda veya her ayın çektiği gün sayısı farklı olduğunda bellek israfını önlemek için mükemmel bir çözümdür.

---

## 5. 2D Dizilerde Boyut Mantığı: `length` ve `[i].length`

İki boyutlu dizilerde boyut sorgularken en sık yapılan hata `length`'in neyi temsil ettiğini unutmaktır:

- **`matris.length`:** Ana dizinin boyutunu, yani **TOPLAM SATIR SAYISINI** verir.
- **`matris[i].length`:** `i.` satırdaki alt dizinin boyutunu, yani **O SATIRDAKİ SÜTUN SAYISINI** verir.

```java
int[][] tablo = new int[4][7]; // 4 satır, 7 sütun

System.out.println("Satır Sayısı : " + tablo.length);    // 4
System.out.println("Sütun Sayısı : " + tablo[0].length); // 7
```

---

## 6. 2D Dizilerde Dolaşma (İç İçe Döngüler ve Biçimli Yazdırma)

Bir matrisin tüm elemanlarına erişmek için **iç içe iki döngü (nested loops)** kullanılır:
- **Dış Döngü (`i`):** Satırları gezer (`0`'dan `matris.length - 1`'e kadar).
- **İç Döngü (`j`):** O satırdaki sütunları gezer (`0`'dan `matris[i].length - 1`'e kadar).

### 💻 1. Klasik `for` ile Tablo Halinde Yazdırma:

```java
int[][] matris = {
    {12, 45, 6},
    {78, 9, 34},
    {5, 67, 89}
};

for (int i = 0; i < matris.length; i++) {
    for (int j = 0; j < matris[i].length; j++) {
        // '\t' tab boşluğu bırakarak sayıları hizalı basar:
        System.out.print(matris[i][j] + "\t");
    }
    System.out.println(); // Satır bitince alt satıra geç!
}
```

### 💻 2. Modern Gelişmiş `for-each` ile Dolaşma:

```java
// Dış döngü her adımda bir 1D satır dizisi yakalar (int[]):
for (int[] satir : matris) {
    // İç döngü o satırdaki tek tek sayıları yakalar (int):
    for (int eleman : satir) {
        System.out.print(eleman + "\t");
    }
    System.out.println();
}
```

---

## 7. Temel ve İleri Matris Algoritmaları

### 💻 1. Tüm Elemanların Toplamı ve Ortalaması

```java
int[][] matris = {
    {10, 20, 30},
    {40, 50, 60}
};

int toplam = 0;
int toplamElemanSayisi = 0;

for (int i = 0; i < matris.length; i++) {
    for (int j = 0; j < matris[i].length; j++) {
        toplam += matris[i][j];
        toplamElemanSayisi++;
    }
}

double ortalama = (double) toplam / toplamElemanSayisi;
System.out.println("Toplam: " + toplam + ", Ortalama: " + ortalama);
```

---

### 💻 2. Satır Satır ve Sütun Sütun Toplamları

Öğrencilerin sınav notlarını düşünelim (Satırlar: Öğrenciler, Sütunlar: Sınavlar):

```java
int[][] notlar = {
    {70, 80, 90}, // 1. Öğrenci
    {60, 65, 70}, // 2. Öğrenci
    {85, 90, 95}  // 3. Öğrenci
};

// A) Her Satırın (Öğrencinin) Toplamı:
for (int i = 0; i < notlar.length; i++) {
    int satirToplami = 0;
    for (int j = 0; j < notlar[i].length; j++) {
        satirToplami += notlar[i][j];
    }
    System.out.println((i + 1) + ". Öğrencinin Toplam Puanı: " + satirToplami);
}

// B) Her Sütunun (Sınavın) Ortalaması (Kare/Dikdörtgen matrisler için):
int sutunSayisi = notlar[0].length;
for (int j = 0; j < sutunSayisi; j++) {
    int sutunToplami = 0;
    for (int i = 0; i < notlar.length; i++) {
        sutunToplami += notlar[i][j];
    }
    System.out.printf("%d. Sınavın Sınıf Ortalaması: %.2f\n", (j + 1), (double)sutunToplami / notlar.length);
}
```

---

### 💻 3. Kare Matrislerde Asıl ve Ters Köşegen Elemanları

Kare matrislerde ($N \times N$):
- **Asıl Köşegen (Primary Diagonal):** Satır ve sütun indekslerinin eşit olduğu hücrelerdir (`matris[i][i]`).
- **Ters Köşegen (Secondary Diagonal):** Sağ üstten sol alta inen hattır (`matris[i][n - 1 - i]`).

```java
int[][] kareMatris = {
    { 1,  2,  3 },
    { 4,  5,  6 },
    { 7,  8,  9 }
};

int n = kareMatris.length;
int asilKosegenToplami = 0;
int tersKosegenToplami = 0;

// Tek bir döngü ile O(N) hızında iki köşegeni de toplayabiliriz:
for (int i = 0; i < n; i++) {
    asilKosegenToplami += kareMatris[i][i];          // (0,0)=1, (1,1)=5, (2,2)=9 -> 15
    tersKosegenToplami += kareMatris[i][n - 1 - i];  // (0,2)=3, (1,1)=5, (2,0)=7 -> 15
}

System.out.println("Asıl Köşegen Toplamı : " + asilKosegenToplami);
System.out.println("Ters Köşegen Toplamı : " + tersKosegenToplami);
```

---

### 💻 4. Matris Transpozu (Satırları Sütun Yapma)

$M \times N$ boyutundaki bir matrisin transpozu $N \times M$ boyutundadır:

```java
int[][] kaynak = {
    {1, 2, 3},
    {4, 5, 6}
}; // 2 Satır, 3 Sütun

int satir = kaynak.length;
int sutun = kaynak[0].length;

int[][] transpoz = new int[sutun][satir]; // 3 Satır, 2 Sütun

for (int i = 0; i < satir; i++) {
    for (int j = 0; j < sutun; j++) {
        transpoz[j][i] = kaynak[i][j]; // Satır ve sütun yer değiştirir
    }
}
```

---

## 8. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: `Arrays.toString()` ile 2D Diziyi Yazdırmaya Çalışmak
Tek boyutlu dizilerde `Arrays.toString(dizi)` çalışır. Ancak 2D dizide kullanırsanız içteki dizilerin bellek adreslerini basar!

```java
int[][] m = {{1, 2}, {3, 4}};

System.out.println(Arrays.toString(m)); 
// ❌ ÇIKTI: [[I@6acbcfc0, [I@5f184fc6] (Adres kodları)

// ✅ DOĞRU: Çok boyutlu diziler için Arrays.deepToString() kullanılmalıdır:
System.out.println(Arrays.deepToString(m)); 
// ÇIKTI: [[1, 2], [3, 4]]
```

---

### 2. Tuzak: Düzensiz Dizide Henüz Oluşturulmamış Satıra Erişmek

```java
int[][] d = new int[3][];
// d[0], d[1], d[2] şu an Heap'te 'null'dır!

// ❌ ÇÖKER: NullPointerException! Çünkü 0. satıra henüz new int[...] bağlanmadı!
d[0][0] = 10; 
```

---

### 3. Tuzak: Dikdörtgen Matrislerde İç Döngü Sınırına `matris.length` Yazmak

```java
int[][] tablo = new int[2][5]; // 2 satır, 5 sütun

for (int i = 0; i < tablo.length; i++) {
    // ❌ HATA: j < tablo.length yazılırsa sütunlar 5 yerine sadece 2'ye kadar döner!
    for (int j = 0; j < tablo[i].length; j++) { // DOĞRUSU: tablo[i].length
        // ...
    }
}
```

---

## 9. Kapsamlı Örnek Konsol Uygulaması: `SinemaSalonuRezervasyonu`

Aşağıdaki program; 5 sıra ve her sırada 6 koltuktan oluşan bir sinema salonunu 2D `char[][]` matrisiyle modelleyen, boş koltukları `[ - ]`, dolu koltukları `[ X ]` ile gösteren, bilet satışı ve hasılat raporu sunan eksiksiz ve çalıştırılabilir bir uygulamadır:

```java
import java.util.Scanner;

public class SinemaSalonuRezervasyonu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int SIRA_SAYISI = 5;
        final int KOLTUK_SAYISI = 6;
        final double BILET_FIYATI = 150.0; // TL

        // 5 Satır x 6 Sütunluk Sinema Salonu Matrisi
        char[][] salon = new char[SIRA_SAYISI][KOLTUK_SAYISI];

        // 1. Matrisi Boş Koltuklarla İlklendirme ('-')
        for (int i = 0; i < SIRA_SAYISI; i++) {
            for (int j = 0; j < KOLTUK_SAYISI; j++) {
                salon[i][j] = '-';
            }
        }

        int satilanBilet = 0;
        int secim;

        do {
            System.out.println("\n==========================================");
            System.out.println("   KTÜ KAMPÜS SİNEMASI REZERVASYON SİSTEMİ ");
            System.out.println("==========================================");
            System.out.println("1 - Salon Koltuk Planını Görüntüle");
            System.out.println("2 - Bilet Satın Al (Koltuk Seç)");
            System.out.println("3 - Bilet İptal Et");
            System.out.println("4 - Güncel Hasılat ve Doluluk Raporu");
            System.out.println("0 - Çıkış Yap");
            System.out.print("İşlem seçiniz: ");
            secim = scanner.nextInt();

            switch (secim) {
                case 1:
                    System.out.println("\n----------- PERDE / SAHNE -----------");
                    System.out.print("Sıra\\Koltuk\t");
                    for (int k = 1; k <= KOLTUK_SAYISI; k++) {
                        System.out.print("[" + k + "]\t");
                    }
                    System.out.println();

                    for (int i = 0; i < SIRA_SAYISI; i++) {
                        System.out.print("Sıra " + (i + 1) + " :\t");
                        for (int j = 0; j < KOLTUK_SAYISI; j++) {
                            System.out.print("[ " + salon[i][j] + " ]\t");
                        }
                        System.out.println();
                    }
                    System.out.println("-------------------------------------");
                    System.out.println("Gösterim: [ - ] Boş  |  [ X ] Dolu");
                    break;

                case 2:
                    System.out.print("Seçmek istediğiniz Sıra No (1-" + SIRA_SAYISI + "): ");
                    int sira = scanner.nextInt() - 1; // 0 tabanlı indekse çevir

                    System.out.print("Seçmek istediğiniz Koltuk No (1-" + KOLTUK_SAYISI + "): ");
                    int koltuk = scanner.nextInt() - 1;

                    // Geçerlilik Denetimi:
                    if (sira < 0 || sira >= SIRA_SAYISI || koltuk < 0 || koltuk >= KOLTUK_SAYISI) {
                        System.out.println("❌ Hata: Geçersiz sıra veya koltuk numarası girdiniz!");
                    } else if (salon[sira][koltuk] == 'X') {
                        System.out.println("❌ Üzgünüz, bu koltuk zaten DOLU!");
                    } else {
                        salon[sira][koltuk] = 'X';
                        satilanBilet++;
                        System.out.println("✅ Tebrikler! Sıra " + (sira + 1) + " Koltuk " + (koltuk + 1) + " adınıza rezerve edildi.");
                        System.out.println("Ödenen Tutar: " + BILET_FIYATI + " TL");
                    }
                    break;

                case 3:
                    System.out.print("İptal edilecek Sıra No (1-" + SIRA_SAYISI + "): ");
                    int iptalSira = scanner.nextInt() - 1;

                    System.out.print("İptal edilecek Koltuk No (1-" + KOLTUK_SAYISI + "): ");
                    int iptalKoltuk = scanner.nextInt() - 1;

                    if (iptalSira < 0 || iptalSira >= SIRA_SAYISI || iptalKoltuk < 0 || iptalKoltuk >= KOLTUK_SAYISI) {
                        System.out.println("❌ Hata: Geçersiz koordinat!");
                    } else if (salon[iptalSira][iptalKoltuk] == '-') {
                        System.out.println("❌ Bu koltuk zaten BOŞ, iptal edilemez!");
                    } else {
                        salon[iptalSira][iptalKoltuk] = '-';
                        satilanBilet--;
                        System.out.println("✅ Rezervasyon başarıyla iptal edildi. Ücret iade edildi.");
                    }
                    break;

                case 4:
                    int toplamKoltuk = SIRA_SAYISI * KOLTUK_SAYISI;
                    double dolulukOrani = ((double) satilanBilet / toplamKoltuk) * 100.0;
                    double toplamHasilat = satilanBilet * BILET_FIYATI;

                    System.out.println("\n------------ FİNANS VE DOLULUK RAPORU ------------");
                    System.out.println("Toplam Kapasite      : " + toplamKoltuk + " Koltuk");
                    System.out.println("Satılan Bilet Sayısı : " + satilanBilet);
                    System.out.println("Kalan Boş Koltuk     : " + (toplamKoltuk - satilanBilet));
                    System.out.printf("Salon Doluluk Oranı  : %%%.1f\n", dolulukOrani);
                    System.out.printf("Toplam Kasa Hasılatı : %.2f TL\n", toplamHasilat);
                    System.out.println("--------------------------------------------------");
                    break;

                case 0:
                    System.out.println("-> Sinema yönetim sisteminden çıkıldı. İyi seyirler!");
                    break;

                default:
                    System.out.println("❌ Geçersiz menü seçimi!");
                    break;
            }

        } while (secim != 0);

        scanner.close();
    }
}
```

---

## 10. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - İç İçe Döngü Çıktısı)
Aşağıdaki programın konsol çıktısını kağıt üzerinde adım adım hesaplayınız:

```java
public class MatrisIzleme {
    public static void main(String[] args) {
        int[][] m = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    m[i][j] = 1;
                } else if (i > j) {
                    m[i][j] = 2;
                } else {
                    m[i][j] = 0;
                }
            }
        }

        for (int[] satir : m) {
            for (int val : satir) System.out.print(val + " ");
            System.out.println();
        }
    }
}
```

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında programın çökmesine yol açan **2 büyük hatayı** bulunuz ve düzeltiniz:

```java
int[][] tablo = new int[3][];
tablo[0] = new int[2];
tablo[1] = new int[4];
// tablo[2] ilklendirilmedi!

for (int i = 0; i <= tablo.length; i++) { // Hata 1 nerede?
    for (int j = 0; j < tablo[i].length; j++) { // Hata 2 nerede?
        tablo[i][j] = i + j;
    }
}
```

---

### 📐 Alıştırma 3: Birim Matris (Identity Matrix) Üretici
Kullanıcıdan alınan bir `N` tamsayısına göre $N \times N$ boyutunda bir **Birim Matris** üreten programı yazınız.  
*(Birim Matris: Asıl köşegenindeki elemanlar `1`, diğer tüm elemanları `0` olan kare matristir).*

---

### 🔄 Alıştırma 4: Simetrik Matris Kontrolü
Verilen bir kare matrisin devriğine (transpozuna) eşit olup olmadığını (`matris[i][j] == matris[j][i]`) test eden ve geriye `boolean` dönen algoritmayı yazınız.

---

## 11. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Matris indis sınırlarına, iç içe döngü optimizasyonuna ve biçimli tablolara özen gösteriniz.

---

### 📋 Ödev 1: İki Kişilik XOX (Tic-Tac-Toe) Oyunu Motoru
**Dosya Adı:** `XoxOyunu.java`

**Problem Tanımı:**  
Konsolda 2 oyuncunun sırayla oynadığı 3x3'lük bir XOX oyunu geliştiriniz:
1. Oyun tahtası `char[3][3]` matrisi ile tutulsun ve başlangıçta boşluk (`' '`) ile doldurulsun.
2. Sırasıyla 1. Oyuncu (`'X'`) ve 2. Oyuncu (`'O'`) satır ve sütun koordinatlarını (0-2 arası) girsin.
3. **Kazanma Kontrolleri:**
   - Herhangi bir satırda 3 aynı sembol var mı?
   - Herhangi bir sütunda 3 aynı sembol var mı?
   - Asıl köşegen veya ters köşegende 3 aynı sembol var mı?
4. Biri kazandığında veya tahta tamamen dolup berabere kalındığında oyun sonlandırılmalıdır.

---

### 📋 Ödev 2: Matematiksel Matris Çarpımı Motoru
**Dosya Adı:** `MatrisCarpimi.java`

**Problem Tanımı:**  
Lineer cebirdeki matris çarpım kuralını uygulayınız:
- İki matrisin çarpılabilmesi için 1. matrisin sütun sayısı ile 2. matrisin satır sayısı eşit olmalıdır:  
  `A (M x K)` ile `B (K x N)` çarpılırsa sonuç `C (M x N)` boyutunda olur.
1. Formül: $C[i][j] = \sum_{k=0}^{K-1} (A[i][k] \times B[k][j])$
2. Boyut uyumsuzluğu varsa kullanıcıya *"Bu iki matris çarpılamaz!"* uyarısı basılmalıdır.
3. Çarpım sonucunda oluşan yeni matrisi ekrana düzenli bir tablo şeklinde yazdırınız.

---

### 📋 Ödev 3: Düzensiz Dizi (Jagged Array) ile Pascal Üçgeni
**Dosya Adı:** `PascalUcgeni.java`

**Problem Tanımı:**  
Kullanıcıdan `satirSayisi` (Örn: 6) alarak **Pascal Üçgenini** bir düzensiz `int[][]` dizisiyle oluşturunuz:
- 0. satır 1 elemanlıdır: `[1]`
- 1. satır 2 elemanlıdır: `[1, 1]`
- 2. satır 3 elemanlıdır: `[1, 2, 1]`
- 3. satır 4 elemanlıdır: `[1, 3, 3, 1]`
- **Kural:** Her satırın ilk ve son elemanı daima `1`'dir. Aradaki elemanlar ise bir üst satırdaki komşu iki sayının toplamıdır:  
  `pascal[i][j] = pascal[i - 1][j - 1] + pascal[i - 1][j];`
- Sonucu konsola piramit şeklinde hizalı olarak yazdırınız.

---

### 📋 Ödev 4: Görsel Piksel Parlaklık Filtresi ve Negatif Alma Simülasyonu
**Dosya Adı:** `PikselFiltresi.java`

**Problem Tanımı:**  
Siyah-beyaz bir dijital görüntüyü temsil eden 5x5 boyutunda, 0 ile 255 arasında gri tonlama piksel değerlerine sahip bir `int[][] resim` matrisi oluşturunuz (0: Tam Siyah, 255: Tam Beyaz):
1. **Parlaklık Artırma:** Kullanıcıdan bir parlaklık artış miktarı alınız (Örn: +30) ve tüm piksellere ekleyiniz. (Değer 255'i aşarsa 255'e sabitlenmelidir / *Clamping*).
2. **Görüntü Negatifi Alma:** Her pikseli `negatif[i][j] = 255 - resim[i][j]` formülüyle tersine çeviriniz.
3. Orijinal resim matrisini ve filtre uygulanmış yeni matrisi yan yana ekrana dökünüz.
