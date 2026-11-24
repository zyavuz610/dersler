# konular
---
* if, else
* import java.util.Scanner; ile kullanıcıdan giriş almak,
* örnek program: not hesaplama
* sözlü 90 ve üstü ise +%10 verelim

```
/*
 * Ağırlık Dağılımı:
 * Ara Sınav : %30
 * Sözlü     : %20
 * Final     : %50
 */
```

Başka bir Soru:
* Sosyal medya platformlarında (örneğin Instagram) paylaşılan gönderilerin altında "şimdi", "15 dakika önce", "3 saat önce", "5 gün önce" veya "2 hafta önce" gibi dinamik zaman ifadeleri yer alır.Kullanıcıdan veya sistemden milisaniye cinsinden iki Unix zaman damgası (gönderinin paylaşıldığı an ve şimdiki zaman) alan bir Java programı yazınız.Programınız, aradaki farkı hesaplayarak Instagram benzeri en uygun tekil zaman etiketini aşağıdaki kurallara göre ekrana yazdırmalıdır:
  * Aradaki fark 1 dakikadan azsa > "şimdi"
  * 1 saatten azsa > "X dakika önce" (veya kısa format: "Xd")
  * 24 saatten azsa > "X saat önce" (veya kısa format: "Xs")
  * 7 günden azsa > "X gün önce" (veya kısa format: "Xg")
  * 7 gün ve üzerindeyse > "X hafta önce" (veya kısa format: "Xh")


```
// Simüle edilen an: Şimdiki zaman damgası
long suAn = 1729500000000L;

// Farklı senaryolar için geçmiş paylaşım zamanları
long post1Paylasim = suAn - (25 * 1000L);                      // 25 saniye önce
long post2Paylasim = suAn - (18 * 60 * 1000L);                 // 18 dakika önce
long post3Paylasim = suAn - (4 * 60 * 60 * 1000L);             // 4 saat önce
long post4Paylasim = suAn - (3 * 24 * 60 * 60 * 1000L);        // 3 gün önce
long post5Paylasim = suAn - (18 * 24 * 60 * 60 * 1000L);       // 18 gün (2 hafta) önce
```


# açıklamalar
- herkes class ismi Grade olan bir program yazsın.
- `java Grade2` yazarak çalıştıracağım

# ödev
* bir tırın genişlik, yükseklik ve derinliğini kullanıcıdan al.
* bir kutunun genişlik, yükseklik ve derinliğini kullanıcıdan al.
* tır bu kutulardan kaç adet alabilir.
* girilen değerler 0 dan küçük olamaz, kontrol et.
* kutu boyutları tırın herhangi bir boyutundan büyük olamaz, kontrol et.
* kodu anlatabilene ödeve ek +10puan
