package threads;


// SYNCHRONIZED БЛОКИ И МОНИТОР
//*****************************

// Здесь synchronized вешается не на весь метод, а на блок кода, который нужно синхронизировать.
// synchronized(объект){блок кода}



public class SynchronizedBlock1 {

    public static void main(String[] args) {
        MyRunnableImpl2 runnable = new MyRunnableImpl2();
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


class Counter2{
    static int count = 0;
}


class MyRunnableImpl2 implements Runnable{

    private void doWork2(){
        System.out.println("Yeah!!!");
    }


    private void doWork1 (){
        doWork2();
        synchronized (this) {
            Counter2.count++;
            System.out.println(Thread.currentThread().getName() + " " + Counter2.count);
        }
    }


    @Override
    public void run() {
        for(int i = 0; i < 3; i++){
            doWork1();
        }
    }
}
