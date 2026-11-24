public class Multithread {
    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            int threadno = i;
            Thread t1 = new Thread(() -> {
                System.out.println("Thread çalışıyor: %d".formatted(threadno) + Thread.currentThread().getName());
            });
            t1.start();
        }
    }
}