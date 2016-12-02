package com.rota.worldpay;

import java.util.Date;

public class Holiday {

    private Date holidayStart;
    private Date holidayEnd;
    public Holiday() {
    }

    public Holiday(Date holidayStart, Date holidayEnd) {
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
    }

    public Date getHolidayStart() {
        return holidayStart;
    }

    public void setHolidayStart(Date holidayStart) {
        this.holidayStart = holidayStart;
    }

    public Date getHolidayEnd() {
        return holidayEnd;
    }

    public void setHolidayEnd(Date holidayEnd) {
        this.holidayEnd = holidayEnd;
    }

    public String toString() {
        return String.format("Start: %-12s" +
                " End:%-12s", holidayStart, holidayEnd);
    }
}

