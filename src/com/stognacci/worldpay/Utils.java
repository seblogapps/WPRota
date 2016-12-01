package com.stognacci.worldpay;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by nisham on 16/11/16.
 */
public class Utils {

    public static final String CSVFILENAME = "Employees.csv";
   // public static final String DATE_PATTERN = "dd/MM/yyyy";

    public static final String DATE_PATTERN = "ddMMyyyy";
    public static final String ICAL_FILENAME = "WPRota.ics";


    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static void checkCSVHolidayFormat(List<Employee> employees) {
        for (Employee employee : employees) {
            for (Holiday holiday : employee.getHolidays()) {
                if (holiday.getHolidayStart() != null) {
                    if (holiday.getHolidayEnd() == null) {
                        System.out.println("Employee: " + employee.getFirstName() + employee.getLastName() + ", Holiday End Date not specified, Start Date: " + holiday.getHolidayStart());
                        exitApplication();
                    } else {
                        if (Utils.getTotalWeeks(convertToLocalDate(holiday.getHolidayStart()), convertToLocalDate(holiday.getHolidayEnd())) < 0) {
                            System.out.println("Employee: " + employee.getFirstName() + employee.getLastName() + ", Start holiday:" + holiday.getHolidayStart() + " is more than end Holiday " + holiday.getHolidayEnd());
                            exitApplication();
                        }
                    }
                } else {
                    if (holiday.getHolidayEnd() != null) {
                        System.out.println("Employee: " + employee.getFirstName() + employee.getLastName() + ", Holiday Start Date not specified, End Date: " + holiday.getHolidayEnd());
                        exitApplication();
                    }
                }
            }
        }
    }


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
                    exitApplication();
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

    public static LocalDate getNextWeek(LocalDate currentWeekDate) {
        currentWeekDate = currentWeekDate.plusDays(7);
        return currentWeekDate;
    }

    public static LocalDate getWeekMonday(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    public static LocalDate getWeekSunday(LocalDate date) {
        return date.with(DayOfWeek.SUNDAY);
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static void exitApplication() {
        System.out.println("Exiting from the application");
        System.exit(0);
    }

    public static void renameFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_Hms");
        String currentdate= String.valueOf(LocalDateTime.now().format(formatter));
        File oldfile =new File("/home/nisham/WorkSpace/WPRota/Employees.csv");
        File newfile =new File("/home/nisham/WorkSpace/WPRota/Employees"+currentdate+".csv");

        if(!oldfile.renameTo(newfile)){
            System.out.println("Rename failed: "+oldfile);
        }
    }

}






