public class SonsuzDongu {
    public static void main(String[] args) {
        /*
        // Sonsuza kadar çalışan for döngüsü:
        for (;;) {
            System.out.println("Bu mesaj hiç durmadan yazdırılır!");
            // Ancak içeride bir 'break' ile manuel olarak çıkılabilir.
        }
        */
        //-----------------------------------------------------
        // ❌ HATA: Sayaç geriye sayması gerekirken artırılıyor!
        // i değeri 10'dan başlayıp 11, 12, 13... diye sonsuza kadar büyür ve hep > 0 kalır!
        for (int i = 10; i > 0; i += 1000) {
            System.out.println(i);
        }
    }
}