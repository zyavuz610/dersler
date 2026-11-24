import java.util.Scanner;
import java.util.Arrays;

public class SinifNotAnalizLaboratuvari {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   KTÜ BİLGİSAYAR MÜH. DİZİ NOT ANALİZİ   ");
        System.out.println("==========================================");

        System.out.print("Sınıftaki toplam öğrenci sayısını giriniz: ");
        int ogrenciSayisi = scanner.nextInt();

        if (ogrenciSayisi <= 0) {
            System.out.println("❌ Hata: Öğrenci sayısı en az 1 olmalıdır!");
            scanner.close();
            return;
        }

        // 1. Dizi Tanımlama ve Bellek Tahsisi
        double[] notlar = new double[ogrenciSayisi];

        // 2. Dizi Elemanlarını Kullanıcıdan Alma
        System.out.println("\nLütfen notları sırayla giriniz (0 - 100):");
        for (int i = 0; i < notlar.length; i++) {
            System.out.print((i + 1) + ". Öğrencinin Notu: ");
            double girilen = scanner.nextDouble();

            // Girdi Doğrulama
            while (girilen < 0 || girilen > 100) {
                System.out.print("❌ Geçersiz! Not 0-100 arasında olmalıdır. Tekrar girin: ");
                girilen = scanner.nextDouble();
            }
            notlar[i] = girilen;
        }

        // 3. İstatistiksel Hesaplamalar
        double toplam = 0.0;
        double enYuksek = notlar[0];
        double enDusuk = notlar[0];
        int gecenSayisi = 0;
        final double GECME_NOTU = 50.0;

        for (int i = 0; i < notlar.length; i++) {
            double notDegeri = notlar[i];
            toplam += notDegeri;

            if (notDegeri > enYuksek) enYuksek = notDegeri;
            if (notDegeri < enDusuk)  enDusuk = notDegeri;
            if (notDegeri >= GECME_NOTU) gecenSayisi++;
        }

        double ortalama = toplam / notlar.length;
        int kalanSayisi = notlar.length - gecenSayisi;
        double basariOrani = ((double) gecenSayisi / notlar.length) * 100.0;

        // 4. Sonuç Raporu
        System.out.println("\n----------------- SINIF ANALİZ RAPORU -----------------");
        System.out.println("Girilen Notlar Seti     : " + Arrays.toString(notlar));
        System.out.println("Toplam Öğrenci Sayısı   : " + notlar.length);
        System.out.printf("Sınıf Not Ortalaması    : %.2f\n", ortalama);
        System.out.println("En Yüksek Puan (Zirve)  : " + enYuksek);
        System.out.println("En Düşük Puan (Taban)   : " + enDusuk);
        System.out.println("Dersi Geçen Öğrenci     : " + gecenSayisi);
        System.out.println("Dersten Kalan Öğrenci   : " + kalanSayisi);
        System.out.printf("Sınıf Başarı Yüzdesi    : %%%.1f\n", basariOrani);

        // 5. Ortalamanın Üstündeki Başarılı Öğrencileri Listeleme
        System.out.println("\nOrtalamanın ( " + String.format("%.2f", ortalama) + " ) Üzerindeki Öğrenciler:");
        for (int i = 0; i < notlar.length; i++) {
            if (notlar[i] > ortalama) {
                System.out.println("-> Öğrenci No #" + (i + 1) + " : " + notlar[i] + " puan");
            }
        }

        System.out.println("=======================================================");
        scanner.close();
    }
}
