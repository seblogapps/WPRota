package com.stognacci.worldpay;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastianot on 22/11/16.
 */
public class ReadFromCSV {

    private static final String DATE_PATTERN = "ddMMyyyy";

    static final String[] FIELD_MAPPING = new String[]{
            "firstName",
            "lastName",
            "experience",
            "isPrimary",
            "isSecondary",
            "holidays[0].holidayStart",
            "holidays[0].holidayEnd",
            "holidays[1].holidayStart",
            "holidays[1].holidayEnd"
    };

    static List<Employee> readFromCSVtoEmployees(String csvFileName) {

        List<Employee> employees = new ArrayList<>();

        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(),                             // firstName
                new NotNull(),                             // lastName
                new ParseEnum(ExpLevel.class),             // experience
                new ParseBool(),                           // isPrimary
                new ParseBool(),                           // isSecondary
                new Optional(new ParseDate(DATE_PATTERN)), // holidayStart1
                new Optional(new ParseDate(DATE_PATTERN)), // holidayEnd1
                new Optional(new ParseDate(DATE_PATTERN)), // holidayStart2
                new Optional(new ParseDate(DATE_PATTERN)), // holidayEnd2
        };

        ICsvDozerBeanReader beanReader = null;
        try {
            beanReader = new CsvDozerBeanReader(new FileReader(csvFileName), CsvPreference.STANDARD_PREFERENCE);

            beanReader.getHeader(true);
            beanReader.configureBeanMapping(Employee.class, FIELD_MAPPING);

            Employee employee;
            while ((employee = beanReader.read(Employee.class, processors)) != null) {
                //System.out.println(String.format("employee=%s", employee));
                employees.add(employee);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Could not find the CSV file: " + ex);
        } catch (IOException ex) {
            System.err.println("Error reading the CSV file: " + ex);
        } catch (SuperCsvCellProcessorException ex) {
            System.err.println("Error in CSV file: " + ex);
        } finally {
            if (beanReader != null) {
                try {
                    beanReader.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the reader: " + ex);
                }
            }
        }
        return employees;
    }
}
