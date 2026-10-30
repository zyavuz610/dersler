# 2. Java'da Karar Yapıları: `switch-case` Kılavuzu

Bu rehber, Java programlama dilinde bir değişkenin veya ifadenin birden çok sabit değere eşitliğini zarif, okunabilir ve yüksek performansla test etmemizi sağlayan **`switch-case` karar yapısını**, geleneksel sözdizimini, basamaklı akış (*fall-through*) mantığını, Java 12/14+ ile gelen modern ok (*arrow `->`*) ve `yield` sözdizimini, `if-else` ile derinlemesine karşılaştırmasını, yaygın tuzakları ve öğrencilerin çözebileceği zengin alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 1_koşul ve döngü**  
> İlgili Bir Önceki Doküman: [1_if-else.md](./1_if-else.md)

---

## 📌 İçindekiler
1. [`switch-case` Nedir ve Ne Amaçla Kullanılır?](#1-switch-case-nedir-ve-ne-amaçla-kullanılır)
2. [Geleneksel `switch-case` Sözdizimi ve Çalışma Mantığı](#2-geleneksel-switch-case-sözdizimi-ve-çalışma-mantığı)
3. [`break` Anahtar Kelimesi ve Fall-Through (Aşağı Düşme) Mekanizması](#3-break-anahtar-kelimesi-ve-fall-through-aşağı-düşme-mekanizması)
4. [`default` Durumu ve Konum Esnekliği](#4-default-durumu-ve-konum-esnekliği)
5. [Java'da Desteklenen ve Desteklenmeyen Veri Tipleri](#5-javada-desteklenen-ve-desteklenmeyen-veri-tipleri)
6. [Modern Java: Gelişmiş `switch` (Arrow `->` ve `yield` - Java 12/14+)](#6-modern-java-gelişmiş-switch-arrow---ve-yield---java-1214)
7. [Büyük Karşılaştırma: `if-else` vs `switch-case`](#7-büyük-karşılaştırma-if-else-vs-switch-case)
8. [En Sık Yapılan Hatalar ve Tuzaklar](#8-en-sık-yapılan-hatalar-ve-tuzaklar)
9. [Kapsamlı Örnek Konsol Uygulaması: `AkilliHesapMakinesi`](#9-kapsamlı-örnek-konsol-uygulaması-akillihesapmakinesi)
10. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#10-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
11. [Ödev Projeleri](#11-ödev-projeleri)

---

## 1. `switch-case` Nedir ve Ne Amaçla Kullanılır?

Programlama yaparken sıklıkla **tek bir değişkenin değerini önceden bilinen sabit seçeneklerle kıyaslarız**:
- Haftanın günü (1 ise Pazartesi, 2 ise Salı...),
- Menü seçimi (1: Bakiye Sorgula, 2: Para Yatır, 3: Para Çek...),
- Klavyeden basılan yön tuşu ('W', 'A', 'S', 'D'),
- HTTP durum kodları (200: OK, 404: Not Found, 500: Server Error...).

Bu gibi eşitlik denetimlerinde arka arkaya 10-15 tane `else if (secim == 1)`, `else if (secim == 2)` yazmak kodu hem hantal ve okunaksız hale getirir hem de derleyici optimizasyonlarını zorlaştırır.

`switch-case` yapısı, **tek bir anahtar ifadeyi alarak doğrudan eşleşen durum etiketine (`case`) sıçramayı (jump)** sağlayan çok yönlü bir karar yapısıdır.

---

## 2. Geleneksel `switch-case` Sözdizimi ve Çalışma Mantığı

Klasik `switch` yapısı; test edilecek bir ifade, bu ifadenin alabileceği değerleri temsil eden `case` etiketleri, her durumun sonuna konulan `break` komutu ve hiçbir durum tutmadığında devreye giren bir `default` bloğundan oluşur.

### 📌 Genel Sözdizimi:

```java
switch (kontrol_edilecek_degisken) {
    case DEGER_1:
        // degisken == DEGER_1 ise çalışacak kodlar
        break; // switch bloğunu sonlandırır
    case DEGER_2:
        // degisken == DEGER_2 ise çalışacak kodlar
        break;
    case DEGER_3:
        // degisken == DEGER_3 ise çalışacak kodlar
        break;
    default:
        // Hiçbir case ile eşleşme olmazsa çalışacak kodlar
        break;
}
```

### 💻 Örnek 1: Haftanın Gününü Belirleme

```java
public class HaftaninGunu {
    public static void main(String[] args) {
        int gunNo = 3;

        switch (gunNo) {
            case 1:
                System.out.println("Pazartesi");
                break;
            case 2:
                System.out.println("Salı");
                break;
            case 3:
                System.out.println("Çarşamba");
                break;
            case 4:
                System.out.println("Perşembe");
                break;
            case 5:
                System.out.println("Cuma");
                break;
            case 6:
                System.out.println("Cumartesi");
                break;
            case 7:
                System.out.println("Pazar");
                break;
            default:
                System.out.println("Geçersiz gün numarası! (1-7 arası olmalı)");
                break;
        }
    }
}
```

**Ekran Çıktısı:**
```text
Çarşamba
```

---

## 3. `break` Anahtar Kelimesi ve Fall-Through (Aşağı Düşme) Mekanizması

### ❓ `break` Neden Hayatidir?
`switch` bloğu çalışırken Java eşleşen `case` etiketini bulduğu anda o noktadan itibaren kodları çalıştırmaya başlar. Eğer satırın sonunda `break;` görmezse, **alttaki diğer `case` koşulları doğru olsun ya da olmasın**, bir sonraki `break` komutuna veya `switch` bloğunun sonuna gelene kadar bütün kodları sırayla çalıştırır. Bu olaya **Fall-Through (Aşağı Düşme)** denir.

### ⚠️ İstenmeyen Fall-Through (Klasik Acemi Hatası):

```java
int seviye = 2;

switch (seviye) {
    case 1:
        System.out.println("Başlangıç Seviyesi");
    case 2:
        System.out.println("Orta Seviye"); // Eşleşti, burası çalışır
    case 3:
        System.out.println("İleri Seviye"); // break olmadığı için BURASI DA ÇALIŞIR!
    default:
        System.out.println("Seviye Belirsiz"); // BURASI DA ÇALIŞIR!
}
```
**İstenmeyen Çıktı:**
```text
Orta Seviye
İleri Seviye
Seviye Belirsiz
```

---

### 💡 Bilinçli Fall-Through Kullanımı (Birden Fazla Değere Aynı İşlemi Yapma):

Fall-through her zaman bir hata değildir; doğru kullanıldığında kodu ciddi ölçüde kısaltır. Birden fazla değer için aynı sonucu üretmek istediğimizde aradaki `break` ifadelerini bilerek kaldırırız:

```java
public class GunTuruBelirleyici {
    public static void main(String[] args) {
        int gun = 6; // Cumartesi

        switch (gun) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("Hafta içi: Çalışma veya ders günü.");
                break;
            case 6:
            case 7:
                System.out.println("Hafta sonu: Dinlenme ve tatil günü! 🎉");
                break;
            default:
                System.out.println("Geçersiz gün.");
                break;
        }
    }
}
```

### 💻 Örnek 2: Ayın Kaç Gün Çektiğini Bulma (Şubat ve Artık Yıl)

```java
public class AyGunSayisi {
    public static void main(String[] args) {
        int ay = 2; // Şubat
        int yil = 2024;
        int gunSayisi = 0;

        switch (ay) {
            // 31 gün çeken aylar:
            case 1:  // Ocak
            case 3:  // Mart
            case 5:  // Mayıs
            case 7:  // Temmuz
            case 8:  // Ağustos
            case 10: // Ekim
            case 12: // Aralık
                gunSayisi = 31;
                break;

            // 30 gün çeken aylar:
            case 4:  // Nisan
            case 6:  // Haziran
            case 9:  // Eylül
            case 11: // Kasım
                gunSayisi = 30;
                break;

            // Şubat ayı kontrolü:
            case 2:
                // Artık yıl hesabı:
                if ((yil % 4 == 0 && yil % 100 != 0) || (yil % 400 == 0)) {
                    gunSayisi = 29;
                } else {
                    gunSayisi = 28;
                }
                break;

            default:
                System.out.println("Hatalı ay numarası!");
                return;
        }

        System.out.println(yil + " yılı " + ay + ". ayında toplam " + gunSayisi + " gün vardır.");
    }
}
```

---

## 4. `default` Durumu ve Konum Esnekliği

- `default` ifadesi, yazılan hiçbir `case` değeri ile eşleşme sağlanamadığında devreye giren acil durum / varsayılan durum bloğudur.
- `if-else` yapısındaki en sondaki `else` bloğunun tam karşılığıdır.
- Kullanımı **zorunlu değildir**, ancak beklenmeyen girdileri yakalamak için yazılması bir yazılım mühendisliği standardıdır.

> 📌 **İlginç Java Kuralı:** `default` bloğu geleneksel olarak en sona yazılır; fakat zorunlu değildir. Bloğun en başında veya ortasında da yer alabilir. Ancak en sonda değilse, altındaki satırlara kaymaması için **mutlaka sonuna `break;` konulmalıdır!**

```java
int kod = 99;

switch (kod) {
    default:
        System.out.println("Bilinmeyen Kod");
        break; // Buradaki break unutulursa alttaki case 1'e kayar!
    case 1:
        System.out.println("Başarılı");
        break;
    case 2:
        System.out.println("Hatalı");
        break;
}
```

---

## 5. Java'da Desteklenen ve Desteklenmeyen Veri Tipleri

Java'da her veri türü `switch` ifadesinin parantezi içine yazılamaz. Bu durum Java'nın bellek mimarisi ve baytkod optimizasyonuyla doğrudan ilgilidir:

| Destek Durumu | Veri Tipleri | Açıklama ve Gerekçe |
| :---: | :--- | :--- |
| ✅ **Desteklenir** | `byte`, `short`, `char`, `int` | 32-bit veya daha küçük tamsayı tipleri. JVM'de `tableswitch` talimatı doğrudan 32-bit tamsayı indeksleriyle çalışır. |
| ✅ **Desteklenir** | `Byte`, `Short`, `Character`, `Integer` | Bu ilkel tiplerin Wrapper (Sarmalayıcı) sınıfları (otomatik kutu açma / unboxing ile). |
| ✅ **Desteklenir** | `String` *(Java 7 ile eklendi)* | Arka planda `String.hashCode()` ve `.equals()` kullanılarak son derece hızlı eşleştirilir. |
| ✅ **Desteklenir** | `enum` (Numaralandırma) *(Java 5)* | Sabit değerler kümesini temsil ettiği için switch ile mükemmel uyumludur. |
| ❌ **DESTEKLENMEZ** | `long` | 64-bitlik büyüklüğü nedeniyle 32-bitlik hızlı jump table indekslerine doğrudan sığmaz. |
| ❌ **DESTEKLENMEZ** | `float`, `double` | Kayan noktalı sayılarda ikilik tabandaki yuvarlama hassasiyetleri (`0.1 + 0.2 != 0.3`) kesin eşitlik kontrolünü güvenilmez kılar. |
| ❌ **DESTEKLENMEZ** | `boolean` | Yalnızca `true` ve `false` olmak üzere 2 durum barındırdığından `if-else` kullanılması zorunlu tutulmuştur. |

---

### ⚠️ `case` Değerlerinin Derleme Zamanı Sabiti (Compile-Time Constant) Olma Zorunluluğu

`case` etiketinin yanına yazılan değer **kesinlikle derleme anında bilinen bir sabit** olmak zorundadır. Değişkenler veya çalışma zamanında üretilecek değerler yazılamaz:

```java
int hedef = 10;
int x = 5;
final int SABIT_SAYI = 20;

switch (hedef) {
    case 5:             // ✅ Geçerli: Literal sabit
        break;
    case SABIT_SAYI:    // ✅ Geçerli: 'final' ile tanımlanmış derleme zamanı sabiti
        break;
    case x:             // ❌ DERLEME HATASI! Değişken yazılamaz!
        break;
    case (10 + 2):      // ✅ Geçerli: Derleyici 10+2'nin 12 olduğunu derlerken hesaplar
        break;
}
```

---

## 6. Modern Java: Gelişmiş `switch` (Arrow `->` ve `yield` - Java 12/14+)

Java 12 ile önizleme olarak sunulan ve **Java 14 ile kalıcı standart haline gelen** Gelişmiş Switch (*Enhanced Switch*), iki büyük devrim getirmiştir:
1. İki nokta (`:`) yerine ok (`->`) sözdizimi: `break` yazma ihtiyacını ve dolayısıyla fall-through hatalarını tamamen ortadan kaldırır!
2. Switch bir **İfade (Expression)** haline gelmiştir: Yani doğrudan bir değer döndürüp değişkene atanabilir.

### 💻 Modern Sözdizimi 1: Arrow (`->`) ile Temiz Kod

```java
public class ModernSwitchOrnek {
    public static void main(String[] args) {
        String rol = "YONETICI";

        // Tek satırlık ok (->) sözdizimi:
        // break yazmaya GEREK YOKTUR! Yalnızca o satır çalışır ve biter.
        switch (rol) {
            case "YONETICI" -> System.out.println("Tüm sistem yetkileri tanımlandı.");
            case "EDITOR"   -> System.out.println("İçerik düzenleme yetkileri verildi.");
            case "UYE"      -> System.out.println("Standart okuma yetkisi tanımlandı.");
            default         -> System.out.println("Yetkisiz misafir kullanıcı!");
        }
    }
}
```

---

### 💻 Modern Sözdizimi 2: Çoklu Etiketler (Virgülle Ayrılmış Durumlar)

Artık aynı kodu paylaşan durumları alt alta boş `case` olarak dizmek yerine tek satırda virgülle yazabiliriz:

```java
int gun = 6;

switch (gun) {
    case 1, 2, 3, 4, 5 -> System.out.println("Hafta içi");
    case 6, 7          -> System.out.println("Hafta sonu");
    default            -> System.out.println("Geçersiz gün");
}
```

---

### 💻 Modern Sözdizimi 3: Switch İfadesi Olarak Değer Döndürme ve `yield`

Modern `switch`, bir hesaplama yapıp sonucunu doğrudan bir değişkene atayabilir:

```java
public class SwitchExpressionOrnek {
    public static void main(String[] args) {
        String ayAdi = "Subat";
        int yil = 2024;

        // switch bir ifade olarak doğrudan tamsayı değer döndürüyor:
        int gunSayisi = switch (ayAdi) {
            case "Ocak", "Mart", "Mayis", "Temmuz", "Agustos", "Ekim", "Aralik" -> 31;
            case "Nisan", "Haziran", "Eylul", "Kasim" -> 30;
            case "Subat" -> {
                // Çok satırlı bir blok çalıştıracaksak değer döndürmek için 'yield' kullanılır:
                if ((yil % 4 == 0 && yil % 100 != 0) || (yil % 400 == 0)) {
                    yield 29;
                } else {
                    yield 28;
                }
            }
            default -> {
                System.out.println("Hatalı ay adı!");
                yield -1;
            }
        };

        System.out.println(ayAdi + " ayı gün sayısı: " + gunSayisi);
    }
}
```

> 💡 **`yield` vs `return`:** `return` komutu tüm metodu sonlandırıp değer döndürürken; `yield` komutu **sadece `switch` bloğundan** bir değer döndürerek switch'i tamamlar, metodun geri kalanı çalışmaya devam eder.

---

## 7. Büyük Karşılaştırma: `if-else` vs `switch-case`

Öğrencilerin en çok merak ettiği sorulardan biri şudur: *"İkisi de koşula göre karar veriyor, hangisini ne zaman tercih etmeliyim?"*

### 📊 Detaylı Özellik Karşılaştırma Tablosu:

| Kriter | `if - else if - else` | `switch - case` |
| :--- | :--- | :--- |
| **Koşul Türü** | Her türlü mantıksal, ilişkisel ve boolean koşul (`<`, `>`, `>=`, `!=`, `&&`, `\|\|`). | Yalnızca **birebir eşitlik (`==`)** denetimi yapar. Aralık kontrolü yapamaz. |
| **Desteklenen Tipler** | Tüm ilkel tipler (`double`, `float`, `long`, `boolean` dahil) ve tüm nesneler. | Yalnızca `int`, `char`, `short`, `byte`, `String`, `enum`. (`double`, `boolean`, `long` **desteklenmez**). |
| **Aralık Denetimi** | Çok kolaydır: `if (not >= 80 && not <= 100)` | Yapılamaz! Her tam sayıyı tek tek `case 80, 81, 82...` yazmak gerekir (pratik değildir). |
| **Performans (Hız)** | **O(N) Sıralı Arama:** Yukarıdan aşağıya her koşul tek tek test edilir. 10. koşul için 9 koşul boşa kontrol edilir. | **O(1) Doğrudan Atlama:** JVM derleyicisi `tableswitch` veya `lookupswitch` baytkodu oluşturur; değere göre doğrudan hedef adrese sıçrar. Seçenek sayısı arttıkça `switch` çok daha hızlıdır. |
| **Okunabilirlik** | Çok fazla eşitlik kıyaslamasında kod hantallaşır ve göz yorar. | Sabit seçenekler ve menü yapılarında son derece düzenli, temiz ve taranabilir (*scannable*) bir görünüm sunar. |
| **Fall-Through Yeteneği**| Doğal olarak yoktur, her koşul bağımsızdır. | Bilinçli fall-through ile birden çok duruma ortak kod paylaştırılabilir. |

---

### 🔄 Kod Dönüştürme Örneği: Hangisi Daha Temiz?

Bir sipariş durumuna göre kullanıcıya bildirim metni hazırlayalım:

#### YOL 1: `if - else if` ile Yazım:
```java
String bildirim;
if (durum.equals("HAZIRLANIYOR")) {
    bildirim = "Siparişiniz mutfakta hazırlanıyor.";
} else if (durum.equals("KURYEDE")) {
    bildirim = "Kuryemiz yola çıktı, kapınıza geliyor.";
} else if (durum.equals("TESLIM_EDILDI")) {
    bildirim = "Afiyet olsun! Siparişiniz teslim edildi.";
} else if (durum.equals("IPTAL")) {
    bildirim = "Siparişiniz iptal edildi, ücret iadeniz yapıldı.";
} else {
    bildirim = "Bilinmeyen sipariş durumu.";
}
```

#### YOL 2: Modern `switch` İfadesi ile Yazım:
```java
String bildirim = switch (durum) {
    case "HAZIRLANIYOR"   -> "Siparişiniz mutfakta hazırlanıyor.";
    case "KURYEDE"        -> "Kuryemiz yola çıktı, kapınıza geliyor.";
    case "TESLIM_EDILDI"  -> "Afiyet olsun! Siparişiniz teslim edildi.";
    case "IPTAL"          -> "Siparişiniz iptal edildi, ücret iadeniz yapıldı.";
    default               -> "Bilinmeyen sipariş durumu.";
};
```
> 🎯 **Karar Kuralı:** Eğer aralık sorgulamıyorsanız (`x > 50` gibi) ve elinizde sabit değerlerden oluşan bir liste varsa (Menüler, Durum Kodları, Günler, Karakterler), **daima `switch-case` tercih ediniz.**

---

## 8. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: `String` Değişkeninin `null` Olması Durumu
Java 7'den itibaren `String` ifadeler `switch` içinde kullanılabilir. Ancak test edilen `String` değişkeni `null` değerine sahipse, switch kontrolü anında **`NullPointerException`** fırlatır!

```java
String secim = null;

// ❌ PROGRAM ÇÖKER! (NullPointerException)
switch (secim) {
    case "A": System.out.println("A"); break;
    default:  System.out.println("Diğer"); break;
}

// ✅ GÜVENLİ YAKLAŞIM:
if (secim != null) {
    switch (secim) { ... }
} else {
    System.out.println("Girdi boş (null) olamaz!");
}
```

---

### 2. Tuzak: Çift / Yinelenen `case` Değeri Yazmak (Duplicate Case)
Bir `switch` içinde aynı değere sahip iki farklı `case` bulunamaz. Derleyici bunu hemen fark eder ve hata verir:

```java
int x = 10;
switch (x) {
    case 5:
        System.out.println("Beş");
        break;
    case 5: // ❌ DERLEME HATASI: Duplicate case label
        System.out.println("Yine Beş");
        break;
}
```

---

### 3. Tuzak: Değişken Kapsamı (Variable Scope) Çakışması
Klasik `switch` yapısının içi **tek bir ortak bloktur**. Farklı `case`'ler altında aynı isimde değişken tanımlarsanız derleyici çakışma hatası verir:

```java
int secenek = 1;

switch (secenek) {
    case 1:
        int sayi = 100; // 'sayi' bu seviyede doğdu
        System.out.println(sayi);
        break;
    case 2:
        int sayi = 200; // ❌ DERLEME HATASI! 'sayi' zaten tanımlandı!
        System.out.println(sayi);
        break;
}

// ✅ ÇÖZÜM: Her case için özel süslü parantez {} açarak yerel kapsam oluşturun:
switch (secenek) {
    case 1: {
        int sayi = 100;
        System.out.println(sayi);
        break;
    }
    case 2: {
        int sayi = 200; // Artık kendi bloğu içinde olduğu için HATA VERMEZ
        System.out.println(sayi);
        break;
    }
}
```

---

### 4. Tuzak: Geleneksel Syntax ile Modern Syntax'ı Karıştırmak
Aynı `switch` bloğu içinde hem iki nokta (`:`) ve `break` hem de ok (`->`) sözdizimi **birlikte kullanılamaz**:

```java
// ❌ DERLEME HATASI! İki farklı stil karıştırılamaz:
switch (deger) {
    case 1 -> System.out.println("Bir");
    case 2: System.out.println("İki"); break;
}
```

---

## 9. Kapsamlı Örnek Konsol Uygulaması: `AkilliHesapMakinesi`

Aşağıdaki program; konsoldan iki sayı ve bir işlem sembolü alan, hem aritmetik işlemleri hem de üs alma ve mod işlemlerini `switch-case` ile yöneten, sıfıra bölme hatasını kontrol eden eksiksiz ve çalıştırılabilir bir uygulamadır:

```java
import java.util.Scanner;

public class AkilliHesapMakinesi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("       AKILLI MATEMATİKSEL HESAP MAKİNESİ ");
        System.out.println("==========================================");
        System.out.println("Desteklenen İşlemler: +, -, *, /, %, ^ (Üs)");

        System.out.print("Birinci sayıyı giriniz: ");
        double sayi1 = scanner.nextDouble();

        System.out.print("Yapılacak işlemi seçiniz (+, -, *, /, %, ^): ");
        char islem = scanner.next().charAt(0);

        System.out.print("İkinci sayıyı giriniz: ");
        double sayi2 = scanner.nextDouble();

        double sonuc = 0.0;
        boolean gecerliIslem = true;

        // char türü üzerinde switch kullanımı:
        switch (islem) {
            case '+':
                sonuc = sayi1 + sayi2;
                break;

            case '-':
                sonuc = sayi1 - sayi2;
                break;

            case '*':
                sonuc = sayi1 * sayi2;
                break;

            case '/':
                // Kritik Mantıksal Kontrol: Sıfıra bölme hatası
                if (sayi2 == 0) {
                    System.out.println("❌ Matematiksel Hata: Bir sayı sıfıra hữu bölünemez!");
                    gecerliIslem = false;
                } else {
                    sonuc = sayi1 / sayi2;
                }
                break;

            case '%':
                if (sayi2 == 0) {
                    System.out.println("❌ Hata: Sıfıra göre mod alınamaz!");
                    gecerliIslem = false;
                } else {
                    sonuc = sayi1 % sayi2;
                }
                break;

            case '^':
                sonuc = Math.pow(sayi1, sayi2);
                break;

            default:
                System.out.println("❌ Hata: Tanımlanmayan işlem sembolü girdiniz: '" + islem + "'");
                gecerliIslem = false;
                break;
        }

        if (gecerliIslem) {
            System.out.println("------------------------------------------");
            System.out.println("İŞLEM SONUCU: " + sayi1 + " " + islem + " " + sayi2 + " = " + sonuc);
            System.out.println("==========================================");
        }

        scanner.close();
    }
}
```

---

## 10. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - Fall-Through Analizi)
Aşağıdaki kod parçasının konsol çıktısını bilgisayarda çalıştırmadan önce adım adım kağıt üzerine yazınız:

```java
int x = 2;
int toplam = 10;

switch (x) {
    case 1:
        toplam += 5;
    case 2:
        toplam += 10;
    case 3:
        toplam *= 2;
        break;
    case 4:
        toplam += 100;
        break;
    default:
        toplam -= 1;
}

System.out.println("Toplam Değeri: " + toplam);
```
*İpucu: `case 2` eşleştikten sonra `break` nerede bulunuyor?*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında **derleyicinin hata vermesine yol açan 3 farklı sorunu** tespit ediniz ve düzeltiniz:

```java
double oran = 2.5; // Sorun 1 nerede?
int secim = 2;

switch (oran) {
    case 1.0:
        System.out.println("Düşük");
        break;
    case 2.5:
        System.out.println("Normal");
        break;
}

switch (secim) {
    case 1 -> System.out.println("Birinci");
    case 2:  System.out.println("İkinci"); break; // Sorun 2 nerede?
    case 1:  System.out.println("Tekrar Bir");     // Sorun 3 nerede?
}
```

---

### 🔄 Alıştırma 3: `if-else` Yapısını Modern `switch` İfadesine Dönüştürme
Aşağıda verilen klasik `if-else` bloğunu, Java 14+ ok (`->`) sözdizimi kullanan ve tek satırda değer atayan bir **Switch Expression** haline getiriniz:

```java
String ay = "Haziran";
String mevsim;

if (ay.equals("Aralik") || ay.equals("Ocak") || ay.equals("Subat")) {
    mevsim = "Kış";
} else if (ay.equals("Mart") || ay.equals("Nisan") || ay.equals("Mayis")) {
    mevsim = "İlkbahar";
} else if (ay.equals("Haziran") || ay.equals("Temmuz") || ay.equals("Agustos")) {
    mevsim = "Yaz";
} else if (ay.equals("Eylul") || ay.equals("Ekim") || ay.equals("Kasim")) {
    mevsim = "Sonbahar";
} else {
    mevsim = "Bilinmeyen Ay";
}
```

---

## 11. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. Kodlarınızda menü tasarımlarını estetik yapmaya, geçersiz girdileri `default` bloklarında yakalamaya ve açıklayıcı yorum satırları eklemeye özen gösteriniz.

---

### 📋 Ödev 1: Çok Katmanlı Restoran Sipariş Sistemi
**Dosya Adı:** `RestoranSiparis.java`

**Problem Tanımı:**  
Bir restoranın dijital sipariş kioskunu simüle ediniz. Program kullanıcıya ana menüyü sunmalı ve ardından seçilen kategoriye göre alt seçenekleri `switch-case` kullanarak listelemelidir.

**Menü Akışı:**
1. **Ana Menü (Kategori Seçimi):**
   - `1` ➡️ Çorbalar
   - `2` ➡️ Ana Yemekler
   - `3` ➡️ Tatlılar
   - `4` ➡️ İçecekler
2. **Alt Menü (İç İçe Switch ile):**
   - Kullanıcı örneğin `2` (Ana Yemekler) seçtiğinde:
     - `1` ➡️ İskender Kebap (280 TL)
     - `2` ➡️ Izgara Köfte (220 TL)
     - `3` ➡️ Mantı (190 TL)
3. Kullanıcıdan porsiyon adedi (tamsayı) alınmalıdır.
4. Toplam tutar hesaplanıp detaylı sipariş fişi ekrana basılmalıdır.
5. Geçersiz kategori veya yemek seçimlerinde `default` bloğu uygun hata mesajı vermelidir.

---

### 📋 Ödev 2: Trafik İhlali ve Ceza Puanı Sistemi
**Dosya Adı:** `TrafikCezasiHesaplayici.java`

**Problem Tanımı:**  
Emniyet Genel Müdürlüğü ceza cetvelini modelleyiniz. Kullanıcıdan ceza madde kodunu (`String` olarak: `"47/1-A"`, `"51/2-A"`, `"48/5"` vb.) alınız ve `switch-case` ile ceza bedelini ve ceza puanını hesaplayınız.

**Ceza Kodları Tablosu:**
- `"47/1-A"` : Kırmızı Işık İhlali ➡️ **1.506 TL**, **20 Ceza Puanı**
- `"51/2-A"` : Hız Sınırını %10 - %30 Aşmak ➡️ **1.506 TL**, **10 Ceza Puanı**
- `"51/2-B"` : Hız Sınırını %30 - %50 Aşmak ➡️ **3.135 TL**, **15 Ceza Puanı**
- `"73/C"`   : Seyir Halinde Cep Telefonu Kullanmak ➡️ **1.506 TL**, **20 Ceza Puanı**
- `"48/5"`   : Alkollü Araç Kullanmak (1. Kez) ➡️ **6.439 TL**, **20 Ceza Puanı**, *Ehliyete 6 ay el koyma uyarısı!*
- `"DEFAULT"`: Tanımsız ihlal kodu.

**Ek Kural:**  
Kullanıcıya cezanın ilk 15 gün içinde peşin ödenip ödenmeyeceği sorulmalı; eğer yanıt `"evet"` ise **%25 erken ödeme indirimi** uygulanmalıdır.

---

### 📋 Ödev 3: Akıllı Kargo Bölge ve Teslimat Fiyatlandırıcısı
**Dosya Adı:** `KargoHesaplayici.java`

**Problem Tanımı:**  
Bir kargo firması Türkiye'yi 4 ana bölgeye ayırmıştır. Gönderi ücreti; bölgeye, kargo ağırlığına (Desi / Kg) ve tercih edilen teslimat türüne göre hesaplanmaktadır.

**Kriterler:**
1. **Bölge Seçimi (`int`):**
   - `1`: Marmara & Ege (Taban: 50 TL, Kg Başı: 8 TL)
   - `2`: İç Anadolu & Karadeniz (Taban: 60 TL, Kg Başı: 10 TL)
   - `3`: Akdeniz & Doğu Anadolu (Taban: 75 TL, Kg Başı: 12 TL)
   - `4`: Güneydoğu Anadolu (Taban: 85 TL, Kg Başı: 14 TL)
2. **Kargo Ağırlığı (`double kg`):**
   - `Ağırlık Ücreti = Taban Fiyat + (kg * Kg Başı Ücret)`
3. **Teslimat Türü Seçimi (`String` - Modern switch ile):**
   - `"STANDART"` ➡️ Ek ücret yok (3 iş günü teslimat).
   - `"EKSPRES"`  ➡️ Tutar üzerinden **%40 ek ücret** (Ertesi gün teslimat).
   - `"VIP_KURYE"` ➡️ Tutar üzerinden **%100 ek ücret (2 katı)** (Aynı gün teslimat).
4. Hesaplanan tüm kalemleri (`Taban`, `Kg Farkı`, `Teslimat Farkı`, `Genel Toplam`) listeleyen profesyonel bir kargo kabul belgesi yazdırınız.

---

### 📋 Ödev 4: Roma Rakamı ve Sayı Çevirici (1 - 10 Arası)
**Dosya Adı:** `RomaRakamiCevirici.java`

**Problem Tanımı:**  
Kullanıcıya iki mod sunan bir çevirici program yazınız:
- **Mod 1:** Kullanıcı 1 ile 10 arasında bir tamsayı girer; program `switch-case` ile bunun Roma rakamı karşılığını (`I, II, III, IV, V, VI, VII, VIII, IX, X`) yazdırır.
- **Mod 2:** Kullanıcı Roma rakamı (`String` olarak: `"I"`, `"IV"`, `"IX"` vb.) girer; program `switch-case` ile bunun Arap rakamı (sayı) karşılığını yazdırır (Büyük/küçük harf duyarsızlığı için `.toUpperCase()` kullanılmalıdır).
- Hatalı girişlerde `default` ile uyarı verilmelidir.
