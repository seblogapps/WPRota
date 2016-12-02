package com.stognacci.worldpay;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Version;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static com.stognacci.worldpay.Utils.*;


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
        Calendar newCal = iCalUtils.setCalendar("-//Worldpay WPRota//iCal4j 2.0.0//EN", Version.VERSION_2_0, CalScale.GREGORIAN);

        // Add Rota event to calendar
        for (Rota rota : rotas) {
            VEvent rotaEventToAdd = iCalUtils.setEvent(rota.toStringforEventDescription(), rota.getWeek(), Utils.SHIFT_HOUR_HANDOVER);
            Attendee attendee1 = iCalUtils.setAttendee(rota.getPrimary(), "Primary", Role.REQ_PARTICIPANT);
            Attendee attendee2 = iCalUtils.setAttendee(rota.getSecondary(), "Secondary", Role.REQ_PARTICIPANT);
            rotaEventToAdd.getProperties().add(attendee1);
            rotaEventToAdd.getProperties().add(attendee2);
            newCal.getComponents().add(rotaEventToAdd);
        }
        System.out.println("Employees after Rota is generated");
        Utils.printRota();

        // Write generated calendar file
        if (newCal != null) {
            iCalUtils.writeIcal(newCal, Utils.ICAL_FILENAME);
        }

        Utils.renameFile();
            WriteToCSV.writeCSVFile(employees, CSVFILENAME);

    }
}
