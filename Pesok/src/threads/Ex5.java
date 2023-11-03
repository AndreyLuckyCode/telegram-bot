package threads;

// МЕТОДЫ .SLEEP() И .JOIN()
//**************************
// .sleep() просто останавливает поток на определенное время
// .join() вешается на поток после его старта = окончание этого потока нужно дождаться для дальнейшего выполнения кода



public class Ex5 extends Thread{
    public void run() {
        for(int i = 1; i <= 10; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyRunnable1());
        Ex5 thread2 = new Ex5();
        thread1.setName("Первый поток");
        thread2.setName("Второй поток");
        thread1.start();
        thread2.start();
// Join вешаем на те потоки, окончания которых должен дождаться Main, чтобы продолжить свою работу
// Вызывается ПОСЛЕ старта потока
        thread1.join();
        thread2.join();

        System.out.println("Over");
    }

}


class MyRunnable1 implements Runnable{

    @Override
    public void run() {
        for(int i = 1; i <= 10; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}
