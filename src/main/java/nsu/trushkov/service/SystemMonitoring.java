package nsu.trushkov.service;

import nsu.trushkov.handler.HashTableHandler;
import nsu.trushkov.initialization.ExampleHashTableInitializer;
import nsu.trushkov.initialization.HashTableInitializer;
import nsu.trushkov.model.DataForReport;
import nsu.trushkov.writer.ConsoleReportWriter;
import nsu.trushkov.writer.ReportWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * This class generate and send a report
 * <p>
 * This class is needed to initialize, create and send a report about yesterday's and today's pages.
 * Report based two hash table.
 * The key of hash table is the url, the value of hash table is the html code of the matching url.
 */
public class SystemMonitoring {

    private final HashTableInitializer initializer;
    private final HashTableHandler handler;
    private final ReportWriter reportWriter;

    /**
     * Constructor for creating an instance of SystemMonitoring.
     * @param initializer  initializer of two hash table
     * @param handler      report creator
     * @param reportWriter report writer
     */
    public SystemMonitoring(HashTableInitializer initializer, HashTableHandler handler, ReportWriter reportWriter) {
        this.initializer = initializer;
        this.handler = handler;
        this.reportWriter = reportWriter;
    }

    /**
     * This method generate report based yesterday's and today's hash table
     */
    public void generateReport() {
        Map<String, String> yesterdayPages = new HashMap<>();
        Map<String, String> todayPages = new HashMap<>();

        initializer.init(yesterdayPages, todayPages);
        DataForReport data = handler.handle(yesterdayPages, todayPages);
        reportWriter.write(data);
    }
}