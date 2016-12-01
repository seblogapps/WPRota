package com.stognacci.worldpay;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by sebastianot on 30/11/16.
 */
public class iCalUtils {

    public static Calendar setCalendar(String prodId, Version version, CalScale calScale) {
        Calendar cal = new Calendar();
        cal.getProperties().add(new ProdId(prodId));
        cal.getProperties().add(version);
        cal.getProperties().add(calScale);
        return cal;
    }

    public static VEvent setEvent(String eventName, LocalDate rotaWeek) {
        VEvent event = null;
        LocalDate rotaMonday = Utils.getWeekMonday(rotaWeek);
        LocalDate rotaSunday = Utils.getWeekSunday(rotaWeek);
        // Convert from LocalDate to Date since it's needed by DateTime constructor of iCal4j
        Date rotaMondayDate = Utils.convertToDate(rotaMonday);
        Date rotaSundayDate = Utils.convertToDate(rotaSunday);

        DateTime start = new DateTime(rotaMondayDate);
        DateTime end = new DateTime(rotaSundayDate);
        event = new VEvent(start, end, eventName);
        // generate unique identifier..
        UidGenerator uidGenerator = null;
        try {
            uidGenerator = new UidGenerator("uidGen");
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Uid uid = uidGenerator.generateUid();
        event.getProperties().add(uid);
        return event;
    }

    public static Attendee setAttendee(Employee employee, String employeeDescription, Role role) {
        String employeeEmail = employee.getFirstName() + "." + employee.getLastName() + "@worldpay.com";
        Attendee attendee = new Attendee(URI.create(employeeEmail));
        attendee.getParameters().add(role);
        attendee.getParameters().add(new Cn(employeeDescription));
        return attendee;
    }

    public static void writeIcal(Calendar calendar, String icsCalendarName) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(icsCalendarName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CalendarOutputter calendarOutputter = new CalendarOutputter();
        try {
            calendarOutputter.output(calendar, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}