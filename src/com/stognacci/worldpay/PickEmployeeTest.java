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

    @BeforeEach
    void setUp() {
        employees = ReadFromCSV.readFromCSVtoEmployees(Utils.EMPLOYEE_CSV_FILENAME);
        // Add all employee to employees arraylist
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