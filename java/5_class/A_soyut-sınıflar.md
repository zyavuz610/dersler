# 10. Java'da Soyut Sınıflar (Abstract Classes) ve Soyut Metotlar

Bu doküman; Nesne Yönelimli Programlamanın (OOP) en temel ilkelerinden biri olan **Soyutlama (Abstraction)** kavramını, **Soyut Sınıfların (Abstract Classes)** neden ve nasıl tanımlandığını, **Soyut Metotların (Abstract Methods)** alt sınıflarla kurduğu "sözleşme (contract)" mantığını, soyut sınıfların kurucu metot (`Constructor`) davranışlarını ve `new` ile somutlaştırılamama kurallarını ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Çok Biçimlilik ve Metot Ezme (9_polymorphizm.md)](./9_polymorphizm.md)
> - 🔜 [Sonraki Bölüm: Arayüzler (Interfaces) ve Karşılaştırma (B_arayüzler.md)](./B_arayüzler.md)

---

## 📌 İçindekiler
1. [Soyutlama (Abstraction) ve Soyut Sınıf Nedir?](#1-soyutlama-abstraction-ve-soyut-sınıf-nedir)
   - ["Ne Yapılacağını Belirle, Nasıl Yapılacağını Alt Sınıfa Bırak"](#ne-yapılacağını-belirle-nasıl-yapılacağını-alt-sınıfa-bırak)
   - [Gerçek Dünya Analojisi: "Bana Bir Şekil Çiz!"](#gerçek-dünya-analojisi-bana-bir-şekil-çiz)
2. [`abstract` Anahtar Kelimesi ve Temel Kurallar](#2-abstract-anahtar-kelimesi-ve-temel-kurallar)
   - [Soyut Sınıf Tanımlama](#soyut-sınıf-tanımlama)
   - [Neden `new` ile Nesnesi Üretilemez?](#neden-new-ile-nesnesi-üretilemez)
   - [Soyut Metotlar (Abstract Methods) - Gövdesiz Metotlar](#soyut-metotlar-abstract-methods---gövdesiz-metotlar)
   - [Gövdeli (Concrete) Metotların Soyut Sınıftaki Varlığı (Kısmi Soyutlama)](#gövdeli-concrete-metotların-soyut-sınıftaki-varlığı)
3. [Soyut Sınıflarda Kurucu (Constructor) Mantığı](#3-soyut-sınıflarda-kurucu-constructor-mantığı)
   - [Madem `new` Yapılamıyor, Neden Kurucu Metot Vardır?](#madem-new-yapılamıyor-neden-kurucu-metot-vardır)
   - [`super(...)` ile Ortak Alanları İlk Değerleme](#super-ile-ortak-alanları-ilk-değerleme)
4. [Alt Sınıfların Sorumluluğu: Sözleşmeye Uyma Mecburiyeti](#4-alt-sınıfların-sorumluluğu-sözleşmeye-uyma-mecburiyeti)
   - [Tüm Soyut Metotları Ezme (`@Override`) Zorunluluğu](#tüm-soyut-metotları-ezme-override-zorunluluğu)
   - [Eğer Alt Sınıf Da Ezmezse Ne Olur?](#eğer-alt-sınıf-da-ezmezse-ne-olur)
5. [`final` ile `abstract` Çelişkisi ve Zıtlığı](#5-final-ile-abstract-çelişkisi-ve-zıtlığı)
6. [Adım Adım Çalıştırılabilir Kod Örnekleri](#6-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Geometrik Şekiller (`Sekil.java`, `Daire.java`, `Dikdortgen.java` & `SekilTest.java`)](#örnek-1-geometrik-şekiller)
   - [Örnek 2: Banka Kredi Hesaplama Şablonu (`Kredi.java` & `BankaKrediTest.java`)](#örnek-2-banka-kredi-hesaplama-şablonu)
   - [Örnek 3: Oyun Karakterleri ve Yetenek Sistemi (`OyunKarakteri.java` & `OyunTest.java`)](#örnek-3-oyun-karakterleri-ve-yetenek-sistemi)
7. [Sık Yapılan Hatalar ve Önemli Kurallar](#7-sık-yapılan-hatalar-ve-önemli-kurallar)
8. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
9. [Ödev Konuları ve Mini Projeler](#9-ödev-konuları-ve-mini-projeler)

---

## 1. Soyutlama (Abstraction) ve Soyut Sınıf Nedir?

Nesne Yönelimli Programlamanın 4 temel direğinden sonuncusu **Soyutlama (Abstraction)**'dır. 

Soyutlama; bir nesnenin gereksiz iç detaylarını kullanıcıdan gizleyip, sadece **ne yaptığını (işlevini)** dış dünyaya sunma sanatıdır.

---

### "Ne Yapılacağını Belirle, Nasıl Yapılacağını Alt Sınıfa Bırak"

Bir üst sınıf düşünün: Alt sınıfların hangi eylemleri **mutlaka yapması gerektiğini** bilir, ancak her alt sınıf bu eylemi **tamamen farklı bir yöntemle** gerçekleştirecektir.
- Üst sınıf: *"Ey alt sınıflar! Hepiniz birer `Sekil`siniz ve hepiniz alanınızı hesaplamak (`alanHesapla()`) ZORUNDASINIZ!"*
- Daire: *"Ben alanımı $\pi \times r^2$ ile hesaplarım."*
- Dikdörtgen: *"Ben alanımı $\text{en} \times \text{boy}$ ile hesaplarım."*
- Üçgen: *"Ben alanımı $(\text{taban} \times \text{yükseklik}) / 2$ ile hesaplarım."*

Üst sınıf olan `Sekil` sınıfı kendi başına bir alan formülüne sahip olamaz; çünkü "şekil" soyut bir kavramdır! İşte bu tür sınıflara **Soyut Sınıf (Abstract Class)** denir.

---

### Gerçek Dünya Analojisi: "Bana Bir Şekil Çiz!"

Biri size *"Bana tahtaya bir şekil çiz"* derse çizemezsiniz!
*"Ne çizeyim? Daire mi, kare mi, üçgen mi?"* diye sorarsınız. Çünkü dünyada tek başına somut bir "şekil" yoktur; şekil bir **üst şablon/kavramdır**.

Benzer şekilde:
- *"Bana bir hayvan getir"* diyemezsiniz; kedi, köpek veya koyun getirebilirsiniz. `Hayvan` soyuttur.
- *"Bana bir taşıt ver"* diyemezsiniz; otomobil veya uçak verebilirsiniz. `Tasit` soyuttur.

```
                    SOYUT (ABSTRACT) KAVRAM
                     class Sekil { ... }
                  (new Sekil() YAPILAMAZ! ❌)
                              ^
             +----------------+----------------+
             |                                 |
     SOMUT (CONCRETE)                  SOMUT (CONCRETE)
   class Daire { ... }              class Dikdortgen { ... }
  (new Daire() YAPILIR ✅)        (new Dikdortgen() YAPILIR ✅)
```

---

## 2. `abstract` Anahtar Kelimesi ve Temel Kurallar

### Soyut Sınıf Tanımlama
Bir sınıfın başına **`abstract`** yazıldığında, o sınıf artık soyut bir sınıfa dönüşür:

```java
public abstract class Sekil {
    protected String isim;
    // ...
}
```

---

### Neden `new` ile Nesnesi Üretilemez?

Soyut sınıflar eksik ve tamamlanmamış taslaklardır. Bu yüzden Java derleyicisi bir soyut sınıftan doğrudan nesne üretilmesine **izin vermez**:

```java
Sekil s = new Sekil(); // ❌ DERLEME HATASI: Sekil is abstract; cannot be instantiated
```

> 💡 **Fakat Dikkat:** `new Sekil()` yapılamaz ama **`Sekil` türünde bir referans değişkeni** pekala tanımlanabilir ve polimorfik olarak alt sınıf nesnelerini tutabilir:
> ```java
> Sekil s = new Daire(5.0); // ✅ Tamamen geçerlidir (Upcasting & Polimorfizm)!
> ```

---

### Soyut Metotlar (Abstract Methods) - Gövdesiz Metotlar

Bir metodun sadece **imzası (adı, parametreleri ve dönüş tipi)** tanımlanıp gövdesi (`{ ... }`) yazılmazsa, o metot **soyut metottur (`abstract method`)**. Sonuna süslü parantez yerine noktalı virgül (`;`) konur:

```java
public abstract class Sekil {
    // Soyut Metot: Gövdesi YOKTUR! Alt sınıflar doldurmak zorundadır!
    public abstract double alanHesapla();
}
```

> 🔴 **ALTIN KURAL:**
> Bir sınıfın içinde **en az bir tane bile** `abstract` metot varsa, o sınıfın kendisi de **mutlaka `abstract class` olmak zorundadır!** Aksi halde derleme hatası alırsınız.

---

### Gövdeli (Concrete) Metotların Soyut Sınıftaki Varlığı

Soyut sınıflar sadece soyut metotlardan oluşmak zorunda **değildir**. İçlerinde:
- Standart değişkenler (`int x;`, `String ad;`),
- `static` değişkenler ve metotlar,
- Kurucu metotlar (`Constructor`),
- **Gövdesi olan normal (concrete) metotlar** barındırabilirler.

Buna **Kısmi Soyutlama (Partial Abstraction)** denir (Genellikle %0 ile %100 arasında soyutlama sunarlar).

```java
public abstract class Sekil {
    protected String isim;

    // Normal (Gövdeli) Metot: Tüm alt sınıflar için ortak davranış
    public void bilgiVer() {
        System.out.println("Şekil Türü: " + isim);
    }

    // Soyut (Gövdesiz) Metot: Her alt sınıf kendine göre doldurmak ZORUNDA
    public abstract double alanHesapla();
}
```

---

## 3. Soyut Sınıflarda Kurucu (Constructor) Mantığı

Öğrencilerin en çok kafasını karıştıran soru:
> *"Hocam madem `new Sekil()` yazıp nesne üretemiyoruz, neden soyut sınıfların içine kurucu metot (`Constructor`) yazabiliyoruz?"*

### Cevap: Alt Sınıfların `super()` ile Miras Alması İçin!

Soyut sınıfın içinde tanımlanan ortak değişkenlerin (`isim`, `id`, `tarih` vb.) ilk değerlerini verebilmek için kurucuya ihtiyaç vardır. Alt sınıflar `new Daire()` dendiğinde, ilk satırda `super(...)` çağrısıyla bu soyut sınıfın kurucusunu çalıştırırlar.

```java
public abstract class Sekil {
    protected String isim;

    // Soyut Sınıfın Kurucusu
    public Sekil(String isim) {
        this.isim = isim;
        System.out.println("Soyut [Sekil] kurucusu çalıştı: " + isim);
    }
}

public class Daire extends Sekil {
    private double yaricap;

    public Daire(String isim, double yaricap) {
        super(isim); // ✅ Soyut sınıf kurucusunu çağırır!
        this.yaricap = yaricap;
    }
}
```

---

## 4. Alt Sınıfların Sorumluluğu: Sözleşmeye Uyma Mecburiyeti

Bir alt sınıf soyut bir sınıfı `extends` ettiğinde, Java derleyicisi alt sınıfa bir sözleşme uzatır:
> *"Ya miras aldığın tüm soyut metotları tek tek `@Override` edip gövdelerini yazarsın; ya da sen de kendini `abstract` ilan edersin!"*

```java
public class Daire extends Sekil {
    private double yaricap;

    public Daire(double yaricap) {
        this.yaricap = yaricap;
    }

    // SÖZLEŞME GEREĞİ EZMEK ZORUNDAYIZ:
    @Override
    public double alanHesapla() {
        return Math.PI * yaricap * yaricap;
    }
}
```

Eğer `Daire` sınıfı `alanHesapla()` metodunu ezmeyi unutursa, Java şu hatayı basar ve programı derlemez:
`Daire is not abstract and does not override abstract method alanHesapla() in Sekil`.

---

## 5. `final` ile `abstract` Çelişkisi ve Zıtlığı

Java'da `final` ve `abstract` birbirine **tamamen zıt iki zihniyettir**:

| Anahtar Kelime | Anlamı ve Amacı |
| :--- | :--- |
| **`abstract`** | "Ben tamamlanmadım! Beni mutlaka miras al (`extends`) ve metotlarımı ez (`override`)!" |
| **`final`** | "Ben son noktayım! Benden asla miras alma ve metotlarımı asla ezme!" |

Bu iki zıt kelime asla aynı sınıf veya aynı metot üzerinde birlikte kullanılamaz:
```java
// ❌ DERLEME HATASI: illegal combination of modifiers: abstract and final
public final abstract class YanlisSinif { }

// ❌ DERLEME HATASI: illegal combination of modifiers: abstract and final
public abstract final void yanlisMetot();
```

---

## 6. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Geometrik Şekiller

#### `Sekil.java`
```java
public abstract class Sekil {
    protected String isim;

    public Sekil(String isim) {
        this.isim = isim;
    }

    // Soyut Metotlar: Her şekil bunları kendine göre hesaplamak zorundadır
    public abstract double alanHesapla();
    public abstract double cevreHesapla();

    // Gövdeli Ortak Metot: Tüm şekiller bu ortak raporu kullanabilir
    public void raporVer() {
        System.out.printf("--- %s Raporu ---\n", isim);
        System.out.printf("Alan  : %.2f\n", alanHesapla());
        System.out.printf("Çevre : %.2f\n\n", cevreHesapla());
    }
}
```

#### `Daire.java`
```java
public class Daire extends Sekil {
    private double yaricap;

    public Daire(double yaricap) {
        super("Daire");
        this.yaricap = yaricap;
    }

    @Override
    public double alanHesapla() {
        return Math.PI * yaricap * yaricap;
    }

    @Override
    public double cevreHesapla() {
        return 2 * Math.PI * yaricap;
    }
}
```

#### `Dikdortgen.java`
```java
public class Dikdortgen extends Sekil {
    private double en;
    private double boy;

    public Dikdortgen(double en, double boy) {
        super("Dikdörtgen");
        this.en = en;
        this.boy = boy;
    }

    @Override
    public double alanHesapla() {
        return en * boy;
    }

    @Override
    public double cevreHesapla() {
        return 2 * (en + boy);
    }
}
```

#### `SekilTest.java`
```java
public class SekilTest {
    public static void main(String[] args) {
        // Sekil s = new Sekil("Genel"); // ❌ HATA! Soyut sınıf nesnesi üretilemez.

        // Polimorfik Dizi: Soyut sınıf referansıyla somut nesneleri topluyoruz
        Sekil[] sekiller = {
            new Daire(5.0),
            new Dikdortgen(4.0, 6.0),
            new Daire(2.5)
        };

        for (Sekil s : sekiller) {
            s.raporVer(); // Dinamik olarak doğru alt sınıfın metotları çalışır!
        }
    }
}
```

#### Konsol Çıktısı:
```text
--- Daire Raporu ---
Alan  : 78,54
Çevre : 31,42

--- Dikdörtgen Raporu ---
Alan  : 24,00
Çevre : 20,00

--- Daire Raporu ---
Alan  : 19,63
Çevre : 15,71
```

---

### Örnek 2: Banka Kredi Hesaplama Şablonu (Template Method Pattern)

Bu örnekte genel bir kredi akışı üst sınıfta sabitlenir, ancak faiz oranı her krediye göre soyut bırakılır.

#### `Kredi.java`
```java
public abstract class Kredi {
    protected String musteriAdi;
    protected double anaPara;
    protected int vadeAy;

    public Kredi(String musteriAdi, double anaPara, int vadeAy) {
        this.musteriAdi = musteriAdi;
        this.anaPara = anaPara;
        this.vadeAy = vadeAy;
    }

    // SOYUT METOT: Her kredi türünün faiz oranı farklıdır!
    public abstract double getAylikFaizOrani();

    // GÖVDELİ ŞABLON METOT: Hesaplama formülü herkes için aynıdır
    public double toplamGeriOdemeHesapla() {
        double toplamFaiz = anaPara * (getAylikFaizOrani() / 100.0) * vadeAy;
        return anaPara + toplamFaiz;
    }

    public double aylikTaksitHesapla() {
        return toplamGeriOdemeHesapla() / vadeAy;
    }

    public void odemePlaniBas() {
        System.out.println("==========================================");
        System.out.println("Müşteri        : " + musteriAdi);
        System.out.println("Kredi Tutarı   : " + anaPara + " TL | Vade: " + vadeAy + " Ay");
        System.out.println("Aylık Faiz     : %" + getAylikFaizOrani());
        System.out.printf("Aylık Taksit   : %.2f TL\n", aylikTaksitHesapla());
        System.out.printf("Toplam Geri Öd.: %.2f TL\n", toplamGeriOdemeHesapla());
        System.out.println("==========================================");
    }
}
```

#### `IhtiyacKredisi.java` & `KonutKredisi.java`
```java
class IhtiyacKredisi extends Kredi {
    public IhtiyacKredisi(String musteriAdi, double anaPara, int vadeAy) {
        super(musteriAdi, anaPara, vadeAy);
    }

    @Override
    public double getAylikFaizOrani() {
        return 3.5; // İhtiyaç kredisi faizi %3.5
    }
}

class KonutKredisi extends Kredi {
    public KonutKredisi(String musteriAdi, double anaPara, int vadeAy) {
        super(musteriAdi, anaPara, vadeAy);
    }

    @Override
    public double getAylikFaizOrani() {
        return 2.1; // Konut kredisi faizi %2.1
    }
}

public class BankaKrediTest {
    public static void main(String[] args) {
        Kredi k1 = new IhtiyacKredisi("Ahmet Yılmaz", 50000, 12);
        Kredi k2 = new KonutKredisi("Zeynep Kaya", 1000000, 120);

        k1.odemePlaniBas();
        System.out.println();
        k2.odemePlaniBas();
    }
}
```

---

### Örnek 3: Oyun Karakterleri ve Yetenek Sistemi

#### `OyunKarakteri.java`, `Savasci.java`, `Buyucu.java`
```java
public abstract class OyunKarakteri {
    protected String ad;
    protected int can;

    public OyunKarakteri(String ad, int can) {
        this.ad = ad;
        this.can = can;
    }

    // Ortak Davranış (Gövdeli): Her karakter hasar alabilir
    public void hasarAl(int miktar) {
        this.can -= miktar;
        if (this.can < 0) this.can = 0;
        System.out.println(ad + " " + miktar + " hasar aldı. Kalan Can: " + can);
    }

    // Soyut Davranış (Gövdesiz): Her karakterin özel yeteneği tamamen farklıdır!
    public abstract void ozelYetenekKullan();
}

class Savasci extends OyunKarakteri {
    public Savasci(String ad) { super(ad, 150); }

    @Override
    public void ozelYetenekKullan() {
        System.out.println(ad + " KILIÇ FIRTINASI başlattı! Düşmanlara zırh delici hasar vurdu! ⚔️");
    }
}

class Buyucu extends OyunKarakteri {
    public Buyucu(String ad) { super(ad, 90); }

    @Override
    public void ozelYetenekKullan() {
        System.out.println(ad + " METEOR YAĞMURU yağdırdı! Tüm haritayı alevler kapladı! ☄️🔥");
    }
}

public class OyunTest {
    public static void main(String[] args) {
        OyunKarakteri[] takim = { new Savasci("Conan"), new Buyucu("Gandalf") };

        for (OyunKarakteri k : takim) {
            k.ozelYetenekKullan(); // Polimorfik soyut metot tetiklenir
        }
    }
}
```

---

## 7. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. Soyut Sınıftan `new` ile Nesne Üretmeye Kalkışmak
```java
Kredi k = new Kredi("Ali", 10000, 6); // ❌ HATA: Kredi is abstract; cannot be instantiated
```
✅ **Çözüm:** Soyut sınıfın alt sınıflarından biri üretilmelidir: `new IhtiyacKredisi(...)`.

---

### 2. Soyut Metoda Gövde `{ }` Koymak
```java
public abstract class Test {
    public abstract void calis() { } // ❌ HATA: abstract methods cannot specify a body
}
```
✅ **Çözüm:** Soyut metotların süslü parantezi olmaz; sonuna `;` konur: `public abstract void calis();`.

---

### 3. `private abstract` Metot Yazmaya Çalışmak
```java
public abstract class Guvenlik {
    private abstract void sifrele(); // ❌ HATA: illegal combination of modifiers: abstract and private
}
```
Soyut metotlar alt sınıflar tarafından ezilmek zorundadır. Ancak `private` metotlar alt sınıflar tarafından görülemez. Görünmeyen bir metot ezilemeyeceği için bu bir mantık çelişkisidir.

---

### 4. Soyut Metot İçeren Sınıfı `abstract` İlan Etmeyi Unutmak
Eğer sınıfın içine tek bir tane bile `abstract void metot();` yazarsanız, sınıf tanımına da mutlaka `abstract class` yazmalısınız.

---

## 8. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Kod Analizi ve Tasarım Sorusu
Aşağıdaki ifadelerden hangileri doğrudur, hangileri yanlıştır?
1. Soyut sınıfların içinde hiç soyut metot bulunmayabilir mi?
2. Soyut bir sınıfın kurucu metodu (`Constructor`) olabilir mi?
3. Soyut bir sınıf başka bir soyut sınıftan miras alabilir mi?
4. Soyut bir metodun gövdesine `super.metot()` yazılabilir mi?

<details>
<summary>🔍 <b>Cevapları Görmek İçin Tıklayın</b></summary>

1. **DOĞRU:** Bir sınıf içinde hiç soyut metot olmasa bile `new` yapılmasını engellemek için `abstract` tanımlanabilir.
2. **DOĞRU:** Alt sınıfların `super(...)` ile alanları başlatabilmesi için kurucu metot tanımlanabilir.
3. **DOĞRU:** Soyut bir sınıf başka bir soyut sınıftan `extends` edebilir ve üst sınıfın soyut metotlarını ezmek zorunda kalmaz.
4. **YANLIŞ:** Soyut bir metodun zaten gövdesi `{ }` yoktur, dolayısıyla içine hiçbir kod yazılamaz!
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında bulunan derleme hatasını bulunuz:

```java
public abstract class Arac {
    public abstract void kornaCal();
}

public class Motosiklet extends Arac {
    // Başka metotlar var ama kornaCal() yazılmadı
    public void gazVer() {
        System.out.println("Vın vın!");
    }
}
```

<details>
<summary>🔍 <b>Hata ve Açıklamayı Görmek İçin Tıklayın</b></summary>

**Hata:** `Motosiklet` somut bir sınıftır (`abstract` değildir) fakat miras aldığı `kornaCal()` soyut metodunu override etmemiştir.
**Düzeltme:** `Motosiklet` sınıfı içine `@Override public void kornaCal() { System.out.println("Düt düt!"); }` eklenmelidir.
</details>

---

### Alıştırma 3: Küçük Tasarım (Belge Yazıcı)
- Soyut Sınıf: `Belge` (`String baslik`, kurucu ve soyut metot `public abstract void yazdir();`).
- Somut Alt Sınıflar: `PdfBelge` ve `WordBelge` (kendi formatlarında yazdırma mesajı bassınlar).
- `main` içinde polimorfik olarak test ediniz.

---

## 9. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Taşıt Yakıt Tüketim Sistemi (`TasitYakitiTest.java`)

**Gereksinimler:**
1. `Arac` adında soyut bir sınıf oluşturunuz:
   - `protected String plaka;`
   - `protected double depoKapasitesi;`
   - Kurucu metot.
   - Soyut Metot: `public abstract double yuzKmdeYakitTuketimi();`
   - Gövdeli Metot: `public double menzilHesapla()` $\rightarrow (\text{depoKapasitesi} / \text{yuzKmdeYakitTuketimi()}) \times 100$.
2. Alt Sınıflar:
   - `Otomobil` (100 km'de 6.5 litre yakar).
   - `Kamyon` (100 km'de 28.0 litre yakar).
   - `Motosiklet` (100 km'de 3.2 litre yakar).
3. 3 farklı araçtan oluşan polimorfik bir dizi oluşturup her birinin plakasını ve tam depo ile gidebilecekleri maksimum menzili yazdırınız.

---

### 🟡 Ödev 2 (Orta Seviye): Çalışan Maaş ve İkramiye Sistemi (`MaasSistemiTest.java`)

**Gereksinimler:**
1. `Calisan` Soyut Üst Sınıfı:
   - `protected String adSoyad;`
   - `protected int calismaYili;`
   - Kurucu metot.
   - Soyut Metot: `public abstract double maasHesapla();`
   - Soyut Metot: `public abstract double ikramiyeHesapla();`
   - Gövdeli Metot: `public void bordroBas()` (Toplam ödemeyi = Maaş + İkramiye olarak formatlı basar).
2. Alt Sınıflar:
   - `TamZamanliCalisan`: Sabit aylık maaş (Örn: 40.000 TL), ikramiye = $\text{Çalışma Yılı} \times 2000\text{ TL}$.
   - `YariZamanliCalisan`: Saatlik ücret (Örn: 300 TL) $\times$ Çalışılan saat. İkramiye yoktur ($0\text{ TL}$).
   - `Stajyer`: Sabit 15.000 TL, ikramiye $1000\text{ TL}$.
3. Farklı çalışanları bir dizide toplayıp şirketin aylık toplam giderini hesaplayan test sınıfını yazınız.

---

### 🔴 Ödev 3 (İleri Seviye): Veri Tabanı Raporlama Motoru (`RaporTest.java`)

**Gereksinimler:**
1. `RaporOlusturucu` Soyut Sınıfı (Template Method Pattern):
   - Soyut Adımlar:
     - `protected abstract void verileriGetir();`
     - `protected abstract void formataDonustur();`
     - `protected abstract void diskeKaydet();`
   - **`final` Gövdeli Şablon Metot:**
     ```java
     public final void raporOlustur() {
         System.out.println(">> Rapor süreci başlatıldı.");
         verileriGetir();
         formataDonustur();
         diskeKaydet();
         System.out.println("<< Rapor başarıyla tamamlandı.\n");
     }
     ```
2. Somut Alt Sınıflar:
   - `PdfRapor`: PDF başlıkları ve tabloları oluşturup `.pdf` olarak kaydeder.
   - `ExcelRapor`: Hücreleri ve satırları işleyip `.xlsx` olarak kaydeder.
   - `JsonRapor`: JSON anahtar-değer çiftlerini oluşturup API çıktısı basar.
3. `RaporTest.java` içinde kullanıcıdan veya polimorfik döngüden gelen talebe göre farklı rapor formatlarının şablon metot üzerinden kusursuzca çalıştığını kanıtlayınız.

