import java.util.Scanner;

public class Grade2 {
  public static void main(String[] args){
    int sinav1, sinav2, sozlu;
    Scanner klavye = new Scanner(System.in);
    System.out.println("=== Öğrenci Not Hesaplama ===");
    System.out.println("");
    System.out.print("Ara Sınav (Vize) notunu giriniz (0-100: ");
    sinav1 = klavye.nextInt(); 
    System.out.println("Girilen Vize Notu: " + sinav1);
    
    System.out.print("Final notunu giriniz (0-100: ");
    sinav2 = klavye.nextInt(); 
    System.out.println("Girilen Final Notu: " + sinav2);
    
    System.out.print("Sözlü notunu giriniz (0-100: ");
    sozlu = klavye.nextInt(); 
    System.out.println("Girilen Sözlü Notu: " + sozlu);

    double ortalama;
    ortalama = 0.3*sinav1 + 0.2*sozlu + 0.5*sinav2;

    // eğer sözlü 80 den fazla ise ortalama % 10 artsın
    if (sozlu >=80){
      // ortalama = ortalama + 0.1*ortalama;
      ortalama += 0.1 * ortalama;
      if (ortalama>100)
        ortalama = 100;
    }
    
    System.out.println("");
    System.out.println("-----------------------------");
    System.out.println("Dönem Sonu Ortalaması : " + ortalama);
    System.out.println("-----------------------------");

    // koşul ifadeleri
    // if, else, switch-case

    // ikili dallanma, 
    if(  ortalama <= 50   ){
      System.out.println("KALDI");
    }
    else {
      System.out.println("GEÇTİ");
    }

    String harf;
    // çoklu dallanma
    if (ortalama < 50 && ortalama >=0){
      harf = "FF";
    }
    else if(ortalama <60){
      harf = "DC";
    }
    else if(ortalama < 75){
      harf = "CC";
    }
    else if (ortalama < 90){
      harf = "BB";      
    }
    else if (ortalama <= 100) {
      harf = "AA";
    }
    else {
      harf = "E";
    }
    System.out.printf("Ortalama: %.2f \n",ortalama);
    System.out.printf("Harf Notu: %s \n",harf);

    // karşılaştırma operatörleri
    /*
    <, >, <=, >=, ==, !=
    */

    // mantıksal operatörler
    // && -> ve, aynı anda ikisi de doğru olmalı
    // || -> veya, herhangi bir true olmalı
    // ! -> değil, doğru ise yanlışa çevirir, ya da tam tersi

    System.out.println("-------------------------------");
    int a = 25;
    if ( a%2 == 1 ){
      System.out.println(a + " sayısı tektir");
    }
    else {
      System.out.println(a + " sayısı çifttir");
    }

    switch(harf){
      case "AA":
        System.out.println("Harika bir dönem geçirdin");
        break;
      case "BB":
        System.out.println("Aferin, geçer nottan daha fazlası");
        break;
      case "CC":  
        System.out.println("İyi,en azından geçer not");
        break;
      case "DC":
        System.out.println("Daha dikkatli olmalısın");
        break;
      case "FF":
        System.out.println("Geçmiş olsun, seneye daha iyi şeyler öğreneceksin");
        break;
      default:
        System.out.println("Bir yerlerde bir eksik var");
    }  
  }
}