package nsu.trushkov.writer;

import nsu.trushkov.model.DataForReport;

/**
 * Interface for sending a report.
 * <p>
 * Classes that implement this interface must implement sending logic a report.
 */
public interface ReportWriter {

    /**
     * This method is needed to send a report data
     * @param data data for creating a report
     */
    void write(DataForReport data);
}
