import java.util.Arrays;

public class DiziTersCevirme {
    public static void main(String[] args) {
        int[] dizi = {1, 2, 3, 4, 5, 6};

        System.out.println("Orijinal Dizi      : " + Arrays.toString(dizi));

        // İki işaretçi (two-pointer) ile yerinde (in-place) ters çevirme
        int sol = 0;
        int sag = dizi.length - 1;

        while (sol < sag) {
            int gecici = dizi[sol];
            dizi[sol] = dizi[sag];
            dizi[sag] = gecici;

            sol++;
            sag--;
        }

        System.out.println("Ters Çevrilmiş Dizi: " + Arrays.toString(dizi));
    }
}
