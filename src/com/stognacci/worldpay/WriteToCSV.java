package com.stognacci.worldpay;

import org.supercsv.cellprocessor.FmtBool;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.io.dozer.ICsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.stognacci.worldpay.ReadFromCSV.FIELD_MAPPING;

/**
 * Created by sebastianot on 01/12/16.
 */
public class WriteToCSV {

    static void writeEmployeesToCSV(List<Employee> employees, String csvFileName)  {

        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(),                             // firstName
                new NotNull(),                             // lastName
                new ParseEnum(ExpLevel.class),             // experience
                new FmtBool("true", "false"),                           // isPrimary
                new FmtBool("true", "false"),                           // isSecondary
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayStart1
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayEnd1
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayStart2
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayEnd2
        };

        ICsvDozerBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvDozerBeanWriter(new FileWriter(csvFileName),
                    CsvPreference.STANDARD_PREFERENCE);

            // configure the mapping from the fields to the CSV columns
            beanWriter.configureBeanMapping(Employee.class, FIELD_MAPPING);

            // write the header
            beanWriter.writeHeader("firstName","lastName","experience","isPrimary",
                    "isSecondary","holidayStart1","holidayEnd1","holidayStart2","holidayEnd2");

            // write the beans
            for (final Employee employee : employees) {
                beanWriter.write(employee, processors);
            }
        } catch (IOException ex) {
            System.err.println("Could not write the CSV file: " + ex);
        } finally {
            if( beanWriter != null ) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }
}
