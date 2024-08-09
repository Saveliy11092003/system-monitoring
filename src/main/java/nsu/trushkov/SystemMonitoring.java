package nsu.trushkov;

import nsu.trushkov.handler.HashTableHandler;
import nsu.trushkov.initialization.HashTableInitializer;
import nsu.trushkov.model.Data;
import nsu.trushkov.writer.WriterReport;

import java.util.HashMap;
import java.util.Map;

public class SystemMonitoring {
    public static void main(String[] args) {
        Map<String, String> yesterdayPages = new HashMap<>();
        Map<String, String> todayPages = new HashMap<>();
        new HashTableInitializer().init(yesterdayPages, todayPages);
        Data data = new HashTableHandler().handle(yesterdayPages, todayPages);
        new WriterReport().write(data);
    }
}