package com.stognacci.worldpay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisham on 14/11/16.
 */
public class PickOnCallEmp {


    public static Employee pickPrimary(List<Employee> employees) {
        boolean isPrimary = false;
        Employee primary = null;

        for (int i = 0; i < employees.size() && primary == null; i++) {
            isPrimary = employees.get(i).getIsPrimary();
            if (isPrimary == false) {
                primary = employees.get(i);
                employees.get(i).setPrimary(true);
                employees.add(employees.remove(i));
            }
        }

        if (primary == null) {
            for (Employee employee : employees) {
                employee.setPrimary(false);
            }
            primary = pickPrimary(employees);
        }
        return primary;
    }

    public static Employee pickSecondary(ExpLevel primaryEx, List<Employee> employees) {
        boolean isSecondary = false;
        Employee secondary = null;
        for (int i=0; i < employees.size()-1;i++) {
            isSecondary = employees.get(i).getIsSecondary();
            if (isSecondary == false) {
                switch (primaryEx) {
                    case EXP1:
                        secondary = employees.get(i);
                        employees.get(i).setSecondary(true);
                        employees.add(employees.remove(i));
                        i = employees.size();
                        break;
                    case EXP2:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1 || employees.get(i).getExperience() == ExpLevel.EXP2) {
                            secondary = employees.get(i);
                            employees.get(i).setSecondary(true);
                            employees.add(employees.remove(i));
                            i = employees.size();
                        }
                        break;
                    case EXP3:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1) {
                            secondary = employees.get(i);
                            employees.get(i).setSecondary(true);
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
                                employees.get(j).setSecondary(false);
                            }
                            break;
                        case EXP2:
                            for (int j = 0; j < employees.size(); j++) {
                                if (employees.get(j).getExperience() == ExpLevel.EXP1 || employees.get(j).getExperience() == ExpLevel.EXP2)
                                    employees.get(j).setSecondary(false);
                            }
                            break;
                        case EXP3:
                            for (int j = 0; j < employees.size(); j++) {
                                if (employees.get(j).getExperience() == ExpLevel.EXP1)
                                    employees.get(j).setSecondary(false);
                            }
                            break;

                    }
                    i = -1;
                }
            }
        }
            return secondary;
        }



    public static Employee pickSecondary2(ExpLevel primaryEx, ArrayList<Employee> employees) {
        boolean isSecondary = false;
        Employee secondary = null;

        for (int i = 0; i < employees.size() - 1 && secondary == null; i++) {
            isSecondary = employees.get(i).getIsSecondary();
            if (isSecondary == false) {
                switch (primaryEx) {
                    case EXP1:
                        secondary = employees.get(i);
                        employees.get(i).setSecondary(true);
                        employees.add(employees.remove(i));
                        // i = employees.size();
                        break;
                    case EXP2:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1 || employees.get(i).getExperience() == ExpLevel.EXP2) {
                            secondary = employees.get(i);
                            employees.get(i).setSecondary(true);
                            employees.add(employees.remove(i));
                            //  i = employees.size();
                        }
                        break;
                    case EXP3:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1) {
                            secondary = employees.get(i);
                            employees.get(i).setSecondary(true);
                            employees.add(employees.remove(i));
                            //   i = employees.size();
                        }
                        break;
                }
            }
        }
        if (secondary == null) {
            switch (primaryEx) {
                case EXP1:
                    // to check here should be emp size or emp size -1
                    for (int j = 0; j < employees.size(); j++) {
                        employees.get(j).setSecondary(false);
                    }
                    break;
                case EXP2:
                    for (int j = 0; j < employees.size(); j++) {
                        if (employees.get(j).getExperience() == ExpLevel.EXP1 || employees.get(j).getExperience() == ExpLevel.EXP2)
                            employees.get(j).setSecondary(false);
                    }
                    break;
                case EXP3:
                    for (int j = 0; j < employees.size(); j++) {
                        if (employees.get(j).getExperience() == ExpLevel.EXP1)
                            employees.get(j).setSecondary(false);
                    }
                    break;

            }
            pickSecondary(primaryEx, employees);
        }

        return secondary;
    }



}



