// Klasik Thread / Runnable Kullanımı
public class ThreadOrnek {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("Thread çalışıyor: " + Thread.currentThread().getName());
        });
        t1.start();
    }
}