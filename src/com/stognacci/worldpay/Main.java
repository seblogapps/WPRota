package com.stognacci.worldpay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DATE_PATTERN = "ddMMyyyy";

    public static void main(String[] args) {
        //List<Employee> employees = new ArrayList<>();
        List<Rota> rotas = new ArrayList<>();
        int weeksForRota = -1;
        
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        String holidayStartDate = "23-12-2016";
        String holidayEndDate = "31-12-2016";
        Date testHolidayStartDate = null, testHolidayEndDate = null;
        List<Holiday> testHolidayList = new ArrayList<>();
        try {
            testHolidayStartDate = ft.parse(holidayStartDate);
            testHolidayEndDate = ft.parse(holidayEndDate);
            Holiday testHoliday = new Holiday(testHolidayStartDate, testHolidayEndDate);
            testHolidayList.add(0, testHoliday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String csvFileName = "Employees.csv";
        List<Employee> employees;// = new ArrayList<>();
        employees = ReadFromCSV.readFromCSVtoEmployees(csvFileName);
        for (Employee employee : employees) {
            System.out.println("employee = " + employee);
            for (Holiday holiday : employee.getHolidays()) {
                System.out.println("\tholiday = " + holiday);
            }
        }

        //Ask for rota starting and ending date
        while (weeksForRota == -1) {
            Scanner scanner = new Scanner(System.in);
            int rotaWeekStart = Utils.getWeekFromDate(scanner, "Please enter Rota starting date", DATE_PATTERN);
            System.out.println("rotaWeekStart = " + rotaWeekStart);
            int rotaWeekEnd = Utils.getWeekFromDate(scanner, "Please enter Rota ending date", DATE_PATTERN);
            System.out.println("rotaWeekEnd = " + rotaWeekEnd);
            weeksForRota = Utils.getDeltaWeeks(rotaWeekStart, rotaWeekEnd);
        }
        System.out.println("weeksForRota = " + weeksForRota);

        //10 January 2016
        LocalDate date3 = LocalDate.of(2016, Month.JANUARY, 10);
        //20 January 2016
        LocalDate date20Jan = LocalDate.of(2016, Month.JANUARY, 20);
        //30 January 2016
        LocalDate date30Jan = LocalDate.of(2016, Month.JANUARY, 30);
        //1 February 2016
        LocalDate date1Feb = LocalDate.of(2016, Month.FEBRUARY, 1);

        System.out.println("Utils.isInBetweenDates(date20Jan, date1Feb, date30Jan) = " + Utils.isInBetweenDates(date30Jan, date1Feb, date20Jan));

        System.out.println("date3: " + date3);

        //Get the current date
        LocalDate date1 = LocalDate.now();
        System.out.println("Current date: " + date1);

        //add 1 month to the current date
        LocalDate date2 = date1.plus(1, ChronoUnit.MONTHS);
        System.out.println("Next month: " + date2);

        Period period = Period.between(date2, date1);
        System.out.println("Period: " + period);

//        // Test set of employee
//        Employee seb = new Employee("Sebastiano", "Tognacci", ExpLevel.EXP3, false, false, testHolidayList);
//        Employee nisha = new Employee("Nisha", "Monga", ExpLevel.EXP3, false, false);
//        Employee mark = new Employee("Mark", "Angel-Trueman", ExpLevel.EXP1, false, true);
//        Employee jose = new Employee("Jose", "Morena", ExpLevel.EXP1, false, false);
//        Employee dave = new Employee("Dave", "Reese", ExpLevel.EXP2, false, false);
//        Employee roy = new Employee("Roy","Reicher",ExpLevel.EXP3, false, false);
//        Employee hernan = new Employee("Hernan", "Rizzuti", ExpLevel.EXP2, false, false);
//        Employee bruno = new Employee("Bruno", "Dias", ExpLevel.EXP1, false, false);
//
//        // Add all employee to employees arraylist
//        employees.add(seb);
//        employees.add(nisha);
//        employees.add(mark);
//        employees.add(dave);
//        employees.add(jose);
//        employees.add(roy);
//        employees.add(hernan);
//        employees.add(bruno);

        // Print out employees arraylist
        System.out.println("employee List");
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        System.out.println("");
        for (int i = 0; i < weeksForRota; i++) {
            Employee primary = PickEmployee.pickPrimary(employees);
            //System.out.println("primary   = " + primary);

            Employee secondary = PickEmployee.pickSecondary(employees, primary);
            //System.out.println("secondary = " + secondary);

            PickEmployee.addToEnd(employees, primary, secondary);

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

        System.out.println("Employees after Rota is generated");
        for (Employee employee : employees) {
            System.out.println(employee);
        }


    }


}
