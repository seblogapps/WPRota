package com.stognacci.worldpay;


public class Rota {
    private long week;
    private Employee primary;
    private Employee secondary;

    public Rota(long week, Employee primary, Employee secondary) {
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