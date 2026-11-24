import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsalMultiProcess {

    // Sayının asallığını kontrol eden fonksiyon
    private static boolean isAsal(int sayi) {
        if (sayi < 2) return false;
        for (int i = 2; i <= Math.sqrt(sayi); i++) {
            if (sayi % i == 0) return false;
        }
        return true;
    }

    // Çocuk Sürecin (Worker Process) yürüttüğü iş mantığı
    private static void calisCocukSurec(int baslangic, int bitis) {
        long pid = ProcessHandle.current().pid(); // Sürecin kendi OS PID değeri
        List<Integer> asallar = new ArrayList<>();

        for (int i = baslangic; i <= bitis; i++) {
            if (isAsal(i)) {
                asallar.add(i);
            }
        }

        System.out.printf("[Çocuk Süreç PID: %d] [%d - %d Aralığı] Bulunan Asal Sayı: %d adet%n",
                pid, baslangic, bitis, asallar.size());
        System.out.printf("[PID: %d] Asallar: %s%n%n", pid, asallar);
    }

    // Ana Süreç (Master Process)
    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. Durum: Eğer komut satırı argümanı varsa bu bir ÇOCUK SÜREÇTİR
        if (args.length == 2) {
            int baslangic = Integer.parseInt(args[0]);
            int bitis = Integer.parseInt(args[1]);
            calisCocukSurec(baslangic, bitis);
            return;
        }

        // 2. Durum: Argüman yoksa bu ANA SÜREÇTİR (Master)
        long anaPid = ProcessHandle.current().pid();
        System.out.println("=== ANA SÜREÇ BAŞLATILDI (Master PID: " + anaPid + ") ===");
        System.out.println("5 ayrı işletim sistemi süreci başlatılıyor...\n");

        // Mevcut JVM çalıştırma komutu ve sınıf yolu
        String javaKomut = ProcessHandle.current().info().command().orElse("java");
        String classPath = System.getProperty("java.class.path");
        String sinifAdi = AsalMultiProcess.class.getName();

        // 5 farklı 100'lük aralık
        int[][] araliklar = {
            {100, 200},
            {200, 300},
            {300, 400},
            {400, 500},
            {500, 600}
        };

        List<Process> surecler = new ArrayList<>();

        for (int[] aralik : araliklar) {
            // İşletim sisteminde yeni bir bağımsız JVM süreci oluşturma
            ProcessBuilder pb = new ProcessBuilder(
                javaKomut,
                "-cp", classPath,
                sinifAdi,
                String.valueOf(aralik[0]),
                String.valueOf(aralik[1])
            );

            // Çocuk sürecin standart çıktısını doğrudan ana terminale bağlar
            pb.inheritIO();

            // Süreci işletim sisteminde başlat
            Process cocuk = pb.start();
            surecler.add(cocuk);

            System.out.printf("-> Süreç oluşturuldu: PID=%d | Aralık=[%d - %d]%n",
                    cocuk.pid(), aralik[0], aralik[1]);
        }

        System.out.println("\nTüm süreçler paralel çalışıyor, sonuçlar bekleniyor...\n");

        // Ana sürecin tüm çocuk süreçlerin bitmesini beklemesi (Wait)
        for (Process cocuk : surecler) {
            cocuk.waitFor();
        }

        System.out.println("=== TÜM ÇOCUK SÜREÇLER TAMAMLANDI ===");
    }
}