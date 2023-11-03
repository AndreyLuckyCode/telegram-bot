package threads;

// VOLATILE (история с памятью)
//***********

// Есть 2 потока. Есть переменная, которая фигурирует в обоих потоках
// значение этой переменной берется каждым из потоков из Main Memory и сохраняется в кеш (у каждого потока свой)
// Если в одном из потоков мы меняем значение переменной и это должно поывлиять на работу второго потока
// мы должны пометить переменную как volatile = значение переменной хранится только в Main Memory, а не в кеше потоков
// В таком случае, если 1 поток меняет значение переменной, оно попадает в Main Memory, откуда значение считывает второй поток

// В случае с volatile только 1 поток может менять значение переменной, иначе работа многопоточности будет некорректной
// из-за асинхронизации потоков (когда речь идет о 2+ ядрах)


public class VolatileEx extends Thread{
    volatile boolean b = true;

    public void run(){
        long counter = 0;
        while (b){
            counter++;
        }
        System.out.println("Loop is finished. Counter = " + counter);
    }


    public static void main(String[] args) throws InterruptedException {
        VolatileEx threadCounter = new VolatileEx();
        threadCounter.start();
// Здесь .sleep() для потока Main. Этот delay как бы дает поработать второму потоку 3 сек, а потом возобновляет работу main потока
        System.out.println("\nthreadCounter starts its loop...");
        Thread.sleep(3000);
        System.out.println("Main threadCounter: After 3 sec its time to wake up");
        threadCounter.b = false;
        threadCounter.join();
        System.out.println("Main threadCounter: Program is finished");

    }
}
