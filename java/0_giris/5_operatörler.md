# 5. Java'da Operatörler Rehberi

Bu rehber, Java programlama dilinde değişkenler ve sabitler üzerinde matematiksel, mantıksal, ilişkisel ve bit düzeyinde işlem yapmamızı sağlayan **operatörleri (operators)**, öncelik sıralarını, kısa devre mantığını ve dizindeki [`Operator.java`](./Operator.java) programının detaylı incelemesini ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - ☕ [1. Java Dünyasına Giriş ve OOP (1_java_giris.md)](./1_java_giris.md)
> - 🐧 [2. Temel Linux Komutları (2_linux.md)](./2_linux.md)
> - 📦 [3. Değişkenler ve Veri Tipleri (3_degisken.md)](./3_degisken.md)
> - 🔒 [4. Sabitler ve Literaller (4_sabitler.md)](./4_sabitler.md)

---

## 📌 İçindekiler
1. [Operatör ve Operand Kavramı](#1-operatör-ve-operand-kavramı)
2. [Aritmetik Operatörler (`+`, `-`, `*`, `/`, `%`)](#2-aritmetik-operatörler)
3. [Birli (Unary) Operatörler: Önceden ve Sonradan Artırma (`++`, `--`)](#3-birli-unary-operatörler)
4. [İlişkisel (Karşılaştırma) Operatörleri (`==`, `!=`, `>`, `<`, `>=`, `<=`)](#4-ilişkisel-karşılaştırma-operatörleri)
5. [Mantıksal Operatörler (`&&`, `||`, `!`) ve Kısa Devre Mantığı](#5-mantıksal-operatörler-ve-kısa-devre-mantığı)
6. [Atama ve Bileşik Atama Operatörleri (`=`, `+=`, `-=`, `*=`, `/=`, `%=`)](#6-atama-ve-bileşik-atama-operatörleri)
7. [Koşul (Ternary / Üçlü) Operatörü (`? :`)](#7-koşul-ternary--üçlü-operatörü)
8. [Bit Düzeyinde (Bitwise) ve Kaydırma Operatörleri](#8-bit-düzeyinde-bitwise-ve-kaydırma-operatörleri)
9. [Operatör Önceliği Tablosu](#9-operatör-önceliği-tablosu)
10. [Örnek Kod: Operator.java Programı](#10-örnek-kod-operatorjava-programı)
11. [En Sık Yapılan Hatalar ve Tuzaklar](#11-en-sık-yapılan-hatalar-ve-tuzaklar)
12. [Alıştırmalar ve Örnek Ödev Soruları](#12-alıştırmalar-ve-örnek-ödev-soruları)

---

## 1. Operatör ve Operand Kavramı

- **Operatör (İşleç):** Belirli bir matematiksel veya mantıksal eylemi gerçekleştiren özel sembollerdir (`+`, `-`, `*`, `&&` vb.).
- **Operand (İşlenen):** Operatörün işlem yaptığı değerlere veya değişkenlere denir.
  - Örn: `toplam = a + b;` ifadesinde `+` ve `=` **operatör**, `a` ve `b` ise **operand**'dır.

### Aldıkları Operand Sayısına Göre Operatörler:
1. **Birli (Unary) Operatörler:** Tek bir operand ile çalışır (`++x`, `-a`, `!aktif`).
2. **İkili (Binary) Operatörler:** İki operand arasında çalışır (`a + b`, `x == y`).
3. **Üçlü (Ternary) Operatörler:** Üç operand alır (`kosul ? deger1 : deger2`).

---

## 2. Aritmetik Operatörler

Sayısal türler (`int`, `double` vb.) üzerinde temel matematik işlemlerini yapar:

| Operatör | İşlem | Örnek (`a=10, b=3`) | Sonuç |
| :---: | :--- | :--- | :--- |
| **`+`** | Toplama | `a + b` | `13` |
| **`-`** | Çıkarma | `a - b` | `7` |
| **`*`** | Çarpma | `a * b` | `30` |
| **`/`** | Bölme | `a / b` *(Tam sayı bölmesi)* | `3` *(0.333 kısmı atılır!)* |
| **`%`** | Mod (Kalan) | `a % b` | `1` *(10'un 3'e bölümünden kalan)* |

> 💡 **Kritik İpucu (Tam Sayı Bölmesi):** Bölme işleminde iki operand da tamsayı ise sonuç tamsayıdır (`5 / 2 = 2`). Hassas sonuç için taraflardan en az birini ondalıklı yazmalı veya dönüştürmelisiniz: `(double) 5 / 2` veya `5.0 / 2` ➡️ `2.5`.

---

## 3. Birli (Unary) Operatörler

Bir değişkenin değerini 1 artırmak veya 1 azaltmak için kullanılır. Yazıldığı yere göre çalışma mantığı tamamen değişir:

### Prefix (Önceden Artırma/Azaltma): `++x`, `--x`
- **Kural:** Önce değişkenin değeri 1 artırılır, ardından o satırdaki işleme dahil edilir.
  ```java
  int a = 5;
  int b = ++a; // 'a' önce 6 olur, sonra 'b'ye 6 atanır. (a=6, b=6)
  ```

### Postfix (Sonradan Artırma/Azaltma): `x++`, `x--`
- **Kural:** Değişkenin mevcut değeri o anki işlemde kullanılır, işlem bittikten sonra değişkenin değeri 1 artırılır.
  ```java
  int x = 5;
  int y = x++; // 'y'ye x'in o anki değeri (5) atanır, ardından x 6 olur. (x=6, y=5)
  ```

---

## 4. İlişkisel (Karşılaştırma) Operatörleri

İki değeri birbiriyle kıyaslar. Sonucu her zaman bir **`boolean` (`true` veya `false`)** değerdir. Karar yapıları (`if`) ve döngülerde (`while`, `for`) temel oluştururlar.

| Operatör | Anlamı | Örnek (`x=10, y=20`) | Sonuç |
| :---: | :--- | :--- | :--- |
| **`==`** | Eşit mi? | `x == y` | `false` |
| **`!=`** | Eşit DEĞİL mi? | `x != y` | `true` |
| **`>`** | Büyüktür | `x > y` | `false` |
| **`<`** | Küçüktür | `x < y` | `true` |
| **`>=`** | Büyük veya Eşit | `x >= 10` | `true` |
| **`<=`** | Küçük veya Eşit | `y <= 20` | `true` |

> ⚠️ **UYARI:** Atama operatörü olan tek eşittir (`=`) ile karşılaştırma operatörü olan çift eşittir (`==`) birbirine karıştırılmamalıdır!

---

## 5. Mantıksal Operatörler ve Kısa Devre Mantığı

Birden fazla boolean koşulu birleştirmek için kullanılır:

### Doğruluk Tablosu:

| `A` | `B` | `A && B` (VE) | `A \|\| B` (VEYA) | `!A` (DEĞİL) |
| :---: | :---: | :---: | :---: | :---: |
| `true` | `true` | **`true`** | **`true`** | `false` |
| `true` | `false` | `false` | **`true`** | `false` |
| `false`| `true` | `false` | **`true`** | `true` |
| `false`| `false`| `false` | `false` | `true` |

### ⚡ Kısa Devre Değerlendirmesi (Short-Circuit Evaluation):
Java performans kazanmak ve gereksiz işlem yapmamak için şu kuralı uygular:
- **`&&` (VE) İşleminde:** İlk koşul `false` ise, ikinci koşulun ne olduğuna bakılmaksızın sonuç kesinlikle `false` olacağından **sağ taraftaki ifade çalıştırılmaz**.
- **`||` (VEYA) İşleminde:** İlk koşul `true` ise, ikinci koşula bakılmaksızın sonuç kesinlikle `true` olacağından **sağ taraftaki ifade çalıştırılmaz**.

```java
int sayi = 5;
// İlk koşul (sayi > 10) false olduğu için sağdaki (++sayi > 5) HİÇ ÇALIŞTIRILMAZ:
boolean test = (sayi > 10) && (++sayi > 5);
System.out.println(sayi); // sayi hala 5'tir, 6 olmaz!
```

---

## 6. Atama ve Bileşik Atama Operatörleri

Değişkenlere değer atamak ve aritmetik işlemlerle atamayı tek adımda birleştirmek için kullanılır:

| Operatör | Uzun Yazımı | Açıklama |
| :--- | :--- | :--- |
| **`=`** | `x = 5;` | Sağdaki değeri soldaki değişkene atar. |
| **`+=`** | `x += 3;` ➡️ `x = x + 3;` | x'in değerini 3 artırır ve günceller. |
| **`-=`** | `x -= 2;` ➡️ `x = x - 2;` | x'in değerinden 2 çıkarır. |
| **`*=`** | `x *= 4;` ➡️ `x = x * 4;` | x'i 4 ile çarpar. |
| **`/=`** | `x /= 2;` ➡️ `x = x / 2;` | x'i 2'ye böler. |
| **`%=`** | `x %= 3;` ➡️ `x = x % 3;` | x'in 3'e modunu alır. |

---

## 7. Koşul (Ternary / Üçlü) Operatörü (`? :`)

Basit bir `if-else` yapısını tek satırda yazmayı sağlayan tek üçlü operatördür.

### Sözdizimi:
```java
sonuc = (kosul) ? dogruysa_deger : yanlissa_deger;
```

### Örnek Kullanım:
```java
int not = 75;
String durum = (not >= 60) ? "Geçti" : "Kaldı";
System.out.println(durum); // Çıktı: Geçti
```

---

## 8. Bit Düzeyinde (Bitwise) ve Kaydırma Operatörleri

Sayıların bellekteki ikilik (0 ve 1) bitleri üzerinde doğrudan işlem yapar:

| Operatör | Adı | Açıklama |
| :---: | :--- | :--- |
| **`&`** | Bitwise AND | Karşılıklı iki bit de 1 ise 1, aksi halde 0 üretir. |
| **`\|`** | Bitwise OR | Karşılıklı bitlerden en az biri 1 ise 1 üretir. |
| **`^`** | Bitwise XOR | Bitler birbirinden farklıysa 1, aynıysa 0 üretir. |
| **`~`** | Bitwise NOT | Tüm bitleri tersine çevirir (1 ➡️ 0, 0 ➡️ 1). |
| **`<<`** | Sola Kaydırma | Bitleri sola kaydırır (`x << n` ➡️ `x * 2^n`). |
| **`>>`** | Sağa Kaydırma | Bitleri işaretini koruyarak sağa kaydırır (`x >> n` ➡️ `x / 2^n`). |

---

## 9. Operatör Önceliği Tablosu

Bir ifadede birden fazla operatör varsa hangi işlemin önce yapılacağını belirler (En yüksekten en düşüğe):

| Öncelik Sırası | Operatör Grubu | Operatörler | Yön (Associativity) |
| :---: | :--- | :--- | :---: |
| **1 (En Yüksek)**| Parantez & Postfix | `()` `[]` `.` `x++` `x--` | Soldan Sağa |
| **2** | Birli (Unary) | `++x` `--x` `+` `-` `!` `~` | Sağdan Sola |
| **3** | Çarpma / Bölme / Mod | `*` `/` `%` | Soldan Sağa |
| **4** | Toplama / Çıkarma | `+` `-` | Soldan Sağa |
| **5** | Bit Kaydırma | `<<` `>>` `>>>` | Soldan Sağa |
| **6** | İlişkisel | `<` `<=` `>` `>=` `instanceof` | Soldan Sağa |
| **7** | Eşitlik | `==` `!=` | Soldan Sağa |
| **8** | Bit Düzeyi | `&` `^` `\|` | Soldan Sağa |
| **9** | Mantıksal VE | `&&` | Soldan Sağa |
| **10** | Mantıksal VEYA | `\|\|` | Soldan Sağa |
| **11** | Koşul (Ternary) | `? :` | Sağdan Sola |
| **12 (En Düşük)**| Atama | `=` `+=` `-=` `*=` `/=` `%=` | Sağdan Sola |

> 💡 **Altın Kural:** Karmaşık bir hesaplamada operatör önceliğini ezberlemeye çalışmak yerine **daima parantez `( )` kullanın**. Parantez hem hatayı önler hem de kodun okunabilirliğini artırır.

---

## 10. Örnek Kod: `Operator.java` Programı

Dizinde yer alan [`Operator.java`](./Operator.java) programı, yukarıda anlatılan tüm operatörleri bölüm bölüm tek bir `main` metodu içinde canlı olarak çalıştırır ve sonuçlarını konsola basar.

Programı çalıştırmak için terminalde:
```bash
javac Operator.java
java Operator
```

Program çıktısının özeti:
```text
--- 1. TEMEL ARİTMETİK OPERATÖRLER ---
Toplama (a + b)             : 26
Çıkarma (a - b)             : 14
Tam Sayı Bölmesi (a / b)    : 3
Hassas Bölme ((double)a / b): 3.3333333333333335
Mod / Kalan (a % b)         : 2

--- 2. BİRLİ OPERATÖRLER (ÖNCE/SONRA ARTIRMA-AZALTMA) ---
sayi++ sonrası dönen değer   : 10 (sayi şu an: 11)
++sayi sonrası dönen değer   : 12 (sayi şu an: 12)

--- 3. İLİŞKİSEL (KARŞILAŞTIRMA) OPERATÖRLERİ ---
Eşit mi? (x == y)          : false
Küçük mü? (x < y)          : true

--- 4. MANTIKSAL OPERATÖRLER ---
Yaş >= 18 VE Ehliyet Var mı? (&&) : true
Ehliyet VEYA Rapor Var mı? (||)   : true
```

---

## 11. En Sık Yapılan Hatalar ve Tuzaklar

1. **`=` ile `==` Karışıklığı:**
   - `=` atama yapar, `==` eşitliği sorgular. Koşullarda yanlışlıkla `=` yazmak en yaygın mantık hatasıdır.
2. **String Metinlerini `==` ile Karşılaştırmak:**
   - Sayılarda `==` değeri kontrol eder. Ancak `String` nesnelerinde `==` değerleri değil, nesnelerin **bellek adreslerini** karşılaştırır!
   - Metin eşitliği için mutlaka **`.equals()`** metodu kullanılmalıdır (`ad.equals("Ali")`).
3. **Sıfıra Bölme Hatası (`ArithmeticException`):**
   - Tam sayılarda sıfıra bölme (`sayi / 0`) programın anında çökmesine (`java.lang.ArithmeticException: / by zero`) neden olur.
4. **Postfix/Prefix Karışıklığı:**
   - Bir döngü veya yazdırma ifadesi içinde `x++` kullanırken o anki değerin eski değer olduğunu unutmak.

---

## 12. Alıştırmalar ve Örnek Ödev Soruları

### ✍️ Pratik Sınıf İçi Alıştırmalar:
1. **İfade İzleme (Tracing):** Aşağıdaki kod parçasının ekran çıktısını kod çalıştırmadan önce kağıt üzerinde tahmin ediniz, ardından çalıştırarak doğruluğunu kontrol ediniz:
   ```java
   int a = 4;
   int b = ++a * 2 + a++;
   System.out.println("a: " + a + ", b: " + b);
   ```
2. **Kısa Devre Testi:** Aşağıdaki ifadede `sayac` değerinin neden değişmediğini açıklayınız:
   ```java
   int sayac = 10;
   boolean sonuc = (5 > 2) || (++sayac > 10);
   System.out.println("Sayaç: " + sayac);
   ```
3. **Ternary Pratiği:** Bir sayının pozitif mi, negatif mi yoksa sıfır mı olduğunu tek bir satırda iç içe iki ternary (`? :`) operatörüyle `String durum = ...` olarak belirleyiniz.

### 📋 Ödev Programları ve Proje Konuları:

#### Ödev 1: Artık Yıl (Leap Year) Belirleyici (`ArtikYil.java`)
- **Amaç:** Bir yılın artık yıl olup olmadığını mantıksal operatörlerle (`&&`, `||`, `%`) tek satırlık bir boolean ifadesiyle belirleyiniz.
- **Kural:**
  - Bir yıl 4'e tam bölünmeli VE 100'e bölünmemeli, VEYA 400'e tam bölünmelidir.
- **Örnek Tanımlama:** `int yil = 2024;`
- **Beklenen Çıktı:**
  ```text
  Yıl: 2024 -> Artık yıl mı? : true (Şubat 29 çeker)
  ```

#### Ödev 2: ATM Banknot Dağıtıcı Programı (`AtmBanknot.java`)
- **Amaç:** Bir müşterinin hesabından çekeceği sabit para tutarı (`int cekilecekTutar = 2870;`) için ATM'nin vereceği en az banknot sayısını hesaplayınız.
- **Kullanılacak Banknotlar:** 200 TL, 100 TL, 50 TL, 20 TL ve 10 TL.
- **Yöntem:** Bölme (`/`) ve Kalan/Mod (`%`) operatörlerini ardışık olarak kullanınız.
- **Beklenen Çıktı:**
  ```text
  Çekilecek Tutar: 2870 TL
  -----------------------
  200 TL : 14 adet
  100 TL : 0 adet
   50 TL : 1 adet
   20 TL : 1 adet
   10 TL : 0 adet
  -----------------------
  Kalan Çekilemeyen: 0 TL
  ```
