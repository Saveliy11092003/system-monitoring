package nsu.trushkov.writer;

import nsu.trushkov.builder.ReportCreator;
import nsu.trushkov.model.DataForReport;

/**
 * This class write a report to the console.
 */
public class ConsoleReportWriter implements ReportWriter{

    public void write(DataForReport data) {
        String report = new ReportCreator().buildReport(data);
        System.out.println(report);
    }

}
