package com.stognacci.worldpay;

import java.util.List;

public class Employee {
    private String firstName;
    private String lastName;
    private ExpLevel experience;
    private boolean isPrimary;
    private boolean isSecondary;
    private List<Holiday> holidays;

    public Employee() {
    }

    public Employee(String firstName, String lastName, ExpLevel experience, Boolean isPrimary, Boolean isSecondary, List<Holiday> holidays) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.isPrimary = isPrimary;
        this.isSecondary = isSecondary;
        this.holidays = holidays;
    }

    public Employee(String firstName, String lastName, ExpLevel experience, boolean isPrimary, boolean isSecondary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.isPrimary = isPrimary;
        this.isSecondary = isSecondary;
    }

    public Employee(String firstName, String lastName, ExpLevel experience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.isPrimary = false;
        this.isSecondary = false;
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
                "Holidays:%-30s" , firstName,lastName,experience, isPrimary, isSecondary, holidays);
    }

    public boolean getIsPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        this.isPrimary = primary;
    }

    public boolean getIsSecondary() {
        return isSecondary;
    }

    public void setSecondary(boolean secondary) {
        this.isSecondary = secondary;
    }
}
