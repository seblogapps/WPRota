package com.stognacci.worldpay;

import java.util.List;

public class Employee {
    private String firstName;
    private String lastName;
    private ExpLevel experience;
    private Boolean isPrimary;
    private Boolean isSecondary;
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

    public Employee(String firstName, String lastName, ExpLevel experience, Boolean isPrimary, Boolean isSecondary) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setExperience(ExpLevel experience) {
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

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        this.isPrimary = primary;
    }

    public Boolean getIsSecondary() {
        return isSecondary;
    }

    public void setIsSecondary(Boolean secondary) {
        this.isSecondary = secondary;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
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
}