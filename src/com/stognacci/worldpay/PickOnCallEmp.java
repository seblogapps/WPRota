package com.stognacci.worldpay;

import java.util.ArrayList;
import java.util.List;

import static com.stognacci.worldpay.Utils.convertToLocalDate;

/**
 * Created by nisham on 14/11/16.
 */
public class PickOnCallEmp {


    public static Employee pickPrimary(List<Employee> employees,int weekNumber) {
        boolean isPrimary = false;
        Employee primary = null;
        for (int i = 0; i < employees.size() && primary == null; i++) {
          boolean onVacation=checkOnVacation(employees.get(i),weekNumber);
          isPrimary = employees.get(i).getIsPrimary();
          if (isPrimary == false && onVacation==false) {
              primary = employees.get(i);
              employees.get(i).setIsPrimary(true);
              employees.add(employees.remove(i));
            }
        }

        if (primary == null) {
            for (Employee employee : employees) {
                employee.setIsPrimary(false);
            }
            primary = pickPrimary(employees,weekNumber);
        }
        return primary;
    }


    public static Employee pickSecondary(ExpLevel primaryEx, List<Employee> employees,int weekNumber) {
        boolean isSecondary = false;
        Employee secondary = null;
        for (int i=0; i < employees.size()-1;i++) {
            boolean onVacation=checkOnVacation(employees.get(i),weekNumber);
            isSecondary = employees.get(i).getIsSecondary();
            if (isSecondary == false && onVacation==false) {
                switch (primaryEx) {
                    case EXP1:
                        secondary = employees.get(i);
                        employees.get(i).setIsSecondary(true);
                        employees.add(employees.remove(i));
                        i = employees.size();
                        break;
                    case EXP2:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1 || employees.get(i).getExperience() == ExpLevel.EXP2) {
                            secondary = employees.get(i);
                            employees.get(i).setIsSecondary(true);
                            employees.add(employees.remove(i));
                            i = employees.size();
                        }
                        break;
                    case EXP3:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1) {
                            secondary = employees.get(i);
                            employees.get(i).setIsSecondary(true);
                            employees.add(employees.remove(i));
                            i = employees.size();
                        }
                        break;
                }
            } else {
                if (i == employees.size() - 2) {
                    switch (primaryEx) {
                        case EXP1:
                            // to check here should be emp size or emp size -1
                            for (int j = 0; j < employees.size(); j++) {
                                employees.get(j).setIsSecondary(false);
                            }
                            break;
                        case EXP2:
                            for (int j = 0; j < employees.size(); j++) {
                                if (employees.get(j).getExperience() == ExpLevel.EXP1 || employees.get(j).getExperience() == ExpLevel.EXP2)
                                    employees.get(j).setIsSecondary(false);
                            }
                            break;
                        case EXP3:
                            for (int j = 0; j < employees.size(); j++) {
                                if (employees.get(j).getExperience() == ExpLevel.EXP1)
                                    employees.get(j).setIsSecondary(false);
                            }
                            break;

                    }
                    i = -1;
                }
            }
        }
            return secondary;
        }

    public static boolean checkOnVacation(Employee employees, int weekNumber){
        List<Integer> holidayWeeks=new ArrayList<>();

        //Preparing Holiday week Array
        for (Holiday holiday : employees.getHolidays()) {
            if (holiday.getHolidayStart() != null) {
                int startHolidayWeekNumber = Utils.getWeekNumber(convertToLocalDate(holiday.getHolidayStart()));
                int endHolidayWeekNumber = Utils.getWeekNumber(convertToLocalDate(holiday.getHolidayEnd()));
                long totalWeeks=Utils.getTotalWeeks(convertToLocalDate(holiday.getHolidayStart()),convertToLocalDate(holiday.getHolidayEnd()));
                for (int j = 0; j <= totalWeeks; j++) {
                    holidayWeeks.add(startHolidayWeekNumber);
                    startHolidayWeekNumber++;
                }
              //  System.out.println(holidayWeeks);
            }
        }
        return holidayWeeks.contains(weekNumber);
    }



}



