package com.stognacci.worldpay;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Version;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //List<Employee> employees = new ArrayList<>();
        List<Rota> rotas = new ArrayList<>();
        //int weeksForRota = -1;
        long weeksForRota = -1;
        LocalDate startWeekDate = null;
        LocalDate endWeekDate = null;

        List<Employee> employees;
        employees = ReadFromCSV.readFromCSVtoEmployees(Utils.EMPLOYEE_CSV_FILENAME);
        for (Employee employee : employees) {
            System.out.println("employee = " + employee);
            for (Holiday holiday : employee.getHolidays()) {
                System.out.println("\tholiday = " + holiday);
            }
        }

        //Ask for rota starting and ending date
        while (weeksForRota <= 0) {
            Scanner scanner = new Scanner(System.in);
            startWeekDate = Utils.getDate(scanner, "Please enter Rota starting date", Utils.DATE_PATTERN);
            endWeekDate = Utils.getDate(scanner, "Please enter Rota ending date", Utils.DATE_PATTERN);
            weeksForRota = Utils.getTotalWeeks(startWeekDate, endWeekDate);
            if (weeksForRota <= 0) {
                System.out.println("End date must be at least 1 week after start date, please enter the dates again");
            }
            System.out.println("weeksForRota = " + weeksForRota);
        }

        // Print out employees arraylist
        System.out.println("employee List");
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        System.out.println("");
        LocalDate rotaWeekDate = startWeekDate;
        for (int i = 0; i <= weeksForRota; i++) {
            Employee primary = PickEmployee.pickPrimary(employees);

            Employee secondary = PickEmployee.pickSecondary(employees, primary);

            PickEmployee.addToEnd(employees, primary, secondary);

            Rota rotaToInsert = new Rota(rotaWeekDate, primary, secondary);
            rotas.add(rotaToInsert);
            rotaWeekDate = Utils.getNextWeek(rotaWeekDate);
        }

        // Create new ics calendar
        Calendar newCal = iCalUtils.setCalendar("-//Worldpay WPRota//iCal4j 2.0.0//EN", Version.VERSION_2_0, CalScale.GREGORIAN);

        for (Rota rota : rotas) {
            System.out.println(rota);
            // Add Rota event to calendar
            VEvent rotaEventToAdd = iCalUtils.setEvent(rota.toStringforEventDescription(), rota.getWeek(), Utils.SHIFT_HOUR_START);
            Attendee attendee1 = iCalUtils.setAttendee(rota.getPrimary(), "Primary", Role.REQ_PARTICIPANT);
            Attendee attendee2 = iCalUtils.setAttendee(rota.getSecondary(), "Secondary", Role.REQ_PARTICIPANT);
            rotaEventToAdd.getProperties().add(attendee1);
            rotaEventToAdd.getProperties().add(attendee2);
            newCal.getComponents().add(rotaEventToAdd);
        }
        // Write generated calendar file
        if (newCal != null) {
            iCalUtils.writeIcal(newCal, Utils.ICAL_FILENAME);
        }

        // Debug print generated calendar
        System.out.println("newCal = " + newCal);

        System.out.println("Employees after Rota is generated");
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        // Write the updated employees list to csv file
        WriteToCSV.writeEmployeesToCSV(employees, "NewEmployees.csv");
    }
}
