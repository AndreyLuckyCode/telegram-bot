package threads;


// Создаем поток в 1 строчку через лямбду
//***************************************

// Интерфейс Runnable функциональный, поэтому в теле просто пишем логику
// Вызываем .start()


public class Ex2 {
    public static void main(String[] args) {


        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
            }
        }).start();


    }
}
