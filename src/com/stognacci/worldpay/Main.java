package com.stognacci.worldpay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        List<Rota> rotas = new ArrayList<>();
        
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        String holidayStartDate = "23-12-2016";
        String holidayEndDate = "31-12-2016";
        Date testHolidayStartDate = null, testHolidayEndDate = null;
        try {
            testHolidayStartDate = ft.parse(holidayStartDate);
            testHolidayEndDate = ft.parse(holidayEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Test set of employee
        Employee seb = new Employee("Sebastiano", "Tognacci", ExpLevel.EXP3, true, false);
        Employee nisha = new Employee("Nisha", "Monga", ExpLevel.EXP3, true, false);
        Employee mark = new Employee("Mark", "Angel-Trueman", ExpLevel.EXP1, false, true);
        Employee jose = new Employee("Jose", "Morena", ExpLevel.EXP1, false, false);
        Employee dave = new Employee("Dave", "Reese", ExpLevel.EXP2, false, false);
        Employee roy = new Employee("Roy","Reicher",ExpLevel.EXP3, false, false);
        Employee hernan = new Employee("Hernan", "Rizzuti", ExpLevel.EXP2, false, false);
        Employee bruno = new Employee("Bruno", "Dias", ExpLevel.EXP1, false, false);

        // Add all employee to employees arraylist
        employees.add(seb);
        employees.add(nisha);
        employees.add(mark);
        employees.add(dave);
        employees.add(jose);
        employees.add(roy);
        employees.add(hernan);
        employees.add(bruno);

        // Print out employees arraylist
        for (Employee employee : employees) {
            System.out.println("employee = " + employee);
        }

        System.out.println("");
        for (int i = 0; i < 10; i++) {
            Employee primary = PickEmployee.pickPrimary(employees);
            System.out.println("primary   = " + primary);

            Employee secondary = PickEmployee.pickSecondary(employees, primary);
            System.out.println("secondary = " + secondary);

            Rota rotaToInsert = new Rota(i+1, primary, secondary);
            rotas.add(rotaToInsert);
        }

//        Employee primary = employees.get(0);
//        System.out.println("Primary exp = " + primary.getExperience());
//        System.out.println("primary = " + primary);
//        Collections.rotate(employees, -1);
//        Employee secondary = employees.get(0);
//        System.out.println("secondary = " + secondary);
//        Collections.rotate(employees, -1);


        for (Rota rota : rotas) {
            System.out.println(rota);
        }

//        Collections.shuffle(employees);

        for (Employee employee : employees) {
            System.out.println("Employees after primary and secondary selected = " + employee);
        }


    }
}
