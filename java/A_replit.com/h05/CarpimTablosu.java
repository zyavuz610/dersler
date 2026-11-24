public class CarpimTablosu {
    public static void main(String[] args) {
        System.out.println("===== ÇARPIŞMA TABLOSU =====");

        for (int i = 1; i <= 5; i++) { // Satırlar
            for (int j = 1; j <= 5; j++) { // Sütunlar
                // '\t' tab boşluğu bırakarak düzgün hizalama sağlar
                System.out.print(i * j + "\t");
            }
            System.out.println(); // Satır tamamlanınca alt satıra geç
        }
    }
}