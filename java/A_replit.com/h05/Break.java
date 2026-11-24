public class Break{
    public static void main(String[] args) {
        // 1 ile 100 arasında 7'ye bölünen İLK sayıyı bulup döngüyü bitirelim:
        for (int i = 1; i <= 100; i++) {
            if (i % 7 == 0) {
                System.out.println("7'ye bölünen sayı bulundu: " + i);
                //break; // Kalan 93 adımı kontrol etmeden döngüyü ANINDA BİTİRİR!
                continue; // bir sonraki sayıya geç!
            }
            System.out.println("Kontrol edilen sayı: " + i);
        }
    }
}
// https://codeshare.io/5vDLJl