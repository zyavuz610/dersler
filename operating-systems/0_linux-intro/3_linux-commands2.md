# Bilgisayar Mühendisliği — İşletim Sistemleri
## Konu: Linux Dosya Sistemi Hiyerarşisi (FHS) ve Kritik Sistem Dosyaları

Linux ve Unix türevi işletim sistemlerinde temel felsefelerden biri **"Her şey bir dosyadır"** (*Everything is a file*) ilkesidir. Donanım aygıtları, çalışan süreçlerin bellek durumları, ağ arabirimleri ve sistem yapılandırmaları dosya sistemi ağacında birer dosya veya sanal dosya olarak temsil edilir.

Bu ders notunda Linux'un dizin mimarisi (**Filesystem Hierarchy Standard - FHS**) ve çekirdek/sistem işleyişini yansıtan en kritik dosyalar incelenmektedir.

---

## 1. Temel Dizinler ve Görevleri (FHS)

Linux'ta tüm dosya sistemi tek bir kökten (`/`) başlar. Farklı disk bölümleri veya aygıtlar bu kök altındaki dizinlere bağlanır (*mount* edilir).

```text
/ (Kök - Root)
├── bin -> usr/bin       (Temel kullanıcı komut ikilileri)
├── sbin -> usr/sbin     (Sistem yöneticisi ikilileri)
├── etc                  (Statik sistem yapılandırma dosyaları)
├── proc                 (Sanal çekirdek ve süreç durum dosyaları)
├── sys                  (Sanal donanım ve sürücü arayüzü)
├── dev                  (Aygıt düğümleri / dosyaları)
├── var                  (Değişken veriler: log, kuyruk, önbellek)
├── tmp                  (Geçici dosyalar)
├── home                 (Kullanıcı ev dizinleri)
├── root                 (Süper kullanıcı ev dizini)
├── usr                  (Kullanıcı programları, kütüphaneler, paylaşımlı veriler)
├── opt                  (Üçüncü taraf ek yazılım paketleri)
├── mnt & media          (Bağlama noktaları: USB, harici disk, geçici bağlamalar)
└── boot                 (Önyükleyici - GRUB, çekirdek imajı)
```

| Dizin | Açılım / Anlam | Görevi ve Önemi |
| :--- | :--- | :--- |
| `/bin` & `/usr/bin` | *Binaries* | Tüm kullanıcıların çalıştırabildiği temel çalıştırılabilir programlar (`ls`, `cp`, `bash`, `cat`). Modern dağıtımlarda `/bin`, `/usr/bin` dizinine sembolik bağdır. |
| `/sbin` & `/usr/sbin` | *System Binaries* | Sistem yönetimi, ağ yapılandırması ve bakım için gereken, çoğunlukla `root` yetkisi isteyen ikili dosyalar (`fdisk`, `reboot`, `iptables`). |
| `/etc` | *Editable Text Config / Et Cetera* | Sistemin ve kurulu servislerin metin tabanlı tüm yapılandırma (konfigürasyon) dosyaları. Burada ikili (binary) program bulunmaz. |
| `/proc` | *Process Information Pseudo-fs* | Sabit diskte yer kaplamayan, doğrudan çekirdeğin (RAM'deki) durumunu yansıtan sanal dosya sistemidir. Süreç ve donanım bilgilerini tutar. |
| `/sys` | *System Pseudo-fs* | `/proc` gibi sanal bir dosya sistemidir; özellikle çekirdeğin aygıt modeli, sürücüler ve donanım veri yollarını modüler temsil eder. |
| `/dev` | *Devices* | Donanımların dosya soyutlamasıdır (`/dev/sda` disk, `/dev/null` veri yutucu, `/dev/urandom` rastgele sayı üreteci). |
| `/var` | *Variable Data* | Sistemin çalışması sırasında boyutu sürekli değişen dinamik veriler: log kayıtları (`/var/log`), posta kuyrukları, veritabanı dosyaları. |
| `/tmp` | *Temporary* | Geçici dosyalar. Sistem yeniden başladığında içeriği genellikle temizlenir; tüm kullanıcıların yazma yetkisi vardır. |
| `/home` | *User Homes* | Standart kullanıcıların kişisel belgelerini, ayarlarını (`.bashrc` vb.) barındıran çalışma dizinleri (Örn: `/home/ahmet`). |
| `/root` | *Superuser Home* | `root` (sistem yöneticisi) kullanıcısının kişisel ev dizinidir. `/home` altında değil, ayrı bir konumdadır. |
| `/usr` | *Unix System Resources* | Kullanıcı düzeyindeki uygulamalar, paylaşımlı kütüphaneler (`/usr/lib`) ve belgeler (`/usr/share`). |

---

## 2. En Önemli Sistem ve Yapılandırma Dosyaları

### 2.1 Dağıtım ve Sürüm Bilgisi

#### `/etc/os-release`
* **Görevi:** Kurulu Linux dağıtımının adını, sürüm numarasını, kod adını ve temel ailesini (Debian, RHEL, Arch vb.) standart formatta tutar.
* **İnceleme Komutu:**
  ```bash
  cat /etc/os-release
  ```
* **Örnek Çıktı:**
  ```text
  NAME="Ubuntu"
  VERSION="24.04 LTS (Noble Numbat)"
  ID=ubuntu
  ID_LIKE=debian
  ```

#### `/etc/issue` ve `/etc/hostname`
* `/etc/hostname` : Makinenin yerel ağdaki adını (hostname) saklar.
* `/etc/issue` : Terminal login ekranında kullanıcıya gösterilen karşılama metnini tutar.

---

### 2.2 Kullanıcı, Grup ve Kimlik Doğrulama Dosyaları

Linux'ta kullanıcı hesapları ve kimlik denetimi `/etc` altındaki üç temel metin dosyasıyla idare edilir:

#### `/etc/passwd`
* **Görevi:** Sistemde kayıtlı tüm kullanıcı hesaplarının temel kimlik bilgilerini içerir. **Herkes tarafından okunabilir** (`world-readable`).
* **Format Yapısı (`:` ile ayrılmış 7 alan):**
  ```text
  kullanici_adi : parola_isareti : UID : GID : Yorum/GECKO : Ev_Dizini : Varsayilan_Kabuk
  ```
* **Örnek Satır:**
  ```text
  ahmet:x:1001:1001:Ahmet Yilmaz,,,:/home/ahmet:/bin/bash
  ```
  *(Buradaki `x`, parolanın güvenlik gerekçesiyle `/etc/shadow` dosyasında hash formatında saklandığını belirtir).*

#### `/etc/shadow`
* **Görevi:** Kullanıcı parolalarının tuzlanmış (salted) kriptografik özetlerini (hash) ve parola geçerlilik sürelerini saklar.
* **Erişim İzni:** Yalnızca `root` okuyabilir (`-rw-r-----` veya `000` / `640`). Normal kullanıcıların doğrudan okuması engellenmiştir.
* **İnceleme Komutu:**
  ```bash
  sudo head -n 5 /etc/shadow
  ```

#### `/etc/group`
* **Görevi:** Sistemdeki kullanıcı gruplarını ve bu gruplara üye olan kullanıcıları tanımlar.
* **Örnek Satır:**
  ```text
  sudo:x:27:ahmet,mehmet
  ```

---

### 2.3 Sanal Çekirdek Dosyaları: `/proc` (Bellek ve Donanım Durumu)

`/proc` dizinindeki dosyalar diskte yer kaplamaz; bu dosyalar okunduğunda çekirdek doğrudan RAM'deki anlık veri yapılarını metne dönüştürerek sunar.

#### `/proc/cpuinfo`
* **Görevi:** CPU modelini, mimarisini, çekirdek sayısını, saat hızını, önbellek (cache) boyutlarını ve desteklenen işlemci bayraklarını gösterir.
* **İnceleme Komutu:**
  ```bash
  cat /proc/cpuinfo | grep -E "model name|cpu MHz|cache size" | head -n 6
  ```

#### `/proc/meminfo`
* **Görevi:** Çekirdeğin anlık bellek kullanım detaylarını (Toplam RAM, Boş RAM, Tampon/Önbellek, Swap boyutu vb.) verir. `free` komutu verilerini bu dosyadan okur.
* **İnceleme Komutu:**
  ```bash
  cat /proc/meminfo | head -n 5
  ```

#### `/proc/version`
* **Görevi:** Çalışan Linux çekirdeğinin (kernel) tam derleme sürümünü ve derleyici (GCC/Clang) bilgisini gösterir.
* **İnceleme Komutu:**
  ```bash
  cat /proc/version
  ```

#### `/proc/[PID]/` Dizinleri (Süreç İçi Soyutlama)
Çalışan her sürecin PID numarasıyla `/proc` altında bir dizini oluşur. Örneğin PID'si `1420` olan bir sürecin:
* `/proc/1420/cmdline` : Sürecin hangi komut satırı argümanlarıyla başlatıldığı.
* `/proc/1420/status` : Sürecin bellek kullanımı, UID/GID ve anlık durumu (`State: S`, `R` vb.).
* `/proc/1420/fd/` : Sürecin açık tuttuğu tüm dosya tanıtıcıları (*file descriptors*).

---

### 2.4 Ağ ve Adres Çözümleme Dosyaları

#### `/etc/hosts`
* **Görevi:** Alan adı sunucularına (DNS) gitmeden önce IP adresleri ile alan adlarını yerel olarak eşleyen statik tablodur.
* **Örnek Satır:**
  ```text
  127.0.0.1   localhost
  192.168.1.50 test-sunucu.yerel
  ```

#### `/etc/resolv.conf`
* **Görevi:** Sistemin alan adlarını çözümlemek için sorgulayacağı DNS sunucularının IP adreslerini (`nameserver`) tanımlar.

---

### 2.5 Disk ve Dosya Sistemi Bağlama Tablosu

#### `/etc/fstab` (*File Systems Table*)
* **Görevi:** Sistem önyüklenirken (boot) hangi disk bölümlerinin, hangi dosya sistemi türüyle (ext4, xfs, btrfs), nereye (`mount point`) ve hangi yetkilerle bağlanacağını belirten kalıcı tablodur.
* **Hatalı Düzenleme Riski:** Bu dosyadaki sözdizimi hataları sistemin açılışta kurtarma moduna (*emergency mode*) düşmesine sebep olabilir.

---

### 2.6 Log ve Kayıt Dosyaları: `/var/log`

* `/var/log/syslog` veya `/var/log/messages`: Sistem geneli ve çekirdek log kayıtları.
* `/var/log/auth.log` (Debian/Ubuntu) veya `/var/log/secure` (RHEL/CentOS): Başarılı ve başarısız tüm giriş denemeleri, `sudo` çağrıları.
* `/var/log/dmesg`: Çekirdeğin donanımı başlatırken ürettiği açılış mesajları (`dmesg` komutuyla da okunur).

---

## 3. Komut Satırından Hızlı Doğrulama ve İpuçları

Aşağıdaki komut dizisiyle bu dosyaların pratik çıktısını tek seferde test edebilirsiniz:

```bash
# Dağıtım ve çekirdek sürümünü yazdırın
cat /etc/os-release | grep PRETTY_NAME
cat /proc/version

# Mevcut kullanıcının /etc/passwd içindeki satırını bulun
grep "^$USER:" /etc/passwd

# Toplam çekirdek sayısını /proc/cpuinfo üzerinden saydırın
grep -c "^processor" /proc/cpuinfo

# Boş bellek miktarını sanal dosyadan çekin
grep "MemAvailable" /proc/meminfo

# Kendi shell sürecinizin komut satırını sanal dosyadan okuyun ($$ geçerli PID'dir)
cat /proc/$$/cmdline; echo ""
```

---

## 4. Özet Değerlendirme Soruları

1. `/proc/cpuinfo` dosyasının disk üzerinde kapladığı alan neden `0` bayttır? `ls -l /proc/cpuinfo` çıktısını inceleyerek açıklayınız.
2. Neden kullanıcı parolaları `/etc/passwd` yerine `/etc/shadow` dosyasında tutulur? İki dosyanın izin haklarını (`ls -l`) karşılaştırınız.
3. Bir sunucuda web servisinin (`nginx` veya `apache`) konfigürasyon dosyaları, log kayıtları ve çalıştırılabilir program dosyaları sırasıyla hangi kök dizinler altında aranmalıdır?