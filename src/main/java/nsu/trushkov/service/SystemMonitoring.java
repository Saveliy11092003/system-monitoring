package nsu.trushkov.service;

import nsu.trushkov.handler.HashTableHandler;
import nsu.trushkov.initialization.HashTableInitializer;
import nsu.trushkov.model.DataForReport;
import nsu.trushkov.writer.ReportWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(SystemMonitoring.class);
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
        log.info("Initialization of tables ended");
        DataForReport data = handler.handle(yesterdayPages, todayPages);
        log.info("Data for report was generated");
        reportWriter.write(data);
        log.info("Report sent");
    }
}