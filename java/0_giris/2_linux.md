# Temel Linux Komutları ve Terminal Rehberi

Java geliştirirken özellikle terminal ortamlarında (Linux, macOS, WSL, Git Bash veya bulut tabanlı platformlar: Replit, GitHub Codespaces vb.) dosyaları yönetmek, derlemek ve çalıştırmak için temel Linux komutlarını bilmek büyük önem taşır.

Bu rehber, en sık kullanılan temel Linux komutlarını, parametrelerini ve pratik kullanım örneklerini içermektedir.

---

## 📌 İçindekiler
1. [Dizin Gezinme ve Listeleme Komutları](#1-dizin-gezinme-ve-listeleme-komutları)
2. [Dosya ve Dizin Oluşturma / Yönetme](#2-dosya-ve-dizin-oluşturma--yönetme)
3. [Dosya İçeriğini Görüntüleme](#3-dosya-içeriğini-görüntüleme)
4. [Sistem ve Ortam Bilgisi Alma](#4-sistem-ve-ortam-bilgisi-alma)
5. [Pratik: Terminalde Bir Java Projesi İş Akışı](#5-pratik-terminalde-bir-java-projesi-iş-akışı)
6. [Hayat Kurtaran Terminal Kısayolları](#6-hayat-kurtaran-terminal-kısayolları)
7. [Terminal Alıştırmaları ve Uygulama Görevleri](#7-terminal-alıştırmaları-ve-uygulama-görevleri)

---

## 1. Dizin Gezinme ve Listeleme Komutları

| Komut | Açıklama | Örnek Kullanım |
| :--- | :--- | :--- |
| **`pwd`** | *Print Working Directory:* Şu an bulunduğunuz dizinin tam yolunu ekrana basar. | `pwd` |
| **`ls`** | *List:* Bulunduğunuz dizindeki dosya ve klasörleri listeler. | `ls` |
| **`cd`** | *Change Directory:* Farklı bir dizine geçiş yapmayı sağlar. | `cd java_projeleri` |
| **`clear`** | Terminal ekranını temizler. | `clear` |

### Pratik Kullanımlar ve Parametreler:

#### `ls` Parametreleri:
- `ls -l` : Dosyaların izinlerini, boyutlarını ve son düzenleme tarihlerini detaylı (long listing) gösterir.
- `ls -a` : Gizli dosyalar (başında `.` olan `.git`, `.bashrc` vb.) dahil her şeyi listeler.
- `ls -la` : Hem detaylı hem de gizli dosyaları birlikte listeler.

#### `cd` ile Gezinme İpuçları:
- `cd ..` : Bir üst dizine (klasöre) çıkar.
- `cd ../..` : İki üst dizine çıkar.
- `cd ~` veya sadece `cd` : Kullanıcının ana (home) dizinine döner.
- `cd -` : Bir önceki bulunduğunuz dizine geri döner.

---

## 2. Dosya ve Dizin Oluşturma / Yönetme

| Komut | Açıklama | Örnek Kullanım |
| :--- | :--- | :--- |
| **`mkdir`** | *Make Directory:* Yeni bir klasör (dizin) oluşturur. | `mkdir 0_giris` |
| **`touch`** | Boş bir dosya oluşturur (veya var olanın tarihini günceller). | `touch Main.java` |
| **`cp`** | *Copy:* Dosya veya dizinleri kopyalar. | `cp Main.java YedekMain.java` |
| **`mv`** | *Move:* Dosyayı taşır veya adını değiştirir (rename). | `mv EskiAd.java YeniAd.java` |
| **`rm`** | *Remove:* Dosyayı siler. | `rm gecici.txt` |
| **`rmdir`** | Sadece **boş** olan bir klasörü siler. | `rmdir bos_klasor` |

### Dikkat Edilmesi Gereken Parametreler:

- **İç içe klasör açma (`mkdir -p`):**
  ```bash
  mkdir -p proje/src/com/uygulama
  ```
- **Klasörü içindekilerle kopyalama (`cp -r`):**
  ```bash
  cp -r eski_klasor yeni_klasor
  ```
- **Dolu klasörü içindekilerle birlikte silme (`rm -r` veya `rm -rf`):**
  ```bash
  rm -r silinecek_klasor
  ```
  > ⚠️ **UYARI:** `rm` ile silinen dosyalar çöp kutusuna gitmez, doğrudan kalıcı olarak silinir. Bu yüzden dikkatli kullanılmalıdır!

---

## 3. Dosya İçeriğini Görüntüleme

Terminalden çıkmadan bir dosyanın içeriğini hızlıca okumak için kullanılır:

| Komut | Açıklama | Örnek Kullanım |
| :--- | :--- | :--- |
| **`cat`** | Dosyanın tüm içeriğini doğrudan terminale döker. | `cat Main.java` |
| **`less`** | Uzun dosyaları sayfa sayfa (yukarı/aşağı oklarla) okumanızı sağlar (`q` ile çıkılır). | `less uzun_log.txt` |
| **`head`** | Dosyanın ilk 10 satırını gösterir. | `head -n 5 Main.java` |
| **`tail`** | Dosyanın son 10 satırını gösterir. | `tail -n 5 Main.java` |

---

## 4. Sistem ve Ortam Bilgisi Alma

| Komut | Açıklama |
| :--- | :--- |
| **`cat /etc/os-release`** | Kurulu olan Linux dağıtımı (Ubuntu, Debian, Fedora vb.) ve sürüm bilgilerini gösterir. |
| **`whoami`** | O an terminalde aktif olan kullanıcının adını yazar. |
| **`which javac`** | `javac` derleyicisinin sistemde hangi dizinde kurulu olduğunu bulur. |
| **`javac -version`** | Yüklü Java derleyicisinin sürümünü kontrol eder. |
| **`java -version`** | Yüklü Java çalışma zamanı (JRE/JVM) sürümünü kontrol eder. |

---

## 5. Pratik: Terminalde Bir Java Projesi İş Akışı

Aşağıda sıfırdan terminal açıldığında uygulanan tipik bir geliştirme adımı gösterilmiştir:

```bash
# 1. Nerede olduğumuzu kontrol edelim:
pwd

# 2. Yeni bir proje klasörü açıp içine girelim:
mkdir java_dersi
cd java_dersi

# 3. 'Main.java' adında bir kaynak dosya oluşturalım:
touch Main.java

# 4. Dosya oluştu mu kontrol edelim:
ls -la

# 5. Dosyaya kod yazalım (nano editörü ile veya IDE ile):
# nano Main.java

# 6. Kodun içeriğini terminalden kontrol edelim:
cat Main.java

# 7. Java kodunu derleyelim:
javac Main.java

# 8. Derleme sonucunda 'Main.class' bayt kodunun geldiğini görelim:
ls

# 9. Programımızı çalıştıralım:
java Main
```

---

## 6. Hayat Kurtaran Terminal Kısayolları

Terminalde hızlı ve hatasız çalışmak için bu kısayolları alışkanlık haline getirin:

- **`Tab` Tuşu (Otomatik Tamamlama):**
  - Dosya veya klasör adının ilk birkaç harfini yazıp `Tab`'a bastığınızda Linux gerisini otomatik tamamlar. Çift `Tab`'a basarsanız olası eşleşmeleri listeler.
- **`Yukarı (↑) / Aşağı (↓)` Ok Tuşları:**
  - Daha önce çalıştırdığınız komutlar arasında gezinmenizi sağlar. Aynı komutu tekrar yazmak yerine yukarı okla bulabilirsiniz.
- **`Ctrl + C` (İptal):**
  - Sonsuz döngüye giren veya o an çalışan bir programı/komutu anında durdurur ve sonlandırır.
- **`Ctrl + L`:**
  - `clear` komutu gibi ekranı temizler ve imleci en üste alır.
- **`history`:**
  - Daha önce terminalde yazdığınız komutların geçmişini numaralı olarak listeler.

---

## 7. Terminal Alıştırmaları ve Uygulama Görevleri

### ✍️ Hızlı Terminal Alıştırmaları:
1. **İç İçe Klasör Oluşturma:** Tek bir `mkdir` komutu kullanarak `java_lab/hafta_1/kodlar` yolunu oluşturunuz (`mkdir -p` parametresini araştırınız).
2. **Gizli Dosyaları Bulma:** Ana dizininize (`cd ~`) geçerek `ls -la` komutunu çalıştırınız. Başında nokta (`.`) olan en az 3 dosya veya klasör tespit ediniz (Örn: `.bashrc`, `.gitconfig`).
3. **Kombinasyonlu Liste:** Dosyaları boyutlarına göre sıralı listelemek için `ls -lhS` komutunu deneyiniz ve parametrelerin ne işe yaradığını not ediniz.

### 📋 Ödev ve Uygulama Görevi:

#### Ödev: "Terminal Şampiyonu" Uçtan Uca Senaryo
Aşağıdaki adımları **yalnızca terminal komutları kullanarak** (grafik arayüze hiç dokunmadan) sırasıyla gerçekleştiriniz ve çalıştırdığınız komutları bir metin dosyasına kaydediniz:

1. `pwd` ile bulunduğunuz dizini görüntüleyiniz.
2. `proje_odevi` adında bir klasör oluşturup içine giriniz (`cd proje_odevi`).
3. `touch IlkDeneme.java` komutuyla boş bir Java kaynak dosyası oluşturunuz.
4. Dosyayı yedeklemek için `cp IlkDeneme.java YedekDeneme.java` komutunu çalıştırınız.
5. `ls -l` ile iki dosyanın da klasörde olduğunu doğrulayınız.
6. `mv YedekDeneme.java EskiDeneme.java` ile yedeğin adını değiştiriniz.
7. `rm EskiDeneme.java` ile değiştirdiğiniz dosyayı siliniz.
8. Bir üst dizine çıkınız (`cd ..`).
9. `proje_odevi` klasörünü ve içindeki tüm dosyaları tek komutla siliniz (`rm -r proje_odevi`).
10. `history` komutuyla bu süreçte yazdığınız tüm komutların çıktısını alınız.
