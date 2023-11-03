package threads;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

//EXCHANGER
//*********

// Используется, когда потоки должны обмениваться информацией одновременно.
// Если 1 поток сделал таску и готов отдать инфу 2 потоку, а 2 поток еще не закончил, 1 поток будет ждать.
// Когда 2 поток закончит свою таску, он меняется инфой с 1 потоком одновременно


public class ExchangerEx {
    public static void main(String[] args) {
        Exchanger<Action> exchanger = new Exchanger<>();
        List<Action> firstFriendAction = new ArrayList<>();
        firstFriendAction.add(Action.NOZJNICI);
        firstFriendAction.add(Action.BUMAGA);
        firstFriendAction.add(Action.KAMEN);
        List<Action> secondFriendAction = new ArrayList<>();
        secondFriendAction.add(Action.BUMAGA);
        secondFriendAction.add(Action.NOZJNICI);
        secondFriendAction.add(Action.NOZJNICI);
        new BestFriend("Vanya", firstFriendAction, exchanger);
        new BestFriend("Lexa", secondFriendAction, exchanger);
    }
}


enum Action{
    KAMEN, NOZJNICI, BUMAGA;
}


class BestFriend extends Thread{
    private String name;
    private List<Action> myActions;
    private Exchanger<Action> exchanger;

    public BestFriend(String name, List<Action> myActions, Exchanger<Action> exchanger) {
        this.name = name;
        this.myActions = myActions;
        this.exchanger = exchanger;
        this.start();
    }

    private void whoWins(Action myAction, Action friendsAction){
        if((myAction==Action.KAMEN && friendsAction==Action.NOZJNICI)
        ||(myAction==Action.NOZJNICI && friendsAction==Action.BUMAGA)
        ||(myAction==Action.BUMAGA && friendsAction==Action.KAMEN)){
            System.out.println(name + " WINS!!");
        }
    }

    public void run(){
        Action reply;
        for(Action action: myActions){
            try {
                reply=exchanger.exchange(action);
                whoWins(action, reply);
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
