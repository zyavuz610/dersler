# Süreç (Process) Mekanizması ve CPU Zamanlama

> **Konu:** Tek bir programdan türetilen bağımsız süreçlerin işletim sistemi tarafından nasıl yönetildiği
> **Yöntem:** Shell üzerinde doğrudan gözlem (`ps`, `taskset`, `watch`)

---

## 1. Temel Kavramlar

| Kavram | Tanım |
|---|---|
| **Program (binary)** | Diskte duran pasif bayt dizisi (çalıştırılabilir kalıp / image) |
| **Process (süreç)** | Çalışan programın bellekteki canlı örneği |
| **PID** | Çekirdeğin her sürece atadığı benzersiz kimlik numarası |
| **PCB (Process Control Block)** | Çekirdeğin süreç hakkında tuttuğu kayıt (PID, durum, kayıtçılar, bellek bilgisi vb.) |
| **Sanal adres uzayı** | Her sürece ayrılan, diğerlerinden yalıtılmış bellek görünümü |
| **Time slice / quantum** | Zamanlayıcının bir sürece bir seferde verdiği birkaç milisaniyelik CPU dilimi |
| **Context switch** | CPU'nun bir süreçten diğerine geçerken durumu kaydedip yükleme işlemi |
| **CPU affinity** | Bir sürecin hangi çekirdek(ler)de çalışabileceğinin kısıtlanması |
| **CFS** | Linux'un Completely Fair Scheduler zamanlayıcısı |

---

## 2. Deney Hazırlığı

CPU tüketen ve terminale durum yazan minimal bir C programı (`worker.c`) kullanılır.

```bash
mkdir -p ~/os_process_lab && cd ~/os_process_lab

cat << 'EOF' > worker.c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Kullanim: %s <Etiket>\n", argv[0]);
        return 1;
    }

    char *label = argv[1];
    pid_t pid = getpid();

    printf("[BASLADI] Etiket: %s | PID: %d\n", label, pid);

    // CPU harcayan döngü: her 150 milyon adımda bir durum basar
    unsigned long long sayac = 0;
    while (1) {
        sayac++;
        if (sayac % 150000000ULL == 0) {
            printf("[CALISIYOR] Etiket: %s | PID: %d | Adim: %llu\n", label, pid, sayac);
            usleep(100000); // 100 ms ara
        }
    }
    return 0;
}
EOF

gcc -O2 worker.c -o worker
```

---

## 3. Gözlemler

### Gözlem 1: Aynı programdan iki process, farklı PID

**Soru:** Diskteki tek `worker` dosyası bellekte nasıl iki bağımsız varlık olur?

```bash
./worker A &
PID_A=$!

./worker B &
PID_B=$!

echo "Süreç A PID: $PID_A"
echo "Süreç B PID: $PID_B"
```

**Çıkarım:**
- Program = diskteki pasif dosya; process = çalışan örnek.
- Çekirdek her çalıştırmada **yeni sanal adres uzayı** ve **yeni PCB** oluşturur.
- Bu yüzden bir dosyadan, farklı PID'lere sahip iki bağımsız süreç doğar.

---

### Gözlem 2: Standart çıktının karışması

**Soru:** İki süreç aynı anda yazarken satırlar neden karışık sırayla görünür?

Örnek çıktı:

```text
[BASLADI] Etiket: A | PID: 12450
[BASLADI] Etiket: B | PID: 12451
[CALISIYOR] Etiket: A | PID: 12450 | Adim: 150000000
[CALISIYOR] Etiket: B | PID: 12451 | Adim: 150000000
[CALISIYOR] Etiket: B | PID: 12451 | Adim: 300000000
[CALISIYOR] Etiket: A | PID: 12450 | Adim: 300000000
```

**Çıkarım:**
- İki sürecin `stdout`'u (dosya tanımlayıcı `1`) aynı sanal terminale (`/dev/pts/X`) bağlıdır.
- Süreçler arasında yazma senkronizasyonu (mutex/lock) yoktur.
- Hangi sürece CPU zamanı verilirse onun çıktısı tampona yazılır, sıra rastgele görünür.

---

### Gözlem 3: CPU zamanının paylaştırılması (time-slicing)

**Soru:** İki süreç gerçekten aynı anda mı çalışıyor, yoksa işletim sistemi bizi mi kandırıyor?

```bash
# Anlık durum, CPU kullanımı ve harcanan işlemci süresi
ps -o pid,stat,%cpu,time,comm -p $PID_A,$PID_B

# Her 1 saniyede bir canlı izleme
watch -n 1 "ps -o pid,stat,%cpu,time,comm -p $PID_A,$PID_B"
```

**Çıkarım:**
- `STAT` sütunu: `R` (Running) veya `S` (Sleeping/Waiting).
- Tek veya kısıtlı çekirdekte CFS, süreçleri **time slice** dilimlerine bölerek dönüşümlü çalıştırır.
- `TIME` sütunu sürecin CPU'da geçirdiği toplam süreyi gösterir; iki sürecin değerlerinin dengeli artması adil paylaşımın kanıtıdır.

---

### Gözlem 4: `&` ≠ ayrı çekirdekte çalıştırma

**Soru:** Komutun sonuna `&` koymak süreci ikinci bir çekirdeğe mi gönderir?

```bash
# Süreçlerin o an koştuğu çekirdek (PSR)
ps -o pid,psr,comm -p $PID_A,$PID_B

# Her iki süreci CPU 0'a sabitle (CPU affinity)
taskset -cp 0 $PID_A
taskset -cp 0 $PID_B

# Tekrar kontrol (ikisi de PSR = 0 olmalı)
ps -o pid,psr,%cpu,comm -p $PID_A,$PID_B
```

**Çıkarım:**
- `&` çekirdeğe değil **kabuğa** talimat verir: "süreç bitmesini beklemeden yeni komut al."
- Sürecin hangi çekirdeğe gideceğine **çekirdeğin zamanlayıcısı** karar verir.
- İki süreç tek çekirdeğe kilitlense bile çıktılar akmaya devam eder, çünkü çekirdek **context switch** ile ikisini sırayla çalıştırır.

---

## 4. Temizlik

```bash
kill -15 $PID_A $PID_B     # nazik sonlandırma (SIGTERM)
ps -p $PID_A,$PID_B        # kapandığını doğrula

kill -9 $PID_A $PID_B      # kapanmadıysa zorla (SIGKILL)
```

---

## 5. Özet (Kısa Tekrar)

1. Bir program dosyası, her çalıştırıldığında **ayrı PID, PCB ve adres uzayına** sahip yeni bir process üretir.
2. Aynı terminale yazan süreçlerin çıktıları, senkronizasyon olmadığı için **iç içe geçebilir**.
3. Tek çekirdekte "eşzamanlılık" aslında **time-slicing + context switch** ile sağlanan bir görüntüdür.
4. `&` yalnızca kabuğun davranışını değiştirir; **çekirdek ataması zamanlayıcının işidir**.
5. `taskset` ile süreçler belirli çekirdeklere sabitlenebilir (CPU affinity).

---

## 6. Değerlendirme Soruları

1. `worker` programını 5 kez `&` ile başlatsaydınız ve sistemde yalnızca 2 çekirdek olsaydı, `ps -o pid,psr` çıktısında ne gözlemlerdiniz?
2. `taskset -cp 0 <PID>` ile iki süreci tek çekirdeğe sabitlediğinizde toplam `%CPU` nasıl dağıldı? Neden?
3. İki sürecin terminal çıktıları çakıştığında veri kaybı yaşanır mı, yoksa yalnızca görsel bir sıralama bozukluğu mudur? Açıklayınız.

---

## 7. Ek Çalışma Önerileri

- Bağlam değişimi sayısını `vmstat` ve `pidstat` ile ölçmek (tek çekirdeğe bağlanmış süreçlerde).
- Derleyicisi olmayan ortamlar için aynı deneyi yalnızca bash/sh yerleşik komutlarıyla yapmak.