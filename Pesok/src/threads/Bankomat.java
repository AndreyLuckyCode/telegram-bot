package threads;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//LOCK
//*****

// Synchronized блоки можно заменить на Lock
// lock.lock() закрывает метод для других потоков, lock.unlock() открывает метод.
// lock.unlock() всегда вызывается через finally

// Если потоку необязательно выполнять synchronized часть кода именно сейчас, он может ее скипнуть,
// если прописать if(lock.tryLock()){блок кода}. Метод проверяет статус lock и, если он закрыт, дает потоку возможность
// скипнуть этот synchronized блок и пойти дальше по своему коду





public class Bankomat {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        new Employee("Lexa", lock);
        new Employee("Oleg", lock);
        new Employee("Elena", lock);
        Thread.sleep(5000);

        new Employee("Victor", lock);
        new Employee("Marina", lock);
    }
}


class Employee extends Thread{
    String name;
    private Lock lock;
    public Employee(String name, Lock lock){
        this.name = name;
        this.lock = lock;
        this.start();
    }

    @Override
    public void run() {
                System.out.println(name + " ждет...");

        if(lock.tryLock()){
//        System.out.println(name + " ждет...");
//        lock.lock();
        System.out.println(name + " снимает наличку...");
        try {
            Thread.sleep(4000);
            System.out.println(name + " набил карманы, идет домой");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
             } finally {
            lock.unlock();
        }
        } else {
            System.out.println(name + " заебался ждать и съебался");
        }
    }
}
