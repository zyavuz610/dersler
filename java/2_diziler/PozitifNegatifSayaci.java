import java.util.Arrays;
import java.util.Random;

public class PozitifNegatifSayaci {
    public static void main(String[] args) {
        int[] sayilar = new int[10];
        Random random = new Random();

        int pozitifSayisi = 0;
        int negatifSayisi = 0;
        int sifirSayisi = 0;

        // 10 elemanlı diziyi -10 ile +10 arasında rastgele tamsayılarla doldurma
        for (int i = 0; i < sayilar.length; i++) {
            sayilar[i] = random.nextInt(21) - 10; // [0, 20] - 10 -> [-10, 10]
        }

        // Dizideki pozitif, negatif ve sıfır değerlerini sayma
        for (int sayi : sayilar) {
            if (sayi > 0) {
                pozitifSayisi++;
            } else if (sayi < 0) {
                negatifSayisi++;
            } else {
                sifirSayisi++;
            }
        }

        // Sonuçları ekrana yazdırma
        System.out.println("Oluşturulan Dizi: " + Arrays.toString(sayilar));
        System.out.println("Pozitif sayı adedi: " + pozitifSayisi);
        System.out.println("Negatif sayı adedi: " + negatifSayisi);
        System.out.println("Sıfır adedi       : " + sifirSayisi);
    }
}
