# 1. Java'da Paketler (Packages) ve İçe Aktarma (Import) Mekanizması

Bu doküman; Java projelerinde sınıfları düzenlemek, isim çakışmalarını (namespace collision) önlemek ve erişim güvenliğini sağlamak için kullanılan **Paket (Package)** yapısını, paket tanımlama kurallarını, ters alan adı standardını, **`import`** ve **`import static`** komutlarını, paket düzeyinde erişim belirleyicilerini ve komut satırından derleme/çalıştırma (`javac -d` / `java`) adımlarını ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Modül: Java Koleksiyonları (6_koleksiyonlar)](../6_koleksiyonlar/readme.md)
> - 🔜 [Sonraki Bölüm: Java 9 Modül Sistemi - JPMS (2_modul_sistemi_jpms.md)](./2_modul_sistemi_jpms.md)

---

## 📌 İçindekiler
1. [Paket (Package) Nedir ve Neden Gerekir?](#1-paket-package-nedir-ve-neden-gerekir)
   - [Fiziksel Klasör Mimarisi ve İsim Alanı (Namespace) Analojisi](#fiziksel-klasör-mimarisi-ve-isim-alanı-analojisi)
   - [İsim Çakışmasını (Name Collision) Önleme](#isim-çakışmasını-önleme)
2. [Paket Tanımlama ve İsimlendirme Standartları](#2-paket-tanımlama-ve-isimlendirme-standartları)
   - [`package` Anahtar Kelimesi](#package-anahtar-kelimesi)
   - [Ters Alan Adı Standardı (Reverse Domain Name: `com.sirket.proje`)](#ters-alan-adı-standardı)
   - [Paket ve Dizin Hiyerarşisi Eşleşme Zorunluluğu](#paket-ve-dizin-hiyerarşisi-eşleşme-zorunluluğu)
3. [Sınıfları İçe Aktarma: `import` Deyimi](#3-sınıfları-içe-aktarma-import-deyimi)
   - [Tekil İçe Aktarma vs. Joker Karakter (`*`) ile Toplu İçe Aktarma](#tekil-içe-aktarma-vs-joker-karakter-ile-toplu-içe-aktarma)
   - [Otomatik İçe Aktarılan Özel Paket: `java.lang`](#otomatik-içe-aktarılan-özel-paket-javalang)
   - [Aynı İsimli İki Sınıfın Çakışması ve Tam Nitelikli İsim (Fully Qualified Name)](#aynı-isimli-iki-sınıfın-çakışması-ve-tam-nitelikli-isim)
4. [Statik İçe Aktarma (`import static`)](#4-statik-içe-aktarma-import-static)
   - [Math ve Sabit Sınıflarını Kod İçine Doğrudan Dahil Etme](#math-ve-sabit-sınıflarını-kod-içine-doğrudan-dahil-etme)
   - [Aşırı Kullanım Tehlikesi ve Okunabilirlik](#aşırı-kullanım-tehlikesi-ve-okunabilirlik)
5. [Paket Düzeyi Erişim Belirleyicileri ve Paket Güvenliği](#5-paket-düzeyi-erişim-belirleyicileri-ve-paket-güvenliği)
   - [Varsayılan (Package-Private) Erişim](#varsayılan-package-private-erişim)
   - [`protected` Erişiminin Paketler Arası Davranışı](#protected-erişiminin-paketler-arası-davranışı)
6. [Komut Satırından Paket Derleme ve Çalıştırma](#6-komut-satırından-paket-derleme-ve-çalıştırma)
   - [`javac -d . Dosya.java` ile Dizinleri Otomatik Üretme](#javac--d--dosyajava-ile-dizinleri-otomatik-üretme)
   - [Paketli Sınıfı Çalıştırma (`java paket.adi.Sinif`)](#paketli-sınıfı-çalıştırma)
   - [CLASSPATH Kavramı](#classpath-kavramı)
7. [Adım Adım Çalıştırılabilir Kod Örnekleri](#7-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Katmanlı Paket Mimarisi (`model`, `service`, `app`)](#örnek-1-katmanlı-paket-mimarisi)
   - [Örnek 2: `import static` Kullanımı (`MatematikTest.java`)](#örnek-2-import-static-kullanımı)
8. [Sık Yapılan Hatalar ve Önemli Kurallar](#8-sık-yapılan-hatalar-ve-önemli-kurallar)
9. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#9-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
10. [Ödev Konuları ve Mini Projeler](#10-ödev-konuları-ve-mini-projeler)

---

## 1. Paket (Package) Nedir ve Neden Gerekir?

Büyük bir işletim sisteminde masaüstünüzde 10.000 tane dosyanın dağınık durduğunu düşünün: Hangi dosyanın ne işe yaradığını bulamazsınız, aynı isimde iki dosya oluşturamazsınız. Bu yüzden dosyalarınızı **Klasörlere (Müzikler, Belgeler, Resimler)** bölersiniz.

Java'da **Paket (Package)**; birbiriyle ilişkili sınıfları, arayüzleri ve enum yapılarını mantıksal ve fiziksel bir **dizin çatısı altında toplayan** mekanizmadır.

```
                    JAVA PROJESİ
                          |
     +--------------------+--------------------+
     |                    |                    |
[ com.eticaret.model ] [ com.eticaret.service ] [ com.eticaret.util ]
  - Urun.java            - OdemeServisi.java      - TarihAraclari.java
  - Musteri.java         - KargoServisi.java      - GuvenlikUtil.java
  - Siparis.java
```

---

### İsim Çakışmasını (Name Collision) Önleme

Gerçek dünyada iki farklı kütüphanede aynı isimde sınıflar bulunabilir. Örneğin:
- Java'nın yerleşik tarih sınıfı: `java.util.Date`
- SQL veritabanı tarih sınıfı: `java.sql.Date`

Eğer paketler olmasaydı, Java aynı isimli iki sınıfı birbirinden ayırt edemezdi. Paketler sayesinde sınıfların **Tam Nitelikli İsimleri (Fully Qualified Name - FQN)** oluşur ve çakışma engellenir.

---

## 2. Paket Tanımlama ve İsimlendirme Standartları

### `package` Anahtar Kelimesi
Bir Java dosyasının hangi pakete ait olduğunu belirtmek için dosyanın **EN BAŞINA (yorum satırları hariç 1. kod satırına)** `package` ifadesi yazılır:

```java
package com.universite.ogrenci;

public class Ogrenci {
    // Sınıf gövdesi
}
```

> 🔴 **ALTIN KURAL:** `package` bildirimi mutlaka dosyanın en üstünde olmalıdır! `import` veya sınıf tanımlarından sonra yazılamaz.

---

### Ters Alan Adı Standardı (Reverse Domain Name)

Dünya çapında paket isimlerinin çakışmaması için endüstri standardı **şirketin internet alan adının ters yazılmasıdır**:

| Şirket / Kurum Web Sitesi | Standart Java Paket Başlangıcı |
| :--- | :--- |
| `google.com` | `com.google...` (örn: `com.google.common.collect`) |
| `oracle.com` | `com.oracle...` |
| `itu.edu.tr` | `tr.edu.itu...` |
| `apache.org` | `org.apache...` |

> 📝 **İsimlendirme Kuralları:**
> Paket isimlerinin **tüm harfleri KÜÇÜK YAZILIR** (`com.sirket.proje`). Büyük harf veya alt tire (`_`) kullanılmaz.

---

### Paket ve Dizin Hiyerarşisi Eşleşme Zorunluluğu

Java'da paket ismi ile dosyanın bilgisayardaki fiziksel klasör yolu **BİREBİR AYNI** olmak zorundadır!
- Paket: `package com.sirket.muhasebe;`
- Dosya Yolu: `src/com/sirket/muhasebe/Fatura.java`

---

## 3. Sınıfları İçe Aktarma: `import` Deyimi

Farklı bir pakette bulunan bir sınıfı projenizde kullanmak istediğinizde Java derleyicisine o sınıfın nerede olduğunu haber vermeniz gerekir. Buna **içe aktarma (`import`)** denir.

```java
package com.sirket.uygulama;

// Başka paketteki sınıfı içeri aktarıyoruz:
import com.sirket.muhasebe.Fatura;

public class Main {
    public static void main(String[] args) {
        Fatura f = new Fatura(); // Artık doğrudan kullanabiliriz!
    }
}
```

---

### Tekil İçe Aktarma vs. Joker Karakter (`*`)

```java
// 1. Tekil İçe Aktarma (Tavsiye Edilen):
import java.util.ArrayList;
import java.util.List;

// 2. Joker Karakter ile Paket İçi Tüm Sınıfları İçe Aktarma:
import java.util.*;
```

> 💡 **Performans Efsanesi:** `import java.util.*;` yazmak programınızın yavaş çalışmasına veya hafızada fazla yer kaplamasına **YOL AÇMAZ!** Çünkü `import` sadece derleyicinin arama motorudur, bayt koduna gereksiz sınıfları yüklemez. Ancak kod okunabilirliği ve isim çakışmalarını önlemek için tek tek içe aktarma profesyonel projelerde tercih edilir.

---

### Otomatik İçe Aktarılan Özel Paket: `java.lang`

Java'da `System`, `String`, `Math`, `Integer`, `Object`, `Thread` gibi en temel sınıfları kullanırken hiçbir zaman `import java.lang.String;` yazmadınız. 
Çünkü **`java.lang` paketi Java tarafından her dosyaya GİZLİ VE OTOMATİK OLARAK İÇE AKTARILIR!**

---

### Aynı İsimli İki Sınıfın Çakışması

Hem `java.util.Date` hem de `java.sql.Date` sınıflarını aynı dosyada kullanmak isterseniz:

```java
import java.util.Date; // Standart import

public class TarihTest {
    Date utilTarih = new Date(); // java.util.Date
    
    // İkinci aynı isimli sınıfı tam yoluyla (FQN) yazmak zorundayız:
    java.sql.Date sqlTarih = new java.sql.Date(System.currentTimeMillis());
}
```

---

## 4. Statik İçe Aktarma (`import static`)

Java 5 ile gelen `import static`, bir sınıfın **`static` metot ve sabitlerini** sınıf adını yazmadan doğrudan kendi metodumuz gibi çağırabilmemizi sağlar:

```java
// Standart Kullanım:
double alan = Math.PI * Math.pow(r, 2);

// Statik İçe Aktarma ile:
import static java.lang.Math.PI;
import static java.lang.Math.pow;

// Sınıf içinde:
double alan = PI * pow(r, 2); // Math yazmaya gerek kalmadı!
```

> ⚠️ **Dikkatli Kullanım Uyarısı:** Statik import aşırı kullanılırsa kodun hangi metodunun nereden geldiği belirsizleşir. Genellikle sadece matematiksel formüllerde veya birim testlerinde (`assertEquals`) tercih edilmelidir.

---

## 5. Paket Düzeyi Erişim Belirleyicileri ve Güvenlik

Paketler sadece organizasyon sağlamaz, aynı zamanda bir **erişim güvenlik duvarıdır**:

1. **Varsayılan (Package-Private / Belirleyicisiz):** Başına `public`, `private` veya `protected` yazılmayan sınıf veya üyeler **sadece ve sadece aynı paket içinden görülebilir**. Dış paketlere tamamen kapalıdır.
2. **`protected`:** Aynı paketteki sınıflara açıktır. Farklı paketteki bir sınıf ise yalnızca o sınıftan **miras alıyorsa (`extends`)** erişebilir.
3. **`public`:** Projedeki her paketten `import` edilerek erişilebilir.

---

## 6. Komut Satırından Paket Derleme ve Çalıştırma

Öğrencilerin terminalde en çok zorlandığı konu paketli kodların derlenmesidir.

Diyelim ki şu dosyaya sahibiz: `src/com/okul/Main.java`

### 1. Adım: `-d` (Directory) Parametresiyle Derleme
`-d .` parametresi, Java derleyicisine dosyanın içindeki `package com.okul;` satırını okumasını ve klasörleri (`com/okul/`) otomatik olarak oluşturup `.class` dosyasını içine koymasını söyler:

```bash
# src klasöründeyken:
javac -d . com/okul/Main.java
```
Bu komuttan sonra `com/okul/Main.class` dosyası oluşur.

### 2. Adım: Paketli Sınıfı Çalıştırma
Paketli bir sınıf çalıştırılırken sınıfın **Tam Nitelikli Adı (FQN)** verilir:

```bash
# Hatalı: java Main ❌ (Bulamaz!)
# Hatalı: cd com/okul && java Main ❌ (Paket kökünü kaybeder!)

# Doğru: Paket kök dizinindeyken tam paket adıyla çalıştırılır:
java com.okul.Main ✅
```

---

## 7. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Katmanlı Paket Mimarisi

Bir e-ticaret uygulamasındaki katmanlı paket mimarisi:

#### Paket 1: `com.eticaret.model.Urun.java`
```java
package com.eticaret.model;

public class Urun {
    private String ad;
    private double fiyat;

    public Urun(String ad, double fiyat) {
        this.ad = ad;
        this.fiyat = fiyat;
    }

    public String getAd() { return ad; }
    public double getFiyat() { return fiyat; }

    @Override
    public String toString() {
        return ad + " (" + fiyat + " TL)";
    }
}
```

#### Paket 2: `com.eticaret.service.SiparisServisi.java`
```java
package com.eticaret.service;

// Model paketindeki Urun sınıfını içe aktarıyoruz:
import com.eticaret.model.Urun;
import java.util.List;

public class SiparisServisi {
    public static double toplamHesapla(List<Urun> sepet) {
        double toplam = 0;
        for (Urun u : sepet) {
            toplam += u.getFiyat();
        }
        return toplam;
    }
}
```

#### Paket 3: `com.eticaret.app.Main.java`
```java
package com.eticaret.app;

// Diğer paketlerdeki sınıfları çağırıyoruz:
import com.eticaret.model.Urun;
import com.eticaret.service.SiparisServisi;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Urun> sepet = new ArrayList<>();
        sepet.add(new Urun("Mekanik Klavye", 1200.0));
        sepet.add(new Urun("Kablosuz Mouse", 450.0));

        System.out.println("Sepetteki Ürünler: " + sepet);
        double toplamTutar = SiparisServisi.toplamHesapla(sepet);
        System.out.printf("Toplam Ödenecek Tutar: %.2f TL\n", toplamTutar);
    }
}
```

---

### Örnek 2: `import static` Kullanımı

#### `MatematikTest.java`
```java
package com.matematik.test;

// Math sınıfının statik üyelerini doğrudan içeri alıyoruz:
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class MatematikTest {
    public static void main(String[] args) {
        double yaricap = 5.0;

        // Math.PI ve Math.pow yerine doğrudan yazıyoruz:
        double daireAlani = PI * pow(yaricap, 2);
        System.out.println("Daire Alanı : " + daireAlani);

        // Pisagor: c = sqrt(a^2 + b^2)
        double hipotenus = sqrt(pow(3, 2) + pow(4, 2));
        System.out.println("3-4-5 Hipotenüs: " + hipotenus);
    }
}
```

---

## 8. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `package` Satırını `import` Satırlarının Altına Yazmak
```java
import java.util.List;
package com.okul; // ❌ HATA! package satırı en üstte olmak zorundadır.
```

---

### 2. Dosya Konumu ile Paket İsminin Uyuşmaması
Paketiniz `package com.banka.hesap;` ise, dosyanız mutlaka `com/banka/hesap/` klasörünün içinde olmalıdır. Aksi halde derleyici:
`Error: Could not find or load main class` hatası verir.

---

### 3. Paket İçindeki Sınıfı Dizin İçine Girip Çalıştırmaya Kalkışmak
`com/okul/Main.class` dosyasını çalıştırmak için `com/okul` klasörünün içine girip `java Main` derseniz Java paketi tanıyamaz.
✅ **Kural:** Daima paketlerin en dışındaki kök klasörde durup `java com.okul.Main` komutuyla çalıştırınız.

---

## 9. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Paket İsimlendirme Uygunluk Testi
Aşağıdaki paket isimlerinden hangileri Java standartlarına uygundur?
1. `com.sirket.PROJE`
2. `com.firma.musteri_yonetimi`
3. `tr.edu.marmara.bilgisayar`
4. `package1`

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

- **1. Hatalı:** Paket isimlerinde büyük harf kullanılmaz.
- **2. Hatalı:** Paket isimlerinde alt tire (`_`) tercih edilmez (`com.firma.musteriyonetimi` olmalıdır).
- **3. UYGUN:** Ters alan adı ve tamamı küçük harf standardına mükemmel uyar.
- **4. Uygunsuz:** Gerçek projelerde anlamsızdır ve alan adı içermez.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında neden derleme hatası alınmaktadır?

```java
import java.util.Date;
import java.sql.Date;

public class Test {
    Date d = new Date();
}
```

<details>
<summary>🔍 <b>Hata ve Çözümü Görmek İçin Tıklayın</b></summary>

**Hata:** İki farklı paketten aynı isimli (`Date`) sınıf `import` edilmiştir (`reference to Date is ambiguous`).
**Çözüm:** Biri tam paket adıyla (`java.sql.Date sqlDate = ...`) yazılmalı, import satırlarından biri silinmelidir.
</details>

---

### Alıştırma 3: Statik İçe Aktarma Dönüşümü
Standart `System.out.println("Merhaba");` ifadesini `import static` kullanarak `out.println("Merhaba");` şeklinde yazmayı deneyiniz.

---

## 10. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Geometri Katmanlı Paketi (`com.geometri`)

**Gereksinimler:**
1. Bilgisayarınızda şu paket hiyerarşisini oluşturunuz:
   - `com.geometri.sekiller` $\rightarrow$ `Daire.java`, `Kare.java`.
   - `com.geometri.hesaplayici` $\rightarrow$ `AlanHesaplayici.java`.
   - `com.geometri.ana` $\rightarrow$ `Main.java`.
2. Terminalden `javac -d .` komutuyla tüm sınıfları derleyip `java com.geometri.ana.Main` ile çalıştırarak paket yapısını doğrulayınız.

---

### 🟡 Ödev 2 (Orta Seviye): Kütüphane Otomasyonu Katmanlı Mimarisi

**Gereksinimler:**
1. Paket Mimarisi:
   - `org.kutuphane.model`: `Kitap`, `Uye` sınıfları (tüm alanlar kapsüllenmiş).
   - `org.kutuphane.depo`: `KitapDeposu` sınıfı (içinde `List<Kitap>` koleksiyonu olan sınıf).
   - `org.kutuphane.servis`: `OduncServisi` sınıfı (`kitapOduncVer(Kitap k, Uye u)` metodu).
   - `org.kutuphane.uygulama`: `KutuphaneApp` (`main` metodu).
2. Erişim belirleyicileri doğru kullanarak sadece gerekli sınıfları `public` yapınız; yardımcı sınıfları paket içinde (*package-private*) gizleyiniz.

---

### 🔴 Ödev 3 (İleri Seviye): JAR Kütüphanesi Paketleme ve Harici Kullanım

**Gereksinimler:**
1. Kendi yazdığınız matematiksel araçları içeren `com.matematik.araclar` paketini derleyiniz.
2. Terminalde `jar -cvf matematik-araclar.jar com/matematik/araclar/*.class` komutuyla bir **JAR (Java Archive)** dosyası üretiniz.
3. Tamamen farklı bir klasörde yeni bir proje açıp, oluşturduğunuz bu `.jar` dosyasını `CLASSPATH` olarak projeye bağlayınız ve sınıflarınızı içe aktararak çalıştırınız.
