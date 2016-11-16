package com.stognacci.worldpay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastianot on 15/11/16.
 */
public class PickEmployee {

    public static Employee pickPrimary(List<Employee> employees) {
        boolean isPrimary;
        Employee primary=null;

        for (int i = 0; (i < employees.size()) && (primary == null); i++) {
            isPrimary = employees.get(i).getIsPrimary();
            if (!isPrimary) {
                primary = employees.get(i);
                employees.get(i).setPrimary(true);
                employees.add(employees.remove(i));
                //i = employees.size();
            }
        }
        if (primary == null) {
            for (Employee employee : employees) {
                employee.setPrimary(false);
            }
            primary = pickPrimary(employees);
        }
        /*else {
                if (i == employees.size() - 1) {
                    for (int j = 0; j < employees.size(); j++) {
                        employees.get(j).setPrimary(false);
                    }
                    i=-1;
                }
            }*/
        //}
        return primary;
    }

}
