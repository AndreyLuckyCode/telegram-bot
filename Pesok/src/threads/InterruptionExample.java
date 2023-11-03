package threads;


// INTERRUPTION
//*************

// Чтобы прервать поток из другого потока нужно прописать thread.interrupt(); и в потоке, который хотим прервать,
// прописать блок if(isInterrupted()){... return}. Если его попатаются прервать из другого потока,
// isInterrupted станет true и сработает описанный блок кода, после чего поток завершится по return


public class InterruptionExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main starts");

        InterruptedThread thread = new InterruptedThread();
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();



        thread.join();
        System.out.println("Main ends");

    }
}



class InterruptedThread extends Thread{
    double sqrtSum=0;
    public void run(){
        for(int i = 1; i < 1000000000; i++) {
            if(isInterrupted()){
                System.out.println("Поток: меня хотят прервать");
                System.out.println("Поток: вот, что успел сделать - " + "sqrtSum = " + sqrtSum);
                System.out.println("Поток: состояние всех объектов нормальное. Завершаю работу");
                return;
            }
            sqrtSum += Math.sqrt(i);
        }
        System.out.println(sqrtSum);
    }
}
