//long mesaj_zamani = 123456L;
//lonh su_an = 123486L;
public class Time {
  public static void main(String[] x){
    long mesaj_zamani  = 725123456L;
    long su_an         = 735473576L;
    long sure = su_an - mesaj_zamani;

    String mesaj = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since 1966, when designers at Letraset and James Mosley, the librarian at St Bride Printing Library in London, took a 1914 Cicero translation and scrambled it to make dummy text for Letraset's Body Type sheets. It has survived not only many decades, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised thanks to these sheets and more recently with desktop publishing software like Aldus PageMaker and Microsoft Word including versions of Lorem Ipsum.";
    String ayrac = "----------------------------------";
    String zaman_str;
    if (sure <60)
      zaman_str = sure + "sn önce";
    else if (sure < 60*60)
      zaman_str = sure/60 + "dk önce";
    else if (sure < 60*60*24)
      zaman_str = sure/(60*24) + "sa önce";
    else if (sure < 60*60*24*30)
      zaman_str = sure/(60*24*30) + "ay önce";
    else // 60*60*24*12*....
      zaman_str = sure/(60*24*30*12) + "yıl önce";

    System.out.println(mesaj);
    System.out.println(ayrac);
    System.out.println(zaman_str);
  }
}