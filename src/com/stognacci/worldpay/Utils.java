package com.stognacci.worldpay;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by sebastianot on 18/11/16.
 */
public class Utils {

    static final String DATE_PATTERN = "ddMMyyyy";

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

    public static long getTotalWeeks(LocalDate startWeekDate,LocalDate endWeekDate){
        return ChronoUnit.WEEKS.between(startWeekDate, endWeekDate);
    }

    public static int getWeekNumber(LocalDate date){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber=date.get(weekFields.weekOfWeekBasedYear());
        return weekNumber;
    }

    public static LocalDate getWeekMonday(LocalDate date){
        return date.with(DayOfWeek.MONDAY);
    }

    public static LocalDate getWeekSunday(LocalDate date){
        return date.with(DayOfWeek.SUNDAY);
    }

    public static LocalDate getNextWeek(LocalDate currentWeekDate){
        currentWeekDate = currentWeekDate.plusWeeks(1);
        return currentWeekDate;
    }

    public static Boolean isInBetweenDates(LocalDate dateStart, LocalDate dateEnd, LocalDate dateToCheck) {
        return !(dateToCheck.isBefore(dateStart) || dateToCheck.isAfter(dateEnd));
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

