# 9. Java'da Çok Biçimlilik (Polymorphism), Metot Ezme (Overriding) ve Tip Dönüşümleri

Bu doküman; Nesne Yönelimli Programlamanın (OOP) en can alıcı ve zarif ilkesi olan **Çok Biçimlilik (Polymorphism)** kavramını, **Derleme Zamanı (Statik)** ve **Çalışma Zamanı (Dinamik)** polimorfizm farkını, **Metot Ezme (Method Overriding)** kurallarını, **`@Override`** anotasyonunu, **Upcasting (Yukarı Dönüşüm)** ve **Downcasting (Aşağı Dönüşüm)** işlemlerini, **`ClassCastException`** hatasından korunmayı ve polimorfik dizilerin mimari gücünü ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Kalıtım ve Miras Alma (Inheritance) (8_kalıtım.md)](./8_kalıtım.md)
> - 🔜 [Sonraki Bölüm: Soyut Sınıflar (Abstract Classes) (A_soyut-sınıflar.md)](./A_soyut-sınıflar.md)

---

## 📌 İçindekiler
1. [Çok Biçimlilik (Polymorphism) Nedir?](#1-çok-biçimlilik-polymorphism-nedir)
   - [Yunanca Kökeni: "Tek Arayüz, Çoklu Biçim"](#yunanca-kökeni-tek-arayüz-çoklu-biçim)
   - [Gerçek Dünya Analojisi: Evrensel Kumanda ve "Oynat / Play" Tuşu](#gerçek-dünya-analojisi-evrensel-kumanda-ve-oynat--play-tuşu)
   - [Neden OOP'nin Kalbidir? (Gevşek Bağımlılık & Open/Closed Prensibi)](#neden-oopnin-kalbidir)
2. [İki Tür Çok Biçimlilik](#2-iki-tür-çok-biçimlilik)
   - [1. Derleme Zamanı (Statik) Çok Biçimlilik - Overloading](#1-derleme-zamanı-statik-çok-biçimlilik-compile-time)
   - [2. Çalışma Zamanı (Dinamik) Çok Biçimlilik - Overriding](#2-çalışma-zamanı-dinamik-çok-biçimlilik-runtime)
   - [Overloading vs. Overriding Karşılaştırma Tablosu](#overloading-vs-overriding-karşılaştırma-tablosu)
3. [Metot Ezme (Method Overriding) ve `@Override` Anotasyonu](#3-metot-ezme-method-overriding-ve-override-anotasyonu)
   - [`@Override` Anotasyonu Neden Hayatidir?](#override-anotasyonu-neden-hayatidir)
   - [Metot Ezmenin 4 Kesin Kuralı](#metot-ezmenin-4-kesin-kuralı)
   - [Hangi Metotlar Asla Ezilemez? (`private`, `static`, `final`)](#hangi-metotlar-asla-ezilemez)
4. [Nesne Tip Dönüşümleri: Upcasting ve Downcasting](#4-nesne-tip-dönüşümleri-upcasting-ve-downcasting)
   - [Upcasting (Yukarı Dönüşüm - Otomatik & Güvenli)](#1-upcasting-yukarı-dönüşüm)
   - [Downcasting (Aşağı Dönüşüm - Manuel & Tehlikeli)](#2-downcasting-aşağı-dönüşüm)
   - [`ClassCastException` Riski ve `instanceof` ile Korunma](#classcastexception-riski-ve-instanceof-ile-korunma)
   - [Modern Java (Java 14+) ile Desen Eşleştirme (Pattern Matching)](#modern-java-ile-desen-eşleştirme-pattern-matching)
5. [Çok Biçimliliğin Gerçek Gücü: Polimorfik Diziler ve Metot Parametreleri](#5-çok-biçimliliğin-gerçek-gücü-polimorfik-diziler-ve-metot-parametreleri)
   - [Üst Sınıf Referansı ile Farklı Nesneleri Tek Bir Dizide Toplama](#üst-sınıf-referansı-ile-farklı-nesneleri-tek-bir-dizide-toplama)
   - [Sisteme Yeni Bir Sınıf Eklendiğinde Mevcut Koda Dokunmama Lüksü](#sisteme-yeni-bir-sınıf-eklendiğinde-mevcut-koda-dokunmama-lüksü)
6. [Adım Adım Çalıştırılabilir Kod Örnekleri](#6-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Hayvanlar Alemi ve Polimorfik Dizi (`Hayvan.java` & `VeterinerTest.java`)](#örnek-1-hayvanlar-alemi-ve-polimorfik-dizi)
   - [Örnek 2: Çalışan ve Dinamik Maaş/Bonus Hesabı (`Calisan.java` & `BordroTest.java`)](#örnek-2-çalışan-ve-dinamik-maaşbonus-hesabı)
   - [Örnek 3: E-Ticaret Ödeme Sistemleri (`OdemeYontemi.java` & `KasaTest.java`)](#örnek-3-e-ticaret-ödeme-sistemleri)
7. [Sık Yapılan Hatalar ve Önemli Kurallar](#7-sık-yapılan-hatalar-ve-önemli-kurallar)
8. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
9. [Ödev Konuları ve Mini Projeler](#9-ödev-konuları-ve-mini-projeler)

---

## 1. Çok Biçimlilik (Polymorphism) Nedir?

### Yunanca Kökeni: "Tek Arayüz, Çoklu Biçim"
Polymorphism kelimesi Antik Yunanca iki kelimenin birleşiminden gelir:
- **Poly:** Çok, birden fazla
- **Morph:** Biçim, form, şekil

> 💡 **Nesne Yönelimli Programlamadaki Tanımı:**
> Çok biçimlilik; **aynı isimdeki tek bir metodun veya arayüzün, farklı nesneler üzerinde çalıştırıldığında o nesneye özgü farklı davranışlar sergilemesi** yeteneğidir.

---

### Gerçek Dünya Analojisi: Evrensel Kumanda ve "Oynat / Play" Tuşu

Elinizde tek bir evrensel kumanda ve üzerinde tek bir **"OYNAT (Play)"** tuşu olduğunu düşünün:
- Kumandayı **Müzik Çalara** doğrultup "Play"e basarsanız $\rightarrow$ Şarkı çalar 🎵
- Kumandayı **Video Oynatıcıya** doğrultup "Play"e basarsanız $\rightarrow$ Film başlar 🎬
- Kumandayı **Oyun Konsoluna** doğrultup "Play"e basarsanız $\rightarrow$ Oyun yüklenir 🎮

Tuş aynı tuştur (`oynat()`), ancak tetiği çeken nesne ne ise o nesnenin kendi doğasına göre bir tepki verilir.

```
                  +--------------------------------+
                  |  ÜST REFERANS: Hayvan (Animal) |
                  |         sesCikar()             |
                  +--------------------------------+
                                  |
            +---------------------+---------------------+
            |                     |                     |
            v                     v                     v
     [ Kopek Nesnesi ]     [ Kedi Nesnesi ]       [ Inek Nesnesi ]
       "Hav hav!"             "Miyav!"               "Mööö!"
```

---

### Neden OOP'nin Kalbidir?

Eğer çok biçimlilik olmasaydı, her hayvan için ayrı bir metot ve devasa `if-else` blokları yazmak zorunda kalırdık:
```java
// ❌ POLİMORFİZM OLMADAN (Kötü Tasarım):
if (h instanceof Kopek) {
    kopekHavlat((Kopek) h);
} else if (h instanceof Kedi) {
    kediMiyavlat((Kedi) h);
} // Yarın 20 yeni hayvan geldiğinde 20 yeni if yazmak gerekir!
```

Çok biçimlilik sayesinde tek satır yeterlidir:
```java
// ✅ POLİMORFİZM İLE (Mükemmel Tasarım):
h.sesCikar(); // Java nesnenin kim olduğunu arka planda bilir ve doğru sesi çıkarır!
```

---

## 2. İki Tür Çok Biçimlilik

Java'da çok biçimlilik gerçekleştiği zamana göre ikiye ayrılır:

```
                      ÇOK BİÇİMLİLİK (POLYMORPHISM)
                                   |
            +----------------------+----------------------+
            |                                             |
            v                                             v
1. DERLEME ZAMANI (Compile-Time)              2. ÇALIŞMA ZAMANI (Runtime)
   • Statik Polimorfizm                          • Dinamik Polimorfizm
   • Metot Aşırı Yükleme (Overloading)           • Metot Ezme (Overriding)
   • Erken Bağlama (Early Binding)               • Geç Bağlama (Dynamic Dispatch)
```

---

### 1. Derleme Zamanı (Statik) Çok Biçimlilik (Compile-Time)
Aynı sınıf içinde **aynı isimde fakat farklı parametre listelerine sahip metotlar** tanımlanmasıdır (**Method Overloading**).
Hangi metodun çalışacağı program henüz derlenirken (`javac` aşamasında) parametrelerin türüne ve sayısına bakılarak kesinleştirilir (*Early Binding*).

```java
public class Hesaplayici {
    public int topla(int a, int b) { return a + b; }
    public double topla(double a, double b) { return a + b; }
}
```

---

### 2. Çalışma Zamanı (Dinamik) Çok Biçimlilik (Runtime)
Bir alt sınıfın, üst sınıftan miras aldığı bir metodu **kendi ihtiyacına göre yeniden gövdeleyip ezmesidir (Method Overriding)**.
Hangi metodun çalışacağına derleme anında değil, program çalışırken (**çalışma anında - Runtime**) Heap bellekteki gerçek nesnenin türüne bakılarak karar verilir (*Late Binding / Dynamic Method Dispatch*).

---

### Overloading vs. Overriding Karşılaştırma Tablosu

| Kriter | Metot Aşırı Yükleme (Overloading) | Metot Ezme (Overriding) |
| :--- | :--- | :--- |
| **Nerede Gerçekleşir?** | Aynı sınıfın içinde | Üst sınıf ve Alt sınıf arasında (Kalıtım şarttır) |
| **Metot İsmi** | Aynı olmak zorundadır | Aynı olmak zorundadır |
| **Parametre Listesi** | **Mutlaka FARKLI** olmalıdır | **BİREBİR AYNI** olmalıdır |
| **Dönüş Tipi** | Farklı olabilir | Aynı veya alt türü (*Covariant*) olmalıdır |
| **Karar Zamanı** | **Derleme anı (Compile-time)** | **Çalışma anı (Runtime)** |
| **Anotasyon** | Gerek yoktur | `@Override` yazılması önerilir |

---

## 3. Metot Ezme (Method Overriding) ve `@Override` Anotasyonu

Bir alt sınıf, üst sınıftan gelen bir metodun davranışını beğenmeyip kendi özelleştirilmiş kodunu yazmak istediğinde metodu ezer (*override eder*).

### `@Override` Anotasyonu Neden Hayatidir?

`@Override` yazmak teknik olarak zorunlu değildir; fakat **yazılması hayati bir en iyi pratiktir (best practice)**:

1. **Yazım Hatalarını Önler:** Eğer üst sınıftaki metot `sesCikar()` iken siz yanlışlıkla `sescikar()` (küçük c ile) yazarsanız; `@Override` koymazsanız Java bunu yeni bir metot sanar ve sessizce derler. Ancak başına `@Override` koyarsanız derleyici sizi anında uyarır: `"Method does not override method from its superclass"`.
2. **Kod Okunabilirliği:** Kodu okuyan diğer yazılımcılara bu metodun üst sınıftan miras alınıp ezildiğini açıkça ilan eder.

---

### Metot Ezmenin 4 Kesin Kuralı

1. **Metot İmzası Aynı Olmalıdır:** Metot adı, parametrelerin sayısı, türü ve sırası üst sınıftakiyle birebir aynı olmak zorundadır.
2. **Dönüş Tipi Uyumlu Olmalıdır:** Dönüş tipi ya üst sınıftakiyle aynı olmalı ya da onun bir alt sınıfı olmalıdır (*Covariant Return Type*).
3. **Erişim Belirleyicisi Daraltılamaz:** Üst sınıfta `protected` olan bir metot alt sınıfta `public` yapılabilir (genişletilebilir); ancak üst sınıfta `public` olan bir metot alt sınıfta `protected` veya `private` **YAPILAMAZ**!
4. **Kontrollü İstisna (Exception) Kuralı:** Alt sınıf metodu, üst sınıftakinden daha geniş kapsamlı yeni bir denetimli istisna (*checked exception*) fırlatamaz.

---

### Hangi Metotlar Asla Ezilemez?

| Metot Türü | Ezilebilir mi? | Neden? |
| :--- | :---: | :--- |
| **`private` Metotlar** | ❌ Hayır | Alt sınıflar `private` metotları göremez, görmediği şeyi ezemez. |
| **`static` Metotlar** | ❌ Hayır | Statik metotlar nesneye değil sınıfa aittir. Alt sınıfta aynı isimle yazılırsa ezilmiş olmaz, gizlenmiş (*Method Hiding*) olur. |
| **`final` Metotlar** | ❌ Hayır | `final` kelimesi açıkça *"Bu metot son sürümdür, alt sınıflar değiştiremez"* demektir. |

---

## 4. Nesne Tip Dönüşümleri: Upcasting ve Downcasting

Java'da kalıtım ilişkisi bulunan nesneler birbirlerinin türüne dönüştürülebilir.

```
             [ Hayvan (Üst Sınıf) ]
                  ^          |
     Upcasting    |          |   Downcasting
    (Otomatik &   |          |    (Manuel &
      Güvenli)    |          v     Riskli)
             [ Kopek (Alt Sınıf) ]
```

---

### 1. Upcasting (Yukarı Dönüşüm)
Alt sınıf türündeki bir nesneyi **üst sınıf referansına** atamaktır. 
- Her köpek zaten bir hayvandır! Bu yüzden **tamamen güvenlidir ve otomatik gerçekleşir** (Cast operatörü gerekmez):

```java
Kopek k = new Kopek();
Hayvan h = k; // ✅ Upcasting (Otomatik)

// Veya tek satırda:
Hayvan h = new Kopek(); // ✅ Upcasting!
```

> ⚠️ **Kritik Kısıtlama:** Üst sınıf referansı (`h`), alt sınıfın **sadece üst sınıfta da tanımlı olan metotlarını** görebilir! Eğer `Kopek` sınıfında fazladan `kemikGetir()` metodu varsa, `h.kemikGetir()` **çağrılamaz** (derleme hatası verir). Çünkü derleyici `h`'yi genel bir `Hayvan` olarak görür.

---

### 2. Downcasting (Aşağı Dönüşüm)
Üst sınıf referansı olarak tutulan bir nesneyi tekrar **kendi asıl alt sınıf türüne** geri dönüştürmektir.
- Köpek olan bir hayvanın köpek olduğunu açıkça belirtmektir.
- **Otomatik yapılmaz, parantez içinde açıkça cast `(Kopek)` yazılmalıdır:**

```java
Hayvan h = new Kopek(); // Upcasting ile tutuluyor

// h.kemikGetir(); // Derleme hatası!

Kopek k = (Kopek) h; // ✅ Downcasting (Açık cast)
k.kemikGetir();      // Artık köpeğe özel metot çağrılabilir!
```

---

### `ClassCastException` Riski ve `instanceof` ile Korunma

Eğer Heap bellekte gerçekte bir **Kedi** olan nesneyi zorla **Köpek** yapmaya çalışırsanız, programınız çalışma anında patlar:

```java
Hayvan h = new Kedi(); // Gerçekte Kedi!
Kopek k = (Kopek) h;   // ❌ RUNTIME ERROR: ClassCastException (Kedi köpeğe dönüştürülemez!)
```

✅ **Çözüm (`instanceof` Kontrolü):** Downcasting yapmadan önce nesnenin gerçekten o türde olup olmadığı kontrol edilmelidir:

```java
if (h instanceof Kopek) {
    Kopek k = (Kopek) h;
    k.kemikGetir();
} else {
    System.out.println("Bu nesne bir köpek değil!");
}
```

---

### Modern Java ile Desen Eşleştirme (Pattern Matching)

Java 14 ve sonrasında `instanceof` ile türü kontrol ederken aynı anda yeni değişkene otomatik atama yapılabilir (*Pattern Matching for instanceof*):

```java
// ✅ Modern Java (Java 14+):
if (h instanceof Kopek k) {
    k.kemikGetir(); // Doğrudan k değişkeni kullanılabilir, ekstra cast satırına gerek kalmaz!
}
```

---

## 5. Çok Biçimliliğin Gerçek Gücü: Polimorfik Diziler ve Metot Parametreleri

Çok biçimliliğin yazılım mimarisine kazandırdığı asıl mucize şudur:

### 1. Üst Sınıf Referansı ile Farklı Nesneleri Tek Bir Dizide Toplama
Tek bir dizi içinde Köpek, Kedi, Kuş, Aslan nesnelerini bir arada saklayabilirsiniz:

```java
Hayvan[] barinak = {
    new Kopek(),
    new Kedi(),
    new Inek()
};

// Hepsini tek döngüde konuşturabiliriz:
for (Hayvan h : barinak) {
    h.sesCikar(); // Her nesne kendi sesini çıkarır!
}
```

---

### 2. Sisteme Yeni Bir Sınıf Eklendiğinde Mevcut Koda Dokunmama Lüksü

Diyelim ki sisteminize 6 ay sonra yeni bir hayvan sınıfı eklediniz: `class Kurt extends Hayvan`.
- Mevcut `for` döngünüzü değiştirmeniz gerekir mi? **HAYIR!**
- Hayvanları muayene eden `veterinerMuayeneEt(Hayvan h)` metodunu değiştirmeniz gerekir mi? **HAYIR!**

Yazılım mühendisliğinde buna **Open/Closed Prensibi (Gelişime Açık, Değişime Kapalı)** denir. Polimorfizm, kodunuzu gelecekteki değişikliklere karşı kurşun geçirmez hale getirir.

---

## 6. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Hayvanlar Alemi ve Polimorfik Dizi

#### `Hayvan.java` ve Alt Sınıflar
```java
// Üst Sınıf
class Hayvan {
    protected String isim;

    public Hayvan(String isim) {
        this.isim = isim;
    }

    public void sesCikar() {
        System.out.println(isim + " genel bir hayvan sesi çıkarıyor.");
    }
}

// Alt Sınıf 1: Kopek
class Kopek extends Hayvan {
    public Kopek(String isim) {
        super(isim);
    }

    @Override
    public void sesCikar() {
        System.out.println(isim + " havlıyor: Hav hav! 🐶");
    }

    // Köpeğe özel metot
    public void terlikGetir() {
        System.out.println(isim + " terlikleri getirdi.");
    }
}

// Alt Sınıf 2: Kedi
class Kedi extends Hayvan {
    public Kedi(String isim) {
        super(isim);
    }

    @Override
    public void sesCikar() {
        System.out.println(isim + " miyavlıyor: Miyav miyav! 🐱");
    }
}

// Alt Sınıf 3: Kus
class Kus extends Hayvan {
    public Kus(String isim) {
        super(isim);
    }

    @Override
    public void sesCikar() {
        System.out.println(isim + " ötüyor: Cik cik! 🐦");
    }
}
```

#### `VeterinerTest.java`
```java
public class VeterinerTest {

    // POLİMORFİK METOT: Üst sınıf türünde tek bir parametre alır!
    public static void muayeneEt(Hayvan h) {
        System.out.print("[Muayene Ediliyor] -> ");
        h.sesCikar(); // Dinamik Metot Bağlama (Late Binding)

        // Downcasting ile köpeğe özel yetenek kontrolü:
        if (h instanceof Kopek k) {
            k.terlikGetir();
        }
    }

    public static void main(String[] args) {
        // POLİMORFİK DİZİ: Farklı alt sınıf nesneleri tek dizide!
        Hayvan[] kliniktekiHayvanlar = {
            new Kopek("Karabaş"),
            new Kedi("Pamuk"),
            new Kus("Maviş"),
            new Kopek("Çomar")
        };

        System.out.println("=== KLİNİKTEKİ TÜM HAYVANLARIN SESLERİ ===");
        for (Hayvan h : kliniktekiHayvanlar) {
            muayeneEt(h);
            System.out.println();
        }
    }
}
```

#### Konsol Çıktısı:
```text
=== KLİNİKTEKİ TÜM HAYVANLARIN SESLERİ ===
[Muayene Ediliyor] -> Karabaş havlıyor: Hav hav! 🐶
Karabaş terlikleri getirdi.

[Muayene Ediliyor] -> Pamuk miyavlıyor: Miyav miyav! 🐱

[Muayene Ediliyor] -> Maviş ötüyor: Cik cik! 🐦

[Muayene Ediliyor] -> Çomar havlıyor: Hav hav! 🐶
Çomar terlikleri getirdi.
```

---

### Örnek 2: Çalışan ve Dinamik Maaş/Bonus Hesabı

Her çalışanın maaş ve prim hesaplama mantığının polimorfik olarak ezilmesi:

#### `Calisan.java`, `Mudur.java`, `Yazilimci.java`
```java
// Üst Sınıf
class Calisan {
    protected String ad;
    protected double bazMaas;

    public Calisan(String ad, double bazMaas) {
        this.ad = ad;
        this.bazMaas = bazMaas;
    }

    // Alt sınıflar bu metodu kendi kurallarına göre ezecektir!
    public double maasHesapla() {
        return bazMaas;
    }

    public void bordroYazdir() {
        System.out.printf("%-15s | Net Ödenecek Maaş: %.2f TL\n", ad, maasHesapla());
    }
}

// Alt Sınıf 1: Yazılımcı (Baz maaş + Proje primi)
class Yazilimci extends Calisan {
    private int tamamlananProjeSayisi;

    public Yazilimci(String ad, double bazMaas, int tamamlananProjeSayisi) {
        super(ad, bazMaas);
        this.tamamlananProjeSayisi = tamamlananProjeSayisi;
    }

    @Override
    public double maasHesapla() {
        // Her proje için 5000 TL bonus
        return bazMaas + (tamamlananProjeSayisi * 5000.0);
    }
}

// Alt Sınıf 2: Müdür (Baz maaş + Şirket kâr payı primi)
class Mudur extends Calisan {
    private double sirketKarPayi;

    public Mudur(String ad, double bazMaas, double sirketKarPayi) {
        super(ad, bazMaas);
        this.sirketKarPayi = sirketKarPayi;
    }

    @Override
    public double maasHesapla() {
        return bazMaas + sirketKarPayi;
    }
}
```

#### `BordroTest.java`
```java
public class BordroTest {
    public static void main(String[] args) {
        // Şirketteki tüm çalışanlar tek bir Calisan[] dizisinde toplanır
        Calisan[] personelListesi = {
            new Calisan("Ahmet (Stajyer)", 17002.0),
            new Yazilimci("Canan (Kıdemli Dev)", 65000.0, 3), // 3 proje bonusu
            new Mudur("Selim (Genel Müdür)", 90000.0, 35000.0) // Kâr payı
        };

        System.out.println("=== AYLIK PERSONEL MAAŞ BORDROSU ===");
        double toplamSirketGideri = 0.0;

        for (Calisan c : personelListesi) {
            c.bordroYazdir(); // Hangi nesne ise onun maasHesapla() metodu çağrılır!
            toplamSirketGideri += c.maasHesapla();
        }

        System.out.println("----------------------------------------------");
        System.out.printf("Toplam Maaş Bütçesi: %.2f TL\n", toplamSirketGideri);
    }
}
```

#### Konsol Çıktısı:
```text
=== AYLIK PERSONEL MAAŞ BORDROSU ===
Ahmet (Stajyer) | Net Ödenecek Maaş: 17002,00 TL
Canan (Kıdemli Dev) | Net Ödenecek Maaş: 80000,00 TL
Selim (Genel Müdür) | Net Ödenecek Maaş: 125000,00 TL
----------------------------------------------
Toplam Maaş Bütçesi: 222002,00 TL
```

---

### Örnek 3: E-Ticaret Ödeme Sistemleri

#### `OdemeYontemi.java`, `KrediKarti.java`, `KriptoPara.java`
```java
class OdemeYontemi {
    public void ode(double tutar) {
        System.out.println("Bilinmeyen yöntemle " + tutar + " TL ödendi.");
    }
}

class KrediKarti extends OdemeYontemi {
    private String kartNo;

    public KrediKarti(String kartNo) {
        this.kartNo = kartNo;
    }

    @Override
    public void ode(double tutar) {
        System.out.println(kartNo + " no'lu Kredi Kartından " + tutar + " TL çekildi. (+Komisyon: 0 TL)");
    }
}

class KriptoPara extends OdemeYontemi {
    private String cuzdanAdresi;

    public KriptoPara(String cuzdanAdresi) {
        this.cuzdanAdresi = cuzdanAdresi;
    }

    @Override
    public void ode(double tutar) {
        System.out.println(cuzdanAdresi + " cüzdanından blockchain ağıyla " + tutar + " TL transfer edildi.");
    }
}

public class KasaTest {
    // Alışveriş sepeti ödeme yönteminin ne olduğunu bilmez; sadece ode() der!
    public static void siparisTamamla(OdemeYontemi yontem, double sepetTutari) {
        System.out.println("Ödeme işlemi başlatılıyor...");
        yontem.ode(sepetTutari); // POLİMORFİK ÇAĞRI
        System.out.println("Sipariş onaylandı! Kargonuz hazırlanıyor.\n");
    }

    public static void main(String[] args) {
        siparisTamamla(new KrediKarti("4543-XXXX-XXXX-1234"), 1500.0);
        siparisTamamla(new KriptoPara("0x71C...B29"), 3200.0);
    }
}
```

---

## 7. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `@Override` Yazarken İmzayı Farklı Yapıp Overload Tuzağına Düşmek
```java
public class Hayvan {
    public void beslen(String yemek) { ... }
}

public class Kedi extends Hayvan {
    // ❌ DİKKAT: Parametre türünü int yaptınız!
    // @Override koymazsanız Java bunu yeni bir metot (overload) zanneder.
    // @Override koyarsanız derleyici sizi anında uyarır!
    @Override 
    public void beslen(int miktar) { ... } // HATA: method does not override!
}
```

---

### 2. Üst Sınıf Referansıyla Alt Sınıfın Özel Metodunu Çağırmaya Çalışmak
```java
Hayvan h = new Kopek();
h.terlikGetir(); // ❌ DERLEME HATASI: cannot find symbol method terlikGetir()
```
`h` referansının türü `Hayvan`'dır. Derleyici sadece `Hayvan` sınıfında tanımlı metotları tanır.
✅ **Çözüm:** Önce `instanceof` kontrolü yapıp downcasting yapınız: `((Kopek) h).terlikGetir();`.

---

### 3. `instanceof` Yapmadan Körlemesine Downcasting Yapmak
```java
Hayvan h = new Kedi();
Kopek k = (Kopek) h; // ❌ ÇÖKER: ClassCastException!
```
Her zaman `if (h instanceof Kopek)` kontrolü yapınız.

---

### 4. `static` Metotları Override Etmeye Çalışmak
`static` metotlar nesneye değil sınıfa aittir. Alt sınıfta aynı isimde `static` metot yazarsanız bu ezme (*overriding*) değil, gizleme (*method hiding*) olur. Polimorfizm statik metotlarda çalışmaz!

---

## 8. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Dinamik Metot Bağlama ve Çıktı Tahmini
Aşağıdaki kod çalıştığında konsola tam olarak ne yazdırılır? Sırayı takip ediniz:

```java
class A {
    public void yaz() {
        System.out.print("A-yaz ");
    }
}

class B extends A {
    @Override
    public void yaz() {
        System.out.print("B-yaz ");
    }

    public void ozel() {
        System.out.print("B-ozel ");
    }
}

public class SoruTest {
    public static void main(String[] args) {
        A nesne1 = new B(); // Upcasting
        nesne1.yaz();

        if (nesne1 instanceof B) {
            ((B) nesne1).ozel();
        }
    }
}
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
```text
B-yaz B-ozel 
```
**Açıklama:**
- `nesne1.yaz()` çağrıldığında, `nesne1` referansı `A` türünde olsa da Heap'teki gerçek nesne `B` olduğu için dinamik olarak `B` sınıfındaki ezilmiş `yaz()` çalışır $\rightarrow$ `"B-yaz "`.
- `instanceof` kontrolü `true` döner, `((B) nesne1)` downcasting başarılı olur ve `ozel()` çağrılır $\rightarrow$ `"B-ozel "`.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında neden derleme hatası alınır ve nasıl düzeltilir?

```java
class Arac {
    public void calis() {
        System.out.println("Araç çalıştı.");
    }
}

class Bisiklet extends Arac {
    @Override
    private void calis() { // DİKKAT!
        System.out.println("Pedal çevriliyor.");
    }
}
```

<details>
<summary>🔍 <b>Hata ve Açıklamayı Görmek İçin Tıklayın</b></summary>

**Hata:** `calis()` metodu üst sınıfta `public` iken alt sınıfta `private` yapılmaya çalışılmıştır.
**Kural:** Metot ezilirken erişim belirleyicisi **daraltılamaz** (`attempting to assign weaker access privileges; was public`). Alt sınıftaki metot da en az `public` olmak zorundadır.
</details>

---

### Alıştırma 3: Geometrik Şekiller Çizimi
- Üst Sınıf: `Sekil` (metot: `public void ciz() { System.out.println("Şekil çiziliyor"); }`).
- Alt Sınıflar: `Daire`, `Kare`, `Ucgen` (her biri `ciz()` metodunu kendine göre override etsin).
- 3 farklı şekli tek bir `Sekil[]` dizisine koyup döngü ile ekrana çizdiriniz.

---

## 9. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Müzik Aletleri ve Orkestra Simülasyonu (`OrkestraTest.java`)

**Gereksinimler:**
1. `MuzikAleti` üst sınıfı oluşturunuz:
   - `protected String marka;`
   - `public void sesCikar()`: `"Müzik aleti ses veriyor."` basar.
2. 3 farklı alt sınıf türetiniz ve `sesCikar()` metodunu override ediniz:
   - `Gitar`: `"Tıngır tıngır akor çalıyor... 🎸"`
   - `Piyano`: `"Klasik melodi yükseliyor... 🎹"`
   - `Davul`: `"Güm güm ritim vuruyor... 🥁"`
3. `Orkestra` adında bir sınıf yazınız. İçinde `MuzikAleti[] enstrumanlar` dizisi olsun.
4. `orkestraCal()` metodu döngüyle tüm enstrümanların `sesCikar()` metodunu polimorfik olarak çalıştırsın.

---

### 🟡 Ödev 2 (Orta Seviye): Geometrik Şekiller ve Alan Hesaplama Sistemi (`SekilTest.java`)

**Gereksinimler:**
1. `Sekil` üst sınıfı:
   - `protected String isim;`
   - `public double alanHesapla()`: Varsayılan olarak `0.0` dönsün.
   - `public void bilgiVer()`: İsmini ve alanını formatlı yazdırsın.
2. Alt Sınıflar:
   - `Daire`: `yaricap` alanı barındırsın. $\text{Alan} = \pi \times r^2$.
   - `Dikdortgen`: `en`, `boy` alanları barındırsın. $\text{Alan} = \text{en} \times \text{boy}$.
   - `Ucgen`: `taban`, `yukseklik` alanları barındırsın. $\text{Alan} = \frac{\text{taban} \times \text{yukseklik}}{2}$.
3. `SekilTest` içinde karışık şekillerden oluşan 5 elemanlı bir `Sekil[]` dizisi açınız.
4. Diziyi tek bir döngüde dönerek:
   - Her şeklin alanını polimorfik olarak hesaplayıp ekrana yazdırınız.
   - Tüm şekillerin toplam alanını hesaplayıp basınız.

---

### 🔴 Ödev 3 (İleri Seviye): Çok Biçimli RPG Savaş Arenası (`ArenaSimulasyon.java`)

**Gereksinimler:**
1. `Karakter` Üst Sınıfı:
   - `protected String ad;`
   - `protected int can;`
   - `public void saldir(Karakter hedef)`: Varsayılan yumruk saldırısı (hedefin canını 5 azaltır).
2. Alt Sınıflar (`saldir` metodunu farklı çarpan ve kritik vuruş ihtimalleriyle override etsinler):
   - `Savasci`: %30 ihtimalle kritik vuruş yaparak 25, normalde 15 hasar versin.
   - `Buyucu`: Hedefin zırhını yok sayarak büyü hasarı (20) versin.
   - `Okcu`: Uzaktan 2 defa ok atsın (her ok 8 hasar).
3. `Arena` Sınıfı:
   - `public static void rauntOynat(Karakter k1, Karakter k2)` metodu:
     - Parametre olarak iki `Karakter` nesnesi alsın.
     - Sırayla `k1.saldir(k2)` ve `k2.saldir(k1)` polimorfik çağrılarını yapsın.
     - `instanceof` ile karakterin türünü öğrenip özel savaş narası attırınız (*Pattern matching kullanarak*).
4. Farklı karakter kombinasyonlarını arenada savaştıran kapsamlı bir test senaryosu kodlayınız.

