package com.stognacci.worldpay;

import java.util.Arrays;
import java.util.Date;

public class Employee {
    private String firstName;
    private String lastName;
    private ExpLevel experience;
    private Date [] holidays;

    Employee(String firstName, String lastName, ExpLevel experience, Date[] holidays) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.holidays = holidays;
    }

    Employee(String firstName, String lastName, ExpLevel experience) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", experience=" + experience +
                ", holidays=" + Arrays.toString(holidays) +
                '}';
    }
}
