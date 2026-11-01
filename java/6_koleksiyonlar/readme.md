# 6. Modül: Java Koleksiyonları (Java Collections Framework)

Bu dizin, Java'da dinamik veri yapılarının, listelerin, kümelerin, kuyrukların ve anahtar-değer haritalarının yer aldığı **Java Koleksiyon Çerçevesi (Collections Framework - `java.util.*`)** eğitim modülüdür.

Her doküman; konu anlatımları, bellek mimarileri, zaman karmaşıklıkları ($O(1), O(n), O(\log n)$), çalıştırılabilir temiz kod örnekleri, yaygın tuzaklar ve çözümlü alıştırmalar/ödevler ile zenginleştirilmiştir.

---

## 🧭 Ders Dokümanları İndeksi ve Öğrenim Sırası

```mermaid
flowchart TD
    M1["1. Giriş & Jenerikler (Generics)<br>(1_generics_ve_koleksiyonlar_giris.md)"] --> M2["2. List Arayüzü (ArrayList & LinkedList)<br>(2_list_arayuzu_ve_siniflari.md)"]
    M2 --> M3["3. Set Arayüzü (HashSet & TreeSet)<br>(3_set_ve_kumeler.md)"]
    M3 --> M4["4. Queue & Deque (FIFO & PriorityQueue)<br>(4_queue_ve_kuyruklar.md)"]
    M4 --> M5["5. Map Arayüzü (HashMap & TreeMap)<br>(5_map_ve_anahtar_deger.md)"]
    M5 --> M6["6. Iterator, Sıralama & Collections Sınıfı<br>(6_iterator_ve_siralama_araclari.md)"]

    style M1 fill:#dfd,stroke:#333,stroke-width:1px
    style M2 fill:#dfd,stroke:#333,stroke-width:1px
    style M3 fill:#dfd,stroke:#333,stroke-width:1px
    style M4 fill:#dfd,stroke:#333,stroke-width:1px
    style M5 fill:#dfd,stroke:#333,stroke-width:1px
    style M6 fill:#ffe,stroke:#e60,stroke-width:2px
```

---

## 📋 Modül Konu Başlıkları Tablosu

| No | Doküman | Konu Başlığı | Temel Kazanımlar ve Kapsam |
| :---: | :--- | :--- | :--- |
| **01** | [**1_generics_ve_koleksiyonlar_giris.md**](./1_generics_ve_koleksiyonlar_giris.md) | **Koleksiyonlara Giriş & Jenerikler** | • Dizilerin sınırları ve dinamik koleksiyon ihtiyacı<br>• İlkel tipler ve sarmalayıcı sınıflar (*Autoboxing / Unboxing*)<br>• Jenerikler (`<T>`) ile tip güvenliği (*Type Safety*) ve elmas operatörü `<>`<br>• Kendi jenerik sınıf/metotlarımızı yazma ve `Collection` / `Iterable` kök arayüzleri |
| **02** | [**2_list_arayuzu_ve_siniflari.md**](./2_list_arayuzu_ve_siniflari.md) | **List Arayüzü ve Uygulamaları** | • `List` arayüzünün doğası: sıralılık, indeks tabanlılık ve tekrara izin verme<br>• `ArrayList` mimarisi: dinamik dizi büyüme formülü ($1.5\times$) ve $O(1)$ okuma hızı<br>• `LinkedList` mimarisi: çift yönlü bağlı liste (düğümler) ve başa/araya hızlı ekleme<br>• `ArrayList` vs. `LinkedList` büyük karşılaştırması ve eski `Vector` / `Stack` (LIFO) yapıları |
| **03** | [**3_set_ve_kumeler.md**](./3_set_ve_kumeler.md) | **Set Arayüzü ve Kümeler** | • `Set` arayüzü ve matematiksel küme mantığı (tekrarsızlık / uniqueness)<br>• `HashSet` ve arka plandaki Hashing / Kova (*Bucket*) mimarisi ($O(1)$)<br>• **Hayati Kural:** `equals()` ve `hashCode()` sözleşmesi ve ezilmesinin önemi<br>• Ekleme sırasını koruyan `LinkedHashSet` ve ağaç tabanlı sıralı `TreeSet` ($O(\log n)$)<br>• Matematiksel küme işlemleri: Birleşim (`addAll`), Kesişim (`retainAll`), Fark (`removeAll`) |
| **04** | [**4_queue_ve_kuyruklar.md**](./4_queue_ve_kuyruklar.md) | **Queue ve Deque Kuyruk Yapıları** | • `Queue` arayüzü ve İlk Giren İlk Çıkar (*FIFO*) prensibi<br>• Hata fırlatan (`add`, `remove`, `element`) vs. Güvenli değer dönen (`offer`, `poll`, `peek`) metotlar<br>• `PriorityQueue` ve Min-Heap mantığı (geliş sırası değil aciliyet sırası, Triyaj analojisi)<br>• Çift uçlu kuyruklar (`Deque`) ve modern Java'da Stack yerine `ArrayDeque` tercihi |
| **05** | [**5_map_ve_anahtar_deger.md**](./5_map_ve_anahtar_deger.md) | **Map Arayüzü (Anahtar - Değer)** | • `Map` arayüzü ve sözlük mimarisi (benzersiz anahtar $\rightarrow$ değer eşleşmesi)<br>• Neden `Collection` arayüzünden türemez? (`K, V` çifti doğası)<br>• En hızlı sözlük `HashMap` ($O(1)$) ve sıra garantisizliği<br>• Ekleme/erişim sıralı `LinkedHashMap` (LRU Cache mantığı) ve anahtarları sıralı `TreeMap`<br>• Map üzerinde gezinme yolları: `keySet()`, `values()`, `entrySet()` ve Java 8 `forEach` |
| **06** | [**6_iterator_ve_siralama_araclari.md**](./6_iterator_ve_siralama_araclari.md) | **Iterator, Sıralama & Araçlar** | • `Iterator` ile gezinme ve `ConcurrentModificationException` tuzağından korunma (`it.remove()`)<br>• Nesneleri sıralama: `Comparable` (doğal sıralama) vs. `Comparator` (esnek/çoklu sıralama)<br>• `java.util.Collections` yardımcı sınıfı: `sort()`, `reverse()`, `shuffle()`, `min()`, `max()`, `frequency()`<br>• Java 9+ değiştirilemez koleksiyon fabrikaları: `List.of()`, `Set.of()`, `Map.of()` kuralları |

