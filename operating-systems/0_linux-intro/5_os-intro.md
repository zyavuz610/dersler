# 1. Hafta: İşletim Sistemlerine Giriş ve Temel Kavramlar

Bu ders notu, soyut işletim sistemi teorilerini doğrudan Linux terminalinde gözlemleyebilmeniz için tasarlanmıştır. Her teorik kavramın hemen altında yer alan kabuk (shell) deneylerini uygulayarak kavramların işletim sistemi çekirdeğindeki (kernel) karşılıklarını bizzat inceleyebilirsiniz.

---

## 1. Ders: İşletim Sisteminin Rolü, Kesmeler ve İkili Mod (Ring 0 / Ring 3)

### 1.1 İşletim Sistemi Nedir?

İşletim sistemi (Operating System - OS), kullanıcı uygulamaları ile fiziksel donanım arasında çalışan bir **Kaynak Yöneticisi (Resource Manager)** ve **Soyutlama Katmanıdır (Extended Machine)**.

* **Kaynak Yöneticisi:** CPU'yu zaman dilimlerine (**Time-multiplexing**), RAM ve diski ise adres bloklarına (**Space-multiplexing**) bölerek süreçler arasında paylaştırır.
* **Soyutlama (Abstraction):** Geliştiriciyi doğrudan disk kafa hareketleri veya fiziksel transistör adresleriyle uğraştırmaz; yerine **Dosya (File)**, **Adres Uzayı (Address Space)** ve **Süreç (Process)** soyutlamalarını sunar.

---

### 1.2 Kesmeler (Interrupts): İşletim Sisteminin Nabzı

İşlemci (CPU), G/Ç (I/O) birimlerini sürekli denetlemez (**Polling / Sorgulama**). Bunun yerine birimler bir durum değişikliği olduğunda CPU'ya sinyal gönderir (**Interrupt / Kesme**). İşletim sistemleri **olay güdümlü (event-driven)** sistemlerdir.

| Kesme Türü | Kaynak | Eşzamanlılık | Örnek |
| --- | --- | --- | --- |
| **Donanım Kesmesi (Hardware Interrupt)** | Çevre birimleri, Zamanlayıcı (Timer) | Asenkron | Klavyeden tuşa basılması, ağ paketinin gelmesi |
| **Tuzak / İstisna (Trap / Exception)** | CPU çalıştırma birimi | Senkron | Sıfıra bölme hatası (`div by zero`), geçersiz bellek erişimi |
| **Yazılımsal Kesme (Software Interrupt)** | Kullanıcı programı | Senkron | Sistem çağrısı (`syscall`, `int 0x80`) |

#### Laboratuvar Deneyi 1: Canlı Kesme Sayacını İnceleme

Linux, çekirdeğin donanım kesmelerini nasıl yakaladığını `/proc/interrupts` sanal dosyasında tutar:

```bash
# Zamanlayıcı (Timer) ve Klavye (i8042) kesmelerini anlık izleyin:
watch -n 1 "head -n 20 /proc/interrupts"
```

> **Gözlem:** Klavyeden bir tuşa bastığınızda ilgili IRQ satırındaki sayacın arttığını göreceksiniz. CPU her tuş basımında mevcut işini geçici olarak durdurup **Interrupt Service Routine (ISR)** çalıştırmaktadır.
> Replit üzerinde çalışmaz. Replit üzerindeki terminal fiziksel bir anakarta bağlı değildir. Klavyenizden bir tuşa bastığınızda sinyal CPU'nun kesme pinine (IRQ 1 - i8042) gitmez; tarayıcınız (JavaScript) tuşu yakalar, ağ üzerinden WebSocket ile sunucuya iletir ve bir sanal terminale (/dev/pts/*) yazar.

---

### 1.3 İkili Mod Çalışması (Dual-Mode) ve Sistem Çağrıları

Sistem kararlılığı için modern işlemciler donanımsal koruma halkaları (**Protection Rings**) sunar:

* **Ring 3 (User Mode):** Kullanıcı kodları çalışır. Doğrudan donanım portlarına veya çekirdek belleğine erişemez.
* **Ring 0 (Kernel Mode):** Çekirdek çalışır. Tüm ayrıcalıklı komutlar (`CLI`, `STI`, `HLT`, `MOV CR3`) serbesttir.

Bir kullanıcı programı ekrana yazı basmak veya dosyaya erişmek istediğinde doğrudan donanıma gidemez; **Sistem Çağrısı (System Call)** ile çekirdekten ricada bulunur:

```
[Kullanıcı Alanı: Ring 3] ── printf() ──> glibc write() ──> [SYSCALL Komutu]
                                                                  │ (Mod Değişimi: User -> Kernel)
                                                                  ▼
[Çekirdek Alanı: Ring 0]  ── sys_call_table ──────────────> sys_write() ──> Donanım Sürücüsü
```

#### Laboratuvar Deneyi 2: Sistem Çağrılarını `strace` ile Yakalama

Herhangi bir program çalışırken arkasında dönen çekirdek taleplerini izleyebilirsiniz:

```bash
# ls komutunun arka planda çekirdekten neleri istediğini filtreleyin:
strace -e trace=openat,read,write ls
```

> **Gözlem:** Basit bir `ls` komutu bile diski açmak için `openat`, okumak için `read` ve ekrana basmak için `write` sistem çağrılarını tetikler.

---

## 2. Ders: Linux Felsefesi, Süreç ve Dosya Sistemi Mekanizmaları

### 2.1 Unix Felsefesi: "Her Şey Bir Dosyadır"

Unix/Linux sistemlerinde donanımlar, soketler, boru hatları ve süreç bilgileri dosya sistemi hiyerarşisi altında soyutlanır.

#### Laboratuvar Deneyi 3: Çekirdek Belleğini Dosya Olarak Okuma

İşlemci mimarinizi ve sistem belleğinizi `/proc` sanal dosya sistemi üzerinden okuyabilirsiniz:

```bash
# İşlemci modelini metin filtresiyle ayıklayın:
cat /proc/cpuinfo | grep "model name" | sort -u

# Çekirdeğin anlık bellek durumunu görüntüleyin:
cat /proc/meminfo | head -n 5
```

---

### 2.2 Süreç (Process) Yaşam Döngüsü ve Gözetleme

Program diskteki pasif bayt dizisidir (çalıştırılabilir dosya). **Process** ise işletim sistemi tarafından belleğe yüklenmiş ve yürütülen canlı program örneğidir. Her sürecin benzersiz bir **PID (Process ID)** değeri vardır.

#### Laboratuvar Deneyi 4: Arka Plan Süreci ve Sinyallerle Müdahale

```bash
# 1. Arka planda 150 saniye uyuyan bir süreç başlatın (&)
sleep 150 &

# 2. Başlatılan son sürecin PID değerini ekrana yazdırın:
echo "Calisan Surec PID: $!"

# 3. Sürecin durumunu (STAT: S -> Sleeping) gözlemleyin:
ps -o pid,ppid,stat,cmd | grep sleep

# 4. Sürece sonlandırma sinyali gönderin:
kill -9 $!
```

> **STAT Kodları Anlamı:**
> * `R (Running):` O anda CPU üzerinde çalışıyor veya çalışmaya hazır.
> * `S (Interruptible Sleep):` Bir olay veya I/O tamamlanmasını bekliyor.
> * `Z (Zombie):` İşi bitmiş ancak üst süreci (parent) çıkış durumunu henüz okumamış süreç.

---

### 2.3 Dosya İzinleri ve Güvenlik Modeli

Linux dosya güvenliği; **Kullanıcı (User)**, **Grup (Group)** ve **Diğerleri (Others)** için ayrı ayrı tanımlanan `r` (okuma - 4), `w` (yazma - 2), `x` (çalıştırma - 1) bitlerine dayanır.

#### Laboratuvar Deneyi 5: İzin Reddi (Permission Denied) Simülasyonu

```bash
# 1. Deney dizini ve gizli dosya oluşturun:
mkdir -p /tmp/os_lab && cd /tmp/os_lab
echo "Bu gizli bir veridir." > gizli.txt

# 2. Dosyanın mevcut izinlerini inceleyin:
ls -l gizli.txt

# 3. Dosyadan tüm okuma/yazma izinlerini kaldırın (000):
chmod 000 gizli.txt
ls -l gizli.txt

# 4. Dosyayı okumaya çalışın:
cat gizli.txt
# Çıktı: cat: gizli.txt: Permission denied (Erişim engellendi)

# 5. İzni sadece dosya sahibine geri verin (rw- --- --- = 600):
chmod 600 gizli.txt
cat gizli.txt
```

---

### 2.4 Dosya Sistemi Derinliği: Hardlink vs. Softlink

Linux'ta her dosya disk üzerinde bir **Inode (Index Node)** yapısıyla tanımlanır. Inode; dosyanın izinlerini, boyutunu ve veri bloklarının fiziksel adresini tutar; dosya adını tutmaz. Dosya adı sadece bir dizin girişidir.

```
[Hardlink 1] ───┐
                ├───> [ Inode 10452 ] ───> [ Fiziksel Veri Blokları ]
[Hardlink 2] ───┘                            ▲
                                             │
[Softlink]   ───> [ Inode 88712 ] ───────────┘ (İçinde hedef yol metni tutulur)
```

| Kriter | Hardlink (Sabit Bağlantı) | Softlink (Sembolik Bağlantı / Symlink) |
| --- | --- | --- |
| **Inode Numarası** | Orijinal dosya ile **aynı** | Kendisine ait **farklı** bir Inode alır |
| **Kaynak Dosya Silinirse** | Veri silinmez, erişim sürer | Kırık bağlantı (**broken link**) olur |
| **Farklı Dosya Sistemi** | Desteklenmez | Desteklenir |
| **Dizin Bağlama** | Döngüleri engellemek için yasaktır | Dizinlere serbestçe uygulanabilir |

#### Laboratuvar Deneyi 6: Inode Davranışını İnceleme

```bash
cd /tmp/os_lab

# 1. Kaynak dosya oluşturun:
echo "Inode test verisi" > kaynak.txt

# 2. Hardlink ve Softlink oluşturun:
ln kaynak.txt sabit_bag.txt
ln -s kaynak.txt sembolik_bag.txt

# 3. Inode numaralarını (-i) ve bağlantı sayılarını karşılaştırın:
ls -li kaynak.txt sabit_bag.txt sembolik_bag.txt
# Gözlem: kaynak.txt ve sabit_bag.txt aynı Inode numarasına sahiptir!

# 4. Orijinal dosyayı silin ve sonuçları gözlemleyin:
rm kaynak.txt
cat sabit_bag.txt       # Veri başarıyla okunur!
cat sembolik_bag.txt    # Hata: "No such file or directory"
```

---