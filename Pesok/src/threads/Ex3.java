package threads;


// ИМЯ И ПРИОРИТЕТ ПОТОКОВ
//************************

// У потоков есть дефолтные название (Thread-0) и приоритет (5)
// Их можно кастомить


public class Ex3 {
    public static void main(String[] args) {

        Thread thread = new Thread(new MyThread5());

        System.out.println("(Default) Name of my Thread by default= " + thread.getName()
                + "\n(Default) Priority of my Thread = " + thread.getPriority());

        thread.setName("Мой поток");
        thread.setPriority(Thread.MAX_PRIORITY); // Приоритет можно просто указать цифрой от 1 до 10

        System.out.println("(Custom) Name of my Thread = " + thread.getName()
                + "\n(Custom) Priority of my Thread = " + thread.getPriority());

        thread.start();
    }
}


class MyThread5 implements Runnable{

    @Override
    public void run() {
        System.out.println("Privet");
    }
}
