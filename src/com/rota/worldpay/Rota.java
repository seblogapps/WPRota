
package com.rota.worldpay;

import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.rota.worldpay.Utils.getNextWeek;

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
        return  StringUtils.repeat('=',75) +
                "\nRota: " +
                "week = " + Utils.getWeekNumber(week) +
                "\tfrom: " + (Utils.getWeekMonday(week)).format(DateTimeFormatter.ofPattern(Utils.DATE_PATTERN)) +
                "\tto: " + (Utils.getWeekMonday(getNextWeek(week))).format(DateTimeFormatter.ofPattern(Utils.DATE_PATTERN)) +"\n"+
             //   "\tto: " + (Utils.getWeekSunday(week)).format(DateTimeFormatter.ofPattern(Utils.DATE_PATTERN)) +"\n"+
                StringUtils.repeat('=',75)+
                " \n\tprimary   = " + primary +
                ", \n\tsecondary = " + secondary +
                "\n";
    }

    public String toStringforEventDescription() {
        return "week = " + Utils.getWeekNumber(week) +
                ", primary = " + primary.getFirstName().substring(0,1) + "." + primary.getLastName() +
                "(" + primary.getExperience().toString() + ")" +
                ", secondary = " + secondary.getFirstName().substring(0,1) + "." + secondary.getLastName() +
                "(" + secondary.getExperience().toString() + ")"
                ;
    }
}