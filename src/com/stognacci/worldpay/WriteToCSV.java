package com.stognacci.worldpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    final static Logger LOG = LoggerFactory.getLogger(WriteToCSV.class.getSimpleName());

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
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayStart3
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayEnd3
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayStart4
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayEnd4
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayStart5
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayEnd5
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayStart6
                new Optional(new FmtDate(Utils.DATE_PATTERN)), // holidayEnd6
        };

        ICsvDozerBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvDozerBeanWriter(new FileWriter(csvFileName),
                    CsvPreference.STANDARD_PREFERENCE);

            // configure the mapping from the fields to the CSV columns
            beanWriter.configureBeanMapping(Employee.class, FIELD_MAPPING);

            // write the header
            beanWriter.writeHeader("firstName","lastName","experience","isPrimary","isSecondary", "holidayStart1","holidayEnd1","holidayStart2","holidayEnd2",
                    "holidayStart3","holidayEnd3","holidayStart4","holidayEnd4","holidayStart5","holidayEnd5","holidayStart6","holidayEnd6");

            // write the beans
            for (final Employee employee : employees) {
                beanWriter.write(employee, processors);
            }
            LOG.info("Csv file updated successfully");
        } catch (IOException ex) {
            LOG.error("Could not write the CSV file: " + ex);
        } finally {
            if( beanWriter != null ) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    LOG.error("Error closing the writer: " + ex);
                }
            }
        }
    }
}
