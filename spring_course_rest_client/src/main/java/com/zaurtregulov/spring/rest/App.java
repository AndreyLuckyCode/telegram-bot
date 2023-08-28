package com.zaurtregulov.spring.rest;

import com.zaurtregulov.spring.rest.configuration.MyConfig;
import com.zaurtregulov.spring.rest.entity.Employee;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
//
// *** GET метод
//
//        List<Employee> allEmployees = communication.getAllEmployees();
//        System.out.println(allEmployees);

// *** GET метод по ID

//        Employee empByID = communication.getEmployee(1);
//        System.out.println(empByID);

// *** SAVE/UPDATE метод

//        Employee emp = new Employee("Lexa", "Miray", "IT", 1200);
//        emp.setId(14);
//        communication.saveEmployee(emp);

// *** DELETE метод
//        communication.deleteEmployee(14);

    }
}
