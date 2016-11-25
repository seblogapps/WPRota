package com.stognacci.worldpay;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by sebastianot on 18/11/16.
 */
public class Utils {

    public static int getWeekFromDate(Scanner scanner, String inputPrompt, String datePattern) {
        int weekNumber = -1;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        while (weekNumber == -1) {
            LocalDate date;
            System.out.println(inputPrompt + " as " + datePattern);
            String userInput = scanner.nextLine();
            //System.out.println("response = " + userInput);
            try {
                date = LocalDate.parse(userInput, dateTimeFormatter);
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                weekNumber = date.get(weekFields.weekOfWeekBasedYear());
            } catch (DateTimeParseException e) {
                System.out.println("Date entered : " + userInput + " is wrong - Any key or enter to try again, n to exit");
                String again = scanner.nextLine();
                if (again.equalsIgnoreCase("n") || again.equalsIgnoreCase("no")) {
                    System.exit(0);
                }
            }
        }
        return weekNumber;
    }

    //TODO: Probably a better way of doing that to consider if a rota has to be generated between end of one year and start of next year
    public static int getDeltaWeeks(int rotaWeekStart, int rotaWeekEnd) {
        if (rotaWeekEnd >= rotaWeekStart) {
            return (rotaWeekEnd - rotaWeekStart) + 1;
        } else if (rotaWeekStart == 52 && rotaWeekEnd == 1) {
            return 1;
        } else {
            System.out.println("End date earlier than start date, please try again");
            return -1;
        }
    }

    public static Boolean isInBetweenDates(LocalDate dateStart, LocalDate dateEnd, LocalDate dateToCheck) {
        return !(dateToCheck.isBefore(dateStart) || dateToCheck.isAfter(dateEnd));
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}

