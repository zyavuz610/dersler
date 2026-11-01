# 7. Java'da Kompozisyon (Composition), Sahiplik ("HAS-A") İlişkisi ve İç Sınıflar

Bu doküman; Nesne Yönelimli Programlamada (OOP) sınıflar arası en temel ve en esnek ilişki biçimi olan **Kompozisyon (Composition / Bileşim)** prensibini, bir sınıf içerisinde başka bir sınıf türünde referans barındırma mantığını, **"HAS-A" (Sahiptir)** ile kalıtımdaki **"IS-A" (-dır / -dir)** ilişkisinin hayati farkını, **Sıkı Sahiplik (Composition)** ile **Gevşek Sahiplik (Aggregation)** ayrımını ve **İç Sınıfları (Nested / Inner Classes)** ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Nesne Dizileri ve Dizi İşlemleri (6_class_ve_diziler.md)](./6_class_ve_diziler.md)
> - 🔜 [Sonraki Bölüm: Kalıtım ve Miras Alma (Inheritance) (8_kalıtım.md)](./8_kalıtım.md)

---

## 📌 İçindekiler
1. [Kompozisyon (Composition) Nedir ve Temel Mantığı](#1-kompozisyon-composition-nedir-ve-temel-mantığı)
   - [Parça-Bütün (Part-Whole) İlişkisi](#parça-bütün-part-whole-ilişkisi)
   - [Gerçek Dünya Analojisi: Araba ve Motor](#gerçek-dünya-analojisi-araba-ve-motor)
   - [Neden Monolitik Tek Bir Sınıf Yerine Kompozisyon Kullanırız?](#neden-monolitik-tek-bir-sınıf-yerine-kompozisyon-kullanırız)
2. ["HAS-A" (Sahiptir) vs. "IS-A" (-dır / -dir) İlişkisi](#2-has-a-sahiptir-vs-is-a--dır---dir-ilişkisi)
   - ["HAS-A" (Sahiplik / Kompozisyon) Örnekleri](#has-a-sahiplik--kompozisyon-örnekleri)
   - ["IS-A" (Kalıtım / Inheritance) Örnekleri](#is-a-kalıtım--inheritance-örnekleri)
   - [Karşılaştırma Tablosu ve Tasarım Kuralı: "Favor Composition over Inheritance"](#karşılaştırma-tablosu-ve-tasarım-kuralı)
3. [Kompozisyon Türleri: Composition vs. Aggregation](#3-kompozisyon-türleri-composition-vs-aggregation)
   - [Composition (Sıkı Sahiplik - Birlikte Doğar, Birlikte Ölür)](#1-composition-sıkı-sahiplik)
   - [Aggregation (Gevşek Sahiplik - Bağımsız Yaşayabilen Parçalar)](#2-aggregation-gevşek-sahiplik)
4. [Bir Sınıf İçinde Başka Sınıf Tanımlama (İç Sınıflar - Inner Classes)](#4-bir-sınıf-içinde-başka-sınıf-tanımlama-iç-sınıflar)
   - [Üye İç Sınıflar (Non-static Inner Classes)](#1-üye-iç-sınıflar-non-static-inner-classes)
   - [Statik Dahili Sınıflar (Static Nested Classes)](#2-statik-dahili-sınıflar-static-nested-classes)
5. [Adım Adım Çalıştırılabilir Kod Örnekleri](#5-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Klasik Kompozisyon (`Motor.java`, `Araba.java` & `OtomobilTest.java`)](#örnek-1-klasik-kompozisyon)
   - [Örnek 2: Çoklu Kompozisyon (`Adres.java`, `Musteri.java` & `ECommerceTest.java`)](#örnek-2-çoklu-kompozisyon)
   - [Örnek 3: Dahili (İç) Sınıf ile Donanım Tasarımı (`Bilgisayar.java`)](#örnek-3-dahili-iç-sınıf-ile-donanım-tasarımı)
6. [Sık Yapılan Hatalar ve Önemli Kurallar](#6-sık-yapılan-hatalar-ve-önemli-kurallar)
7. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#7-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
8. [Ödev Konuları ve Mini Projeler](#8-ödev-konuları-ve-mini-projeler)

---

## 1. Kompozisyon (Composition) Nedir ve Temel Mantığı?

Gerçek hayatta karmaşık nesneler sıfırdan tek parça halinde üretilmez; **daha küçük, uzmanlaşmış parçaların bir araya getirilmesiyle (montajlanmasıyla)** oluşturulur.

Örneğin bir **Bilgisayar**:
- Kendi başına tek bir metal yığını değildir.
- Bir **İşlemciye (CPU)**, bir **Belleğe (RAM)**, bir **Anakarta** ve bir **Ekrana** sahiptir.

Yazılım dünyasında da büyük sınıflar, içlerinde küçük sınıfların nesnelerini birer üye değişken (alan / field) olarak barındırır. Buna **Kompozisyon (Composition / Bileşim)** denir.

---

### Parça-Bütün (Part-Whole) İlişkisi

```
+-------------------------------------------------------------+
|                     BÜTÜN (WHOLE): ARABA                    |
|                                                             |
|  - Marka: "Toyota"                                          |
|  - Model: "Corolla"                                         |
|                                                             |
|  +---------------------------+ +-------------------------+  |
|  |   PARÇA 1: MOTOR (Engine) | |  PARÇA 2: TEKERLEK (Tire)|  |
|  |   - Beygir: 130 HP        | |  - Ebat: 17 inç         |  |
|  |   - Hacim: 1.6 L          | |  - Basınç: 32 PSI       |  |
|  |   - calis()               | |  - dondur()             |  |
|  +---------------------------+ +-------------------------+  |
+-------------------------------------------------------------+
```

---

### Neden Monolitik Tek Bir Sınıf Yerine Kompozisyon Kullanırız?

Eğer kompozisyon kullanmasaydık, tüm özellikleri tek bir devasa sınıfın içine yığmak zorunda kalırdık:

```java
// ❌ KÖTÜ VE KONTROLSÜZ TASARIM (Spaghetti / God Class):
public class Araba {
    String marka;
    String model;
    int motorBeygirGucu;
    double motorHacmi;
    String motorYakitTuru;
    int tekerlekEbadi;
    int tekerlekBasinci;
    String akuMarkasi;
    int akuVoltaji;
    // ... 50 tane değişken!
}
```

**Kompozisyonun Sağladığı Güç:**
1. **Tek Sorumluluk Prensibi (Single Responsibility):** Motorla ilgili kurallar `Motor` sınıfında, araba ile ilgili kurallar `Araba` sınıfında yaşar.
2. **Yeniden Kullanılabilirlik (Reusability):** Yazdığınız `Motor` sınıfını yarın bir `Tekne` veya `Jeneratör` sınıfında da aynen kullanabilirsiniz!
3. **Kolay Bakım ve Test:** Motoru değiştirmek veya güncellemek istediğinizde Araba sınıfının kodlarını bozmazsınız.

---

## 2. "HAS-A" (Sahiptir) vs. "IS-A" (-dır / -dir) İlişkisi

Nesne Yönelimli Programlamada iki sınıf arasındaki ilişkiyi anlamanın en kolay yolu Türkçe cümle kurmaktır:

```
+-----------------------------------------------------------------+
|  HAS-A (Sahiptir) İlişkisi   ---> KOMPOZİSYON (Composition)     |
|  IS-A (-dır / -dir) İlişkisi ---> KALITIM / MİRAS (Inheritance) |
+-----------------------------------------------------------------+
```

---

### "HAS-A" (Sahiplik / Kompozisyon) Örnekleri
Bir nesne başka bir nesneyi kendi bünyesinde barındırıyorsa:
- Araba bir Motora **sahiptir** (`Car HAS-A Engine`).
- Öğrenci bir Nüfus Cüzdanına **sahiptir** (`Student HAS-A IdCard`).
- Ev bir Mutfak ve Odalara **sahiptir** (`House HAS-A Room`).
- Banka Hesabı bir Müşteriye **sahiptir** (`Account HAS-A Customer`).

Kod Gösterimi:
```java
public class Araba {
    private Motor motor; // Araba bir motora SAHİPTİR (HAS-A)
}
```

---

### "IS-A" (Kalıtım / Inheritance) Örnekleri
Bir sınıf başka bir sınıfın daha özel bir türüyse:
- Kamyon bir Araba**dır** (`Truck IS-A Vehicle/Car`).
- Kedi bir Hayvan**dır** (`Cat IS-A Animal`).
- Müdür bir Çalışan**dır** (`Manager IS-A Employee`).

Kod Gösterimi (İleride göreceğimiz `extends` sözcüğü):
```java
public class Kamyon extends Araba {
    // Kamyon bir arabadır (IS-A)
}
```

---

### Karşılaştırma Tablosu ve Tasarım Kuralı

| Kriter | HAS-A (Kompozisyon) | IS-A (Kalıtım / Miras) |
| :--- | :--- | :--- |
| **İlişki Türü** | Sahiplik / Parça-Bütün | Tür / Soy Ağacı |
| **Java Karşılığı** | Alan (Field) olarak tanımlama (`private Motor m;`) | `extends` anahtar kelimesi |
| **Bağımlılık Seviyesi** | **Gevşek Bağımlılık (Loose Coupling)** - Esnektir | **Sıkı Bağımlılık (Tight Coupling)** - Kırılgandır |
| **Çalışma Zamanı Değişimi**| Parça nesne çalışma anında değiştirilebilir (`setMotor()`) | Çalışma anında üst sınıf değiştirilemez |

> 🌟 **ALTIN YAZILIM PRENSİBİ:**
> *"Favor object composition over class inheritance"* (Sınıf kalıtımı yerine nesne kompozisyonunu tercih ediniz). 
> Bir işi kalıtımla çözmeye kalkmadan önce her zaman "Burada bir sahiplik mi var yoksa tür ilişkisi mi?" sorusunu sorunuz. "Araba bir Motordur" demek saçmadır; "Araba bir Motora sahiptir" mantıklıdır!

---

## 3. Kompozisyon Türleri: Composition vs. Aggregation

Kompozisyon ilişkisi parçanın ömrünün bütüne olan bağımlılığına göre **ikiye ayrılır**:

### 1. Composition (Sıkı Sahiplik)
- **Kural:** Parça, bütünden bağımsız yaşayamaz. Bütün yok edilirse parça da yok olur (**Ölümüne Bağlılık**).
- **Örnek:** İnsan ve Kalp. İnsan öldüğünde kalp de yaşayamaz. Bina yıkıldığında içindeki odalar da yok olur.
- **Kodlama Yolu:** Parça nesnesi genellikle ana sınıfın kendi kurucusu (`Constructor`) içinde `new` yapılır:

```java
public class Bina {
    private Oda oda;

    public Bina() {
        this.oda = new Oda(); // Bina oluşurken oda doğar, bina yıkılırsa oda silinir!
    }
}
```

---

### 2. Aggregation (Gevşek Sahiplik)
- **Kural:** Parça, bütünden bağımsız olarak tek başına da var olabilir. Bütün yok olsa bile parça yaşamaya devam eder.
- **Örnek:** Şirket ve Çalışan. Şirket iflas edip kapansa bile çalışan ölmez; başka bir şirkete geçebilir. Araba ve Şoför.
- **Kodlama Yolu:** Parça nesnesi dışarıda üretilir ve ana sınıfın kurucusuna veya setter metoduna parametre olarak verilir:

```java
public class Sirket {
    private Calisan calisan;

    // Dışarıda yaşayan çalışan şirkete atanır:
    public Sirket(Calisan calisan) {
        this.calisan = calisan;
    }
}
```

---

## 4. Bir Sınıf İçinde Başka Sınıf Tanımlama (İç Sınıflar)

Java'da bir sınıf doğrudan başka bir sınıfın gövdesi içine yazılabilir (**Nested / Inner Class**).
Bu yapı, bir sınıfın **sadece ve sadece tek bir ana sınıfa hizmet ettiği** durumlarda mantıksal gruplama yapmak için kullanılır.

### 1. Üye İç Sınıflar (Non-static Inner Classes)
Dış sınıfın her bir nesnesine doğrudan bağlıdır ve dış sınıfın `private` alanlarına doğrudan erişebilir:

```java
public class DisSinif {
    private String gizliYazi = "Çok Gizli";

    // İç Sınıf
    public class IcSinif {
        public void oku() {
            // Dış sınıfın private alanına doğrudan erişebilir!
            System.out.println("Okunan veri: " + gizliYazi);
        }
    }
}

// Kullanım:
DisSinif dis = new DisSinif();
DisSinif.IcSinif ic = dis.new IcSinif(); // Dış nesne üzerinden üretilir
ic.oku();
```

---

### 2. Statik Dahili Sınıflar (Static Nested Classes)
Dış sınıfın nesnesine ihtiyaç duymaz. Dış sınıfın sadece `static` üyelerine erişebilir. Bağımsız bir yardımcı sınıf gibi davranır:

```java
public class DisSinif {
    public static class StatikIcNesne {
        public void calis() {
            System.out.println("Statik iç sınıf çalıştı.");
        }
    }
}

// Kullanım (Dış nesne üretmeye gerek YOKTUR):
DisSinif.StatikIcNesne nesne = new DisSinif.StatikIcNesne();
nesne.calis();
```

---

## 5. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Klasik Kompozisyon (Araba ve Motor)

Bu örnekte `Motor` sınıfını bağımsız tasarlayıp `Araba` sınıfına monte edeceğiz.

#### `Motor.java`
```java
public class Motor {
    private int beygirGucu;
    private double hacim;
    private boolean calisiyorMu;

    public Motor(int beygirGucu, double hacim) {
        this.beygirGucu = beygirGucu;
        this.hacim = hacim;
        this.calisiyorMu = false;
    }

    public void calistir() {
        if (!calisiyorMu) {
            calisiyorMu = true;
            System.out.println(">> Motor homurdandı ve ÇALIŞTI. (" + beygirGucu + " HP, " + hacim + "L)");
        } else {
            System.out.println("Motor zaten çalışıyor.");
        }
    }

    public void durdur() {
        if (calisiyorMu) {
            calisiyorMu = false;
            System.out.println("<< Motor STOP etti.");
        }
    }

    public boolean isCalisiyorMu() {
        return calisiyorMu;
    }

    public int getBeygirGucu() {
        return beygirGucu;
    }
}
```

#### `Araba.java`
```java
public class Araba {
    private String marka;
    private String model;
    
    // KOMPOZİSYON: Araba bir Motora SAHİPTİR (HAS-A)
    private Motor motor;

    public Araba(String marka, String model, int beygirGucu, double motorHacmi) {
        this.marka = marka;
        this.model = model;
        // Sıkı Sahiplik (Composition): Araba üretilirken motoru da üretilir
        this.motor = new Motor(beygirGucu, motorHacmi);
    }

    public void kontakCevir() {
        System.out.println(marka + " " + model + " için kontak çevriliyor...");
        motor.calistir(); // İçindeki motor nesnesinin metodunu çağırır!
    }

    public void parkEt() {
        System.out.println(marka + " " + model + " park ediliyor...");
        motor.durdur();
    }

    public void gazaBas() {
        if (motor.isCalisiyorMu()) {
            System.out.println(marka + " " + model + " hızla ileri atılıyor! 🏎️💨");
        } else {
            System.out.println("Uyarı: Motor çalışmıyor! Önce kontak çeviriniz.");
        }
    }
}
```

#### `OtomobilTest.java`
```java
public class OtomobilTest {
    public static void main(String[] args) {
        Araba arabam = new Araba("BMW", "M3", 480, 3.0);

        arabam.gazaBas();     // Motor çalışmadığı için uyarı verir
        System.out.println();

        arabam.kontakCevir(); // Motoru çalıştırır
        arabam.gazaBas();     // Artık araba gider!
        System.out.println();

        arabam.parkEt();      // Motoru stop eder
    }
}
```

#### Konsol Çıktısı:
```text
Uyarı: Motor çalışmıyor! Önce kontak çeviriniz.

BMW M3 için kontak çevriliyor...
>> Motor homurdandı ve ÇALIŞTI. (480 HP, 3.0L)
BMW M3 hızla ileri atılıyor! 🏎️💨

BMW M3 park ediliyor...
<< Motor STOP etti.
```

---

### Örnek 2: Çoklu Kompozisyon (Müşteri ve Adres)

Bir e-ticaret sisteminde `Adres` sınıfı bağımsızdır ve `Musteri` sınıfının bir alanıdır.

#### `Adres.java`
```java
public class Adres {
    private String sehir;
    private String ilce;
    private String sokakNo;

    public Adres(String sehir, String ilce, String sokakNo) {
        this.sehir = sehir;
        this.ilce = ilce;
        this.sokakNo = sokakNo;
    }

    public String getTamAdres() {
        return sokakNo + " - " + ilce + " / " + sehir;
    }
}
```

#### `Musteri.java`
```java
public class Musteri {
    private String adSoyad;
    private String eposta;

    // KOMPOZİSYON: Müşteri bir Adrese SAHİPTİR (HAS-A)
    private Adres evAdresi;

    // Aggregation (Gevşek Sahiplik): Adres dışarıdan parametre gelir
    public Musteri(String adSoyad, String eposta, Adres evAdresi) {
        this.adSoyad = adSoyad;
        this.eposta = eposta;
        this.evAdresi = evAdresi;
    }

    public void kargoKartiYazdir() {
        System.out.println("================================");
        System.out.println("Alıcı       : " + adSoyad);
        System.out.println("E-Posta     : " + eposta);
        System.out.println("Teslimat Adr: " + evAdresi.getTamAdres());
        System.out.println("================================");
    }

    // Adres nesnesini güncelleyen setter
    public void setEvAdresi(Adres yeniAdres) {
        this.evAdresi = yeniAdres;
    }
}
```

#### `ECommerceTest.java`
```java
public class ECommerceTest {
    public static void main(String[] args) {
        // Önce adres nesnesini bağımsız üretiyoruz:
        Adres eskiAdres = new Adres("İstanbul", "Kadıköy", "Moda Cad. No:14");

        // Müşteriye bu adresi teslim ediyoruz:
        Musteri musteri = new Musteri("Ayşe Yılmaz", "ayse@mail.com", eskiAdres);
        musteri.kargoKartiYazdir();

        // Müşteri taşındı: Yeni adres nesnesi üretip atıyoruz
        System.out.println("\n[BİLGİ] Müşteri adres değişikliği yaptı:");
        Adres yeniAdres = new Adres("İzmir", "Urla", "Deniz Sok. No:5");
        musteri.setEvAdresi(yeniAdres);

        musteri.kargoKartiYazdir();
    }
}
```

---

### Örnek 3: Dahili (İç) Sınıf ile Donanım Tasarımı

#### `Bilgisayar.java`
```java
public class Bilgisayar {
    private String marka;
    private Islemci islemci; // Kompozisyon

    // Dahili (İç) Sınıf: Islemci sadece Bilgisayar için anlamlıdır
    public class Islemci {
        private String model;
        private double frekansGhz;

        public Islemci(String model, double frekansGhz) {
            this.model = model;
            this.frekansGhz = frekansGhz;
        }

        public void bilgiVer() {
            System.out.println("CPU: " + model + " @ " + frekansGhz + " GHz");
        }
    }

    public Bilgisayar(String marka, String cpuModel, double cpuFrekans) {
        this.marka = marka;
        // İç sınıf nesnesini doğrudan oluşturuyoruz
        this.islemci = new Islemci(cpuModel, cpuFrekans);
    }

    public void sistemRaporu() {
        System.out.println("Bilgisayar Markası: " + marka);
        islemci.bilgiVer();
    }
}
```

#### `BilgisayarTest.java`
```java
public class BilgisayarTest {
    public static void main(String[] args) {
        Bilgisayar pc = new Bilgisayar("Dell XPS", "Intel i7-13700H", 4.8);
        pc.sistemRaporu();
    }
}
```

---

## 6. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `NullPointerException` (İç Nesneyi Başlatmayı Unutmak)
Ana nesneyi oluşturup iç nesneye `new` yapmazsanız iç referans `null` kalır:
```java
public class Araba {
    private Motor motor; // null!

    public void calistir() {
        motor.calistir(); // ❌ RUNTIME ERROR: NullPointerException!
    }
}
```
✅ **Çözüm:** İç nesneyi ya kurucuda `new` yapın ya da kurucuya parametre olarak zorunlu isteyin.

---

### 2. Aşırı Zincirleme (Demeter Yasası İhlali - "Tren Kazası")
Birbirinin içindeki nesnelere peş peşe ulaşmaya çalışmak kodu kırılgan yapar:
```java
// ⚠️ KÖTÜ KOD:
araba.getMotor().getPiston().getSegman().getCap();
```
Eğer motor pistonu değiştirirse araba kodu çöker.
✅ **Çözüm:** Nesneler işi birbirine devretmelidir (*Tell, Don't Ask*). `araba.bilgiVer()` çağrılmalı, araba motora sormalı, motor pistona sormalıdır.

---

### 3. Kalıtım (IS-A) ile Kompozisyonu (HAS-A) Karıştırmak
- ❌ Hatalı: `class Araba extends Motor` ("Araba bir motordur" - Yanlış!)
- ✅ Doğru: `class Araba { private Motor motor; }` ("Araba bir motora sahiptir" - Doğru!)

---

## 7. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: İlişki Türü Tespiti (IS-A mı HAS-A mı?)
Aşağıdaki ikililer arasındaki ilişkiyi **IS-A (Kalıtım)** veya **HAS-A (Kompozisyon)** olarak sınıflandırınız:

1. `Banka` ile `BankaHesabi`
2. `Kopek` ile `Hayvan`
3. `AkilliTelefon` ile `Batarya`
4. `Daire` ile `GeometrikSekil`
5. `Kitap` ile `Yazar`

<details>
<summary>🔍 <b>Cevapları Görmek İçin Tıklayın</b></summary>

1. **HAS-A (Kompozisyon):** Banka hesaplara sahiptir.
2. **IS-A (Kalıtım):** Köpek bir hayvandır.
3. **HAS-A (Kompozisyon):** Akıllı telefon bir bataryaya sahiptir.
4. **IS-A (Kalıtım):** Daire bir geometrik şekildir.
5. **HAS-A (Kompozisyon / Aggregation):** Kitap bir yazara sahiptir.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kodda derleme hatasına veya `NullPointerException`'a yol açacak eksikliği bulunuz:

```java
class Ekran {
    int boyut;
    public Ekran(int b) { this.boyut = b; }
}

class Televizyon {
    Ekran ekran;
    public void ac() {
        System.out.println(ekran.boyut + " inç ekran açıldı.");
    }
}

public class Test {
    public static void main(String[] args) {
        Televizyon tv = new Televizyon();
        tv.ac();
    }
}
```

<details>
<summary>🔍 <b>Hata ve Düzeltmeyi Görmek İçin Tıklayın</b></summary>

**Hata:** `Televizyon` kurucusunda `ekran` nesnesi başlatılmamıştır (`ekran == null`). `tv.ac()` çalıştığında `ekran.boyut` ifadesi **`NullPointerException`** fırlatır!
**Düzeltme:** `Televizyon` içine kurucu eklenip `this.ekran = new Ekran(55);` veya parametre olarak `Televizyon(Ekran e)` atanmalıdır.
</details>

---

### Alıştırma 3: Küçük Tasarım (Kalem ve Silgi)
- `Silgi` sınıfı: `int omur = 100;` ve `sil()` metodu (her silmede ömrü 10 azaltsın).
- `Kalem` sınıfı: Bir `Silgi` nesnesine sahip olsun (`HAS-A`).
- `Kalem.yaz()` ve `Kalem.arkasiylaSil()` metotlarını yazıp test ediniz.

---

## 8. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Akıllı Telefon Donanım Kompozisyonu (`TelefonTest.java`)

**Gereksinimler:**
1. `Batarya` sınıfı: `kapasite` (mAh) ve `sarjYuzdesi` (%).
   - `sarjEt(int miktar)` ve `enerjiHarca(int miktar)`.
2. `Kamera` sınıfı: `megapiksel` (MP) ve `fotoCek()` metodu.
3. `AkilliTelefon` sınıfı:
   - `private String marka;`
   - `private Batarya batarya;` (Kompozisyon)
   - `private Kamera kamera;` (Kompozisyon)
   - `fotografCek()` metodu: Kamera ile fotoğraf çeksin ve bataryadan %2 enerji harcasın! Batarya %0 ise fotoğraf çekilemesin.
4. `main` metodu içinde nesneleri birleştirip telefonun fotoğraf çekme ve şarj döngüsünü test ediniz.

---

### 🟡 Ödev 2 (Orta Seviye): Kütüphane ve Kitap Rafı Kompozisyonu (`KutuphaneSistemi.java`)

**Gereksinimler:**
1. `Yazar` sınıfı: `ad`, `ulke`.
2. `Kitap` sınıfı: `baslik`, `fiyat`, `Yazar yazar` (Kompozisyon - Bir kitap bir yazara sahiptir).
3. `Kutuphane` sınıfı:
   - `private String kutuphaneAdi;`
   - `private Kitap[] kitaplar;` (Kompozisyon + Dizi! Kütüphane bir kitap dizisine sahiptir).
   - `kitapEkle(Kitap yeniKitap)`
   - `yazarinKitaplariniBul(String yazarAdi)`: Parametre verilen yazarın kütüphanedeki tüm kitaplarını ekrana bassın.
4. Test sınıfında 2 yazar, 4 kitap ve 1 kütüphane oluşturup arama senaryolarını test ediniz.

---

### 🔴 Ödev 3 (İleri Seviye): Restoran Sipariş ve Masa Yönetimi (`RestoranSimulasyonu.java`)

**Gereksinimler:**
1. `Yemek` sınıfı: `isim`, `fiyat`.
2. `SiparisKalemi` sınıfı:
   - `private Yemek yemek;` (Kompozisyon)
   - `private int adet;`
   - `getAraToplam()`: $\text{yemek.fiyat} \times \text{adet}$.
3. `Masa` sınıfı:
   - `private int masaNo;`
   - `private SiparisKalemi[] siparisler;` (Maksimum 10 sipariş kalemi)
   - `siparisVer(Yemek y, int adet)`
   - `hesapOzetiGoster()`: Masadaki tüm siparişleri ve toplam ödenecek hesabı şık bir fiş olarak yazdırsın.
4. Test sınıfında menüdeki yemeklerden masaya sipariş ekleyip hesap kapatma simülasyonunu çalıştırınız.

