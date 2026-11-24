import java.util.Scanner;
public class FaktoriyelVeToplam {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.print("n değerini gir: ");
        int n = input.nextInt();
        long faktoriyel = 1;
        int toplam = 0;

        int i;
        for (i = 1; i <= n; i++) {
            toplam += i;       // toplam = toplam + i;
            faktoriyel *= i;   // faktoriyel = faktoriyel * i;
        }
        
        System.out.println("i: " + i);

        System.out.println("1'den " + n + "'e kadar sayıların toplamı : " + toplam);
        System.out.println(n + "! (Faktöriyel) değeri                 : " + faktoriyel);
    }
}