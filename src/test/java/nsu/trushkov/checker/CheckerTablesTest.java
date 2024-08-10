package nsu.trushkov.checker;

import nsu.trushkov.model.ExceptionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_TODAY;
import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_YESTERDAY;
import static org.junit.jupiter.api.Assertions.*;

class CheckerTablesTest {

    private CheckerTables checkerTables;

    @BeforeEach
    public void setUp() {
        checkerTables = new CheckerTables();
    }

    @Test
    void check_onlyYesterdayTableIsNull() {

        //given
        Map<String, String> yesterdayTable = null;

        Map<String, String> todayTable = new HashMap<>();
        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");

        //when
        ExceptionData exceptionData = checkerTables.check(yesterdayTable, todayTable);

        //then
        assertTrue(exceptionData.incorrectTables().contains(INCORRECT_TABLE_YESTERDAY));
        assertFalse(exceptionData.incorrectTables().contains(INCORRECT_TABLE_TODAY));
    }

    @Test
    void check_onlyTodayTableIsNull() {

        //given
        Map<String, String> yesterdayTable = new HashMap<>();
        yesterdayTable.put("http://example.com/page1", "<html>New content 1</html>");
        yesterdayTable.put("http://example.com/page2", "<html>Old content 2</html>");
        yesterdayTable.put("http://example.com/page3", "<html>Old content 3</html>");

        Map<String, String> todayTable = null;

        //when
        ExceptionData exceptionData = checkerTables.check(yesterdayTable, todayTable);

        //then
        assertTrue(exceptionData.incorrectTables().contains(INCORRECT_TABLE_TODAY));
        assertFalse(exceptionData.incorrectTables().contains(INCORRECT_TABLE_YESTERDAY));
    }

    @Test
    void check_withIncorrectUrls() {
        //given
        Map<String, String> yesterdayTable = new HashMap<>();
        yesterdayTable.put("htt://example.com/page1", "<html>New content 1</html>");
        yesterdayTable.put("http://example.com/page2", "<html>Old content 2</html>");
        yesterdayTable.put("http://example.com/page3", "<html>Old content 3</html>");

        Map<String, String> todayTable = new HashMap<>();
        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("htt://example.com/page2", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");

        Set<String> expectIncorrectUrls = new HashSet<>();
        expectIncorrectUrls.add("htt://example.com/page1");
        expectIncorrectUrls.add("htt://example.com/page2");

        //when
        ExceptionData exceptionData = checkerTables.check(yesterdayTable, todayTable);

        //then
        assertEquals(expectIncorrectUrls, exceptionData.incorrectUrls());
    }

    @Test
    void check_withIncorrectPages() {
        //given
        Map<String, String> yesterdayTable = new HashMap<>();
        yesterdayTable.put("http://example.com/page1", "<html>New content 1</html>");
        yesterdayTable.put("http://example.com/page2", null);
        yesterdayTable.put("http://example.com/page3", "<html>Old content 3</html>");

        Map<String, String> todayTable = new HashMap<>();
        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page2", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", null);

        Set<String> expectIncorrectUrls = new HashSet<>();
        expectIncorrectUrls.add("http://example.com/page2");
        expectIncorrectUrls.add("http://example.com/page4");

        //when
        ExceptionData exceptionData = checkerTables.check(yesterdayTable, todayTable);

        //then
        assertEquals(expectIncorrectUrls, exceptionData.urlsWithIncorrectPages());
    }

}