package com.stognacci.worldpay;

/**
 * Created by sebastianot on 11/11/16.
 */
public class Rota {
    private int week;
    private Employee primary;
    private Employee secondary;

    public Rota(int week, Employee primary, Employee secondary) {
        this.week = week;
        this.primary = primary;
        this.secondary = secondary;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setPrimary(Employee primary) {
        this.primary = primary;
    }

    public void setSecondary(Employee secondary) {
        this.secondary = secondary;
    }

    @Override
    public String toString() {
        return "Rota: " +
                "week=" + week +
                ", \n\tprimary   = " + primary +
                ", \n\tsecondary = " + secondary +
                "\n";
    }
}
