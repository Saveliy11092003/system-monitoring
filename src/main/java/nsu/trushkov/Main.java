package nsu.trushkov;

import nsu.trushkov.handler.HashTableHandler;
import nsu.trushkov.initialization.ExampleHashTableInitializer;
import nsu.trushkov.initialization.HashTableInitializer;
import nsu.trushkov.service.SystemMonitoring;
import nsu.trushkov.writer.ConsoleReportWriter;
import nsu.trushkov.writer.ReportWriter;

/**
 * The Main class contains the entry point for the application.
 * <p>
 * This class initializes components for system monitoring and generates a report.
 */
public class Main {

    /**
     * The main method is the entry point of the application.
     * <p>
     * This method creates {@link SystemMonitoring} and generates a report using this class.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        HashTableInitializer initializer = new ExampleHashTableInitializer();
        HashTableHandler handler = new HashTableHandler();
        ReportWriter writer = new ConsoleReportWriter();

        SystemMonitoring systemMonitoring = new SystemMonitoring(initializer, handler, writer);
        systemMonitoring.generateReport();
    }
}
