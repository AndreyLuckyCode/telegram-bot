package threads;

// SYNCHRONIZED БЛОКИ И МОНИТОР. Пример синхронизации 3х методов
//*************************************************************

// Если нужно синхронизировать 3 метода (блоки), нужна общая переменная с монитором (свободно/занято).
// Создается static final Object lock = new Object(); по которому будут синхронизироваться блоки.

// synchronized (lock){блок кода} + то же самое для других методов.
// Один поток заходит в 1 из 3 методов и пока он там, другие потоки не имеют доступа к остальным методам,
// тк, у них 1 монитор объекта lock на 3 потока, который сейчас занят первым потоком в первом методе.





public class Ex12 {

    static final Object lock = new Object();
    void mobileCall() {
        synchronized (lock) {
            System.out.println("Mobile call STARTS");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Mobile call ENDS");
        }
    }

    void skypeCall() {
        synchronized (lock) {
            System.out.println("Skype call STARTS");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Skype call ENDS");
        }
    }

    void whatsAppCall() {
        synchronized (lock) {
            System.out.println("WhatsApp call STARTS");
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("WhatsApp call ENDS");
        }
    }


    public static void main(String[] args) {
        Thread thread1 = new Thread(new RunnableImplMobile());
        Thread thread2 = new Thread(new RunnableImplSkype());
        Thread thread3 = new Thread(new RunnableImplWhatsApp());

        thread1.start();
        thread2.start();
        thread3.start();
    }
}



class RunnableImplMobile implements Runnable{

    @Override
    public void run() {
        new Ex12().mobileCall();
    }
}


class RunnableImplSkype implements Runnable{

    @Override
    public void run() {
        new Ex12().skypeCall();
    }
}


class RunnableImplWhatsApp implements Runnable{

    @Override
    public void run() {
        new Ex12().whatsAppCall();
    }
}
