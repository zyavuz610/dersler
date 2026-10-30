# 4. Java'da Dizi Algoritmaları: Temel İstatistik, Sıralama ve Arama Kılavuzu

Bu rehber; Java programlama dilinde bir dizi üzerinde en sık uygulanan temel ve ileri algoritmaları, uç değerleri (**`min`, `max`**), merkezi eğilim istatistiklerini (**`avg` / `mean`, `median`, `mode`**), diziyi tersine çevirmeyi (**`reverse`**), popüler sıralama algoritmalarını (**Bubble Sort, Selection Sort, `Arrays.sort`**) ve arama yöntemlerini (**Linear Search, Binary Search**), karmaşıklık mantığını, sık düşülen tuzakları ve öğrencilerin çözebileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 2_diziler**  
> İlgili Önceki Dokümanlar:  
> • [1_dizi giriş.md](./1_dizi%20giriş.md)  
> • [2_string dizi.md](./2_string%20dizi.md)  
> • [3_2D diziler.md](./3_2D%20diziler.md)

---

## 📌 İçindekiler
1. [Dizi Algoritmalarına Giriş ve Veri Analizi](#1-dizi-algoritmalarına-giriş-ve-veri-analizi)
2. [Dizi Boyutu ve Boşluk Kontrolü: `length`](#2-dizi-boyutu-ve-boşluk-kontrolü-length)
3. [Uç Değerleri Bulma: En Küçük (`min`) ve En Büyük (`max`)](#3-uç-değerleri-bulma-en-küçük-min-ve-en-büyük-max)
4. [Aritmetik Ortalama: `mean` / `avg`](#4-aritmetik-ortalama-mean--avg)
5. [Ortanca Değer Algoritması: `median` (Medyan)](#5-ortanca-değer-algoritması-median-medyan)
6. [Tepe Değer Algoritması: `mode` (En Çok Tekrar Eden Sayı)](#6-tepe-değer-algoritması-mode-en-çok-tekrar-eden-sayı)
7. [Diziyi Tersine Çevirme: `reverse` (İki İşaretçi Yöntemi)](#7-diziyi-tersine-çevirme-reverse-iki-işaretçi-yöntemi)
8. [Sıralama Algoritmaları: `sort` (Bubble Sort, Selection Sort, `Arrays.sort`)](#8-sıralama-algoritmaları-sort-bubble-sort-selection-sort-arrayssort)
9. [Arama Algoritmaları: Doğrusal Arama vs İkili Arama (`Binary Search`)](#9-arama-algoritmaları-doğrusal-arama-vs-ikili-arama-binary-search)
10. [En Sık Yapılan Hatalar ve Tuzaklar](#10-en-sık-yapılan-hatalar-ve-tuzaklar)
11. [Kapsamlı Örnek Konsol Uygulaması: `DiziAlgoritmaVeIstatistikMotoru`](#11-kapsamlı-örnek-konsol-uygulaması-dizialgoritmaveistatistikmotoru)
12. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#12-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
13. [Ödev Projeleri](#13-ödev-projeleri)

---

## 1. Dizi Algoritmalarına Giriş ve Veri Analizi

Bir diziyi bellekte oluşturup içine verileri doldurmak işin sadece başlangıcıdır. Yazılım mühendisliğinde asıl değer, bu ham veriyi işleyip anlamlı kararlar üretmektir:
- Borsa verileri içinde günün en yüksek ve en düşük fiyatını bulmak,
- Sınıf notlarının ortalamasını ve medyanını hesaplamak,
- Ürünleri fiyata göre küçükten büyüğe sıralamak,
- Milyonlarca kayıt içinde aranan TC kimlik numarasını mikrosaniyeler içinde tespit etmek.

Bu işlemleri yapabilmek için her yazılımcının bilmesi gereken standart **dizi algoritmaları** bulunmaktadır.

---

## 2. Dizi Boyutu ve Boşluk Kontrolü: `length`

Dizi algoritmaları yazarken yapılacak ilk iş **veri güvenliği kontrolüdür**. Dizi `null` ise veya hiç elemanı yoksa (`length == 0`), hiçbir algoritma çalıştırılmamalıdır.

```java
public static boolean diziGecerliMi(int[] dizi) {
    return (dizi != null && dizi.length > 0);
}
```

- **`dizi.length`:** Dizinin bellekteki toplam kapasitesini verir ($O(1)$ hızında doğrudan okunur).
- **Son Eleman İndeksi:** `dizi.length - 1`

---

## 3. Uç Değerleri Bulma: En Küçük (`min`) ve En Büyük (`max`)

Bir dizideki en küçük veya en büyük elemanı bulmak için **"Şampiyon Seçme" (King of the Hill)** algoritması kullanılır:
1. Dizinin ilk elemanı (`dizi[0]`) geçici şampiyon ilan edilir.
2. Dizinin kalan elemanları tek tek taranır.
3. Şampiyondan daha büyük (veya daha küçük) biri çıkarsa yeni şampiyon o olur.

```java
public class MinMaxAlgoritmasi {

    // En Büyük Elemanı Bulan Metot:
    public static int maxBul(int[] dizi) {
        if (dizi == null || dizi.length == 0) throw new IllegalArgumentException("Dizi boş olamaz!");

        int enBuyuk = dizi[0]; // 1. elemanı şampiyon yap
        for (int i = 1; i < dizi.length; i++) {
            if (dizi[i] > enBuyuk) {
                enBuyuk = dizi[i];
            }
        }
        return enBuyuk;
    }

    // En Küçük Elemanı Bulan Metot:
    public static int minBul(int[] dizi) {
        if (dizi == null || dizi.length == 0) throw new IllegalArgumentException("Dizi boş olamaz!");

        int enKucuk = dizi[0];
        for (int i = 1; i < dizi.length; i++) {
            if (dizi[i] < enKucuk) {
                enKucuk = dizi[i];
            }
        }
        return enKucuk;
    }
}
```

> ⚠️ **Hayati Tuzak:** `enBuyuk` değişkenine başlangıçta `0` atamayınız! Eğer dizideki tüm sayılar negatifse (Örn: `{-10, -5, -20}`), sonuç yanlışlıkla `0` çıkar! Başlangıç değeri daima **`dizi[0]`** olmalıdır.

---

## 4. Aritmetik Ortalama: `mean` / `avg`

Aritmetik ortalama; dizideki tüm elemanların toplamının eleman sayısına bölünmesiyle bulunur.

$$\text{Ortalama} = \frac{\sum_{i=0}^{N-1} \text{dizi}[i]}{N}$$

```java
public static double ortalamaHesapla(int[] dizi) {
    if (dizi == null || dizi.length == 0) return 0.0;

    long toplam = 0; // Çok sayıda eleman varsa int taşmasını önlemek için long kullanılır
    for (int sayi : dizi) {
        toplam += sayi;
    }

    // ⚠️ KRİTİK: Tamsayı bölmesi yapmamak için double tür dönüştürmesi (cast) zorunludur:
    return (double) toplam / dizi.length;
}
```

---

## 5. Ortanca Değer Algoritması: `median` (Medyan)

Veri setinde aşırı uç değerler (örneğin 10 kişinin maaşı 25.000 TL iken şirketin CEO'sunun 2.000.000 TL alması) ortalamayı yanıltır. Bu gibi durumlarda verinin gerçek merkezini bulmak için **Medyan (Ortanca Değer)** kullanılır.

### 📌 Medyan Hesaplama Adımları:
1. **Dizi kesinlikle küçükten büyüğe sıralanmalıdır!**
2. **Eleman sayısı TEK ise:** Tam ortadaki eleman medyandır.  
   $$\text{İndeks} = \frac{N}{2}$$
3. **Eleman sayısı ÇİFT ise:** Ortada kalan iki elemanın aritmetik ortalaması medyandır.  
   $$\text{Medyan} = \frac{\text{dizi}[\frac{N}{2} - 1] + \text{dizi}[\frac{N}{2}]}{2.0}$$

```java
import java.util.Arrays;

public class MedyanAlgoritmasi {

    public static double medyanBul(int[] dizi) {
        if (dizi == null || dizi.length == 0) return 0.0;

        // ⚠️ DİKKAT: Orijinal diziyi bozmamak için kopyasını (klonunu) sıralıyoruz!
        int[] sirali = dizi.clone();
        Arrays.sort(sirali);

        int n = sirali.length;

        if (n % 2 != 0) {
            // Tek sayıda eleman varsa tam ortadaki eleman:
            return sirali[n / 2];
        } else {
            // Çift sayıda eleman varsa ortadaki iki sayının ortalaması:
            return (sirali[(n / 2) - 1] + sirali[n / 2]) / 2.0;
        }
    }
}
```

**Örnek Hesaplama:**
- Dizi: `{7, 1, 3}` ➡️ Sıralı: `{1, 3, 7}` ➡️ Medyan: **`3`**
- Dizi: `{10, 20, 30, 40}` ➡️ Sıralı: `{10, 20, 30, 40}` ➡️ Medyan: $(20 + 30) / 2.0 =$ **`25.0`**

---

## 6. Tepe Değer Algoritması: `mode` (En Çok Tekrar Eden Sayı)

Dizide frekansı (görülme sıklığı) en yüksek olan elemandır.

```java
public class ModAlgoritmasi {

    public static int modBul(int[] dizi) {
        if (dizi == null || dizi.length == 0) return -1;

        int enCokTekrarEden = dizi[0];
        int maxTekrarSayisi = 0;

        for (int i = 0; i < dizi.length; i++) {
            int sayac = 0;
            for (int j = 0; j < dizi.length; j++) {
                if (dizi[j] == dizi[i]) {
                    sayac++;
                }
            }

            if (sayac > maxTekrarSayisi) {
                maxTekrarSayisi = sayac;
                enCokTekrarEden = dizi[i];
            }
        }

        return enCokTekrarEden;
    }
}
```

---

## 7. Diziyi Tersine Çevirme: `reverse` (İki İşaretçi Yöntemi)

Bir dizinin elemanlarını baştan sona ters çevirmek için en verimli algoritma **İki İşaretçi (Two-Pointer)** yaklaşımıdır:
- `sol` işaretçi baştan (`0`), `sag` işaretçi sondan (`length - 1`) başlar.
- İki eleman takas edilir (*swap*), sol bir adım sağa, sağ bir adım sola kayar.
- İki işaretçi ortada karşılaştığında döngü biter.

```java
public class ReverseAlgoritmasi {

    // Diziyi yerinde (ek bellek açmadan) tersine çeviren metot:
    public static void tersCevirYerinde(int[] dizi) {
        if (dizi == null) return;

        int sol = 0;
        int sag = dizi.length - 1;

        while (sol < sag) {
            // Takas (Swap) İşlemi:
            int gecici = dizi[sol];
            dizi[sol] = dizi[sag];
            dizi[sag] = gecici;

            sol++;
            sag--;
        }
    }
}
```

> 💡 **Performans:** Bu algoritma sadece $N / 2$ adımda biter ve ek bellek kullanmaz ($O(1)$ alan, $O(N)$ zaman karmaşıklığı).

---

## 8. Sıralama Algoritmaları: `sort`

### 1. Kabarcık Sıralaması (Bubble Sort)
En sezgisel ve klasik sıralama algoritmasıdır. Yan yana duran iki elemanı karşılaştırır, eğer sol sağdakinden büyükse yerlerini değiştirir. Her turda dizinin en büyük elemanı su yüzeyine çıkan bir baloncuk gibi dizinin en sağına yerleşir.

```java
public static void bubbleSort(int[] dizi) {
    int n = dizi.length;
    for (int i = 0; i < n - 1; i++) {
        boolean takasYapildiMi = false;

        for (int j = 0; j < n - 1 - i; j++) {
            if (dizi[j] > dizi[j + 1]) {
                // Swap (Takas):
                int temp = dizi[j];
                dizi[j] = dizi[j + 1];
                dizi[j + 1] = temp;
                takasYapildiMi = true;
            }
        }

        // Optimizasyon: Eğer bir tur boyunca hiç takas yapılmadıysa dizi zaten sıralıdır!
        if (!takasYapildiMi) break;
    }
}
```

---

### 2. Seçmeli Sıralama (Selection Sort)
Her turda dizinin henüz sıralanmamış kısmındaki **en küçük elemanı bulur** ve onu sıralanmamış kısmın en başına yerleştirir.

```java
public static void selectionSort(int[] dizi) {
    int n = dizi.length;

    for (int i = 0; i < n - 1; i++) {
        int minIndeks = i;

        for (int j = i + 1; j < n; j++) {
            if (dizi[j] < dizi[minIndeks]) {
                minIndeks = j;
            }
        }

        // Bulunan en küçüğü i. sıraya al:
        int temp = dizi[minIndeks];
        dizi[minIndeks] = dizi[i];
        dizi[i] = temp;
    }
}
```

---

### 3. Java'nın Yerleşik ve En Hızlı Sıralaması: `Arrays.sort()`
Üretim ortamlarında Bubble Sort yazılmaz. Java'nın hazır `Arrays.sort()` metodu kullanılır. İlkel tipler için son derece optimize edilmiş **Dual-Pivot Quicksort** algoritmasını kullanır ($O(N \log N)$ süper hızlı).

```java
import java.util.Arrays;

int[] sayilar = {64, 34, 25, 12, 22, 11, 90};
Arrays.sort(sayilar); // Anında küçükten büyüğe sıralar
```

---

## 9. Arama Algoritmaları: Doğrusal Arama vs İkili Arama (`Binary Search`)

| Kriter | Doğrusal Arama (Linear Search) | İkili Arama (Binary Search) |
| :--- | :--- | :--- |
| **Ön Koşul** | Dizinin sıralı olmasına **gerek yoktur**. | **DİZİ KESİNLİKLE SIRALI OLMAK ZORUNDADIR!** |
| **Çalışma Mantığı** | 0. indeksten sonuna kadar tek tek arar. | Ortadaki elemana bakar; hedef küçükse sol yarıya, büyükse sağ yarıya atlar. |
| **Zaman Hızı** | **$O(N)$** (1.000.000 elemanda en kötü 1.000.000 adım). | **$O(\log N)$** (1.000.000 elemanda en kötü **20 adımda** bulur!). |

```java
public class AramaAlgoritmalari {

    // 1. Doğrusal Arama (Linear Search):
    public static int linearSearch(int[] dizi, int hedef) {
        for (int i = 0; i < dizi.length; i++) {
            if (dizi[i] == hedef) return i;
        }
        return -1;
    }

    // 2. İkili Arama (Binary Search):
    public static int binarySearch(int[] siraliDizi, int hedef) {
        int sol = 0;
        int sag = siraliDizi.length - 1;

        while (sol <= sag) {
            int orta = sol + (sag - sol) / 2; // Taşmayı önleyen orta indeks formülü

            if (siraliDizi[orta] == hedef) {
                return orta; // Bulundu!
            } else if (siraliDizi[orta] < hedef) {
                sol = orta + 1; // Hedef sağ yarıda
            } else {
                sag = orta - 1; // Hedef sol yarıda
            }
        }
        return -1; // Bulunamadı
    }
}
```

---

## 10. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: Medyan Hesaplarken Orijinal Diziyi Bozmak (*Side Effect*)
Eğer medyan metodunda `Arrays.sort(dizi)` derseniz, diziler referansla aktarıldığı için `main` metodundaki orijinal dizinin sırası da kalıcı olarak bozulur!
- **Çözüm:** Medyan hesaplamadan önce daima `int[] sirali = dizi.clone();` ile bağımsız bir kopya oluşturulmalıdır.

---

### 2. Tuzak: Ters Çevirmede Döngüyü `dizi.length`'e Kadar Döndürmek
`reverse` yaparken döngüyü dizinin sonuna kadar döndürürseniz, elemanlar önce tersine döner, sonra tekrar tersine dönerek **eski haline geri gelir!**
- **Çözüm:** Döngü sadece `dizi.length / 2` veya `sol < sag` şartında durmalıdır.

---

### 3. Tuzak: Aritmetik Ortalamada Küsurat Kaybı
`int toplam = 15; int n = 2; double ort = toplam / n;` yazdığınızda ekrana `7.5` değil `7.0` basılır. Çünkü iki `int` bölündüğünde sonuç `int` olur!
- **Çözüm:** `(double) toplam / n` yazılmalıdır.

---

## 11. Kapsamlı Örnek Konsol Uygulaması: `DiziAlgoritmaVeIstatistikMotoru`

Aşağıdaki program; rastgele üretilen bir dizi üzerinde `min`, `max`, `avg/mean`, `median`, `mode`, `reverse`, `sort` ve `binarySearch` algoritmalarını tek bir menüde çalıştıran derli toplu ve çalıştırılabilir bir uygulamadır:

```java
import java.util.Scanner;
import java.util.Arrays;

public class DiziAlgoritmaVeIstatistikMotoru {

    // 1. Min Değer
    public static int minBul(int[] dizi) {
        int min = dizi[0];
        for (int s : dizi) if (s < min) min = s;
        return min;
    }

    // 2. Max Değer
    public static int maxBul(int[] dizi) {
        int max = dizi[0];
        for (int s : dizi) if (s > max) max = s;
        return max;
    }

    // 3. Ortalama (Mean / Avg)
    public static double ortalamaBul(int[] dizi) {
        long toplam = 0;
        for (int s : dizi) toplam += s;
        return (double) toplam / dizi.length;
    }

    // 4. Medyan (Ortanca Değer)
    public static double medyanBul(int[] dizi) {
        int[] kopya = dizi.clone();
        Arrays.sort(kopya);
        int n = kopya.length;
        if (n % 2 != 0) {
            return kopya[n / 2];
        } else {
            return (kopya[(n / 2) - 1] + kopya[n / 2]) / 2.0;
        }
    }

    // 5. Yerinde Ters Çevirme (Reverse)
    public static void tersCevir(int[] dizi) {
        int sol = 0, sag = dizi.length - 1;
        while (sol < sag) {
            int temp = dizi[sol];
            dizi[sol] = dizi[sag];
            dizi[sag] = temp;
            sol++;
            sag--;
        }
    }

    // 6. Kabarcık Sıralaması (Bubble Sort)
    public static void bubbleSort(int[] dizi) {
        int n = dizi.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (dizi[j] > dizi[j + 1]) {
                    int t = dizi[j];
                    dizi[j] = dizi[j + 1];
                    dizi[j + 1] = t;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   KTÜ DİZİ ALGORİTMA VE İSTATİSTİK LAB   ");
        System.out.println("==========================================");

        // 10 Elemanlı Rastgele Test Dizisi Oluşturma (1-50 arası):
        int[] veriler = new int[10];
        for (int i = 0; i < veriler.length; i++) {
            veriler[i] = (int)(Math.random() * 50) + 1;
        }

        System.out.println("Orijinal Test Dizisi: " + Arrays.toString(veriler));
        System.out.println("------------------------------------------");

        // İstatistiki Rapor
        System.out.println("Dizinin Boyutu (length)   : " + veriler.length);
        System.out.println("En Küçük Değer (min)      : " + minBul(veriler));
        System.out.println("En Büyük Değer (max)      : " + maxBul(veriler));
        System.out.printf("Aritmetik Ortalama (mean) : %.2f\n", ortalamaBul(veriler));
        System.out.println("Ortanca Değer (median)    : " + medyanBul(veriler));

        // Ters Çevirme (Reverse)
        int[] tersKopya = veriler.clone();
        tersCevir(tersKopya);
        System.out.println("Ters Çevrilmiş Hali       : " + Arrays.toString(tersKopya));

        // Sıralama (Sort)
        int[] siraliKopya = veriler.clone();
        bubbleSort(siraliKopya);
        System.out.println("Sıralanmış Hali (Sort)    : " + Arrays.toString(siraliKopya));

        // İkili Arama Testi
        System.out.print("\nSıralı dizi içinde aranacak sayıyı giriniz: ");
        int aranan = scanner.nextInt();
        int bulunanIndeks = Arrays.binarySearch(siraliKopya, aranan);

        if (bulunanIndeks >= 0) {
            System.out.println("✅ " + aranan + " sayısı sıralı dizinin " + bulunanIndeks + ". indeksinde bulundu!");
        } else {
            System.out.println("❌ " + aranan + " sayısı dizide mevcut değil.");
        }

        System.out.println("==========================================");
        scanner.close();
    }
}
```

---

## 12. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Bubble Sort 1. Tur)
`{5, 1, 4, 2, 8}` dizisi için Bubble Sort algoritmasının **yalnızca 1. dış döngü turu (`i=0`) bittiğinde** dizinin alacağı yeni hali kağıt üzerinde adım adım gösteriniz.

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki medyan metodunda yer alan **2 kritik hatayı** bulunuz ve düzeltiniz:

```java
public static double hataliMedyan(int[] dizi) {
    // 1. ve 2. Hata nerede?
    int n = dizi.length;
    if (n % 2 != 0) {
        return dizi[n / 2];
    } else {
        return (dizi[n / 2] + dizi[(n / 2) - 1]) / 2;
    }
}
```

---

### 🥈 Alıştırma 3: İkinci En Büyük Elemanı Bulma (*Second Largest*)
Bir diziyi sıralamadan ($O(N)$ zaman sınırında), sadece tek bir döngü içerisinde dizideki **en büyük ikinci sayıyı** bulan metodu yazınız.

---

### ⚖️ Alıştırma 4: Dizi Dengesi (Denge / Pivot Noktası Bulma)
Bir dizide solundaki elemanların toplamı, sağındaki elemanların toplamına eşit olan indeks numarasına **Denge İndeksi (Equilibrium Index)** denir. Verilen bir dizide denge indeksini bulan algoritmayı tasarlayınız.

---

## 13. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Fonksiyonel tasarıma, dizilerin klonlanmasına ve algoritmik verimliliğe özen gösteriniz.

---

### 📋 Ödev 1: Borsa ve Kripto Varlık Volatilite Analizörü
**Dosya Adı:** `BorsaAnalizMotoru.java`

**Problem Tanımı:**  
Bir hisse senedinin 30 günlük kapanış fiyatlarını saklayan bir `double[]` dizisi üzerinde finansal göstergeleri hesaplayınız:
1. `minFiyat` ve `maxFiyat` (Dip ve Zirve değerleri ve oluştukları gün numaraları),
2. `aritmetikOrtalama` (30 günlük fiyat ortalaması),
3. `medyanFiyat` (Aşırı spekülatif günlerden arındırılmış ortanca fiyat),
4. **Maksimum Kazanç Potansiyeli:** Hangi gün alınıp hangi gün satılsaydı maksimum kâr elde edilirdi? ($O(N)$ zaman taraması ile `enBuyukFark = satisFiyati - alisFiyati`).

---

### 📋 Ödev 2: Sınıf Çan Eğrisi ve Başarı İndeksi Hesaplayıcı
**Dosya Adı:** `CanEgrisiHesaplayici.java`

**Problem Tanımı:**  
Bir üniversite dersinin 50 kişilik sınav sonuçları için:
1. Sınıf ortalamasını (`mean`) ve ortancasını (`median`) bulunuz.
2. Eğer `mean < median` ise ekrana: *"Sınıf genel olarak başarılı, düşük not alan az sayıda öğrenci ortalamayı aşağı çekmiştir."* yorumunu basınız.
3. Standart sapmayı formülle hesaplayınız.
4. **Çan Eğrisi Notlandırması:**
   - Notu $\ge \text{Ortalama} + 1.5 \times \text{Standart Sapma}$ olanlara: **AA**
   - Notu $\ge \text{Ortalama}$ olanlara: **BB**
   - Notu $< \text{Ortalama} - 1.5 \times \text{Standart Sapma}$ olanlara: **FF** harf notunu atayıp listeleyiniz.

---

### 📋 Ödev 3: Mükerrer Elemanları Ayıklama ve Benzersiz Dizi Üretme
**Dosya Adı:** `BenzersizDiziUretici.java`

**Problem Tanımı:**  
İçerisinde tekrar eden sayıların bulunduğu bir tamsayı dizisi için (Örn: `{4, 2, 4, 7, 2, 9, 7, 1}`):
1. Tekrar eden elemanları ayıklayarak her sayının sadece 1 kez geçtiği **yeni bir dizi** (`{4, 2, 7, 9, 1}`) üreten `public static int[] benzersizYap(int[] dizi)` metodunu kodlayınız.
2. Elde edilen yeni benzersiz diziyi küçükten büyüğe sıralayınız.
3. Giriş ve çıkış dizilerini ekranda kıyaslayınız.

---

### 📋 Ödev 4: Kendi `BinarySearch` Metodunu Yazma ve Performans Testi
**Dosya Adı:** `OzelBinarySearch.java`

**Problem Tanımı:**  
1. `public static int ozelBinarySearch(int[] siraliDizi, int hedef)` metodunu döngü yardımıyla yazınız. Metot kaçıncı karşılaştırmada (adımda) sayıyı bulduğunu konsola adım adım yazdırsın (`"1. Adım: orta=...", "2. Adım: orta=..."`).
2. 1 ile 1.000.000 arasındaki sıralı sayılardan oluşan bir dizide rastgele bir sayıyı:
   - Önce `linearSearch` ile aratıp kaç adım sürdüğünü ekrana basınız,
   - Sonra `ozelBinarySearch` ile aratıp kaç adım sürdüğünü ekrana basarak logaritmik hız farkını kanıtlayınız.
