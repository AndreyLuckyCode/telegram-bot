package threads;


// СОЗДАНИЕ ПОТОКОВ
//*****************
// psvm - тоже поток

// Создаем потоки через классы, имплементирующие Runnable

// Создаем класс (объект которого будет потоком)
// Имплементируем Runnable
// Переопределяем метод run и пишем в нем логику

// Как обычно создаем в psvm объект класса, который имплементирует Runnable
// Вызываем метод .start()  (НЕ МЕТОД RUN!!!)


public class Ex1 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyThread1());
        Thread thread2 = new Thread(new MyThread2());
        thread1.start();
        thread2.start();
    }
}



class MyThread1 implements Runnable{
    public void run (){
        for (int i = 0; i<1000; i++){
            System.out.println("Thread - 1 " + i);
        }
    }
}


class MyThread2 implements Runnable{
    public void run(){
        for (int i = 1000; i>0; i--){
            System.out.println("Thread - 2 " + i);
        }
    }
}
