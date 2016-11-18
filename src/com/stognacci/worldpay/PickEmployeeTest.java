package com.stognacci.worldpay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by sebastianot on 16/11/16.
 */
class PickEmployeeTest {
    private List<Employee> employees = new ArrayList<>();

    Employee seb = new Employee("Sebastiano", "Tognacci", ExpLevel.EXP3, false, false);
    Employee nisha = new Employee("Nisha", "Monga", ExpLevel.EXP3, false, false);
    Employee mark = new Employee("Mark", "Angel-Trueman", ExpLevel.EXP1, false, false);
    Employee jose = new Employee("Jose", "Morena", ExpLevel.EXP1, false, false);
    Employee dave = new Employee("Dave", "Reese", ExpLevel.EXP2, false, false);
    Employee roy = new Employee("Roy","Reicher",ExpLevel.EXP3, false, false);
    Employee hernan = new Employee("Hernan", "Rizzuti", ExpLevel.EXP2, false, false);
    Employee bruno = new Employee("Bruno", "Dias", ExpLevel.EXP1, false, false);

    @BeforeEach
    void setUp() {
        // Add all employee to employees arraylist
        employees.add(seb);
        employees.add(nisha);
        employees.add(mark);
        employees.add(dave);
        employees.add(jose);
        employees.add(roy);
        employees.add(hernan);
        employees.add(bruno);
        Collections.shuffle(employees);
    }

    @Test
    void pickPrimary() {
        Employee testPrimary = null;
        for (int i = 0; i < employees.size() && testPrimary == null; i++) {
            if (!employees.get(i).getIsPrimary()) {
                testPrimary = employees.get(i);
            }
        }

        Employee primary = PickEmployee.pickPrimary(employees);
        assertEquals(testPrimary, primary);
    }

    @Test
    void pickSecondary() {

    }

}