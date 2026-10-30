# 3. Java'da Döngüler (Loops): `for` Döngüsü Kılavuzu

Bu rehber; bilgisayar programlamanın en temel yapı taşlarından biri olan **tekrarlı ifadeleri (döngüler / loops)**, kod tekrarını önleme mantığını (DRY ilkesi), Java'daki döngü türlerini, **`for` döngüsünün** tüm anatomisini, iç içe (*nested*) döngüleri, akış kontrol ifadelerini (`break`, `continue`, `labeled break`), dizilerde kullanılan **for-each** yapısını, sık düşülen tuzakları ve öğrencilerin çözebileceği kapsamlı alıştırmalar ile ödev projelerini içermektedir.

---

> 📖 **Modül: 1_koşul ve döngü**  
> İlgili Önceki Dokümanlar:  
> • [1_if-else.md](./1_if-else.md)  
> • [2_switch-case.md](./2_switch-case.md)

---

## 📌 İçindekiler
1. [Döngü (Loop / İterasyon) Kavramı Nedir?](#1-döngü-loop--iterasyon-kavramı-nedir)
2. [Java'da Döngü Çeşitlerine Genel Bakış](#2-javada-döngü-çeşitlerine-genel-bakış)
3. [`for` Döngüsünün Anatomisi ve Çalışma Adımları](#3-for-döngüsünün-anatomisi-ve-çalışma-adımları)
4. [Temel `for` Döngüsü Senaryoları](#4-temel-for-döngüsü-senaryoları)
5. [Döngü Değişkeninin Kapsamı (Scope) ve Çoklu Değişken Tanımlama](#5-döngü-değişkeninin-kapsamı-scope-ve-çoklu-değişken-tanımlama)
6. [Sonsuz Döngü (Infinite Loop) Kavramı](#6-sonsuz-döngü-infinite-loop-kavramı)
7. [İç İçe (Nested) `for` Döngüleri ve Matris Mantığı](#7-iç-içe-nested-for-döngüleri-ve-matris-mantığı)
8. [Döngü Kontrol İfadeleri: `break` ve `continue`](#8-döngü-kontrol-ifadeleri-break-ve-continue)
9. [Etiketli (Labeled) Döngüler](#9-etiketli-labeled-döngüler)
10. [Gelişmiş Döngü: `for-each` (Enhanced For Loop)](#10-gelişmiş-döngü-for-each-enhanced-for-loop)
11. [En Sık Yapılan Hatalar ve Tuzaklar](#11-en-sık-yapılan-hatalar-ve-tuzaklar)
12. [Kapsamlı Örnek Konsol Uygulaması: `AsalSayiVeIstatistikMotoru`](#12-kapsamlı-örnek-konsol-uygulaması-asalsayiveistatistikmotoru)
13. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#13-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
14. [Ödev Projeleri](#14-ödev-projeleri)

---

## 1. Döngü (Loop / İterasyon) Kavramı Nedir?

Bilgisayarların insanlara kıyasla en güçlü olduğu alan, **aynı veya benzer işlemleri bıkmadan, yorulmadan ve sıfır hata payıyla milyonlarca kez tekrarlayabilmeleridir**.

Ekrana 5 kez *"Merhaba Java!"* yazdırmak istediğimizde bunu art arda 5 satır yazarak çözebiliriz:
```java
System.out.println("Merhaba Java!");
System.out.println("Merhaba Java!");
System.out.println("Merhaba Java!");
System.out.println("Merhaba Java!");
System.out.println("Merhaba Java!");
```

Peki ya bu mesajı **10.000 kez** yazdırmamız gerekirse? Veya veri tabanındaki 50.000 öğrencinin not ortalamasını hesaplamamız icap ettiğinde ne yapacağız? Kodları tek tek kopyalayıp yapıştırmak imkansızdır. Bu durum yazılım dünyasının temel taşlarından olan **DRY (Don't Repeat Yourself - Kendini Tekrar Etme)** ilkesine tamamen aykırıdır.

İşte belirli bir kod bloğunu, **belirli bir koşul sağlandığı sürece** tekrar tekrar çalıştıran mekanizmalara **Döngü (Loop)**, her bir tekrar turuna ise **İterasyon (Iteration)** denir.

---

## 2. Java'da Döngü Çeşitlerine Genel Bakış

Java dilinde tekrarlı işlemleri yönetmek için 4 farklı döngü mekanizması bulunur:

| Döngü Türü | Ne Zaman Tercih Edilir? | Tipik Kullanım Alanı |
| :--- | :--- | :--- |
| **`for`** | Tekrar sayısının **önceden bilindiği veya hesaplanabildiği** durumlarda. | 1'den 100'e kadar sayma, belirli sayıda işlem yapma, matris gezme. |
| **`while`** | Tekrar sayısının değil, **bir koşulun geçerliliğinin** önemli olduğu durumlarda. | Kullanıcı doğru şifreyi girene kadar sorma, dosya sonuna gelene kadar okuma. |
| **`do-while`** | Koşul ne olursa olsun kodun **en az bir kez kesinlikle çalışması** gereken durumlarda. | Konsol menüsü gösterme, ilk veri girişini alma. |
| **`for-each`** | Bir dizi (*array*) veya koleksiyonun tüm elemanlarını **baştan sona indekslerle uğraşmadan** gezmek için. | Listedeki tüm öğrencilerin isimlerini listeleme. |

---

## 3. `for` Döngüsünün Anatomisi ve Çalışma Adımları

`for` döngüsü; bir sayacı başlatan, her adımda koşulu denetleyen ve sayacı güncelleyen üç ana bileşeni tek bir satırda derli toplu sunar.

### 📌 Genel Sözdizimi:

```java
for (başlatma ; koşul ; güncelleme) {
    // Koşul 'true' olduğu sürece tekrarlanacak kod bloğu
}
```

### 🔍 3 Temel Bileşenin Görevleri:
1. **Başlatma (Initialization):** Döngü sayacının tanımlandığı ve ilk değerinin atandığı kısımdır (`int i = 0;`). Döngü ömrü boyunca **sadece bir kez**, döngüye ilk girilirken çalıştırılır.
2. **Koşul (Condition):** Her iterasyondan önce test edilen `boolean` ifadedir (`i < 10;`). Koşul `true` olduğu sürece gövdedeki kodlar çalıştırılır; `false` olduğu anda döngü sonlanır.
3. **Güncelleme (Update):** Döngü bloğu içerisindeki kodlar çalışıp bittikten sonra sayacın artırıldığı veya azaltıldığı kısımdır (`i++`, `i += 2`, `i--`).

---

### ⏱️ Adım Adım Yürütme Sırası:

```java
for (int i = 1; i <= 3; i++) {
    System.out.println("Tur: " + i);
}
```

Aşağıdaki tablo döngünün bellekteki adım adım kronolojisini gösterir:

| Adım | Yapılan İşlem | Durum / Değer | Sonuç |
| :---: | :--- | :--- | :--- |
| **1** | Başlatma çalışır | `i = 1` | Sayaç bellekte oluşturuldu (bir daha asla çalışmaz). |
| **2** | Koşul kontrol edilir | `1 <= 3` | **`true`** ➡️ Döngü gövdesine girilir. |
| **3** | Gövde çalışır | `System.out.println` | Ekrana `"Tur: 1"` basılır. |
| **4** | Güncelleme çalışır | `i++` | `i` değeri 2 olur. |
| **5** | Koşul kontrol edilir | `2 <= 3` | **`true`** ➡️ Gövdeye girilir. |
| **6** | Gövde çalışır | `System.out.println` | Ekrana `"Tur: 2"` basılır. |
| **7** | Güncelleme çalışır | `i++` | `i` değeri 3 olur. |
| **8** | Koşul kontrol edilir | `3 <= 3` | **`true`** ➡️ Gövdeye girilir. |
| **9** | Gövde çalışır | `System.out.println` | Ekrana `"Tur: 3"` basılır. |
| **10**| Güncelleme çalışır | `i++` | `i` değeri 4 olur. |
| **11**| Koşul kontrol edilir | `4 <= 3` | **`false`** ❌ ➡️ Döngü sonlanır! |

---

## 4. Temel `for` Döngüsü Senaryoları

### 💻 Senaryo 1: Geriye Doğru Sayım (Azalan Sayaç)
Sayaç sadece artmak zorunda değildir, eksiltilerek geriye doğru da saydırılabilir:

```java
public class GeriSayim {
    public static void main(String[] args) {
        System.out.println("Roket fırlatılışına son:");

        for (int i = 5; i >= 1; i--) {
            System.out.println(i + " saniye...");
        }

        System.out.println("🚀 ATEŞLE! Fırlatma gerçekleşti.");
    }
}
```

---

### 💻 Senaryo 2: Belirli Adımlarla Artırma (Çift Sayıları Bulma)
Sayacı her defasında 1 artırmak yerine `+= 2`, `+= 5` veya `*= 2` gibi farklı adımlarla güncelleyebiliriz:

```java
public class CiftSayilar {
    public static void main(String[] args) {
        System.out.println("0 ile 20 arasındaki çift sayılar:");

        for (int i = 0; i <= 20; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println(); // Alt satıra geçiş
    }
}
```

---

### 💻 Senaryo 3: Birikimli Toplam ve Çarpım (Akümülatör Mantığı)
Döngüler, seri toplamları ve faktöriyel gibi matematiksel çarpımları hesaplamak için idealdir:

```java
public class FaktoriyelVeToplam {
    public static void main(String[] args) {
        int n = 5;
        long faktoriyel = 1;
        int toplam = 0;

        for (int i = 1; i <= n; i++) {
            toplam += i;       // toplam = toplam + i;
            faktoriyel *= i;   // faktoriyel = faktoriyel * i;
        }

        System.out.println("1'den " + n + "'e kadar sayıların toplamı : " + toplam);
        System.out.println(n + "! (Faktöriyel) değeri                 : " + faktoriyel);
    }
}
```

---

## 5. Döngü Değişkeninin Kapsamı (Scope) ve Çoklu Değişken Tanımlama

### 🔒 Döngü Değişkeninin Yaşam Alanı (Scope)
`for` parantezi içinde tanımlanan sayaç değişkeni (`int i = 0`), **yalnızca o döngünün gövdesinde yaşar**. Döngü bittiği anda bellekten silinir:

```java
for (int k = 0; k < 5; k++) {
    System.out.println(k);
}

// ❌ DERLEME HATASI! 'k' değişkeni döngü bittiği için burada yaşamaz:
// System.out.println("Döngü bittiğindeki k değeri: " + k);
```

Eğer sayacın ulaştığı son değere döngüden sonra da ihtiyaç duyuyorsanız, değişkeni **döngüden önce** tanımlamalısınız:

```java
int k; // Dışarıda tanımlandı
for (k = 0; k < 5; k++) {
    // İşlemler
}
System.out.println("Döngüden çıkan k değeri: " + k); // Sorunsuz çalışır (Çıktı: 5)
```

---

### 👥 Çoklu Değişken ve Güncelleme ile `for`
Java'da aynı türe sahip olmak şartıyla başlatma ve güncelleme kısmında **birden fazla değişken** virgülle ayrılarak kullanılabilir:

```java
public class CiftYonluSayac {
    public static void main(String[] args) {
        // 'i' 1'den başlar artar, 'j' 10'dan başlar azalır:
        for (int i = 1, j = 10; i <= 5 && j >= 6; i++, j--) {
            System.out.println("i: " + i + " <---> j: " + j);
        }
    }
}
```

---

## 6. Sonsuz Döngü (Infinite Loop) Kavramı

Bir döngünün sonlanma koşulu hiçbir zaman `false` olmuyorsa, program bu döngüden çıkamaz ve işlemciyi tüketmeye devam eder.

### 1. Klasik Boş `for` Sonsuz Döngüsü:
Java'da `for` ifadesinin 3 bileşeni de isteğe bağlıdır. Hiçbir parametre verilmezse koşul otomatikman `true` kabul edilir:

```java
// Sonsuza kadar çalışan for döngüsü:
for (;;) {
    System.out.println("Bu mesaj hiç durmadan yazdırılır!");
    // Ancak içeride bir 'break' ile manuel olarak çıkılabilir.
}
```

### 2. Mantık Hatası Nedeniyle Oluşan İstenmeyen Sonsuz Döngü:
```java
// ❌ HATA: Sayaç geriye sayması gerekirken artırılıyor!
// i değeri 10'dan başlayıp 11, 12, 13... diye sonsuza kadar büyür ve hep > 0 kalır!
for (int i = 10; i > 0; i++) {
    System.out.println(i);
}
```

---

## 7. İç İçe (Nested) `for` Döngüleri ve Matris Mantığı

Bir `for` döngüsünün gövdesi içine başka bir `for` döngüsü yerleştirilmesine **iç içe (nested) döngü** denir. İki boyutlu yapılar (satır-sütun matrisleri, piksel ızgaraları, koordinat düzlemleri ve geometrik desenler) için kullanılır.

- **Dış Döngü (Outer Loop):** Genellikle **Satırları** temsil eder.
- **İç Döngü (Inner Loop):** Genellikle **Sütunları** temsil eder. Dış döngünün **her 1 adımında**, iç döngü **baştan sona tüm turlarını** tamamlar!

```text
Dış Döngü i=1 olduğunda  -->  İç döngü j=1, j=2, j=3 turlarını bitirir.
Dış Döngü i=2 olduğunda  -->  İç döngü j=1, j=2, j=3 turlarını tekrar baştan bitirir.
```

---

### 💻 Örnek 4: Çarpım Tablosu (1 - 5 Arası)

```java
public class CarpimTablosu {
    public static void main(String[] args) {
        System.out.println("===== ÇARPIŞMA TABLOSU =====");

        for (int i = 1; i <= 5; i++) { // Satırlar
            for (int j = 1; j <= 5; j++) { // Sütunlar
                // '\t' tab boşluğu bırakarak düzgün hizalama sağlar
                System.out.print(i * j + "\t");
            }
            System.out.println(); // Satır tamamlanınca alt satıra geç
        }
    }
}
```

**Ekran Çıktısı:**
```text
1	2	3	4	5	
2	4	6	8	10	
3	6	9	12	15	
4	8	12	16	20	
5	10	15	20	25	
```

---

### 💻 Örnek 5: Yıldızlarla Dik Üçgen Deseni Çizme

```java
public class UcgenDeseni {
    public static void main(String[] args) {
        int satirSayisi = 5;

        for (int i = 1; i <= satirSayisi; i++) { // 1'den 5'e kadar satırlar
            for (int j = 1; j <= i; j++) {       // Her satırda satır numarası (i) kadar yıldız bas
                System.out.print("* ");
            }
            System.out.println(); // Satır sonu
        }
    }
}
```

**Ekran Çıktısı:**
```text
* 
* * 
* * * 
* * * * 
* * * * * 
```

---

## 8. Döngü Kontrol İfadeleri: `break` ve `continue`

Döngünün normal akışını değiştirmek için iki özel anahtar kelime kullanılır:

### 🛑 1. `break`: Döngüyü Tamamen Sonlandırır
Belirli bir koşul gerçekleştiğinde döngüyü derhal kırıp döngü gövdesinden tamamen dışarı fırlar.

```java
// 1 ile 100 arasında 7'ye bölünen İLK sayıyı bulup döngüyü bitirelim:
for (int i = 1; i <= 100; i++) {
    if (i % 7 == 0) {
        System.out.println("7'ye bölünen ilk sayı bulundu: " + i);
        break; // Kalan 93 adımı kontrol etmeden döngüyü ANINDA BİTİRİR!
    }
}
```

---

### ⏭️ 2. `continue`: O Anki Adımı Atlar, Sonraki Tura Geçer
Döngüyü tamamen bitirmez; yalnızca **içinde bulunduğu mevcut turun geri kalan satırlarını atlayarak** doğrudan güncelleme (`i++`) adımına sıçrar.

```java
// 1 ile 10 arasındaki TEK sayıları ekrana yazdıralım (Çiftleri atlayalım):
for (int i = 1; i <= 10; i++) {
    if (i % 2 == 0) {
        continue; // Çift ise alttaki println'i çalıştırmadan bir sonraki sayıya geç!
    }
    System.out.print(i + " "); // Sadece tek sayılar basılır
}
// Çıktı: 1 3 5 7 9
```

---

## 9. Etiketli (Labeled) Döngüler

İç içe birden çok döngü varken, içteki döngüde çağrılan bir `break` komutu **sadece içinde bulunduğu en içteki döngüyü kırar**. Dış döngü dönmeye devam eder.

Eğer içteki bir koşul sağlandığında **en dıştaki döngüyü de tamamen sonlandırmak** istiyorsak döngülere bir **Etiket (Label)** veririz:

```java
public class EtiketliDongu {
    public static void main(String[] args) {
        // Dış döngüye 'disDongu' etiketi atandı:
        disDongu:
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("Hedef koordinat (2,2) bulundu. Tüm döngüler sonlandırılıyor!");
                    break disDongu; // En dıştaki döngüyü kırar ve tamamen çıkar!
                }
                System.out.println("i: " + i + ", j: " + j);
            }
        }
        System.out.println("Arama bitti.");
    }
}
```

---

## 10. Gelişmiş Döngü: `for-each` (Enhanced For Loop)

Java 5 ile gelen `for-each` döngüsü, bir dizi (*array*) veya liste üzerindeki tüm elemanlara **indeks değişkeni (`i`) tanımlamaya gerek kalmadan** sırasıyla ulaşmamızı sağlar.

### 📌 Genel Sözdizimi:
```java
for (ElemanTipi degiskenAdi : DiziVeyaKoleksiyon) {
    // Her iterasyonda 'degiskenAdi' sıradaki elemanı tutar
}
```

### 💻 Karşılaştırma Örneği:

```java
public class ForEachKarsilastirma {
    public static void main(String[] args) {
        int[] notlar = {85, 92, 78, 64, 99};

        // 1. YOL: Klasik for döngüsü (İndeks ile erişim)
        int toplam1 = 0;
        for (int i = 0; i < notlar.length; i++) {
            toplam1 += notlar[i];
        }

        // 2. YOL: Modern for-each döngüsü (Çok daha temiz ve güvenli!)
        int toplam2 = 0;
        for (int not : notlar) {
            toplam2 += not;
        }

        System.out.println("Notlar Toplamı: " + toplam2);
    }
}
```

> ⚠️ **Ne Zaman Hangisi?**  
> - Elemanların sadece değerlerini sırayla okumak istiyorsanız: **`for-each`** kullanınız.  
> - Elemanın indeks numarasına ihtiyacınız varsa veya dizideki elemanları yerinde değiştirmek istiyorsanız: **Klasik `for`** kullanınız.

---

## 11. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: Parantezin Sonuna Noktalı Virgül (`;`) Koymak

```java
// ❌ HATA: for satırının sonundaki ';' döngünün gövdesini anında boş olarak bitirir!
for (int i = 0; i < 5; i++); 
{
    // Bu blok döngüden BAĞIMSIZDIR ve döngü bittikten sonra SADECE 1 KEZ ÇALIŞIR!
    System.out.println("Sayı: " + i); // Hatta 'i' burada scope dışı kalıp derleme hatası verir!
}
```

---

### 2. Tuzak: "Off-by-One" (Bir Eksik / Bir Fazla) Hatası
Döngünün kaç tur atacağını belirlerken `<` ile `<=` operatörlerini yanlış seçmek en sık yapılan mantık hatasıdır:

```java
int[] sayilar = new int[5]; // İndeksler: 0, 1, 2, 3, 4

// ❌ HATA: i <= 5 yazıldığında sayilar[5] aranır ve 'ArrayIndexOutOfBoundsException' fırlatılır!
for (int i = 0; i <= sayilar.length; i++) { ... }

// ✅ DOĞRU KULLANIM:
for (int i = 0; i < sayilar.length; i++) { ... }
```

---

### 3. Tuzak: Döngü Gövdesinde Sayacı İstemeden Değiştirmek
Döngü sayacının değerini gövde içerisinde kontrolsüzce değiştirmek beklenmeyen atlamalara veya sonsuz döngülere yol açar:

```java
for (int i = 0; i < 10; i++) {
    System.out.println(i);
    i++; // ❌ TEHLİKE: Sayaç hem for başlığında hem de burada 2'şer 2'şer artar!
}
```

---

### 4. Tuzak: Sayaç Türü Olarak `float` veya `double` Kullanmak
Kayan noktalı sayılar ikilik tabandaki yuvarlama kusurları nedeniyle döngü sayacı olarak asla kullanılmamalıdır:

```java
// ❌ YANLIŞ YAKLAŞIM: 0.1 toplamları tam 1.0 olmayabilir, tur sayısı şaşabilir!
for (double d = 0.0; d != 1.0; d += 0.1) { ... }

// ✅ DOĞRU: Sayaç daima tamsayı (int) olmalıdır:
for (int i = 0; i < 10; i++) {
    double d = i * 0.1;
}
```

---

## 12. Kapsamlı Örnek Konsol Uygulaması: `AsalSayiVeIstatistikMotoru`

Aşağıdaki program; kullanıcıdan bir aralık (başlangıç ve bitiş) alan, bu aralıktaki **asal sayıları**, **toplamlarını**, **ortalamalarını** ve **en büyüğünü** iç içe `for` döngüleri, `boolean` bayraklar (*flag*) ve `break` ifadeleriyle hesaplayan derli toplu ve çalıştırılabilir bir uygulamadır:

```java
import java.util.Scanner;

public class AsalSayiVeIstatistikMotoru {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("    ASAL SAYI VE ARALIK İSTATİSTİK MOTORU ");
        System.out.println("==========================================");

        System.out.print("Başlangıç değerini giriniz (En az 2): ");
        int baslangic = scanner.nextInt();

        System.out.print("Bitiş değerini giriniz: ");
        int bitis = scanner.nextInt();

        // Temel Girdi Doğrulama
        if (baslangic < 2 || bitis < baslangic) {
            System.out.println("❌ Hata: Başlangıç en az 2 olmalı ve bitiş başlangıçtan küçük olamaz!");
            scanner.close();
            return;
        }

        int asalAdedi = 0;
        long asalToplami = 0;
        int enBuyukAsal = -1;

        System.out.println("\n[" + baslangic + ", " + bitis + "] aralığındaki asal sayılar:");
        System.out.println("------------------------------------------");

        // Dış Döngü: Belirtilen aralıktaki her sayıyı tek tek test eder
        for (int sayi = baslangic; sayi <= bitis; sayi++) {
            boolean asalMi = true;

            // İç Döngü: Sayının 2'den kareköküne kadar olan bölenlerini arar (Optimizasyon)
            for (int bolen = 2; bolen * bolen <= sayi; bolen++) {
                if (sayi % bolen == 0) {
                    asalMi = false; // Bir bölen bulundu, asal değildir
                    break;          // Diğer bölenlere bakmaya gerek yok, iç döngüden çık!
                }
            }

            // Eğer hiçbir sayıya bölünmediyse sayımız asaldır:
            if (asalMi) {
                System.out.print(sayi + " ");
                asalAdedi++;
                asalToplami += sayi;
                enBuyukAsal = sayi;

                // Her 10 asal sayıda bir alt satıra geç (Görsel düzen)
                if (asalAdedi % 10 == 0) {
                    System.out.println();
                }
            }
        }

        System.out.println("\n------------------------------------------");
        System.out.println("Bulunan Toplam Asal Adedi : " + asalAdedi);

        if (asalAdedi > 0) {
            double ortalama = (double) asalToplami / asalAdedi;
            System.out.println("Asal Sayıların Toplamı    : " + asalToplami);
            System.out.printf("Asal Sayıların Ortalaması : %.2f\n", ortalama);
            System.out.println("En Büyük Asal Sayı        : " + enBuyukAsal);
        } else {
            System.out.println("Belirtilen aralıkta hiç asal sayı bulunamadı.");
        }

        System.out.println("==========================================");
        scanner.close();
    }
}
```

---

## 13. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - `continue` ve `break` Analizi)
Aşağıdaki kod parçasının konsol çıktısını kağıt üzerinde adım adım hesaplayınız:

```java
int sayac = 0;

for (int i = 1; i <= 5; i++) {
    for (int j = 1; j <= 5; j++) {
        if (j == 3) {
            continue;
        }
        if (i == 4) {
            break;
        }
        sayac++;
    }
}

System.out.println("Sonuç Sayaç: " + sayac);
```
*İpucu: `i = 4` olduğunda `break` komutu hangi döngüyü kırar? `j = 3` atlandığında tur başına sayaç kaç artar?*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında **1 sonsuz döngü** ve **2 derleme/mantık hatası** vardır. Hataları bulup düzeltiniz:

```java
// 1 ile 10 arasındaki sayıların karesini basmak istiyoruz:
for (int i = 1; i <= 10; i--); {
    int kare = i * i;
    System.out.println(i + " sayısının karesi: " + kare);
}
System.out.println("Son ulaşılan i: " + i);
```

---

### 📐 Alıştırma 3: Sayı Piramidi Çizdirme
Kullanıcıdan bir `n` tamsayısı alarak aşağıdaki gibi bir sayı üçgeni çizen iç içe `for` döngüsünü kodlayınız (`n = 5` için):

```text
1
1 2
1 2 3
1 2 3 4
1 2 3 4 5
```

---

### 🔢 Alıştırma 4: Bir Sayının Rakamları Toplamı
Kullanıcıdan alınan pozitif bir tamsayının (Örn: `1453`) tüm basamaklarındaki rakamların toplamını (`1 + 4 + 5 + 3 = 13`), sayıyı `for` döngüsü içinde sürekli `10`'a bölerek (`sayi /= 10`) ve mod alarak (`sayi % 10`) hesaplayınız.

---

## 14. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Değişken isimlerinde `camelCase` standardına uyunuz ve döngü sınırlarını dikkatle belirleyiniz.

---

### 📋 Ödev 1: Simetrik Elmas Deseni Oluşturucu
**Dosya Adı:** `ElmasDeseni.java`

**Problem Tanımı:**  
Kullanıcıdan **tek bir tamsayı** (Örn: `n = 7`) alınız. Eğer kullanıcı çift sayı girerse *"Lütfen tek sayı giriniz!"* uyarısı veriniz.  
İç içe `for` döngüleri kullanarak konsola boşluklar (` `) ve yıldızlardan (`*`) oluşan kusursuz simetrik bir elmas çizdiriniz.

**Örnek Çıktı (`n = 7` için):**
```text
   *
  ***
 *****
*******
 *****
  ***
   *
```
*İpucu: Deseni üst üçgen (satır 1'den (n/2 + 1)'e kadar) ve alt ters üçgen olarak iki ana aşamada kurgulayınız.*

---

### 📋 Ödev 2: Sayı Tahmin Oyunu (Sınırlı Hak ve Puanlama)
**Dosya Adı:** `SayiTahminOyunu.java`

**Problem Tanımı:**  
Bilgisayar 1 ile 100 arasında rastgele bir gizli sayı tutsun:  
`int gizliSayi = (int)(Math.random() * 100) + 1;`

**Kurallar:**
1. Kullanıcının toplam **7 tahmin hakkı** olsun. Bu hak kontrolünü `for` döngüsü ile yönetiniz:  
   `for (int hak = 1; hak <= 7; hak++)`
2. Her tahminde:
   - Tahmin gizli sayıdan küçükse: *"Daha BÜYÜK bir sayı giriniz!"*
   - Tahmin gizli sayıdan büyükse: *"Daha KÜÇÜK bir sayı giriniz!"*
   - Tahmin doğruysa: *"Tebrikler! {hak}. denemede bildiniz!"* mesajı verip `break` ile döngüyü sonlandırınız.
3. Kullanıcı doğru bildiğinde puanı hesaplansın: `Puan = 100 - ((hak - 1) * 15)`.
4. 7 hakkında da bilemezse: *"Hakkınız bitti! Kaybettiniz. Gizli sayı: {gizliSayi}"* yazdırılmalıdır.

---

### 📋 Ödev 3: Banka Yatırım ve Yıllık Bileşik Getiri Simülatörü
**Dosya Adı:** `BilesikGetiriSimulasyonu.java`

**Problem Tanımı:**  
Bir yatırımcının ana parasını, yıllık faiz/kâr payı oranını ve yatırım süresini (yıl) alarak her yıl sonundaki toplam bakiyeyi listeleyen bir simülasyon hazırlayınız.

**Girdiler:**
- Ana Para: Örn. `100.000 TL`
- Yıllık Getiri Oranı: Örn. `%25`
- Süre: Örn. `5 Yıl`

**İstenen Çıktı Tablosu:**
```text
Yıl     Başlangıç Bakiyesi    Yıllık Kâr         Yıl Sonu Toplamı
-----------------------------------------------------------------
1       100000.00 TL          25000.00 TL        125000.00 TL
2       125000.00 TL          31250.00 TL        156250.00 TL
3       156250.00 TL          39062.50 TL        195312.50 TL
4       195312.50 TL          48828.13 TL        244140.63 TL
5       244140.63 TL          61035.16 TL        305175.78 TL
-----------------------------------------------------------------
Toplam Elde Edilen Net Kâr: 205175.78 TL (Toplam Büyüme: %205.18)
```
*İpucu: `for (int yil = 1; yil <= vade; yil++)` döngüsünü kullanınız ve formatlı yazdırma için `System.out.printf()` tercih ediniz.*

---

### 📋 Ödev 4: Mükemmel Sayı ve Arkadaş Sayı Analizcisi
**Dosya Adı:** `MukemmelSayiAnalizi.java`

**Problem Tanımı:**  
1. **Mükemmel Sayı:** Kendisi hariç tüm pozitif tam bölenlerinin toplamı kendisine eşit olan sayıdır (Örn: `6 = 1 + 2 + 3`, `28 = 1 + 2 + 4 + 7 + 14`).  
   Kullanıcının girdiği bir sayının mükemmel sayı olup olmadığını bir `for` döngüsü ile bölenlerini toplayarak test ediniz.
2. **Genişletme:** 1 ile 10.000 arasındaki tüm mükemmel sayıları bulan ve ekrana yazdıran iç içe `for` döngüsünü kurunuz.
