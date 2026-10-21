public class Operator {
  public static void main(String [] args){

    // aritmetik operatörler, sonucu yine sayı olan işlemlerdir.
    int birinciSayi = 20;
    int ikinciSayi = birinciSayi + 15;
    int ucuncuSayi = ikinciSayi - birinciSayi + 45;

    double pi = 3.14;
    double cap = 25;
    double r = cap / 2;

    // diğer aritmetik operatörler
    // +, -, *, /, %
    // bunlar 2 adet operand alırlar

    int sayi = 45;
    int kalan = sayi % 2; // mod, kalan işlemi

    // tekli aritmetik operatörler
    // ++, --
    int i = 25;
    System.out.println(i++); // 26
    int j = ++i; // 27

    int k = (j++) + 10; // 37, j=28, postfix
    // önce işlem sonra artırma
    System.out.println("k:"+k);
    
    int k2 = (++j) + 10; // 39, j=29, prefix
    // önce artırma sonra işlem
    System.out.println("k2:"+k2);

    //---------------------------------------------
    // karşılaştırma operatörleri, ikili operatördür
    // sayılar girer, boolean sonuç çıkar, if ve while içinde kullanılırlar
    // <, <=, >, >=, !=, ==
    int sayi1 = 13;
    int sayi2 = 21-8;
    boolean sonuc = (sayi1 == sayi2); // true;
    boolean sonuc2 = (sayi1 <= 14); // false

    //---------------------------------------------
    // mnatıksal operatörler, ikili operatör
    // boolean ifadeler girer, boolean ifadeler çıkar, if ve while içinde kullanılırlar
    // and (&&), or(||), not (!)

    boolean sonuc3 = sonuc && sonuc2; // false

    // && işlemi, mantıksal and işlemi
    /*
    true && true -> true
    true && false -> false
    false && true -> false
    false && false -> false
    */

    // || işlemi, mantıksal veya
    /*
    true || true -> true
    true || false -> true
    false || true -> true
    false || false -> false    
    */

    // !, mantıksal değil işlemi
    boolean sonuc4 = !sonuc3; // true
    /*
    true -> false
    false -> true
    */  

    //------------------------------------------------
    // tür dönüştürme,
    //   yenitur = (type)degisken
    double sayi50 = 25.0;
    //int sayi51 = sayi50; // illegal
    int sayi52 = (int)sayi50; // legal

    //------------python --------------
    /*
    sayi50 = 25.0
    sayi52 = int(sayi50)
    */
    //---------------------------------

    byte harfKod = 65;
    char ch = (char)(harfKod);
    System.out.println(ch);
  }
}
