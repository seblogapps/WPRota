package com.stognacci.worldpay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        // write your code here
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

        Employee seb = new Employee("Sebastiano", "Tognacci", ExpLevel.EXP3);
        Employee nisha = new Employee("Nisha", "Monga", ExpLevel.EXP3);
        Employee mark = new Employee("Mark", "Angel-Trueman", ExpLevel.EXP1);
        Employee dave = new Employee("Dave", "Reese", ExpLevel.EXP2, testHolidayStartDate, testHolidayEndDate);

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(seb);
        employees.add(nisha);
        employees.add(mark);
        employees.add(dave);
        for (Employee employee : employees) {
            System.out.println("employee = " + employee);
        }
        System.out.println("");
        Employee primary = employees.get(0);
        System.out.println("primary = " + primary);
        Collections.rotate(employees, -1);
        Employee secondary = employees.get(0);
        System.out.println("secondary = " + secondary);
        Collections.rotate(employees, -1);

//        Collections.shuffle(employees);

        for (Employee employee : employees) {
            System.out.println("Rotated after primary and secondary = " + employee);
        }


    }
}
