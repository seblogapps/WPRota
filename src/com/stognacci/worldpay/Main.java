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
        Employee seb = new Employee("Sebastiano", "Tognacci", ExpLevel.EXP3);
        Employee nisha = new Employee("Nisha", "Monga", ExpLevel.EXP3);
        Employee mark = new Employee("Mark", "Angel-Trueman", ExpLevel.EXP1);
        Employee jose = new Employee("Jose", "Morena", ExpLevel.EXP1);
        Employee dave = new Employee("Dave", "Reese", ExpLevel.EXP2, testHolidayStartDate, testHolidayEndDate);
        Employee roy = new Employee("Roy","Reicher",ExpLevel.EXP3);
        Employee hernan = new Employee("Hernan", "Rizzuti", ExpLevel.EXP2);
        Employee bruno = new Employee("Bruno", "Dias", ExpLevel.EXP1);

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
        Employee primary = employees.get(0);
        System.out.println("Primary exp = " + primary.getExperience());
        System.out.println("primary = " + primary);
        Collections.rotate(employees, -1);
        Employee secondary = employees.get(0);
        System.out.println("secondary = " + secondary);
        Collections.rotate(employees, -1);
        Rota rotaToInsert = new Rota(0, primary, secondary);
        rotas.add(rotaToInsert);

        for (Rota rota : rotas) {
            System.out.println("rota = " + rota);
        }

//        Collections.shuffle(employees);

        for (Employee employee : employees) {
            System.out.println("Rotated after primary and secondary = " + employee);
        }


    }
}
