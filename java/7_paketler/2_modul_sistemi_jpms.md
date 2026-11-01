# 2. Java 9 Modül Sistemi (JPMS - Java Platform Module System)

Bu doküman; Java 9 ile yazılım dünyasına giren en köklü mimari dönüşüm olan **Java Platform Modül Sistemini (JPMS / Project Jigsaw)**, klasik `classpath` kütüphane karmaşasının (JAR Hell) nasıl çözüldüğünü, **`module-info.java`** dosyasının yapısını, **`exports`**, **`requires`**, **`opens`** direktiflerini, güçlü kapsüllemeyi (*Strong Encapsulation*) ve **`jlink`** ile mini Java çalışma ortamları üretmeyi ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Paketler ve İçe Aktarma (1_paketler_ve_import.md)](./1_paketler_ve_import.md)
> - 🔜 [Sonraki Modül: İleri Java Konuları (8_ileri konular)](../8_ileri%20konular/readme.md)

---

## 📌 İçindekiler
1. [Neden Modül Sistemi? (Project Jigsaw)](#1-neden-modül-sistemi-project-jigsaw)
   - [Klasik Java'nın İki Büyük Problemi: "JAR Hell" ve Monolitik `rt.jar`](#klasik-javanın-iki-büyük-problemi)
   - [Yetersiz Kapsülleme Sorunu (`public` Olan Her Şeyin Herkese Açık Olması)](#yetersiz-kapsülleme-sorunu)
2. [Modül (Module) Nedir?](#2-modül-module-nedir)
   - ["Paketlerin Paketi" Mimarisi](#paketlerin-paketi-mimarisi)
   - [Temel Kavram: `module-info.java`](#temel-kavram-module-infojava)
3. [`module-info.java` Temel Direktifleri](#3-module-infojava-temel-direktifleri)
   - [1. `requires`: Bağımlılık Bildirimi (Neye İhtiyacım Var?)](#1-requires-bağımlılık-bildirimi)
   - [2. `exports`: Dış Dünyaya Açma (Hangi Paketim Kullanılabilir?)](#2-exports-dış-dünyaya-açma)
   - [3. `requires transitive`: Geçişli Bağımlılık](#3-requires-transitive-geçişli-bağımlılık)
   - [4. `opens`: Yansıma (Reflection) İzni Verme](#4-opens-yansıma-reflection-izni-verme)
4. [Java'nın Çekirdek Modülü: `java.base`](#4-javanın-çekirdek-modülü-javabase)
5. [Hafif Java Runtime Üretimi: `jlink` Aracı](#5-hafif-java-runtime-üretimi-jlink-aracı)
   - [200 MB'lık JVM Yerine 35 MB'lık Özel Mini Çalışma Ortamı](#200-mblık-jvm-yerine-35-mblık-özel-mini-çalışma-ortamı)
6. [Adım Adım Çalıştırılabilir Kod Örnekleri](#6-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [İki Modüllü Proje Mimarisi: `com.hesaplayici` ve `com.uygulama`](#iki-modüllü-proje-mimarisi)
7. [Sık Yapılan Hatalar ve Önemli Kurallar](#7-sık-yapılan-hatalar-ve-önemli-kurallar)
8. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
9. [Ödev Konuları ve Mini Projeler](#9-ödev-konuları-ve-mini-projeler)

---

## 1. Neden Modül Sistemi? (Project Jigsaw)

Java 9 öncesinde (Java 1.0'dan Java 8'e kadar) tüm Java uygulamaları iki temel yapı taşına sahipti: **Sınıflar** ve **Paketler**. Ancak yazılımlar devasa boyutlara ulaştığında bu iki yapı yetersiz kaldı.

### Klasik Java'nın İki Büyük Problemi

#### 1. "JAR Hell" (Kütüphane Çakışması ve Eksiklikleri)
Eski sistemde projeye 50 tane `.jar` dosyası eklerdiniz (*Classpath*).
- Eğer o kütüphanelerden bir tanesi eksikse, program bunu derleme anında **fark edemezdi**!
- Program çalıştıktan saatler sonra eksik sınıfa sıra geldiğinde aniden `NoClassDefFoundError` patlar ve sistem çökerdi.

#### 2. Monolitik ve Hantal `rt.jar`
Eski Java'da basit bir "Merhaba Dünya" yazsanız bile JVM arka planda 60 MB'lık devasa `rt.jar` dosyasını (içinde masaüstü UI araçları, ses motorları, veritabanı sürücüleri olan her şeyi) belleğe yüklemek zorundaydı. Bu durum bulut sunucularında ve IoT cihazlarında ciddi bellek israfına yol açıyordu.

---

### Yetersiz Kapsülleme Sorunu

Java 9 öncesinde bir sınıfı başka bir paketten kullanabilmek için tek çare onu **`public`** yapmaktı.
Fakat bir sınıfı `public` yaptığınız anda, o sınıf **projedeki herkes tarafından ve dışarıdan projeyi kullanan müşteriler tarafından da** görülebilir hale geliyordu. 

Kendi iç yardımcı sınıflarınızı sadece kendi modülünüzün içinde `public` yapıp dış dünyadan gizlemenin hiçbir yolu yoktu!

---

## 2. Modül (Module) Nedir?

> 💡 **Tanım:** Modül; birbiriyle ilişkili paketlerin, kaynak dosyalarının ve konfigürasyonların bir araya getirildiği, **dışarıya neyi açacağını ve dışarıdan neyi talep ettiğini açıkça ilan eden** bağımsız bir yazılım birimidir (**"Paketlerin Paketi"**).

```
+-------------------------------------------------------------+
|                 MODÜL: com.sirket.muhasebe                  |
|                                                             |
|  [ DIŞARIYA AÇILANLAR (exports) ]                           |
|    - com.sirket.muhasebe.api (FaturaServisi.class)          |
|                                                             |
|  [ GİZLİ İÇ PAKETLER (Dışarıdan ASLA erişilemez!) ]        |
|    - com.sirket.muhasebe.dahili (HesaplamaMotoru.class)     |
|    - com.sirket.muhasebe.guvenlik (Sifreleme.class)         |
+-------------------------------------------------------------+
```

---

### Temel Kavram: `module-info.java`

Bir dizinin modül olabilmesi için modül kök dizininde **`module-info.java`** adında özel bir dosya bulunmalıdır:

```java
module com.sirket.muhasebe {
    // Modül direktifleri buraya yazılır
}
```

---

## 3. `module-info.java` Temel Direktifleri

### 1. `requires`: Bağımlılık Bildirimi
Modülünüzün çalışabilmesi için başka hangi modüllere ihtiyaç duyduğunu ilan eder:

```java
module com.sirket.uygulama {
    requires java.sql; // Bu modül veritabanı sürücülerine ihtiyaç duyar!
}
```
Eğer `java.sql` modülü çalışma ortamında yoksa, Java programı başlatmayı **anında reddeder** ve nedenini söyler (Güvenilirlik / Reliable Configuration).

---

### 2. `exports`: Dış Dünyaya Açma
Modülünüzün içindeki hangi paketlerin diğer modüller tarafından kullanılabileceğini belirler. **`exports` edilmeyen paketler `public` olsalar dahi dış modüllerden görünmez!**

```java
module com.sirket.kutuphane {
    // Sadece api paketi dışarıya açıktır:
    exports com.sirket.kutuphane.api;
    
    // dahili (internal) paket yazılmadı -> DIŞ DÜNYAYA TAMAMEN GİZLENDİ!
}
```

---

### 3. `requires transitive`: Geçişli Bağımlılık
Eğer sizin modülünüzü kullanan biri, sizin bağımlı olduğunuz modülü de otomatik olarak kullansın istiyorsanız:

```java
module ModulB {
    requires transitive ModulA; // ModulB'yi kullanan herkes ModulA'yı da otomatik alır!
}
```

---

### 4. `opens`: Yansıma (Reflection) İzni Verme
Hibernate, Spring veya JSON ayrıştırıcıları (Jackson) gibi modern kütüphaneler nesnelerin `private` alanlarına çalışma anında **Reflection** ile erişir. Modül sisteminde buna varsayılan olarak izin verilmez. İzin vermek için `opens` kullanılır:

```java
module com.sirket.model {
    opens com.sirket.model to org.hibernate.orm.core;
}
```

---

## 4. Java'nın Çekirdek Modülü: `java.base`

Java 9 ile birlikte tüm Java standart kütüphanesi 90'dan fazla modüle bölünmüştür (`java.sql`, `java.desktop`, `java.xml` vb.).

Bu hiyerarşinin kalbinde **`java.base`** yer alır:
- `java.lang`, `java.util`, `java.io` gibi temel paketleri barındırır.
- Her Java modülü **otomatik olarak `requires java.base;` direktifine sahiptir**, elinizle yazmanıza gerek yoktur.

---

## 5. Hafif Java Runtime Üretimi: `jlink` Aracı

Eskiden müşteriye veya sunucuya Java uygulamanızı yüklemek için 200-300 MB'lık tam bir JDK / JRE kurmak gerekirdi.

Java 9 Modül Sistemi sayesinde gelen **`jlink` (Java Linker)** aracıyla:
- Sadece projenizin kullandığı modülleri (`java.base`, `java.sql` vb.) çekip,
- İşletim sistemine özel, içinde yalnızca gereken parçaları barındıran **30-40 MB'lık bağımsız bir mini-JVM** paketleyebilirsiniz!

```bash
jlink --module-path $JAVA_HOME/jmods:mlib \
      --add-modules com.sirket.uygulama \
      --output ozel-mini-jre
```

---

## 6. Adım Adım Çalıştırılabilir Kod Örnekleri

İki modüllü temiz bir mimari örneği:

### 1. Modül: `com.matematik` (Hizmet Sağlayan Modül)

#### `src/com.matematik/module-info.java`
```java
module com.matematik {
    // Sadece servis paketini dışarıya açıyoruz:
    exports com.matematik.servis;
}
```

#### `src/com.matematik/com/matematik/servis/Hesaplayici.java`
```java
package com.matematik.servis;

public class Hesaplayici {
    public static int topla(int a, int b) {
        return a + b;
    }
}
```

---

### 2. Modül: `com.uygulama` (Hizmeti Tüketen Ana Modül)

#### `src/com.uygulama/module-info.java`
```java
module com.uygulama {
    // Matematik modülünü talep ediyoruz:
    requires com.matematik;
}
```

#### `src/com.uygulama/com/uygulama/Main.java`
```java
package com.uygulama;

import com.matematik.servis.Hesaplayici;

public class Main {
    public static void main(String[] args) {
        int sonuc = Hesaplayici.topla(25, 15);
        System.out.println("Modüler Hesaplama Sonucu: " + sonuc);
    }
}
```

---

## 7. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `module-info.java` Dosyasını Yanlış Klasöre Koymak
`module-info.java` dosyası herhangi bir paketin içine değil, **doğrudan modülün kök kaynak dizinine (`src/modul_adi/`)** konulmalıdır.

---

### 2. `exports` Edilmemiş Paketi Import Etmeye Çalışmak
Bir sınıf `public` olsa bile eğer bulunduğu paket `module-info.java` içinde `exports` edilmemişse, dışarıdaki modüllerden çağrıldığında derleme hatası verir:
`Package com.sirket.gizli is declared in module com.sirket, but is not exported`.

---

### 3. Döngüsel Bağımlılık (Cyclic Dependency)
Modül A, Modül B'yi gerektirirken (`requires B`); Modül B de Modül A'yı gerektiremez (`requires A`). Java modülleri arasında döngüsel bağımlılık **kesinlikle yasaktır**.

---

## 8. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Direktif Anlamı
Aşağıdaki `module-info.java` dosyasını analiz ediniz:
```java
module com.banka.guvenlik {
    requires java.sql;
    exports com.banka.guvenlik.api;
}
```
1. Bu modül hangi yerleşik Java modülünü kullanmaktadır?
2. Dış dünyadaki diğer modüller `com.banka.guvenlik.dahili` paketindeki bir sınıfı kullanabilir mi?

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

1. `java.sql` modülünü kullanmaktadır (Veritabanı işlemleri için).
2. **KULLANAMAZLAR!** Çünkü sadece `com.banka.guvenlik.api` paketi dışarıya `exports` edilmiştir. Dahili paket güçlü kapsülleme ile tamamen gizlenmiştir.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki modül dosyasında neden derleme hatası alınır?

```java
module com.proje {
    requires java.base;
    exports com.proje;
}
```

<details>
<summary>🔍 <b>Açıklamayı Görmek İçin Tıklayın</b></summary>

- `exports com.proje;` ifadesi geçersizdir; çünkü `exports` sadece **paket isimlerini** alır (Paketler dosya barındıran alt dizinlerdir).
- `requires java.base;` gereksizdir; çünkü her modül varsayılan olarak zaten `java.base`'i otomatik alır.
</details>

---

## 9. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Modüler Karşılama Uygulaması

**Gereksinimler:**
1. İki modül oluşturunuz:
   - `modul.mesaj`: İçinde `com.mesaj.MesajUretici` sınıfı olsun (`public static String selamVer(String isim)`). Bu paketi `exports` ediniz.
   - `modul.ana`: `requires modul.mesaj` direktifini ekleyip `main` içinde mesajı konsola yazdırınız.

---

### 🟡 Ödev 2 (Orta Seviye): Korumalı Ödeme Altyapısı Modülü

**Gereksinimler:**
1. `finans.odeme` Modülü:
   - `finans.odeme.api` paketi: Dışarıya açılan `OdemeServisi` sınıfı (`odemeYap(double tutar)`).
   - `finans.odeme.gizli` paketi: Dışarıya **açılmayan** `KriptoGuvenlik` sınıfı (kart şifreleme algoritması).
2. Bir test modülü yazarak `OdemeServisi`'ni çağırınız; ancak `KriptoGuvenlik` sınıfını çağırmaya çalıştığınızda modül sisteminin sizi engellediğini gösteriniz.

---

### 🔴 Ödev 3 (İleri Seviye): `jlink` ile Kendi Bağımsız Mini-JRE'nizi Üretme

**Gereksinimler:**
1. Konsola güncel tarihi ve sistem özelliklerini basan modüler bir Java konsol projesi yazınız.
2. Terminalde `jlink` komutunu kullanarak yalnızca projenizin ve `java.base`'in dahil edildiği, hedef bilgisayarda Java kurulu olmasa dahi çalışan mini bir çalışma ortamı (*custom runtime image*) oluşturunuz ve boyutunu standart JDK ile kıyaslayınız.
