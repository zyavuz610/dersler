public class Asal {

    // Asal sayı kontrol algoritması: O(sqrt(n))
    public static boolean asalMi(long n) {
        if (n < 2) {
            return false;
        }
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Kullanım: java Asal <ornek_id>");
            System.exit(1);
        }

        String ornekId = args[0];

        // Java 9 ve sonrası: Mevcut sürecin PID değerini işletim sisteminden okur
        long pid = ProcessHandle.current().pid();

        long aday = 2;
        int bulunanAsalSayisi = 0;
        final int HEDEF_ASAL_LIMITI = 5; // Üst sınır: 5 asal sayı

        System.out.printf("[BAŞLATILDI] Örnek No: %s | PID: %d (Hedef: %d asal sayı)%n",
                ornekId, pid, HEDEF_ASAL_LIMITI);

        // Belirlenen limite ulaşana kadar döngü devam eder
        while (bulunanAsalSayisi < HEDEF_ASAL_LIMITI) {
            if (asalMi(aday)) {
                bulunanAsalSayisi++;
                System.out.printf(
                    "Süreç: %s | PID: %d -> %d. Asal Sayı Bulundu: %d%n",
                    ornekId, pid, bulunanAsalSayisi, aday
                );

                // Süreçlerin eşzamanlı çalışmasını ve çıktı yarışını gözlemleyebilmek için kısa bekleme
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            aday++;
        }

        System.out.printf("[BİTTİ] Örnek No: %s | PID: %d görevini tamamladı ve sonlanıyor.%n", ornekId, pid);
    }
}