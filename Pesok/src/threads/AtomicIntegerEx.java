package threads;

import java.util.concurrent.atomic.AtomicInteger;


//ATOMICINTEGER
//*************

// Из-за того, что synchronized очень затратная штука, в случае с целочисленными объектами лучше использовать
// AtomicInteger, у которого есть свои методы для инкремента и тп. Юзаются атомарные операции.
// Решает проблему с Data Race


public class AtomicIntegerEx {
//    static int counter;

    static AtomicInteger counter = new AtomicInteger();

    public static void increment(){
//        counter++;

        //заменяет ++
//        counter.incrementAndGet();

        //добавляет к числу параметр и выдает значение
        counter.addAndGet(5);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyRunnableImpl18());
        Thread thread2 = new Thread(new MyRunnableImpl18());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(counter);
    }
}

class MyRunnableImpl18 implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i < 1000; i++){
            AtomicIntegerEx.increment();
        }
    }
}
