public class Operator {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("          JAVA OPERATÖRLERİ DEMO PROGRAMI         ");
        System.out.println("==================================================");

        // ==========================================================
        // 1. BÖLÜM: TEMEL ARİTMETİK OPERATÖRLER (+, -, *, /, %)
        // İki sayısal operand alır ve sonuç olarak sayısal bir değer üretir.
        // ==========================================================
        System.out.println("\n--- 1. TEMEL ARİTMETİK OPERATÖRLER ---");
        int a = 20;
        int b = 6;

        int toplam = a + b;   // Toplama (26)
        int fark = a - b;     // Çıkarma (14)
        int carpim = a * b;   // Çarpma (120)
        int bolumTam = a / b; // Tam sayı bölmesi (20 / 6 = 3, ondalık kısım atılır!)
        double bolumHassas = (double) a / b; // Tür dönüştürme ile hassas bölme (3.333...)
        int kalan = a % b;    // Mod alma (20'nin 6'ya bölümünden kalan: 2)

        System.out.println("a = " + a + ", b = " + b);
        System.out.println("Toplama (a + b)             : " + toplam);
        System.out.println("Çıkarma (a - b)             : " + fark);
        System.out.println("Çarpma (a * b)              : " + carpim);
        System.out.println("Tam Sayı Bölmesi (a / b)    : " + bolumTam);
        System.out.println("Hassas Bölme ((double)a / b): " + bolumHassas);
        System.out.println("Mod / Kalan (a % b)         : " + kalan);

        // ==========================================================
        // 2. BÖLÜM: BİRLİ (UNARY) OPERATÖRLER (+, -, ++, --)
        // Tek bir operand üzerinde işlem yaparlar.
        // ==========================================================
        System.out.println("\n--- 2. BİRLİ OPERATÖRLER (ÖNCE/SONRA ARTIRMA-AZALTMA) ---");
        int sayi = 10;
        System.out.println("Başlangıç değeri sayi = " + sayi);

        // Postfix (Sonradan Artırma): Önce mevcut değer kullanılır, sonra artırılır.
        int sonradanArtir = sayi++;
        System.out.println("sayi++ sonrası dönen değer   : " + sonradanArtir + " (sayi şu an: " + sayi + ")");

        // Prefix (Önceden Artırma): Önce değer artırılır, sonra işleme alınır.
        int oncedenArtir = ++sayi;
        System.out.println("++sayi sonrası dönen değer   : " + oncedenArtir + " (sayi şu an: " + sayi + ")");

        // Postfix Azaltma ve Prefix Azaltma
        int sonradanAzalt = sayi--;
        System.out.println("sayi-- sonrası dönen değer   : " + sonradanAzalt + " (sayi şu an: " + sayi + ")");
        int oncedenAzalt = --sayi;
        System.out.println("--sayi sonrası dönen değer   : " + oncedenAzalt + " (sayi şu an: " + sayi + ")");

        // İşaret Operatörleri (+, -)
        int pozitif = +sayi;
        int negatif = -sayi;
        System.out.println("Pozitif (+sayi) : " + pozitif);
        System.out.println("Negatif (-sayi) : " + negatif);

        // ==========================================================
        // 3. BÖLÜM: İLİŞKİSEL (KARŞILAŞTIRMA) OPERATÖRLERİ
        // İki değeri karşılaştırır ve sonuç daima boolean (true/false) döner.
        // ==, !=, >, <, >=, <=
        // ==========================================================
        System.out.println("\n--- 3. İLİŞKİSEL (KARŞILAŞTIRMA) OPERATÖRLERİ ---");
        int x = 15;
        int y = 20;

        System.out.println("x = " + x + ", y = " + y);
        System.out.println("Eşit mi? (x == y)          : " + (x == y)); // false
        System.out.println("Eşit DEĞİL mi? (x != y)    : " + (x != y)); // true
        System.out.println("Büyük mü? (x > y)          : " + (x > y));  // false
        System.out.println("Küçük mü? (x < y)          : " + (x < y));  // true
        System.out.println("Büyük eşit mi? (x >= y)    : " + (x >= y)); // false
        System.out.println("Küçük eşit mi? (x <= y)    : " + (x <= y)); // true

        // ==========================================================
        // 4. BÖLÜM: MANTIKSAL OPERATÖRLER (&&, ||, !)
        // Birden fazla koşulu birleştirmek için kullanılır.
        // && (VE): Her iki koşul da doğruysa true.
        // || (VEYA): Koşullardan en az biri doğruysa true.
        // ! (DEĞİL): Değeri tersine çevirir (true -> false, false -> true).
        // ==========================================================
        System.out.println("\n--- 4. MANTIKSAL OPERATÖRLER ---");
        boolean ehliyetVar = true;
        int yas = 21;
        boolean saglikRaporuVar = false;

        // Mantıksal VE (&&)
        boolean aracSurebilirMi = (yas >= 18) && ehliyetVar;
        System.out.println("Yaş >= 18 VE Ehliyet Var mı? (&&) : " + aracSurebilirMi);

        // Mantıksal VEYA (||)
        boolean basvurabilirMi = ehliyetVar || saglikRaporuVar;
        System.out.println("Ehliyet VEYA Rapor Var mı? (||)   : " + basvurabilirMi);

        // Mantıksal DEĞİL (!)
        boolean raporEksikMi = !saglikRaporuVar;
        System.out.println("Sağlık Raporu Eksik mi? (!)       : " + raporEksikMi);

        // Kısa Devre (Short-Circuit) Mantığı:
        // 'false && ...' durumunda sağdaki ifade hiç hesaplanmaz!
        // 'true || ...' durumunda sağdaki ifade hiç hesaplanmaz!
        int kontrolSayisi = 5;
        boolean kisaDevreTest = (kontrolSayisi > 10) && (++kontrolSayisi > 5);
        System.out.println("Kısa devre sonucu (++kontrol çalışmadı): kontrolSayisi = " + kontrolSayisi);

        // ==========================================================
        // 5. BÖLÜM: ATAMA VE BİLEŞİK ATAMA OPERATÖRLERİ
        // =, +=, -=, *=, /=, %=
        // ==========================================================
        System.out.println("\n--- 5. ATAMA VE BİLEŞİK ATAMA OPERATÖRLERİ ---");
        int puan = 50;
        System.out.println("Başlangıç Puanı           : " + puan);

        puan += 10; // puan = puan + 10 (60)
        System.out.println("10 Ekle (puan += 10)      : " + puan);

        puan -= 5;  // puan = puan - 5 (55)
        System.out.println("5 Çıkar (puan -= 5)       : " + puan);

        puan *= 2;  // puan = puan * 2 (110)
        System.out.println("2 ile Çarp (puan *= 2)    : " + puan);

        puan /= 4;  // puan = puan / 4 (27)
        System.out.println("4'e Böl (puan /= 4)       : " + puan);

        puan %= 5;  // puan = puan % 5 (2)
        System.out.println("5'e Modunu Al (puan %= 5) : " + puan);

        // ==========================================================
        // 6. BÖLÜM: KOŞUL (TERNARY / ÜÇLÜ) OPERATÖRÜ (? :)
        // Sözdizimi: kosul ? dogruysa_deger : yanlissa_deger;
        // ==========================================================
        System.out.println("\n--- 6. KOŞUL (TERNARY) OPERATÖRÜ ---");
        int sinavNotu = 72;
        String sonucMetni = (sinavNotu >= 60) ? "GEÇTİ" : "KALDI";
        System.out.println("Not: " + sinavNotu + " -> Durum: " + sonucMetni);

        int sayi1 = 45;
        int sayi2 = 80;
        int buyukOlan = (sayi1 > sayi2) ? sayi1 : sayi2;
        System.out.println(sayi1 + " ve " + sayi2 + " arasından büyük olan: " + buyukOlan);

        // ==========================================================
        // 7. BÖLÜM: BİT DÜZEYİNDE (BITWISE) VE KAYDIRMA OPERATÖRLERİ
        // Sayıların ikilik (binary) bitleri üzerinde doğrudan işlem yapar.
        // &, |, ^, ~, <<, >>
        // ==========================================================
        System.out.println("\n--- 7. BİT DÜZEYİNDE (BITWISE) OPERATÖRLER ---");
        int m = 5;  // 0000 0101 (Binary)
        int n = 3;  // 0000 0011 (Binary)

        System.out.println("m = " + m + " (Binary: " + Integer.toBinaryString(m) + ")");
        System.out.println("n = " + n + " (Binary: " + Integer.toBinaryString(n) + ")");

        // Bitwise AND (&): Her iki bit 1 ise 1
        System.out.println("Bitwise AND (m & n)        : " + (m & n) + " (Binary: " + Integer.toBinaryString(m & n) + ")"); // 1

        // Bitwise OR (|): En az bir bit 1 ise 1
        System.out.println("Bitwise OR (m | n)         : " + (m | n) + " (Binary: " + Integer.toBinaryString(m | n) + ")"); // 7

        // Bitwise XOR (^): Farklı bitlerde 1, aynı bitlerde 0
        System.out.println("Bitwise XOR (m ^ n)        : " + (m ^ n) + " (Binary: " + Integer.toBinaryString(m ^ n) + ")"); // 6

        // Bitwise NOT (~): Tüm bitleri ters çevirir (1'e tümleyen)
        System.out.println("Bitwise NOT (~m)           : " + (~m)); // -6

        // Sola Kaydırma (<<): Sayıyı 2 ile çarpmaya denktir
        int kaydirSola = m << 1; // 5 * 2 = 10
        System.out.println("Sola 1 Kaydır (m << 1)     : " + kaydirSola);

        // Sağa Kaydırma (>>): Sayıyı 2'ye tam bölmeye denktir
        int kaydirSaga = m >> 1; // 5 / 2 = 2
        System.out.println("Sağa 1 Kaydır (m >> 1)     : " + kaydirSaga);

        // ==========================================================
        // 8. BÖLÜM: OPERATÖR ÖNCELİĞİ (OPERATOR PRECEDENCE)
        // Çarpma/Bölme önce yapılır, parantez daima önceliği belirler.
        // ==========================================================
        System.out.println("\n--- 8. OPERATÖR ÖNCELİĞİ ---");
        int islem1 = 10 + 2 * 5;     // 10 + 10 = 20
        int islem2 = (10 + 2) * 5;   // 12 * 5 = 60
        System.out.println("10 + 2 * 5   = " + islem1 + " (Önce çarpma yapıldı)");
        System.out.println("(10 + 2) * 5 = " + islem2 + " (Parantez önceliği aldı)");

        System.out.println("\n==================================================");
        System.out.println("      OPERATÖRLER DEMO BAŞARIYLA TAMAMLANDI       ");
        System.out.println("==================================================");
    }
}
