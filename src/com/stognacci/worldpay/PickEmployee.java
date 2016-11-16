package com.stognacci.worldpay;

import javax.swing.text.StyledEditorKit;
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

    public static Employee pickSecondary(List<Employee> employees, Employee primary) {
        Boolean isSecondary;
        Employee secondary = null;
        ExpLevel primaryExpLevel = primary.getExperience();

        for (int i = 0; (i < employees.size() - 1) && (secondary == null); i++) {
            isSecondary = employees.get(i).getIsSecondary();
            if (!isSecondary) {
                switch (primaryExpLevel) {
                    case EXP1:
                        secondary = employees.get(i);
                        employees.get(i).setSecondary(true);
                        employees.add(employees.remove(i));
                        break;
                    case EXP2:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1 || employees.get(i).getExperience() == ExpLevel.EXP2) {
                            secondary = employees.get(i);
                            employees.get(i).setSecondary(true);
                            employees.add(employees.remove(i));
                        }
                        break;
                    case EXP3:
                        if (employees.get(i).getExperience() == ExpLevel.EXP1) {
                            secondary = employees.get(i);
                            employees.get(i).setSecondary(true);
                            employees.add(employees.remove(i));
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
                employees.get(i).setSecondary(false);
            }
            secondary = pickSecondary(employees, primary);
        }

//        if (primaryExpLevel == ExpLevel.EXP1) {
//            for (int i = 0; (i < employees.size() - 1) && (secondary == null); i++) {
//                isSecondary = employees.get(i).getIsSecondary();
//                if (!isSecondary) {
//                    secondary = employees.get(i);
//                    employees.get(i).setSecondary(true);
//                    employees.add(employees.remove(i));
//                }
//            }
//            if (secondary == null) {
//                for (int i = 0; i < employees.size() - 1; i++) {
//                    employees.get(i).setSecondary(false);
//                }
//                secondary = pickSecondary(employees, primary);
//            }
//        }
        return secondary;
    }
}
