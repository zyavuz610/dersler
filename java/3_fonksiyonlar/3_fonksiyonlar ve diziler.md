# 3. Java'da Fonksiyonlar ve Diziler (Arrays & Methods) Kılavuzu

Bu rehber; Java programlama dilinde birden fazla veriyi saklayan **Diziler (Arrays)** ile bu verileri işleyen **Fonksiyonların (Metotların)** birlikte kullanımını, dizilerin metotlara parametre olarak aktarılmasını, **Java'nın en kritik konusu olan Referans ile Aktarım (Pass-by-Reference Davranışı)** mekanizmasını, **metotlardan dizi geri döndürmeyi (`return array`)**, değişken sayıda argüman almayı (**Varargs**) ve öğrencilerin pekiştirmesi için pratik alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 2_fonksiyonlar**  
> İlgili Önceki Dokümanlar:  
> • [1_fonksiyon-giriş.md](./1_fonksiyon-giriş.md)  
> • [2_fonksiyon return.md](./2_fonksiyon%20return.md)

---

## 📌 İçindekiler
1. [Fonksiyonlar ve Dizilerin Güç Birliği](#1-fonksiyonlar-ve-dizilerin-güç-birliği)
2. [Dizilerin Metotlara Parametre Olarak Gönderilmesi](#2-dizilerin-metotlara-parametre-olarak-gönderilmesi)
3. [Hayati Kavram: Referans ile Aktarım (Pass-by-Reference Davranışı)](#3-hayati-kavram-referans-ile-aktarım-pass-by-reference-davranışı)
4. [Metotlardan Dizi Geri Döndürme (`return array`)](#4-metotlardan-dizi-geri-döndürme-return-array)
5. [Diziyi Yerinde Değiştiren (*Mutator*) vs Yeni Dizi Üreten Metotlar](#5-diziyi-yerinde-değiştiren-mutator-vs-yeni-dizi-üreten-metotlar)
6. [Çok Boyutlu Dizilerin (Matrislerin) Metotlara Gönderilmesi](#6-çok-boyutlu-dizilerin-matrislerin-metotlara-gönderilmesi)
7. [Değişken Sayıda Argüman: Varargs (`...`)](#7-değişken-sayıda-argüman-varargs-)
8. [En Sık Yapılan Hatalar ve Tuzaklar](#8-en-sık-yapılan-hatalar-ve-tuzaklar)
9. [Kapsamlı Örnek Konsol Uygulaması: `DiziVeriAnalizLaboratuvari`](#9-kapsamlı-örnek-konsol-uygulaması-diziverianalizlaboratuvari)
10. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#10-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
11. [Ödev Projeleri](#11-ödev-projeleri)

---

## 1. Fonksiyonlar ve Dizilerin Güç Birliği

Büyük veri setleriyle çalışırken verileri tek tek değişkenlerde (`sayi1`, `sayi2`, `sayi3`...) tutmak imkansızdır. Benzer verileri tek bir çatı altında toplamak için **Diziler (Arrays)** kullanılır.

Ancak bir diziyi ekrana yazdırmak, en büyük elemanını bulmak, elemanlarını sıralamak veya ortalamasını almak gibi işlemler onlarca satır döngü gerektirir. Eğer bir projede 10 farklı dizi varsa, aynı döngüleri 10 kez kopyalamak yerine:
- Dizi işleme mantığını **bağımsız bir metoda** yazarız.
- Diziyi o metoda **parametre** olarak göndeririz.
- Metot işlemi tamamlayıp sonucu geriye döndürür.

---

## 2. Dizilerin Metotlara Parametre Olarak Gönderilmesi

Bir metot parametre olarak ilkel tipler (`int`, `double` vb.) alabildiği gibi, herhangi bir türde dizi de alabilir.

### 📌 Metot Tanımlama ve Çağırma Sözdizimi:

```java
// Metot Tanımı: Parametre olarak 'int[]' (tamsayı dizisi) bekler:
public static void diziYazdir(int[] dizi) {
    for (int eleman : dizi) {
        System.out.print(eleman + " ");
    }
    System.out.println();
}

public static void main(String[] args) {
    int[] sayilar = {10, 20, 30, 40, 50};

    // Metot Çağrısı: Sadece dizinin adı yazılır! Köşeli parantez konulmaz!
    diziYazdir(sayilar); 
}
```

---

### 💻 Temel Dizi Analiz Metotları:

```java
public class DiziTemelFonksiyonlar {

    // 1. Dizideki elemanların toplamını hesaplayan metot:
    public static int diziToplami(int[] dizi) {
        int toplam = 0;
        for (int sayi : dizi) {
            toplam += sayi;
        }
        return toplam;
    }

    // 2. Dizideki en büyük elemanı bulan metot:
    public static int enBuyukBul(int[] dizi) {
        if (dizi == null || dizi.length == 0) {
            throw new IllegalArgumentException("Dizi boş olamaz!");
        }
        int enBuyuk = dizi[0];
        for (int i = 1; i < dizi.length; i++) {
            if (dizi[i] > enBuyuk) {
                enBuyuk = dizi[i];
            }
        }
        return enBuyuk;
    }

    public static void main(String[] args) {
        int[] notlar = {75, 90, 60, 100, 85};

        System.out.println("Notlar Toplamı: " + diziToplami(notlar));
        System.out.println("En Yüksek Not : " + enBuyukBul(notlar));
    }
}
```

---

## 3. Hayati Kavram: Referans ile Aktarım (Pass-by-Reference Davranışı)

Önceki derste gördüğümüz gibi; bir metoda ilkel bir değişken (`int sayi = 5;`) gönderildiğinde Java bu değişkenin bir kopyasını çıkarır ve metot içindeki değişiklikler `main`'deki orijinali etkilemezdi (*Pass-by-Value*).

> 🚨 **DİZİLERDE DURUM TAMAMEN FARKLIDIR!**  
> Java'da diziler birer **Nesnedir (Object)** ve belleğin **Heap** bölgesinde saklanırlar.  
> `main` metodundaki dizi değişkeni ise **Stack** bölgesinde yaşar ve Heap'teki bu dizinin **bellek adresini (referansını)** tutar.

Bir diziyi metoda parametre olarak gönderdiğinizde, dizinin kopyası çıkarılmaz! **Dizinin bellekteki referans adresi metoda kopyalanır.**

### 💥 Bu Ne Anlama Gelir?
Metodun içinde dizinin herhangi bir indeksindeki elemanı değiştirirseniz (`dizi[0] = 999;`), metodun eriştiği bellek alanı `main`'deki dizi ile **birebir aynı yer olduğu için**, **`main` METODUNDAKİ ORİJİNAL DİZİ DE KALICI OLARAK DEĞİŞİR!**

```text
[ STACK BELLEK ]                             [ HEAP BELLEK ]
main: sayilar ------------+
                          |---------> [ 10 | 20 | 30 ] (Aynı bellek adresi!)
metot: dizi  -------------+
```

### 💻 Kanıt Kodu:

```java
public class DiziReferansIspati {

    public static void elemaniDegistir(int[] dizi) {
        dizi[0] = 999; // Metot içinde 0. indeksi değiştiriyoruz!
        System.out.println("Metodun içinde dizi[0]: " + dizi[0]); // 999
    }

    public static void main(String[] args) {
        int[] sayilar = {10, 20, 30};

        System.out.println("Metot çağrılmadan önce sayilar[0]: " + sayilar[0]); // 10

        elemaniDegistir(sayilar); // Referans gönderildi!

        System.out.println("Metot çağrıldıktan sonra sayilar[0]: " + sayilar[0]); // 999! (DEĞİŞTİ!)
    }
}
```

**Ekran Çıktısı:**
```text
Metot çağrılmadan önce sayilar[0]: 10
Metodun içinde dizi[0]: 999
Metot çağrıldıktan sonra sayilar[0]: 999
```

---

### 🔍 İnce Ayrıntı: Metot İçinde `new` ile Yeni Dizi Atanırsa?

Eğer metot içinde dizinin bir elemanını değiştirmek yerine dizi değişkenine `new int[...]` ile yepyeni bir dizi bağlarsanız, sadece metodun kendi yerel referansı kopar; `main`'deki dizi asla değişmez:

```java
public static void yeniDiziAta(int[] dizi) {
    dizi = new int[]{100, 200, 300}; // Yerel referans yeni bir diziye bağlandı
}

public static void main(String[] args) {
    int[] sayilar = {1, 2, 3};
    yeniDiziAta(sayilar);
    System.out.println(sayilar[0]); // HALA 1'dir! Çünkü orijinal referans bağını korur.
}
```

---

## 4. Metotlardan Dizi Geri Döndürme (`return array`)

Bir metot sadece tek bir sayı veya metin değil, bir dizi dolusu veriyi de geriye döndürebilir. Bunun için metodun dönüş tipine `int[]`, `String[]` veya `double[]` yazılır.

### 💻 Senaryo 1: Dizi Fabrikası (Rastgele Sayılarla Dizi Üreten Metot)

```java
public class DiziFabrikasi {

    // Belirtilen boyutta ve aralıkta rastgele sayılardan oluşan yeni bir dizi üreten metot:
    public static int[] rastgeleDiziUret(int elemanSayisi, int min, int max) {
        int[] yeniDizi = new int[elemanSayisi];

        for (int i = 0; i < elemanSayisi; i++) {
            // min ile max arasında rastgele sayı üretimi:
            yeniDizi[i] = (int)(Math.random() * (max - min + 1)) + min;
        }

        return yeniDizi; // Üretilen dizinin referansını geri döndür
    }

    public static void main(String[] args) {
        // Metot 10 elemanlı taze bir dizi üreterek bize teslim eder:
        int[] sansliSayilar = rastgeleDiziUret(6, 1, 49);

        System.out.print("Üretilen Şanslı Sayılar Dizisi: ");
        for (int sayi : sansliSayilar) {
            System.out.print(sayi + " ");
        }
        System.out.println();
    }
}
```

---

### 💻 Senaryo 2: Filtreleme Metodu (Çift Sayıları Ayıklayıp Yeni Dizi Döndürme)

```java
public class DiziFiltreleme {

    public static int[] ciftSayilariAyikla(int[] kaynakDizi) {
        // 1. Aşama: Kaç tane çift sayı olduğunu sayalım (yeni dizinin boyutunu belirlemek için)
        int ciftAdet = 0;
        for (int sayi : kaynakDizi) {
            if (sayi % 2 == 0) {
                ciftAdet++;
            }
        }

        // 2. Aşama: Tam o boyutta yeni bir sonuç dizisi oluşturalım
        int[] ciftler = new int[ciftAdet];
        int indeks = 0;

        for (int sayi : kaynakDizi) {
            if (sayi % 2 == 0) {
                ciftler[indeks] = sayi;
                indeks++;
            }
        }

        return ciftler; // Sadece çiftleri içeren yeni dizi döner
    }
}
```

---

## 5. Diziyi Yerinde Değiştiren (*Mutator*) vs Yeni Dizi Üreten Metotlar

Bir dizi üzerinde dönüşüm yaparken iki farklı yazılım yaklaşımı vardır:

| Yaklaşım | Bellek Kullanımı | Orijinal Dizi Durumu | Tipik Kullanım |
| :--- | :--- | :--- | :--- |
| **Yerinde Değiştiren (*In-Place / Mutator*)** | Yeni bellek açmaz, çok hızlıdır. | Orijinal dizi kalıcı olarak **değişir**. | Büyük dizileri sıralama (`sort`), tersine çevirme. |
| **Saf / Kopyalayan (*Pure Function*)** | Yeni dizi oluşturduğu için ek Heap bellek tüketir. | Orijinal diziye **asla dokunulmaz**, güvendedir. | Filtreleme, veri güvenliğinin şart olduğu durumlar. |

### 💻 İki Yaklaşımın Karşılaştırması: Bir Diziyi Tersine Çevirme

```java
public class DiziTersCevirici {

    // 1. YAKLAŞIM: Orijinal diziyi bozan / yerinde tersine çeviren metot:
    public static void tersCevirYerinde(int[] dizi) {
        int sol = 0;
        int sag = dizi.length - 1;

        while (sol < sag) {
            int gecici = dizi[sol];
            dizi[sol] = dizi[sag];
            dizi[sag] = gecici;
            sol++;
            sag--;
        }
    }

    // 2. YAKLAŞIM: Orijinale dokunmayan, ters çevrilmiş YENİ bir dizi döndüren metot:
    public static int[] tersCevirYeniDizi(int[] dizi) {
        int[] sonuc = new int[dizi.length];
        for (int i = 0; i < dizi.length; i++) {
            sonuc[i] = dizi[dizi.length - 1 - i];
        }
        return sonuc;
    }
}
```

---

## 6. Çok Boyutlu Dizilerin (Matrislerin) Metotlara Gönderilmesi

İki boyutlu diziler (satır-sütun matrisleri) parametre olarak `int[][]` sözdizimiyle aktarılır:

```java
public class MatrisIslemleri {

    // 2 Boyutlu bir matrisi konsola tablo şeklinde basan metot:
    public static void matrisYazdir(int[][] matris) {
        for (int i = 0; i < matris.length; i++) {
            for (int j = 0; j < matris[i].length; j++) {
                System.out.print(matris[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // Matrisin köşegen (Diagonal) elemanlarının toplamını döndüren metot:
    public static int kosegenToplami(int[][] matris) {
        int toplam = 0;
        for (int i = 0; i < matris.length; i++) {
            toplam += matris[i][i];
        }
        return toplam;
    }

    public static void main(String[] args) {
        int[][] tablo = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        System.out.println("Matris Tablosu:");
        matrisYazdir(tablo);
        System.out.println("Köşegen Toplamı (1+5+9): " + kosegenToplami(tablo));
    }
}
```

---

## 7. Değişken Sayıda Argüman: Varargs (`...`)

Metodun kaç tane argüman alacağını önceden bilmediğimiz durumlarda (örneğin: 2 sayıyı da, 5 sayıyı da, 100 sayıyı da toplayabilen tek bir metot) Java 5 ile gelen **Varargs (Variable Arguments)** özelliği kullanılır.

### 📌 Sözdizimi: `Tür... parametreAdi`

```java
public class VarargsOrnek {

    // 'int... sayilar' ifadesi, Java tarafından arka planda 'int[] sayilar' gibi yönetilir:
    public static int dinamikTopla(int... sayilar) {
        int toplam = 0;
        for (int s : sayilar) {
            toplam += s;
        }
        return toplam;
    }

    public static void main(String[] args) {
        // İster 2 parametre verin, ister 4, ister doğrudan dizi gönderin:
        System.out.println("Toplam 1: " + dinamikTopla(5, 10));             // 15
        System.out.println("Toplam 2: " + dinamikTopla(1, 2, 3, 4, 5));     // 15
        System.out.println("Toplam 3: " + dinamikTopla(100));               // 100
        System.out.println("Toplam 4: " + dinamikTopla());                  // 0 (Boş)
    }
}
```

### ⚠️ Varargs Kullanımının 2 Katı Kuralı:
1. Bir metotta **en fazla 1 adet** varargs parametresi olabilir.
2. Varargs parametresi parametre listesinin **EN SONUNDA** yer almak zorundadır!  
   - `public static void test(String baslik, int... sayilar)` ➡️ ✅ DOĞRU
   - `public static void test(int... sayilar, String baslik)` ➡️ ❌ DERLEME HATASI!

---

## 8. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: Metot Çağrılırken Köşeli Parantez Kullanmak

```java
int[] puanlar = {80, 90, 70};

// ❌ DERLEME HATASI:
// ortalamaHesapla(puanlar[]); 
// ortalamaHesapla(int[] puanlar);

// ✅ DOĞRU ÇAĞRI:
double ort = ortalamaHesapla(puanlar);
```

---

### 2. Tuzak: İstenmeyen Yan Etki (*Unintended Side Effect*)
Dizilerin referansla aktarıldığını unutup metodun içinde orijinal diziyi bozmak büyük yazılım hatalarına sebep olur:

```java
// Sadece ekrana basması beklenen metot diziyi kalıcı olarak sıfırlıyor!
public static void yazdirVeSifirla(int[] dizi) {
    for (int i = 0; i < dizi.length; i++) {
        System.out.println(dizi[i]);
        dizi[i] = 0; // ❌ TEHLİKE: main'deki dizi de sıfırlandı!
    }
}
```

---

### 3. Tuzak: `NullPointerException` (Boş Dizi Kontrolü İhmali)
Metoda `null` referanslı bir dizi gönderilirse `dizi.length` çağrıldığı anda program çöker:

```java
public static double ortalama(int[] dizi) {
    // ✅ GÜVENLİK KONTROLÜ:
    if (dizi == null || dizi.length == 0) {
        return 0.0;
    }
    // ...
}
```

---

## 9. Kapsamlı Örnek Konsol Uygulaması: `DiziVeriAnalizLaboratuvari`

Aşağıdaki program; dizi üreten, analiz eden, filtreleyen, sıralayan ve dizi döndüren profesyonel bir veri işleme motorudur:

```java
import java.util.Scanner;

public class DiziVeriAnalizLaboratuvari {

    // 1. Diziyi Ekrana Düzenli Basan Metot (void)
    public static void diziYazdir(String etiket, int[] dizi) {
        System.out.print(etiket + ": [ ");
        for (int i = 0; i < dizi.length; i++) {
            System.out.print(dizi[i] + (i == dizi.length - 1 ? "" : ", "));
        }
        System.out.println(" ]");
    }

    // 2. Rastgele Sayılardan Yeni Dizi Üreten Metot (return int[])
    public static int[] rastgeleDiziOlustur(int elemanSayisi, int min, int max) {
        int[] yeni = new int[elemanSayisi];
        for (int i = 0; i < elemanSayisi; i++) {
            yeni[i] = (int)(Math.random() * (max - min + 1)) + min;
        }
        return yeni;
    }

    // 3. Aritmetik Ortalama Hesaplayan Metot (return double)
    public static double ortalamaHesapla(int[] dizi) {
        if (dizi == null || dizi.length == 0) return 0.0;
        int toplam = 0;
        for (int sayi : dizi) toplam += sayi;
        return (double) toplam / dizi.length;
    }

    // 4. Dizideki Çift Sayıları Ayıklayıp Yeni Dizi Döndüren Metot (return int[])
    public static int[] ciftleriAyikla(int[] dizi) {
        int adet = 0;
        for (int s : dizi) if (s % 2 == 0) adet++;

        int[] ciftler = new int[adet];
        int k = 0;
        for (int s : dizi) {
            if (s % 2 == 0) {
                ciftler[k++] = s;
            }
        }
        return ciftler;
    }

    // 5. Diziyi Küçükten Büyüğe Sıralayan Metot (In-place Bubble Sort)
    public static void diziSirala(int[] dizi) {
        for (int i = 0; i < dizi.length - 1; i++) {
            for (int j = 0; j < dizi.length - 1 - i; j++) {
                if (dizi[j] > dizi[j + 1]) {
                    int gecici = dizi[j];
                    dizi[j] = dizi[j + 1];
                    dizi[j + 1] = gecici;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   KTÜ DİZİ VE VERİ ANALİZ LABORATUVARI   ");
        System.out.println("==========================================");

        // 1. Taze Dizi Üretimi:
        int[] anaVeri = rastgeleDiziOlustur(8, 10, 99);
        diziYazdir("Oluşturulan Ham Veri Seti", anaVeri);

        // 2. İstatistiksel Hesaplamalar:
        double ortalama = ortalamaHesapla(anaVeri);
        System.out.printf("Dizinin Aritmetik Ortalaması : %.2f\n", ortalama);

        // 3. Filtreleme (Yeni dizi döner):
        int[] ciftSayilar = ciftleriAyikla(anaVeri);
        diziYazdir("Ayıklanan Çift Sayılar     ", ciftSayilar);

        // 4. Referansla Sıralama (Orijinal dizi değişir):
        System.out.println("\n-> Dizi küçükten büyüğe yerinde sıralanıyor...");
        diziSirala(anaVeri);
        diziYazdir("Sıralanmış Orijinal Dizi   ", anaVeri);

        System.out.println("==========================================");
    }
}
```

---

## 10. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Referans Takibi)
Aşağıdaki programın konsol çıktısı ne olur? Adım adım takip ediniz:

```java
public class DiziIzleme {
    public static void gizemliIslem(int[] d, int x) {
        d[0] = d[0] + 10;
        x = x + 10;
    }

    public static void main(String[] args) {
        int[] sayilar = {5, 20};
        int deger = 50;

        gizemliIslem(sayilar, deger);

        System.out.println("sayilar[0]: " + sayilar[0] + ", deger: " + deger);
    }
}
```
*İpucu: `sayilar` bir dizi, `deger` ise ilkel bir tamsayıdır.*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında yer alan **2 derleme hatasını** ve **1 mantık hatasını** tespit ediniz:

```java
public class Hatalar {
    public static void main(String[] args) {
        int[] liste = {1, 2, 3, 4};
        yazdir(liste[]); // Hata 1 nerede?
    }

    public static void yazdir(int[] dizi, String... baslik, int kod) { // Hata 2 nerede?
        for (int i = 0; i <= dizi.length; i++) { // Hata 3 nerede?
            System.out.println(dizi[i]);
        }
    }
}
```

---

### 🔢 Alıştırma 3: Dizinin Elemanlarını 2 Katına Çıkaran Metot
Kendisine parametre olarak verilen bir `int[]` dizisinin her bir elemanını yerinde **2 ile çarparak güncelleyen** `public static void ikiyeKatla(int[] dizi)` metodunu yazınız ve `main` içinde test ediniz.

---

### 🔍 Alıştırma 4: Dizide Eleman Arama (*Linear Search*)
Bir `int[]` dizisi ve bir `int aranan` değeri alan metot; aranan değer dizide varsa kaç adet bulunduğunu (`int`), yoksa `0` döndüren `public static int frekansBul(int[] dizi, int aranan)` metodunu kodlayınız.

---

## 11. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Dizilerin referans özelliklerine ve metot yapısına dikkat ediniz.

---

### 📋 Ödev 1: İki Diziyi Birleştiren ve Sıralayan Motor
**Dosya Adı:** `DiziBirlestirici.java`

**Problem Tanımı:**  
1. `public static int[] diziBirlestir(int[] a, int[] b)`:
   - Parametre olarak iki farklı tamsayı dizisi alsın.
   - Bu iki dizinin elemanlarını birleştirip boyutu `(a.length + b.length)` olan **yeni bir dizi** olarak döndürsün.
2. `public static void diziSirala(int[] dizi)`:
   - Birleşen diziyi küçükten büyüğe sıralasın.
3. `main` içinde farklı boyutlardaki iki diziyi birleştirip sıralanmış halini ekrana yazdırınız.

---

### 📋 Ödev 2: Matris Transpozu (Devriği) Hesaplayıcı
**Dosya Adı:** `MatrisTranspoz.java`

**Problem Tanımı:**  
Matematikte bir matrisin satırları sütun, sütunları satır yapılarak elde edilen yeni matrise **Transpoz** denir.
1. `public static int[][] transpozAl(int[][] matris)`:
   - `M x N` boyutunda bir matris alıp, `N x M` boyutunda yeni transpoz matrisini üretip geriye döndürsün.
2. `public static void matrisYazdir(int[][] matris)`:
   - Matrisi ekrana biçimli bassın.
3. `main` içinde 2x3 boyutundaki bir matrisin transpozunu alarak ekranda kıyaslayınız.

---

### 📋 Ödev 3: Diziden Eleman Silme ve Kaydırma Algoritması
**Dosya Adı:** `DizidenSilme.java`

**Problem Tanımı:**  
Diziler sabit boyutlu olduğu için bir eleman silindiğinde yeni bir dizi oluşturmak gerekir:
1. `public static int[] elemanSil(int[] dizi, int silinecekIndeks)`:
   - Eğer indeks geçersizse (`< 0` veya `>= dizi.length`), diziyi değiştirmeden kopyasını döndürsün.
   - İndeks geçerliyse, o indeksteki elemanı çıkartıp boyutu 1 eksilmiş yeni bir dizi döndürsün.
2. `main` içinde kullanıcıdan silinecek indeksi alıp sonucu ekrana basınız.

---

### 📋 Ödev 4: Sınıf Not İstatistik ve Standart Sapma Hesaplayıcı
**Dosya Adı:** `StandartSapmaHesaplayici.java`

**Problem Tanımı:**  
Öğrencilerin sınav notlarından oluşan bir `double[]` dizisi için:
1. `public static double ortalama(double[] notlar)`: Aritmetik ortalamayı döndürsün.
2. `public static double standartSapma(double[] notlar)`:
   - Formül: Her elemanın ortalamadan farkının kareleri toplanır, `(N - 1)`'e bölünür ve karekökü (`Math.sqrt()`) alınır.
3. `public static int[] harfNotuDagilimi(double[] notlar)`:
   - Sınıftaki notları harf aralıklarına göre sayıp 5 elemanlı bir dizi döndürsün (`[AA, BA, BB, CB, FF]`).
