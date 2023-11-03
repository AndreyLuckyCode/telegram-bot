package threads;

// SYNCHRONIZED и DATA RACE 2
//*************************

// Когда 2+ потока имеют доступ к 1 переменной, которую они могут менять, возникает проблема при итерировании,
// тк из-за разницы в скорости работы потоков один поток не успевает учесть результат работы второго.

// Чтобы избежать ситуации, когда к 1 методу (который изменяет переменную) имеют доступ несколько потоков одновременно,
// вешаем synchronized, которы закрывает метод для всех потоков, если в него зашел один поток, до тех пор, пока он не закончит
// работу с этим методом.

// Таким образом, вешая lock на метод, мы не даем потокам беспорядочно использовать его и менять значение переменной.
// То есть, делаем работу потоков синхронизированной (НЕ СИНХРОННОЙ!!!)


public class Ex11 {
    static int counter = 0;
    public static synchronized void increment(){
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new R());
        Thread thread2 = new Thread(new R());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(counter);
    }
}


class R implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i<1000; i++){
            Ex11.increment();
        }
    }
}
