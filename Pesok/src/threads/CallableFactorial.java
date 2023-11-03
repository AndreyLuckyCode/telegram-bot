package threads;

import java.util.concurrent.*;


// CALLABLE И FUTURE
//******************

//Callable - аналог Runnable, только с возмрожностью выбрасывать исключения и с return type. Принимает Generic.
// Работает только с Executor, вручную создать поток с Callable нельзя.

// Если не нужно, чтобы таск возвращал результат - Runnable, если результат нужен - Callable

//        Вызывается через .submit(); (передает таск в thread pool и возвращает тип Future, в котором лежит результат таска
//
//Учитывая, что у него есть return, то есть результат работы потока, он его хранит в Future,
//        из которого можно достать результат через Future<Integer> future = executorService.submit(factorial); +
//+ factorialResult = future.get();

// Exception можем ловить через e.getCause();


public class CallableFactorial {
    static int factorialResult;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Factorial factorial= new Factorial(6);
        Future<Integer> future = executorService.submit(factorial);
        try {
            System.out.println("Хотим получить результат");
            System.out.println("Таск завершен? "+ future.isDone());
            factorialResult = future.get();
            Thread.sleep(2000);
            System.out.println("Получили результат");
            System.out.println("Таск завершен? " + future.isDone());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            System.out.println(e.getCause());
        }
        finally {
            executorService.shutdown();
        }
        System.out.println("Результат таска: " + factorialResult);

    }
}



class Factorial implements Callable<Integer>{
    int f;

    public Factorial(int f){
        this.f=f;
    }
    @Override
    public Integer call() throws Exception {
        if(f<=0){throw new Exception("Вы ввели некорректное число");}
        System.out.println("Выполняем таск...");
        int result = 1;
        for (int i = 1; i<=f;i++){
            result *= i;
            Thread.sleep(1000);
        }
        System.out.println("Таск выполнен, передаем значение в Future");
        return result;
    }
}