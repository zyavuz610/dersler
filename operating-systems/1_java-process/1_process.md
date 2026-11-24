# Bilgisayar Mühendisliği — İşletim Sistemleri

## Süreç (Process) Kavramı, CPU Yönetimi ve Yaşam Döngüsü

Bu ders notunda, teoride kalan işletim sistemi kavramlarını pratik shell komutları ve Java uygulamaları üzerinden inceleyeceğiz. Amacımız komutları ezberlemek değil; bir program çalıştırıldığında işletim sisteminin arka planda hangi mekanizmaları devreye soktuğunu kavramaktır.

---

## 1. Temel Kavramlar

### Program ve Süreç (Process) Farkı
- **Program (Pasif):** Sabit diskte (veya ikincil depolamada) ikili (binary) veya baytkod (`.class`) formatında bekleyen pasif bir kod ve veri bütünüdür.
- **Süreç / Process (Aktif):** İşletim sistemi tarafından belleğe (RAM) yüklenmiş, kaynak tahsis edilmiş (CPU zamanı, bellek alanı, dosya tanıtıcıları) ve yürütülmekte olan program örneğidir.

Tek bir program dosyasından (`Asal.class`) bellekte bağımsız olarak çalışan onlarca farklı süreç türetilebilir.

### İşletim Sistemi Süreci Nasıl Takip Eder?
İşletim sistemi her yeni süreç oluşturulduğunda çekirdekte (kernel) bir **Process Control Block (PCB)** oluşturur. PCB içinde:
- **PID (Process ID):** Sürecin benzersiz kimlik numarası.
- **PPID (Parent Process ID):** Süreci başlatan üst sürecin kimlik numarası.
- **Program Sayacı (PC - Program Counter):** Bir sonraki yürütülecek makine buyruğunun adresi.
- **CPU Yazmaçları (Registers) ve Durum:** Süreç durdurulup tekrar başlatılırken saklanan bağlam (context) bilgileri.
- **Bellek Yönetim Bilgisi:** Sürece tahsis edilen sanal adres uzayı (Text, Data, Heap, Stack).

---

## 2. Uygulama: Java ile Süreç İzleme ve Yaşam Döngüsü (Üst Sınır: 5 Asal Sayı)

Aşağıdaki Java programı, komut satırından aldığı kimlik parametresiyle asal sayıları hesaplar. **Üst sınır olarak tam 5 adet asal sayı bulduğunda** sürecini normal yoldan (`exit code 0`) sonlandırır.

### Java Kodu: `Asal.java`

```java
public class Asal {

    // Asal sayı kontrol algoritması: O(sqrt(n))
    public static boolean asalMi(long n) {
        if (n < 2) {
            return false;
        }
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Kullanım: java Asal <ornek_id>");
            System.exit(1);
        }

        String ornekId = args[0];

        // Java 9 ve sonrası: Mevcut sürecin PID değerini işletim sisteminden okur
        long pid = ProcessHandle.current().pid();

        long aday = 2;
        int bulunanAsalSayisi = 0;
        final int HEDEF_ASAL_LIMITI = 5; // Üst sınır: 5 asal sayı

        System.out.printf("[BAŞLATILDI] Örnek No: %s | PID: %d (Hedef: %d asal sayı)%n",
                ornekId, pid, HEDEF_ASAL_LIMITI);

        // Belirlenen limite ulaşana kadar döngü devam eder
        while (bulunanAsalSayisi < HEDEF_ASAL_LIMITI) {
            if (asalMi(aday)) {
                bulunanAsalSayisi++;
                System.out.printf(
                    "Süreç: %s | PID: %d -> %d. Asal Sayı Bulundu: %d%n",
                    ornekId, pid, bulunanAsalSayisi, aday
                );

                // Süreçlerin eşzamanlı çalışmasını ve çıktı yarışını gözlemleyebilmek için kısa bekleme
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            aday++;
        }

        System.out.printf("[BİTTİ] Örnek No: %s | PID: %d görevini tamamladı ve sonlanıyor.%n", ornekId, pid);
    }
}
```

---

## 3. Laboratuvar Adımları (Shell / Replit / Linux)

### Adım 1: Ortam Kontrolü ve Derleme

```bash
javac -version
java -version
```

Programı derleyin:
```bash
javac Asal.java
```
Bu komut disk üzerinde pasif bir ikili dosya olan `Asal.class` dosyasını oluşturur.

### Adım 2: İki Bağımsız Süreci Arka Planda Başlatma

Shell'de komutun sonuna konulan `&` operatörü, sürecin arka planda (background) çalışmasını sağlar:

```bash
java Asal 1 &
java Asal 2 &
```

*Beklenen Terminal Çıktısı (PID'ler dinamiktir, çıktılar yarış durumuna göre karışık gelebilir):*
```text
[1] 1420
[2] 1421
[BAŞLATILDI] Örnek No: 1 | PID: 1420 (Hedef: 5 asal sayı)
[BAŞLATILDI] Örnek No: 2 | PID: 1421 (Hedef: 5 asal sayı)
Süreç: 1 | PID: 1420 -> 1. Asal Sayı Bulundu: 2
Süreç: 2 | PID: 1421 -> 1. Asal Sayı Bulundu: 2
Süreç: 1 | PID: 1420 -> 2. Asal Sayı Bulundu: 3
Süreç: 2 | PID: 1421 -> 2. Asal Sayı Bulundu: 3
Süreç: 2 | PID: 1421 -> 3. Asal Sayı Bulundu: 5
Süreç: 1 | PID: 1420 -> 3. Asal Sayı Bulundu: 5
...
[BİTTİ] Örnek No: 1 | PID: 1420 görevini tamamladı ve sonlanıyor.
[BİTTİ] Örnek No: 2 | PID: 1421 görevini tamamladı ve sonlanıyor.
[1]-  Done                    java Asal 1
[2]+  Done                    java Asal 2
```

### Adım 3: Süreç Durumu ve Çıkış Kodu (Exit Status) Kontrolü

Süreçler tamamlandığında işletim sistemine bir dönüş değeri (exit code) bırakırlar. Linux shell'de son komutun başarıyla bitip bitmediğini görmek için:

```bash
echo $?
```
*Çıktı `0` ise süreç hatasız tamamlanmıştır.*

Eğer süreçler bitmeden durumlarını görmek isterseniz:
```bash
ps -ef | grep '[A]sal'
```

---

## 4. Laboratuvar Bulguları ve İşletim Sistemi Karşılıkları

| Gözlemlenen Durum | İşletim Sistemi Mekanizması |
| :--- | :--- |
| Tek bir `Asal.class` dosyasından iki farklı PID oluşması | **Program vs. Process ayrımı:** Kod kesiti (code/text segment) bellekte bağımsız iki adres uzayına ayrılır. |
| İki sürecin konsola karışık/dönüşümlü çıktı basması | **CPU Zamanlaması (Scheduling):** Çekirdek, işlemci zamanını süreçler arasında milisaniyeler düzeyinde paylaştırır (Time Slicing / Round Robin). |
| 5 asal sayı bulunca sürecin kendiliğinden kapanması (`Done`) | **Süreç Yaşam Döngüsü (Process Lifecycle):** Çalışan (Running) durumundan Sonlanmış (Terminated/Zombie) durumuna geçiş ve kaynakların çekirdeğe iadesi. |
| `echo $?` komutunun `0` döndürmesi | **Çıkış Durumu (Exit Status):** Süreç üst sürece (Shell) işini başarıyla tamamladığını bildirir. |

---

## 5. Öğrenci Görevi & Düşünme Soruları

1. **Süreçlerin Bağımsızlığı:** 1 numaralı süreci çalışırken `kill <PID>` ile yarıda kestiğinizde, 2 numaralı süreç 5 asal sayıyı bulmaya devam eder mi?
2. **Çıkış Kodları:** `System.exit(1);` ile normal tamamlanma (`return;`) arasındaki farkı shell üzerinden (`echo $?`) nasıl doğrularız?
3. **Parametrik Üst Sınır:** Kodu, bulunacak asal sayı limitini komut satırından ikinci parametre olarak alacak biçimde güncelleyin (`java Asal <ornek_id> <limit>`).