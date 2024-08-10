package nsu.trushkov.handler;

import nsu.trushkov.model.DataForReport;
import nsu.trushkov.model.ExceptionData;
import nsu.trushkov.model.enumeration.IncorrectTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_TODAY;
import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_YESTERDAY;
import static org.junit.jupiter.api.Assertions.*;

class HashTableHandlerTest {

    private HashTableHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new HashTableHandler();
    }

    @Test
    void handle_YesterdayTableIsNull() {
        //given
        Map<String, String> yesterdayTable = null;

        Map<String, String> todayTable = new HashMap<>();
        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");

        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_YESTERDAY);
        ExceptionData exceptionData = new ExceptionData(incorrectTables, new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = new HashSet<>(todayTable.keySet());
        Set<String> disappearedPages = new HashSet<>();
        Set<String> changedPages = new HashSet<>();
        DataForReport dataExpect = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    @Test
    void handle_TodayTableIsNull() {
        //given
        Map<String, String> yesterdayTable = new HashMap<>();
        yesterdayTable.put("http://example.com/page1", "<html>New content 1</html>");
        yesterdayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        yesterdayTable.put("http://example.com/page4", "<html>New content 4</html>");

        Map<String, String> todayTable = null;


        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_TODAY);
        ExceptionData exceptionData = new ExceptionData(incorrectTables, new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = new HashSet<>();
        Set<String> disappearedPages = new HashSet<>(yesterdayTable.keySet());
        Set<String> changedPages = new HashSet<>();
        DataForReport dataExpect = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    @Test
    void handle_TablesIsNull() {
        //given
        Map<String, String> yesterdayTable = null;
        Map<String, String> todayTable = null;

        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_TODAY);
        incorrectTables.add(INCORRECT_TABLE_YESTERDAY);
        ExceptionData exceptionData = new ExceptionData(incorrectTables, new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = new HashSet<>();
        Set<String> disappearedPages = new HashSet<>();
        Set<String> changedPages = new HashSet<>();
        DataForReport dataExpect = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    @Test
    void handle_validTables_validDataForReport() {
        //given
        Map<String, String> yesterdayTable = new HashMap<>();
        yesterdayTable.put("http://example.com/page1", "<html>Content 1</html>");
        yesterdayTable.put("http://example.com/page2", "<html>Content 2</html>");
        yesterdayTable.put("http://example.com/page3", "<html>Content 3</html>");
        yesterdayTable.put("http://example.com/page8", "<html>Content 8</html>");

        Map<String, String> todayTable = new HashMap<>();
        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");
        todayTable.put("http://example.com/page8", "<html>Content 8</html>");


        ExceptionData exceptionData = new ExceptionData(new HashSet<>(), new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = new HashSet<>();
        appearedPages.add("http://example.com/page6");
        appearedPages.add("http://example.com/page4");

        Set<String> disappearedPages = new HashSet<>();
        disappearedPages.add("http://example.com/page2");
        disappearedPages.add("http://example.com/page3");

        Set<String> changedPages = new HashSet<>();
        changedPages.add("http://example.com/page1");

        DataForReport dataExpect = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    @Test
    void handle_tablesWithIncorrectUrlAndPages_dataForReportWithErrorInfo() {
        //given
        Map<String, String> yesterdayTable = new HashMap<>();
        yesterdayTable.put("http://example.com/page1", "<html>Content 1</html>");
        yesterdayTable.put("http://example.com/page2", "<html>Content 2</html>");
        yesterdayTable.put("http://example.com/page3", "<html>Content 3</html>");
        yesterdayTable.put("htt://example.com/page7", "<html>Content 7</html>");
        yesterdayTable.put("htt://example.com/page9", "<html>Content 9</html>");

        Map<String, String> todayTable = new HashMap<>();
        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");
        yesterdayTable.put("http://example.com/page8", null);
        yesterdayTable.put("htt://example.com/page9", "<html>New content 9</html>");

        Set<String> incorrectUrls = new HashSet<>();
        incorrectUrls.add("htt://example.com/page7");
        incorrectUrls.add("htt://example.com/page9");
        Set<String> urlsWithIncorrectPages = new HashSet<>();
        urlsWithIncorrectPages.add("http://example.com/page8");

        ExceptionData exceptionData = new ExceptionData(new HashSet<>(), incorrectUrls, urlsWithIncorrectPages);
        Set<String> appearedPages = new HashSet<>();
        appearedPages.add("http://example.com/page6");
        appearedPages.add("http://example.com/page4");

        Set<String> disappearedPages = new HashSet<>();
        disappearedPages.add("http://example.com/page2");
        disappearedPages.add("http://example.com/page3");

        Set<String> changedPages = new HashSet<>();
        changedPages.add("http://example.com/page1");

        DataForReport dataExpect = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

}