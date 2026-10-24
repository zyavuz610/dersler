# 1. Oturum
* veri türleri, ilkel veri türleri ve String
  * tamsayı
    * int, byte, short, long
  * char
    * char -> 'A'
  * ondalıklı
    * float -> float abc = 345.6F;
    * double pi = 3.14;
  * metin
    * String str = "Merhaba, şimdi mola zamanı!";
* değişken tanımlama kuralları
  * büyük-küçük harf farklıdır -> Sayi1 ile sayi1 farklı değişkendir.
  * özel karakterler kullanılamaz, _ hariç -> sayi-1 hatalı
  * rakam en başa gelemez, onun dışında harf, rakam ve _ kullanılabilir -> 1sayi hatalı
  * keyword olan ifadeler değişken olmaz -> for adında bir değişken olamaz, for_ olabilir.
* matematiksel 4 işlem
* sizlere örnek problem

# 2. Oturum
* atama operatörü, =, değer -> değişken
* Tür dönüştürme operatörü, sayı -> sayı
  * (type)değişken
* aritmetik operatörler, sayı -> sayı
  * +, -, *, /, %, ++, --, prefix, postfix 
* karşılaştırma operatörleri, sayı -> boolean
  * <, >, <=, >=, ==, !=
* mantıksal operatörleri, boolean -> boolean
  * &&, ||, !

# Ödevler
* kitaptaki 1. ünitedeki alıştırma soruları
------------
* Yarıçapı (r) 7.0 cm olan bir dairenin alanını ve çevresini hesaplayınız.
------------
* Kilosu 68.5 kg ve boyu 1.72 metre olan bir kişinin Vücut Kitle İndeksini hesaplayınız.
------------
* Bir aile arabayla 450 km yol gidecektir.
  *   - Araba 100 kilometrede ortalama 6.4 litre benzin yakmaktadır.
  *   - Benzinin litre fiyatı 42.50 TL'dir.
  * İstenenler:
      *   1) Yol boyunca harcanacak toplam benzin miktarını (litre) bulunuz.
      *   2) Bu yolculuk için ödenecek toplam yakıt tutarını (TL) hesaplayınız.
      * Formül: Harcanan Benzin = (Gidilen Yol / 100) * 6.4
------------
* Problem: Bir dik üçgenin dik kenarları a = 6 cm, b = 8 cm ve hipotenüsü c = 10 cm'dir.
  * İstenenler:
    *   1) Üçgenin Çevresi = a + b + c
    *   2) Dik Üçgenin Alanı = (a * b) / 2
  * Sonuçları tam ve anlaşılır bir açıklama ile ekrana yazdırınız.
------------
* Problem: Bir öğrencinin kumbarasında:
         *   - 24 adet 1 TL
         *   - 18 adet 50 Kuruş (0.50 TL)
         *   - 32 adet 25 Kuruş (0.25 TL)
         *   - 14 adet 10 Kuruş (0.10 TL) madeni para vardır.
         * İstenen: Kumbarada biriken toplam parayı TL cinsinden ondalıklı olarak hesaplayıp ekrana yazdırınız.
------------
* Problem: Bir işçi bir fabrikada toplam 820 gün çalışmıştır.
 *   - 1 yılı 365 gün kabul ediniz.
 *   - 1 ayı 30 gün kabul ediniz.
 * Bu işçinin çalıştığı süreyi bölme (/) ve mod (%) operatörlerini kullanarak;
 * "... Yıl, ... Ay, ... Gün" şeklinde ekrana yazdıran programı kodlayınız.
