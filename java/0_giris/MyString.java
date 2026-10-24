public class MyString {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("         JAVA STRING VE METOTLARI DEMO            ");
        System.out.println("==================================================");

        // ==========================================================
        // 1. BÖLÜM: STRING TANIMLAMA, BELLEK YAPISI VE EŞİTLİK KONTROLÜ
        // String değişmezdir (immutable). Havuz (Pool) vs new farkı.
        // ==========================================================
        System.out.println("\n--- 1. TANIMLAMA, STRING POOL VE EŞİTLİK (== vs equals) ---");
        String s1 = "Java";               // String Pool (Sabit Havuzu) içinde saklanır
        String s2 = "Java";               // Havuzdaki mevcut adresi gösterir
        String s3 = new String("Java");   // Heap bellekte ZORLA yeni nesne açar

        // '==' bellek referansını (adresini), '.equals()' ise metin içeriğini karşılaştırır:
        System.out.println("s1 == s2 (Aynı havuz adresi mi?)        : " + (s1 == s2));       // true
        System.out.println("s1 == s3 (Farklı nesne adresleri mi?)   : " + (s1 == s3));       // false (TUZAK!)
        System.out.println("s1.equals(s3) (İçerikleri aynı mı?)     : " + s1.equals(s3));    // true (DOĞRU YÖNTEM)
        System.out.println("s1.equalsIgnoreCase(\"JAVA\")           : " + s1.equalsIgnoreCase("JAVA")); // true (Büyük/küçük harf yok say)

        // ==========================================================
        // 2. BÖLÜM: UZUNLUK VE KARAKTER ERİŞİM METOTLARI
        // length(), charAt(), toCharArray()
        // ==========================================================
        System.out.println("\n--- 2. UZUNLUK VE KARAKTER ERİŞİMİ ---");
        String metin = "Programlama";

        int uzunluk = metin.length(); // Karakter sayısını verir (11)
        System.out.println("Metin: \"" + metin + "\" -> Uzunluk (length) : " + uzunluk);

        // Belirli indisteki karakteri alma (0-indeksli)
        char ilkHarf = metin.charAt(0);              // 'P'
        char sonHarf = metin.charAt(metin.length() - 1); // 'a'
        System.out.println("İlk karakter (charAt(0))               : " + ilkHarf);
        System.out.println("Son karakter (charAt(length - 1))       : " + sonHarf);

        // Karakter dizisine (char[]) dönüştürme
        char[] karakterDizisi = metin.toCharArray();
        System.out.println("toCharArray() ile 4. indis karakteri     : " + karakterDizisi[4]); // 'r'

        // ==========================================================
        // 3. BÖLÜM: ALT METİN KOPARMA (SUBSTRING)
        // substring(baslangic), substring(baslangic, bitis)
        // DİKKAT: Bitiş indisi DAHİL DEĞİLDİR! [baslangic, bitis)
        // ==========================================================
        System.out.println("\n--- 3. ALT METİN KOPARMA (SUBSTRING) ---");
        String sehir = "Kahramanmaraş";

        String parca1 = sehir.substring(8);      // 8. indisten sonuna kadar: "maraş"
        String parca2 = sehir.substring(0, 8);   // 0'dan 8'e kadar (8 dahil değil): "Kahraman"
        System.out.println("Orijinal Metin                     : " + sehir);
        System.out.println("sehir.substring(8)                 : " + parca1);
        System.out.println("sehir.substring(0, 8)              : " + parca2);

        // ==========================================================
        // 4. BÖLÜM: ARAMA VE KONUM BULMA METOTLARI
        // indexOf(), lastIndexOf(), contains(), startsWith(), endsWith()
        // ==========================================================
        System.out.println("\n--- 4. ARAMA VE KONTROL METOTLARI ---");
        String cumle = "Java çok güçlü ve Java çok popüler bir dildir.";

        // indexOf: Baştan arar, bulamazsa -1 döner
        int ilkJavaKonumu = cumle.indexOf("Java");         // 0
        int ikinciJavaKonumu = cumle.indexOf("Java", 5);    // 5. indisten sonraki ilk Java (18)
        int sonJavaKonumu = cumle.lastIndexOf("Java");     // 18 (Sondan başa doğru arar)

        System.out.println("cumle.indexOf(\"Java\")              : " + ilkJavaKonumu);
        System.out.println("cumle.indexOf(\"Java\", 5)           : " + ikinciJavaKonumu);
        System.out.println("cumle.lastIndexOf(\"Java\")          : " + sonJavaKonumu);
        System.out.println("cumle.indexOf(\"Python\")            : " + cumle.indexOf("Python")); // -1 (Yok)

        // contains: İçeriyor mu?
        boolean iceriyorMu = cumle.contains("güçlü");
        System.out.println("cumle.contains(\"güçlü\")             : " + iceriyorMu); // true

        // startsWith / endsWith: İle mi başlıyor / bitiyor?
        System.out.println("cumle.startsWith(\"Java\")           : " + cumle.startsWith("Java")); // true
        System.out.println("cumle.endsWith(\"dildir.\")          : " + cumle.endsWith("dildir.")); // true

        // ==========================================================
        // 5. BÖLÜM: BÜYÜK/KÜÇÜK HARF VE BOŞLUK DÜZENLEME METOTLARI
        // toUpperCase(), toLowerCase(), trim(), strip()
        // ==========================================================
        System.out.println("\n--- 5. HARF DÖNÜŞÜMÜ VE BOŞLUK TEMİZLEME ---");
        String hamVeri = "   Zafer Yavuz   ";

        System.out.println("Orijinal                           : [\"" + hamVeri + "\"]");
        System.out.println("toUpperCase()                      : " + hamVeri.toUpperCase());
        System.out.println("toLowerCase()                      : " + hamVeri.toLowerCase());
        System.out.println("trim() (Baştaki/sondaki boşluklar) : [\"" + hamVeri.trim() + "\"]");

        // String'in değişmezlik (immutability) kanıtı:
        System.out.println("Orijinal değişken değişti mi?      : [\"" + hamVeri + "\"] (Değişmez!)");

        // ==========================================================
        // 6. BÖLÜM: DEĞİŞTİRME METOTLARI (REPLACE)
        // replace(eski, yeni), replaceAll(regex, yeni)
        // ==========================================================
        System.out.println("\n--- 6. METİN DEĞİŞTİRME (REPLACE) ---");
        String telefon = "0532-111-22-33";

        String tiresiz = telefon.replace("-", ""); // Tüm tireleri kaldır
        System.out.println("Orijinal Telefon                   : " + telefon);
        System.out.println("telefon.replace(\"-\", \"\")          : " + tiresiz);

        String yazi = "Elma, armut, elma ve muz.";
        String yeniYazi = yazi.replace("elma", "çilek"); // Harf duyarlı değişim
        System.out.println("replace(\"elma\", \"çilek\")          : " + yeniYazi);

        // ==========================================================
        // 7. BÖLÜM: BÖLME VE BİRLEŞTİRME (SPLIT VE JOIN)
        // split(ayrac), String.join(ayrac, elemanlar)
        // ==========================================================
        System.out.println("\n--- 7. BÖLME (SPLIT) VE BİRLEŞTİRME (JOIN) ---");
        String dillerMetni = "Java,Python,C++,JavaScript,Rust";

        // split() ile metni diziye bölme:
        String[] dilDizisi = dillerMetni.split(",");
        System.out.println("split(\",\") ile ayrılan eleman sayısı: " + dilDizisi.length);
        for (int i = 0; i < dilDizisi.length; i++) {
            System.out.println("  [" + i + "] " + dilDizisi[i]);
        }

        // String.join() ile dizi elemanlarını araya ayraç koyarak tek metin yapma:
        String birlestirilmis = String.join(" - ", dilDizisi);
        System.out.println("String.join(\" - \", ...)            : " + birlestirilmis);

        // concat() metodu ile birleştirme:
        String selamlama = "Merhaba ".concat("Dünya!");
        System.out.println("concat() sonucu                    : " + selamlama);

        // ==========================================================
        // 8. BÖLÜM: BOŞLUK VE DOLULUK KONTROLÜ
        // isEmpty(), isBlank()
        // ==========================================================
        System.out.println("\n--- 8. BOŞLUK KONTROLÜ (isEmpty vs isBlank) ---");
        String bos = "";
        String sadeceBosluk = "    ";

        // isEmpty(): Uzunluk 0 ise true döner
        System.out.println("\"\".isEmpty()                         : " + bos.isEmpty());           // true
        System.out.println("\"    \".isEmpty()                     : " + sadeceBosluk.isEmpty());  // false (4 boşluk karakteri var!)

        // isBlank(): Uzunluk 0 ise VEYA sadece boşluklardan oluşuyorsa true döner (Java 11+)
        System.out.println("\"    \".isBlank()                     : " + sadeceBosluk.isBlank());  // true

        // ==========================================================
        // 9. BÖLÜM: KARŞILAŞTIRMA (COMPARETO) VE TÜR DÖNÜŞÜMÜ (VALUEOF)
        // compareTo(), String.valueOf()
        // ==========================================================
        System.out.println("\n--- 9. ALFABETİK SIRALAMA VE TÜR DÖNÜŞÜMÜ ---");
        String kelime1 = "Ahmet";
        String kelime2 = "Ayşe";

        // compareTo: Sözlük sırasına göre alfabetik farkı verir (0: eşit, negatif: önce, pozitif: sonra)
        int karsilastirma = kelime1.compareTo(kelime2);
        System.out.println("kelime1.compareTo(kelime2)         : " + karsilastirma + " (Ahmet alfabetik olarak Ayşe'den önce)");

        // String.valueOf(): Diğer veri türlerini (sayı, boolean vb.) String'e çevirir:
        int sayi = 2026;
        double pi = 3.14;
        boolean bayrak = true;

        String sayiMetin = String.valueOf(sayi);
        String piMetin = String.valueOf(pi);
        String bayrakMetin = String.valueOf(bayrak);

        System.out.println("String.valueOf(2026)               : \"" + sayiMetin + "\" (Uzunluk: " + sayiMetin.length() + ")");
        System.out.println("String.valueOf(3.14)               : \"" + piMetin + "\"");
        System.out.println("String.valueOf(true)               : \"" + bayrakMetin + "\"");

        System.out.println("\n==================================================");
        System.out.println("        STRING METOTLARI DEMO TAMAMLANDI          ");
        System.out.println("==================================================");
    }
}
