# 4. Java'da Kapsülleme (Encapsulation) ve Getter / Setter Metotları

Bu doküman; Nesne Yönelimli Programlamanın (OOP) 4 temel direğinden birincisi olan **Kapsülleme (Encapsulation / Veri Gizleme)** ilkesini, sınıf verilerinin neden `private` yapılması gerektiğini, **Getter ve Setter** metotlarının işlevini, **veri doğrulama (validation)** mantığını, salt okunur/yazılır alanları ve **savunmacı kopyalama (defensive copying)** prensiplerini ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: static Anahtar Kelimesi ve Sınıf Üyeleri (3_static-class.md)](./3_static-class.md)
> - 🔜 [Sonraki Bölüm: Sınıflar, Metotlar ve Nesne Aktarımı (5_class_ve_fonksiyonlar.md)](./5_class_ve_fonksiyonlar.md)

---

## 📌 İçindekiler
1. [Kapsülleme (Encapsulation) Nedir?](#1-kapsülleme-encapsulation-nedir)
   - [Gerçek Dünya Analojisi: Tıbbi Kapsül ve Araba Pedalları](#gerçek-dünya-analojisi-tıbbi-kapsül-ve-araba-pedalları)
   - [Veri Gizleme (Information / Data Hiding) Prensibi](#veri-gizleme-information--data-hiding-prensibi)
2. [Kapsüllemenin 2 Temel Kuralı](#2-kapsüllemenin-2-temel-kuralı)
   - [Alanları `private` Yapmak](#1-alanları-private-yapmak)
   - [Erişimi `public` Getter ve Setter Metotları ile Sağlamak](#2-erişimi-public-getter-ve-setter-metotları-ile-sağlamak)
3. [Getter ve Setter Metotlarının Yapısı ve İsimlendirme Kuralları](#3-getter-ve-setter-metotlarının-yapısı-ve-isimlendirme-kuralları)
   - [Standart Getter / Setter Sözdizimi](#standart-getter--setter-sözdizimi)
   - [`boolean` Alanlar İçin Özel Kural (`is...`)](#boolean-alanlar-için-özel-kural-is)
4. [Kapsüllemenin Sağladığı Kritik Avantajlar](#4-kapsüllemenin-sağladığı-kritik-avantajlar)
   - [1. Veri Doğrulama ve Güvenlik (Validation)](#1-veri-doğrulama-ve-güvenlik-validation)
   - [2. Salt Okunur (Read-Only) ve Salt Yazılır (Write-Only) Tasarımlar](#2-salt-okunur-read-only-ve-salt-yazılır-write-only-tasarımlar)
   - [3. Hesaplanmış / Sanal Alanlar (Calculated Properties)](#3-hesaplanmış--sanal-alanlar-calculated-properties)
   - [4. Esneklik ve İç Yapıyı Dış Dünyadan Bağımsız Değiştirebilme](#4-esneklik-ve-iç-yapıyı-dış-dünyadan-bağımsız-değiştirebilme)
5. [İleri Düzey İpucu: Savunmacı Kopyalama (Defensive Copying)](#5-ileri-düzey-ipucu-savunmacı-kopyalama-defensive-copying)
6. [Adım Adım Çalıştırılabilir Kod Örnekleri](#6-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Temel Kapsülleme ve Doğrulama (`Kullanici.java` & `KullaniciTest.java`)](#örnek-1-temel-kapsülleme-ve-doğrulama)
   - [Örnek 2: Salt Okunur Alanlar ve Dinamik Hesaplama (`OgrenciNot.java`)](#örnek-2-salt-okunur-alanlar-ve-dinamik-hesaplama)
   - [Örnek 3: Güvenli Banka Hesabı ve İşlem Kaydı (`BankaHesabi.java`)](#örnek-3-güvenli-banka-hesabı-ve-işlem-kaydı)
7. [Sık Yapılan Hatalar ve Önemli Kurallar](#7-sık-yapılan-hatalar-ve-önemli-kurallar)
8. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
9. [Ödev Konuları ve Mini Projeler](#9-ödev-konuları-ve-mini-projeler)

---

## 1. Kapsülleme (Encapsulation) Nedir?

Nesne Yönelimli Programlamanın (OOP) en temel ilkesi **Kapsülleme (Encapsulation)**'dir. 

Kapsülleme iki şeyi ifade eder:
1. Bir nesneye ait **verileri (değişkenleri)** ve bu veriler üzerinde işlem yapan **metotları tek bir sınıf çatısı altında bir araya getirmek**.
2. Nesnenin iç verilerini dış dünyadan doğrudan müdahaleye **gizlemek (Information Hiding)** ve yalnızca belirlenmiş güvenli kapılardan (metotlardan) kontrollü erişim izni vermek.

---

### Gerçek Dünya Analojisi: Tıbbi Kapsül ve Araba Pedalları

```
               GÜVENSİZ YAKLAŞIM (Kapsüllemesiz)
   Dış Dünya -------------------------> motor.benzinEnjeksiyonu = -500;
   (Herkes doğrudan iç mekanizmayı bozar)

               GÜVENLİ YAKLAŞIM (Kapsüllü / Encapsulated)
   Dış Dünya ----> [ GAZ PEDALI (Setter) ] ----> Kontrol Edilir ----> Motor
                   (public metot)               (Hızlanır)
```

- **Tıbbi Kapsül:** İlacın içindeki kimyasal tozlar doğrudan mideye dağılmaz; jelatin bir kapsülle sarılıdır. Vücut sadece kapsülün dış yüzeyiyle temas eder.
- **Araba Kullanımı:** Gaza bastığınızda motordaki yakıt enjektörlerini elinizle ayarlamazsınız. Araba size gaz pedalı adında güvenli bir **arayüz (interface / metot)** sunar. Siz pedala basarsınız, araba kendi içinde hız sınırını ve yakıt akışını kontrol eder.
- **Banka ATM'si:** Bankanın para kasasını doğrudan müşterilere açmazsınız. Müşteri para çekme butonuna (metot) basar; sistem şifreyi ve limiti doğrularsa parayı verir.

---

### Veri Gizleme (Information / Data Hiding) Prensibi

Eğer bir sınıfın değişkenleri `public` yapılırsa, projenin herhangi bir yerinden istenmeyen veya saçma değerler atanabilir:

```java
// ❌ KAPSÜLLEME YOK! (Tehlikeli ve Kötü Tasarım)
class Kisi {
    public int yas;
}

// Programın bir yerinde:
Kisi k = new Kisi();
k.yas = -45;  // Mantıksız! Yaş negatif olamaz.
k.yas = 999;  // Mantıksız! İnsan 999 yaşında olamaz.
```

Kapsülleme sayesinde sınıf, **kendi verisinin bekçisi** haline gelir. Geçersiz verilerin sınıfa sızması engellenir.

---

## 2. Kapsüllemenin 2 Temel Kuralı

Java'da tam anlamıyla kapsüllenmiş (*fully encapsulated*) bir sınıf yazmak için **2 standart adım** uygulanır:

```
+---------------------------------------------------------------+
| 1. ADIM: Tüm alanları (fields) "private" tanımla.             |
|          (Doğrudan dış erişimi tamamen engelle)               |
+---------------------------------------------------------------+
                               |
                               v
+---------------------------------------------------------------+
| 2. ADIM: Her alan için "public" Getter ve Setter metotları    |
|          sağla. (Kontrollü okuma ve yazma kapıları aç)        |
+---------------------------------------------------------------+
```

```java
public class Kisi {
    // 1. Adım: Veri gizlendi
    private int yas;

    // 2. Adım: Okuma kapısı (Getter)
    public int getYas() {
        return this.yas;
    }

    // 2. Adım: Yazma ve Denetim kapısı (Setter)
    public void setYas(int yas) {
        if (yas >= 0 && yas <= 130) {
            this.yas = yas;
        } else {
            System.out.println("Hata: Geçersiz yaş değeri (" + yas + ")!");
        }
    }
}
```

---

## 3. Getter ve Setter Metotlarının Yapısı ve İsimlendirme Kuralları

Java topluluğunda ve endüstri standardında (**JavaBeans Spesifikasyonu**) getter ve setter metotları katı isimlendirme kurallarına bağlıdır:

### Standart Getter / Setter Sözdizimi

| Metot Türü | Görevi | Dönüş Tipi | Parametre | İsimlendirme Kuralı |
| :--- | :--- | :--- | :--- | :--- |
| **Getter (Okuyucu)** | Değeri dışarıya okutur | Değişkenin türü ile aynı | Almaz | `get` + `BüyükHarfleAlanAdi()` |
| **Setter (Yazıcı)** | Dışarıdan değeri alır ve atar | `void` | Değişkenin türünde 1 parametre | `set` + `BüyükHarfleAlanAdi(tip deger)` |

Örnekler:
- `private String ad;` $\rightarrow$ `getAd()`, `setAd(String ad)`
- `private double maas;` $\rightarrow$ `getMaas()`, `setMaas(double maas)`
- `private int ogrenciNo;` $\rightarrow$ `getOgrenciNo()`, `setOgrenciNo(int ogrenciNo)`

---

### `boolean` Alanlar İçin Özel Kural (`is...`)

`boolean` (mantıksal) tipindeki değişkenlerin getter metotları `get` yerine genellikle **`is`** ile başlar:

```java
public class Uye {
    private boolean aktif;

    // Getter: getAktif() yerine isAktif() tercih edilir
    public boolean isAktif() {
        return this.aktif;
    }

    // Setter: Standart setAktif
    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }
}
```

Kullanımı if bloklarında çok daha doğal ve akıcı bir İngilizce oluşturur:
```java
if (uye.isAktif()) {
    System.out.println("Giriş başarılı.");
}
```

---

## 4. Kapsüllemenin Sağladığı Kritik Avantajlar

### 1. Veri Doğrulama ve Güvenlik (Validation)
Setter metotları sayesinde alana atanacak veriler filtrelerden, regex kontrollerinden veya mantıksal sınırlardan geçirilebilir:

```java
public void setEposta(String eposta) {
    if (eposta != null && eposta.contains("@") && eposta.contains(".")) {
        this.eposta = eposta;
    } else {
        System.out.println("Geçersiz e-posta formatı!");
    }
}
```

---

### 2. Salt Okunur (Read-Only) ve Salt Yazılır (Write-Only) Tasarımlar

Her değişkene hem getter hem setter yazmak **zorunda değilsiniz**:
- **Salt Okunur (Read-Only):** Sadece `get...()` metodunu yazarsınız, `set...()` metodunu **yazmazsınız**. Değer yalnızca kurucuda atanır, dışarıdan kimse değiştiremez.
- **Salt Yazılır (Write-Only):** Sadece `set...()` metodunu yazarsınız (örneğin kullanıcının şifresi; dışarıdan okunamasın ama güncellenebilsin).

```java
public class KimlikKarti {
    private final String tcKimlikNo; // Değiştirilemez
    private String sifre;

    public KimlikKarti(String tcKimlikNo, String sifre) {
        this.tcKimlikNo = tcKimlikNo;
        this.sifre = sifre;
    }

    // READ-ONLY: TC No sadece okunabilir, set metodu YOKTUR!
    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    // WRITE-ONLY: Şifre dışarıdan okunamaz (getSifre YOKTUR), sadece değiştirilebilir!
    public void setSifre(String yeniSifre) {
        if (yeniSifre.length() >= 6) {
            this.sifre = yeniSifre;
        }
    }
}
```

---

### 3. Hesaplanmış / Sanal Alanlar (Calculated Properties)

Bir sınıfın sunduğu bilgi mutlaka bellekte ayrı bir değişken olarak tutulmak zorunda değildir. Getter metodu anlık bir hesaplama yapıp sonucu dönebilir:

```java
public class Dikdortgen {
    private double en;
    private double boy;

    // "alan" adında bir değişken tutmuyoruz; getter anında hesaplıyor!
    public double getAlan() {
        return this.en * this.boy;
    }
}
```

Bu sayede `en` veya `boy` değiştiğinde `alan` değişkeninin güncellenmesini unutma riski tamamen ortadan kalkar.

---

### 4. Esneklik ve İç Yapıyı Dış Dünyadan Bağımsız Değiştirebilme

Sınıfınızın içindeki değişkenin adını veya veri tipini değiştirdiğinizde, dışarıdaki kodların bozulmasını önleyebilirsiniz. 
Örneğin ilk başta isim ve soyismi tek değişkende tutuyordunuz: `private String tamAd;`. Sonradan ikiye böldünüz: `private String ad; private String soyad;`.
Getter metodunuzu şöyle güncelleyebilirsiniz:
```java
public String getTamAd() {
    return ad + " " + soyad; // Dışarıdaki 100 farklı sınıf bu değişiklikten hiç etkilenmez!
}
```

---

## 5. İleri Düzey İpucu: Savunmacı Kopyalama (Defensive Copying)

Kapsülleme yaparken yeni başlayanların düştüğü en sinsi tuzak: **Referans tipli (Mutable) nesneleri getter ile doğrudan dışarıya açmaktır.**

```java
public class SinifListesi {
    private int[] notlar = {80, 90, 100};

    // ⚠️ TEHLİKELİ GETTER:
    public int[] getNotlar() {
        return this.notlar; // Dizinin Heap adresini dışarıya verir!
    }
}
```

Bu durumda dışarıdan biri veriyi bozabilir:
```java
SinifListesi liste = new SinifListesi();
int[] disDizi = liste.getNotlar();
disDizi[0] = -999; // ❌ Kapsülleme kırıldı! Sınıfın içindeki orijinal dizi bozuldu!
```

✅ **Savunmacı Kopyalama Çözümü:** Referansın kendisini değil, bir **kopyasını (klonunu)** dışarıya dönün:
```java
public int[] getNotlar() {
    return this.notlar.clone(); // ✅ Orijinal dizi güvendedir!
}
```

---

## 6. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Temel Kapsülleme ve Doğrulama

#### `Kullanici.java`
```java
public class Kullanici {
    private String kullaniciAdi;
    private String eposta;
    private int yas;

    public Kullanici(String kullaniciAdi, String eposta, int yas) {
        // Kurucu içinde de setter'ları çağırarak doğrulamaları garantiye alıyoruz
        setKullaniciAdi(kullaniciAdi);
        setEposta(eposta);
        setYas(yas);
    }

    // --- GETTER & SETTER METOTLARI ---

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        if (kullaniciAdi != null && kullaniciAdi.trim().length() >= 3) {
            this.kullaniciAdi = kullaniciAdi.trim();
        } else {
            System.out.println("Hata: Kullanıcı adı en az 3 karakter olmalıdır!");
            this.kullaniciAdi = "GeçersizKullanici";
        }
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        if (eposta != null && eposta.contains("@") && eposta.contains(".")) {
            this.eposta = eposta;
        } else {
            System.out.println("Hata: Geçersiz e-posta adresi (" + eposta + ")!");
            this.eposta = "tanimsiz@eposta.com";
        }
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yas) {
        if (yas >= 18 && yas <= 100) {
            this.yas = yas;
        } else {
            System.out.println("Hata: Yaş 18 ile 100 arasında olmalıdır! Girilen: " + yas);
            this.yas = 18; // Varsayılan reşit yaş
        }
    }

    public void profilGoster() {
        System.out.println("--------------------------------");
        System.out.println("Kullanıcı Adı : " + kullaniciAdi);
        System.out.println("E-Posta       : " + eposta);
        System.out.println("Yaş           : " + yas);
        System.out.println("--------------------------------");
    }
}
```

#### `KullaniciTest.java`
```java
public class KullaniciTest {
    public static void main(String[] args) {
        System.out.println("=== 1. Doğru Kullanıcı Kaydı ===");
        Kullanici k1 = new Kullanici("ahmet_yazilim", "ahmet@gmail.com", 25);
        k1.profilGoster();

        System.out.println("\n=== 2. Hatalı Değer Giriş Denemeleri ===");
        // Geçersiz yaş, geçersiz kullanıcı adı ve geçersiz e-posta deneyelim
        Kullanici k2 = new Kullanici("ab", "hatalieposta", 15);
        k2.profilGoster();

        System.out.println("\n=== 3. Setter ile Güvenli Güncelleme ===");
        k2.setKullaniciAdi("baris_kaya");
        k2.setEposta("baris@sirket.com");
        k2.setYas(22);
        k2.profilGoster();
    }
}
```

#### Konsol Çıktısı:
```text
=== 1. Doğru Kullanıcı Kaydı ===
--------------------------------
Kullanıcı Adı : ahmet_yazilim
E-Posta       : ahmet@gmail.com
Yaş           : 25
--------------------------------

=== 2. Hatalı Değer Giriş Denemeleri ===
Hata: Kullanıcı adı en az 3 karakter olmalıdır!
Hata: Geçersiz e-posta adresi (hatalieposta)!
Hata: Yaş 18 ile 100 arasında olmalıdır! Girilen: 15
--------------------------------
Kullanıcı Adı : GeçersizKullanici
E-Posta       : tanimsiz@eposta.com
Yaş           : 18
--------------------------------

=== 3. Setter ile Güvenli Güncelleme ===
--------------------------------
Kullanıcı Adı : baris_kaya
E-Posta       : baris@sirket.com
Yaş           : 22
--------------------------------
```

---

### Örnek 2: Salt Okunur Alanlar ve Dinamik Hesaplama

#### `OgrenciNot.java`
```java
public class OgrenciNot {
    private final int ogrenciNo; // Değiştirilemez (final)
    private String adSoyad;
    private double vize;
    private double finalNotu;

    public OgrenciNot(int ogrenciNo, String adSoyad, double vize, double finalNotu) {
        this.ogrenciNo = ogrenciNo;
        this.adSoyad = adSoyad;
        setVize(vize);
        setFinalNotu(finalNotu);
    }

    // READ-ONLY: Numara yalnızca okunabilir
    public int getOgrenciNo() {
        return ogrenciNo;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public double getVize() {
        return vize;
    }

    public void setVize(double vize) {
        if (vize >= 0 && vize <= 100) {
            this.vize = vize;
        } else {
            System.out.println("Hata: Vize notu 0-100 arasında olmalıdır!");
        }
    }

    public double getFinalNotu() {
        return finalNotu;
    }

    public void setFinalNotu(double finalNotu) {
        if (finalNotu >= 0 && finalNotu <= 100) {
            this.finalNotu = finalNotu;
        } else {
            System.out.println("Hata: Final notu 0-100 arasında olmalıdır!");
        }
    }

    // HESAPLANMIŞ ALAN: Değişkeni yok, anlık hesaplanır
    public double getOrtalama() {
        return (vize * 0.4) + (finalNotu * 0.6);
    }

    // HESAPLANMIŞ ALAN: Harf notu dinamik üretilir
    public String getHarfNotu() {
        double ort = getOrtalama();
        if (ort >= 85) return "AA";
        if (ort >= 70) return "BA";
        if (ort >= 60) return "BB";
        if (ort >= 50) return "CC";
        return "FF";
    }

    // boolean Getter: is... ile başlar
    public boolean isGectiMi() {
        return getOrtalama() >= 50.0;
    }
}
```

#### `OgrenciTest.java`
```java
public class OgrenciTest {
    public static void main(String[] args) {
        OgrenciNot ogr = new OgrenciNot(2023001, "Selin Aydın", 75, 85);

        System.out.println("Öğrenci No : " + ogr.getOgrenciNo());
        System.out.println("Öğrenci Adı: " + ogr.getAdSoyad());
        System.out.println("Ortalama   : " + ogr.getOrtalama());
        System.out.println("Harf Notu  : " + ogr.getHarfNotu());
        System.out.println("Geçti mi?  : " + (ogr.isGectiMi() ? "EVET" : "HAYIR"));
    }
}
```

---

### Örnek 3: Güvenli Banka Hesabı ve İşlem Kaydı

#### `BankaHesabi.java`
```java
import java.util.ArrayList;
import java.util.List;

public class BankaHesabi {
    private final String hesapNo;
    private double bakiye;
    private final List<String> islemGecmisi = new ArrayList<>();

    public BankaHesabi(String hesapNo, double ilkBakiye) {
        this.hesapNo = hesapNo;
        if (ilkBakiye > 0) {
            this.bakiye = ilkBakiye;
            islemGecmisi.add("Hesap açılışı: +" + ilkBakiye + " TL");
        } else {
            this.bakiye = 0;
            islemGecmisi.add("Hesap açılışı: 0 TL");
        }
    }

    public String getHesapNo() {
        return hesapNo;
    }

    // Bakiyeyi doğrudan değiştirecek bir setBakiye() METODU YOKTUR!
    // Bakiye sadece paraYatir ve paraCek metotlarıyla değişebilir.
    public double getBakiye() {
        return bakiye;
    }

    public void paraYatir(double miktar) {
        if (miktar > 0) {
            bakiye += miktar;
            islemGecmisi.add("Para Yatırma: +" + miktar + " TL (Güncel Bakiye: " + bakiye + " TL)");
            System.out.println(miktar + " TL başarıyla yatırıldı.");
        } else {
            System.out.println("Hata: Yatırılacak miktar pozitif olmalıdır!");
        }
    }

    public boolean paraCek(double miktar) {
        if (miktar <= 0) {
            System.out.println("Hata: Çekilecek miktar pozitif olmalıdır!");
            return false;
        }
        if (miktar > bakiye) {
            System.out.println("Hata: Yetersiz bakiye! Mevcut: " + bakiye + " TL");
            return false;
        }

        bakiye -= miktar;
        islemGecmisi.add("Para Çekme: -" + miktar + " TL (Güncel Bakiye: " + bakiye + " TL)");
        System.out.println(miktar + " TL çekildi.");
        return true;
    }

    // Savunmacı kopyalama ile liste dışarı verilir
    public List<String> getIslemGecmisi() {
        return new ArrayList<>(this.islemGecmisi);
    }
}
```

---

## 7. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. "Her Şeye Körlemesine Getter ve Setter Yazmak"
Kapsülleme yalnızca `get` ve `set` üretmek değildir. Eğer bir alana hiçbir kural koymadan doğrudan `set` açıyorsanız, o alanı `public` yapmaktan çok az farkı kalır.
- Alanın dışarıdan değiştirilmesine gerek yoksa **setter yazmayınız**.
- Alanın dışarıdan okunmasına gerek yoksa **getter yazmayınız**.

---

### 2. Kurucu İçinde Setter Kontrollerini Atlamak
Kurucuda doğrudan `this.yas = yas;` derseniz, setter içine koyduğunuz kontroller aşılabilir (`new Kisi(-50)` gibi).
✅ **En İyi Pratik:** Kurucunun içinde de doğrudan `setYas(yas);` metodunu çağırarak veriyi süzgeçten geçirin.

---

### 3. `boolean` Alanlarda `getAktif()` İsimlendirmesi
Java standartlarında `boolean` alanlar için getter metodu `get...` değil, **`is...`** veya **`has...`** ile başlamalıdır (`isOgrenciMi()`, `hasIndirim()`).

---

### 4. Klonlamadan Referans Döndürmek
Nesne referanslarını (`List`, dizi `[]`, `Date`) getter ile doğrudan döndürmek dışarıdaki bir kodun sınıfın iç durumunu değiştirmesine sebep olur. Mutlaka kopya döndürülmelidir (*Defensive Copying*).

---

## 8. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Kod Analizi ve Güvenlik Açığı Tespiti
Aşağıdaki sınıfta kapsülleme prensiplerine aykırı **2 büyük tasarım hatasını** bulunuz:

```java
public class Termostat {
    public double hedefSicaklik;
    private double anlikSicaklik;

    public Termostat(double hedef, double anlik) {
        this.hedefSicaklik = hedef;
        this.anlikSicaklik = anlik;
    }

    public double getAnlikSicaklik() {
        return anlikSicaklik;
    }
}
```

<details>
<summary>🔍 <b>Hataları ve Çözümü Görmek İçin Tıklayın</b></summary>

1. **Hata 1:** `hedefSicaklik` değişkeni `public` yapılmıştır! Dışarıdan biri `termostat.hedefSicaklik = -500;` yazabilir. `private` yapılmalı ve 10°C ile 35°C aralığını kontrol eden bir `setHedefSicaklik` metodu eklenmelidir.
2. **Hata 2:** Kurucu metot parametreleri kontrolsüzce doğrudan atamaktadır.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında neden derleme hatası alınır ve nasıl düzeltilir?

```java
class Urun {
    private double fiyat;

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        if (fiyat > 0) {
            fiyat = fiyat; // DİKKAT!
        }
    }
}

public class Test {
    public static void main(String[] args) {
        Urun u = new Urun();
        u.setFiyat(100.0);
        System.out.println("Fiyat: " + u.getFiyat());
    }
}
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Sorun:** `setFiyat` içinde `fiyat = fiyat;` yazılmıştır. Parametre kendi kendine atanır, sınıfın `this.fiyat` alanına hiçbir şey yazılmaz! Konsola `Fiyat: 0.0` basılır.
**Düzeltme:** `this.fiyat = fiyat;` olarak değiştirilmelidir.
</details>

---

### Alıştırma 3: Küçük Tasarım (Güvenli Şifre Alanı)
Bir `HesapGuvenligi` sınıfı tasarlayınız:
- `private String sifre;`
- `setSifre(String yeniSifre)`: Şifre en az 8 karakter olmalı, boşluk içermemeli ve en az bir rakam barındırmalıdır. Şartlar sağlanmıyorsa şifreyi değiştirmeyip hata basmalıdır.
- `sifreyiDogrula(String denenenSifre)`: Girilen şifre doğruysa `true`, değilse `false` dönsün (Şifreyi dışarıya açıkça veren bir `getSifre()` metodu OLMAMALIDIR!).

---

## 9. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Sağlık Takipçisi (`KisiSaglik.java` & `SaglikTest.java`)

**Gereksinimler:**
1. `KisiSaglik` adında kapsüllenmiş bir sınıf oluşturunuz.
2. Özel Alanlar (`private`):
   - `String adSoyad;`
   - `double kilo;` (30 ile 250 kg arasında olmalı)
   - `double boy;` (Metre cinsinden, 0.50 ile 2.50 m arasında olmalı)
3. Metotlar:
   - Tüm alanlar için gerekli doğrulama kontrollerine sahip Getter ve Setter'lar.
   - `getVucutKitleIndeksi()`: $\text{VKİ} = \frac{\text{kilo}}{\text{boy}^2}$ formülü ile dinamik hesaplansın.
   - `getKiloDurumu()`: VKİ değerine göre `"Zayıf"`, `"Normal"`, `"Fazla Kilolu"`, `"Obez"` metinlerini döndürsün.
4. `SaglikTest` sınıfında geçerli ve geçersiz boy/kilo değerleri girerek sistemin tepkisini ve VKİ çıktısını test ediniz.

---

### 🟡 Ödev 2 (Orta Seviye): E-Ticaret Sepet Kalemi (`SepetUrunu.java`)

**Gereksinimler:**
1. `SepetUrunu` sınıfı tasarlayınız.
2. Alanlar:
   - `private final String urunKodu;` (Salt okunur)
   - `private String urunAdi;`
   - `private double birimFiyat;` (0'dan büyük olmalı)
   - `private int adet;` (En az 1, en fazla 50 olmalı)
   - `private double indirimOrani;` (0.0 ile 0.50 arasında yani maksimum %50 indirim)
3. Özel Metotlar:
   - `getAraToplam()`: $\text{birimFiyat} \times \text{adet}$ tutarını dönsün.
   - `getIndirimTutari()`: $\text{AraToplam} \times \text{indirimOrani}$ tutarını dönsün.
   - `getOdenecekTutar()`: $\text{AraToplam} - \text{IndirimTutari}$ tutarını dönsün.
4. Bir test sınıfı yazarak sepete ürün ekleyip adet ve indirim güncelleyiniz; dinamik hesaplanan toplamları yazdırınız.

---

### 🔴 Ödev 3 (İleri Seviye): TC Kimlik Doğrulamalı Vatandaş Kayıt Sistemi (`Vatandas.java`)

**Gereksinimler:**
1. `Vatandas` adında tam kapsüllenmiş bir sınıf tasarlayınız.
2. Alanlar:
   - `private String tcNo;`
   - `private String ad;`
   - `private String soyad;`
   - `private int dogumYili;`
3. Katı Doğrulama Kuralları (`setTcNo` içinde):
   - TC Kimlik No tam olarak **11 haneli** olmalıdır.
   - Sadece rakamlardan oluşmalıdır.
   - İlk hanesi **`0` olamaz**.
   - (İsteğe bağlı bonus): 11. hane kural kontrolü (İlk 10 hanenin toplamının mod 10'u 11. haneye eşit olmalıdır).
4. `dogumYili` alanı 1900 ile günümüz yılı arasında olmalıdır.
5. `getYas()` hesaplanmış getter metodu ekleyiniz.
6. Hatalı TC numaraları girildiğinde sistemin bunu reddettiğini, doğru girildiğinde kaydı tamamladığını test eden kapsamlı bir test sınıfı yazınız.

