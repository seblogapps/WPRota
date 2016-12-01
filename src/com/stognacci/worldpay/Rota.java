package com.stognacci.worldpay;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by sebastianot on 11/11/16.
 */
public class Rota {
    private LocalDate week;
    private Employee primary;
    private Employee secondary;

    public Rota(LocalDate week, Employee primary, Employee secondary) {
        this.week = week;
        this.primary = primary;
        this.secondary = secondary;
    }

    public void setWeek(LocalDate week) {
        this.week = week;
    }

    public void setPrimary(Employee primary) {
        this.primary = primary;
    }

    public void setSecondary(Employee secondary) {
        this.secondary = secondary;
    }

    public LocalDate getWeek() {
        return week;
    }

    public Employee getPrimary() {
        return primary;
    }

    public Employee getSecondary() {
        return secondary;
    }

    @Override
    public String toString() {
        return "Rota: " +
                "week = " + week.format(DateTimeFormatter.ofPattern(Utils.DATE_PATTERN)) +
                "\tfrom: " + (Utils.getWeekMonday(week)).format(DateTimeFormatter.ofPattern(Utils.DATE_PATTERN)) +
                "\tto: " + (Utils.getWeekSunday(week)).format(DateTimeFormatter.ofPattern(Utils.DATE_PATTERN)) +
                ", \n\tprimary   = " + primary +
                ", \n\tsecondary = " + secondary +
                "\n";
    }

    public String toStringforEventDescription() {
        return "week = " + Utils.getWeekNumber(week) +
                ", primary = " + primary.getFirstName().substring(0,1) + "." + primary.getLastName() +
                ", secondary = " + secondary.getFirstName().substring(0,1) + "." + secondary.getLastName()
                ;
    }
}
