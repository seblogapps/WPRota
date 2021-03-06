package com.stognacci.worldpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by sebastianot on 18/11/16.
 */
public class Utils {

    static final Logger LOG = LoggerFactory.getLogger(Utils.class.getSimpleName());

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String EMPLOYEE_CSV_FILENAME = "Employees.csv";
    public static final String ICAL_FILENAME = "WPRota.ics";
    public static final String EMPLOYEE_CSV_BACKUPDIRNAME = "RotaBackup";
    public static final int SHIFT_HOUR_HANDOVER = 9;

    public static LocalDate getDate(Scanner scanner, String inputPrompt, String datePattern) {
        LocalDate date = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        while (date == null) {
            System.out.println(inputPrompt + " as " + datePattern);
            String userInput = scanner.nextLine();
            try {
                date = LocalDate.parse(userInput, dateTimeFormatter);
                LOG.debug("Date entered: {} ", date);
            } catch (DateTimeParseException e) {
                LOG.error("Date entered {}: is wrong", userInput);
                System.out.println("Any key or enter to try again, n to exit");
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
                        LOG.error("Employee: " + employee.getFirstName() + employee.getLastName() + ", Holiday end date not specified for Start Date: " + holiday.getHolidayStart());
                        LOG.error("Please fix CSV, Cannot proceed with ROTA creation");
                        return false;
                    } else if (holiday.getHolidayEnd().before(holiday.getHolidayStart())) {
                        LOG.error("Employee: " + employee.getFirstName() + employee.getLastName() + ", Start holiday:" + holiday.getHolidayStart() + " is after than end Holiday " + holiday.getHolidayEnd());
                        LOG.error("Please fix CSV, Cannot proceed with ROTA creation");
                        return false;
                    }
                } else if (holiday.getHolidayEnd() != null) {
                    LOG.error("Employee: " + employee.getFirstName() + employee.getLastName() + ", Holiday start date not specified for End Date: " + holiday.getHolidayEnd());
                    LOG.error("Please fix CSV, Cannot proceed with ROTA creation");
                    return false;
                }
            }
        }
        return true;
    }

    public static void backupEmployeeCSV(String csvFileName, String backupDirName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_Hms");
        String currentDate = LocalDateTime.now().format(formatter);

        File backupDir = new File(backupDirName);

        if (backupDir.exists() && backupDir.isFile()) {
            LOG.error("The directory with name " + backupDir + " could not be created as it is a normal file");
        } else {
            if (!backupDir.exists()) {
                boolean isDirCreated = backupDir.mkdir();
                if (!isDirCreated) {
                    LOG.error("Cannot create directory with name {}", backupDir);
                    Utils.exitApplication();
                }
            }
        }
        Path oldFilePath = Paths.get(csvFileName);
        Path newFilePath = Paths.get(backupDirName, currentDate + "_" + csvFileName);
        try {
            Files.copy(oldFilePath, newFilePath, REPLACE_EXISTING);
            LOG.debug("Copied {} to {}", oldFilePath, newFilePath);
        } catch (IOException ex) {
            LOG.error("{} failed to move to backup folder - {}", oldFilePath, ex);
        }
    }

    public static void exitApplication() {
        LOG.error("Rota Generation unsuccessful - Exiting from the application");
        System.exit(0);
    }
}

