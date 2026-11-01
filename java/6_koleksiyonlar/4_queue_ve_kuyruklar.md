# 4. Queue ve Deque Kuyruk Yapıları (FIFO, PriorityQueue, ArrayDeque)

Bu doküman; Java Koleksiyon Çerçevesi'nde **İlk Giren İlk Çıkar (FIFO - First-In-First-Out)** kuralıyla çalışan **`Queue` (Kuyruk)** arayüzünü, aciliyet sırasına göre çalışan **`PriorityQueue` (Öncelikli Kuyruk)** sınıfını ve her iki uçtan da işlem yapabilen yüksek performanslı **`Deque` / `ArrayDeque` (Çift Uçlu Kuyruk)** yapılarını ele almaktadır.

---

> 📖 **İlgili Bölüm Dokümanları:**
> - 🔙 [Önceki Bölüm: Set Arayüzü ve Kümeler (3_set_ve_kumeler.md)](./3_set_ve_kumeler.md)
> - 🔜 [Sonraki Bölüm: Map Arayüzü ve Anahtar-Değer Çiftleri (5_map_ve_anahtar_deger.md)](./5_map_ve_anahtar_deger.md)

---

## 📌 İçindekiler
1. [`Queue` Arayüzü Nedir ve FIFO Mantığı](#1-queue-arayüzü-nedir-ve-fifo-mantığı)
   - [İlk Giren İlk Çıkar (FIFO) Prensibi](#ilk-giren-ilk-çıkar-fifo-prensibi)
   - [Gerçek Dünya Analojisi: Ekmek Kuyruğu ve Yazıcı Baskı Sırası](#gerçek-dünya-analojisi-ekmek-kuyruğu-ve-yazıcı-baskı-sırası)
2. [`Queue` Arayüzünün Kritik Metot Tablosu](#2-queue-arayüzünün-kritik-metot-tablosu)
   - [Hata Fırlatanlar (`add`, `remove`, `element`) vs. Özel Değer Dönenler (`offer`, `poll`, `peek`)](#hata-fırlatanlar-vs-özel-değer-dönenler)
3. [`PriorityQueue`: Öncelikli Kuyruk Mimarisi](#3-priorityqueue-öncelikli-kuyruk-mimarisi)
   - [Geliş Sırası Değil, Aciliyet Sırası!](#geliş-sırası-değil-aciliyet-sırası)
   - [Hastane Acil Servisi (Triyaj) Analojisi](#hastane-acil-servisi-triyaj-analojisi)
   - [Min-Heap Mantığı ve `Comparable` Zorunluluğu](#min-heap-mantığı-ve-comparable-zorunluluğu)
4. [`Deque` ve `ArrayDeque`: Çift Uçlu Kuyruklar](#4-deque-ve-arraydeque-çift-uçlu-kuyruklar)
   - [Hem Baştan Hem Sondan Ekleme/Çıkarma](#hem-baştan-hem-sondan-eklemeçıkarma)
   - [Neden Modern Java'da `Stack` Yerine `ArrayDeque` Tercih Edilir?](#neden-modern-javada-stack-yerine-arraydeque-tercih-edilir)
5. [Karşılaştırma Tablosu: Hangi Kuyruk Ne Zaman Seçilmeli?](#5-karşılaştırma-tablosu-hangi-kuyruk-ne-zaman-seçilmeli)
6. [Adım Adım Çalıştırılabilir Kod Örnekleri](#6-adım-adım-çalıştırılabilir-kod-örnekleri)
   - [Örnek 1: Standart FIFO Kuyruk (`BankaSirasi.java`)](#örnek-1-standart-fifo-kuyruk)
   - [Örnek 2: Hastane Acil Servis Triyaj Sistemi (`AcilServisTest.java`)](#örnek-2-hastane-acil-servis-triyaj-sistemi)
   - [Örnek 3: `ArrayDeque` ile Palindrom Kontrolü (`PalindromTest.java`)](#örnek-3-arraydeque-ile-palindrom-kontrolü)
7. [Sık Yapılan Hatalar ve Önemli Kurallar](#7-sık-yapılan-hatalar-ve-önemli-kurallar)
8. [Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar](#8-öğrenciler-için-pekiştirici-sınıf-içi-alıştırmalar)
9. [Ödev Konuları ve Mini Projeler](#9-ödev-konuları-ve-mini-projeler)

---

## 1. `Queue` Arayüzü Nedir ve FIFO Mantığı?

Günlük hayatta sıraya girdiğimiz her durum bir kuyruktur: market kasası, sinema bilet gişesi, banka sırası matigi.

Java'da **`Queue`** arayüzü; elemanların bir uçtan (kuyruğun sonundan) eklendiği ve diğer uçtan (kuyruğun başından) çekildiği **FIFO (First-In-First-Out / İlk Giren İlk Çıkar)** veri yapısını temsil eder.

```
                  KUYRUK (QUEUE) AKIŞI
               <--- ÇIKIŞ (Head / Başı) <---
+-----------+    +-----------+    +-----------+
| 1. Müşteri| -> | 2. Müşteri| -> | 3. Müşteri| <--- GİRİŞ (Tail / Sonu) <---
+-----------+    +-----------+    +-----------+
(İlk giren ilk çıkar)
```

---

## 2. `Queue` Arayüzünün Kritik Metot Tablosu

Java'da kuyruk metotları iki gruba ayrılır. Bu ayrım mülakatlarda ve sınavlarda en çok sorulan yerdir:
1. **İşlem başarısız olduğunda HATA fırlatanlar** (Exception).
2. **İşlem başarısız olduğunda ÖZEL BİR DEĞER (`false` veya `null`) dönenler** (Güvenli).

| İşlem Türü | Hata Fırlatan Metot (Throws Exception) | Özel Değer Dönen Metot (Returns false/null) |
| :--- | :--- | :--- |
| **Kuyruğa Ekleme (Insert)** | `add(e)` *(Kapasite doluysa `IllegalStateException` fırlatır)* | **`offer(e)`** *(Kapasite doluysa `false` döner)* |
| **Kuyruktan Çıkarma (Remove)**| `remove()` *(Kuyruk boşsa `NoSuchElementException` fırlatır)* | **`poll()`** *(Kuyruk boşsa `null` döner)* |
| **Baştaki Elemana Bakma (Examine)**| `element()` *(Kuyruk boşsa `NoSuchElementException` fırlatır)* | **`peek()`** *(Kuyruk boşsa `null` döner)* |

> 💡 **En İyi Pratik:** Uygulamanızın beklenmedik anlarda çökmesini istemiyorsanız her zaman güvenli üçlüyü tercih ediniz: **`offer()`**, **`poll()`**, **`peek()`**.

---

## 3. `PriorityQueue`: Öncelikli Kuyruk Mimarisi

Standart kuyrukta ilk gelen ilk çıkar; ancak hayat her zaman sıraya göre işlemez!

### Hastane Acil Servisi (Triyaj) Analojisi
Hastanenin acil servisine gittiğinizi düşünün:
- Sıraya ilk giren hasta başı ağrıyan biridir.
- 5 dakika sonra sedyede kalp krizi geçiren ağır bir hasta getirilir.
- Sıra kimindir? Tabii ki acil hastanındır! Acil hasta sıranın önüne geçer.

İşte **`PriorityQueue`**, elemanları geliş sırasına göre değil, sahip oldukları **öncelik değerine (priority)** göre sıralayan özel bir kuyruktur.

```
       GİRİŞ                      PRIORITY QUEUE                      ÇIKIŞ
[Hafif Yaralı (Öncelik: 3)]                                   [Kalp Krizi (Öncelik: 1)]
[Kırık Kol     (Öncelik: 2)]  ---> [ Min-Heap Ağacı ]  --->   (En acil olan daima
[Kalp Krizi    (Öncelik: 1)]                                   en başta durur!)
```

### Min-Heap Mantığı ve `Comparable` Kuralı
- Varsayılan olarak Java'nın `PriorityQueue` sınıfı bir **Min-Heap (Küçükten Büyüğe)** yapısı kullanır. En küçük sayı veya alfabetik olarak en öndeki harf daima kuyruğun tepesindedir (`peek()` daima onu verir).
- `PriorityQueue` içine kendi nesnelerinizi koyacaksanız, nesneniz mutlaka **`Comparable`** arayüzünü uygulamalıdır; aksi halde program çalışma anında çöker.

---

## 4. `Deque` ve `ArrayDeque`: Çift Uçlu Kuyruklar

**`Deque` (Double-Ended Queue / Telaffuz: "Dek")**, hem baştan hem sondan eleman eklenip çıkarılabilen çift uçlu bir yapıdır.

```
<--- [Çıkış / Giriş] <--- [ D E Q U E ] ---> [Giriş / Çıkış] --->
```

### Neden Modern Java'da `Stack` Yerine `ArrayDeque` Tercih Edilir?
Eski `Stack` sınıfı `Vector`'den türer ve senkronizasyon kilitleri yüzünden hantaldır.
Java'nın resmi dökümantasyonu açıkça şunu söyler:
> *"LIFO (Yığın) yapısına ihtiyacınız olduğunda `Stack` yerine `ArrayDeque` kullanınız."*

- `ArrayDeque` içinde `push()` ve `pop()` metotları da bulunur; bu sayede hem ultra hızlı bir **Yığın (Stack)** hem de ultra hızlı bir **Kuyruk (Queue)** olarak çalışabilir.

---

## 5. Karşılaştırma Tablosu

| Sınıf | Sıralama Kriteri | Tekrarlı Eleman? | `null` Kabul Eder mi? | Kullanım Amacı |
| :--- | :--- | :---: | :---: | :--- |
| **`LinkedList` (Queue)** | Geliş Sırası (FIFO) | ✅ Evet | ✅ Evet | Standart kuyruklar, bekleme listeleri |
| **`PriorityQueue`** | Öncelik / Doğal Sıralama | ✅ Evet | ❌ **HAYIR!** | Görev zamanlayıcılar, aciliyet sistemleri |
| **`ArrayDeque`** | FIFO veya LIFO | ✅ Evet | ❌ **HAYIR!** | Yüksek performanslı yığın (Stack) ve çift uçlu kuyruk |

---

## 6. Adım Adım Çalıştırılabilir Kod Örnekleri

---

### Örnek 1: Standart FIFO Kuyruk

#### `BankaSirasi.java`
```java
import java.util.LinkedList;
import java.util.Queue;

public class BankaSirasi {
    public static void main(String[] args) {
        // En iyi pratik: Sol taraf Queue, sağ taraf LinkedList
        Queue<String> giseKuyrugu = new LinkedList<>();

        // 1. Müşteriler sıraya girer (offer):
        giseKuyrugu.offer("101 - Ahmet");
        giseKuyrugu.offer("102 - Zeynep");
        giseKuyrugu.offer("103 - Mehmet");

        System.out.println("Sıradaki Müşteriler: " + giseKuyrugu);

        // 2. Sıradaki ilk müşteriye bakalım (peek - silmez):
        System.out.println("Gişeye çağrılacak ilk kişi: " + giseKuyrugu.peek());

        // 3. Müşterilere sırayla hizmet verip kuyruktan çıkaralım (poll):
        System.out.println("\n--- İŞLEMLER BAŞLADI ---");
        while (!giseKuyrugu.isEmpty()) {
            String hizmetVerilen = giseKuyrugu.poll(); // Baştakini al ve sil
            System.out.println("Hizmet veriliyor -> " + hizmetVerilen);
            System.out.println("Kalan kuyruk boyutu: " + giseKuyrugu.size());
        }

        // Kuyruk boşken poll çağrılırsa hata vermez, null döner:
        System.out.println("\nKuyruk boşken poll(): " + giseKuyrugu.poll());
    }
}
```

---

### Örnek 2: Hastane Acil Servis Triyaj Sistemi

Öncelik seviyesine göre (1: Çok Acil, 2: Orta, 3: Hafif) hastaları sıralayan sistem:

#### `Hasta.java` & `AcilServisTest.java`
```java
import java.util.PriorityQueue;
import java.util.Queue;

// Comparable arayüzü ile öncelik kıyaslamasını sağlıyoruz
class Hasta implements Comparable<Hasta> {
    private String ad;
    private int aciliyetSeviyesi; // 1: Kırmızı Kod, 2: Sarı Kod, 3: Yeşil Kod

    public Hasta(String ad, int aciliyetSeviyesi) {
        this.ad = ad;
        this.aciliyetSeviyesi = aciliyetSeviyesi;
    }

    // Küçük sayı = Yüksek öncelik (Min-Heap)
    @Override
    public int compareTo(Hasta diger) {
        return Integer.compare(this.aciliyetSeviyesi, diger.aciliyetSeviyesi);
    }

    @Override
    public String toString() {
        return "[" + ad + " | Öncelik Derecesi: " + aciliyetSeviyesi + "]";
    }
}

public class AcilServisTest {
    public static void main(String[] args) {
        Queue<Hasta> acilKuyrugu = new PriorityQueue<>();

        // Hastalar farklı sıralarda geliyor:
        acilKuyrugu.offer(new Hasta("Ali (Baş Ağrısı)", 3));
        acilKuyrugu.offer(new Hasta("Kemal (Kalp Spazmı)", 1)); // En son geldi ama en acil!
        acilKuyrugu.offer(new Hasta("Selin (Kırık Parmak)", 2));

        System.out.println("=== DOKTOR MUAYENE SIRASI ===");
        // poll() her çağrıldığında en acil olan hasta ilk çıkar!
        while (!acilKuyrugu.isEmpty()) {
            System.out.println("Ameliyathaneye alındı: " + acilKuyrugu.poll());
        }
    }
}
```

#### Konsol Çıktısı:
```text
=== DOKTOR MUAYENE SIRASI ===
Ameliyathaneye alındı: [Kemal (Kalp Spazmı) | Öncelik Derecesi: 1]
Ameliyathaneye alındı: [Selin (Kırık Parmak) | Öncelik Derecesi: 2]
Ameliyathaneye alındı: [Ali (Baş Ağrısı) | Öncelik Derecesi: 3]
```

---

### Örnek 3: `ArrayDeque` ile Palindrom Kontrolü

`ArrayDeque`'in iki ucundan aynı anda karakter çekerek bir kelimenin tersten okunuşunu test etme:

#### `PalindromTest.java`
```java
import java.util.ArrayDeque;
import java.util.Deque;

public class PalindromTest {
    public static boolean palindromMu(String metin) {
        Deque<Character> deque = new ArrayDeque<>();

        // Harfleri kuyruğa ekle:
        for (char c : metin.toLowerCase().toCharArray()) {
            deque.addLast(c);
        }

        // İki uçtan aynı anda çekip karşılaştır:
        while (deque.size() > 1) {
            char bastaki = deque.removeFirst();
            char sondaki = deque.removeLast();

            if (bastaki != sondaki) {
                return false; // Eşleşmedi, palindrom değil
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("kayak  -> " + palindromMu("kayak"));  // true
        System.out.println("kabak  -> " + palindromMu("kabak"));  // true
        System.out.println("yazılım-> " + palindromMu("yazılım"));// false
    }
}
```

---

## 7. Sık Yapılan Hatalar ve Önemli Kurallar

### 1. `PriorityQueue`'yu `for-each` ile Yazdırırken Sıralı Çıkacağını Sanmak
`PriorityQueue` bir ağaçtır (Heap). Yalnızca en tepedeki elemanın (`peek` veya `poll`) mutlak en küçük olduğu garanti edilir.
Eğer `for (Hasta h : acilKuyrugu)` yazdırırsanız ağacın iç dizilişini görürsünüz ve sıralı **çıkmayabilir**.
✅ **Doğru Yöntem:** Sıralı tüketmek için daima `while(!kuyruk.isEmpty()) { kuyruk.poll(); }` döngüsü kullanılmalıdır!

---

### 2. `ArrayDeque` veya `PriorityQueue` İçine `null` Eklemeye Çalışmak
Bu sınıflar `poll()` çağrıldığında kuyruğun boş olduğunu anlatmak için `null` döndürürler. Eğer içine `null` eklemeye izin verselerdi, dönen `null`'ın kuyruğun boş olduğunu mu yoksa içerideki verinin mi `null` olduğunu bilemezdik. Bu yüzden `add(null)` **`NullPointerException`** fırlatır.

---

## 8. Öğrenciler İçin Pekiştirici Sınıf İçi Alıştırmalar

### Alıştırma 1: Çıktı Analizi
Aşağıdaki kod çalıştığında konsola ne yazdırılır?

```java
Queue<String> q = new LinkedList<>();
q.offer("A");
q.offer("B");
q.offer("C");
q.poll();
q.offer("D");

System.out.println("Peek: " + q.peek() + " | Boyut: " + q.size());
```

<details>
<summary>🔍 <b>Çözümü Görmek İçin Tıklayın</b></summary>

**Konsol Çıktısı:**
`Peek: B | Boyut: 3`
**Açıklama:**
1. `A, B, C` sıraya girer.
2. `poll()` ile en baştaki `A` kuyruktan çıkar. Kalanlar: `B, C`.
3. `D` sıranın sonuna eklenir: `B, C, D`.
4. `peek()` en baştaki `B`'yi okur (silmez). Boyut ise 3'tür.
</details>

---

### Alıştırma 2: Hata Avı (Bug Hunting)
Aşağıdaki kodda derleme veya çalışma hatası veren satırı tespit ediniz:

```java
Queue<Integer> pq = new PriorityQueue<>();
pq.offer(50);
pq.offer(null);
pq.offer(20);
```

<details>
<summary>🔍 <b>Hata ve Çözümü Görmek İçin Tıklayın</b></summary>

**Hata:** `pq.offer(null)` satırında çalışma zamanında **`NullPointerException`** fırlatılır. `PriorityQueue` asla `null` değer kabul etmez.
</details>

---

### Alıştırma 3: Küçük Tasarım (Yazıcı Baskı Kuyruğu)
Bir `Belge(String ad, int sayfaSayisi)` sınıfı oluşturup standart FIFO kuyruğuyla sırayla belgeleri basan bir `Yazici` sınıfı kodlayınız.

---

## 9. Ödev Konuları ve Mini Projeler

---

### 🟢 Ödev 1 (Temel Seviye): Süpermarket Kasa Simülasyonu (`KasaKuyruguTest.java`)

**Gereksinimler:**
1. `Musteri` sınıfı (`String ad`, `int sepetUrunSayisi`).
2. `Queue<Musteri> kasa = new LinkedList<>();` tanımlayınız.
3. 5 müşteri sıraya girsin.
4. Kasiyer her müşteriyi sırayla çağırsın (`poll`):
   - Ekrana `"Müşteri: [Ad] işlem görüyor... Ürün Sayısı: X | Süre: X saniye"` yazdırsın.
   - Her ürünün kasadan geçiş süresi 2 saniye kabul edilsin.
   - Kasa tamamen boşalana kadar kuyruğu eriten simülasyonu çalıştırınız.

---

### 🟡 Ödev 2 (Orta Seviye): Çağrı Merkezi VIP Öncelik Kuyruğu (`CagriMerkezi.java`)

**Gereksinimler:**
1. `ArayanKisi` sınıfı: `ad`, `musteriTipi` ("VIP", "TICARI", "BIREYSEL").
2. `PriorityQueue` kullanarak:
   - "VIP" müşteriler en yüksek önceliğe (1),
   - "TICARI" müşteriler orta önceliğe (2),
   - "BIREYSEL" müşteriler standart önceliğe (3) sahip olsun.
3. Müşteriler karışık sırayla çağrı merkezine bağlansın.
4. Müşteri temsilcisi her seferinde sıradaki en öncelikli müşteriyi yanıtlasın ve çağrı tamamlansın.

---

### 🔴 Ödev 3 (İleri Seviye): İşletim Sistemi CPU İş Parçacığı Zamanlayıcı (`CpuScheduler.java`)

**Gereksinimler:**
1. `IsParcacigi (Task)` sınıfı: `String gorevAdi`, `int islemSuresiMs`, `int oncelikSeviyesi` (1 en acil, 10 en düşük).
2. Bir `PriorityQueue<Task>` havuzu oluşturunuz.
3. Aynı önceliğe sahip görevler arasında adaleti sağlamak için geliş zamanını da hesaba katan iki kriterli bir `Comparator` yazınız.
4. CPU'nun görevleri sırayla işleyip tamamlandıkça konsola döktüğü bir simülasyon motoru yazınız.

