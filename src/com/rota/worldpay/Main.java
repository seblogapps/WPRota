package com.rota.worldpay;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static com.rota.worldpay.Utils.*;


public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner=new Scanner(System.in);
        boolean checkContinue=true;
        LocalDate startWeekDate=null;
        LocalDate endWeekDate=null;
        List<Employee> employees;
        employees = ReadFromCSV.readFromCSVtoEmployees(CSVFILENAME);
        Utils.checkCSVHolidayFormat(employees);


        long totalWeeks=-1;

        while (totalWeeks<0){
          startWeekDate= Utils.getDate(scanner,"Start Date",DATE_PATTERN);
          endWeekDate= Utils.getDate(scanner,"End Date",DATE_PATTERN);
          totalWeeks=Utils.getTotalWeeks(startWeekDate, endWeekDate);
          if (totalWeeks<0){
              System.out.println("Start Date is less than End Date. Any key or enter to try again, n to exit");
              String again = scanner.nextLine();
              if (again.equalsIgnoreCase("n") || again.equalsIgnoreCase("no")) {
                  Utils.exitApplication();
              }
          }
      }

        LocalDate currentWeekMonday=Utils.getWeekMonday(startWeekDate);

        for (long i=0;i<=totalWeeks;i++) {

            Employee primary = PickOnCallEmp.pickPrimary(employees,Utils.getWeekNumber(currentWeekMonday),true);
            Employee secondary = PickOnCallEmp.pickSecondary(primary.getExperience(), employees,Utils.getWeekNumber(currentWeekMonday),true);

            Rota rotaToInsert = new Rota(currentWeekMonday, primary, secondary);
            rotas.add(rotaToInsert);
            currentWeekMonday=Utils.getNextWeek(currentWeekMonday);
        }



        // Create new ics calendar
        iCalUtils.createCalendar();
        System.out.println("Employees after Rota is generated");
        //Printing generated rota
        Utils.printRota();
        //Take backup of old CSV
        Utils.renameFile();
        //Write to CSV file
        WriteToCSV.writeCSVFile(employees, CSVFILENAME);

    }
}
