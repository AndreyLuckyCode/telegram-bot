package spring_introduction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component ("personBean")
public class Person {
//    @Autowired
//    @Qualifier("catBean")
    private Pet pet;
    @Value("${person.surname}")
    private String surname;
    @Value("${person.age}")
    private int age;

//    @Autowired
//    public Person(@Qualifier("catBean") Pet pet){
//        System.out.println("Person bean is created");
//        this.pet = pet;
//   }
    public Person(Pet pet){
        System.out.println("Person bean is created");
        this.pet = pet;
    }

//    public Person(){
//        System.out.println("Person bean is created");
//    }        // Пустой конструктор


//    @Autowired
//    @Qualifier("dog")
    public void setPet(Pet pet) {
        System.out.println("Class Person: pet setter");
        this.pet = pet;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        System.out.println("Class Person: surname setter");
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("Class Person: age setter");
        this.age = age;
    }




    public void callYourPet (){
        System.out.println("Hello, my lovely Pet!");
        pet.say();
    }
}
