/**
 * DERS 1: DEĞİŞKENLER VE VERİ TÜRLERİ (VARIABLES & DATA TYPES)
 * 
 * Konu Özeti:
 *   1. Değişken Nedir?
 *   2. Java'da Veri Türleri (İlkel Türler ve String)
 *   3. Değişken Tanımlama Kuralları ve İsimlendirme Standartları
 *   4. Değişkenler ile 4 İşlem ve Matematiksel Örnek Problemler
 *   5. Öğrenciler İçin Algoritmik Programlama Ödevleri
 */

public class DegiskenlerVeVeriTurleri {

    public static void main(String[] args) {

        // =====================================================================
        // BÖLÜM 1: DEĞİŞKEN NEDİR?
        // =====================================================================
        // Değişkeni bir "ayakkabı kutusu" veya "kavanoz" gibi düşünebilirsin.
        // Bilgisayarın hafızasında (RAM) geçici bir alan açarız,
        // bu alana bir İSİM (etiket) veririz ve içine bir DEĞER koyarız.
        // 
        // Formülümüz:
        // [Veri Türü] [Değişken Adı] = [Değer];
        // =====================================================================

        //-------------------
        // 1. TAM SAYI TÜRÜ: int (Integer - Tam Sayı)
        // Günlük hayatta virgülü olmayan tüm sayılar için en çok "int" kullanılır.
        // Hafızada 4 bayt (32 bit) yer kaplar. (-2 milyar ile +2 milyar arası)
        int ogrenciYasi = 16;
        int sinifMevcudu = 28;

        System.out.println("Öğrencinin Yaşı: " + ogrenciYasi);
        System.out.println("Sınıf Mevcudu: " + sinifMevcudu);

        //-------------------
        // 2. KÜÇÜK TAM SAYILAR: byte ve short
        // byte: Çok küçük sayılar (-128 ile +127 arası). Hafızada sadece 1 bayt yer tutar.
        // short: Orta büyüklükte sayılar (-32.768 ile +32.767 arası). 2 bayt yer tutar.
        byte kardesSayisi = 2;
        short okulNumarasi = 1453;

        System.out.println("Kardeş Sayısı: " + kardesSayisi);
        System.out.println("Okul Numarası: " + okulNumarasi);

        //-------------------
        // 3. ÇOK BÜYÜK TAM SAYILAR: long
        // 2 milyardan daha büyük sayılar için kullanılır. 8 bayt yer kaplar.
        // DİKKAT: Sayının sonuna 'L' veya 'l' harfi eklenmelidir.
        long dunyaNufusu = 8045311447L;
        long turkiyeNufusu = 85372377L;

        System.out.println("Dünya Nüfusu: " + dunyaNufusu);
        System.out.println("Türkiye Nüfusu: " + turkiyeNufusu);

        //-------------------
        // 4. ONDALIKLI (KESİRLİ) SAYILAR: float ve double
        // Boy, kilo, fiyat, ortalama gibi virgüllü sayılar için kullanılır.
        //
        // float: Virgülden sonra 6-7 basamak hassasiyeti vardır. Sonuna 'f' veya 'F' konur.
        // double: Virgülden sonra 15-16 basamak hassasiyeti vardır. Java'da varsayılandır.
        float boy = 1.75f;
        float ekmekFiyati = 10.50f;

        double piSayisi = 3.141592653589793;
        double altinGramFiyati = 2450.75;

        System.out.println("Öğrencinin Boyu: " + boy + " metre");
        System.out.println("Ekmek Fiyatı: " + ekmekFiyati + " TL");
        System.out.println("Pi Sayısı: " + piSayisi);
        System.out.println("Gram Altın: " + altinGramFiyati + " TL");

        //-------------------
        // 5. TEK KARAKTER TÜRÜ: char (Character)
        // Klavyedeki tek bir harf, rakam veya sembolü saklar.
        // DİKKAT: Mutlaka TEK TIRNAK (' ') içerisine yazılır!
        char sube = 'B';
        char notDerecesi = 'A';
        char cinsiyet = 'E'; // E: Erkek, K: Kız
        char sembol = '#';

        System.out.println("Şube: " + sube);
        System.out.println("Not Derecesi: " + notDerecesi);
        System.out.println("Cinsiyet Kodu: " + cinsiyet);
        System.out.println("Özel Sembol: " + sembol);

        //-------------------
        // 6. MANTIKSAL TÜR: boolean
        // Sadece iki değer alabilir: true (doğru) veya false (yanlış).
        // Tıpkı bir elektrik anahtarının açık veya kapalı olması gibidir.
        boolean derseKatildiMi = true;
        boolean odeviniYaptiMi = false;
        boolean yagmurYagiyorMu = true;

        System.out.println("Derse Katıldı mı?: " + derseKatildiMi);
        System.out.println("Ödevini Yaptı mı?: " + odeviniYaptiMi);
        System.out.println("Hava Yağmurlu mu?: " + yagmurYagiyorMu);

        //-------------------
        // 7. METİN (YAZI) TÜRÜ: String
        // Kelimeler, cümleler, isimler veya adresler için kullanılır.
        // DİKKAT 1: String bir ilkel (primitive) tür değil, nesnedir (S harfi büyüktür).
        // DİKKAT 2: Mutlaka ÇİFT TIRNAK (" ") içerisine yazılır!
        String ad = "Ali";
        String soyad = "Yılmaz";
        String okulAdi = "Atatürk Anadolu Lisesi";
        String sehir = "Trabzon";

        // Metinleri '+' işareti ile uç uca ekleyebiliriz (Buna Concatenation denir)
        String tamAd = ad + " " + soyad;

        System.out.println("Öğrenci Adı Soyadı: " + tamAd);
        System.out.println("Okulu: " + okulAdi);
        System.out.println("Yaşadığı Şehir: " + sehir);

        //-------------------
        // DEĞİŞKENİN DEĞERİNİ DEĞİŞTİRMEK
        // Değişken adı üstünde "değişebilir".
        // Bir kez tanımlandıktan sonra tekrar türünü (int, double vb.) yazmaya gerek yoktur.
        int puan = 50;
        System.out.println("İlk Puan: " + puan);

        puan = 85; // Kutunun içindeki eski değer (50) silindi, yerine 85 geldi.
        System.out.println("Güncellenen Puan: " + puan);


        // =====================================================================
        // BÖLÜM 2: DEĞİŞKEN TANIMLAMA KURALLARI
        // =====================================================================
        // Java'da değişken ismi verirken uymamız gereken KESİN kurallar vardır:
        //
        // 1. KURAL: Değişken isimleri RAKAMLA BAŞLAYAMAZ!
        //    Geçerli:  sayi1, not2, ogrenci3
        //    GEÇERSİZ: 1sayi, 2not (Java hata verir!)
        //
        // 2. KURAL: Değişken ismi BOŞLUK İÇEREMEZ!
        //    Geçerli:  ogrenciNotu, ogrenci_notu
        //    GEÇERSİZ: ogrenci notu (Java bunu iki ayrı kelime sanır!)
        //
        // 3. KURAL: Özel karakterler (+, -, *, /, @, !, %, ?) KULLANILAMAZ!
        //    Sadece alt tire (_) ve dolar işareti ($) kullanılabilir.
        //    Geçerli:  kullanici_adi, toplamTutar, $fiyat
        //    GEÇERSİZ: kullanici-adi, not+, mail@adresi
        //
        // 4. KURAL: Java'nın kendi özel anahtar kelimeleri (Keywords) KULLANILAMAZ!
        //    GEÇERSİZ: class, public, static, void, int, double, true, new
        //
        // 5. KURAL: BÜYÜK/KÜÇÜK HARF DUYARLILIĞI (Case Sensitivity)!
        //    Java için "yas", "Yas" ve "YAS" üç farklı değişkendir.
        //
        // 6. TAVSİYE (Gelenek): camelCase (Deve Hörgücü Kuralı)
        //    Birden fazla kelimeden oluşan isimlerde ilk kelime küçük,
        //    sonraki her kelimenin ilk harfi BÜYÜK yazılır.
        //    Örnek: ogrenciOkulNumarasi, sinavBirinciNotu, gunlukAdimSayisi
        //
        // 7. TAVSİYE: Türkçe karakter (ç, ğ, ı, ö, ş, ü) kullanmamaya özen gösterin.
        //    Kodlarda uluslararası standart İngilizce karakter kümesidir.
        // =====================================================================

        //-------------------
        // Doğru ve Standartlara Uygun Değişken Tanımlama Örnekleri:
        int dogumYili = 2008;
        String anneAdi = "Fatma";
        double aylikHarclik = 750.50;
        boolean ehliyetVarMi = false;

        System.out.println("Doğum Yılı: " + dogumYili);
        System.out.println("Anne Adı: " + anneAdi);
        System.out.println("Aylık Harçlık: " + aylikHarclik + " TL");
        System.out.println("Ehliyet Durumu: " + ehliyetVarMi);


        // =====================================================================
        // BÖLÜM 3: DEĞİŞKENLER ÜZERİNDE 4 İŞLEM VE BASİT PROBLEMLER
        // =====================================================================

        //-------------------
        // PROBLEM 1: İKİ SAYI İLE TEMEL DÖRT İŞLEM VE MOD ALMA
        // Toplama (+), Çıkarma (-), Çarpma (*), Bölme (/), Kalan/Mod Bulma (%)
        int sayi1 = 20;
        int sayi2 = 6;

        int toplam = sayi1 + sayi2;
        int fark = sayi1 - sayi2;
        int carpim = sayi1 * sayi2;
        int tamBolum = sayi1 / sayi2;      // Dikkat: İki int bölünürse sonuç tam sayı çıkar! (20 / 6 = 3)
        int kalan = sayi1 % sayi2;         // Mod operatörü (%): 20'nin 6'ya bölümünden kalan (2)

        // Virgülden sonraki kısmı da görmek için sayılardan en az birini ondalıklı (double) yaparız:
        double gercekBolum = (double) sayi1 / sayi2; // 20.0 / 6 = 3.3333333333333335

        System.out.println("===== 1. PROBLEM: DÖRT İŞLEM =====");
        System.out.println("Sayı 1: " + sayi1 + ", Sayı 2: " + sayi2);
        System.out.println("Toplam (+): " + toplam);
        System.out.println("Fark (-): " + fark);
        System.out.println("Çarpım (*): " + carpim);
        System.out.println("Tam Bölüm (/): " + tamBolum);
        System.out.println("Kalan / Mod (%): " + kalan);
        System.out.println("Hassas/Ondalıklı Bölüm: " + gercekBolum);

        //-------------------
        // PROBLEM 2: DİKDÖRTGENİN ALANINI VE ÇEVRESİNİ HESAPLAMA
        // Formüller:
        // Alan = KisaKenar * UzunKenar
        // Cevre = 2 * (KisaKenar + UzunKenar)
        int kisaKenar = 8;
        int uzunKenar = 14;

        int dikdortgenAlani = kisaKenar * uzunKenar;
        int dikdortgenCevresi = 2 * (kisaKenar + uzunKenar);

        System.out.println("===== 2. PROBLEM: DİKDÖRTGEN ALAN VE ÇEVRE =====");
        System.out.println("Kısa Kenar: " + kisaKenar + " cm");
        System.out.println("Uzun Kenar: " + uzunKenar + " cm");
        System.out.println("Dikdörtgenin Alanı: " + dikdortgenAlani + " cm²");
        System.out.println("Dikdörtgenin Çevresi: " + dikdortgenCevresi + " cm");

        //-------------------
        // PROBLEM 3: ÖĞRENCİ DERS NOTU ORTALAMASI HESAPLAMA
        // Senaryo: 1. Yazılı (%30), 2. Yazılı (%30) ve Sözlü/Performans (%40) etkilidir.
        double sinav1 = 70.0;
        double sinav2 = 85.0;
        double sozlu = 90.0;

        // Ağırlıklı ortalama hesabı
        double donemNotu = (sinav1 * 0.30) + (sinav2 * 0.30) + (sozlu * 0.40);

        System.out.println("===== 3. PROBLEM: NOT ORTALAMASI =====");
        System.out.println("1. Yazılı Sınavı: " + sinav1);
        System.out.println("2. Yazılı Sınavı: " + sinav2);
        System.out.println("Sözlü / Performans: " + sozlu);
        System.out.println("Yıl Sonu Ağırlıklı Ortalama: " + donemNotu);

        //-------------------
        // PROBLEM 4: ALIŞVERİŞ FİŞİ VE KDV HESAPLAMA
        // Senaryo: Bir kırtasiyeden 3 adet defter, 2 adet kalem alıyoruz.
        // Defter fiyatı: 45.0 TL, Kalem fiyatı: 20.0 TL. KDV oranı: %20 (0.20)
        double defterFiyati = 45.0;
        int defterAdedi = 3;

        double kalemFiyati = 20.0;
        int kalemAdedi = 2;

        double kdvsizAraToplam = (defterFiyati * defterAdedi) + (kalemFiyati * kalemAdedi);
        double kdvOrani = 0.20; // %20 KDV
        double kdvTutari = kdvsizAraToplam * kdvOrani;
        double toplamOdenecekTutar = kdvsizAraToplam + kdvTutari;

        System.out.println("===== 4. PROBLEM: MARKET / ALIŞVERİŞ FİŞİ =====");
        System.out.println(defterAdedi + " adet Defter Tutarı: " + (defterFiyati * defterAdedi) + " TL");
        System.out.println(kalemAdedi + " adet Kalem Tutarı: " + (kalemFiyati * kalemAdedi) + " TL");
        System.out.println("KDV Hariç Ara Toplam: " + kdvsizAraToplam + " TL");
        System.out.println("Hesaplanan KDV Tutarı (%20): " + kdvTutari + " TL");
        System.out.println("Ödenecek Genel Toplam: " + toplamOdenecekTutar + " TL");

        //-------------------
        // PROBLEM 5: SICAKLIK BİRİMİ DÖNÜŞTÜRÜCÜ (CELSIUS -> FAHRENHEIT VE KELVIN)
        // Formüller:
        // Fahrenheit = (Celsius * 1.8) + 32
        // Kelvin = Celsius + 273.15
        double dereceCelsius = 25.0; // 25 santigrat derece oda sıcaklığı

        double dereceFahrenheit = (dereceCelsius * 1.8) + 32.0;
        double dereceKelvin = dereceCelsius + 273.15;

        System.out.println("===== 5. PROBLEM: SICAKLIK DÖNÜŞÜMÜ =====");
        System.out.println("Hava Sıcaklığı (Celsius): " + dereceCelsius + " °C");
        System.out.println("Fahrenheit Karşılığı: " + dereceFahrenheit + " °F");
        System.out.println("Kelvin Karşılığı: " + dereceKelvin + " K");

        //-------------------
        // PROBLEM 6: ZAMAN BİRİMİ DÖNÜŞTÜRÜCÜ (SANİYEYİ SAAT, DAKİKA VE SANİYEYE AYIRMA)
        // Senaryo: Koşucu parkuru 3754 saniyede tamamladı.
        // Bu süre kaç saat, kaç dakika ve kaç saniyeye denk gelir?
        // 1 saat = 3600 saniye, 1 dakika = 60 saniye
        int toplamSaniye = 3754;

        int saat = toplamSaniye / 3600;                // 3754 içinde kaç tane 3600 var? -> 1 saat
        int artanSaniye1 = toplamSaniye % 3600;        // Saat çıktıktan sonra kalan saniye -> 154 saniye
        int dakika = artanSaniye1 / 60;                // 154 içinde kaç tane 60 var? -> 2 dakika
        int saniye = artanSaniye1 % 60;                // Dakikalar çıktıktan sonra en son kalan -> 34 saniye

        System.out.println("===== 6. PROBLEM: ZAMAN DÖNÜŞTÜRÜCÜ =====");
        System.out.println("Toplam Süre: " + toplamSaniye + " saniye");
        System.out.println("Sonuç: " + saat + " saat, " + dakika + " dakika, " + saniye + " saniye.");

        //-------------------
        // PROBLEM 7: İKİ DEĞİŞKENİN DEĞERİNİ YER DEĞİŞTİRME (SWAP İŞLEMİ)
        // Senaryo: Elimizde A bardağında "Portakal Suyu", B bardağında "Elma Suyu" var.
        // Bardakların içindekileri birbirine karıştırmadan yer değiştirmek için
        // geçici üçüncü boş bir bardağa (gecici/temp) ihtiyacımız vardır.
        int bardakA = 10;
        int bardakB = 20;

        System.out.println("===== 7. PROBLEM: DEĞİŞKEN TAKASI (SWAP) =====");
        System.out.println("Takastan Önce -> A: " + bardakA + " | B: " + bardakB);

        int geciciKutu = bardakA; // 1. Adım: A'nın değerini güvenli bir kutuya yedekle (geciciKutu = 10)
        bardakA = bardakB;        // 2. Adım: B'nin değerini A'ya aktar (bardakA = 20)
        bardakB = geciciKutu;     // 3. Adım: Yedekteki eski A değerini B'ye koy (bardakB = 10)

        System.out.println("Takastan Sonra -> A: " + bardakA + " | B: " + bardakB);


        // =====================================================================
        // BÖLÜM 4: ÖĞRENCİLER İÇİN PROGRAM YAZMA ÖDEVLERİ
        // =====================================================================
        /*
         * SEVGİLİ ÖĞRENCİLER,
         * Aşağıdaki algoritmik problemleri if (şart) veya döngü (for/while)
         * KULLANMADAN, sadece yukarıda öğrendiğiniz değişkenler, veri türleri 
         * ve matematiksel operatörleri (+, -, *, /, %) kullanarak çözünüz.
         * Her ödev için ayrı bir değişken tanımlayıp sonuçları System.out.println()
         * ile ekrana yazdırınız.
         * 
         * //-------------------
         * ÖDEV 1: DAİRENİN ALANI VE ÇEVRESİ
         * Problem: Yarıçapı (r) 7.0 cm olan bir dairenin alanını ve çevresini hesaplayınız.
         * Formüller:
         *   - Alan = pi * r * r
         *   - Çevre = 2 * pi * r
         * İpucu: pi sayısı için "double pi = 3.14159;" değişkenini kullanınız.
         * 
         * //-------------------
         * ÖDEV 2: VÜCUT KİTLE İNDEKSİ (VKİ) HESAPLAYICI
         * Problem: Kilosu 68.5 kg ve boyu 1.72 metre olan bir kişinin Vücut Kitle İndeksini hesaplayınız.
         * Formül:
         *   - VKİ = Kilo / (Boy * Boy)
         * İpucu: Kilo ve boy için "double" veri türünü tercih ediniz.
         * 
         * //-------------------
         * ÖDEV 3: ARABA YOLCULUĞU YAKIT TÜKETİMİ VE MASRAF HESABI
         * Problem: Bir aile arabayla 450 km yol gidecektir.
         *   - Araba 100 kilometrede ortalama 6.4 litre benzin yakmaktadır.
         *   - Benzinin litre fiyatı 42.50 TL'dir.
         * İstenenler:
         *   1) Yol boyunca harcanacak toplam benzin miktarını (litre) bulunuz.
         *   2) Bu yolculuk için ödenecek toplam yakıt tutarını (TL) hesaplayınız.
         * Formül: Harcanan Benzin = (Gidilen Yol / 100) * 6.4
         * 
         * //-------------------
         * ÖDEV 4: ÜÇGENİN ALANI VE ÇEVRESİ
         * Problem: Bir dik üçgenin dik kenarları a = 6 cm, b = 8 cm ve hipotenüsü c = 10 cm'dir.
         * İstenenler:
         *   1) Üçgenin Çevresi = a + b + c
         *   2) Dik Üçgenin Alanı = (a * b) / 2
         * Sonuçları tam ve anlaşılır bir açıklama ile ekrana yazdırınız.
         * 
         * //-------------------
         * ÖDEV 5: KUMBARADAKİ BOZUK PARALARI HESAPLAMA
         * Problem: Bir öğrencinin kumbarasında:
         *   - 24 adet 1 TL
         *   - 18 adet 50 Kuruş (0.50 TL)
         *   - 32 adet 25 Kuruş (0.25 TL)
         *   - 14 adet 10 Kuruş (0.10 TL) madeni para vardır.
         * İstenen: Kumbarada biriken toplam parayı TL cinsinden ondalıklı olarak hesaplayıp ekrana yazdırınız.
         * 
         * //-------------------
         * ÖDEV 6 (BONUS): GÜN SAYISINI YIL, AY VE GÜNE ÇEVİRME
         * Problem: Bir işçi bir fabrikada toplam 820 gün çalışmıştır.
         *   - 1 yılı 365 gün kabul ediniz.
         *   - 1 ayı 30 gün kabul ediniz.
         * Bu işçinin çalıştığı süreyi bölme (/) ve mod (%) operatörlerini kullanarak;
         * "... Yıl, ... Ay, ... Gün" şeklinde ekrana yazdıran programı kodlayınız.
         * 
         * //-------------------
         * ÖDEV 7 (BONUS): ÜÇ BASAMAKLI BİR SAYININ RAKAMLARI TOPLAMI
         * Problem: int sayi = 584; olarak verilen üç basamaklı sayının
         * birler, onlar ve yüzler basamağını bölme (/) ve mod (%) kullanarak ayrı ayrı bulunuz,
         * ardından bu üç rakamın toplamını hesaplayıp ekrana yazdırınız.
         * Örnek: 584 için Çıktı -> 5 + 8 + 4 = 17 olmalıdır.
         */

    }
}
