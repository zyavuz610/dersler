# 5. Modül: Java'da Nesne Yönelimli Programlama (OOP) ve Sınıflar

Bu dizin, **Java Nesne Yönelimli Programlama (Object-Oriented Programming - OOP)** kavramlarını en temelden ileri seviyeye kadar adım adım ele alan kapsamlı ders modülüdür. 

Tüm ders dokümanları pedagojik konu anlatımları, gerçek hayat analojileri, bellek (Stack/Heap) şemaları, çalıştırılabilir kod örnekleri, tuzaklar ve çözümlü alıştırmalar/ödevler ile donatılmıştır.

---

## 🧭 Ders Dokümanları İndeksi ve Öğrenim Sırası

```mermaid
flowchart TD
    M1["1. Sınıf ve Nesne Temelleri<br>(1_class-tanım.md)"] --> M2["2. Kurucular, Yıkıcılar & this<br>(2_class-constructor.md)"]
    M2 --> M3["3. static & Sınıf Üyeleri<br>(3_static-class.md)"]
    M3 --> M4["4. Kapsülleme & Getter/Setter<br>(4_kapsulleme.md)"]
    M4 --> M5["5. Metotlar & Nesne Etkileşimi<br>(5_class_ve_fonksiyonlar.md)"]
    M5 --> M6["6. Nesne Dizileri<br>(6_class_ve_diziler.md)"]
    M6 --> M7["7. Kompozisyon (HAS-A)<br>(7_composition.md)"]
    M7 --> M8["8. Kalıtım / Miras (IS-A)<br>(8_kalıtım.md)"]
    M8 --> M9["9. Çok Biçimlilik (Polymorphism)<br>(9_polymorphizm.md)"]
    M9 --> M10["A. Soyut Sınıflar (Abstract Classes)<br>(A_soyut-sınıflar.md)"]
    M10 --> M11["B. Arayüzler (Interfaces)<br>(B_arayüzler.md)"]

    style M1 fill:#dfd,stroke:#333,stroke-width:1px
    style M2 fill:#dfd,stroke:#333,stroke-width:1px
    style M3 fill:#dfd,stroke:#333,stroke-width:1px
    style M4 fill:#dfd,stroke:#333,stroke-width:1px
    style M5 fill:#dfd,stroke:#333,stroke-width:1px
    style M6 fill:#dfd,stroke:#333,stroke-width:1px
    style M7 fill:#dfd,stroke:#333,stroke-width:1px
    style M8 fill:#dfd,stroke:#333,stroke-width:1px
    style M9 fill:#dfd,stroke:#333,stroke-width:1px
    style M10 fill:#ffe,stroke:#e60,stroke-width:2px
    style M11 fill:#ffe,stroke:#e60,stroke-width:2px
```

---

## 📋 Modül Konu Başlıkları Tablosu

| No | Doküman | Konu Başlığı | Temel Kazanımlar ve Kapsam |
| :---: | :--- | :--- | :--- |
| **01** | [**1_class-tanım.md**](./1_class-tanım.md) | **Sınıf ve Nesne Temelleri** | • Class & Object mantığı, mimari plan analojisi<br>• Stack ve Heap bellek mimarisi<br>• `public`, `private`, `protected`, `default` erişim belirleyicileri karşılaştırması |
| **02** | [**2_class-constructor.md**](./2_class-constructor.md) | **Kurucu, Yıkıcı & `this`** | • Constructor mantığı ve aşırı yükleme (overloading)<br>• `this` anahtar kelimesi ve kurucu zincirleme (`this(...)`)<br>• C++ Destructor vs. Java Garbage Collector, `AutoCloseable` & `close()` |
| **03** | [**3_static-class.md**](./3_static-class.md) | **`static` Anahtar Kelimesi** | • Nesne değişkeni vs. Sınıf değişkeni farkı, sayaçlar<br>• `Math` sınıfı ve yardımcı (utility) sınıf tasarımı<br>• `static` başlatma blokları (`static { ... }`) ve yaygın derleme tuzakları |
| **04** | [**4_kapsulleme.md**](./4_kapsulleme.md) | **Kapsülleme & Getter/Setter** | • Veri gizleme (*Information Hiding*) ilkesi<br>• Setter içinde veri doğrulama (*validation*)<br>• Salt okunur / yazılır alanlar ve savunmacı kopyalama (*Defensive Copying*) |
| **05** | [**5_class_ve_fonksiyonlar.md**](./5_class_ve_fonksiyonlar.md) | **Metotlar ve Nesne Etkileşimi** | • Java'da *Pass-by-Value* kuralının nesnelerdeki davranışı<br>• Alanların otomatik varsayılan değerleri vs. yerel değişkenler<br>• Metotlardan nesne döndürme (*Return Object*) ve nesneler arası transfer |
| **06** | [**6_class_ve_diziler.md**](./6_class_ve_diziler.md) | **Nesne Dizileri** | • Nesne dizilerinin iki aşamalı başlatma zorunluluğu (`null` tuzağı)<br>• For-each gezinimi, arama, filtreleme, max/min algoritmaları<br>• Metotlara dizi gönderme ve metottan nesne dizisi döndürme |
| **07** | [**7_composition.md**](./7_composition.md) | **Kompozisyon ("HAS-A")** | • Sınıf içinde başka sınıf nesnesi barındırma<br>• Sıkı sahiplik (*Composition*) vs. Gevşek sahiplik (*Aggregation*)<br>• İç sınıflar (*Inner / Nested Classes*) ve Demeter Yasası |
| **08** | [**8_kalıtım.md**](./8_kalıtım.md) | **Kalıtım / Miras ("IS-A")** | • `extends` sözdizimi, üst ve alt sınıf hiyerarşisi<br>• Tekli, çok seviyeli ve hiyerarşik miras modelleri<br>• Java'da sınıflarda çoklu miras yasağı ve **Elmas Problemi (Diamond Problem)**<br>• `super()` çağrı kuralları, `final` sınıflar ve `Object` atası |
| **09** | [**9_polymorphizm.md**](./9_polymorphizm.md) | **Çok Biçimlilik (Polymorphism)** | • Statik (Overloading) vs. Dinamik (Overriding) polimorfizm<br>• `@Override` kuralları ve Dinamik Metot Bağlama (*Dynamic Dispatch*)<br>• Upcasting, Downcasting, `instanceof` ve Pattern Matching |
| **10** | [**A_soyut-sınıflar.md**](./A_soyut-sınıflar.md) | **Soyut Sınıflar (Abstract Classes)** | • `abstract` sınıf ve gövdesiz soyut metot mantığı<br>• Neden `new` yapılamaz? Kurucularının alt sınıflara desteği<br>• Şablon Metot Deseni (*Template Method Pattern*) ve `final` çelişkisi |
| **11** | [**B_arayüzler.md**](./B_arayüzler.md) | **Arayüzler (Interfaces)** | • `interface` ve `implements` ile tam soyutlama ve sözleşme ilkesi<br>• Çoklu arayüz uygulama (Java'nın çoklu miras çözümü)<br>• Java 8/9 ile gelen `default`, `static` ve `private` metotlar<br>• **Büyük Karşılaştırma:** Soyut Sınıf vs. Arayüz seçim rehberi |

