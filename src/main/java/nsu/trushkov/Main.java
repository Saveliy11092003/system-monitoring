package nsu.trushkov;

import nsu.trushkov.builder.ReportCreator;
import nsu.trushkov.handler.HashTableHandler;
import nsu.trushkov.initialization.ExampleHashTableInitializer;
import nsu.trushkov.initialization.HashTableInitializer;
import nsu.trushkov.service.SystemMonitoring;
import nsu.trushkov.writer.ConsoleReportWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Main class contains the entry point for the application.
 * <p>
 * This class initializes components for system monitoring and generates a report.
 */
public class Main {


    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * The main method is the entry point of the application.
     * <p>
     * This method creates {@link SystemMonitoring} and generates a report using this class.
     * @param args command line arguments
     */
    public static void main(String[] args) {

        log.info("Application starts");
        HashTableInitializer initializer = new ExampleHashTableInitializer();
        HashTableHandler handler = new HashTableHandler();
        ConsoleReportWriter writer = new ConsoleReportWriter(new ReportCreator());
        SystemMonitoring systemMonitoring = new SystemMonitoring(initializer, handler, writer);
        systemMonitoring.generateReport();
        log.info("Application ended");
    }

}
