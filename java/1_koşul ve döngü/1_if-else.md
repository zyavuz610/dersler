# 1. Java'da Karar Yapıları: `if` ve `else` Kılavuzu

Bu rehber, Java programlama dilinde program akışını kontrol etmemizi sağlayan **karar ve dallanma yapılarını (`if`, `else if`, `else`)**, mantıksal koşulların çalışma prensiplerini, iç içe (*nested*) yapıları, kısa devre değerlendirmesini, en sık düşülen tuzakları ve öğrencilerin pekiştirmesi için pratik alıştırmalar ile kapsamlı ödevleri içermektedir.

---

> 📖 **Modül: 1_koşul ve döngü**  
> Bu doküman, programın düz bir hat (sıralı/sekansiyel) üzerinden akmak yerine belirli şartlara göre farklı kod bloklarını çalıştırmasını sağlayan koşul yapılarını ele alır.

---

## 📌 İçindekiler
1. [Program Akışı ve Karar Yapılarına Giriş](#1-program-akışı-ve-karar-yapılarına-giriş)
2. [Tek Yönlü Karar: `if` Bloğu](#2-tek-yönlü-karar-if-bloğu)
3. [Çift Yönlü Karar: `if - else` Bloğu](#3-çift-yönlü-karar-if---else-bloğu)
4. [Çok Yönlü Karar: Basamaklı `if - else if - else` Yapısı](#4-çok-yönlü-karar-basamaklı-if---else-if---else-yapısı)
5. [İç İçe (Nested) `if-else` Yapıları](#5-iç-içe-nested-if-else-yapıları)
6. [Koşul İfadelerinde Operatörler ve Kısa Devre Mantığı](#6-koşul-ifadelerinde-operatörler-ve-kısa-devre-mantığı)
7. [En Sık Yapılan Hatalar ve Tuzaklar](#7-en-sık-yapılan-hatalar-ve-tuzaklar)
8. [Ternary (Üçlü) Operatör (`? :`) ile `if-else` Karşılaştırması](#8-ternary-üçlü-operatör---ile-if-else-karşılaştırması)
9. [Kapsamlı Örnek Konsol Uygulaması: `BiletFiyatiHesaplayici`](#9-kapsamlı-örnek-konsol-uygulaması-biletfiyatihesaplayici)
10. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#10-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
11. [Ödev Projeleri](#11-ödev-projeleri)

---

## 1. Program Akışı ve Karar Yapılarına Giriş

Şimdiye kadar yazdığımız programlar yukarıdan aşağıya doğru her bir satırı sırasıyla (**sekansiyel / ardışık**) çalıştırıyordu. Ancak gerçek hayat senaryoları düz bir çizgide ilerlemez; seçimler, şartlar ve alternatif yollar içerir:

- Sınav notu 50'den büyükse öğrenci geçsin, değilse kalsın.
- Kullanıcı adı ve şifre doğruysa sisteme girilsin, yanlışsa hata mesajı gösterilsin.
- Sıcaklık sıfırın altındaysa "Don Uyarısı" verilsin.

Programlama dillerinde bu tür yol ayrımlarına **Dallanma (Branching)** veya **Karar Yapıları (Conditional Statements)** denir.



Java'da bir koşul ifadesinin sonucu **kesinlikle bir `boolean` (`true` ya da `false`)** değer üretmelidir. C veya C++ dillerinde olduğu gibi `1` veya `0` sayıları Java'da koşul olarak doğrudan **kullanılamaz**.

---

## 2. Tek Yönlü Karar: `if` Bloğu

`if` yapısı, yalnızca koşul **`true`** olduğunda çalışmasını istediğimiz kodlar için kullanılır. Koşul `false` çıkarsa `if` bloğu atlanır ve program sonraki satırdan akmaya devam eder.

### 📌 Genel Sözdizimi (Syntax):

```java
if (koşul_ifadesi) {
    // Koşul 'true' olduğunda çalışacak kodlar
}
```

### 💻 Örnek 1: Temel Kullanım

```java
public class IfTemel {
    public static void main(String[] args) {
        int sicaklik = -4;

        System.out.println("Sistem kontrol ediliyor...");

        if (sicaklik < 0) {
            System.out.println("⚠️ UYARI: Hava sıcaklığı sıfırın altında! Don riski var.");
            System.out.println("Lütfen araç hızınızı düşürünüz.");
        }

        System.out.println("Kontrol tamamlandı.");
    }
}
```

**Ekran Çıktısı:**
```text
Sistem kontrol ediliyor...
⚠️ UYARI: Hava sıcaklığı sıfırın altında! Don riski var.
Lütfen araç hızınızı düşürünüz.
Kontrol tamamlandı.
```

*(Eğer `sicaklik = 15` olsaydı, `if` bloğu içindeki iki satır hiç çalışmayacak, doğrudan `"Kontrol tamamlandı."` yazdırılacaktı.)*

---

### ⚠️ Blok Parantezleri (`{}`) ve Tek Satır Kuralı:

Eğer `if` şartından sonra süslü parantez `{}` açılmazsa, `if` ifadesi **sadece kendisinden sonraki ilk tek bir ifadeyi (statement)** kendi gövdesi kabul eder:

```java
int bakiye = 100;
int cekilecek = 150;

if (cekilecek > bakiye)
    System.out.println("Yetersiz bakiye!"); // if'e dahildir
    System.out.println("İşlem iptal edildi."); // if'e DAHİL DEĞİLDİR! Her durumda çalışır!
```

> 🚨 **Yazılım Mühendisliği İlkesi:** Kod tek bir satırdan ibaret olsa dahi her zaman süslü parantez `{}` kullanınız! Bu alışkanlık, sonradan araya satır eklendiğinde ortaya çıkabilecek büyük mantık hatalarını (*Apple'ın meşhur SSL 'goto fail' açığı gibi*) engeller.

---

## 3. Çift Yönlü Karar: `if - else` Bloğu

Koşul doğru olduğunda bir işin, yanlış olduğunda ise **başka bir alternatif işin** kesinlikle yapılmasını istediğimiz durumlarda `if - else` yapısı kullanılır. İki yoldan sadece biri çalışır; ikisinin birden çalışması veya ikisinin birden atlanması imkansızdır.

### 📌 Genel Sözdizimi:

```java
if (koşul_ifadesi) {
    // Koşul TRUE ise çalışacak kodlar
} else {
    // Koşul FALSE ise çalışacak kodlar
}
```

### 💻 Örnek 2: Tek / Çift Sayı Tespiti

```java
public class TekCiftKontrol {
    public static void main(String[] args) {
        int sayi = 27;

        // Sayının 2'ye bölümünden kalan 0 ise çifttir, değilse tektir.
        if (sayi % 2 == 0) {
            System.out.println(sayi + " bir ÇİFT sayıdır.");
        } else {
            System.out.println(sayi + " bir TEK sayıdır.");
        }
    }
}
```

### 💻 Örnek 3: Sınav Baraj Kontrolü

```java
public class SinavDurumu {
    public static void main(String[] args) {
        int vize = 40;
        int finalNotu = 60;
        double ortalama = (vize * 0.4) + (finalNotu * 0.6);

        System.out.println("Dönem Ortalaması: " + ortalama);

        if (ortalama >= 50.0 && finalNotu >= 50) {
            System.out.println("Tebrikler! Dersi başarıyla geçtiniz. ✅");
        } else {
            System.out.println("Maalesef dersten kaldınız. Bütünleme sınavına girmelisiniz. ❌");
        }
    }
}
```

---

## 4. Çok Yönlü Karar: Basamaklı `if - else if - else` Yapısı

İkiden fazla ihtimalin veya koşulun bulunduğu durumlarda basamaklı (`ladder`) yapı kullanılır. Java yukarıdan aşağıya doğru koşulları sırayla test eder:
1. `true` üreten ilk koşulun bloğu çalıştırılır.
2. Bloğun çalışması bittiğinde geriye kalan tüm `else if` ve `else` adımları **atlanır** ve yapıdan çıkılır.
3. Eğer hiçbir koşul sağlanmazsa en sondaki varsayılan **`else`** bloğu (varsa) devreye girer.


### 💻 Örnek 4: Üniversite Not Harflendirme Sistemi

```java
public class NotHarflendirme {
    public static void main(String[] args) {
        int not = 78;

        System.out.println("Öğrenci Puanı: " + not);

        if (not < 0 || not > 100) {
            System.out.println("Geçersiz not girdiniz! Not 0 ile 100 arasında olmalıdır.");
        } else if (not >= 90) {
            System.out.println("Harf Notu: AA (Mükemmel)");
        } else if (not >= 80) {
            System.out.println("Harf Notu: BA (Çok İyi)");
        } else if (not >= 70) {
            System.out.println("Harf Notu: BB (İyi)");
        } else if (not >= 60) {
            System.out.println("Harf Notu: CB (Orta)");
        } else if (not >= 50) {
            System.out.println("Harf Notu: CC (Geçer)");
        } else {
            System.out.println("Harf Notu: FF (Başarısız / Kaldı)");
        }
    }
}
```

### ⚡ Hayati Kural: Koşul Sıralamasının Mantıksal Önemi

Basamaklı `if-else if` yapılarında koşulların yazılma sırası kritiktir. Dar ve spesifik koşullar daima daha geniş koşullardan önce yazılmalıdır:

```java
// ❌ HATALI YAKLAŞIM:
int puan = 95;
if (puan >= 50) {
    System.out.println("Geçti"); // 95 sayısı 50'den büyük olduğu için buraya girer!
} else if (puan >= 90) {
    System.out.println("AA ile Geçti"); // BU SATIRA ASLA ULAŞILAMAZ! (Dead Code)
}

// ✅ DOĞRU YAKLAŞIM:
if (puan >= 90) {
    System.out.println("AA ile Geçti");
} else if (puan >= 50) {
    System.out.println("Geçti");
}
```

---

## 5. İç İçe (Nested) `if-else` Yapıları

Bir `if` veya `else` bloğunun içerisine başka bir `if-else` yapısı yerleştirilmesine **iç içe (nested) koşul** denir. Birinci koşul sağlanmadan ikinci koşulun kontrol edilmesinin anlamsız olduğu hiyerarşik durumlarda kullanılır.

### 💻 Örnek 5: Kan Bağışı Kabul Kontrolü

Kan bağışında bulunabilmek için kişinin hem yaş şartını (18-65 arası) hem de kilo şartını (en az 50 kg) sağlaması gerekir:

```java
public class KanBagisiKontrol {
    public static void main(String[] args) {
        int yas = 22;
        double kilo = 48.5;

        if (yas >= 18 && yas <= 65) {
            // Yaş uygun, şimdi kilo kontrolüne geçelim:
            if (kilo >= 50.0) {
                System.out.println("Kan bağışında bulunabilirsiniz. Teşekkür ederiz! 🩸");
            } else {
                System.out.println("Yaşınız uygun fakat kilonuz 50 kg altında olduğu için bağış yapamazsınız.");
            }
        } else {
            System.out.println("Kan bağışı yapabilmek için 18-65 yaş aralığında olmalısınız.");
        }
    }
}
```

---

### 🔍 "Dangling Else" (Askıda Kalan Else) Problemi

Parantez kullanılmadığında derleyici `else` ifadesini **kendisinden önceki en yakın eşleşmemiş `if`** ile eşleştirir. Bu durum beklenmedik mantık hatalarına yol açabilir:

```java
int x = 5;
int y = 20;

// Göz yanıltıcı girintileme:
if (x > 10)
    if (y > 15)
        System.out.println("İkisi de büyük");
else
    System.out.println("x 10'dan küçük mü, yoksa y 15'ten küçük mü?");
```

Yukarıdaki kodda girintiye aldanırsanız `else`'in dıştaki `if (x > 10)`a ait olduğunu düşünebilirsiniz. Oysa derleyici onu içteki `if (y > 15)` ile eşleştirir! `x = 5` olduğu için dıştaki `if` `false` döner ve ekrana hiçbir şey yazılmaz!

> 💡 **Çözüm:** Her bloğu daima `{ }` parantezleri arasına alınız:
```java
if (x > 10) {
    if (y > 15) {
        System.out.println("İkisi de büyük");
    }
} else {
    System.out.println("x 10'dan küçük");
}
```

---

### 🛡️ Guard Clauses (Erken Çıkış) Yaklaşımı ile Temiz Kod

İç içe 4-5 katman `if` yazmak kodu okunamaz hale getirir (buna yazılım dünyasında *"Arrow Anti-Pattern"* veya *"Piramit Kodu"* denir). Metotlar içinde hata veya geçersiz durumları en başta kontrol edip metottan çıkmak (`return`) kodu düzleştirir ve çok daha okunabilir kılar:

```java
// ❌ Aşırı iç içe piramit yapısı:
if (kullaniciGecerli) {
    if (hesapAktif) {
        if (bakiyeYeterli) {
            // Asıl işlem 3 kademe içeride kayboldu
        }
    }
}

// ✅ Guard Clauses (Temiz ve Düz Kod):
if (!kullaniciGecerli) return "Geçersiz kullanıcı";
if (!hesapAktif)       return "Hesap pasif";
if (!bakiyeYeterli)    return "Yetersiz bakiye";

// Asıl işlem girintisiz ve tertemiz yürütülür:
parayiTransferEt();
```

---

## 6. Koşul İfadelerinde Operatörler ve Kısa Devre Mantığı

`if` bloklarının parantezleri içinde birden fazla şartı birleştirmek için mantıksal operatörler kullanılır:

| Operatör | Tanım | Açıklama |
| :---: | :--- | :--- |
| **`&&`** | Mantıksal VE (AND) | Tüm koşullar `true` ise sonuç `true` olur. |
| **`\|\|`** | Mantıksal VEYA (OR) | Koşullardan en az biri `true` ise sonuç `true` olur. |
| **`!`** | Mantıksal DEĞİL (NOT) | Boolean değeri tersine çevirir (`!true` ➡️ `false`). |

### ⚡ Kısa Devre Değerlendirmesi (Short-Circuit Evaluation)
Java, koşulun sonucu kesinleştiği anda sağdaki diğer ifadeleri **çalıştırmaz**:
- `A && B` işleminde `A` yanlış (`false`) ise, `B`'nin sonucuna bakılmaksızın tüm ifade `false` olacağından `B` **hiç değerlendirilmez**.
- `A || B` işleminde `A` doğru (`true`) ise, tüm ifade zaten `true` olacağından `B` **hiç değerlendirilmez**.

#### 🎯 Bu Özellik Bize Ne Kazandırır? (`NullPointerException` Önleme)
Kısa devre mantığı, nesnelerin `null` olup olmadığını güvenle denetlememizi sağlar:

```java
String kullaniciAdi = null;

// Kısa devre sayesinde NullPointerException hatası ALINMAZ!
// İlk koşul false olduğu anda sağ taraftaki .length() çalıştırılmaz:
if (kullaniciAdi != null && kullaniciAdi.length() > 3) {
    System.out.println("Geçerli kullanıcı adı: " + kullaniciAdi);
} else {
    System.out.println("Kullanıcı adı boş veya geçersiz!");
}
```

*(Eğer `&&` yerine tek `&` kullanılsaydı, kısa devre uygulanmaz ve `null.length()` çağrıldığı için program `NullPointerException` ile çökerdi!)*

---

### ⚠️ Aralık Kontrolünde Matematiksel Yazım Hatası:

Matematikte `10 <= x <= 50` yazabiliriz. Ancak **Java'da bu sözdizimi derleme hatası verir**:

```java
int x = 25;

// ❌ DERLEME HATASI VERİR:
// if (10 <= x <= 50) { ... }

// ✅ JAVA'DA DOĞRU YAZIM:
if (x >= 10 && x <= 50) {
    System.out.println("x, 10 ile 50 aralığındadır.");
}
```

---

## 7. En Sık Yapılan Hatalar ve Tuzaklar

Java'ya yeni başlayan öğrencilerin `if-else` yapılarında en sık düştüğü 5 klasik tuzak:

### 1. Tuzak: Atama (`=`) ile Eşitlik Karşılaştırmasını (`==`) Karıştırmak
Sayısal türlerde `if (sayi = 10)` yazarsanız derleyici tür uyuşmazlığı hatası verir (çünkü `sayi = 10` ifadesi `int` döner, `boolean` değil). Ancak değişken **`boolean`** ise derleyici hata vermez ve ölümcül bir mantık hatası oluşur:

```java
boolean aktif = false;

// ❌ BÜYÜK HATA: Karşılaştırma yerine değer atandı!
if (aktif = true) { 
    // 'aktif' değişkenine 'true' atandı ve if şartı 'true' kabul edildi!
    System.out.println("Kullanıcı aktif."); // Burası yanlışlıkla çalışır!
}

// ✅ DOĞRU KULLANIM:
if (aktif == true) // veya daha temiz haliyle: if (aktif)
```

---

### 2. Tuzak: `if` Parantezinin Sonuna Yanlışlıkla Noktalı Virgül (`;`) Koymak

```java
int yas = 12;

// ❌ HATA: if'in sonuna konulan noktalı virgül koşulu hemen sonlandırır!
if (yas >= 18); 
{
    // Bu blok if'ten bağımsız, sıradan bir blok haline gelir ve HER ZAMAN ÇALIŞIR!
    System.out.println("Ehliyet alabilirsiniz."); // Yaş 12 olmasına rağmen ekrana basılır!
}
```

---

### 3. Tuzak: Metinleri (`String`) Karşılaştırırken `==` Kullanmak
Java'da ilkel tipler (`int`, `double`, `char`) `==` ile karşılaştırılır. Ancak nesneler ve `String` türü referans tiptir. `==` operatörü metinlerin **içeriğini değil, bellekteki adreslerini** karşılaştırır:

```java
String girilenSifre = new String("gizli123");
String gercekSifre = "gizli123";

// ❌ HATALI: Bellek adresleri farklı olduğu için FALSE dönebilir!
if (girilenSifre == gercekSifre) { ... }

// ✅ DOĞRU: Metin içeriğini harf harf kıyaslamak için .equals() kullanılır!
if (girilenSifre.equals(gercekSifre)) {
    System.out.println("Şifre doğru, giriş yapıldı.");
}

// Harf büyüklüğü önemsizse (Örn: "EVET" / "evet"):
if (cevap.equalsIgnoreCase("evet")) { ... }
```

---

### 4. Tuzak: Değişken Kapsamı (Variable Scope) Hatası
Bir `if` veya `else` bloğu içinde tanımlanan (`declare` edilen) bir değişken, o bloğun süslü parantezi kapandığı anda bellekten silinir. Dışarıdan erişilemez:

```java
int puan = 85;

if (puan >= 50) {
    String sonuc = "Geçti"; // Değişken if bloğunda doğdu
} else {
    String sonuc = "Kaldı";
}

// ❌ DERLEME HATASI: 'sonuc' değişkeni burada görünmez (out of scope)!
// System.out.println("Durum: " + sonuc);

// ✅ DOĞRU KULLANIM:
String sonuc; // Bloğun dışında tanımla
if (puan >= 50) {
    sonuc = "Geçti";
} else {
    sonuc = "Kaldı";
}
System.out.println("Durum: " + sonuc); // Sorunsuz çalışır
```

---

### 5. Tuzak: Gereksiz Boolean Kontrolü
Boolean değişkenler zaten kendi başlarına `true` ya da `false` üretirler. Yanlarına `== true` yazmak kod kalitesini düşürür:

```java
boolean ogrenciMi = true;

// ❌ ACEMİ YAZIMI:
if (ogrenciMi == true) { ... }
if (ogrenciMi == false) { ... }

// ✅ PROFESYONEL VE TEMİZ YAZIM:
if (ogrenciMi) { ... }
if (!ogrenciMi) { ... }
```

---

## 8. Ternary (Üçlü) Operatör (`? :`) ile `if-else` Karşılaştırması

Basit bir koşula bağlı olarak tek bir değişkene değer atamak istediğimizde, 5-6 satırlık `if-else` yerine tek satırlık **koşul (ternary) operatörü** kullanılabilir.

### 📌 Formül:
```text
değişken = (koşul) ? true_ise_değer : false_ise_değer;
```

### 💻 Karşılaştırma Örneği:

```java
int sayi = 14;

// 1. YOL: Klasik if-else (6 Satır)
String sonuc1;
if (sayi % 2 == 0) {
    sonuc1 = "Çift";
} else {
    sonuc1 = "Tek";
}

// 2. YOL: Ternary Operatörü (Tek Satır - Zarif ve Okunaklı)
String sonuc2 = (sayi % 2 == 0) ? "Çift" : "Tek";
```

> ⚠️ **Tavsiye:** Koşul karmaşıklaştıkça veya iç içe 3-4 durum oluştuğunda ternary kodun okunabilirliğini bozar. Basit atamalarda ternary, karmaşık mantıklarda ise daima `if-else` tercih edilmelidir.

---

## 9. Kapsamlı Örnek Konsol Uygulaması: `BiletFiyatiHesaplayici`

Aşağıdaki program; kullanıcı girdisi alma (`Scanner`), basamaklı `if - else if - else`, mantıksal operatörler (`&&`, `||`) ve `String.equalsIgnoreCase()` metotlarını bir arada kullanan kapsamlı bir simülasyondur:

```java
import java.util.Scanner;

public class BiletFiyatiHesaplayici {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("  KTÜ ETKİNLİK MERKEZİ BİLET HESAPLAMA   ");
        System.out.println("==========================================");

        double tabanFiyat = 200.0; // TL

        System.out.print("Lütfen yaşınızı giriniz: ");
        int yas = scanner.nextInt();

        // 1. Aşama: Temel Doğrulama
        if (yas < 0 || yas > 120) {
            System.out.println("❌ Hata: Geçersiz bir yaş girdiniz!");
            scanner.close();
            return; // Programı sonlandır
        }

        System.out.print("Öğrenci misiniz? (Evet/Hayır): ");
        String ogrenciCevap = scanner.next();
        boolean ogrenciMi = ogrenciCevap.equalsIgnoreCase("evet");

        System.out.print("Etkinlik Günü (Haftaici / Haftasonu): ");
        String gunTuru = scanner.next();

        double indirimOrani = 0.0;

        // 2. Aşama: Basamaklı Yaş ve Statü İndirimi
        if (yas < 7) {
            indirimOrani = 1.0; // 7 yaş altına %100 ÜCRETSİZ
            System.out.println("-> 0-6 Yaş Grubu: Ücretsiz Giriş!");
        } else if (yas >= 65) {
            indirimOrani = 0.50; // 65 yaş ve üstüne %50 İndirim
            System.out.println("-> 65+ Yaş Kıdemli İndirimi: %50");
        } else if (ogrenciMi) {
            indirimOrani = 0.30; // Öğrencilere %30 İndirim
            System.out.println("-> Öğrenci İndirimi: %30");
        } else if (yas >= 7 && yas <= 18) {
            indirimOrani = 0.20; // 7-18 yaş genç indirimi: %20
            System.out.println("-> Genç İndirimi: %20");
        } else {
            System.out.println("-> Standart Yetişkin Tarifesi.");
        }

        // 3. Aşama: İndirimli Tutarı Hesaplama
        double odenecekTutar = tabanFiyat - (tabanFiyat * indirimOrani);

        // 4. Aşama: Hafta Sonu Ek Ücreti Kontrolü
        if (gunTuru.equalsIgnoreCase("haftasonu") && indirimOrani < 1.0) {
            odenecekTutar += 30.0; // Hafta sonu yoğunluk ek ücreti
            System.out.println("-> Hafta sonu seansı için +30 TL ilave edilmiştir.");
        }

        System.out.println("------------------------------------------");
        System.out.println("Taban Bilet Tutarı : " + tabanFiyat + " TL");
        System.out.println("Uygulanan İndirim  : %" + (int)(indirimOrani * 100));
        System.out.println("ÖDENECEK TOPLAM    : " + odenecekTutar + " TL");
        System.out.println("==========================================");

        scanner.close();
    }
}
```

---

## 10. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

Bu bölümdeki soruları bir bilgisayara ihtiyaç duymadan önce kağıt-kalem ile çözünüz, ardından bilgisayarda test ederek kontrol ediniz.

### ✍️ Alıştırma 1: Kod İzleme (Code Tracing - Çıktı Tahmini)
Aşağıdaki kod parçasının konsol çıktısı ne olur? Adım adım takip ediniz:

```java
int a = 15;
int b = 20;

if (a > 10 && b++ > 20) {
    System.out.println("Durum 1: a=" + a + ", b=" + b);
} else if (a < 20 || ++b > 25) {
    System.out.println("Durum 2: a=" + a + ", b=" + b);
} else {
    System.out.println("Durum 3: a=" + a + ", b=" + b);
}
```
*İpucu: `&&` kısa devre mantığına ve postfix (`b++`) ile prefix (`++b`) operatörlerinin çalışma sırasına dikkat ediniz.*

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kod parçasında **3 adet mantık/derleme hatası** bulunmaktadır. Bu hataları tespit edip kodun doğrusunu yazınız:

```java
int sicaklik = 25;
boolean klimaAcik = false;

if (sicaklik > 20) 
    klimaAcik = true;
    System.out.println("Klima açıldı."); // Hata 1 nerede?

String mod;
if (klimaAcik = true) { // Hata 2 nerede?
    mod = "Soğutma";
}

if (sicaklik >= 20 && sicaklik <= 30); { // Hata 3 nerede?
    System.out.println("Hava ılık, seçilen mod: " + mod);
}
```

---

### 📐 Alıştırma 3: Üçgen Geçerlilik ve Türü Belirleme
Klavyeden girilen 3 tamsayı kenar uzunluğunun (`a`, `b`, `c`):
1. Öncelikle geçerli bir üçgen oluşturup oluşturamayacağını kontrol ediniz.  
   *(Üçgen Eşitsizliği Kuralı: Herhangi bir kenar, diğer iki kenarın toplamından küçük ve farkının mutlak değerinden büyük olmalıdır: `|b - c| < a < b + c`)*
2. Eğer geçerli bir üçgense:
   - Tüm kenarları eşitse: **"Eşkenar Üçgen"**
   - İki kenarı eşitse: **"İkizkenar Üçgen"**
   - Kenarların hepsi farklıysa: **"Çeşitkenar Üçgen"** yazdırınız.

---

### 🔢 Alıştırma 4: 3 Sayıdan En Büyüğünü Bulma
Kullanıcıdan alınan 3 farklı tamsayı (`sayi1`, `sayi2`, `sayi3`) arasından **en büyüğünü**, `Math.max()` fonksiyonu **kullanmadan**, yalnızca `if - else` ve mantıksal operatörler yardımıyla bulan algoritmayı kodlayınız.

---

## 11. Ödev Projeleri

Aşağıdaki projeleri her biri ayrı bir `.java` dosyası olacak şekilde kodlayınız. Kodlarınızda açıklama satırları (`//`) kullanmaya, değişken isimlerini anlamlı seçmeye (`camelCase`) ve geçersiz girdi durumlarını denetlemeye özen gösteriniz.

---

### 📋 Ödev 1: Gelişmiş Vücut Kitle İndeksi (VKİ) ve Sağlık Danışmanı
**Dosya Adı:** `VkiHesaplayici.java`

**Problem Tanımı:**  
Kullanıcıdan **kilosunu (kg)**, **boyunu (metre)** ve **yaşını** alınız.
- **Formül:** `VKİ = kilo / (boy * boy)`

**İstenen Koşullar:**
1. Geçersiz girdi kontrolü: Kilo <= 0 veya Boy <= 0 girilirse program hata verip kapanmalıdır.
2. Basamaklı `if - else if - else` ile sınıflandırma:
   - `VKİ < 18.5`: **Zayıf**
   - `18.5 <= VKİ < 24.9`: **Normal / İdeal Kilo**
   - `25.0 <= VKİ < 29.9`: **Fazla Kilolu**
   - `30.0 <= VKİ < 34.9`: **1. Derece Obezite**
   - `VKİ >= 35.0`: **İleri Derece (Morbid) Obezite**
3. **Ekstra Yaş Faktörü (İç İçe `if`):**
   - Eğer kişi 65 yaşın üzerindeyse ve kategorisi "Zayıf" ise: *"Yaşlı bireylerde zayıflık kas kaybı riski taşır, lütfen hekime danışınız."* uyarısı basılmalıdır.
   - Eğer kişi 18 yaşın altındaysa: *"18 yaş altı bireyler için persentil tablosu önerilir."* notu düşülmelidir.

---

### 📋 Ödev 2: Kademeli Elektrik Tüketim Faturası Hesaplayıcı
**Dosya Adı:** `ElektrikFaturasi.java`

**Problem Tanımı:**  
Bir elektrik dağıtım şirketi mesken abonelerine aylık tükettikleri kilovatsaat (kWh) elektrik enerjisine göre kademeli tarife uygulamaktadır:

- **1. Kademe (İlk 150 kWh'e kadar):** Birim fiyat **1.80 TL / kWh**
- **2. Kademe (151 - 250 kWh arası):** Birim fiyat **2.60 TL / kWh**
- **3. Kademe (250 kWh'in üzerindeki kısım):** Birim fiyat **3.90 TL / kWh**

**Örnek Hesaplama Mantığı:**  
Aylık 280 kWh tüketen bir abone için:
- İlk 150 kWh ➡️ `150 * 1.80 = 270 TL`
- Sonraki 100 kWh (151-250 arası) ➡️ `100 * 2.60 = 260 TL`
- Kalan 30 kWh (250 üstü) ➡️ `30 * 3.90 = 117 TL`
- Ham Tutar = `270 + 260 + 117 = 647 TL`

**İstenenler:**
1. Kullanıcıdan tüketim miktarını (kWh) alınız.
2. Doğru kademelendirmeyi `if - else if - else` bloklarıyla hesaplayınız.
3. Ham tutara **%20 KDV** ekleyiniz.
4. Eğer toplam fatura tutarı 1000 TL'yi aşıyorsa ekrana: *"Yüksek tüketim grubundasınız, tasarruf tedbirlerini gözden geçiriniz."* uyarısı yazdırınız.
5. Fatura dökümünü kademe kademe ekrana basınız.

---

### 📋 Ödev 3: Güvenli İnternet Şubesi Giriş Doğrulama Simülasyonu
**Dosya Adı:** `BankaGirisSimulasyonu.java`

**Problem Tanımı:**  
Bir banka uygulamasının kullanıcı giriş ekranını simüle ediniz.

**Kriterler:**
- Sistemde kayıtlı sabit veriler:  
  `String kayitliKullanici = "ogrenci61";`  
  `String kayitliSifre = "Ktu2026!";`  
  `int smsKodu = 4582;`
- Kullanıcıdan sırasıyla kullanıcı adı ve şifresi istenmelidir.
- Metin karşılaştırmalarında kesinlikle **`.equals()`** metodu kullanılmalıdır.

**Akış:**
1. Eğer kullanıcı adı **veya** şifre yanlışsa: Hangi bilginin yanlış olduğunu güvenlik nedeniyle açıkça belirtmeden *"Kullanıcı adı veya şifre hatalı!"* mesajı verilip program sonlandırılmalıdır.
2. Kullanıcı adı ve şifre doğruysa: Kullanıcıya 4 haneli bir SMS doğrulama kodu sorulmalıdır.
3. SMS kodu da doğru girilirse: *"Giriş başarılı! KTÜ Bankacılık ana sayfasına yönlendiriliyorsunuz..."* yazdırılmalıdır.
4. SMS kodu hatalı girilirse: *"SMS kodu hatalı! Güvenlik nedeniyle oturum kilitlendi."* uyarısı verilmelidir.

---

### 📋 Ödev 4: Akıllı Uçak Bileti Rezervasyon Fiyatlandırıcısı
**Dosya Adı:** `UcakBileti.java`

**Problem Tanımı:**  
Mesafe (KM), Yolcu Yaşı ve Yolculuk Tipi (1: Tek Yön, 2: Gidiş-Dönüş) bilgilerini alarak bilet fiyatını hesaplayan programı yazınız.

**Kurallar:**
1. Mesafe başına ücret: **1.50 TL / KM**
2. **Hatalı Veri Kontrolü:** Mesafe veya Yaş değeri pozitif olmalı, Yolculuk Tipi sadece 1 veya 2 seçilebilmelidir. Aksi halde *"Hatalı Veri Girdiniz!"* uyarısı verilmelidir.
3. **Yaş İndirimleri:**
   - Kişi 12 yaşından küçükse bilet fiyatı üzerinden **%50 indirim** uygulanır.
   - Kişi 12-24 yaşları arasındaysa bilet fiyatı üzerinden **%20 indirim** uygulanır.
   - Kişi 65 yaşından büyükse bilet fiyatı üzerinden **%30 indirim** uygulanır.
   - Diğer yaş gruplarına yaş indirimi uygulanmaz.
4. **Yolculuk Tipi İndirimi:**
   - Eğer yolculuk tipi "Gidiş-Dönüş" (2) seçilmişse, yaş indirimi uygulandıktan sonraki tutar üzerinden **%20 ek indirim** uygulanır ve iki bilet ücreti hesaplanır (tutar * 2).
5. Sonuçta müşteriye indirim kalemlerini ve nihai ödeyeceği tutarı gösteren temiz bir özet fişi yazdırınız.
