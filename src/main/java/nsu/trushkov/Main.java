package nsu.trushkov;

import nsu.trushkov.builder.ReportCreator;
import nsu.trushkov.handler.HashTableHandler;
import nsu.trushkov.initialization.ExampleHashTableInitializer;
import nsu.trushkov.initialization.HashTableInitializer;
import nsu.trushkov.service.SystemMonitoring;
import nsu.trushkov.writer.ConsoleReportWriter;
import nsu.trushkov.writer.ReportWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Main class contains the entry point for the application.
 * <p>
 * This class initializes components for system monitoring and generates a report.
 */
public class Main {


    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private final SystemMonitoring systemMonitoring;

    public Main(SystemMonitoring systemMonitoring) {
        this.systemMonitoring = systemMonitoring;
    }

    /**
     * The main method is the entry point of the application.
     * <p>
     * This method creates {@link SystemMonitoring} and generates a report using this class.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        log.info("Application starts");
        Main main = new Main(new SystemMonitoring(new ExampleHashTableInitializer(), new HashTableHandler(),
                new ConsoleReportWriter(new ReportCreator())));
        main.run();
        log.info("Application ended");
    }

    public void run() {
        systemMonitoring.generateReport();
    }

}
