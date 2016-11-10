package com.stognacci.worldpay;

import java.util.Arrays;
import java.util.Date;

public class Employee {
    private String firstName;
    private String lastName;
    private ExpLevel experience;
    private boolean primary;
    private boolean secondary;
    private Date holidayStart;
    private Date holidayEnd;

    Employee(String firstName, String lastName, ExpLevel experience, Date holidayStart, Date holidayEnd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
    }

    Employee(String firstName, String lastName, ExpLevel experience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
    }

    public void setPrimary() {
        this.primary = true;
        this.secondary = false;
    }

    public void setSecondary() {
        this.primary = false;
        this.secondary = true;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", experience=" + experience +
                ", primary=" + primary +
                ", secondary=" + secondary +
                ", holidayStart=" + holidayStart +
                ", holidayEnd=" + holidayEnd +
                '}';
    }
}
