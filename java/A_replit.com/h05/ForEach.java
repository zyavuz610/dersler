public class ForEach {
    public static void main(String[] args) {
        int[] grades = {85, 92, 78, 64, 99};

        // 1. YOL: Klasik for döngüsü (İndeks ile erişim)
        int sum1 = 0;
        for (int i = 0; i < grades.length; i++) {
            sum1 += grades[i];
        }

        // 2. YOL: Modern for-each döngüsü (Çok daha temiz ve güvenli!)
        int sum2 = 0;
        for (int x : grades) {
            sum2 += x;
        }

        System.out.println("The sum of the grades: " + sum1);
        System.out.println("The sum of the grades: " + sum2);
    }
}