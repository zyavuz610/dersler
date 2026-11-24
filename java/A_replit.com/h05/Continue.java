public class Continue{
    public static void main(String[] args) {
        // 1-100 arası sayıları yaz
        for(int i=1;i<=100;i++){
            if (i%5 == 0){
                System.out.println("[" + i + "], ");
            }
            else {
                System.out.print(i + ", ");
            }
        }
        System.out.println();
    }
}