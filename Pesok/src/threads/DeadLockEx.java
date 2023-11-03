package threads;

// DEADLOCK (случай синхронизации по мониторам 2+ объектов. Потоки ждут бесконечно)

// LIVELOCK (то же самое, только потоки работают без результата,
// а не просто ожидают (один поток записывает инфу, а второй удаляет))

// LOCK STARVATION (когда потоки с низким приоритетом очень долго или бесконечно ожидают своей очереди,
// пока работают потоки с высоким приоритетом)
//********************************************************

//  Thread20: попытка захватить монитор объекта lock2
//  Thread20: монитор объекта lock2 захвачен
//  Thread10: попытка захватить монитор объекта lock1
//  Thread10: монитор объекта lock1 захвачен
//  Thread10: попытка захватить монитор объекта lock2
//  Thread20: попытка захватить монитор объекта lock1

// Чтобы избежать этой ситуации, порядок захвата lock1 и lock2 должен быть одинаковым у обоих потоков

// *** сначала lock1, потом lock2. Если lock1 захвачен первым потоком, второй пытается его захватить и не может,
// тк он уже занят. В это время первый поток идет дальше и пытается захватить lock2, который 100% свободен,
// тк доступ к lock2 имеет поток, который уже захватил lock1


public class DeadLockEx {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread10 thread10 = new Thread10();
        Thread20 thread20 = new Thread20();
        thread10.start();
        thread20.start();
    }
}

class Thread10 extends Thread{
    public void run(){
        System.out.println("Thread10: попытка захватить монитор объекта lock1");
        synchronized (DeadLockEx.lock1){
            System.out.println("Thread10: монитор объекта lock1 захвачен");
            System.out.println("Thread10: попытка захватить монитор объекта lock2");
            synchronized (DeadLockEx.lock2){
                System.out.println("Thread10: мониторы объектов lock1 и lock2 захвачены");
            }
        }
    }
}

class Thread20 extends Thread{
    public void run(){
        System.out.println("Thread20: попытка захватить монитор объекта lock2");
        synchronized (DeadLockEx.lock2){
            System.out.println("Thread20: монитор объекта lock2 захвачен");
            System.out.println("Thread20: попытка захватить монитор объекта lock1");
            synchronized (DeadLockEx.lock1){
                System.out.println("Thread20: мониторы объектов lock2 и lock1 захвачены");
            }
        }
    }
}
