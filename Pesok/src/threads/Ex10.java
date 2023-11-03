package threads;

// SYNCHRONIZED и DATA RACE 1
//*************************

// Когда 2+ потока имеют доступ к 1 переменной, которую они могут менять, возникает проблема при итерировании,
// тк из-за разницы в скорости работы потоков один поток не успевает учесть результат работы второго.

// Чтобы избежать ситуации, когда к 1 методу (который изменяет переменную) имеют доступ несколько потоков одновременно,
// вешаем synchronized, которы закрывает метод для всех потоков, если в него зашел один поток, до тех пор, пока он не закончит
// работу с этим методом.

// Таким образом, вешая lock на метод, мы не даем потокам беспорядочно использовать его и менять значение переменной.
// То есть, делаем работу потоков синхронизированной (НЕ СИНХРОННОЙ!!!)

public class Ex10 {
    public static void main(String[] args) {
        MyRunnableImpl runnable = new MyRunnableImpl();
        Thread thread1 = new Thread(runnable);
        thread1.setName("Первый поток");
        Thread thread2 = new Thread(runnable);
        thread2.setName("Второй поток");
        Thread thread3 = new Thread(runnable);
        thread3.setName("Третий поток");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}


class Counter{
    static int count = 0;
}


class MyRunnableImpl implements Runnable{
    public synchronized void increment (){
        Counter.count++;
        System.out.println(Thread.currentThread().getName() + " " + Counter.count);
    }


    @Override
    public void run() {
        for(int i = 0; i < 100; i++){
            increment();
        }
    }
}