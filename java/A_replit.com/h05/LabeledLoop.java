public class LabeledLoop {
    public static void main(String[] args) {
        outerLoop:
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print("(" + i + "," + j + ") \t");
                if(i==2 && j==2){
                    break outerLoop;
                }
            }
            System.out.println();
        }
        System.out.println("Arama bitti.");
    }
}