# 2. Java'da Fonksiyonlar: Geri Dönüş Değerleri (`return`) Kılavuzu

Bu rehber; Java programlama dilinde bir metodun ürettiği sonuçları çağıran noktaya iletmesini sağlayan **Geri Dönüş Değeri (`return`) mekanizmasını**, `void` ile değer döndüren metotlar arasındaki felsefi ve pratik farkları, **Erken Dönüş (Guard Clauses)** mimarisini, çoklu `return` kurallarını, Java'da birden çok değer döndürme tekniklerini, yaygın derleme hatalarını ve öğrencilerin çözebileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 2_fonksiyonlar**  
> İlgili Önceki Doküman: [1_fonksiyon-giriş.md](./1_fonksiyon-giriş.md)

---

## 📌 İçindekiler
1. [Geri Dönüş Değeri (Return Value) Kavramı ve Mantığı](#1-geri-dönüş-değeri-return-value-kavramı-ve-mantığı)
2. [`return` Anahtar Kelimesinin İki Temel Rolü](#2-return-anahtar-kelimesinin-iki-temel-rolü)
3. [Dönüş Türleri ve Tip Uyumu (Type Compatibility)](#3-dönüş-türleri-ve-tip-uyumu-type-compatibility)
4. [Çoklu `return` İfadeleri ve Erken Çıkış (Guard Clauses)](#4-çoklu-return-ifadeleri-ve-erken-çıkış-guard-clauses)
5. [Döngüler İçinde `return` Kullanımı](#5-döngüler-içinde-return-kullanımı)
6. [Java'da Bir Metottan Birden Fazla Değer Nasıl Döndürülür?](#6-javada-bir-metottan-birden-fazla-değer-nasıl-döndürülür)
7. [En Sık Yapılan Hatalar ve Tuzaklar](#7-en-sık-yapılan-hatalar-ve-tuzaklar)
8. [Kapsamlı Örnek Konsol Uygulaması: `FinansVeKrediDanismani`](#8-kapsamlı-örnek-konsol-uygulaması-finansvekredidanismani)
9. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#9-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
10. [Ödev Projeleri](#10-ödev-projeleri)

---

## 1. Geri Dönüş Değeri (Return Value) Kavramı ve Mantığı

Fonksiyonları bir fabrikaya veya mutfaktaki bir aşçıya benzetebiliriz:
- **Girdiler (Parametreler):** Aşçıya verdiğimiz un, şeker ve yumurtadır.
- **İşlem (Metot Gövdesi):** Aşçının fırını çalıştırıp keki pişirmesidir.
- **Çıktı (Geri Dönüş Değeri):** Aşçının pişen keki tepsiyle bize geri teslim etmesidir.

Eğer aşçı keki pişirip kendisi yerse veya çöpe atarsa, bu **`void`** bir metottur (bize hiçbir somut çıktı vermez, sadece ekrana basar veya bir eylemi tamamlar).  
Ancak keki elimize verirse, biz o keki ister yeriz, ister buzdolabına kaldırırız, istersek başka bir arkadaşımıza hediye ederiz. İşte bu **değer döndüren bir metottur**.

```text
+---------------------------------------------------------------+
|                      BİR METODUN AKIŞI                        |
|                                                               |
|   Argümanlar (Girdiler) ---> [ METOT GÖVDESİ ] ---> return    |
|   (Örn: 5 ve 10)             (Toplama İşlemi)      (Sonuç: 15)|
+---------------------------------------------------------------+
```

### 💻 Metottan Dönen Değerin 3 Farklı Kullanım Şekli:

```java
public class DonusDegeriKullanimi {

    public static int topla(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        // 1. Bir değişkene kaydedebiliriz (En yaygın kullanım):
        int toplamSonucu = topla(20, 30);
        System.out.println("Kayıt Edilen Değer: " + toplamSonucu);

        // 2. Doğrudan konsola veya ekrana yazdırabiliriz:
        System.out.println("Doğrudan Yazdırma: " + topla(5, 7));

        // 3. Başka bir ifadenin veya metodun içine parametre olarak gönderebiliriz:
        // İç içe çağrı (Nested invocation): topla(10, 15) önce 25 üretir, sonra topla(25, 50) çalışır.
        int buyukToplam = topla(topla(10, 15), 50);
        System.out.println("Zincirleme Metot Çağrısı: " + buyukToplam); // 75
    }
}
```

---

## 2. `return` Anahtar Kelimesinin İki Temel Rolü

`return` ifadesi Java'da yalnızca veri taşımakla kalmaz, aynı zamanda programın kontrol akışını yöneten güçlü bir komuttur:

### 1. Rolü: Değer İletme (Data Hand-off)
Metot içerisinde hesaplanan veya elde edilen veriyi çağıran satıra teslim eder:
```java
return sonuc;
```

### 2. Rolü: Metodu Anında Sonlandırma (Control-Flow Exit)
`return` komutu hangi satırda çalışırsa çalışsın, altındaki satırlara bakılmaksızın metot **o milisaniyede derhal biter** ve akış çağrıldığı yere geri döner.

#### 💡 `void` Metotlarda `return;` Kullanımı:
Dönüş tipi `void` olan metotlar geriye bir değer veremez (`return 5;` yazamazsınız). Ancak boş bir `return;` yazarak **metottan erken çıkış** yapabilirsiniz:

```java
public static void kullaniciKaydet(String kullaniciAdi, int yas) {
    // Yaş 18'den küçükse işlemi iptal et ve metottan hemen çık:
    if (yas < 18) {
        System.out.println("❌ Hata: 18 yaşından küçükler sisteme kayıt olamaz!");
        return; // Metodu burada sonlandır! Alttaki kayıt kodları ÇALIŞMAZ.
    }

    // Yalnızca 18 yaş ve üzeri bu satıra ulaşabilir:
    System.out.println("✅ Kullanıcı veritabanına başarıyla kaydedildi: " + kullaniciAdi);
}
```

---

## 3. Dönüş Türleri ve Tip Uyumu (Type Compatibility)

Bir metodun `return` ettiği değerin veri türü ile metodun imzasında belirtilen **Dönüş Türü (Return Type)** arasında tam bir uyum olmak zorundadır.

### 🔄 Otomatik Genişletme (Implicit Widening):
Küçük veri türleri, daha büyük bir türe dönen metotta sorunsuz şekilde `return` edilebilir:

```java
// Metot 'double' döndürüyor, içeride 'int' return ediyoruz:
public static double getSayi() {
    int x = 10;
    return x; // Sorunsuz çalışır! Java 10'u otomatik olarak 10.0 yapar.
}
```

### ⚠️ Daraltma (Narrowing) ve Tip Uyuşmazlığı Hatası:
Büyük veri türleri, küçük bir türe dönen metoda doğrudan verilemez; açık tür dönüştürme (*explicit casting*) gerekir:

```java
// ❌ DERLEME HATASI: Incompatible types (double cannot be converted to int)
public static int bolme(double a, double b) {
    return a / b; // HATA! double sonuç int'e sığmaz!
}

// ✅ DOĞRU (Tür Dönüştürme ile):
public static int bolme(double a, double b) {
    return (int)(a / b); // Küsurat atılarak int'e dönüştürülür
}
```

---

## 4. Çoklu `return` İfadeleri ve Erken Çıkış (Guard Clauses)

Bir metot içerisinde koşullara bağlı olarak birden fazla `return` satırı bulunabilir.

### 📌 Hayati Derleyici Kuralı: *Missing Return Statement*
Java derleyicisi metodun tüm yollarını analiz eder. Eğer bir koşulun gerçekleşmemesi durumunda metodun açıkta kalma ihtimali varsa derleme hatası verir:

```java
// ❌ DERLEME HATASI: Missing return statement
// Eğer sayi == 0 ise ne dönecek? Java bunu garanti altına almak ister!
public static String isaretBelirle(int sayi) {
    if (sayi > 0) {
        return "Pozitif";
    } else if (sayi < 0) {
        return "Negatif";
    }
    // HATA: 0 durumunda hiçbir return çalışmıyor!
}

// ✅ DOĞRU:
public static String isaretBelirle(int sayi) {
    if (sayi > 0) {
        return "Pozitif";
    } else if (sayi < 0) {
        return "Negatif";
    } else {
        return "Sıfır"; // Tüm yollar kapatıldı!
    }
}
```

---

### 🛡️ Temiz Kod Yaklaşımı: Guard Clauses (Koruyucu Cümlecikler)

İç içe onlarca `if-else` merdiveni kurmak yerine, geçersiz veya özel durumları metodun en başında kontrol edip `return` ile çıkmak kodu çok daha okunaklı kılar:

```java
// ❌ KÖTÜ VE KARMAŞIK YAKLAŞIM (Piramit Kodu):
public static double indirimliFiyat(double fiyat, boolean kuponVar, boolean uyeMi) {
    double sonuc;
    if (fiyat > 0) {
        if (uyeMi) {
            if (kuponVar) {
                sonuc = fiyat * 0.70;
            } else {
                sonuc = fiyat * 0.85;
            }
        } else {
            sonuc = fiyat;
        }
    } else {
        sonuc = 0;
    }
    return sonuc;
}

// ✅ PROFESYONEL YAKLAŞIM (Guard Clauses / Erken Dönüş):
public static double indirimliFiyatTemiz(double fiyat, boolean kuponVar, boolean uyeMi) {
    if (fiyat <= 0) return 0.0;     // Hatalı durumu hemen savuştur
    if (!uyeMi)     return fiyat;   // Üye değilse indirimsiz dön

    if (kuponVar) {
        return fiyat * 0.70;        // Üye + Kuponlu
    }
    return fiyat * 0.85;            // Üye + Kuponsuz
}
```

---

## 5. Döngüler İçinde `return` Kullanımı

Döngülerin içinde kullanılan `break` komutu yalnızca o döngüyü kırar; metodun kalan kısmı çalışmaya devam eder.  
Oysa döngü içerisinde çağrılan bir **`return`** komutu, **hem döngüyü hem de metodun kendisini anında sonlandırır**.

Bu özellik, arama algoritmalarında aranan eleman bulunduğu anda gereksiz tur atmayı engellemek için idealdir:

```java
public class AramaAlgoritmasi {

    // Dizide aranan elemanın indeksini bulan metot (Bulamazsa -1 döner):
    public static int elemanAra(int[] dizi, int aranan) {
        for (int i = 0; i < dizi.length; i++) {
            if (dizi[i] == aranan) {
                // Eleman bulundu! Kalan elemanlara hiç bakmadan ANINDA indeksi döndür ve çık:
                return i;
            }
        }
        // Döngü baştan sona bitti ve eleman bulunamadıysa buraya gelinir:
        return -1;
    }

    public static void main(String[] args) {
        int[] sayilar = {12, 45, 78, 23, 56, 89};

        int sonuc = elemanAra(sayilar, 23);
        System.out.println("23 sayısının indeksi: " + sonuc); // 3

        int bulunamayan = elemanAra(sayilar, 999);
        System.out.println("999 sayısı bulundu mu?: " + bulunamayan); // -1
    }
}
```

---

## 6. Java'da Bir Metottan Birden Fazla Değer Nasıl Döndürülür?

Python veya Go gibi dillerde bir fonksiyondan `return min, max;` diyerek birden fazla değeri aynı anda döndürebilirsiniz. Ancak **Java'da sözdizimsel olarak bir metot sadece TEK BİR nesne veya değer döndürebilir**.

Peki bir hesaplamanın sonucunda birden fazla veriyi (örneğin hem en küçük hem en büyük sayıyı) geriye göndermek istersek ne yapmalıyız?

### 1. Çözüm: Dizi (Array) Olarak Döndürmek
Aynı türe sahip birden fazla sonucu bir diziye koyup o diziyi tek bir paket olarak döndürürüz:

```java
public static int[] minVeMaxBul(int a, int b, int c) {
    int enKucuk = Math.min(a, Math.min(b, c));
    int enBuyuk = Math.max(a, Math.max(b, c));

    // 2 elemanlı yeni bir dizi oluşturup tek bir referans olarak return ediyoruz:
    return new int[] { enKucuk, enBuyuk };
}
```

---

### 2. Çözüm: Özel Bir Sınıf (Class) veya `record` (Java 14+) ile Döndürmek
Farklı türdeki verileri (örneğin bir `boolean basariliMi` ile bir `String mesaj` ve `double kalanBakiye`) birlikte döndürmek için özel bir veri taşıyıcı sınıf kullanılır:

```java
// Sonuçları paketleyen yardımcı sınıf:
class IslemSonucu {
    boolean basarili;
    String mesaj;
    double bakiye;

    public IslemSonucu(boolean basarili, String mesaj, double bakiye) {
        this.basarili = basarili;
        this.mesaj = mesaj;
        this.bakiye = bakiye;
    }
}

public class BankaServisi {
    public static IslemSonucu paraCek(double mevcutBakiye, double cekilecekTutar) {
        if (cekilecekTutar > mevcutBakiye) {
            return new IslemSonucu(false, "Yetersiz bakiye!", mevcutBakiye);
        }
        double yeniBakiye = mevcutBakiye - cekilecekTutar;
        return new IslemSonucu(true, "Para çekme başarılı.", yeniBakiye);
    }
}
```

---

## 7. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: `return` Satırından Sonra Kod Yazmak (*Unreachable Code*)

```java
public static int hesapla(int x) {
    return x * 2;
    x = x + 1; // ❌ DERLEME HATASI: Unreachable code! Buraya asla ulaşılamaz!
}
```

---

### 2. Tuzak: Dönen Değeri Çağıran Yerde Havada Bırakmak
Öğrenciler sıklıkla bir metodun değer döndürmesini ekrana yazdırmakla karıştırırlar:

```java
public static int topla(int a, int b) {
    return a + b;
}

public static void main(String[] args) {
    // ⚠️ MANTIK HATASI: Metot çalışır, 15 değerini üretir ama sonuç hiçbir yere
    // atanmadığı veya System.out.println'e verilmediği için kaybolur!
    topla(5, 10); 

    // ✅ DOĞRUSU:
    int sonuc = topla(5, 10);
    System.out.println("Sonuç: " + sonuc);
}
```

---

### 3. Tuzak: `void` Metotta Değer Döndürmeye Çalışmak

```java
public static void mesajYaz() {
    System.out.println("Merhaba");
    return 100; // ❌ DERLEME HATASI: Cannot return a value from a method with void result type
}
```

---

### 4. Tuzak: Döngü İçinde `return` Unutulursa Döngü Sonrasındaki Dönüşü İhmal Etmek

```java
// ❌ DERLEME HATASI: Missing return statement!
// Eğer dizi boşsa döngüye hiç girilmez ve metot geriye ne döndüreceğini bilemez!
public static int ilkPozitifiBul(int[] sayilar) {
    for (int sayi : sayilar) {
        if (sayi > 0) return sayi;
    }
    // HATA: Döngü biterse ne dönecek?
}

// ✅ DOĞRU:
public static int ilkPozitifiBul(int[] sayilar) {
    for (int sayi : sayilar) {
        if (sayi > 0) return sayi;
    }
    return -1; // Pozitif bulunamazsa varsayılan dönüş
}
```

---

## 8. Kapsamlı Örnek Konsol Uygulaması: `FinansVeKrediDanismani`

Aşağıdaki program; farklı veri tipleri (`double`, `String`, `boolean`, `int[]`) döndüren, birden çok `return` ve koruyucu cümlecikler (*guard clauses*) kullanan, bankacılık kredi skorlama ve taksit hesaplama sistemini modelleyen eksiksiz bir konsol uygulamasıdır:

```java
import java.util.Scanner;

public class FinansVeKrediDanismani {

    // 1. Kredi Uygunluğunu Test Eden Metot (boolean döner - Guard Clauses kullanımı)
    public static boolean krediUygunMu(int yas, double aylikGelir, int krediSkoru) {
        if (yas < 18 || yas > 70) {
            return false; // Yaş kriteri uymuyor
        }
        if (aylikGelir < 15000.0) {
            return false; // Asgari gelir yetersiz
        }
        if (krediSkoru < 1200) {
            return false; // Kredi notu riskli
        }
        return true; // Tüm şartları sağlıyor
    }

    // 2. Kredi Notuna Göre Risk Kategorisi Döndüren Metot (String döner)
    public static String riskKategorisiBelirle(int krediSkoru) {
        if (krediSkoru >= 1700) {
            return "Çok İyi (A+) - En Düşük Faiz Oranı";
        } else if (krediSkoru >= 1400) {
            return "İyi (B) - Standart Faiz Oranı";
        } else if (krediSkoru >= 1200) {
            return "Orta (C) - Yüksek Faiz Oranı";
        } else {
            return "Yüksek Riskli (D) - Kredi Verilemez";
        }
    }

    // 3. Aylık Taksit Tutarını Hesaplayan Metot (double döner)
    public static double aylikTaksitHesapla(double anaPara, double yillikFaizOrani, int vadeAy) {
        if (anaPara <= 0 || vadeAy <= 0) {
            return 0.0;
        }
        double toplamFaiz = anaPara * (yillikFaizOrani / 100.0) * (vadeAy / 12.0);
        double toplamGeriOdeme = anaPara + toplamFaiz;
        return toplamGeriOdeme / vadeAy;
    }

    // 4. Ödeme Planı Özeti Döndüren Metot (Birden fazla değeri int[] olarak döndürür)
    // [0]: Toplam Ödenecek Tutar (TL), [1]: Toplam Faiz Tutarı (TL)
    public static int[] odemeOzetiPaketi(double anaPara, double yillikFaizOrani, int vadeAy) {
        double toplamFaiz = anaPara * (yillikFaizOrani / 100.0) * (vadeAy / 12.0);
        double toplamGeriOdeme = anaPara + toplamFaiz;

        return new int[] { (int)toplamGeriOdeme, (int)toplamFaiz };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("     KTÜ BANK DİJİTAL KREDİ DANIŞMANI     ");
        System.out.println("==========================================");

        System.out.print("Yaşınızı giriniz: ");
        int yas = scanner.nextInt();

        System.out.print("Aylık net gelirinizi giriniz (TL): ");
        double gelir = scanner.nextDouble();

        System.out.print("Kredi notunuzu giriniz (0 - 1900): ");
        int skor = scanner.nextInt();

        // 1. Durum: Uygunluk kontrolü (boolean return)
        boolean onayDurumu = krediUygunMu(yas, gelir, skor);

        System.out.println("\n---------------- DEĞERLENDİRME SONUCU ----------------");
        System.out.println("Risk Grubu : " + riskKategorisiBelirle(skor));

        if (!onayDurumu) {
            System.out.println("❌ Üzgünüz, kredi başvuru kriterlerini sağlayamıyorsunuz.");
            scanner.close();
            return; // main metodunu güvenle erken sonlandır!
        }

        System.out.println("✅ Tebrikler! Ön onaylı kredi başvurunuz kabul edildi.");

        // Kredi Hesaplama Aşaması
        double talepKredi = 120000.0;
        double faiz = 24.0; // Yıllık %24
        int vade = 12;      // 12 Ay

        double aylikTaksit = aylikTaksitHesapla(talepKredi, faiz, vade);
        int[] ozetPaket = odemeOzetiPaketi(talepKredi, faiz, vade);

        System.out.println("Talep Edilen Kredi : " + talepKredi + " TL");
        System.out.println("Vade Süresi        : " + vade + " Ay");
        System.out.printf("Aylık Taksit Tutarı: %.2f TL\n", aylikTaksit);
        System.out.println("Toplam Faiz Yükü   : " + ozetPaket[1] + " TL");
        System.out.println("Toplam Geri Ödeme  : " + ozetPaket[0] + " TL");
        System.out.println("======================================================");

        scanner.close();
    }
}
```

---

## 9. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Erken Return Takibi)
Aşağıdaki metodun `islem(8)` çağrısı için konsola ne yazdıracağını ve geriye ne döndüreceğini adım adım takip ediniz:

```java
public class TracingTest {
    public static int islem(int n) {
        System.out.println("A: n=" + n);
        if (n % 2 == 0) {
            if (n > 5) {
                System.out.println("B: n=" + n);
                return n * 2;
            }
            System.out.println("C: n=" + n);
            return n + 10;
        }
        System.out.println("D: n=" + n);
        return 0;
    }

    public static void main(String[] args) {
        int sonuc = islem(8);
        System.out.println("Sonuç: " + sonuc);
    }
}
```

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında derleyicinin hata vermesine neden olan **3 sorunu** bulunuz:

```java
public class HataBul {
    public static int mutlakDeger(int sayi) {
        if (sayi < 0) {
            return -sayi;
        }
        // Hata 1 nerede?
    }

    public static void bilgiBas(String ad) {
        System.out.println("Kullanıcı: " + ad);
        return ad; // Hata 2 nerede?
        System.out.println("Bitti"); // Hata 3 nerede?
    }
}
```

---

### 🔢 Alıştırma 3: En Büyük Sayıyı Döndüren Metot
Üç tamsayı (`int a, int b, int c`) alarak içlerinden en büyüğünü `Math.max()` kullanmadan, yalnızca `if-return` kalıplarıyla geriye döndüren `public static int enBuyuk(int a, int b, int c)` metodunu kodlayınız.

---

### 🔁 Alıştırma 4: Bir Sayının Tersini Tamsayı Olarak Döndürme
Girdi olarak pozitif bir tamsayı alan (Örn: `12345`) ve bu sayının tersten yazılışını (`54321`) yine bir `int` olarak `return` eden metodu yazınız.

---

## 10. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Fonksiyonların geriye değer döndürme prensiplerini ve guard clauses mantığını eksiksiz uygulayınız.

---

### 📋 Ödev 1: Akıllı Vergi ve Gelir Dilimi Hesaplayıcı
**Dosya Adı:** `VergiHesaplayici.java`

**Problem Tanımı:**  
Gelir İdaresi Başkanlığı'nın yıllık kümülatif gelir vergisi tarifesini modelleyiniz.
1. `public static double vergiOraniBelirle(double yillikGelir)`:
   - Gelir < 110.000 TL ise: `%15` döndürsün (`0.15`).
   - 110.000 - 230.000 TL arası: `%20` döndürsün (`0.20`).
   - 230.000 - 870.000 TL arası: `%27` döndürsün (`0.27`).
   - 870.000 TL üzeri: `%35` döndürsün (`0.35`).
2. `public static double netMaasHesapla(double brutMaas)`:
   - Önce vergi oranını bulmak için yukarıdaki metodu çağırsın, ardından kesintiyi düşüp net maaşı `double` olarak döndürsün.
3. `public static boolean vergiMuafiyetiVarMi(double gelir, boolean engelliRaporu)`:
   - Belirli şartlara göre vergi muafiyeti olup olmadığını `boolean` döndürsün.
4. `main` içinde kullanıcıdan brüt maaş alıp bu metotlarla net maaş dökümünü hesaplayınız.

---

### 📋 Ödev 2: İstatistik Paketi (Dizi ile Çoklu Değer Döndürme)
**Dosya Adı:** `IstatistikPaketi.java`

**Problem Tanımı:**  
Kullanıcının girdiği 3 tamsayıyı analiz eden yardımcı metotlar geliştiriniz:
1. `public static int[] analizPaketi(int a, int b, int c)`:
   - Bu metot geriye **3 elemanlı bir `int[]` dizisi** döndürmelidir:
     - `dizi[0]`: En küçük sayı
     - `dizi[1]`: En büyük sayı
     - `dizi[2]`: Sayıların toplamı
2. `public static double aritmetikOrtalama(int a, int b, int c)`:
   - Sayıların ortalamasını `double` olarak döndürsün.
3. `public static boolean hepsiEsitMi(int a, int b, int c)`:
   - Üç sayı da birbirine eşitse `true`, değilse `false` döndürsün.

---

### 📋 Ödev 3: Güvenli E-Posta ve Parola Doğrulama Servisi
**Dosya Adı:** `DogrulamaServisi.java`

**Problem Tanımı:**  
Kullanıcı kayıt ekranı için `boolean` ve `String` döndüren koruyucu cümlecikli (*guard clause*) metotlar tasarlayınız:
1. `public static boolean epostaGecerliMi(String email)`:
   - Eğer `email` `null` ise veya uzunluğu 5'ten küçükse `false` dönsün.
   - İçinde hem `'@'` hem de `'.'` karakteri bulunmalı ve `'@'` karakteri `'.'` karakterinden önce gelmelidir. Şartlar sağlanıyorsa `true` dönsün.
2. `public static String parolaHataMesaji(String sifre)`:
   - Şifre < 8 karakter ise: `"Şifre en az 8 karakter olmalıdır."` döndürsün.
   - Şifre boşluk (`" "`) içeriyorsa: `"Şifrede boşluk bulunamaz."` döndürsün.
   - Herhangi bir hata yoksa: `"GEÇERLİ"` döndürsün.

---

### 📋 Ödev 4: Tarih ve Gün Farkı Hesaplama Motoru
**Dosya Adı:** `TarihHesaplayici.java`

**Problem Tanımı:**  
1. `public static boolean artikYilMi(int yil)`:
   - Yıl artık yıl ise `true`, değilse `false` dönsün (4'e bölünüp 100'e bölünmeyen veya 400'e bölünen).
2. `public static int ayinGunSayisi(int ay, int yil)`:
   - Verilen ayın kaç gün çektiğini döndürsün (Şubat için `artikYilMi(yil)` metodunu çağırmalıdır!).
3. `public static boolean tarihGecerliMi(int gun, int ay, int yil)`:
   - Yıl > 0, Ay 1-12 arası ve Gün 1 ile `ayinGunSayisi(ay, yil)` arasında ise `true`, değilse `false` döndürsün.
4. `main` metodunda kullanıcının girdiği bir doğum tarihinin takvimde gerçekten var olup olmadığını bu metotları birbirine bağlayarak test ediniz.
