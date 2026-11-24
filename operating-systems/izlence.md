# 1. hafta ne işlemiştik
* işletim sistemine giriş - Hafta 1_word dosyası
* linux - windows karşılaştırması
* replit.com üzerinden linux üzerinde çalışmaya başladık.

# 2. hafta
* temel shell bilgi komutları -> uname, hostname, whoami, pwd
* dosya ve dizin işlemleri
  * `ls`, `ls -a`, `ls -l`, `ls -al`
  * `cd` ve .. klasörü
  * `mkdir`
  * `rm`, `rmdir`
  * `cp`
  * `mv`
  * `touch` -> boş dosya oluştur
  * `tree`

# 3. hafta 
* chmod
* f1 adında bir dosya oluştur ve diğer kullanıcılara yazma yetkisi ver
* önce f1 için izinleri bulalım
  * -rw-r--r--
  * -110100100 ->(110)(100)(100) -> 644
  * vermek istediğim yetki
  * -rw-rw-rw-
  * -110110110 -> (110)(110)(110) -> 666
  * yeni vermek istediğim yetki
  * ---------- -> 000

## process komutları
* sleep 300 &
* jobs
* ps aux; tüm processleri görüntülüyor
* program ile process arasındaki fark

## uygulama
* `h03` adında bir klasör oluşturun bu sizin bu haftaki çalışma klasörünüz
* `h03` içine `d1` `d2` `d3` isminde 3 adet dizin oluşturun
* `d1` dizini içine `f1` ve `f2` adında 2 boş dosya oluşturun
* `cat /etc/hosts > f1` komutunu çalıştırın.
* `cat /etc/passwd > f2` komutunu çalıştırın.
* `ls -al` ile dosyaların konum ve boyutlarını kontrol edin.
* `f1` dosyasını `d2` dizinine kopyalayın ve adını `f1d2` yapın
* `f2` dosyasını `d3`dizinine kopyalayın ve adını `f2d3` yapın.
* `d1` dizinini silin.
* `d3/f2d3` dosyasını silin

# 4. hafta
* linux komutları tekrar edildi
* softlink, hardlink
* işletim sistemleri yapıları (dual mode, monolitik, microkernel)
* sistem çağrıları

# 5. hafta
* süreç ve program tanımı
* süreç bellek yapısı
* süreç durumları ve geçiş mantığı (5 durum)
* sürec PCB
* süreç bağlam değiştirme

# 6. hafta
* derse gelen olmadı
* thread konusu işlenecekti,
  * process görüntüle, her biri ayrı bellek
  * aynı process içinde thread çalıştır
  * thread fonk. tanımla
  * thread fonksiyonuna parametre aktar

