public class CiftSayilar {
    public static void main(String[] args) {
        System.out.println("0 ile 20 arasındaki çift sayılar:");

        for (int i = 1; i <= 20; i += 1) {
            if (i%2 == 0){
                System.out.print(i + " ");
            }
        }
        System.out.println(); // Alt satıra geçiş
    }
}