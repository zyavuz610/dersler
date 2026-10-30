# 4. Java'da Rekürsif (Özyinelemeli) Metotlar Kılavuzu

Bu rehber; bilgisayar bilimlerinin en zarif ve güçlü kavramlarından biri olan **Rekürsiyonu (Özyineleme / Recursion)**, bir metodun kendi kendini çağırma mantığını, **Taban Durumu (Base Case)** kuralını, **Çağrı Yığını (Call Stack)** ve **`StackOverflowError`** mekanizmasını, klasikleşmiş rekürsif algoritmaları (**Faktöriyel, Fibonacci, Hızlı Üs Alma, EBOB, String Ters Çevirme, Hanoi Kuleleri**), döngülerle karşılaştırmasını, sık düşülen tuzakları ve öğrencilerin çözebileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 3_fonksiyonlar**  
> İlgili Önceki Dokümanlar:  
> • [1_fonksiyon-giriş.md](./1_fonksiyon-giriş.md)  
> • [2_fonksiyon return.md](./2_fonksiyon%20return.md)  
> • [3_fonksiyonlar ve diziler.md](./3_fonksiyonlar%20ve%20diziler.md)

---

## 📌 İçindekiler
1. [Rekürsiyon (Özyineleme) Nedir ve Temel Mantığı](#1-rekürsiyon-özyineleme-nedir-ve-temel-mantığı)
2. [Rekürsif Bir Metodun İki Temel Bileşeni](#2-rekürsif-bir-metodun-iki-temel-bileşeni)
3. [Bellek Mimarisi: Çağrı Yığını (Call Stack) ve `StackOverflowError`](#3-bellek-mimarisi-çağrı-yığını-call-stack-ve-stackoverflowerror)
4. [En Çok Bilinen Klasik Rekürsif Algoritmalar](#4-en-çok-bilinen-klasik-rekürsif-algoritmalar)
   - [4.1 Faktöriyel Hesabı](#41-faktöriyel-hesabı)
   - [4.2 Fibonacci Sayı Dizisi](#42-fibonacci-sayı-dizisi)
   - [4.3 Hızlı Üs Alma (Fast Exponentiation)](#43-hızlı-üs-alma-fast-exponentiation)
   - [4.4 Öklid Algoritması ile EBOB (En Büyük Ortak Bölen)](#44-öklid-algoritması-ile-ebob-en-büyük-ortak-bölen)
   - [4.5 Sayının Basamakları Toplamı](#45-sayının-basamakları-toplamı)
   - [4.6 Metin (String) Ters Çevirme ve Palindrom Kontrolü](#46-metin-string-ters-çevirme-ve-palindrom-kontrolü)
   - [4.7 Hanoi Kuleleri (Towers of Hanoi)](#47-hanoi-kuleleri-towers-of-hanoi)
5. [Rekürsiyon vs İterasyon (Döngüler) Karşılaştırması](#5-rekürsiyon-vs-iterasyon-döngüler-karşılaştırması)
6. [En Sık Yapılan Hatalar ve Tuzaklar](#6-en-sık-yapılan-hatalar-ve-tuzaklar)
7. [Kapsamlı Örnek Konsol Uygulaması: `RekursifAlgoritmaLaboratuvari`](#7-kapsamlı-örnek-konsol-uygulaması-rekursifalgoritmalaboratuvari)
8. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
9. [Ödev Projeleri](#9-ödev-projeleri)

---

## 1. Rekürsiyon (Özyineleme) Nedir ve Temel Mantığı?

Günlük hayatta iç içe geçmiş Rus Matruşka bebeklerini veya karşılıklı iki ayna arasına geçtiğinizde oluşan sonsuz yansımayı görmüşsünüzdür.

Programlamada **Rekürsiyon (Recursion)**; **bir metodun bir problemi çözmek için kendi kendisini daha küçük parametrelerle tekrar çağırmasıdır**.

Matematikte ve bilgisayar bilimlerinde pek çok karmaşık problem, kendisinin bir küçük kopyası cinsinden tanımlanabilir:
- Bir dosya klasörünün içindeki tüm dosyaları bulmak için: Klasörün içindeki alt klasörlere de aynı arama fonksiyonunu uygularsınız.
- $5!$ sayısını hesaplamak için: $5 \times 4!$ dersiniz. $4!$ için $4 \times 3!$ dersiniz.

Bu yaklaşıma yazılım dünyasında **Parçala ve Fethet (Divide and Conquer)** denir: Büyük problemi çözmek yerine, problem çözülmesi çok kolay bir boyuta inene kadar ufak parçalara bölünür.

---

## 2. Rekürsif Bir Metodun İki Temel Bileşeni

Her rekürsif metot **kesinlikle ve istisnasız** iki kısımdan oluşmak zorundadır:

```text
+-------------------------------------------------------------+
|                REKÜRSİF METODUN ANATOMİSİ                   |
|                                                             |
|   1. TABAN DURUMU (Base Case):                              |
|      "Artık dur! Sonucu doğrudan ver."                      |
|                                                             |
|   2. REKÜRSİF ADIM (Recursive Step):                        |
|      "Problemi biraz küçült ve kendini tekrar çağır."       |
+-------------------------------------------------------------+
```

1. **Taban Durumu (Base Case / Stop Condition):** Metodun kendi kendini çağırmayı bıraktığı, cevabın apaçık belli olduğu en temel durumdur (Örn: $0! = 1$ veya $sayi == 0$). Eğer taban durumu yazılmazsa metot sonsuz döngüye girer!
2. **Rekürsif Adım (Recursive Step):** Asıl problemin daha küçük bir girdisiyle metodun kendisini çağırdığı yerdir (`f(n) = n * f(n - 1)`).

---

## 3. Bellek Mimarisi: Çağrı Yığını (Call Stack) ve `StackOverflowError`

Java'da bir metot çağrıldığında işletim sistemi belleğin **Stack (Yığın)** bölgesinde o metoda özel bir **Çerçeve (Stack Frame)** açar.

### 🔄 Adım Adım Yürütme: İleri Sarma ve Geri Sarma
`faktoriyel(3)` çağrıldığında bellekte şu iki aşama gerçekleşir:

1. **İleri Sarma (Winding / Dalış Aşaması):**
   - `faktoriyel(3)` çağrılır ➡️ `3 * faktoriyel(2)` beklemeye geçer.
   - `faktoriyel(2)` çağrılır ➡️ `2 * faktoriyel(1)` beklemeye geçer.
   - `faktoriyel(1)` çağrılır ➡️ Taban durumuna ulaşılır! Geriye `1` döner.
2. **Geri Sarma (Unwinding / Çözülme Aşaması):**
   - `faktoriyel(1)` biter ve `1` değerini `faktoriyel(2)`'ye teslim eder ➡️ `2 * 1 = 2` hesaplanır.
   - `faktoriyel(2)` biter ve `2` değerini `faktoriyel(3)`'e teslim eder ➡️ `3 * 2 = 6` hesaplanır.
   - Stack çerçeveleri sırayla bellekten silinir ve nihai sonuç `6` olarak `main` metoduna ulaşır.

```text
[ STACK BELLEK YIĞINI ]
| faktoriyel(1) -> 1 döner (Taban Durumu) | ^
| faktoriyel(2) -> 2 * 1 = 2 döner        | | Geriye Doğru Çözülme
| faktoriyel(3) -> 3 * 2 = 6 döner        | | (Unwinding)
| main()                                  | |
+-----------------------------------------+
```

### 💣 `StackOverflowError` Felaketi
Eğer taban durumunu yazmayı unutursanız veya yanlış yazarsanız, metot durmadan yeni çerçeveler açar. Sonunda Stack belleği dolar ve Java meşhur **`java.lang.StackOverflowError`** hatasıyla programı anında çökertir!

---

## 4. En Çok Bilinen Klasik Rekürsif Algoritmalar

### 4.1 Faktöriyel Hesabı

- **Matematiksel Tanım:**  
  $$n! = \begin{cases} 1, & \text{eğer } n = 0 \text{ veya } n = 1 \text{ (Taban Durumu)} \\ n \times (n-1)!, & \text{eğer } n > 1 \text{ (Rekürsif Adım)} \end{cases}$$

```java
public class FaktoriyelRekursif {

    public static long faktoriyel(int n) {
        // 1. Taban Durumu (Base Case):
        if (n <= 1) {
            return 1;
        }
        // 2. Rekürsif Adım:
        return n * faktoriyel(n - 1);
    }

    public static void main(String[] args) {
        System.out.println("5! = " + faktoriyel(5)); // 120
    }
}
```

---

### 4.2 Fibonacci Sayı Dizisi

Fibonacci dizisinde her sayı, kendisinden önceki iki sayının toplamıdır: `0, 1, 1, 2, 3, 5, 8, 13, 21...`
- **Taban Durumu:** $F(0) = 0$, $F(1) = 1$
- **Rekürsif Adım:** $F(n) = F(n-1) + F(n-2)$

```java
public class FibonacciRekursif {

    // Ağaç Yapılı Rekürsiyon (Tree Recursion)
    public static int fibonacci(int n) {
        if (n <= 0) return 0; // Taban 1
        if (n == 1) return 1; // Taban 2

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {
        System.out.println("Fibonacci(7) = " + fibonacci(7)); // 13
    }
}
```

> ⚠️ **Verimlilik Notu:** Saf rekürsif Fibonacci algoritması aynı alt değerleri tekrar tekrar hesapladığı için $O(2^n)$ gibi çok ağır bir zaman karmaşıklığına sahiptir. `fibonacci(50)` çağrısı bilgisayarı dakikalarca kilitleyebilir! Büyük sayılarda döngü veya dinamik programlama tercih edilir.

---

### 4.3 Hızlı Üs Alma (Fast Exponentiation)

Bir sayının üssünü ($taban^{us}$) döngüyle hesaplamak $O(N)$ adım sürer. Oysa matematiksel bir kurnazlıkla üssü her adımda ikiye bölerek $O(\log N)$ süper hızlı hesaplayabiliriz:
- $2^8 = (2^4)^2$
- $2^9 = 2 \times (2^4)^2$

```java
public class HizliUsAlma {

    public static double usAl(double taban, int us) {
        // Taban Durumu: Her sayının 0. kuvveti 1'dir
        if (us == 0) return 1.0;

        // Negatif üs kontrolü: 2^-3 = 1 / (2^3)
        if (us < 0) return 1.0 / usAl(taban, -us);

        // Rekürsif Adım:
        double yariUs = usAl(taban, us / 2);

        if (us % 2 == 0) {
            return yariUs * yariUs; // Çift üs
        } else {
            return taban * yariUs * yariUs; // Tek üs
        }
    }
}
```

---

### 4.4 Öklid Algoritması ile EBOB (En Büyük Ortak Bölen)

İki sayının EBOB'unu bulmak için dünyanın en eski ve en zarif algoritmalarından biridir:
$$\text{EBOB}(a, b) = \text{EBOB}(b, a \pmod b)$$

```java
public class EbobRekursif {

    public static int ebob(int a, int b) {
        // Taban Durumu: b sıfır olduğunda a değeri EBOB'dur:
        if (b == 0) {
            return a;
        }
        // Rekürsif Adım:
        return ebob(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println("EBOB(48, 18) = " + ebob(48, 18)); // 6
    }
}
```

---

### 4.5 Sayının Basamakları Toplamı

Bir sayının rakamları toplamını mod (`%`) ve bölme (`/`) ile rekürsif bulma:

```java
public class BasamakToplami {

    public static int basamakTopla(int n) {
        if (n == 0) return 0; // Taban Durumu
        return (n % 10) + basamakTopla(n / 10);
    }

    public static void main(String[] args) {
        System.out.println("1453 Rakamları Toplamı: " + basamakTopla(1453)); // 1 + 4 + 5 + 3 = 13
    }
}
```

---

### 4.6 Metin (String) Ters Çevirme ve Palindrom Kontrolü

```java
public class StringRekursif {

    // Metni tersine çeviren rekürsif metot:
    public static String tersCevir(String metin) {
        // Taban Durumu: Boş metin veya tek harf zaten terstir
        if (metin == null || metin.length() <= 1) {
            return metin;
        }
        // Son harfi al + kalan metnin tersini arkasına ekle:
        return metin.charAt(metin.length() - 1) + tersCevir(metin.substring(0, metin.length() - 1));
    }

    // Palindrom kelime testi (Örn: "kayak"):
    public static boolean palindromMu(String str) {
        if (str.length() <= 1) return true; // Taban Durumu

        // İlk ve son harf eşit değilse palindrom değildir:
        if (str.charAt(0) != str.charAt(str.length() - 1)) {
            return false;
        }
        // Ortada kalan iç metni test etmeye devam et:
        return palindromMu(str.substring(1, str.length() - 1));
    }
}
```

---

### 4.7 Hanoi Kuleleri (Towers of Hanoi)

Bilgisayar biliminin en ünlü zeka oyunudur: 3 direk (Kaynak, Hedef, Yardımcı) ve farklı boyutlarda $N$ disk vardır.  
**Kurallar:** Bir seferde sadece bir disk taşınabilir ve hiçbir büyük disk küçük diskin üzerine konulamaz.

**Rekürsif Strateji:**
1. Üstteki $N-1$ diski Kaynaktan Yardımcı kuleye taşı.
2. En alttaki en büyük diski doğrudan Hedef kuleye taşı.
3. Yardımcı kuledeki $N-1$ diski Hedef kuleye taşı.

```java
public class HanoiKuleleri {

    public static void hanoiCoz(int diskSayisi, char kaynak, char hedef, char yardimci) {
        // Taban Durumu: Tek disk varsa doğrudan hedefe koy:
        if (diskSayisi == 1) {
            System.out.println("Disk 1 : " + kaynak + " ---> " + hedef);
            return;
        }

        // 1. Adım: n-1 diski kaynak direğinden yardımcı direğe taşı:
        hanoiCoz(diskSayisi - 1, kaynak, yardimci, hedef);

        // 2. Adım: Asıl büyük diski hedefe taşı:
        System.out.println("Disk " + diskSayisi + " : " + kaynak + " ---> " + hedef);

        // 3. Adım: Yardımcı direkteki n-1 diski hedefe taşı:
        hanoiCoz(diskSayisi - 1, yardimci, hedef, kaynak);
    }

    public static void main(String[] args) {
        System.out.println("--- 3 DİSK İÇİN HANOİ KULELERİ ADIMLARI ---");
        hanoiCoz(3, 'A', 'C', 'B'); // A: Kaynak, C: Hedef, B: Yardımcı
    }
}
```

---

## 5. Rekürsiyon vs İterasyon (Döngüler) Karşılaştırması

| Kriter | Rekürsif Metotlar | İteratif Yöntem (Döngüler: `for`, `while`) |
| :--- | :--- | :--- |
| **Kod Yapısı** | Genellikle çok daha kısa, matematiksel ve zariftir. | Sayaçlar ve durum değişkenleriyle biraz daha uzundur. |
| **Bellek Tüketimi** | **Yüksektir.** Her adımda Call Stack'e yeni çerçeve açılır ($O(N)$ bellek). | **Düşüktür.** Tek bir çerçevede değişken güncellenir ($O(1)$ bellek). |
| **Çalışma Hızı** | Metot çağrı maliyeti (*function call overhead*) nedeniyle bir miktar daha yavaştır. | Doğrudan işlemci döngüsüyle son derece hızlıdır. |
| **Hata Riski** | Taban durumu unutulursa `StackOverflowError` patlar. | Koşul hatasında sonsuz döngüye girer. |
| **En İyi Olduğu Alan** | Ağaçlar (*Trees*), Grafikler, Hiyerarşik Dosya Dizinleri, Backtracking. | Basit sayma, dizi elemanlarını gezme, toplam alma. |

---

## 6. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: Taban Durumunu Unutmak veya Erişilemez Kılmak

```java
// ❌ FELAKET: Taban durumu yok! Sonsuza kadar çağırır ve StackOverflowError verir!
public static int sonsuz(int n) {
    return n + sonsuz(n - 1);
}
```

---

### 2. Tuzak: Parametreyi Küçültmemek

```java
// ❌ HATA: n-1 yerine n çağrılmış! Değer hiç değişmediği için durmaz!
public static int hatali(int n) {
    if (n == 0) return 0;
    return n + hatali(n); // StackOverflowError!
}
```

---

### 3. Tuzak: Çok Derin Çağrılar
Java'da varsayılan Stack boyutu genellikle birkaç bin çağrıya izin verir. Eğer `faktoriyel(100000)` çağırmaya kalkarsanız, taban durumu doğru olsa bile yığın dolduğu için `StackOverflowError` alırsınız. Bu büyüklükteki sayılarda iterasyon şarttır.

---

## 7. Kapsamlı Örnek Konsol Uygulaması: `RekursifAlgoritmaLaboratuvari`

Aşağıdaki program; faktöriyel, Fibonacci, hızlı üs, EBOB ve Hanoi Kuleleri algoritmalarını adım adım kullanıcı girdileriyle çalıştıran eksiksiz bir konsol laboratuvarıdır:

```java
import java.util.Scanner;

public class RekursifAlgoritmaLaboratuvari {

    // 1. Faktöriyel
    public static long faktoriyel(int n) {
        if (n <= 1) return 1;
        return n * faktoriyel(n - 1);
    }

    // 2. Fibonacci
    public static int fibonacci(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // 3. EBOB (Öklid)
    public static int ebob(int a, int b) {
        if (b == 0) return a;
        return ebob(b, a % b);
    }

    // 4. Hızlı Üs Alma
    public static double usAl(double taban, int us) {
        if (us == 0) return 1.0;
        if (us < 0) return 1.0 / usAl(taban, -us);
        double yari = usAl(taban, us / 2);
        return (us % 2 == 0) ? yari * yari : taban * yari * yari;
    }

    // 5. Hanoi Kuleleri
    public static void hanoi(int n, char k, char h, char y) {
        if (n == 1) {
            System.out.println("  -> Disk 1 : " + k + " ==> " + h);
            return;
        }
        hanoi(n - 1, k, y, h);
        System.out.println("  -> Disk " + n + " : " + k + " ==> " + h);
        hanoi(n - 1, y, h, k);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int secim;

        do {
            System.out.println("\n==========================================");
            System.out.println("    KTÜ REKÜRSİF ALGORİTMA LABORATUVARI   ");
            System.out.println("==========================================");
            System.out.println("1 - Faktöriyel Hesabı");
            System.out.println("2 - Fibonacci Sayısı Bul");
            System.out.println("3 - İki Sayının EBOB'unu Bul (Öklid)");
            System.out.println("4 - Hızlı Üs Alma (O(logN))");
            System.out.println("5 - Hanoi Kuleleri Çözümü");
            System.out.println("0 - Çıkış Yap");
            System.out.print("İşlem seçiniz: ");
            secim = scanner.nextInt();

            switch (secim) {
                case 1 -> {
                    System.out.print("Pozitif bir tamsayı giriniz (0-20): ");
                    int n = scanner.nextInt();
                    if (n < 0 || n > 20) System.out.println("❌ 0 ile 20 arası sayı giriniz.");
                    else System.out.println("-> " + n + "! = " + faktoriyel(n));
                }
                case 2 -> {
                    System.out.print("Kaçıncı Fibonacci sayısını istiyorsunuz (0-30): ");
                    int n = scanner.nextInt();
                    System.out.println("-> Fibonacci(" + n + ") = " + fibonacci(n));
                }
                case 3 -> {
                    System.out.print("1. Sayıyı giriniz: ");
                    int a = scanner.nextInt();
                    System.out.print("2. Sayıyı giriniz: ");
                    int b = scanner.nextInt();
                    System.out.println("-> EBOB(" + a + ", " + b + ") = " + ebob(a, b));
                }
                case 4 -> {
                    System.out.print("Taban değerini giriniz: ");
                    double taban = scanner.nextDouble();
                    System.out.print("Üs tamsayısını giriniz: ");
                    int us = scanner.nextInt();
                    System.out.println("-> " + taban + "^" + us + " = " + usAl(taban, us));
                }
                case 5 -> {
                    System.out.print("Hanoi disk sayısını giriniz (1-6): ");
                    int disk = scanner.nextInt();
                    System.out.println("\nÇözüm Adımları (A:Kaynak, B:Yardımcı, C:Hedef):");
                    hanoi(disk, 'A', 'C', 'B');
                }
                case 0 -> System.out.println("-> Laboratuvardan çıkılıyor. İyi çalışmalar!");
                default -> System.out.println("❌ Geçersiz seçim!");
            }

        } while (secim != 0);

        scanner.close();
    }
}
```

---

## 8. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Gizemli Rekürsiyon)
Aşağıdaki metodun `gizem(4)` çağrısı için ekrana sırasıyla ne basacağını ve geriye ne döndüreceğini adım adım takip ediniz:

```java
public class GizemliTest {
    public static int gizem(int n) {
        if (n <= 1) {
            return 1;
        }
        System.out.print(n + " ");
        int sonuc = n + gizem(n - 1);
        System.out.print(n + " ");
        return sonuc;
    }

    public static void main(String[] args) {
        int r = gizem(4);
        System.out.println("\nDönen Sonuç: " + r);
    }
}
```
*İpucu: Metodun hem dalış (winding) hem çıkış (unwinding) esnasında ekrana yazdığına dikkat ediniz!*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında `StackOverflowError` hatasına yol açan mantık hatasını bulunuz ve düzeltiniz:

```java
public static int toplam(int n) {
    if (n == 0) {
        return 0;
    }
    return n + toplam(n - 2); // Negatif veya tek sayılarda ne olur?
}
```

---

### 🔢 Alıştırma 3: Rekürsif Dizi Elemanları Toplamı
Bir `int[] dizi` ve bir `int indeks` parametresi alarak dizinin tüm elemanlarının toplamını döngü **kullanmadan**, rekürsif olarak hesaplayan metodu yazınız.

---

### 🔤 Alıştırma 4: 1'den N'e Kadar Sayıları Ekrana Basma
Verilen pozitif bir $N$ tamsayısı için döngü kullanmadan:
1. `sayilariArtanBas(n)`: $1, 2, 3, \dots, N$ şeklinde ekrana bassın.
2. `sayilariAzalanBas(n)`: $N, \dots, 3, 2, 1$ şeklinde ekrana bassın.

---

## 9. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Taban durumlarını (`base case`) titizlikle belirleyiniz.

---

### 📋 Ödev 1: Onluk Sayıyı İkilik Tabana (Binary) Çevirici
**Dosya Adı:** `OnluktanIkilige.java`

**Problem Tanımı:**  
Kullanıcının girdiği pozitif bir onluk tamsayıyı (Örn: `13`), döngü kullanmadan rekürsif olarak ikilik tabana (`1101`) çevirip `String` olarak döndüren metodu kodlayınız.
- **İpucu:** Sayı 2'ye bölünür (`n / 2`), kalan (`n % 2`) sona eklenir. Taban durumu: `n <= 1`.

---

### 📋 Ödev 2: Rekürsif İkili Arama (Recursive Binary Search)
**Dosya Adı:** `RekursifBinarySearch.java`

**Problem Tanımı:**  
`public static int binarySearch(int[] dizi, int sol, int sag, int hedef)` metodunu rekürsif olarak yazınız:
- Taban durumu: `sol > sag` ise bulunamadı (`-1`).
- Ortadaki eleman hedefe eşitse `orta` indeksi döndür.
- Hedef küçükse sol yarıyı rekürsif ara: `binarySearch(dizi, sol, orta - 1, hedef)`.
- Hedef büyükse sağ yarıyı rekürsif ara: `binarySearch(dizi, orta + 1, sag, hedef)`.

---

### 📋 Ödev 3: Rekürsif Kombinasyon $C(n, r)$ Hesaplayıcı
**Dosya Adı:** `KombinasyonHesaplayici.java`

**Problem Tanımı:**  
Pascal kuralına göre $n$ elemanlı bir kümeden $r$ elemanlı alt küme seçimi faktöriyel çarpmadan rekürsif hesaplanabilir:
$$C(n, r) = C(n - 1, r - 1) + C(n - 1, r)$$
- **Taban Durumları:** $r = 0$ veya $r = n$ ise $C(n, r) = 1$'dir.
- Bu matematiksel tanımı doğrudan uygulayan rekürsif metodu yazınız.

---

### 📋 Ödev 4: Labirentte Çıkış Yolu Bulucu (Backtracking / Geriye İz Sürme)
**Dosya Adı:** `LabirentCozucu.java`

**Problem Tanımı:**  
5x5 boyutunda bir labirent matrisi tanımlayınız (`1`: Açık Yol, `0`: Duvar):
- Giriş noktası: `(0, 0)`, Çıkış noktası: `(4, 4)`.
- Rekürsif bir metotla; yukarı, aşağı, sağa ve sola hareket ederek çıkışa ulaşan bir yol bulunuz.
- Gezilen yolları `2` ile işaretleyiniz; çıkmaz sokağa girildiğinde geri adım atarak (*Backtrack*) başka yollar deneyiniz.
- Çıkışa ulaşıldığında nihai labirent rotasını ekrana dökünüz.
