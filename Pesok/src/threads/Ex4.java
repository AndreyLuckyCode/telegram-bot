package threads;

// МЕТОД SLEEP базовый пример
//***************************
// По сути, это просто delay
// При использовании .sleep() нужно прописать exception в методе
// или блок try catch в переопределении метода run()

public class Ex4 {
    public static void main(String[] args) throws InterruptedException {
        for(int i = 5; i > 0; i--){
            System.out.println(i);
            Thread.sleep(1000);
        }
        System.out.println("Loop завершен");
    }
}
