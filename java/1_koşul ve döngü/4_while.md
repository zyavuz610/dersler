# 4. Java'da Döngüler: `while` ve `do-while` Kılavuzu

Bu rehber; Java programlama dilinde tekrar sayısı önceden kesin olarak bilinmeyen, belirli bir koşulun doğruluğuna veya kullanıcı eylemlerine bağlı olarak çalışan **`while`** ve **`do-while`** döngülerini, çalışma prensiplerini, `for` döngüsü ile farklarını, kritik sonsuz döngü tuzaklarını ve öğrencilerin pekiştirmesi için pratik alıştırmalar ile kapsamlı ödevleri içermektedir.

---

> 📖 **Modül: 1_koşul ve döngü**  
> İlgili Önceki Dokümanlar:  
> • [1_if-else.md](./1_if-else.md)  
> • [2_switch-case.md](./2_switch-case.md)  
> • [3_for.md](./3_for.md)

---

## 📌 İçindekiler
1. [`while` Döngüsü Nedir ve Ne Zaman Kullanılır?](#1-while-döngüsü-nedir-ve-ne-zaman-kullanılır)
2. [`while` Döngüsünün Anatomisi ve Çalışma Adımları](#2-while-döngüsünün-anatomisi-ve-çalışma-adımları)
3. [Temel `while` Döngüsü Senaryoları](#3-temel-while-döngüsü-senaryoları)
4. [`do-while` Döngüsü (En Az Bir Kez Çalışma Garantisi)](#4-do-while-döngüsü-en-az-bir-kez-çalışma-garantisi)
5. [Büyük Karşılaştırma: `for` vs `while` vs `do-while`](#5-büyük-karşılaştırma-for-vs-while-vs-do-while)
6. [`while` İçinde `break` ve `continue` Kullanımı (Kritik Tuzak!)](#6-while-içinde-break-ve-continue-kullanımı-kritik-tuzak)
7. [En Sık Yapılan Hatalar ve Tuzaklar](#7-en-sık-yapılan-hatalar-ve-tuzaklar)
8. [Kapsamlı Örnek Konsol Uygulaması: `BankaAtmSimulasyonu`](#8-kapsamlı-örnek-konsol-uygulaması-bankaatmsimulasyonu)
9. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#9-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
10. [Ödev Projeleri](#10-ödev-projeleri)

---

## 1. `while` Döngüsü Nedir ve Ne Zaman Kullanılır?

Önceki konuda gördüğümüz `for` döngüsü, bir işlemin **kaç kez tekrarlanacağını en baştan bildiğimiz** durumlarda (örneğin: *"10 defa yazdır"*, *"dizideki 50 elemanı tara"*) mükemmel bir tercihti.

Ancak gerçek hayat yazılımlarında tekrar sayısı genellikle önceden kestirilemez:
- Bir kullanıcı doğru şifreyi kaçıncı denemede girecek? (1. deneme mi, 5. mi, 20. mi?)
- Bir oyunda karakterin canı ne zaman sıfıra inecek?
- Kullanıcı konsoldan *"çıkış"* (`q`) yazana kadar kaç adet veri girecek?

İşte bu tür, **tekrar sayısı baştan belli olmayan ve bir mantıksal koşul `true` kaldığı müddetçe dönmesi gereken** durumlarda **`while`** döngüsü kullanılır.

> 💡 **Türkçe Anlamı:** `while`, İngilizcede *"iken"*, *"-dığı sürece"* anlamına gelir.  
> Yani: `while (can > 0)` ➡️ *"Can sıfırdan büyük olduğu sürece oynamaya devam et."*

---

## 2. `while` Döngüsünün Anatomisi ve Çalışma Adımları

`while` döngüsü bir **Ön Koşullu Döngüdür (Entry-Controlled Loop)**. Yani Java, döngü gövdesine girmeden önce koşulu en başta test eder. Eğer koşul en baştan `false` ise, gövde **0 kez (hiç)** çalıştırılmaz.

### 📌 Genel Sözdizimi:

```java
// 1. Sayaç / Başlangıç Durumu (Döngüden önce tanımlanır)
int sayac = 1;

while (sayac <= 5) { // 2. Koşul Kontrolü (Her turun başında)
    // 3. Döngü Gövdesi
    System.out.println("Sayı: " + sayac);

    sayac++; // 4. Güncelleme (Gövde içinde elle yapılmak ZORUNDADIR!)
}
```

### 🔍 `for` ile `while` Arasındaki Yapısal Fark:
- `for` döngüsünde `(başlatma; koşul; güncelleme)` tek satırda toplanmıştır.
- `while` döngüsünde ise:
  - Başlangıç değişkeni döngünün **dışında** tanımlanır.
  - Güncelleme adımı gövdenin **içinde manuel olarak** yazılır.
  - Eğer güncelleme adımını (`sayac++`) yazmayı unutursanız, koşul hep `true` kalır ve program **sonsuz döngüye** girer!

---

## 3. Temel `while` Döngüsü Senaryoları

### 💻 Senaryo 1: Klasik Sayaç ile Sayma ve Toplama

```java
public class WhileTemelToplam {
    public static void main(String[] args) {
        int sayi = 1;
        int toplam = 0;

        // 1'den 10'a kadar olan sayıların toplamı:
        while (sayi <= 10) {
            toplam += sayi;
            sayi++; // Sayacı artırmayı unutursak sayi hep 1 kalır!
        }

        System.out.println("1 ile 10 arası sayıların toplamı: " + toplam);
    }
}
```

---

### 💻 Senaryo 2: Sentinel Değer (Bekçi / Çıkış Değeri) ile Kullanıcı Girdisi İşleme
Kullanıcıdan sürekli sayı alıp toplayan, kullanıcı `0` (Sentinel Değer) girdiğinde duran dinamik algoritma:

```java
import java.util.Scanner;

public class SentinelToplayici {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int toplam = 0;

        System.out.println("Toplanacak sayıları giriniz (Bitirmek için 0 yazınız):");
        System.out.print("Sayı girin: ");
        int girilen = scanner.nextInt();

        // Girilen sayı 0 OLMADIĞI SÜRECE döngü döner:
        while (girilen != 0) {
            toplam += girilen;

            System.out.print("Sıradaki sayı (Çıkış için 0): ");
            girilen = scanner.nextInt(); // Bir sonraki girdiyi al
        }

        System.out.println("Girilen tüm sayıların genel toplamı: " + toplam);
        scanner.close();
    }
}
```

---

### 💻 Senaryo 3: Matematiksel Basamak Ayrıştırma (Sayının Rakamları ve Tersi)
`while` döngüsünün en sık kullanıldığı klasik mülakat problemlerinden biri: Bir sayının basamak sayısını önceden bilmeden basamak basamak küçültülmesi (`sayi /= 10`).

```java
public class SayiyiTersCevir {
    public static void main(String[] args) {
        int sayi = 98765;
        int orijinalSayi = sayi;
        int tersSayi = 0;

        // Sayı 0 olana kadar son basamağını kopar ve ters sayıya ekle:
        while (sayi > 0) {
            int sonBasamak = sayi % 10;           // Son basamağı al (Örn: 5)
            tersSayi = (tersSayi * 10) + sonBasamak; // Tersi oluştur
            sayi /= 10;                           // Sayıyı 10'a bölerek basamağı at (9876)
        }

        System.out.println("Orijinal Sayı : " + orijinalSayi);
        System.out.println("Ters Çevrilmiş : " + tersSayi);
    }
}
```

---

### 💻 Senaryo 4: Bayrak (Flag) Değişkeni ile Durum Kontrolü

```java
boolean sistemAktif = true;
int denemeSayisi = 0;

while (sistemAktif) {
    denemeSayisi++;
    System.out.println("Sistem çalışıyor, tur: " + denemeSayisi);

    if (denemeSayisi >= 3) {
        sistemAktif = false; // Bayrağı indir, döngü sonlansın
    }
}
```

---

## 4. `do-while` Döngüsü (En Az Bir Kez Çalışma Garantisi)

`do-while` döngüsü bir **Son Koşullu Döngüdür (Exit-Controlled Loop)**.  
Java önce `do` bloğu içindeki kodları **şartsız şurtsuz çalıştırır**, ardından `while` parantezindeki koşula bakar. Koşul `false` olsa bile kod **en az bir kez** çalışmış olur.

### 📌 Genel Sözdizimi:

```java
do {
    // Koşula bakılmaksızın EN AZ BİR KEZ çalışacak kodlar
    // Güncelleme işlemi
} while (koşul_ifadesi); // ⚠️ DİKKAT: Sonundaki ';' ZORUNLUDUR!
```

---

### ⚔️ `while` ile `do-while` Arasındaki Kritik Fark:

```java
int x = 100;

// 1. NORMAL WHILE: Koşul (x < 10) en baştan false olduğu için HİÇ ÇALIŞMAZ:
while (x < 10) {
    System.out.println("Bu satır asla ekrana basılmaz!");
}

// 2. DO-WHILE: Koşul false olsa dahi gövde 1 KEZ KESİNLİKLE ÇALIŞIR:
do {
    System.out.println("Bu satır koşul false olsa bile 1 kez çalışır!");
} while (x < 10);
```

### 💻 Tipik Kullanım: Kullanıcıdan Geçerli Veri Alma (Girdi Doğrulama)

```java
import java.util.Scanner;

public class GirdiDogrulamaDoWhile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int not;

        // Kullanıcı 0 ile 100 arasında geçerli bir not girene kadar sormaya devam et:
        do {
            System.out.print("Lütfen vize notunu giriniz (0 - 100): ");
            not = scanner.nextInt();

            if (not < 0 || not > 100) {
                System.out.println("⚠️ Hatalı not girdiniz! Tekrar deneyiniz.");
            }
        } while (not < 0 || not > 100);

        System.out.println("Giriş başarılı! Kaydedilen not: " + not);
        scanner.close();
    }
}
```

---

## 5. Büyük Karşılaştırma: `for` vs `while` vs `do-while`

| Özellik | `for` Döngüsü | `while` Döngüsü | `do-while` Döngüsü |
| :--- | :--- | :--- | :--- |
| **Koşul Denetim Zamanı** | En başta (Ön Koşullu) | En başta (Ön Koşullu) | En sonda (Son Koşullu) |
| **Minimum Çalışma Sayısı** | **0** (Koşul baştan yanlışsa hiç çalışmaz) | **0** (Koşul baştan yanlışsa hiç çalışmaz) | **1** (Koşul yanlış olsa bile kesinlikle 1 kez çalışır) |
| **Sayacın Yaşam Alanı (Scope)**| `for` parantezinde doğar, döngü bitince silinir (Temiz bellek). | Döngüden önce tanımlanır, döngü bitse de dışarıda yaşamaya devam eder. | Döngüden önce tanımlanır, döngü bitse de yaşamaya devam eder. |
| **En Uygun Kullanım Yeri** | Tur / tekrar sayısı **önceden hesaplanabiliyorsa**. | Tur sayısı belirsiz, **olay veya şart odaklıysa**. | Kullanıcıdan **en az bir girdi almak** veya **menü göstermek** gerektiğinde. |

---

### 🔄 Aynı Görevin 3 Döngüyle Yazılışı:

1'den 3'e kadar olan sayıları ekrana basalım:

```java
// 1. FOR İLE:
for (int i = 1; i <= 3; i++) {
    System.out.print(i + " ");
}

// 2. WHILE İLE:
int j = 1;
while (j <= 3) {
    System.out.print(j + " ");
    j++;
}

// 3. DO-WHILE İLE:
int k = 1;
do {
    System.out.print(k + " ");
    k++;
} while (k <= 3);
```

---

## 6. `while` İçinde `break` ve `continue` Kullanımı (Kritik Tuzak!)

### 🛑 `while (true)` ve `break` Kalıbı
Java geliştirmede en popüler tasarım kalıplarından biri, döngüyü `while (true)` ile sonsuz başlatıp, çıkış şartı sağlandığında içeriden `break` ile kırmaktır:

```java
Scanner scanner = new Scanner(System.in);

while (true) {
    System.out.print("Bir komut giriniz ('cikis' bitirir): ");
    String komut = scanner.next();

    if (komut.equalsIgnoreCase("cikis")) {
        System.out.println("Programdan güvenle çıkıldı.");
        break; // Döngüyü tamamen sonlandırır
    }

    System.out.println("Komut işlendi: " + komut);
}
```

---

### ⚠️ ÖLÜMCÜL TUZAK: `while` İçinde `continue` ve Unutulan Sayaç!

`for` döngüsünde `continue` dendiğinde döngü otomatik olarak parantezdeki `i++` güncelleme adımına gider. Ancak `while` döngüsünde güncelleme **gövdenin içindedir**.

Eğer `continue` ifadesini güncelleme satırının **önüne** yazarsanız, sayaç hiçbir zaman artamaz ve döngü **anında sonsuz döngüye kilitlenir!**

```java
// ❌ ÖLÜMCÜL HATA: Sonsuz Döngü Tuzağı!
int i = 1;
while (i <= 10) {
    if (i == 5) {
        continue; // i=5 iken continue çağrılır, alttaki i++ ATLANIR!
                  // i değeri hep 5 kalır ve döngü sonsuza kadar döner!
    }
    System.out.println(i);
    i++;
}

// ✅ DOĞRU KULLANIM: continue çağrılmadan önce sayaç mutlaka artırılmalıdır:
int i = 1;
while (i <= 10) {
    if (i == 5) {
        i++;      // Sayacı artır ve öyle atla!
        continue;
    }
    System.out.println(i);
    i++;
}
```

---

## 7. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: `while` Parantezinin Sonuna Yanlışlıkla Noktalı Virgül (`;`) Koymak

```java
int i = 0;

// ❌ FELAKET HATASI: while(i < 5); sonundaki ';' boş bir döngü oluşturur!
while (i < 5); 
{
    // Program üstteki satırda sonsuza kadar asılı kalır (freeze)!
    // Bu süslü paranteze ASLA GEÇEMEZ!
    System.out.println(i);
    i++;
}
```

---

### 2. Tuzak: `do-while` Sonundaki Noktalı Virgülü (`;`) Unutmak
`while` döngüsünün sonuna noktalı virgül koymak mantık hatası iken, **`do-while` döngüsünün sonundaki `while (kosul);` ifadesinin sonuna noktalı virgül koymak ZORUNLUDUR**. Unutulursa derleyici hata verir:

```java
do {
    System.out.println("Merhaba");
} while (kosul) // ❌ DERLEME HATASI: ';' bekleniyor!
```

---

### 3. Tuzak: Sayacı Güncellemeyi Unutmak
Sayacı artırmayı veya azaltmayı unutmak programın donmasına ve bellek/CPU tüketmesine sebep olur:

```java
int sayac = 10;
while (sayac > 0) {
    System.out.println(sayac);
    // sayac--; UNUTULDU! sayac hep 10 kalır!
}
```

---

### 4. Tuzak: String Eşitliklerinde `!=` Kullanmak
String türündeki çıkış kontrollerinde `!=` operatörü adres kontrolü yaptığı için beklenmedik sonuçlar doğurur:

```java
String cevap = "evet";

// ❌ HATALI:
// while (cevap != "hayir") { ... }

// ✅ DOĞRU:
while (!cevap.equalsIgnoreCase("hayir")) {
    // İşlemler
}
```

---

## 8. Kapsamlı Örnek Konsol Uygulaması: `BankaAtmSimulasyonu`

Aşağıdaki program; `do-while` ile ana menüyü ekranda tutan, kullanıcı çıkış yapana kadar işlemleri kesintisiz sürdüren, bakiye sorgulama, para yatırma ve para çekme işlemlerini güvenle yöneten tam teşekküllü bir simülasyondur:

```java
import java.util.Scanner;

public class BankaAtmSimulasyonu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double bakiye = 5000.0; // Başlangıç bakiyesi (TL)
        int secim;

        System.out.println("==========================================");
        System.out.println("       KTÜ KAMPÜS DİJİTAL BANKAMATİK      ");
        System.out.println("==========================================");

        // Menü en az bir kez gösterilmeli ve kullanıcı '4' (Çıkış) seçene kadar dönmelidir:
        do {
            System.out.println("\n--- İŞLEM MENÜSÜ ---");
            System.out.println("1) Bakiye Sorgulama");
            System.out.println("2) Para Yatırma");
            System.out.println("3) Para Çekme");
            System.out.println("4) Kart İade / Güvenli Çıkış");
            System.out.print("Lütfen yapmak istediğiniz işlemi seçiniz (1-4): ");
            secim = scanner.nextInt();

            switch (secim) {
                case 1:
                    System.out.println("-> Güncel Kullanılabilir Bakiyeniz: " + bakiye + " TL");
                    break;

                case 2:
                    System.out.print("Yatırmak istediğiniz tutarı giriniz (TL): ");
                    double yatirilacak = scanner.nextDouble();

                    // Girdi geçerlilik kontrolü
                    while (yatirilacak <= 0) {
                        System.out.print("❌ Geçersiz tutar! Lütfen sıfırdan büyük bir miktar giriniz: ");
                        yatirilacak = scanner.nextDouble();
                    }

                    bakiye += yatirilacak;
                    System.out.println("-> " + yatirilacak + " TL başarıyla yatırıldı. Yeni bakiye: " + bakiye + " TL");
                    break;

                case 3:
                    System.out.print("Çekmek istediğiniz tutarı giriniz (TL): ");
                    double cekilecek = scanner.nextDouble();

                    if (cekilecek <= 0) {
                        System.out.println("❌ Hata: Çekilecek tutar pozitif olmalıdır.");
                    } else if (cekilecek > bakiye) {
                        System.out.println("❌ Yetersiz Bakiye! En fazla " + bakiye + " TL çekebilirsiniz.");
                    } else {
                        bakiye -= cekilecek;
                        System.out.println("-> Lütfen paranızı hazneden alınız. Yeni bakiye: " + bakiye + " TL");
                    }
                    break;

                case 4:
                    System.out.println("-> Kartınız iade ediliyor. Bizi tercih ettiğiniz için teşekkür ederiz! 👋");
                    break;

                default:
                    System.out.println("❌ Geçersiz seçim! Lütfen 1 ile 4 arasında bir numara tuşlayınız.");
                    break;
            }

        } while (secim != 4); // Kullanıcı 4'e basmadığı sürece menü tekrar gelir

        System.out.println("==========================================");
        scanner.close();
    }
}
```

---

## 9. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Sayı Parçalama)
Aşağıdaki kod parçasının konsol çıktısını kağıt üzerinde adım adım hesaplayınız:

```java
int sayi = 1234;
int toplam = 0;

while (sayi > 0) {
    toplam += (sayi % 10);
    sayi /= 10;
}

System.out.println("Sonuç Toplam: " + toplam);
```

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında programın donmasına veya derlenememesine yol açan **2 büyük hatayı** tespit edip düzeltiniz:

```java
int sayac = 1;

while (sayac <= 5); {
    System.out.println("Sayaç: " + sayac);
    sayac++;
}

int secim = 0;
do {
    secim++;
} while (secim < 3)
```

---

### 🔄 Alıştırma 3: Palindrom Sayı Kontrolü
Bir sayının tersten okunuşu kendisine eşitse bu sayıya **Palindrom Sayı** denir (Örn: `121`, `1331`, `4554`).  
Kullanıcıdan alınan bir tamsayının `while` döngüsü yardımıyla palindrom olup olmadığını test eden programı yazınız.

---

### 📐 Alıştırma 4: İki Sayının EBOB Hesabı (Öklid Algoritması)
İki sayının En Büyük Ortak Bölenini (EBOB) bulmak için Öklid algoritması şu kuralı uygular:  
`sayi2` sıfır olmadığı sürece:
1. `kalan = sayi1 % sayi2;`
2. `sayi1 = sayi2;`
3. `sayi2 = kalan;`  
Döngü bittiğinde `sayi1` değeri iki sayının EBOB'udur. Bu algoritmayı bir `while` döngüsü ile kodlayınız.

---

## 10. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Döngülerin sonlanma şartlarını eksiksiz planlayınız.

---

### 📋 Ödev 1: Collatz Problemi (3n + 1 Sanısı)
**Dosya Adı:** `CollatzProblemi.java`

**Problem Tanımı:**  
Matematik dünyasının en ünlü çözülememiş problemlerinden biri olan Collatz dizisini modelleyiniz:
- Kullanıcıdan pozitif bir `n` tamsayısı alınız.
- `n` sayısı `1`'e eşit olmadığı sürece (`while (n != 1)`):
  - Eğer `n` çift sayı ise: `n = n / 2`
  - Eğer `n` tek sayı ise: `n = (3 * n) + 1`
- Her adımda oluşan sayıyı ekrana basınız.
- **İstenen İstatistikler:**
  1. Sayının 1'e ulaşması için kaç adım geçtiği (Toplam döngü tur sayısı),
  2. Adımlar boyunca ulaşılan **en yüksek tepe sayısı** (`max`).

**Örnek Akış (`n = 6` için):**
```text
Dizi: 6 -> 3 -> 10 -> 5 -> 16 -> 8 -> 4 -> 2 -> 1
Toplam Adım Sayısı: 8
Ulaşılan En Yüksek Değer: 16
```

---

### 📋 Ödev 2: Sayısal Loto Kupon Üreteci (Tekrarsız 6 Rastgele Sayı)
**Dosya Adı:** `SayisalLoto.java`

**Problem Tanımı:**  
Milli Piyango Sayısal Loto oyunu için 1 ile 49 arasında **birbirinden tamamen farklı rastgele 6 adet sayı** üreten programı yazınız.

**Kriterler:**
1. Rastgele sayı üretmek için: `int rastgele = (int)(Math.random() * 49) + 1;`
2. Üretilen sayının daha önce seçilip seçilmediğini kontrol ediniz.
3. Eğer üretilen sayı daha önce seçilmişse kabul edilmemeli, sayaç artırılmamalı ve yeni sayı üretilmeye devam edilmelidir. (Tam 6 benzersiz sayı bulunana kadar `while (adet < 6)` dönmelidir).
4. Çıkan 6 şanslı sayıyı konsola düzenli bir şekilde yazdırınız.

---

### 📋 Ödev 3: Kasiyer ve Akıllı Para Üstü İade Sistemi
**Dosya Adı:** `KasiyerParaUstu.java`

**Problem Tanımı:**  
Bir market kasasını modelleyiniz:
1. `while` döngüsü ile kasiyerden satılan ürünlerin fiyatları tek tek alınsın. Kasiyer `0` girdiğinde ürün girişi sonlandırılsın ve toplam sepet tutarı gösterilsin.
2. Müşterinin verdiği nakit para tutarı alınsın. Verilen para toplam tutardan az ise geçerli para verilene kadar `do-while` ile tekrar sorulsun.
3. **Para Üstü Dağıtımı (`while` ile):**
   - Müşteriye iade edilecek para üstü; 200, 100, 50, 20, 10, 5 ve 1 TL'lik madeni/banknotlar arasından **mümkün olan en az adetle** verilmelidir.
   - Her banknot için `while (kalanParaUstu >= banknot)` döngüsü ile kaç adet verildiğini hesaplayıp listeleyiniz.

---

### 📋 Ödev 4: İkiye Bölerek Karekök Yaklaşımı (Babil Yöntemi)
**Dosya Adı:** `KarekokYaklasimi.java`

**Problem Tanımı:**  
Kullanıcının girdiği pozitif bir sayının karekökünü `Math.sqrt()` kullanmadan, antik **Babil / Newton-Raphson Yaklaşımı** ile hesaplayınız:

**Formül:**  
- Başlangıç tahmini: `tahmin = sayi / 2.0;`
- Bir sonraki tahmin: `sonrakiTahmin = (tahmin + (sayi / tahmin)) / 2.0;`
- İki tahmin arasındaki mutlak fark çok küçük bir hassasiyetten (`0.00001`) büyük olduğu sürece `while (Math.abs(tahmin - sonrakiTahmin) > 0.00001)` döngüyü sürdürünüz.
- Döngü bittiğinde bulunan yaklaşık karekökü ve kaç iterasyonda sonuca ulaşıldığını ekrana basınız.
