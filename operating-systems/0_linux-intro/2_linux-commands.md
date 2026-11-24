# Bilgisayar Mühendisliği — İşletim Sistemleri
## Temel Linux, Dosya Sistemi ve Süreç İnceleme Mekanizmaları

Bu ders notu, işletim sisteminin çekirdeği (kernel) ile kullanıcı arasındaki köprü olan kabuk (shell) ortamını, dosya hiyerarşisini, süreç denetimini ve sistem kaynaklarını gözlemleme araçlarını ele alır. Amacımız komutları yüzeysel ezberlemek değil, işletim sisteminin mimarisini ve soyutlamalarını doğrudan terminal üzerinden doğrulamaktır.

---

## 1. Kabuk (Shell) ve Sistem Bilgilerini Öğrenme

Kabuk, kullanıcıdan aldığı komutları yorumlayıp işletim sistemi çekirdeğine ileten bir sistem programıdır (Command Line Interpreter).

### 1.1 Sistem ve Çekirdek Bilgileri
İşletim sistemi türünü, çekirdek sürümünü ve mimarisini sorgulamak için kullanılır:

* `uname -a` : İşletim sistemi çekirdeğinin adı, ağdaki ana bilgisayar adı (hostname), kernel sürümü, mimari (x86_64 vb.) gibi tüm sistem detaylarını basar.
* `hostname` : Sistemin ağdaki tanımlayıcı adını gösterir.
* `whoami` : Terminalde o an oturum açmış olan aktif kullanıcının kullanıcı adını (username) döndürür.
* `id` : Aktif kullanıcının UID (User ID), GID (Group ID) ve dahil olduğu ek grupları listeler. İşletim sistemi güvenlik ve erişim denetimlerini isimlere göre değil, bu nümerik ID değerlerine göre yürütür.

### 1.2 Sistem Kaynakları ve Çalışma Zamanı
* `uptime` : Sistemin ne kadar süredir kesintisiz çalıştığını, bağlı kullanıcı sayısını ve son 1, 5 ve 15 dakikalık ortalama sistem yükünü (**Load Average**) gösterir.
* `free -h` : Bellek (RAM) ve Takas Alanı (Swap) durumunu insan tarafından okunabilir (human-readable: MB, GB) biçimde listeler.
* `df -h` : Sisteme bağlı dosya sistemlerinin disk alanı kullanımını, toplam boyutunu ve bağlama noktalarını (mount point) listeler.
* `lscpu` : CPU mimarisi, çekirdek sayısı, iş parçacığı (thread) kapasitesi ve önbellek (L1, L2, L3 cache) boyutları hakkında işlemci detaylarını sunar.

---

## 2. Dosya ve Dizin İşlemleri

Linux dosya sistemi tek bir kök dizin (`/`) altında hiyerarşik bir ağaç yapısı olarak organize edilir. Linux felsefesine göre donanım aygıtları, süreçler ve soketler dahil **"her şey bir dosyadır"** (everything is a file).

### 2.1 Temel Gezinme ve Dizin Yönetimi
* `pwd` (*Print Working Directory*): İşletim sisteminin o an içinde bulunduğunuz çalışma dizininin mutlak yolunu (absolute path) verir.
* `ls` : Dizin içeriğini listeler.
  * `ls -l` : Uzun liste formatı (izinler, dosya sahibi, grup, boyut, son değiştirilme zamanı).
  * `ls -la` : Gizli dosyalar (`.` ile başlayanlar) dahil tüm dosyaları listeler.
  * `ls -lh` : Dosya boyutlarını KB, MB cinsinden okunabilir gösterir.
* `cd <hedef>` (*Change Directory*): Çalışma dizinini değiştirir.
  * `cd ..` : Bir üst dizine geçer.
  * `cd ~` veya yalnızca `cd` : Kullanıcının ev dizinine (`/home/kullanici`) döner.
* `mkdir <dizin_adi>` : Yeni bir dizin oluşturur (`mkdir -p a/b/c` iç içe dizinler açar).
* `rmdir <dizin_adi>` : Yalnızca boş olan dizinleri siler.

### 2.2 Dosya Oluşturma, Düzenleme ve Taşıma
* `touch <dosya>` : Dosya mevcut değilse boş bir dosya oluşturur; mevcutsa erişim ve değiştirilme zaman damgasını (timestamp) günceller.
* `cat <dosya>` : Dosyanın içeriğini terminale art arda döker.
* `head -n 5 <dosya>` : Dosyanın ilk 5 satırını görüntüler.
* `tail -n 5 <dosya>` : Dosyanın son 5 satırını görüntüler (`tail -f` log dosyalarını canlı izler).
* `cp <kaynak> <hedef>` : Dosya veya dizinleri kopyalar (dizinler için `cp -r`).
* `mv <kaynak> <hedef>` : Dosyayı taşır veya adını değiştirir.
* `rm <dosya>` : Dosyayı kalıcı olarak siler (`rm -r` dizinleri yinelemeli siler).

---

## 3. Dosya İzinleri ve Güvenlik Mekanizması

Linux'ta her dosya ve dizin için üç erişim seviyesi tanımlanır:
1. **User (u):** Dosya sahibi
2. **Group (g):** Dosyanın ait olduğu gruptaki kullanıcılar
3. **Others (o):** Sistemdeki diğer tüm kullanıcılar

İzin türleri ve sayısal (oktal) karşılıkları:
* **r (read - okuma):** `4`
* **w (write - yazma/değiştirme):** `2`
* **x (execute - çalıştırma):** `1`
* **- (izin yok):** `0`

Örnek İzin Deseni: `-rwxr-xr--`
* `rwx` (Sahip = 4+2+1 = 7): Tam yetkili.
* `r-x` (Grup = 4+0+1 = 5): Okur ve çalıştırır, yazamaz.
* `r--` (Diğerleri = 4+0+0 = 4): Yalnızca okur.

İlgili komutlar:
* `chmod <mod> <dosya>` : Dosya izinlerini değiştirir (Örn: `chmod 755 script.sh` veya `chmod 600 secret.txt`).
* `chown <kullanici>:<grup> <dosya>` : Dosyanın sahibini veya grubunu günceller.

---

## 4. Süreç (Process) İşlemleri ve Arka Plan Yönetimi

İşletim sisteminde çalışan her süreç bir yaşam döngüsüne ve benzersiz bir kimliğe sahiptir.

### 4.1 Ön Plan (Foreground) ve Arka Plan (Background)
* Bir komutun sonuna `&` eklendiğinde, kabuk sürecin bitmesini beklemeden derhal kontrolü kullanıcıya geri verir.
  ```bash
  sleep 60 &
  ```
* `jobs` : Mevcut shell oturumuna ait arka planda çalışan veya askıya alınmış işleri numaralarıyla listeler.
* `Ctrl + C` : Ön planda çalışan sürece `SIGINT` (Interrupt) sinyali göndererek süreci sonlandırır.
* `Ctrl + Z` : Ön plandaki süreci duraklatır (askıya alır) ve arka plana iter (`SIGTSTP` sinyali).
* `bg %1` : Askıya alınmış 1 numaralı işi arka planda çalıştırmaya devam eder.
* `fg %1` : Arka plandaki 1 numaralı işi ön plana getirir.

---

## 5. Süreç Gözetleme ve İzleme Komutları

### 5.1 Anlık Süreç Gözetleme: `ps`
`ps` (*Process Status*), o anda çalışan süreçlerin anlık ekran görüntüsünü (snapshot) alır:

* `ps` : Yalnızca geçerli terminal oturumuna bağlı süreçleri gösterir.
* `ps -ef` : Sistemde koşan tüm süreçleri tam formatta listeler (`UID`, `PID`, `PPID`, `C`, `STIME`, `TTY`, `TIME`, `CMD`).
* `ps aux` : BSD formatında tüm süreçleri CPU ve Bellek kullanım yüzdeleriyle döker.
* `ps -o pid,ppid,stat,cmd` : Çıktıyı belirli alanlarla sınırlandırır:
  * `STAT` Sütunundaki Temel Kodlar:
    * `R` (*Running*): Çalışıyor veya çalışmaya hazır kuyruğunda.
    * `S` (*Interruptible Sleep*): Bir olay, I/O veya sinyal bekliyor.
    * `D` (*Uninterruptible Sleep*): Genellikle disk I/O bekleyen, kesilemeyen süreç.
    * `Z` (*Zombie*): Görevini tamamlamış ancak üst süreci (parent) tarafından çıkış kodu henüz okunmamış süreç.
    * `T` (*Stopped*): Sinyal ile durdurulmuş süreç.

### 5.2 Dinamik ve Canlı İzleme: `top` ve `htop`
* `top` : Sistemdeki süreçleri, işlemci ve RAM kullanımını gerçek zamanlı güncelleyen dinamik izleme aracıdır.
  * `q` : Çıkış yapar.
  * `k` : PID girerek süreç sonlandırma imkanı tanır.
  * `M` : Süreçleri bellek kullanımına göre sıralar.
  * `P` : Süreçleri CPU kullanımına göre sıralar.

---

## 6. Süreç Sinyalleri ve Süreç Sonlandırma

Linux çekirdeği süreçler arası haberleşme ve durum yönetimi için **sinyalleri (Signals)** kullanır. Bir sürece sinyal göndermek için `kill` komutu kullanılır.

### 6.1 Temel Sinyaller
* `SIGTERM (15)` : Varsayılan sonlandırma isteğidir. Süreç bu sinyali yakalayabilir, açık dosyalarını kaydedip temiz bir şekilde (graceful shutdown) kapanabilir.
* `SIGKILL (9)` : Çekirdek tarafından doğrudan ele alınır; süreç tarafından yakalanamaz veya yok sayılamaz. Süreci anında zorla öldürür.
* `SIGHUP (1)` : Oturumun koptuğunu bildirir; arka plan sunucularında (daemon) konfigürasyon dosyasını yeniden yüklemek için kullanılır.

### 6.2 Sinyal Gönderme Komutları
```bash
# PID üzerinden sonlandırma (Varsayılan olarak SIGTERM gönderir):
kill <PID>

# Zorla sonlandırma (SIGKILL):
kill -9 <PID>

# İsme göre sonlandırma:
killall sleep
pkill -f sleep
```

---

## 7. Doğrudan Laboratuvar Uygulaması: 10 Dakikalık Keşif

Aşağıdaki komut dizisini terminalinizde sırayla uygulayarak sonuçları inceleyin:

```bash
# 1. Kendi kimliğinizi ve sisteminizi doğrulayın
whoami
uname -s -r

# 2. Deneme dizini açıp içine bir dosya yazın
mkdir oslab_demo && cd oslab_demo
echo "Isletim sistemleri arka plan mekanizmalari" > lab.txt
ls -lh lab.txt

# 3. İzinleri değiştirip erişimi test edin
chmod 000 lab.txt
cat lab.txt              # "Permission denied" hatası gözlemlenmelidir
chmod 644 lab.txt
cat lab.txt              # Dosya başarıyla okunacaktır

# 4. Arka planda bir süreç başlatıp PID'sini yakalayın
sleep 120 &
SUREC_PID=$!
echo "Baslatilan surecin PID degeri: $SUREC_PID"

# 5. Sürecin durumunu gözlemleyin ve ardından sonlandırın
ps -o pid,ppid,stat,cmd -p $SUREC_PID
kill -9 $SUREC_PID

# 6. Temizlik
cd .. && rm -rf oslab_demo
```