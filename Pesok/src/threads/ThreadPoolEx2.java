package threads;


//          scheduledExecutorService.schedule(new Runnable200(), 3, TimeUnit.SECONDS);
//параметры: какой поток, сколько ждать перед началом работы, единица измерения.

//          scheduledExecutorService.scheduleAtFixedRate(new Runnable200(), 3, 1, TimeUnit.SECONDS);
//параметры: какой поток, сколько ждать перед началом работы, сколько ждать между заданиями (на задачу 1 сек...
// 1 сек выполняет, 1 сек ждет, 1 сек выполняет, 1 сек ждет. Если на задачу нужно 2 сек, он 1 сек выполняет
// и сразу же выполняет 2 сек. Период прошел по время выполнения задачи, поэтому
// следующую задачу поток выполняет без дилея), единица измерения.

//          scheduledExecutorService.scheduleWithFixedDelay(new Runnable200(), 3, 1, TimeUnit.SECONDS);
//То же самое, что и 2 метод, НО здесь 1 сек - между выполнением задач. Само время выполнения не учитывается как во 2 методе


//          ExecutorService executorService = Executors.newCachedThreadPool();
// Кешированный пул, который создает потоки по мере необходимости.
// Таск1 - 1поток... Таск2, 1поток занят - 2поток... Таск3, 1поток и 2поток заняты - 3поток...
// Таск3, 1поток освободился - 1поток
// Если поток не вызывается 60 сек - он делитается



import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLOutput;
import java.util.concurrent.*;

public class ThreadPoolEx2 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        for(int i = 0; i < 10; i++){
//            scheduledExecutorService.execute(new Runnable200());
//        }

        //1 МЕТОД
        //*******


//        scheduledExecutorService.schedule(new Runnable200(), 3, TimeUnit.SECONDS);
//        scheduledExecutorService.shutdown();


        //2 МЕТОД
        //*******


//        scheduledExecutorService.scheduleAtFixedRate(new Runnable200(), 3, 1, TimeUnit.SECONDS);
//        Thread.sleep(20000);
//        scheduledExecutorService.shutdown();


        //3 МЕТОД
        //*******


        scheduledExecutorService.scheduleWithFixedDelay(new Runnable200(), 3, 2, TimeUnit.SECONDS);

        Thread.sleep(20000);
        scheduledExecutorService.shutdown();
    }
}


class Runnable200 implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " begins work");
        System.out.println("выполнение...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ends work");
        System.out.println("2 сек delay");
    }
}
