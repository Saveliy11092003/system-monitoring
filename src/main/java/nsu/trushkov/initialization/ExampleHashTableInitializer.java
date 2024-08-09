package nsu.trushkov.initialization;

import java.util.Map;

/**
 * Initializer of yesterday's and today's tables.
 * <p>
 * This is an example of a class that is needed to initialize yesterday's and today's tables.
 * The key of hash table is the url, the value of hash table is the html code of the matching url.
 */
public class ExampleHashTableInitializer implements HashTableInitializer {

    /**
     * This method is needed to init two hash table.
     * <p>
     * This method initializes tables with sample data.
     * @param yesterdayTable yesterday's hash table
     * @param todayTable     today's hash table
     */
    public void init(Map<String, String> yesterdayTable, Map<String, String> todayTable) {
        yesterdayTable.put("http://example.com/page1", "<html>New content 1</html>");
        yesterdayTable.put("http://example.com/page2", "<html>Old content 2</html>");
        yesterdayTable.put("http://example.com/page3", "<html>Old content 3</html>");

        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");
    }
}
