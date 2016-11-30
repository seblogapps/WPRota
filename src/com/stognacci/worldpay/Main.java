package com.stognacci.worldpay;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final String DATE_PATTERN = "ddMMyyyy";
    public static void main(String[] args) throws  ParseException, IOException{
       // List<Employee> employees = new ArrayList<>();
        List<Rota> rotas = new ArrayList<>();
       // List<Holiday> testHolidayList = new

        Scanner scanner=new Scanner(System.in);
       /* Employee seb = new Employee("Sebastiano", "Tognacci", ExpLevel.EXP3, false, false);
        Employee nisha = new Employee("Nisha", "Monga", ExpLevel.EXP3, false, false);
        Employee mark = new Employee("Mark", "Angel-Trueman", ExpLevel.EXP1, false, false);
        Employee dave = new Employee("Dave", "Rees", ExpLevel.EXP2, false, false);
        Employee jose = new Employee("Jose", "Morena", ExpLevel.EXP1, false, false);
        Employee user1 = new Employee("test", "user", ExpLevel.EXP2, false, false);
        Employee user2 = new Employee("test2", "user2", ExpLevel.EXP1, false, false);

        employees.add(seb);
        employees.add(nisha);
        employees.add(mark);
        employees.add(dave);
        employees.add(jose);
        employees.add(user1);
        employees.add(user2);*/

        boolean checkContinue=true;
        LocalDate startWeekDate=null;
        LocalDate endWeekDate=null;
        String csvFileName = "Employees.csv";
        List<Employee> employees;// = new ArrayList<>();
        employees = ReadFromCSV.readFromCSVtoEmployees(csvFileName);
       /* for (Employee employee : employees) {
            System.out.println("employee = " + employee);
            for (Holiday holiday : employee.getHolidays()) {
                System.out.println("\tholiday = " + holiday);
            }
        }*/


      while (checkContinue){
          startWeekDate= Utils.getDate(scanner,"Start Date","ddMMyyyy");
          //checkContinue=false;
          endWeekDate= Utils.getDate(scanner,"End Date","ddMMyyyy");
          checkContinue=Utils.checkEnteredDate(scanner,startWeekDate,endWeekDate);
        }


        long totalWeeks=Utils.getTotalWeeks(startWeekDate, endWeekDate);
        LocalDate currentWeekMonday=Utils.getWeekMonday(startWeekDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
       // String formattedString = localDate.format(formatter);

        for (long i=0;i<=totalWeeks;i++) {
            System.out.println("");
            System.out.println("========================================");
            System.out.println("Generating Rota for Week " + Utils.getWeekNumber(currentWeekMonday) + " Start date: " + currentWeekMonday.format(formatter) + ", End date: " + Utils.getNextWeek(currentWeekMonday));
            System.out.println("========================================");

            Employee primary = PickOnCallEmp.pickPrimary(employees,Utils.getWeekNumber(currentWeekMonday));
            System.out.println("PRIMARY IS " + primary);

            Employee secondary = PickOnCallEmp.pickSecondary(primary.getExperience(), employees,Utils.getWeekNumber(currentWeekMonday));
            System.out.println("SECONDARY IS " + secondary);
            Rota rotaToInsert = new Rota(i, primary, secondary);
            rotas.add(rotaToInsert);
            currentWeekMonday=Utils.getNextWeek(currentWeekMonday);
        }
        System.out.println("Employees after Rota is generated");
        for (Rota rota : rotas) {
      //     System.out.println(rota);
        }
        System.out.println("Final state of array");
        for (Employee employee : employees) {
            System.out.println("employee = " + employee);
            }

    }
}
