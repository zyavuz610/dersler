# 2. Java'da Metin Dizileri (`String[]`) Kılavuzu

Bu rehber; Java programlama dilinde metinsel verileri organize bir şekilde saklamamızı sağlayan **`String[]` (String Dizileri)** yapısını, primitif dizilerden temel farklarını, iki katmanlı bellek mimarisini (**Stack -> Heap Dizi -> String Havuzu**), `null` tuzaklarını, metin karşılaştırmayı (`.equals()`), diziler ile String'leri birbirine bağlayan köprü metotları (`split()`, `join()`), `main(String[] args)` komut satırı argümanlarını ve öğrencilerin çözebileceği alıştırmalar ile ödevleri kapsamaktadır.

---

> 📖 **Modül: 2_diziler**  
> İlgili Önceki Doküman: [1_dizi giriş.md](./1_dizi%20giriş.md)

---

## 📌 İçindekiler
1. [`String[]` Nedir ve Primitif Dizilerden Farkı](#1-string-nedir-ve-primitif-dizilerden-farkı)
2. [`String[]` Tanımlama ve İki Katmanlı Bellek Mimarisi](#2-string-tanımlama-ve-iki-katmanlı-bellek-mimarisi)
3. [Varsayılan Değer Tuzağı: `null` ve `NullPointerException`](#3-varsayılan-değer-tuzağı-null-ve-nullpointerexception)
4. [Elemanlara Erişim, Güncelleme ve Dolaşma Yöntemleri](#4-elemanlara-erişim-güncelleme-ve-dolaşma-yöntemleri)
5. [Dizide Metin Arama ve Karşılaştırma (`.equals()` vs `==`)](#5-dizide-metin-arama-ve-karşılaştırma-equals-vs-)
6. [Diziler ve Metinler Arasındaki Köprüler: `split()` ve `String.join()`](#6-diziler-ve-metinler-arasındaki-köprüler-split-ve-stringjoin)
7. [`main(String[] args)` Komut Satırı Argümanları](#7-mainstring-args-komut-satırı-argümanları)
8. [Pratik `String[]` Algoritmaları (En Uzun Kelime, Sıralama, Filtreleme)](#8-pratik-string-algoritmaları-en-uzun-kelime-sıralama-filtreleme)
9. [En Sık Yapılan Hatalar ve Tuzaklar](#9-en-sık-yapılan-hatalar-ve-tuzaklar)
10. [Kapsamlı Örnek Konsol Uygulaması: `AkilliRehberVeAramaMotoru`](#10-kapsamlı-örnek-konsol-uygulaması-akillirehbervearamamotoru)
11. [Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar](#11-öğrenciler-için-pratik-sınıf-içi-alıştırmalar)
12. [Ödev Projeleri](#12-ödev-projeleri)

---

## 1. `String[]` Nedir ve Primitif Dizilerden Farkı?

Önceki konuda gördüğümüz `int[]` veya `double[]` gibi diziler, doğrudan sayısal değerleri bellekte yan yana saklar. Ancak gerçek dünya uygulamalarında sayıların yanında **isimler, şehirler, e-postalar, ürün kodları ve menü seçenekleri** gibi metinsel verileri saklamamız gerekir.

Java'da metinler `String` sınıfı ile temsil edilir. `String[]` ise **birden çok `String` nesnesini tek bir dizi çatısı altında toplayan referans tipli bir dizidir**.

### 🌟 Primitif Dizi ile String Dizisi Arasındaki Temel Fark:

| Özellik | Primitif Dizi (`int[]`, `double[]`) | Referans Tipli Dizi (`String[]`) |
| :--- | :--- | :--- |
| **Eleman Türü** | İlkel veri tipi (Sayı, boolean, karakter). | `String` bir **Nesnedir (Object)**. |
| **Hücre İçeriği** | Dizi hücresinde doğrudan **sayısal değerin kendisi** yatar (`10`, `3.14`). | Dizi hücresinde metnin kendisi değil, metin nesnesinin **bellek referansı (adresi)** yatar. |
| **Varsayılan Değer** | `0`, `0.0`, `false` | **`null`** (Hiçbir nesneyi işaret etmeyen boş adres). |
| **Metot Çağrısı** | Eleman üzerinden metot çağrılamaz (`sayilar[0].birSey()` YOKTUR). | Her eleman üzerinden String metotları çağrılabilir (`isimler[0].toUpperCase()`). |

---

## 2. `String[]` Tanımlama ve İki Katmanlı Bellek Mimarisi

Bir `String` dizisi oluşturmanın iki klasik yolu vardır:

### 1. Yol: Boyutunu Belirleyerek Boş Dizi Açma
```java
// 3 elemanlı boş bir String dizisi açılır (Tüm elemanlar başlangıçta 'null'dır):
String[] sehirler = new String[3];

sehirler[0] = "Trabzon";
sehirler[1] = "İstanbul";
sehirler[2] = "Ankara";
```

### 2. Yol: Dizi Literali ile Doğrudan Değer Atama
```java
String[] gunler = { "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma" };
```

---

### 🧠 İki Katmanlı Bellek Mimarisi (Stack -> Heap Dizi -> String Constant Pool)

Java'da bir `String[]` oluşturduğunuzda bellekte 3 farklı katman oluşur:
1. **Stack Bellek:** `sehirler` referans değişkeni yaşar.
2. **Heap Bellek (Dizi Bloğu):** 3 elemanlık dizi nesnesi yaşar. Bu dizinin her bir hücresi birer **işaretçi (pointer / adres)** barındırır.
3. **Heap / String Constant Pool:** Asıl metin karakterleri (`"Trabzon"`, `"İstanbul"`) burada saklanır. Dizi hücreleri bu metinlerin adresini tutar.

```text
[ STACK ]                  [ HEAP: DİZİ NESNESİ ]            [ HEAP: STRING HAVUZU ]
sehirler ----(Adres A)---> [0] (Adres X) --------------> "Trabzon"
                           [1] (Adres Y) --------------> "İstanbul"
                           [2] (Adres Z) --------------> "Ankara"
```

---

## 3. Varsayılan Değer Tuzağı: `null` ve `NullPointerException`

Java'da `new int[3]` dediğinizde elemanlar `0` olur ve güvenle işlem yapabilirsiniz.  
Ancak `new String[3]` dediğinizde tüm elemanlar başlangıçta **`null`** (boş) değerini alır!

> 🚨 **Ölümcül Tuzak:** Eğer bir `String[]` hücresine henüz metin atamadan o elemanın bir metodunu (`.length()`, `.toUpperCase()` vb.) çağırmaya çalışırsanız, Java anında **`NullPointerException`** fırlatır ve programınız çöker!

```java
public class NullTuzagiDemo {
    public static void main(String[] args) {
        String[] ogrenciler = new String[3]; // [null, null, null]

        System.out.println(ogrenciler[0]); // Ekrana güvenle 'null' yazar

        // ❌ ÇÖKER: NullPointerException! null olan bir şeyin harfleri sayılamaz!
        // int harfSayisi = ogrenciler[0].length(); 

        // ✅ GÜVENLİ KOD YAKLAŞIMI:
        if (ogrenciler[0] != null) {
            System.out.println("Uzunluk: " + ogrenciler[0].length());
        } else {
            System.out.println("0. indeksteki öğrenci henüz atanmamış!");
        }
    }
}
```

---

## 4. Elemanlara Erişim, Güncelleme ve Dolaşma Yöntemleri

### 💻 1. Klasik `for` Döngüsü ile Dolaşma
İndeks numarasına ihtiyaç duyduğumuzda ve elemanları güncellerken klasik `for` döngüsü kullanılır:

```java
String[] diller = { "Java", "Python", "C++", "JavaScript" };

for (int i = 0; i < diller.length; i++) {
    // diller[i] bir String olduğu için doğrudan String metotları kullanılabilir:
    System.out.println((i + 1) + ". Dil: " + diller[i].toUpperCase() + " (Harf Sayısı: " + diller[i].length() + ")");
}
```

### 💻 2. Modern `for-each` Döngüsü ile Dolaşma
Sadece elemanları sırayla okumak istediğimizde en temiz ve zarif yoldur:

```java
String[] meyveler = { "Elma", "Armut", "Muz", "Çilek" };

for (String meyve : meyveler) {
    System.out.println("Meyve: " + meyve);
}
```

---

## 5. Dizide Metin Arama ve Karşılaştırma (`.equals()` vs `==`)

Öğrencilerin en çok düştüğü hata, dizideki bir kelimeyi ararken çift eşittir (`==`) kullanmaktır:

```java
String[] kullanicilar = { "admin", "moderator", "ogrenci" };
String aranan = new String("admin");

// ❌ BÜYÜK HATA: '==' bellek adreslerini karşılaştırır!
// 'aranan' yeni bir nesne olduğu için dizi hücresindeki adresle eşleşmez ve FALSE döner!
if (kullanicilar[0] == aranan) { ... } // EŞLEŞMEZ!

// ✅ DOĞRU YOL: Metin içeriklerini harf harf kıyaslamak için daima .equals() kullanılır:
if (kullanicilar[0].equals(aranan)) {
    System.out.println("Kullanıcı bulundu!");
}

// Büyük/küçük harf duyarsız arama için:
if (kullanicilar[0].equalsIgnoreCase("ADMIN")) {
    System.out.println("Yönetici girişi onaylandı.");
}
```

---

## 6. Diziler ve Metinler Arasındaki Köprüler: `split()` ve `String.join()`

Java'da tek bir metni parçalayıp `String[]` dizisine çevirmek veya bir dizideki kelimeleri birleştirip tek bir metin yapmak günlük yazılım hayatının en sık yapılan işlemlerindendir.

### ✂️ 1. `split()`: Metni Bölüp Diziye Dönüştürme
Bir metni belirli bir ayırıcı karaktere (virgül, boşluk, tire vb.) göre dilimleyip geriye `String[]` döndürür:

```java
public class SplitOrnek {
    public static void main(String[] args) {
        // CSV Formatında bir veri satırı:
        String veri = "Ahmet,Yılmaz,Bilgisayar Mühendisliği,2024";

        // Virgüllerden bölerek diziye çevirelim:
        String[] parcalar = veri.split(",");

        System.out.println("Ad       : " + parcalar[0]);
        System.out.println("Soyad    : " + parcalar[1]);
        System.out.println("Bölüm    : " + parcalar[2]);
        System.out.println("Giriş Yılı: " + parcalar[3]);

        // Cümleyi kelimelerine ayırma (Boşluğa göre split):
        String cumle = "Java programlama dili çok güçlüdür";
        String[] kelimeler = cumle.split(" ");
        System.out.println("Cümledeki Kelime Sayısı: " + kelimeler.length); // 5
    }
}
```

---

### 🔗 2. `String.join()`: Diziyi Birleştirip Tek Bir Metin Yapma
Bir dizideki tüm elemanların arasına istediğimiz bir ayırıcıyı koyarak tek bir `String` üretir:

```java
String[] sehirler = { "Trabzon", "Rize", "Artvin", "Giresun" };

// Elemanların arasına " - " koyarak birleştirelim:
String rota = String.join(" -> ", sehirler);
System.out.println("Karadeniz Tur Rotası: " + rota);
// Çıktı: Trabzon -> Rize -> Artvin -> Giresun

// Virgülle birleştirip CSV satırı yapma:
String csvSatiri = String.join(";", sehirler);
System.out.println("CSV: " + csvSatiri);
// Çıktı: Trabzon;Rize;Artvin;Giresun
```

---

## 7. `main(String[] args)` Komut Satırı Argümanları

Her Java programının giriş kapısı olan `public static void main(String[] args)` metodundaki `args` parametresi aslında **bir `String[]` dizisinden başka bir şey değildir!**

Programınızı terminalden (*Command Prompt / Bash*) çalıştırırken program adına verdiğiniz tüm parametreler Java tarafından otomatik olarak bu `args` dizisine doldurulur.

### 💻 Örnek Program (`Selamlayici.java`):

```java
public class Selamlayici {
    public static void main(String[] args) {
        // Kullanıcı komut satırından parametre verdi mi kontrolü:
        if (args.length < 2) {
            System.out.println("❌ Kullanım Hatası: Lütfen adınızı ve yaşınızı giriniz!");
            System.out.println("Örnek: java Selamlayici Ahmet 22");
            return;
        }

        String isim = args[0];
        int yas = Integer.parseInt(args[1]); // String'i int'e çevir

        System.out.println("Hoş geldin " + isim + ", gelecek yıl " + (yas + 1) + " yaşında olacaksın.");
    }
}
```

**Terminalden Çalıştırma Şekli:**
```bash
javac Selamlayici.java
java Selamlayici Ahmet 22
```
**Çıktı:**
```text
Hoş geldin Ahmet, gelecek yıl 23 yaşında olacaksın.
```

---

## 8. Pratik `String[]` Algoritmaları

### 💻 1. Dizideki En Uzun ve En Kısa Kelimeyi Bulma

```java
String[] kelimeler = { "Java", "Mühendislik", "Algoritma", "Kod", "Veri" };

String enUzun = kelimeler[0];
String enKisa = kelimeler[0];

for (int i = 1; i < kelimeler.length; i++) {
    if (kelimeler[i].length() > enUzun.length()) {
        enUzun = kelimeler[i];
    }
    if (kelimeler[i].length() < enKisa.length()) {
        enKisa = kelimeler[i];
    }
}

System.out.println("En Uzun Kelime : " + enUzun + " (" + enUzun.length() + " harf)");
System.out.println("En Kısa Kelime : " + enKisa + " (" + enKisa.length() + " harf)");
```

---

### 💻 2. Alfabetik Sıralama (`Arrays.sort`)
Java'nın hazır `Arrays.sort()` metodu `String[]` dizilerini doğrudan **sözlük sırasına (alfabetik / leksikografik)** göre dizer:

```java
import java.util.Arrays;

String[] ogrenciler = { "Zeynep", "Ahmet", "Can", "Burak", "Ayşe" };

Arrays.sort(ogrenciler);

System.out.println("Alfabetik Sıralı Liste: " + Arrays.toString(ogrenciler));
// Çıktı: [Ahmet, Ayşe, Burak, Can, Zeynep]
```

---

### 💻 3. Filtreleme ve Arama (Belirli Bir Harfle Başlayanları Bulma)

```java
String[] sehirler = { "Ankara", "Antalya", "Trabzon", "Amasya", "Bursa" };

System.out.println("'A' Harfi ile Başlayan Şehirler:");
for (String sehir : sehirler) {
    if (sehir.startsWith("A")) {
        System.out.println("-> " + sehir);
    }
}
```

---

## 9. En Sık Yapılan Hatalar ve Tuzaklar

### 1. Tuzak: `dizi.length` ile `dizi[i].length()` Karışıklığı!
- **`dizi.length`:** Dizide **toplam kaç adet kelime/eleman olduğunu** söyler (Özelliktir, parantezsizdir).
- **`dizi[i].length()`:** O indeksteki **kelimenin kaç harften oluştuğunu** söyler (String metodudur, parantezlidir).

```java
String[] kelimeler = {"Elma", "Portakal"};

int elemanSayisi = kelimeler.length;       // 2 (Parantezsiz!)
int ilkKelimeHarf = kelimeler[0].length();  // 4 (Parantezli!)
```

---

### 2. Tuzak: `split(".")` Regex Kaçış Karakteri Tuzağı
Java'da `split` metodu arka planda bir Düzenli İfade (*Regex*) çalıştırır. Regex'te nokta (`.`) sembolü *"herhangi bir karakter"* anlamına gelir!

```java
String dosya = "proje.rapor.pdf";

// ❌ HATA: Noktaya göre bölmez, boş dizi üretir veya tüm harfleri siler!
// String[] parcalar = dosya.split("."); 

// ✅ DOĞRU YOL: Regex kaçış karakteri (çift ters bölü) kullanılmalıdır:
String[] parcalar = dosya.split("\\.");
System.out.println("Uzantı: " + parcalar[parcalar.length - 1]); // pdf
```

---

### 3. Tuzak: `null` Eleman Kontrolü Yapmadan Arama Yapmak
Kullanıcıdan dinamik toplanan veya boyutu geniş tutulmuş dizilerde boş hücreler `null` kalabilir:

```java
String[] liste = new String[5];
liste[0] = "Elma";
liste[1] = "Armut";
// liste[2], liste[3], liste[4] HALA null!

for (String eleman : liste) {
    // ❌ TEHLİKE: eleman null olduğunda NullPointerException patlar!
    // if (eleman.equals("Elma")) { ... }

    // ✅ DOĞRU VE GÜVENLİ KONTROL:
    if (eleman != null && eleman.equals("Elma")) {
        System.out.println("Bulundu!");
    }
}
```

---

## 10. Kapsamlı Örnek Konsol Uygulaması: `AkilliRehberVeAramaMotoru`

Aşağıdaki program; paralel iki `String[]` dizisinde (isimler ve telefonlar) arama yapan, isimleri alfabetik listeleyen, yeni kişi ekleyen ve virgülle ayrılmış tek satırlık CSV girdisini `split` ile parçalayarak rehbere kaydeden eksiksiz bir konsol uygulamasıdır:

```java
import java.util.Scanner;
import java.util.Arrays;

public class AkilliRehberVeAramaMotoru {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Maksimum 50 kişilik telefon rehberi veritabanı:
        final int KAPASITE = 50;
        String[] isimler = new String[KAPASITE];
        String[] telefonlar = new String[KAPASITE];
        int kisiSayisi = 0;

        // Başlangıç için birkaç örnek kayıt ekleyelim:
        isimler[kisiSayisi] = "Ahmet Yılmaz";   telefonlar[kisiSayisi++] = "05321112233";
        isimler[kisiSayisi] = "Zeynep Kaya";    telefonlar[kisiSayisi++] = "05442223344";
        isimler[kisiSayisi] = "Canan Demir";    telefonlar[kisiSayisi++] = "05553334455";
        isimler[kisiSayisi] = "Burak Çelik";    telefonlar[kisiSayisi++] = "05054445566";

        int secim;

        do {
            System.out.println("\n==========================================");
            System.out.println("      KTÜ AKILLI TELEFON REHBERİ          ");
            System.out.println("==========================================");
            System.out.println("1 - Tüm Rehberi Listele");
            System.out.println("2 - İsim ile Kişi Ara (İçerik Arama)");
            System.out.println("3 - Hızlı Kişi Ekle (Ad Soyad,Telefon)");
            System.out.println("4 - Baş Harfe Göre Filtrele");
            System.out.println("0 - Çıkış Yap");
            System.out.print("Lütfen bir işlem seçiniz (0-4): ");
            secim = scanner.nextInt();
            scanner.nextLine(); // Boşluk karakterini temizle (Buffer flush)

            switch (secim) {
                case 1:
                    System.out.println("\n--- KAYITLI KİŞİ LİSTESİ (" + kisiSayisi + " Kişi) ---");
                    for (int i = 0; i < kisiSayisi; i++) {
                        System.out.printf("%d) %-20s : %s\n", (i + 1), isimler[i], telefonlar[i]);
                    }
                    break;

                case 2:
                    System.out.print("\nAranacak kişinin adını veya bir kısmını giriniz: ");
                    String aranan = scanner.nextLine().trim().toLowerCase();
                    boolean bulundu = false;

                    for (int i = 0; i < kisiSayisi; i++) {
                        // Küçük harfe çevirerek duyarsız içerik kontrolü:
                        if (isimler[i].toLowerCase().contains(aranan)) {
                            System.out.println("-> Eşleşen Kayıt: " + isimler[i] + " (" + telefonlar[i] + ")");
                            bulundu = true;
                        }
                    }
                    if (!bulundu) {
                        System.out.println("❌ Aradığınız kriterde hiçbir kişi bulunamadı.");
                    }
                    break;

                case 3:
                    if (kisiSayisi >= KAPASITE) {
                        System.out.println("❌ Rehber hafızası dolu! Yeni kayıt eklenemez.");
                        break;
                    }

                    System.out.print("\nKişi Bilgisini 'Ad Soyad,Telefon' formatında giriniz: ");
                    String hamGirdi = scanner.nextLine();

                    // split() ile virgülü baz alarak parçalayalım:
                    String[] parcalar = hamGirdi.split(",");

                    if (parcalar.length == 2 && !parcalar[0].trim().isEmpty() && !parcalar[1].trim().isEmpty()) {
                        isimler[kisiSayisi] = parcalar[0].trim();
                        telefonlar[kisiSayisi] = parcalar[1].trim();
                        kisiSayisi++;
                        System.out.println("✅ Kişi başarıyla eklendi: " + parcalar[0].trim());
                    } else {
                        System.out.println("❌ Geçersiz format! Lütfen araya virgül koyarak giriniz.");
                    }
                    break;

                case 4:
                    System.out.print("\nFiltrelemek istediğiniz baş harfi giriniz: ");
                    String harf = scanner.nextLine().trim().toUpperCase();

                    System.out.println("\n'" + harf + "' Harfi ile Başlayan Kişiler:");
                    int sayac = 0;
                    for (int i = 0; i < kisiSayisi; i++) {
                        if (isimler[i].toUpperCase().startsWith(harf)) {
                            System.out.println("-> " + isimler[i] + " - " + telefonlar[i]);
                            sayac++;
                        }
                    }
                    if (sayac == 0) {
                        System.out.println("Bu harfle başlayan kayıt bulunamadı.");
                    }
                    break;

                case 0:
                    System.out.println("-> Rehberden güvenle çıkıldı. İyi günler dileriz!");
                    break;

                default:
                    System.out.println("❌ Geçersiz işlem seçimi!");
                    break;
            }

        } while (secim != 0);

        scanner.close();
    }
}
```

---

## 11. Öğrenciler İçin Pratik Sınıf İçi Alıştırmalar

### ✍️ Alıştırma 1: Kod İzleme (Tracing - `split` ve Dizi Boyutu)
Aşağıdaki kod parçasının konsol çıktısını adım adım hesaplayınız:

```java
public class StringDiziIzleme {
    public static void main(String[] args) {
        String metin = "Karadeniz-Teknik-Universitesi-Bilgisayar";
        String[] parcalar = metin.split("-");

        System.out.println("Boyut: " + parcalar.length);
        System.out.println("İlk: " + parcalar[0]);
        System.out.println("Son: " + parcalar[parcalar.length - 1]);
        System.out.println("Birleşim: " + String.join(" ", parcalar));
    }
}
```

---

### 🐛 Alıştırma 2: Hata Avcısı (Debug Egzersizi)
Aşağıdaki kodda bulunan **2 derleme hatasını** ve **1 çalışma zamanı (Runtime) çökme hatasını** tespit ediniz:

```java
String[] sehirler = new String[3];
sehirler[0] = "Ankara";
sehirler[1] = "İzmir";

System.out.println("Şehir sayısı: " + sehirler.length()); // Hata 1 nerede?

for (int i = 0; i <= sehirler.length; i++) { // Hata 2 nerede?
    if (sehirler[i].equals("Ankara")) {      // Hata 3 (Çökme) nerede?
        System.out.println("Başkent bulundu");
    }
}
```

---

### 🔄 Alıştırma 3: Palindrom Kelimeleri Ayıklama
Bir `String[] kelimeler = {"kayak", "java", "radar", "bilgisayar", "madam", "kod"};` dizisi içindeki tersten okunuşu kendisine eşit olan **Palindrom kelimeleri** bulup ekrana yazdıran programı kodlayınız.

---

### 🔠 Alıştırma 4: Dizideki Tüm Kelimeleri Büyük Harfe Çevirme
Kendisine verilen bir `String[]` dizisindeki her bir metni `.toUpperCase()` ile büyük harfe çevirip aynı indekse tekrar kaydeden ve diziyi güncelleyen döngüyü yazınız.

---

## 12. Ödev Projeleri

Aşağıdaki projeleri her biri bağımsız bir `.java` dosyası olacak şekilde kodlayınız. `null` kontrollerine, `split()` kullanımına ve metin karşılaştırmalarında `.equals()` kurallarına özen gösteriniz.

---

### 📋 Ödev 1: Akıllı Cümle Analizcisi ve Kelime Sayacı
**Dosya Adı:** `CumleAnalizMotoru.java`

**Problem Tanımı:**  
Kullanıcıdan konsoldan uzun bir paragraf/cümle alınız:
1. Cümleyi boşluk karakterine (`" "`) göre bölerek `String[] kelimeler` dizisine dönüştürünüz.
2. **Hesaplamalar:**
   - Toplam kelime sayısı,
   - Cümledeki **en uzun kelime** ve harf sayısı,
   - Cümledeki **en kısa kelime** ve harf sayısı,
   - Kelimelerin ortalama harf uzunluğu (`double`),
   - Cümledeki tüm kelimeleri alfabetik sıraya (`Arrays.sort`) dizerek listeleme.

---

### 📋 Ödev 2: Optik Sınav Değerlendirme Sistemi
**Dosya Adı:** `OptikFormDegerlendirici.java`

**Problem Tanımı:**  
Bir test sınavında 10 soru sorulmuştur.
- Doğru Cevap Anahtarı:  
  `char[] cevapAnahtari = {'A', 'C', 'B', 'D', 'A', 'E', 'D', 'C', 'B', 'A'};`
- 5 öğrencinin verdiği cevapları temsil eden `String[] ogrenciCevaplari` dizisi:  
  Örn: `"ACBDAEDCBA"` (Öğrenci 1), `"ACBDABDCBE"` (Öğrenci 2)...
- Her öğrencinin doğru, yanlış ve boş sayısını hesaplayıp **100 üzerinden sınav puanını** ekrana basan programı yazınız.

---

### 📋 Ödev 3: Güvenli Yorum ve Yasaklı Kelime Filtresi
**Dosya Adı:** `YorumSansurleyici.java`

**Problem Tanımı:**  
Bir sosyal medya platformunun içerik denetleme filtresini kodlayınız:
1. Yasaklı kelimelerden oluşan bir dizi tanımlayınız:  
   `String[] yasakliKelimeler = {"kötü", "çirkin", "sahte", "hile", "spam"};`
2. Kullanıcıdan bir yorum metni alınız (Örn: *"Bu ürün çok sahte ve kötü sakın almayın"*).
3. Metni `split(" ")` ile kelimelerine ayırınız.
4. Eğer kelime yasaklı kelimeler dizisinde varsa, o kelimenin harf sayısı kadar yıldız (`"***"`) basarak sansürleyiniz.
5. Sansürlenmiş kelimeleri `String.join(" ", sansurluKelimeler)` ile tekrar tek bir cümle haline getirip ekrana yazdırınız.

---

### 📋 Ödev 4: Çok Dilli Karşılama Sözlüğü
**Dosya Adı:** `CokDilliKarsilama.java`

**Problem Tanımı:**  
İki paralel `String[]` dizisi tanımlayınız:
- `String[] diller = {"Türkçe", "İngilizce", "Almanca", "Fransızca", "İspanyolca"};`
- `String[] selamlar = {"Merhaba!", "Hello!", "Guten Tag!", "Bonjour!", "¡Hola!"};`
1. Kullanıcıya desteklenen dilleri numaralandırılmış bir menü ile sununuz.
2. Kullanıcının seçtiği dilin indeksine göre o dildeki selamlama mesajını ekrana basınız.
3. Kullanıcı doğrudan dil adını metin olarak yazarsa (Büyük/küçük harf duyarsız `.equalsIgnoreCase()`), arama algoritmasıyla dili bulup selamı ekrana basınız.
