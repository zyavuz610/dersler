public class VeriTipi {
  public static void main(String [] args){
    // tamsayı, 32bit 
    int ogrenciYasi = 18;
    int sinifMevcudu = 32;
    int bosDeger;
    bosDeger = 25;

    System.out.println("Öğrenci Yaşı: " + ogrenciYasi);
    System.out.println("Sınıf Mevcudu: " + sinifMevcudu);

    // küçük tamsayı
    // byte -> 1 byte, 8 bit, -128 ile 127 arası
    // short -> 2 byte, 16bit, -30bin ile 30bin
    byte kardesSayisi = 2;
    short dogumYili = 2002;

    // çok büyük tam sayı, 64bit
    // long -> 64bit
    long dunyaNufusu = 8195342158L;

    // ondalıklı sayı, noktalı sayı
    // float (32bit), double (64bit)
    float boy = 1.75F;
    double boy2 = 1.63;

    double cap = 25.2;
    double pi = 3.14159;
    double r = cap/2;
    double cevre = 2*pi*r;
    double alan = pi*r*r;

    System.out.println("Alan: " + alan);
    System.out.println("Çevre: " + cevre);


    // karakter, tek bir karakter tutmak için
    char sinifSubesi = 'A';
    char sinifSubesi2 = 'B';

    // mantıksal tür: boolean -> true, false;
    boolean derseKatilim = true;
    boolean odeviYaptiMi = false;

    System.out.println("Derse Katılım:" + derseKatilim);


    // metin (string), String
    String metin = "Merhaba Avrasya!";
    String selam = "Merhaba";
    String ad = "Ferdi";
    String soyad = "Yılmaz";
    System.out.println(selam + " " + ad + " " + soyad);

    int sayi1;
    int SAYI1; // büyük-küçük harf farklı değişken  oluşturur
    //int 1sayi; HATA

    //double birinci not = 34.5;
    double birinci_not = 34.5;

    //boolean kaldi-mi = true;
    boolean kaldi_mi = true;

    // short public; // hata, keyword olmaz
    short public_; 

    byte _sınif;

    int _1 = 23; 
  }
}
