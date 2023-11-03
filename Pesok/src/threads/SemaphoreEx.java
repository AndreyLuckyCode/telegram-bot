package threads;

import java.util.concurrent.Semaphore;

//SEMAPHORE
//*********
//
//High lvl, который сам решает вопросы low lvl. Semaphore callBox = new Semaphore(2);
//В параметре - количество потоков, которые могут работать одновременно с методом
//
//запускается через .acquire() - проверяет количество потоков в методе и пускает/удерживает другие потоки
//закрывается через .release() - освобождает место для потока, который ожидает. Метод вызывается в finally





public class SemaphoreEx {
    public static void main(String[] args) {
        Semaphore callBox = new Semaphore(2);

        new Person("Lexa", callBox);
        new Person("Oleg", callBox);
        new Person("Serega", callBox);
        new Person("Lenka", callBox);
        new Person("Marishka", callBox);
    }
}


class Person extends Thread{
    String name;
    private Semaphore callBox;
    public Person(String name, Semaphore callBox){
        this.name = name;
        this.callBox = callBox;
        this.start();
    }

    public void run(){
        try {
            System.out.println(name + " ждет очередь...");
            callBox.acquire();
            System.out.println(name + " начал(а) звонок. Будка ЗАНЯТА...");
            sleep(2000);
            System.out.println(name + " завершил(а) звонок. Будка СВОБОДНА!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            callBox.release();
        }
    }
}
