import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator extends JFrame implements ActionListener {           // Интерфейс для считывания действий в окне

    static JFrame frame;                                                                  // Переменная (=поле) для окна
    static JTextField result;                                                      // Переменная (= поле) для поля ввода
    static String a = "";                                                      // 2 поля для вычислений и 1 для операций
    static String b = "";
    static String operation = "";

    public static void main(String[] args) {

        Calculator listen = new Calculator();     // Через конструктор создаем переменную для считывания действий в окне

        frame = new JFrame("Calculator");                  // С помощью конструктора создаем новый объект (физ окно)

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // Метод, который офает код, когда окно закрывается
        result = new JTextField(16);              // С помощью конструктора создаем новый объект (поле для ввода)
        result.setEditable(false);              // Вызываем метод setEditable у объекта result (делаем неизменяемым поле)


        ArrayList<JButton> btns = new ArrayList<>();     // Создаем новую коллекцию ArrayList. Тип данных - JButton (класс, который предоставляет кнопки из библиотеки Swing)
        for (int i = 0; i < 10; i++) {      // На каждой итерации создаем новый объект JTmpButton на основе класса JButton
            JButton jTmpButton = new JButton(Integer.toString(i));   // Сразу же конвертим i с инта в стринг, тк объект на базе класса JButton создается стринговый (в виде надписи на кнопке)
            jTmpButton.addActionListener(listen);    //Объект listen регистрируется в качестве слушателя событий для кнопки TmpButton. При нажатии на эту кнопку будет вызываться метод actionPerformed() объекта listen.
            btns.add(jTmpButton);            // На каждой итерации кладем в наш btns (ArrayList) новый объект jTmpButton
        }

        List<String> operations = Arrays.asList("+", "-", "/", "*", "C", "=");  // Создаем List с данными типа String под названием operations
        // asList - метод класса Arrays, который конвертит массив в лист

        //JPanel - это компонент Swing, представляющий собой контейнер для размещения других компонентов.
        JPanel buttons = new JPanel();                       // Создаем объект buttons на основе класса JPanel (buttons - экземпляр JPanel).
        btns.forEach(buttons::add);                          // Вызываем метод forEach для объекта btns (наш ArrayList, в который положили цифры (i) в формате String). forEach - метод класса ArrayList (делает что-то для каждого элемента)
// Конструкция someObject::someMethod - ссылка на метод (ссылка на метод add для объекта buttons). Есть полная форма этой темы (какие-то lambda выражения)
// По итогу здесь мы для каждого элемента ArrayList btns (тк forEach) добавляем кнопку на панели в нашем окне


        operations.forEach(it -> {         // Обращаемся к нашему листу operations и для каждого элемента (forEach метод)...
            JButton jTmpButton = new JButton(it);      // Создаем объект jTmpButton а базе класса JButton, где it - имя элемента нашего листа
            jTmpButton.addActionListener(listen);      // Объект listen регистрируется в качестве слушателя событий для кнопки TmpButton. При нажатии на эту кнопку будет вызываться метод actionPerformed() объекта listen.
            buttons.add(jTmpButton);                   // Добавляем кнопку через метод add (кнопку, которую создаем в предыдущей строчке)
        });                                            // Это лямбда-выражение (в нашем случае - аналог цикла)

        GridLayout numsAndOpsLayout = new GridLayout(4, 4);       // Через конструктор класса GridLayout создаем объект и задаем параметры. Заданные параметры формируют сетку из столбцов и строк
        buttons.setLayout(numsAndOpsLayout);                               // Для объекта buttons вызываем метод setLayout и указываем объект, параметры которого будут применены к buttons

        buttons.setPreferredSize(new Dimension(200, 200));  // У объекта buttons вызывается метод setPreferredSize. А в качестве аргумента этому методу передается новый объект класса Dimension.
        // Dimension - это специальный класс в Swing для задания размеров (ширины и высоты) компонентов.
        result.setPreferredSize(new Dimension(300, 30));    // Аналогично

        JPanel mainPanel = new JPanel();                     // Создаем объект mainPanel через конструктор класса JPanel
        mainPanel.add(result);                                     // Через метод add добавляем объекты result и buttons
        mainPanel.add(buttons);       // Фактически, мы не трогаем объекты result и buttons. В mainPanel мы кладем ССЫЛКИ на result и buttons. Поэтому, при использовании mainPanel, через ссылки будут вызываться result и buttons

        frame.add(mainPanel);                            // Выше мы добавили в buttons все из ArrayList btns, потом добавили туда же все из листа operations. А сейчас мы добавляем buttons со всей внутрянкой в окно frame
        frame.setSize(300, 300);             // Вызываем метод setSize для объекта frame (задаем размер окна)
        frame.setVisible(true);           // Вызываем метод setVisible для объекта frame (делаем видимым/выводим на экран)


    }

// Мы имплеиентировали интерфейс ActionListener. От него нам нужен метод actionPerformed, который будет вызываться при
// нажатии на кнопку в окне (эту логику пихнули в листах). параметр e, который в скобках, содержит данные о событии.
// Override - мы переписываем тело метода, который мы наследуем из другого класса или интерфейса (Наследование + Полиморфизм)

    @Override
    public void actionPerformed(ActionEvent e) {
       System.out.println(e.getActionCommand());      // Прописываем вывод в консоль значений, которые падают в параметр е, после нажатия на кнопки в окне

        String s = e.getActionCommand();              // СОздаем переменную, в которую будем класть значения, считанные параметром е, при нажатии на кнопки в окне
        if(s.charAt(0) >= '0' && s.charAt(0) <= '9'){   // Если нажали на кнопку от 0 до 9, и операций нет - записываем первую цифру
            if(operation.equals("")){
                a = a + s;
            } else
                b = b + s;                              // Если операция уже была - записываем вторую цифру
            result.setText(a + operation + b);          // Вывод инфы в поле
        } else if(s.charAt(0) == 'C'){                  // Если прилетает С - делитаем все поле
            a = operation = b = "";
            result.setText(a + operation + b);          // Вывод инфы в поле
        } else if (s.charAt(0) == '='){                 // Если прилетает =, тогда идем в switch
            int rslt = switch(operation){
                case "+" -> Integer.parseInt(a) + Integer.parseInt(b);       // Перебираем операции
                case "-" -> Integer.parseInt(a) - Integer.parseInt(b);
                case "/" -> Integer.parseInt(a) / Integer.parseInt(b);
                default -> Integer.parseInt(a) * Integer.parseInt(b);
            };
            a = String.valueOf(rslt);                 // Кладем результат в первую переменную и выводим результат в поле
            result.setText(a);
            operation = b = "";                       // А операцию обнуляем через вторую переменную
        } else {
            if(operation.equals(""))                  // Если операции нет - кладем s (которая е, которая параметр интерфейса)
                operation = s;
            result.setText(a + operation + b);
        }

    }
}
