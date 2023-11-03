package threads;

// ВРЕМЯ ОЖИДАНИЯ В .JOIN()
//*************************


public class Ex6 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main starts");
        Thread thread = new Thread(new Worker());
        System.out.println("Состояние потока после его создания - " + thread.getState());
        thread.start();
        System.out.println("Состояние потока после метода .start() - " + thread.getState());
        thread.join();
//  В методе join можно прописывать время ожидания выполнения потока
//  тк поток будет спать 2.5 сек, а потоку Main мы прописали ожидание в 1.5 сек, Main через 1.5 сек продолжит работу
//  если прописать больше времени в join для ожидания потока, он продолжит работу после того, как поток закончит работу
//        thread.join(1500);
        System.out.println("Состояние потока после окончания ожидания по .join() - " + thread.getState());
        System.out.println("Main ends");
    }
}


class Worker implements Runnable{

    @Override
    public void run() {
        System.out.println("Work begins");
        System.out.println("*******************");
        System.out.println("Waiting for 2.5 sec");
        System.out.println("*******************");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Work is done");
    }
}
