package 2_operatörler;

public public class OperatorlerVeOncelik {

    public static void main(String[] args) {

        /* =========================================================================
         * 1. ARİTMETİK OPERATÖRLER (+, -, *, /, %)
         * ========================================================================= */
        System.out.println("--- 1. ARİTMETİK OPERATÖRLER ---");
        int sayi1 = 15;
        int sayi2 = 4;

        System.out.println("Toplama (15 + 4) : " + (sayi1 + sayi2));
        System.out.println("Çıkarma (15 - 4) : " + (sayi1 - sayi2));
        System.out.println("Çarpma  (15 * 4) : " + (sayi1 * sayi2));
        // Tamsayı bölmesinde küsurat atılır (15 / 4 = 3)
        System.out.println("Bölme   (15 / 4) : " + (sayi1 / sayi2));
        // Modülüs: Bölümden kalanı verir (15 % 4 = 3)
        System.out.println("Mod Alma (15 % 4): " + (sayi1 % sayi2));


        /* =========================================================================
         * 2. TEKLİ (UNARY) OPERATÖRLER (++ ve --)
         * Ön-arttırma (++x) ile sonradan-arttırma (x++) farkı
         * ========================================================================= */
        System.out.println("\n--- 2. TEKLİ (UNARY) OPERATÖRLER ---");
        int a = 5;
        // Sonradan arttırma (post-increment): Önce mevcut değer yazdırılır, sonra 'a' 1 artar.
        System.out.println("a++ ifadesinin o anki değeri : " + (a++)); // Ekrana 5 basar, bellek 6 olur
        System.out.println("a++ işleminden sonra a değeri: " + a);     // Ekrana 6 basar

        int b = 5;
        // Ön-arttırma (pre-increment): Önce 'b' 1 artar, sonra yeni değer kullanılır.
        System.out.println("++b ifadesinin o anki değeri : " + (++b)); // Ekrana 6 basar, bellek 6 olur


        /* =========================================================================
         * 3. İLİŞKİSEL VE MANTIKSAL OPERATÖRLER (==, !=, >, <, >=, <=, &&, ||, !)
         * ========================================================================= */
        System.out.println("\n--- 3. İLİŞKİSEL VE MANTIKSAL OPERATÖRLER ---");
        int yas = 20;
        boolean ehliyetVarMi = true;

        // Mantıksal VE (&&): İki koşul da true ise sonuç true döner.
        boolean aracSurebilirMi = (yas >= 18) && ehliyetVarMi;
        System.out.println("Araç sürebilir mi? (yas >= 18 && ehliyet): " + aracSurebilirMi);

        // Mantıksal VEYA (||): Koşullardan en az biri true ise sonuç true döner.
        boolean indirimliBilet = (yas < 18) || (yas > 65);
        System.out.println("İndirimli bilet alabilir mi? (yas < 18 || yas > 65): " + indirimliBilet);

        // Mantıksal DEĞİL (!): Boolean değeri tersine çevirir.
        System.out.println("!ehliyetVarMi : " + (!ehliyetVarMi));


        /* =========================================================================
         * 4. BİLEŞİK ATAMA VE TERNARY (ÜÇLÜ) OPERATÖRLER
         * ========================================================================= */
        System.out.println("\n--- 4. ATAMA VE TERNARY OPERATÖRÜ ---");
        int puan = 50;
        puan += 10; // puan = puan + 10 anlamına gelir (Bileşik Atama)
        System.out.println("puan += 10 sonucu: " + puan);

        // Ternary (koşul ? doğruysa_değer : yanlışsa_değer)
        String durum = (puan >= 50) ? "Geçti" : "Kaldı";
        System.out.println("Ternary Durum Sonucu: " + durum);


        /* =========================================================================
         * 5. OPERATÖR ÖNCELİĞİ (OPERATOR PRECEDENCE) GÖSTERİMİ
         * Öncelik Sırası:
         * 1. Parantez içleri ()
         * 2. Çarpma (*), Bölme (/), Mod (%)
         * 3. Toplama (+), Çıkarma (-)
         * 4. İlişkisel Operatörler (<, >, <=, >=)
         * 5. Eşitlik Operatörleri (==, !=)
         * 6. Mantıksal VE (&&)
         * 7. Mantıksal VEYA (||)
         * 8. Atama (=, +=, vb.)
         * ========================================================================= */
        System.out.println("\n--- 5. OPERATÖR ÖNCELİĞİ ÖRNEKLERİ ---");

        // ÖRNEK 1: Çarpma / Bölme, Toplama / Çıkarmadan önce gelir
        // Adım 1: 5 * 2 = 10
        // Adım 2: 10 + 10 = 20
        int sonuc1 = 10 + 5 * 2;
        System.out.println("10 + 5 * 2 = " + sonuc1 + " (Önce çarpma yapıldı)");

        // Parantez önceliği değiştirir:
        // Adım 1: (10 + 5) = 15
        // Adım 2: 15 * 2 = 30
        int sonuc2 = (10 + 5) * 2;
        System.out.println("(10 + 5) * 2 = " + sonuc2 + " (Parantez önceliği aldı)");


        // ÖRNEK 2: Aritmetik, Karşılaştırma ve Mantıksal operatörlerin karma kullanımı
        // İfade: 5 + 3 * 2 > 10 && 4 + 2 == 6
        // Adım 1 (Çarpma)      : 3 * 2 = 6  -->  5 + 6 > 10 && 4 + 2 == 6
        // Adım 2 (Toplama)     : 5 + 6 = 11, 4 + 2 = 6  -->  11 > 10 && 6 == 6
        // Adım 3 (Büyüktür >)  : 11 > 10 --> true  -->  true && 6 == 6
        // Adım 4 (Eşittir ==)  : 6 == 6  --> true  -->  true && true
        // Adım 5 (Mantıksal &&): true && true --> true
        boolean karmasikSonuc = 5 + 3 * 2 > 10 && 4 + 2 == 6;
        System.out.println("5 + 3 * 2 > 10 && 4 + 2 == 6 ifadesinin sonucu: " + karmasikSonuc);


        // ÖRNEK 3: Mantıksal VE (&&), Mantıksal VEYA'dan (||) daha önceliklidir!
        // İfade: true || false && false
        // Eğer soldan sağa çalışsaydı: (true || false) -> true, ardından true && false -> false çıkardı.
        // Ancak && öncelikli olduğu için: (false && false) -> false, ardından true || false -> true çıkar.
        boolean mantikOnceligi = true || false && false;
        System.out.println("true || false && false = " + mantikOnceligi + " (&&, || operatöründen daha önceliklidir)");
    }
} {
    
}
