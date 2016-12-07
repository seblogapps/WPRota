package com.stognacci.worldpay;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * Created by sebastianot on 18/11/16.
 */
public class Utils {

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String EMPLOYEE_CSV_FILENAME = "Employees.csv";
    public static final String ICAL_FILENAME = "WPRota.ics";
    public static final int SHIFT_HOUR_HANDOVER = 9;

    public static LocalDate getDate(Scanner scanner, String inputPrompt, String datePattern) {
        LocalDate date = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        while (date == null) {
            System.out.println(inputPrompt + " as " + datePattern);
            String userInput = scanner.nextLine();
            try {
                date = LocalDate.parse(userInput, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Date entered : " + userInput + " is wrong - Any key or enter to try again, n to exit");
                String again = scanner.nextLine();
                if (again.equalsIgnoreCase("n") || again.equalsIgnoreCase("no")) {
                    System.exit(0);
                }
            }
        }
        return date;
    }

    public static long getTotalWeeks(LocalDate startWeekDate, LocalDate endWeekDate) {
        return ChronoUnit.WEEKS.between(startWeekDate, endWeekDate);
    }

    public static int getWeekNumber(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        return weekNumber;
    }

    public static LocalDate getWeekMonday(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    public static LocalDate getWeekSunday(LocalDate date) {
        return date.with(DayOfWeek.SUNDAY);
    }

    public static LocalDate getNextWeek(LocalDate currentWeekDate) {
        currentWeekDate = currentWeekDate.plusWeeks(1);
        return currentWeekDate;
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static boolean areHolidayDatesValid(List<Employee> employees) {
        for (Employee employee : employees) {
            for (Holiday holiday : employee.getHolidays()) {
                if (holiday.getHolidayStart() != null) {
                    if (holiday.getHolidayEnd() == null) {
                        System.out.println("Employee: " + employee.getFirstName() + employee.getLastName() + ", Holiday End Date not specified for Start Date: " + holiday.getHolidayStart());
                        System.out.println("Please fix CSV, Cannot proceed with ROTA creation");
                        return false;
                    } else if (holiday.getHolidayEnd().before(holiday.getHolidayStart())) {
                        System.out.println("Employee: " + employee.getFirstName() + employee.getLastName() + ", Start holiday:" + holiday.getHolidayStart() + " is after than end Holiday " + holiday.getHolidayEnd());
                        System.out.println("Please fix CSV, Cannot proceed with ROTA creation");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void exitApplication() {
        System.out.println("Rota Generation unsuccessful" + "\n" + "Exiting from the application");
        System.exit(0);
    }
}

