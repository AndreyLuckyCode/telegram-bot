package threads;

// DAEMON THREAD
//**************

// отличие от user thread - при завершении работы последнего user thread программа завершает свое выполнение,
// не дожидаясь окончания работы daemon threads.

// Daemon threads предназначены для выполнения фоновых задач и оказания различных сервисов user threads

// Чтобы обозначить поток daemon, нужно до его запуска прописать имя.setDaemon(true);

public class DaemonExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread starts");
        UserThread userThread = new UserThread();
        userThread.setName("user_thread");
        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setName("daemon_thread");
        daemonThread.setDaemon(true);
        userThread.start();
        daemonThread.start();
        userThread.join();
        System.out.println("Main Thread ends");
    }
}


class UserThread extends Thread{
    public void run(){
        System.out.println(Thread.currentThread().getName() + " is daemon: " + isDaemon());
        for(char i = 'A'; i <= 'J'; i++){
            try {
                sleep(300);
                System.out.println(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


class DaemonThread extends Thread{
    public void run(){
        System.out.println(Thread.currentThread().getName() + " is daemon: " + isDaemon());
        for(int i = 0; i < 1000; i++){
            try {
                sleep(300);
                System.out.println(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

