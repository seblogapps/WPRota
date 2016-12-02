package com.stognacci.worldpay;
import java.util.List;

public class Employee {
    private String firstName;
    private String lastName;
    private ExpLevel experience;
    private Boolean isPrimary;
    private Boolean isSecondary;
    private List<Holiday> holidays;


    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public ExpLevel getExperience() {return experience;}
    public Boolean getIsPrimary() {return isPrimary;}
    public Boolean getIsSecondary() {return isSecondary;}
    public List<Holiday> getHolidays() {return holidays;}

    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setExperience(ExpLevel experience) {this.experience = experience;}
    public void setIsPrimary(Boolean primary) {this.isPrimary = primary;}
    public void setIsSecondary(Boolean secondary) {this.isSecondary = secondary;}
    public void setHolidays(List<Holiday> holidays) {this.holidays = holidays;}

    public void resetPrimary() {this.isPrimary = false;}
    public void resetSecondary() {this.isSecondary = false;}

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