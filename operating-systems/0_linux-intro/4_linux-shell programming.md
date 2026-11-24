# Ders Notu: Linux Sistem Programlama ve Kabuk (Bash) Betikleme

Bu ders notu, işletim sistemi kavramlarını otomatize etmek ve sistem düzeyinde programlama pratikleri geliştirmek amacıyla hazırlanmıştır. Bir shell betiğinin yaşam döngüsü, çalışma mantığı, kontrol akış mekanizmaları (`if`, `for`, döngüler), değişkenler ve dosya izinleri üzerinden betik çalıştırma yöntemleri uygulamalı olarak ele alınmaktadır.

---

## 1. Kabuk (Shell) Betiği Nedir ve Nasıl Çalışır?

Bir kabuk betiği (shell script), Linux kabuğuna (örneğin `/bin/bash` veya `/bin/sh`) sırayla yorumlatılan komutlar dizisidir. Derlenen dillerden (C/C++, Go) farklı olarak **yorumlanan (interpreted)** bir dildir.

### 1.1 Shebang (`#!`) Satırının Anlamı

Her kabuk betiğinin en başında yer alan `#!/bin/bash` ifadesine **Shebang** (veya Hashbang) denir.

* Çekirdek (kernel), çalıştırılabilir bir metin dosyasıyla karşılaştığında dosyanın ilk 2 baytındaki sihirli sayıya (`0x23 0x21` yani `#!`) bakar.
* Bu satır, çekirdeğe bu dosyadaki komutları hangi yorumlayıcının (interpreter) çalıştıracağını bildirir:

```bash
#!/bin/bash
```

---

## 2. İlk Betiği Yazma, Yetkilendirme ve Çalıştırma Adımları

Bir shell betiğini çalıştırmak 3 temel adımdan oluşur:

1. Betik dosyasını oluşturup içine komutları yazmak
2. Dosyaya çalıştırma izni (`+x`) vermek
3. Doğru yol (`./`) belirterek çalıştırmak

### Uygulama

```bash
# 1. Metin editörü (nano/vim) veya cat ile betik oluşturun:
cat << 'EOF' > merhaba.sh
#!/bin/bash

echo "Merhaba İşletim Sistemleri Dersi!"
echo "Şu anki kullanıcı: $(whoami)"
echo "Çalışılan dizin: $(pwd)"
EOF

# 2. Dosyanın mevcut izinlerini inceleyin:
ls -l merhaba.sh
# Görüntü: -rw-r--r-- (Henüz çalıştırma izni yok!)

# 3. Dosyaya çalıştırma (execute) yetkisi verin:
chmod +x merhaba.sh

# Tekrar izinleri kontrol edin:
ls -l merhaba.sh
# Görüntü: -rwxr-xr-x (x harfi geldi, dosya çalıştırılabilir)

# 4. Betiği çalıştırın:
./merhaba.sh
```

> **Neden `./merhaba.sh` yazıyoruz?**
> Linux güvenlik gereği mevcut dizini (`.`) varsayılan arama yolu olan `PATH` ortam değişkenine eklemez. Çekirdeğe "bu komutu sistem dizinlerinde değil, tam olarak bulunduğum mevcut dizinde ara" demek için `./` ön eki kullanılır.

---

## 3. Temel Kavramlar ve Sözdizimi (Syntax)

### 3.1 `echo` ile Çıktı Üretme

Ekrana metin, değişken veya komut çıktısı basmak için kullanılır.

* `-e`: Özel kaçış karakterlerini (`\n` yeni satır, `\t` sekme) etkinleştirir.
* `-n`: Satır sonuna otomatik yeni satır (`\n`) eklenmesini engeller.

```bash
echo "Standart çıktı"
echo -e "İlk Satır\nİkinci Satır\tSekme Boşluğu"
echo -n "Bu metnin ardına imleç alt satıra geçmez: "
echo "Devamı burada."
```

### 3.2 Değişkenler (Variables)

Bash'te değişken tanımlarken **eşittir (`=`) işaretinin sağına ve soluna asla boşluk bırakılmaz**.

```bash
# Doğru tanımlama:
DERS_ADI="İşletim Sistemleri"
HAFTA=2

# Yanlış tanımlama (Hata verir!):
# DERS_ADI = "İşletim Sistemleri"

# Değişkeni okuma ($ işareti ile):
echo "Ders: $DERS_ADI, Hafta: $HAFTA"

# Komut çıktısını değişkene atama (Command Substitution):
MEVCUT_TARIH=$(date +%Y-%m-%d)
echo "Bugünün tarihi: $MEVCUT_TARIH"
```

### 3.3 Özel Parametreler ve Argümanlar

Dışarıdan parametre alan betiklerde bu argümanlar özel değişkenlerle tutulur:

* `$0`: Betiğin kendi adı
* `$1`, `$2`, `$3`: 1., 2. ve 3. parametreler
* `$#`: Verilen toplam parametre sayısı
* `$?`: Son çalışan komutun çıkış kodu (Exit Status) — `0` başarı, `0` dışı hata demektir.
* `$$`: Betiği çalıştıran sürecin PID numarası.

---

## 4. Karar Mekanizmaları: `if - elif - else`

Bash'te koşullar genellikle çift köşeli parantez `[[ ... ]]` veya tek köşeli parantez `[ ... ]` içinde test edilir. Parantezlerin iç sınırlarında **mutlaka boşluk olmalıdır**.

### Temel Karşılaştırma Operatörleri

| Sayısal Karşılaştırma | Metin (String) Karşılaştırma | Dosya Sistemi Testleri |
| --- | --- | --- |
| `-eq` (Eşit - Equal) | `==` (Eşit) | `-f dosya` (Normal dosya var mı?) |
| `-ne` (Eşit değil - Not Equal) | `!=` (Eşit değil) | `-d dizin` (Dizin var mı?) |
| `-gt` (Büyük - Greater Than) | `-z metin` (Metin uzunluğu 0 mı?) | `-e dosya` (Dosya/klasör mevcut mu?) |
| `-lt` (Küçük - Less Than) | `-n metin` (Metin dolu mu?) | `-r / -w / -x` (Okuma/Yazma/Çalıştırma izni var mı?) |
| `-ge` (Büyük veya eşit) | | |
| `-le` (Küçük veya eşit) | | |

### Örnek Betik: Dosya ve Sayı Kontrolü (`kontrol.sh`)

```bash
cat << 'EOF' > kontrol.sh
#!/bin/bash

# Dışarıdan girilen parametre kontrolü
if [ $# -lt 1 ]; then
    echo "Hata: Lütfen bir dosya veya dizin yolu girin!"
    echo "Kullanım: $0 <dosya_veya_dizin>"
    exit 1
fi

HEDEF=$1

if [ -f "$HEDEF" ]; then
    echo "[DOSYA] '$HEDEF' normal bir dosyadır."
    if [ -x "$HEDEF" ]; then
        echo "-> Bu dosya çalıştırılabilir (+x) iznine sahiptir."
    else
        echo "-> Bu dosya çalıştırılamaz (-x)."
    fi
elif [ -d "$HEDEF" ]; then
    echo "[DİZİN] '$HEDEF' bir klasördür."
else
    echo "[BULUNAMADI] '$HEDEF' mevcut değil."
fi
EOF

chmod +x kontrol.sh
./kontrol.sh /etc/passwd
./kontrol.sh /tmp
```

---

## 5. Döngüler: `for` ve `while`

### 5.1 `for` Döngüsü

Belirli bir eleman listesi, sayı aralığı veya komut çıktısı üzerinde sırayla işlem yapmak için kullanılır.

#### Standart Liste Döngüsü

```bash
for ELEMAN in disk ram cpu ag; do
    echo "Denetlenen birim: $ELEMAN"
done
```

#### Sayı Dizisi (Range) ile Döngü

```bash
# 1'den 5'e kadar sayma:
for i in {1..5}; do
    echo "Adım numarası: $i"
done

# C tarzı for döngüsü:
for ((i=1; i<=3; i++)); do
    echo "Döngü sayacı: $i"
done
```

#### Komut Çıktısı veya Dosya Listesi Üzerinde Dönme

```bash
# Bulunulan dizindeki tüm .sh uzantılı dosyaları bulup izinlerini listeleme:
for dosya in *.sh; do
    echo "Betik bulundu: $dosya"
done
```

### 5.2 `while` Döngüsü

Belirtilen koşul doğru (`true`) olduğu sürece çalışmaya devam eder.

```bash
SAYAC=3
while [ $SAYAC -gt 0 ]; do
    echo "Geri sayım: $SAYAC"
    sleep 1
    SAYAC=$((SAYAC - 1))
done
echo "Süre doldu!"
```

---

## 6. Kapsamlı Sistem Programlama Örneği: Süreç Gözetleme Betiği

Aşağıdaki betik; öğrendiğimiz `if`, `for`, `echo`, parametreler ve komut çalıştırma mekanizmalarını birleştirerek bir sistem yöneticisi (sysadmin) aracı simüle eder.

### `surec_gozlemci.sh`

```bash
cat << 'EOF' > surec_gozlemci.sh
#!/bin/bash

# Shebang ile Bash çağrıldı
# PID ve temel argüman bilgisi
echo "============================================="
echo "   SİSTEM VE SÜREÇ GÖZETLEME BETİĞİ"
echo "   Çalıştıran PID: $$ | Kullanıcı: $(whoami)"
echo "============================================="

# 1. Kontrol edilecek kritik süreç isimleri listesi
HEDEF_SURECLER=("bash" "cron" "sshd" "systemd" "bilinmeyen_surec")

echo -e "\n[ADIM 1] Kritik Süreçler Denetleniyor..."

for SUREC in "${HEDEF_SURECLER[@]}"; do
    # pgrep komutu sürecin PID'sini bulmaya çalışır
    # Çıktı /dev/null'a yönlendirilir, sadece durum kodu kontrol edilir
    pgrep -x "$SUREC" > /dev/null 2>&1
    DURUM=$?

    if [ $DURUM -eq 0 ]; then
        PID_LISTESI=$(pgrep -d ',' -x "$SUREC")
        echo "[AKTİF] $SUREC çalışıyor (PID: $PID_LISTESI)"
    else
        echo "[PASİF] $SUREC sistemde aktif değil!"
    fi
done

# 2. Sistem Bellek Kullanımı Kontrolü
echo -e "\n[ADIM 2] Bellek Durumu İnceleniyor..."

BELLEK_KULLANIM_YUZDESI=$(free | grep Mem | awk '{print int($3/$2 * 100)}')
echo "Mevcut RAM Kullanımı: %$BELLEK_KULLANIM_YUZDESI"

if [ "$BELLEK_KULLANIM_YUZDESI" -gt 85 ]; then
    echo "UYARI: Bellek tüketimi kritik seviyede!"
else
    echo "DURUM: Bellek kullanımı güvenli sınırlarda."
fi

echo -e "\nİşlem tamamlandı."
EOF

# Yetkilendirme ve çalıştırma
chmod +x surec_gozlemci.sh
./surec_gozlemci.sh
```

---

## 7. Betik Hata Ayıklama (Debugging) İpuçları

Bir shell betiğinde hata aldığınızda kodun hangi satırda tıkandığını görmek için betiği debug modunda çalıştırabilirsiniz:

```bash
# -x parametresi her satırı çalıştırılmadan hemen önce ekrana basar (Trace modu)
bash -x ./surec_gozlemci.sh

# Betik içerisinden belirli bir bloğu debug etmek için:
set -x  # Hata ayıklamayı başlatır
# ... incelenecek kodlar ...
set +x  # Hata ayıklamayı kapatır
```

---

## 8. Laboratuvar Alıştırmaları

1. **Girdi Denetimi:** Kullanıcıdan parametre olarak bir dosya adı alan; eğer dosya varsa satır sayısını (`wc -l`), yoksa "Dosya bulunamadı" uyarısı veren `satir_say.sh` betiğini yazın.
2. **Yedekleme Döngüsü:** Bir `for` döngüsü kullanarak mevcut dizindeki tüm `.txt` dosyalarının birer kopyasını `.bak` uzantısıyla oluşturan bir betik yazın (`dosya.txt` -> `dosya.txt.bak`).
3. **Süreç Sinyali:** Parametre olarak verilen PID numarasına sahip sürecin çalışıp çalışmadığını `kill -0 <PID>` ile test eden bir `pid_kontrol.sh` betiği hazırlayın.
