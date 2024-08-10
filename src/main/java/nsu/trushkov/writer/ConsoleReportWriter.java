package nsu.trushkov.writer;

import nsu.trushkov.builder.ReportCreator;
import nsu.trushkov.model.DataForReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class write a report to the console.
 */
public class ConsoleReportWriter implements ReportWriter{

    private final ReportCreator reportCreator;
    private static final Logger log = LoggerFactory.getLogger(ConsoleReportWriter.class);

    public ConsoleReportWriter(ReportCreator reportCreator) {
        this.reportCreator = reportCreator;
    }

    public void write(DataForReport data) {
        log.debug("DataForReport - {}", data);
        String report = reportCreator.buildReport(data);
        System.out.println(report);
    }

}
