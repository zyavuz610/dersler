# 5. Map Arayüzü ve Anahtar-Değer Çiftleri (HashMap, LinkedHashMap, TreeMap)

Bu doküman; Java'da verileri tekil elemanlar yerine **Anahtar - Değer (Key - Value)** çiftleri olarak depolayan **`Map`** arayüzünü, en popüler sözlük yapısı olan **`HashMap`**'i, ekleme sırasını koruyan **`LinkedHashMap`**'i, anahtarları sıralı tutan **`TreeMap`**'i ve Map üzerinde yüksek performanslı gezinme yöntemlerini ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Queue ve Deque Kuyruk Yapıları (4_queue_ve_kuyruklar.md)](./4_queue_ve_kuyruklar.md)
> - 🔜 [Sonraki Bölüm: Iterator, Sıralama ve Koleksiyon Araçları (6_iterator_ve_siralama_araclari.md)](./6_iterator_ve_siralama_araclari.md)

---

## 📌 İçindekiler
1. [`Map` Arayüzü Nedir ve Sözlük Mantığı](#1-map-arayüzü-nedir-ve-sözlük-mantığı)
   - [Anahtar (Key) ve Değer (Value) İlişkisi](#anahtar-key-ve-değer-value-ilişkisi)
   - [Neden `Collection` Arayüzünden Türemez?](#neden-collection-arayüzünden-türemez)
   - [Anahtarlar Benzersizdir (`Set`), Değerler Tekrarlı Olabilir](#anahtarlar-benzersizdir-değerler-tekrarlı-olabilir)
2. [`HashMap`: Hızlı Sözlük Mimarisi](#2-hashmap-hızlı-sözlük-mimarisi)
   - [Çalışma Mantığı ve Zaman Karmaşıklığı ($O(1)$)](#çalışma-mantığı-ve-zaman-karmaşıklığı)
   - [`null` Anahtar ve Değer Kuralları](#null-anahtar-ve-değer-kuralları)
3. [`LinkedHashMap`: Ekleme Sırasını Koruyan Map](#3-linkedhashmap-ekleme-sırasını-koruyan-map)
   - [Erişim Sırası (Access-Order) ve LRU Cache (Önbellek) Mantığı](#erişim-sırası-ve-lru-cache-mantığı)
4. [`TreeMap`: Anahtarları Otomatik Sıralayan Map (Red-Black Tree)](#4-treemap-anahtarları-otomatik-sıralayan-map)
5. [`Map` Üzerinde Gezinme (İterasyon) Yolları](#5-map-üzerinde-gezinme-yolları)
   - [1. `keySet()` ile Sadece Anahtarları Gezme](#1-keyset-ile-sadece-anahtarları-gezme)
   - [2. `values()` ile Sadece Değerleri Gezme](#2-values-ile-sadece-değerleri-gezme)
   - [3. `entrySet()` ile Çiftleri Gezme (En Verimli Yol!)](#3-entryset-ile-çiftleri-gezme-en-verimli-yol)
   - [4. Modern Java 8 `forEach` ile Lambda Gezinimi](#4-modern-java-8-foreach-ile-lambda-gezinimi)
6. [Büyük Karşılaştırma Tablosu: `HashMap` vs. `LinkedHashMap` vs. `TreeMap`](#6-büyük-karşılaştırma-tablosu)
7. [Adım Adım Çalıştırılabilir Kod Örnekleri](#7-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: `HashMap` ile Telefon Rehberi CRUD Sistemi (`TelefonRehberi.java`)](#örnek-1-hashmap-ile-telefon-rehberi)
   - [Örnek 2: Kelime Frekansı Sayacı (Paragraf Kelime Analizi) (`KelimeSayaci.java`)](#örnek-2-kelime-frekansı-sayacı)
   - [Örnek 3: `TreeMap` ile Şehir ve Plaka Kodu Eşleme (`PlakaSistemi.java`)](#örnek-3-treemap-ile-şehir-ve-plaka-kodu-eşleme)
8. [Sık Yapılan Hatalar ve Önemli Kurallar](#8-sık-yapılan-hatalar-ve-önemli-kurallar)
9. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#9-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
10. [Ödev Konuları ve Mini Projeler](#10-ödev-konuları-ve-mini-projeler)

---

## 1. `Map` Arayüzü Nedir ve Sözlük Mantığı?

Gerçek hayattaki bir Türkçe-İngilizce sözlüğü düşünün:
- Sol tarafta kelime yazar (örn. "Araba" $\rightarrow$ **Anahtar / Key**).
- Sağ tarafta kelimenin karşılığı yazar (örn. "Car" $\rightarrow$ **Değer / Value**).

Sözlükte bir kelimeyi ararken sayfa 1'den başlayıp tek tek tüm sayfaları okumazsınız; doğrudan "A" harfine gidip kelimenin karşılığını anında çekersiniz.

```
+-------------------------------------------------------------+
|                     MAP (SÖZLÜK) MİMARİSİ                   |
|                                                             |
|       ANAHTAR (KEY)                     DEĞER (VALUE)       |
|    (Benzersiz / Unique)              (Tekrarlı Olabilir)    |
|   +---------------------+           +--------------------+  |
|   | "TC: 11122233344"   | --------> | Ali Yılmaz (Nesne) |  |
|   | "TC: 55566677788"   | --------> | Ayşe Kaya (Nesne)  |  |
|   | "TC: 99900011122"   | --------> | Ali Yılmaz (Nesne) |  |
|   +---------------------+           +--------------------+  |
+-------------------------------------------------------------+
```

### Neden `Collection` Arayüzünden Türemez?
`List`, `Set` ve `Queue` yapıları tekil birer eleman (`E`) saklar. `Map` ise iki parçalı bir çift (`Key, Value`) saklar. Bu uyumsuzluk sebebiyle `Map`, Java'da bağımsız bir kök arayüzdür.

### Anahtar ve Değer Kuralları
1. **Anahtarlar (Keys) bir `Set` gibidir:** Asla tekrar edemez! Aynı anahtarla yeni bir değer eklerseniz, eski değerin üzerine yazar (*overwrite*).
2. **Değerler (Values) bir `List` gibidir:** Tekrar edebilir. Farklı anahtarlar aynı değere işaret edebilir (örneğin iki farklı TC kimlik numaralı kişinin adı "Ahmet" olabilir).

---

## 2. `HashMap`: Hızlı Sözlük Mimarisi

`HashMap`, anahtarların `hashCode()` değerine göre çalışan en popüler sözlük sınıfıdır.

- **Muazzam Arama Hızı:** Bir anahtarın değerini getirmek (`map.get(anahtar)`) ortalama **$O(1)$** sürede tamamlanır.
- **Sıra Garantisi Yoktur:** Anahtarların hangi sırada tutulduğu belirsizdir.
- **`null` Desteği:** 1 adet `null` anahtar ve sınırsız sayıda `null` değer kabul eder.

### Temel Metotlar
- `put(K key, V value)`: Anahtar-değer çiftini ekler (anahtar zaten varsa eski değeri günceller).
- `get(Object key)`: Anahtarın karşılığı olan değeri döner (yoksa `null` döner).
- `getOrDefault(Object key, V defaultValue)`: Anahtar varsa değerini, yoksa varsayılan değeri döner.
- `containsKey(Object key)`: Bu anahtar sözlükte var mı? (`true`/`false`).
- `containsValue(Object value)`: Bu değer sözlükte var mı?
- `remove(Object key)`: Anahtarı ve değerini sözlükten siler.
- `putIfAbsent(K key, V value)`: Anahtar sözlükte henüz YOKSA ekler; varsa dokunmaz.

---

## 3. `LinkedHashMap`: Ekleme Sırasını Koruyan Map

Eğer sözlüğe girdiğiniz verilerin **eklenme sırasıyla (Insertion Order)** listelenmesini istiyorsanız `LinkedHashMap` kullanılır.

### Erişim Sırası (Access-Order) ve LRU Cache Mantığı
`LinkedHashMap`, özel kurucusuyla `accessOrder = true` olarak açılabilir. Bu modda bir anahtara her `get()` yaptığınızda o eleman listenin en sonuna taşınır. Bu sayede en az kullanılan elemanlar başta kalır ve efsanevi **LRU Cache (Least Recently Used Önbellek)** mimarisi tek satırda inşa edilir!

---

## 4. `TreeMap`: Anahtarları Otomatik Sıralayan Map

`TreeMap`, anahtarları **küçükten büyüğe (alfabetik / sayısal)** otomatik olarak sıralayan Kırmızı-Siyah Ağaç tabanlı bir yapıdır.

- Arama ve ekleme hızı $O(\log n)$'dir.
- Anahtarlar mutlaka kıyaslanabilir (`Comparable`) olmalıdır.
- ❌ **`null` anahtar kabul ETMEZ!** (`NullPointerException`).

---

## 5. `Map` Üzerinde Gezinme (İterasyon) Yolları

Bir `Map` doğrudan `for-each` döngüsüne verilemez (`for (String s : map)` HATA verir!). Çünkü haritanın içinde hem anahtar hem değer vardır. Gezinmek için 4 yol kullanılır:

### 1. `keySet()` ile Sadece Anahtarları Gezme
```java
for (String anahtar : map.keySet()) {
    System.out.println("Anahtar: " + anahtar);
}
```

### 2. `values()` ile Sadece Değerleri Gezme
```java
for (Double deger : map.values()) {
    System.out.println("Değer: " + deger);
}
```

### 3. `entrySet()` ile Çiftleri Gezme (EN VERİMLİ YOL!)
`Map.Entry<K, V>` arayüzü anahtar ve değeri tek bir paket halinde sunar. Tek işlemde ikisine birden ulaşıldığı için **en hızlı ve profesyonel yöntemdir**:

```java
for (Map.Entry<String, Double> cift : map.entrySet()) {
    System.out.println(cift.getKey() + " -> " + cift.getValue());
}
```

### 4. Modern Java 8 `forEach` ile Lambda Gezinimi
```java
map.forEach((anahtar, deger) -> {
    System.out.println(anahtar + " = " + deger);
});
```

---

## 6. Büyük Karşılaştırma Tablosu

| Kriter | `HashMap` | `LinkedHashMap` | `TreeMap` |
| :--- | :---: | :---: | :---: |
| **Anahtar Sıralaması** | **Sırasız (Rastgele)** | **Ekleme Sırası** | **Sıralı (A-Z / 0-9)** |
| **Zaman Karmaşıklığı** | **$O(1)$ (En Hızlı)** | $O(1)$ (Hızlı) | $O(\log n)$ |
| **Arka Plandaki Yapı** | Hash Table | Hash Table + Bağlı Liste | Red-Black Tree (Ağaç) |
| **`null` Anahtar Kabulü?** | ✅ 1 Tane Olabilir | ✅ 1 Tane Olabilir | ❌ **HAYIR!** |
| **`null` Değer Kabulü?** | ✅ Sınırsız | ✅ Sınırsız | ✅ Sınırsız |

---

## 7. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: `HashMap` ile Telefon Rehberi

#### `TelefonRehberi.java`
```java
import java.util.HashMap;
import java.util.Map;

public class TelefonRehberi {
    public static void main(String[] args) {
        // İsim (String) -> Telefon No (String)
        Map<String, String> rehber = new HashMap<>();

        // 1. Ekleme (put)
        rehber.put("Ahmet Kaya", "0555-111-2233");
        rehber.put("Zeynep Demir", "0544-222-3344");
        rehber.put("Burak Çelik", "0532-333-4455");

        // Aynı anahtarla tekrar eklersek eski değer güncellenir:
        rehber.put("Ahmet Kaya", "0555-999-8877");

        System.out.println("Rehber Boyutu: " + rehber.size());

        // 2. Okuma (get)
        String no = rehber.get("Ahmet Kaya");
        System.out.println("Ahmet'in Güncel Numarası: " + no);

        // Olmayan birini ararken güvenli getOrDefault kullanımı:
        String mecvutOlmayan = rehber.getOrDefault("Canan", "Numara Bulunamadı!");
        System.out.println("Canan'ın Numarası: " + mecvutOlmayan);

        // 3. entrySet ile Tüm Rehberi Listeleme:
        System.out.println("\n=== REHBER LİSTESİ ===");
        for (Map.Entry<String, String> kayit : rehber.entrySet()) {
            System.out.println("👤 " + kayit.getKey() + " \t📞 " + kayit.getValue());
        }
    }
}
```

---

### Örnek 2: Kelime Frekansı Sayacı (Klasik Mülakat Sorusu!)

Bir cümledeki her kelimenin kaç defa geçtiğini sayan algoritma:

#### `KelimeSayaci.java`
```java
import java.util.HashMap;
import java.util.Map;

public class KelimeSayaci {
    public static void main(String[] args) {
        String metin = "elma armut muz elma elma armut cilek muz";
        String[] kelimeler = metin.split(" ");

        Map<String, Integer> frekansTablosu = new HashMap<>();

        for (String kelime : kelimeler) {
            // Kelime daha önce varsa sayısını 1 artır, yoksa 0 kabul et + 1 yap:
            int guncelSayi = frekansTablosu.getOrDefault(kelime, 0) + 1;
            frekansTablosu.put(kelime, guncelSayi);
        }

        System.out.println("=== KELİME FREKANS RAPORU ===");
        frekansTablosu.forEach((kelime, adet) -> {
            System.out.println(kelime + " -> " + adet + " adet geçti.");
        });
    }
}
```

#### Konsol Çıktısı:
```text
=== KELİME FREKANS RAPORU ===
elma -> 3 adet geçti.
armut -> 2 adet geçti.
muz -> 2 adet geçti.
cilek -> 1 adet geçti.
```

---

### Örnek 3: `TreeMap` ile Şehir ve Plaka Kodu Eşleme

Plaka kodlarına göre illeri otomatik sıralı tutan sistem:

#### `PlakaSistemi.java`
```java
import java.util.Map;
import java.util.TreeMap;

public class PlakaSistemi {
    public static void main(String[] args) {
        // Plaka No (Integer) -> Şehir Adı (String)
        TreeMap<Integer, String> plakalar = new TreeMap<>();

        plakalar.put(34, "İstanbul");
        plakalar.put(6, "Ankara");
        plakalar.put(35, "İzmir");
        plakalar.put(1, "Adana");
        plakalar.put(16, "Bursa");

        System.out.println("=== PLAKA SIRALI LİSTE (Küçükten Büyüğe) ===");
        for (Map.Entry<Integer, String> entry : plakalar.entrySet()) {
            System.out.printf("[%02d] -> %s\n", entry.getKey(), entry.getValue());
        }

        System.out.println("\nİlk Plaka (firstKey): " + plakalar.firstKey());
        System.out.println("Son Plaka (lastKey) : " + plakalar.lastKey());
    }
}
```

---

## 8. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `keySet()` ile Döngü Kurup İçeride `map.get(k)` Çağırmak (Performans Tuzağı!)
```java
// ⚠️ KÖTÜ KOD (Çift Arama Maliyeti):
for (String key : map.keySet()) {
    Double val = map.get(key); // Her seferinde Map içinde baştan arama yapar!
}
```
✅ **En İyi Pratik:** Tek seferde anahtar ve değere ulaşmak için **`map.entrySet()`** kullanınız!

---

### 2. `null` Değer Dönerken Unboxing Tuzağı
```java
Map<String, Integer> notlar = new HashMap<>();
// "Ali" listede yok, bu yüzden get() null döner!
int not = notlar.get("Ali"); // 💣 RUNTIME CRASH: NullPointerException!
```
`null` olan `Integer` nesnesi ilkel `int`'e dönüştürülmeye çalışırken çöker.
✅ **Çözüm:** `notlar.getOrDefault("Ali", 0)` kullanınız.

---

## 9. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Çıktı ve Değer Analizi
Aşağıdaki kod çalıştığında `rehber.get("Ali")` ne döner?

```java
Map<String, String> rehber = new HashMap<>();
rehber.put("Ali", "111");
rehber.put("Veli", "222");
rehber.put("Ali", "333");

System.out.println(rehber.get("Ali"));
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Cevap:** `"333"`
**Açıklama:** Map anahtarları benzersizdir. Aynı anahtar tekrar eklendiğinde eski değer silinir ve yeni değer ("333") atanır.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kod parçasında neden derleme veya çalışma anı hatası alınmaktadır?

```java
TreeMap<String, Double> fiyatlar = new TreeMap<>();
fiyatlar.put("Kitap", 120.0);
fiyatlar.put(null, 50.0);
```

<details>
<summary>🔍 <b>Hata ve Çözümü Görmek İçin Tıklayın</b></summary>

**Hata:** `TreeMap` anahtarları sıralamak için `compareTo()` çağırır. `null` anahtar kıyaslanamayacağı için `fiyatlar.put(null, 50.0)` satırında **`NullPointerException`** fırlatılır. `TreeMap` anahtar olarak `null` kabul etmez.
</details>

---

### Alıştırma 3: Harf Sayacı
Kullanıcının girdiği bir metindeki her karakterin kaç defa geçtiğini sayıp bir `Map<Character, Integer>` içine kaydeden metodu yazınız.

---

## 10. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Personel Departman Rehberi (`SirketRehberi.java`)

**Gereksinimler:**
1. `Map<Integer, String> sirket = new HashMap<>();` (Personel Sicil No $\rightarrow$ Ad Soyad).
2. Metotlar:
   - `personelEkle(int sicilNo, String ad)`: Sicil no zaten varsa uyarı verip eklemesin (`putIfAbsent`).
   - `personelBul(int sicilNo)`: İsmi getirsin.
   - `personelSil(int sicilNo)`
   - `tumunuListele()`: `entrySet` kullanarak tüm personeli listeleyin.

---

### 🟡 Ödev 2 (Orta Seviye): Çoklu Değer Tutan Öğrenci Not Defteri (`MultiMapTest.java`)

**Gereksinimler:**
1. Bir öğrencinin birden fazla notu olabilir. Bunu sağlamak için Değer kısmını bir liste yapınız:
   `Map<String, List<Integer>> notDefteri = new HashMap<>();` (Öğrenci Adı $\rightarrow$ Notlar Listesi).
2. Fonksiyonlar:
   - `notEkle(String ogrenci, int not)`: Öğrenci henüz yoksa yeni bir `ArrayList` oluşturup notu eklesin; varsa mevcut listeye notu ilave etsin.
   - `ortalamaHesapla(String ogrenci)`: Öğrencinin notlarının ortalamasını dönsün.
   - `sinifKarnesi()`: Her öğrencinin adını, aldığı tüm notları ve genel ortalamasını tablo halinde yazdırsın.

---

### 🔴 Ödev 3 (İleri Seviye): Ürün Stok ve Fiyat Takip Motoru (`ECommerceStok.java`)

**Gereksinimler:**
1. `UrunBilgisi` sınıfı (`double fiyat`, `int stokAdedi`).
2. `TreeMap<String, UrunBilgisi>` kullanarak ürün kodlarına göre alfabetik sıralı bir envanter oluşturunuz.
3. Menü Akışı:
   - Yeni ürün tanımlama veya stok güncelleme.
   - Fiyat güncelleme.
   - Stokta 5 adetten az kalan kritik ürünleri filtreleyip uyarı veren rapor metodu.
   - Toplam envanter değerini ($\sum \text{fiyat} \times \text{stok}$) hesaplayan fonksiyon.

