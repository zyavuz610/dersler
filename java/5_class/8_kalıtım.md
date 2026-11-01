# 8. Java'da Kalıtım (Inheritance), "IS-A" İlişkisi ve Miras Alma Türleri

Bu doküman; Nesne Yönelimli Programlamanın (OOP) en güçlü ve temel sütunlarından biri olan **Kalıtım (Inheritance / Miras)** kavramını, sınıflar arasındaki **"IS-A" (-dır / -dir)** ilişkisini, miras hiyerarşisi türlerini (**Tekli, Çok Seviyeli, Hiyerarşik ve Çoklu Miras**), Java'nın sınıflarda çoklu mirasa neden izin vermediğini (**Elmas Problemi - Diamond Problem**), **`super`** anahtar kelimesini ve erişim belirleyicilerin (`public`, `protected`, `default`, `private`) mirastaki davranışlarını ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Kompozisyon (HAS-A İlişkisi) ve İç Sınıflar (7_composition.md)](./7_composition.md)
> - 🔜 [Sonraki Bölüm: Çok Biçimlilik (Polymorphism) ve Metot Ezme (9_polymorphizm.md)](./9_polymorphizm.md)

---

## 📌 İçindekiler
1. [Kalıtım (Inheritance / Miras) Nedir?](#1-kalıtım-inheritance--miras-nedir)
   - [Temel Kavramlar: Üst Sınıf (Superclass) ve Alt Sınıf (Subclass)](#temel-kavramlar-üst-sınıf-superclass-ve-alt-sınıf-subclass)
   - [Gerçek Dünya Analojisi: Biyolojik Kalıtım ve Canlılar Ağacı](#gerçek-dünya-analojisi-biyolojik-kalıtım-ve-canlılar-ağacı)
   - [Neden Kalıtım Kullanırız? (Kod Tekrarını Önleme & Genişletilebilirlik)](#neden-kalıtım-kullanırız)
2. ["IS-A" (-dır / -dir) İlişkisi ve `extends` Sözdizimi](#2-is-a--dır---dir-ilişkisi-ve-extends-sözdizimi)
   - [`extends` Anahtar Kelimesi](#extends-anahtar-kelimesi)
   - [`instanceof` Operatörü ile Tür Kontrolü](#instanceof-operatörü-ile-tür-kontrolü)
3. [Kalıtım Hiyerarşisi Türleri](#3-kalıtım-hiyerarşisi-türleri)
   - [1. Tek Seviyeli Miras (Single Inheritance)](#1-tek-seviyeli-miras-single-inheritance)
   - [2. Çok Seviyeli Miras (Multilevel Inheritance)](#2-çok-seviyeli-miras-multilevel-inheritance)
   - [3. Hiyerarşik Miras (Hierarchical Inheritance)](#3-hiyerarşik-miras-hierarchical-inheritance)
   - [4. Çoklu Miras (Multiple Inheritance) ve Java'nın Yasaklama Sebebi](#4-çoklu-miras-multiple-inheritance-ve-javanın-yasaklama-sebebi)
   - [Elmas Problemi (The Diamond Problem) Analizi](#elmas-problemi-the-diamond-problem-analizi)
   - [Java'da Çoklu Miras İhtiyacı Nasıl Çözülür? (Arayüzler / `interface`)](#javada-çoklu-miras-ihtiyacı-nasıl-çözülür-arayüzler--interface)
4. [Miras Alma Biçimleri ve Erişim Belirleyicilerin Rolü](#4-miras-alma-biçimleri-ve-erişim-belirleyicilerin-rolü)
   - [C++ ile Java'nın Temel Farkı](#c-ile-javanın-temel-farkı)
   - [Erişim Belirleyicilerin Miras Aktarım Tablosu](#erişim-belirleyicilerin-miras-aktarım-tablosu)
   - [`protected` Belirleyicisinin Mirastaki Özel Gücü](#protected-belirleyicisinin-mirastaki-özel-gücü)
   - [`private` Alanlar Miras Alınır mı?](#private-alanlar-miras-alınır-mı)
5. [`super` Anahtar Kelimesi ve Kurucu Çağrı Sırası](#5-super-anahtar-kelimesi-ve-kurucu-çağrı-sırası)
   - [`super()` ile Üst Sınıf Kurucusunu Çağırma](#super-ile-üst-sınıf-kurucusunu-çağırma)
   - [`super.alan` ve `super.metot()` Kullanımı](#superalan-ve-supermetot-kullanımı)
   - [Kurucuların Çalışma Sırası (Atadan Toruna Çağrı Yığını)](#kurucuların-çalışma-sırası)
6. [`final` Anahtar Kelimesinin Mirastaki Rolü](#6-final-anahtar-kelimesinin-mirastaki-rolü)
   - [`final class` (Miras Alınamaz Sınıflar)](#final-class-miras-alınamaz-sınıflar)
   - [`final method` (Ezilemez Metotlar)](#final-method-ezilemez-metotlar)
7. [Java'nın Evrensel Atası: `java.lang.Object` Sınıfı](#7-javanın-evrensel-atası-javalangobject-sınıfı)
8. [Adım Adım Çalıştırılabilir Kod Örnekleri](#8-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Tek Seviyeli Miras ve `super` (`Calisan.java`, `Yazilimci.java` & `SirketTest.java`)](#örnek-1-tek-seviyeli-miras-ve-super)
   - [Örnek 2: Çok Seviyeli Miras (`Canli.java`, `Hayvan.java`, `Kopek.java` & `DogaTest.java`)](#örnek-2-çok-seviyeli-miras)
   - [Örnek 3: Hiyerarşik Miras ve `protected` Alanlar (`BankaHesabi.java` & Alt Sınıfları)](#örnek-3-hiyerarşik-miras-ve-protected-alanlar)
9. [Sık Yapılan Hatalar ve Önemli Kurallar](#9-sık-yapılan-hatalar-ve-önemli-kurallar)
10. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#10-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
11. [Ödev Konuları ve Mini Projeler](#11-ödev-konuları-ve-mini-projeler)

---

## 1. Kalıtım (Inheritance / Miras) Nedir?

Kalıtım; bir sınıfın, başka bir sınıfın sahip olduğu **özellikleri (alanları)** ve **yetenekleri (metotları)** devralmasını ve bunların üzerine kendi özel yeteneklerini ekleyerek genişlemesini sağlayan nesne yönelimli programlama mekanizmasıdır.

---

### Temel Kavramlar: Üst Sınıf ve Alt Sınıf

```
          +--------------------------------------+
          |     ÜST SINIF (Superclass / Parent)  |
          |               Hayvan                 |
          |   - ad, yas                          |
          |   - beslen(), uyu()                  |
          +--------------------------------------+
                             ^
                             | (extends - Miras Alır)
          +--------------------------------------+
          |      ALT SINIF (Subclass / Child)    |
          |                Kopek                 |
          |   + havla() (Ekstra kendi yeteneği)  |
          +--------------------------------------+
```

- **Üst Sınıf (Superclass / Parent / Base Class):** Ortak özellikleri barındıran genel sınıftır (örn: `Hayvan`, `Arac`, `Calisan`).
- **Alt Sınıf (Subclass / Child / Derived Class):** Üst sınıftan türeyen, onun tüm miras bıraktığı özellikleri devralan ve kendine has yeni özellikler ekleyen özel sınıftır (örn: `Kopek`, `Otomobil`, `Yazilimci`).

---

### Gerçek Dünya Analojisi: Biyolojik Kalıtım ve Canlılar Ağacı

Bir çocuk ailesinden göz rengini, kan grubunu veya boy potansiyelini genetik miras olarak alır. Ancak çocuk sadece ailesinin kopyası değildir; kendine özgü hobileri, yetenekleri ve kariyeri vardır. 

Benzer şekilde biyolojideki canlılar sınıflandırması tam bir kalıtım ağacıdır:
- Omurgalı $\rightarrow$ Memeli $\rightarrow$ Köpek
- Her köpek aynı zamanda bir memelidir ve aynı zamanda bir omurgalıdır.

---

### Neden Kalıtım Kullanırız?

1. **Kod Tekrarını Önler (DRY - Don't Repeat Yourself):** Şirkette 5 farklı çalışan rolü (Müdür, Mühendis, Stajyer vb.) varsa; hepsine tek tek `ad`, `soyad`, `maas` değişkenleri ve `girisYap()` metotları yazılmaz. Bunlar tek bir `Calisan` üst sınıfında toplanır.
2. **Genişletilebilirlik (Extensibility):** Mevcut çalışan kodları bozmadan sisteme yeni bir alt sınıf (örneğin `GuvenlikGorevlisi`) rahatça eklenebilir.
3. **Çok Biçimliliğe (Polymorphism) Zemin Hazırlar:** Tüm alt sınıflar üst sınıf türünde referanslarla yönetilebilir.

---

## 2. "IS-A" (-dır / -dir) İlişkisi ve `extends` Sözdizimi

Bir önceki dersimizde kompozisyonun **"HAS-A" (Sahiptir)** ilişkisi kurduğunu görmüştük. Kalıtım ise **"IS-A" (-dır / -dir)** ilişkisi kurar:

- Otomobil bir Araç**tır** (`Otomobil IS-A Arac`).
- Yazılımcı bir Çalışan**dır** (`Yazilimci IS-A Calisan`).
- Kedi bir Evcil Hayvan**dır** (`Kedi IS-A EvcilHayvan`).

---

### `extends` Anahtar Kelimesi

Java'da bir sınıfın başka bir sınıftan miras aldığını belirtmek için **`extends`** sözcüğü kullanılır:

```java
// Üst Sınıf
public class Calisan {
    String ad;
    double maas;

    public void calis() {
        System.out.println(ad + " çalışıyor.");
    }
}

// Alt Sınıf: Calisan sınıfından türetilir
public class Yazilimci extends Calisan {
    String anaDil; // Sadece yazılımcıya özel ek alan

    public void kodYaz() { // Sadece yazılımcıya özel ek davranış
        System.out.println(ad + ", " + anaDil + " ile kod yazıyor.");
    }
}
```

Kullanımı:
```java
Yazilimci y = new Yazilimci();
y.ad = "Ali";              // Calisan'dan miras geldi!
y.maas = 45000;           // Calisan'dan miras geldi!
y.anaDil = "Java";        // Kendi özelliği
y.calis();                // Calisan'dan miras geldi!
y.kodYaz();               // Kendi metodu
```

---

### `instanceof` Operatörü ile Tür Kontrolü

Java'da bir nesnenin belirli bir sınıfa (veya onun üst sınıflarına) ait olup olmadığını çalışma anında test etmek için `instanceof` operatörü kullanılır:

```java
Yazilimci y = new Yazilimci();

System.out.println(y instanceof Yazilimci); // true (Kendisi)
System.out.println(y instanceof Calisan);   // true (Miras aldığı üst sınıf)
System.out.println(y instanceof Object);    // true (Evrensel ata)
```

---

## 3. Kalıtım Hiyerarşisi Türleri

Nesne yönelimli dünyada kalıtım modelleri 5'e ayrılır:

```
1. Tekli (Single)       2. Çok Seviyeli (Multilevel)     3. Hiyerarşik (Hierarchical)
    [ A ]                     [ A ] (Dede)                       [ A ]
      |                         |                               /     \
      v                         v                              v       v
    [ B ]                     [ B ] (Baba)                   [ B ]   [ C ]
                                |
                                v
                              [ C ] (Torun)
```

---

### 1. Tek Seviyeli Miras (Single Inheritance)
Bir alt sınıfın yalnızca tek bir üst sınıftan türediği en sade modeldir:
`Arac` $\rightarrow$ `Araba`.

---

### 2. Çok Seviyeli Miras (Multilevel Inheritance)
Bir sınıfın başka bir alt sınıftan türediği, mirasın zincirleme aktarıldığı modeldir:
`Canli` $\rightarrow$ `Hayvan` $\rightarrow$ `Memeli` $\rightarrow$ `Kopek`.
- `Kopek` sınıfı hem `Memeli`'nin, hem `Hayvan`'ın hem de `Canli`'nın tüm `public` ve `protected` özelliklerine sahip olur!

---

### 3. Hiyerarşik Miras (Hierarchical Inheritance)
Tek bir üst sınıftan birden çok farklı alt sınıfın türediği modeldir:
- `Sekil` $\rightarrow$ `Daire`, `Kare`, `Ucgen`.
- `BankaHesabi` $\rightarrow$ `VadesizHesap`, `VadeliHesap`, `YatirimHesabi`.

---

### 4. Çoklu Miras (Multiple Inheritance) ve Java'nın Yasaklama Sebebi

> 🔴 **JAVA'NIN EN ÖNEMLİ KURALLARINDAN BİRİ:**
> **Java'da bir sınıf AYNI ANDA BİRDEN FAZLA SINIFTAN MİRAS ALAMAZ! (`extends A, B` YASAKTIR!)**

C++ gibi dillerde bir sınıf aynı anda iki farklı sınıftan türeyebilir (`class C : public A, public B`). Fakat Java bu özelliği **bilerek ve isteyerek yasaklamıştır**.

#### Elmas Problemi (The Diamond Problem) Analizi

Neden yasaklandı? Çünkü iki üst sınıfta aynı isimde bir metot varsa, derleyici hangisini çalıştıracağını bilemez ve belirsizlik (ambiguity) doğar:

```
            [ Sınıf A ]
           /           \
          /             \
    [ Sınıf B ]     [ Sınıf C ]
    (selamla())     (selamla())
          \             /
           \           /
            [ Sınıf D ]  <--- D.selamla() dediğimizde B'deki mi C'deki mi çalışacak?
```

Eğer Java çoklu sınıfsal mirasa izin verseydi:
```java
// ❌ JAVA DERLEME HATASI VERİR (Syntax Error):
public class AkilliTelefon extends Bilgisayar, Kamera { 
    // HATA: Class cannot extend multiple classes
}
```

---

### Java'da Çoklu Miras İhtiyacı Nasıl Çözülür? (Arayüzler / `interface`)

Java geliştiricileri çoklu mirasın avantajından mahrum bırakmamak için **Arayüz (Interface)** yapısını geliştirmiştir.
- Bir sınıf sadece **1 sınıftan miras alabilir (`extends`)**,
- Ancak **sınırsız sayıda arayüzü uygulayabilir (`implements`)**:

```java
// ✅ JAVA'DA GEÇERLİ OLAN ÇOKLU MİRAS ÇÖZÜMÜ:
public class AkilliTelefon extends Cihaz implements MuzikCalar, Kamera, GPS {
    // Sınırsız arayüz uygulanabilir!
}
```

---

## 4. Miras Alma Biçimleri ve Erişim Belirleyicilerin Rolü

### C++ ile Java'nın Temel Farkı
C++ dilinde miras alırken `class B : private A` veya `class B : protected A` gibi miras alma belirleyicileri kullanılır ve üst sınıfın tüm üyelerinin erişim seviyesi alt sınıfta toptan daraltılabilir.

**Java'da ise miras alma biçimi tektir:** Sadece `class Alt extends Ust` yazılır. Üyelerin alt sınıfa nasıl geçeceği tamamen **üyelerin kendi tanımlarındaki erişim belirleyicisine (`public`, `protected`, `default`, `private`)** bağlıdır.

---

### Erişim Belirleyicilerin Miras Aktarım Tablosu

| Belirleyici | Alt Sınıfın Doğrudan Erişimi Var mı? | Farklı Paketteki Alt Sınıf Erişebilir mi? | Açıklama |
| :--- | :---: | :---: | :--- |
| **`public`** | ✅ Evet | ✅ Evet | Alt sınıfın her yerinden ve dış dünyadan serbestçe erişilir. |
| **`protected`** | ✅ Evet | ✅ Evet | **Kalıtım için özel tasarlanmıştır.** Farklı pakette olsa dahi alt sınıflar erişebilir. |
| **`default` (varsayılan)** | ✅ Evet (Aynı paketteyse) | ❌ Hayır | Eğer alt sınıf üst sınıfla aynı paket içindeyse erişir, başka paketteyse görünmez. |
| **`private`** | ❌ Hayır (Doğrudan erişemez) | ❌ Hayır | Alt sınıfa fiziksel olarak geçer ancak doğrudan görünmez. Getter/Setter gerekir. |

---

### `protected` Belirleyicisinin Mirastaki Özel Gücü

`protected`, dış dünyaya kapalı ama **aile içine (mirasçılara ve aynı pakete) açık** olan özel bir erişim türüdür.

```java
public class Arac {
    protected int tekerlekSayisi = 4; // Mirasçılar erişebilir!
}

public class Bisiklet extends Arac {
    public void ayarla() {
        tekerlekSayisi = 2; // ✅ Başka pakette olsa bile alt sınıf olduğu için erişebilir!
    }
}
```

---

### `private` Alanlar Miras Alınır mı?

Öğrenciler sıklıkla *"private alanlar alt sınıfa miras kalmaz"* zanneder. Bu teknik olarak **yanlıştır**.
- Alt sınıftan bir nesne ürettiğinizde, bellekte üst sınıfın `private` alanları da yer kaplar.
- Ancak alt sınıf bu alanlara doğrudan `this.sifre` diyerek dokunamaz.
- Üst sınıfın `public` veya `protected` metotları (Getter/Setter/Kurucu) aracılığıyla bu alanları kullanır.

---

## 5. `super` Anahtar Kelimesi ve Kurucu Çağrı Sırası

Java'da **`super`** anahtar kelimesi, doğrudan **üst sınıfı (Superclass / Parent)** temsil eden özel bir referanstır. 2 ana kullanım yeri vardır:

### 1. `super()` ile Üst Sınıf Kurucusunu Çağırma
Bir alt sınıf nesnesi oluşturulduğunda, **önce üst sınıfın kurucusu çalışmak zorundadır** (Temel atılmadan çatı dikilemez!).

> 🔴 **ALTIN KURAL:**
> `super(...)` çağrısı, alt sınıf kurucusunun **İSTİSNASIZ İLK SATIRINDA** yer almak zorundadır!

```java
public class Personel {
    private String ad;

    public Personel(String ad) {
        this.ad = ad;
        System.out.println("1. Personel kurucusu çalıştı.");
    }
}

public class Mudur extends Personel {
    private String departman;

    public Mudur(String ad, String departman) {
        super(ad); // ✅ Üst sınıfın kurucusuna adı iletiyoruz (İlk satırda!)
        this.departman = departman;
        System.out.println("2. Müdür kurucusu çalıştı.");
    }
}
```

---

### 2. `super.alan` ve `super.metot()` Kullanımı
Eğer alt sınıfta üst sınıftaki bir alanla veya metotla aynı isimde bir tanım yapılmışsa, üst sınıftakine açıkça ulaşmak için `super.` kullanılır:

```java
public class Hayvan {
    public void sesCikar() {
        System.out.println("Hayvan ses çıkarıyor.");
    }
}

public class Kopek extends Hayvan {
    public void sesCikar() {
        super.sesCikar(); // Üst sınıftaki orijinal metodu da çalıştır!
        System.out.println("Köpek havlıyor: Hav hav!");
    }
}
```

---

### Kurucuların Çalışma Sırası

Kurucular daima **en tepedeki atadan başlayarak aşağıya doğru (Dede $\rightarrow$ Baba $\rightarrow$ Torun)** sırayla tetiklenir:

```
new Kopek() çağrıldığında:
  1. Object() kurucusu devreye girer
  2. Hayvan() kurucusu devreye girer
  3. Memeli() kurucusu devreye girer
  4. En son Kopek() kurucusu tamamlanır!
```

---

## 6. `final` Anahtar Kelimesinin Mirastaki Rolü

### `final class` (Miras Alınamaz Sınıflar)
Bir sınıfın başına `final` yazarsanız, o sınıfın bir üst sınıf olarak kullanılmasını ve **ondan miras alınmasını tamamen engellersiniz**:

```java
public final class GuvenlikProtokolu {
    // Bu sınıftan kimse extends yapamaz!
}

// ❌ DERLEME HATASI: cannot inherit from final GuvenlikProtokolu
public class Saldiri extends GuvenlikProtokolu { }
```

> 💡 **Biliyor muydunuz?** Java'nın standart `String`, `Integer`, `Math` sınıfları `final` olarak tanımlanmıştır. Bu sayede kimse `String` sınıfından türeyip onun güvenlik mekanizmalarını bozamaz.

---

### `final method` (Ezilemez Metotlar)
Bir metodun başına `final` yazılırsa, alt sınıflar o metodu miras alabilir fakat **üzerine yazamaz (override edemez)**.

```java
public class Banka {
    public final void faizHesaplamaFormulu() {
        // Bu formül devlet standardıdır, alt sınıflar değiştiremez!
    }
}
```

---

## 7. Java'nın Evrensel Atası: `java.lang.Object` Sınıfı

Java'da yazdığınız her sınıf, arkasında hiçbir `extends` yazmasanız dahi **otomatik olarak `java.lang.Object` sınıfından miras alır**:

```java
// Bizim yazdığımız:
public class Araba { }

// Java derleyicisinin gördüğü:
public class Araba extends Object { }
```

Bu sayede Java'daki her nesne doğrudan şu metotlara sahip olarak doğar:
- `toString()`: Nesneyi metin olarak ifade eder (varsayılan: `SinifAdi@HexAdres`).
- `equals(Object obj)`: İki nesnenin bellekteki adres eşitliğini kontrol eder.
- `hashCode()`: Nesnenin bellek kimlik numarasını döner.
- `getClass()`: Nesnenin hangi sınıfa ait olduğunu döner.

---

## 8. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Tek Seviyeli Miras ve `super` Kullanımı

#### `Calisan.java`
```java
public class Calisan {
    protected String ad;
    protected double maas;

    public Calisan(String ad, double maas) {
        this.ad = ad;
        this.maas = maas;
        System.out.println(">> [Calisan] kurucusu çalıştı: " + ad);
    }

    public void bilgileriGoster() {
        System.out.println("Çalışan: " + ad + " | Maaş: " + maas + " TL");
    }

    public void maasArtir(double oran) {
        this.maas += this.maas * (oran / 100.0);
        System.out.println(ad + " için yeni maaş: " + this.maas + " TL");
    }
}
```

#### `Yazilimci.java`
```java
public class Yazilimci extends Calisan {
    private String uzmanlikAlani;

    public Yazilimci(String ad, double maas, String uzmanlikAlani) {
        super(ad, maas); // Üst sınıf kurucusuna devrediyoruz
        this.uzmanlikAlani = uzmanlikAlani;
        System.out.println(">> [Yazilimci] kurucusu tamamlandı: " + uzmanlikAlani);
    }

    public void kodYaz() {
        // 'ad' değişkenine protected olduğu için doğrudan erişebiliyoruz
        System.out.println(ad + ", " + uzmanlikAlani + " alanında kod yazıyor...");
    }

    // Üst sınıftaki bilgileriGoster metodunu genişletiyoruz
    public void yazilimciKartiBas() {
        super.bilgileriGoster(); // Üst sınıfın metodunu çağırıyoruz
        System.out.println("Uzmanlık: " + uzmanlikAlani);
    }
}
```

#### `SirketTest.java`
```java
public class SirketTest {
    public static void main(String[] args) {
        Yazilimci dev = new Yazilimci("Caner Akın", 60000, "Backend / Java Spring");

        System.out.println("\n--- Kart Bilgileri ---");
        dev.yazilimciKartiBas();

        System.out.println("\n--- İşlemler ---");
        dev.kodYaz();
        dev.maasArtir(20); // Calisan sınıfından miras gelen metot
    }
}
```

#### Konsol Çıktısı:
```text
>> [Calisan] kurucusu çalıştı: Caner Akın
>> [Yazilimci] kurucusu tamamlandı: Backend / Java Spring

--- Kart Bilgileri ---
Çalışan: Caner Akın | Maaş: 60000.0 TL
Uzmanlık: Backend / Java Spring

--- İşlemler ---
Caner Akın, Backend / Java Spring alanında kod yazıyor...
Caner Akın için yeni maaş: 72000.0 TL
```

---

### Örnek 2: Çok Seviyeli Miras (Multilevel Inheritance)

Dede $\rightarrow$ Baba $\rightarrow$ Torun zincirleme aktarımı:

#### `Canli.java`, `Hayvan.java`, `Kopek.java`
```java
// 1. Seviye (Dede)
class Canli {
    public Canli() {
        System.out.println("1. [Canli] Hayat başladı.");
    }
    public void nefesAl() {
        System.out.println("Oksijen tüketiliyor.");
    }
}

// 2. Seviye (Baba)
class Hayvan extends Canli {
    public Hayvan() {
        super(); // Canli() çağrılır
        System.out.println("2. [Hayvan] İçgüdüler yüklendi.");
    }
    public void beslen() {
        System.out.println("Besin tüketiliyor.");
    }
}

// 3. Seviye (Torun)
class Kopek extends Hayvan {
    private String cins;

    public Kopek(String cins) {
        super(); // Hayvan() çağrılır
        this.cins = cins;
        System.out.println("3. [Kopek] " + cins + " dünyaya geldi!");
    }

    public void havla() {
        System.out.println("Hav hav!");
    }
}

public class DogaTest {
    public static void main(String[] args) {
        Kopek k = new Kopek("Golden Retriever");

        System.out.println("\n--- Miras Yetenekleri ---");
        k.nefesAl(); // Canli'dan miras
        k.beslen();  // Hayvan'dan miras
        k.havla();   // Kendi yeteneği
    }
}
```

#### Konsol Çıktısı:
```text
1. [Canli] Hayat başladı.
2. [Hayvan] İçgüdüler yüklendi.
3. [Kopek] Golden Retriever dünyaya geldi!

--- Miras Yetenekleri ---
Oksijen tüketiliyor.
Besin tüketiliyor.
Hav hav!
```

---

### Örnek 3: Hiyerarşik Miras ve `protected` Alanlar

#### `BankaHesabi.java`
```java
public class BankaHesabi {
    protected String hesapNo;
    protected double bakiye;

    public BankaHesabi(String hesapNo, double bakiye) {
        this.hesapNo = hesapNo;
        this.bakiye = bakiye;
    }

    public void bakiyeGoster() {
        System.out.println("Hesap [" + hesapNo + "] Bakiye: " + bakiye + " TL");
    }
}
```

#### Alt Sınıf 1: `VadesizHesap.java`
```java
public class VadesizHesap extends BankaHesabi {
    private double ekHesapLimiti;

    public VadesizHesap(String hesapNo, double bakiye, double ekHesapLimiti) {
        super(hesapNo, bakiye);
        this.ekHesapLimiti = ekHesapLimiti;
    }

    public void paraCek(double miktar) {
        // protected bakiye'ye doğrudan erişebiliyoruz
        if (miktar <= (bakiye + ekHesapLimiti)) {
            bakiye -= miktar;
            System.out.println(miktar + " TL çekildi. Güncel bakiye: " + bakiye + " TL");
        } else {
            System.out.println("Yetersiz limit!");
        }
    }
}
```

#### Alt Sınıf 2: `VadeliHesap.java`
```java
public class VadeliHesap extends BankaHesabi {
    private double faizOrani; // Örn: %35

    public VadeliHesap(String hesapNo, double bakiye, double faizOrani) {
        super(hesapNo, bakiye);
        this.faizOrani = faizOrani;
    }

    public void faizGetirisiEkle() {
        double kazanc = bakiye * (faizOrani / 100.0);
        bakiye += kazanc;
        System.out.println("Dönem faizi eklendi: +" + kazanc + " TL. Yeni bakiye: " + bakiye + " TL");
    }
}
```

---

## 9. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `super()` Çağrısının İlk Satırda Olmaması
```java
public class Ogrenci extends Kisi {
    public Ogrenci(String ad) {
        System.out.println("Öğrenci oluşturuluyor...");
        super(ad); // ❌ DERLEME HATASI: call to super must be first statement in constructor
    }
}
```
✅ **Çözüm:** `super(ad);` mutlaka kurucunun 1. satırına yazılmalıdır.

---

### 2. Üst Sınıfta Boş Kurucu Yokken `super()` Hatası
Eğer üst sınıfa sadece parametreli bir kurucu yazarsanız (örn: `Kisi(String ad)`), Java varsayılan boş kurucuyu siler. 
Alt sınıfın kurucusunda `super(ad)` çağırmazsanız, Java gizlice `super()` aramaya çalışır ve derleme hatası verir:
`constructor Kisi in class Kisi cannot be applied to given types; required: String, found: no arguments`.
✅ **Çözüm:** Ya üst sınıfa boş bir `Kisi() { }` ekleyin ya da alt sınıfta açıkça `super(ad)` çağırın.

---

### 3. Çoklu Sınıf Mirası Almaya Çalışmak
```java
public class Kedi extends Hayvan, EvcilHayvan { } // ❌ HATA!
```
Java sınıflarda çoklu mirasa izin vermez; arayüzler (`implements`) kullanılmalıdır.

---

### 4. `private` Alanlara Doğrudan Erişmeye Çalışmak
```java
public class Baba {
    private int para = 1000;
}

public class Ogul extends Baba {
    public void harca() {
        this.para -= 100; // ❌ HATA: para has private access in Baba
    }
}
```
✅ **Çözüm:** `Baba` sınıfındaki alanı `protected` yapınız veya `public getPara() / setPara()` metotları sağlayınız.

---

## 10. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Kurucu Çağrı Sırası Analizi
Aşağıdaki kod çalıştırıldığında konsola tam olarak ne yazdırılır?

```java
class X {
    public X() {
        System.out.print("X ");
    }
}

class Y extends X {
    public Y() {
        System.out.print("Y ");
    }
}

class Z extends Y {
    public Z() {
        System.out.print("Z ");
    }
}

public class Test {
    public static void main(String[] args) {
        Z nesne = new Z();
    }
}
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
```text
X Y Z 
```
**Açıklama:**
`new Z()` çağrıldığında görünmez `super()` ile `Y()`'ye gider, `Y()` de `super()` ile `X()`'e gider. Önce en tepedeki `X`, sonra `Y`, en son `Z` kurucusu tamamlanır.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında derleme hatası veren satırı bulunuz ve sebebini açıklayınız:

```java
final class Guvenlik {
    public void sifrele() {
        System.out.println("Şifrelendi.");
    }
}

class OzelGuvenlik extends Guvenlik {
    public void ekstraGuvenlik() {
        System.out.println("Ekstra koruma aktif.");
    }
}
```

<details>
<summary>🔍 <b>Hata ve Açıklamayı Görmek İçin Tıklayın</b></summary>

**Hata:** `class OzelGuvenlik extends Guvenlik` satırı derlenmez.
**Sebep:** `Guvenlik` sınıfı `final` olarak tanımlanmıştır. `final` anahtar kelimesi sınıfların miras alınmasını (türetilmesini) kesin olarak engeller.
</details>

---

### Alıştırma 3: Küçük Tasarım (Geometrik Şekiller)
- Bir üst sınıf: `Sekil` (alan: `String isim`, kurucu ve `bilgiYazdir()` metodu).
- Bir alt sınıf: `Dikdortgen` (`Sekil`'den türer; ek alanlar: `double en, boy`; metot: `alanHesapla()`).
- `super` kullanarak kurucuyu bağlayınız ve `main` içinde test ediniz.

---

## 11. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Çok Seviyeli Taşıt Hiyerarşisi (`TasitTest.java`)

**Gereksinimler:**
1. `Tasit` (1. Seviye Üst Sınıf):
   - `protected String marka;`
   - `protected int maksHiz;`
   - Kurucu ve `bilgiGoster()` metodu.
2. `MotorluTasit` (2. Seviye Alt Sınıf - `Tasit`'tan türer):
   - `protected double motorHacmi;`
   - `protected String yakitTuru;`
   - `super(...)` ile kurucu zincirlemesi.
3. `Otomobil` (3. Seviye Alt Sınıf - `MotorluTasit`'tan türer):
   - `private int kapiSayisi;`
   - `private boolean otomatikVitesMi;`
   - Tüm bilgileri eksiksiz yazdıran `detayliRapor()` metodu.
4. `TasitTest` sınıfında bir `Otomobil` nesnesi üretip tüm üst sınıflardan miras gelen verilerin nasıl başarıyla doldurulduğunu ekrana yazdırınız.

---

### 🟡 Ödev 2 (Orta Seviye): Üniversite Personel ve Akademisyen Sistemi (`UniversiteTest.java`)

**Gereksinimler:**
1. `Kisi` sınıfı: `ad`, `soyad`, `tcKimlikNo` (kurucu ve getter'lar).
2. `Calisan` sınıfı (`Kisi`'den türer):
   - `sicilNo`, `maas`, `departman`.
3. `Akademisyen` sınıfı (`Calisan`'dan türer):
   - `unvan` ("Prof. Dr.", "Doç. Dr.", "Dr. Öğr. Üyesi").
   - `verilenDersler` (`String[]` dizisi).
   - `dersEkle(String dersAdi)` metodu.
4. `IdariPersonel` sınıfı (`Calisan`'dan türer):
   - `gorevAlani` ("Öğrenci İşleri", "Kütüphane", "Mali İşler").
   - `nobetGunu` (String).
5. Hiyerarşik ve çok seviyeli kalıtımı kullanarak 1 Akademisyen ve 1 İdari Personel oluşturup bordro ve kimlik kartı basınız.

---

### 🔴 Ödev 3 (İleri Seviye): RPG Karakterleri ve Hiyerarşik Yetenek Sistemi (`RpgOyunTest.java`)

**Gereksinimler:**
1. `Karakter` Üst Sınıfı:
   - `protected String ad;`
   - `protected int can;`
   - `protected int mana;`
   - `public final void canGoster()`: (`final` metot - alt sınıflar ezemez).
   - `public void saldir(Karakter hedef)`: Temel yumruk saldırısı (hedefin canını 5 azaltır).
2. Alt Sınıf 1: `Savasci` (`Karakter`'den türer):
   - Ek alan: `zirhPuani`.
   - `kilicDarbe(Karakter hedef)`: 20 hasar verir, kendi manasından 10 harcar.
3. Alt Sınıf 2: `Buyucu` (`Karakter`'den türer):
   - Ek alan: `buyuGucu`.
   - `atesTopu(Karakter hedef)`: Hedefe $\text{buyuGucu} \times 2$ hasar verir, 25 mana harcar.
4. Alt Sınıf 3: `SifaBuyucusu` (`Buyucu`'den türer - Çok seviyeli kalıtım!):
   - `iyilestir(Karakter dost)`: Dost karakterin canını 30 artırır.
5. Bir simülasyonda Savaşçı ve Büyücü'nün düellosunu, Şifa Büyücüsü'nün takım arkadaşını iyileştirmesini `super`, `protected` ve `final` kurallarına sadık kalarak kodlayınız.

