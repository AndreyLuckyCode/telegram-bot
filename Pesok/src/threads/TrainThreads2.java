package threads;

// Класс, реализующий дочерний поток в задаче
class ThreadMinMax implements Runnable {
    private Thread thr; // ссылка на дочерний поток
    private int[] AI;
    private int maximum; // максимальное значение
    private int minimum; // минимальное значение

    // Конструктор - получает массив целых чисел
    public ThreadMinMax(int[] _AI) {
        // Инициализация массива
        AI = _AI;

        // Создать поток
        thr = new Thread(this, "Thread1.");

        // Запустить поток выполнения
        thr.start();
    }

    // Метод, в котором вписывается код выполнения потока
    // В нашем случае вписывается код поиска минимального   значения
    // и заполнение переменной maximum.
    public void run() {
        int max = AI[0];
        int min = AI[0];
        for (int i=1; i<AI.length; i++) {
            if (max<AI[i]) max = AI[i];
            if (min>AI[i]) min = AI[i];
        }
        maximum = max;
        minimum = min;
    }

    // Методы доступа к полям класса
    public Thread getThread() { return thr; }
    public int getMax() { return maximum; }
    public int getMin() { return minimum; }
}

public class TrainThreads2 {

    public static void main(String[] args) {
        // 1. Объявить тестируемый массив
        int[] AI = { 2, 3, 4, 8, -1 };

        // 2. Создать два дочерних потока, получить ссылки на них
        ThreadMinMax t1 = new ThreadMinMax(AI);
        ThreadMinMax t2 = new ThreadMinMax(AI);

        // 3. Прочитать результат
        try {
            // Ожидание завершения потоков t1, t2 - обязательно,
            // иначе можно получить нулевые значения
            t1.getThread().join();
            t2.getThread().join();
        }
        catch (InterruptedException e) {
            System.out.println("Error.");
        }

        // Прочитать результат после завершения потоков t1, t2
        System.out.println("max = " + t1.getMax());
        System.out.println("min = " + t1.getMin());
    }
}