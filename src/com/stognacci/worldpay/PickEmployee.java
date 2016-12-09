package com.stognacci.worldpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastianot on 15/11/16.
 */
public class PickEmployee {

    final Logger LOG = LoggerFactory.getLogger(PickEmployee.class.getSimpleName());

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
            for (Employee employee : employees) {
                employee.setIsSecondary(false);
            }
            secondary = pickSecondary(employees, primary);
        }
        return secondary;
    }

    public static boolean checkOnVacation(Employee employee, int weekNumber) {
        List<Integer> holidayWeeks = new ArrayList<>();

        //Preparing Holiday week Array
        for (Holiday holiday : employee.getHolidays()) {
            if (holiday.getHolidayStart() != null) {
                LocalDate currentHolidayStartWeekDate = Utils.convertToLocalDate(holiday.getHolidayStart());
                LocalDate currentHolidayEndWeekDate = Utils.convertToLocalDate(holiday.getHolidayEnd());
                long totalWeeks = Utils.getTotalWeeks(currentHolidayStartWeekDate, currentHolidayEndWeekDate);
                for (int j = 0; j <= totalWeeks; j++) {
                    holidayWeeks.add(Utils.getWeekNumber(currentHolidayStartWeekDate));
                    currentHolidayStartWeekDate = Utils.getNextWeek(currentHolidayStartWeekDate);
                }
            }
        }
        return holidayWeeks.contains(weekNumber);
    }

    // Check if at least one EXP1 is available for that week
    public static boolean isExp1Available(List<Employee> employees, int weekNumber) {
        boolean isExp1Available = false;
        for (int i = 0; i < employees.size() && !isExp1Available; i++) {
            if (employees.get(i).getExperience() == ExpLevel.EXP1 && !checkOnVacation(employees.get(i), weekNumber)) {
                isExp1Available = true;
            }
        }
        return isExp1Available;
    }

    public static void moveToEnd(List<Employee> employees, Employee primary, Employee secondary) {
        employees.remove(primary);
        employees.remove(secondary);
        employees.add(primary);
        employees.add(secondary);
    }


    public static List<Employee> pickOnDuty(List<Employee> employees, LocalDate rotaWeekDate) {
        List<Employee> employeesOnDuty = new ArrayList<>(employees);
        for (Employee employee : employees) {
            for (Holiday holiday : employee.getHolidays()) {
                if (holiday.getHolidayStart() != null) {
                    LocalDate empHolidayStart = Utils.convertToLocalDate(holiday.getHolidayStart());
                    LocalDate empHolidayEnd = Utils.convertToLocalDate(holiday.getHolidayEnd());
                    Boolean isOnHoliday = (!rotaWeekDate.isBefore(empHolidayStart) && rotaWeekDate.isBefore(empHolidayEnd));
                    if (isOnHoliday) {
                        employeesOnDuty.remove(employee);
                        System.out.println(employee.getFirstName() + " isOnHoliday for week " + rotaWeekDate);
                    }
                }
            }
        }
        return employeesOnDuty;
    }

    public static List<Employee> canCreateRota(List<Employee> employeesOnDuty) {
        List<Employee> employeesOnDutyWithCorrectExp = new ArrayList<>(employeesOnDuty);
        int exp1EmpCount = 0;
        int exp3EmpCount = 0;
        for (Employee employee : employeesOnDuty) {
            ExpLevel expLevel = employee.getExperience();
            switch (expLevel) {
                case EXP1:
                    exp1EmpCount++;
                    break;
                case EXP3:
                    exp3EmpCount++;
                    break;
                default:
                    break;
            }
        }
        // Remove EXP3 employees if there are no EXP1 available
        if (exp3EmpCount > 0 && exp1EmpCount == 0) {
            for (Employee employee : employeesOnDuty) {
                if (employee.getExperience() == ExpLevel.EXP3) {
                    employeesOnDutyWithCorrectExp.remove(employee);
                }
            }
        }
        return employeesOnDutyWithCorrectExp;
    }
}

