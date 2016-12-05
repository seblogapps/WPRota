package com.rota.worldpay;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import org.apache.commons.lang3.time.DateUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.time.LocalDate;
import java.util.Date;

import static com.rota.worldpay.Utils.rotas;

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

    public static VEvent setEvent(String eventName, LocalDate rotaWeek, int shiftHourHandover) {
        // Create a VEvent for the rota from Monday to next Monday
        VEvent event = null;
        LocalDate rotaMonday = Utils.getWeekMonday(rotaWeek);
        LocalDate rotaNextMonday = Utils.getNextWeek(rotaMonday);
        // Convert from LocalDate to Date since it's needed by DateTime constructor of iCal4j
        Date rotaMondayDate = Utils.convertToDate(rotaMonday);
        Date rotaNextMondayDate = Utils.convertToDate(rotaNextMonday);
        Date mondayStartShiftDate = DateUtils.setHours(rotaMondayDate, shiftHourHandover);
        Date nextMondayStopShiftDate = DateUtils.setHours(rotaNextMondayDate, shiftHourHandover);
        DateTime start = new DateTime(mondayStartShiftDate);
        DateTime end = new DateTime(nextMondayStopShiftDate);
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

    public static void createCalendar() {
        Calendar newCal = iCalUtils.setCalendar("-//Worldpay WPRota//iCal4j 2.0.0//EN", Version.VERSION_2_0, CalScale.GREGORIAN);
        // Add Rota event to calendar
        for (Rota rota : rotas) {
            VEvent rotaEventToAdd = setEvent(rota.toStringforEventDescription(), rota.getWeek(), Utils.SHIFT_HOUR_HANDOVER);
            Attendee attendee1 = setAttendee(rota.getPrimary(), "Primary", Role.REQ_PARTICIPANT);
            Attendee attendee2 = setAttendee(rota.getSecondary(), "Secondary", Role.REQ_PARTICIPANT);
            rotaEventToAdd.getProperties().add(attendee1);
            rotaEventToAdd.getProperties().add(attendee2);
            newCal.getComponents().add(rotaEventToAdd);
        }
        if (newCal != null) {
            iCalUtils.writeIcal(newCal, Utils.ICAL_FILENAME);
        }
    }
}