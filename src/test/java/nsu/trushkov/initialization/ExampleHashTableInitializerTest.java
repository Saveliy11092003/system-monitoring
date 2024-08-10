package nsu.trushkov.initialization;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExampleHashTableInitializerTest {

    @Test
    void init() {
        //given
        Map<String, String> yesterdayTable = new HashMap<>();
        Map<String, String> todayTable = new HashMap<>();

        //when
        HashTableInitializer initializer = new ExampleHashTableInitializer();
        initializer.init(yesterdayTable, todayTable);

        //then
        assertEquals(3, yesterdayTable.size());
        assertEquals("<html>Content 1</html>", yesterdayTable.get("http://example.com/page1"));
        assertEquals("<html>Content 2</html>", yesterdayTable.get("http://example.com/page2"));
        assertEquals("<html>Content 3</html>", yesterdayTable.get("http://example.com/page3"));

        assertEquals(3, todayTable.size());
        assertEquals("<html>New content 1</html>", todayTable.get("http://example.com/page1"));
        assertEquals("<html>Content 5</html>", todayTable.get("http://example.com/page5"));
        assertEquals("<html>Content 6</html>", todayTable.get("http://example.com/page6"));
    }
}