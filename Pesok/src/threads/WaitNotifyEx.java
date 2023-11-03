package threads;

// МЕТОДЫ wait() и notify();
//*************************

// wait() включает режим ожидания на потоке, в котором он вызывается, и присваивает монитору (lock) статус "свободен",
// тем самым открывая доступ к методам (которые синхронизированы по этому же lock) для других потоков.
// Это означает, что поток приостанавливает свою работу и перестает выполнять инструкции до тех пор,
// пока не будет вызван метод notify() или notifyAll() из другого потока

// notify() вызывается для оповещения одного из ожидающих потоков, которые были приостановлены методом wait().
// Это позволяет одному из ожидающих потоков продолжить выполнение.
// Важно отметить, что notify() не освобождает монитор, он просто разблокирует один из ожидающих потоков.


// 2 метода putBread() и getBread() меняют значение переменной breadCount.
// Именно поэтому нужна синхронизация, хоть эти методы и вызываются в разных потоках.
// Также создаем private final Object lock = new Object(); и вешаем его на блок кода.


public class WaitNotifyEx {
    public static void main(String[] args) {
        Market market = new Market();
        Producer producer = new Producer(market);
        Consumer consumer = new Consumer(market);

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.start();
        thread2.start();
    }
}


class Market{
    private int breadCount = 0;
    private final Object lock = new Object();

    public  void getBread() {
        synchronized (lock) {
            while (breadCount < 1) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            breadCount--;
            System.out.println("Потребитель купил 1 хлеб");
            System.out.println("Количество хлеба = " + breadCount);
            lock.notify();
        }
    }

    public void putBread() {
        synchronized (lock) {
            while (breadCount >= 5) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            breadCount++;
            System.out.println("Производитель добавил на витрину 1 хлеб");
            System.out.println("Количество хлеба на витрине = " + breadCount);
            lock.notify();
        }
    }
}

class Producer implements Runnable{
    Market market;
    Producer(Market market){
        this.market=market;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            market.putBread();
        }
    }
}


class Consumer implements Runnable{
    Market market;
    Consumer(Market market){
        this.market=market;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            market.getBread();
        }
    }
}
