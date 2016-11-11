package com.stognacci.worldpay;

import java.util.Date;

public class Employee {
    private String firstName;
    private String lastName;
    private ExpLevel experience;
    private boolean isPrimary;
    private boolean isSecondary;
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

    public void resetPrimary() {
        this.isPrimary = false;
    }

    public void resetSecondary() {
        this.isSecondary = false;
    }

    public ExpLevel getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        return String.format("First: %-12s" +
                "Last:%-16s" +
                "Exp:%-6s" +
                "Pri:%-8s" +
                "Sec:%-8s" +
                "HolStart:%-30s" +
                "HolEnd:%-30s", firstName,lastName,experience, isPrimary, isSecondary,holidayStart,holidayEnd);
    }
}
