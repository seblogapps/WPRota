package com.stognacci.worldpay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.stognacci.worldpay.Utils.*;

/**
 * Created by nisham on 14/11/16.
 */
public class PickOnCallEmp {

    public static Employee pickPrimary(List<Employee> employees,int weekNumber,boolean toReset) {
        boolean isPrimary = false;
        Employee primary = null;
        for (int i = 0; i < employees.size() && primary == null; i++) {
            boolean onVacation = checkOnVacation(employees.get(i), weekNumber);
            isPrimary = employees.get(i).getIsPrimary();
            boolean canBePrimary = true;
            if (employees.get(i).getExperience() == ExpLevel.EXP3) {
                canBePrimary = hasExp1(employees, weekNumber);
            }
            if (isPrimary == false && onVacation == false && canBePrimary == true) {
                primary = employees.get(i);
                employees.get(i).setIsPrimary(true);
                employees.add(employees.remove(i));
            }
        }

            if (primary==null && !toReset){
                Utils.printRota();
                for (Employee employee:employees){
                    //System.out.println(employee);
                }
                System.out.println("There are not enough employees to select Primary for weekNumber "+weekNumber);
                exitApplication();
            }


        if (primary == null && toReset==true) {
            for (Employee employee : employees) {
                employee.setIsPrimary(false);
            }
            primary = pickPrimary(employees,weekNumber,false);
        }
        return primary;
    }

    public static boolean hasExp1(List<Employee> employees,int weekNumber) {
        boolean hasExp1 = false;
        for (int j = 0; j < employees.size() && hasExp1 == false; j++) {
            if (employees.get(j).getExperience() == ExpLevel.EXP1 && !checkOnVacation(employees.get(j),weekNumber)) {
                hasExp1 = true;
            }
        }
        return hasExp1;
    }

    public static Employee pickSecondary(ExpLevel primaryEx, List<Employee> employees,int weekNumber,boolean toReset) {
        boolean isSecondary = false;
        Employee secondary = null;
        for (int i=0; i < employees.size()-1 && secondary==null ;i++) {
            boolean onVacation = checkOnVacation(employees.get(i), weekNumber);
            isSecondary = employees.get(i).getIsSecondary();
            if (isSecondary == false && onVacation == false) {
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
            }
        }

            if (secondary==null && !toReset){
                Utils.printRota();
                System.out.println("There are not enough employees to select Secondary for weekNumber "+weekNumber);
                exitApplication();
            }


            if (secondary==null && toReset==true ) {
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
                secondary=pickSecondary(primaryEx,employees,weekNumber,false);
                }
        return secondary;
    }

    public static boolean checkOnVacation(Employee employees, int weekNumber){
        List<Integer> holidayWeeks=new ArrayList<>();

        //Preparing Holiday week Array
        for (Holiday holiday : employees.getHolidays()) {
            if (holiday.getHolidayStart() != null) {
                LocalDate currentWeekDate=convertToLocalDate(holiday.getHolidayStart());
                long totalWeeks=Utils.getTotalWeeks(convertToLocalDate(holiday.getHolidayStart()),convertToLocalDate(holiday.getHolidayEnd()));
                for (int j = 0; j <= totalWeeks; j++) {
                    holidayWeeks.add(Utils.getWeekNumber(currentWeekDate));
                    currentWeekDate=getNextWeek(currentWeekDate);
                }
            }
        }
        return holidayWeeks.contains(weekNumber);
    }


}



