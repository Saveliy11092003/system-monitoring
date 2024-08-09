package nsu.trushkov.initialization;

import java.util.Map;

public class HashTableInitializer {

    public void init(Map<String, String> yesterdayTable, Map<String, String> todayTable) {
        yesterdayTable.put("http://example.com/page1", "<html>New content 1</html>");
        yesterdayTable.put("http://example.com/page2", "<html>Old content 2</html>");
        yesterdayTable.put("http://example.com/page3", "<html>Old content 3</html>");

        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");
    }
}
