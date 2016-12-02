package com.stognacci.worldpay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastianot on 15/11/16.
 */
public class PickEmployee {

    public static Employee pickPrimary(List<Employee> employees) {
        boolean isPrimary;
        Employee primary = null;

        for (int i = 0; (i < employees.size()) && (primary == null); i++) {
            isPrimary = employees.get(i).getIsPrimary();
            if (!isPrimary) {
                primary = employees.get(i);
                employees.get(i).setIsPrimary(true);
                employees.remove(i);
                //employees.add(employees.remove(i));
            }
        }
        if (primary == null) {
            for (Employee employee : employees) {
                employee.setIsPrimary(false);
            }
            primary = pickPrimary(employees);
        }
        return primary;
    }

    public static Employee pickSecondary(List<Employee> employees, Employee primary) {
        Boolean isSecondary;
        Employee secondary = null;
        ExpLevel primaryExpLevel = primary.getExperience();

        for (int i = 0; (i < employees.size() /*- 1*/) && (secondary == null); i++) {
            isSecondary = employees.get(i).getIsSecondary();
            if (!isSecondary) {
                switch (primaryExpLevel) {
                    case EXP1:
                        secondary = employees.get(i);
                        employees.get(i).setIsSecondary(true);
                        employees.remove(i);
                        //employees.add(employees.remove(i));
                        break;
                    case EXP2:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1 || employees.get(i).getExperience() == ExpLevel.EXP2) {
                            secondary = employees.get(i);
                            employees.get(i).setIsSecondary(true);
                            employees.remove(i);
                            //employees.add(employees.remove(i));
                        }
                        break;
                    case EXP3:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1) {
                            secondary = employees.get(i);
                            employees.get(i).setIsSecondary(true);
                            employees.remove(i);
                            //employees.add(employees.remove(i));
                        }
                        break;
                    default:
                        System.out.println("Error occurred");
                        break;
                }
            }
        }
        if (secondary == null) {
            for (int i = 0; i < employees.size() - 1; i++) {
                employees.get(i).setIsSecondary(false);
            }
            secondary = pickSecondary(employees, primary);
        }
        return secondary;
    }

    public static boolean checkOnVacation(Employee employees, int weekNumber) {
        List<Integer> holidayWeeks = new ArrayList<>();

        //Preparing Holiday week Array
        for (Holiday holiday : employees.getHolidays()) {
            if (holiday.getHolidayStart() != null) {
                LocalDate currentWeekDate = Utils.convertToLocalDate(holiday.getHolidayStart());
                long totalWeeks = Utils.getTotalWeeks(Utils.convertToLocalDate(holiday.getHolidayStart()), Utils.convertToLocalDate(holiday.getHolidayEnd()));
                for (int j = 0; j <= totalWeeks; j++) {
                    holidayWeeks.add(Utils.getWeekNumber(currentWeekDate));
                    currentWeekDate = Utils.getNextWeek(currentWeekDate);
                }
            }
        }
        return holidayWeeks.contains(weekNumber);
    }

    public static void addToEnd(List<Employee> employees, Employee primary, Employee secondary) {
        employees.add(primary);
        employees.add(secondary);
    }
}
