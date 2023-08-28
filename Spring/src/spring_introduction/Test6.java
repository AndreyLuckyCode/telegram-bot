package spring_introduction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test6 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);

//        Pet petCat = context.getBean("catBean", Pet.class);
//        Pet petCat2 = context.getBean("CatBean", Pet.class);
//        petCat.say();

        Person person = context.getBean("personBean", Person.class);
        System.out.println(person.getSurname());
        System.out.println(person.getAge());

//        Person person2 = context.getBean("personBean", Person.class);
//        person.callYourPet();
    }
}
