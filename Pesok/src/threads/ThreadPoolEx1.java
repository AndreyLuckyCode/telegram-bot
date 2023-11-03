package threads;


// THREAD POOLS
//*************
//
//Вызывается так   ExecutorService executorService = Executors.newFixedThreadPool(5); метод newFixedThreadPool(5) - Factory
//В параметр передается количество потоков.
//
//Запускается пул через .execute(), закрывается через .shutdown(). Если не закрыть - будет ждать следующие таски.
//
//Метод executorService.awaitTermination(2, TimeUnit.SECONDS); заставляет ждать поток, в котором он вызывается, пока
//пул потоков не закончит свою работу.



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolEx1 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 10; i++){
            executorService.execute(new Runnable100());
        }
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Main ends");
    }
}


class Runnable100 implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
