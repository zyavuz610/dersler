// ProcessBuilder ile Başka Bir Süreç Başlatma
public class ProcessOrnek {
    public static void main(String[] args) throws Exception {
        // İşletim sisteminde bağımsız bir süreç başlatır (Örn: ping veya başka bir Java süreci)
        ProcessBuilder pb = new ProcessBuilder("ping", "-c", "3", "google.com");
        Process process = pb.start();

        // Sürecin çıktısını okuma (IPC - Pipe)
        process.getInputStream().transferTo(System.out);

        int exitCode = process.waitFor();
        System.out.println("Süreç tamamlandı, çıkış kodu: " + exitCode);
    }
}