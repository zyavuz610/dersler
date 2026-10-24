# 0. Giriş: Java Programlamaya Giriş Modülü

Bu dizin, **Java Programlama Dersi** için hazırlanan başlangıç modülüdür. Java'nın çalışma prensiplerini, Nesneye Dayalı Programlama (OOP) temellerini, geliştirme ortamlarında kullanılan temel Linux terminal komutlarını ve değişkenler konusunu adım adım ele alan kapsamlı ders dokümanlarını ve örnek kodları barındırır.

---

## 🧭 Konu Anlatımı ve Ders Rehberleri İndeksi

Aşağıdaki sırayı takip ederek modülü eksiksiz bir şekilde tamamlayabilirsiniz:

```mermaid
flowchart TD
    M1["1. Bölüm: Java'ya Giriş & OOP Mantığı<br>(1_java_giris.md)"] --> M2["2. Bölüm: Temel Linux Komutları<br>(2_linux.md)"]
    M2 --> M3["3. Bölüm: Değişkenler & Veri Tipleri<br>(3_degisken.md)"]
    M3 --> M4["4. Bölüm: Sabitler & Literaller<br>(4_sabitler.md)"]
    M4 --> M5["5. Bölüm: Operatörler<br>(5_operatörler.md)"]
    M5 --> M6["6. Bölüm: String Yapısı & Metotları<br>(6_string.md)"]
    
    style M1 fill:#f9f,stroke:#333,stroke-width:1px
    style M2 fill:#bbf,stroke:#333,stroke-width:1px
    style M3 fill:#dfd,stroke:#333,stroke-width:1px
    style M4 fill:#ffe,stroke:#333,stroke-width:1px
    style M5 fill:#fed,stroke:#333,stroke-width:1px
    style M6 fill:#def,stroke:#333,stroke-width:1px
```

| No | Konu Başlığı | Dosya | Açıklama ve Kapsam |
| :---: | :--- | :--- | :--- |
| **01** | **Java Dünyası & OOP Mantığı** | [1_java_giris.md](./1_java_giris.md) | • Java'da programın kendisinin bile nesne/sınıf olması<br>• Sınıf (*Class*) ve Nesne (*Object*) kavramları<br>• OOP'nin 4 temel ilkesi (*Encapsulation, Inheritance, vb.*)<br>• `Main.java` analizi (`public`, `static`, `void`, `main`)<br>• JDK, JRE, JVM ilişkisi ve `javac` / `java` komutları |
| **02** | **Temel Linux & Terminal Rehberi** | [2_linux.md](./2_linux.md) | • Dizin gezinme ve listeleme (`pwd`, `ls`, `cd`, `clear`)<br>• Dosya/klasör yönetimi (`mkdir`, `touch`, `cp`, `mv`, `rm`)<br>• Dosya içeriği okuma (`cat`, `less`, `head`, `tail`)<br>• Sistem ve Java sürüm sorgulama (`cat /etc/os-release`, `which`)<br>• Terminal kısayolları (`Tab`, `Ctrl+C`, `Ctrl+L`) |
| **03** | **Değişkenler & Veri Tipleri** | [3_degisken.md](./3_degisken.md) | • Değişken tanımlama (*declaration*) ve değer atama (*initialization*)<br>• İsimlendirme kuralları ve `camelCase` standardı<br>• 8 İlkel (*Primitive*) tip ve `String` nesnesi<br>• Stack vs. Heap bellek çalışma mantığı<br>• Sabit verilerle **Not Ortalaması Hesaplama** programı<br>• En sık yapılan derleme hataları ve tuzaklar |
| **04** | **Sabitler & Literaller** | [4_sabitler.md](./4_sabitler.md) | • Literal sabit kavramı ve türleri (Tamsayı, Ondalıklı, Karakter, String, Boolean)<br>• Farklı sayı tabanları (Binary `0b`, Octal `0`, Hex `0x`) ve alt tire (`_`) kullanımı<br>• Kaçış karakterleri (`\n`, `\t`, `\"`, `\\`) ve Unicode gösterimi<br>• `final` anahtar kelimesi ile değiştirilemez sabit tanımlama |
| **05** | **Operatörler** | [5_operatörler.md](./5_operatörler.md) | • Aritmetik, birli (prefix/postfix `++`, `--`), ilişkisel ve mantıksal operatörler<br>• Mantıksal işlemlerde kısa devre (*short-circuit*) mantığı<br>• Koşul (*ternary*) operatörü (`? :`), bitwise ve kaydırma operatörleri<br>• Operatör öncelik sıralaması ve yaygın tuzaklar |
| **06** | **String Yapısı & Metotları** | [6_string.md](./6_string.md) | • `String` sınıfı, değişmezlik (*immutability*) ve String Constant Pool mimarisi<br>• `==` ile `.equals()` arasındaki adres ve içerik farkı<br>• Karakter, alt metin (`substring`), arama, düzenleme ve bölme (`split`) metotları<br>• `isEmpty()` vs `isBlank()` farkı ve yaygın tuzaklar |

---

## 💻 Dizindeki Örnek Java Kodları

Ders anlatımlarında kullanılan ve bu dizinde yer alan çalıştırılabilir Java kaynak kodları:

| Kaynak Dosya | İlgili Konu | Açıklama |
| :--- | :--- | :--- |
| [`Main.java`](./Main.java) | İlk Program | Java'nın en temel yapısı, `main` metodu ve konsola `Merhaba Dünya` yazdırma. |
| [`Selam.java`](./Selam.java) | Çıktı & Yorumlar | `System.out.println()` ile `System.out.print()` arasındaki farklar, tek satırlı (`//`) ve çok satırlı (`/* */`) yorum kullanımı. |
| [`Degisken.java`](./Degisken.java) | Değişken Tipleri | `int`, `float`, `char`, `String`, `boolean`, `byte` türlerinin tanımlanması ve metin birleştirme (*concatenation*). |
| [`Operator.java`](./Operator.java) | Tüm Operatörler | Aritmetik, birli artırma/azaltma, ilişkisel, mantıksal, atama, ternary ve bitwise operatörlerin bölüm bölüm çalıştırıldığı kapsamlı demo. |
| [`MyString.java`](./MyString.java) | String Metotları | String Pool, `==` vs `.equals()`, `substring()`, `indexOf()`, `replace()`, `split()`, `join()`, `trim()`, `valueOf()` ve tüm String alt metotlarının tek `main` içinde çalıştırıldığı demo. |
| [`konular.md`](./konular.md) | Ders İzlencesi | Ders müfredatı, önerilen kaynak kitap, temel komut notları ve pratik alıştırma yönergeleri. |


---

## ⚡ Hızlı Başlangıç: Kodları Nasıl Derleyip Çalıştırırsınız?

Terminali (Komut İstemi / Bash) bu dizin (`0_giris`) içinde açtıktan sonra:

```bash
# 1. Kaynak kodu bayt koduna (.class) derleyin:
javac Main.java

# 2. Üretilen sınıfı JVM üzerinde çalıştırın (uzantısız!):
java Main
```

---

## 🎯 Modül Öğrenim Kazanımları

Bu modülü tamamlayan bir öğrenci:
1. Java'nın neden saf bir nesne yönelimli dil olduğunu ve her kodun neden bir sınıf içinde var olması gerektiğini anlar.
2. `javac` derleyicisi ile kaynak koddan (`.java`) ara bayt kodu (`.class`) oluşturmayı ve `java` komutuyla JVM üzerinde çalıştırmayı öğrenir.
3. Linux / Terminal komutlarıyla dosya ve klasörleri hatasız yönetebilir.
4. Java'nın ilkel veri tipleri ile referans tipleri arasındaki temel farkları, bellek kullanımını (Stack/Heap) ve değişken isimlendirme standartlarını kavrar.
5. Değişken tanımlarken yapılan yaygın hataları (ilk değer atamama, parantez dengesizliği, tür uyumsuzluğu, tam sayı bölmesi tuzağı) hızlıca fark edip düzeltebilir.
6. Farklı veri türlerindeki literal sabitleri, sayı tabanlarını (`0b`, `0x`), kaçış karakterlerini ve `final` ile değişmez sabit tanımlamayı etkin şekilde kullanabilir.
7. Aritmetik, ilişkisel, mantıksal, birli (prefix/postfix), koşul (ternary) ve bit düzeyindeki tüm operatörleri öncelik sıralarına uygun biçimde güvenle kullanabilir.
8. `String` sınıfının referans ve değişmezlik (*immutability*) doğasını, `==` ile `.equals()` ayrımını kavrar; hazır metotları (`substring`, `replace`, `split`, `indexOf` vb.) metin işleme senaryolarında yetkinlikle kullanabilir.
