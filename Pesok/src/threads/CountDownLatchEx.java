package threads;

import java.util.concurrent.CountDownLatch;

//countDownLatch - концепция счетчика. Пример с магазином
//**************
//
//Есть n операций, которые нужно выполнить до того, как потокам будет позволено взяться за таску.
//
//static CountDownLatch countDownLatch = new CountDownLatch(3); В параметре кол-во операций.
//В конце операции, которая должна быть выполнена, прописываем countDownLatch.countDown();
//и счетчик уменьшается с 3 на 2. Когда счетчик будет 0 - все потоки, которые ожидали, начнут выполнение таски.
//
//В таске вызываем countDownLatch.await(); который будет проверять счетчик и удерживать поток, пока счетчик
//не упадет до 0 (т.е. пока все необходимые операции (кол-во в параметре) не будут выполнены)




public class CountDownLatchEx {
    static CountDownLatch countDownLatch = new CountDownLatch(3);
    private static void marketStaffIsOnPlace() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Работники на месте");
        countDownLatch.countDown();
        System.out.println("Счетчик countDownLatch = " + countDownLatch.getCount());
    }

    private static void everythingIsReady() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Полы помыли");
        countDownLatch.countDown();
        System.out.println("Счетчик countDownLatch = " + countDownLatch.getCount());
    }


    private static void openMarket() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("Открыли двери");
        countDownLatch.countDown();
        System.out.println("Счетчик countDownLatch = " + countDownLatch.getCount());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Готовим магаз к открытию...");
        Thread.sleep(5000);

        new Friend("Lexa", countDownLatch);
        new Friend("Vadik", countDownLatch);
        new Friend("Sergey", countDownLatch);
        new Friend("Semen", countDownLatch);
        new Friend("Elicey", countDownLatch);

        marketStaffIsOnPlace();
        everythingIsReady();
        openMarket();
    }
}


class Friend extends Thread{
    String name;
    private CountDownLatch countDownLatch;
    public Friend(String name, CountDownLatch countDownLatch){
        this.name = name;
        this.countDownLatch = countDownLatch;
        this.start();
    }

    public void run(){
        try {
            countDownLatch.await();
            System.out.println(name + " пошел шопиться");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
