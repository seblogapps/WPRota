package com.stognacci.worldpay;

import java.util.Arrays;
import java.util.Date;

public class Employee {
    private String firstName;
    private String lastName;
    private ExpLevel experience;
    private Boolean isPrimary;
    private Boolean isSecondary;



    public ExpLevel getExperience(){
        return experience;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public Boolean getIsSecondary() {
        return isSecondary;
    }

    public void setPrimary(Boolean isPrimary) {
        this.isPrimary=isPrimary;
    }

    public void setSecondary(Boolean isSecondary) {
        this.isSecondary=isSecondary;
    }

    private Date [] holidays;



    Employee(String firstName, String lastName, ExpLevel experience, Boolean isPrimary, Boolean isSecondary){
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.isPrimary = isPrimary;
        this.isSecondary = isSecondary;
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
