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
        Map<String, String> todayTable = getCorrectTodayTable();
        DataForReport dataExpect = getDataForReportYesterdayTableIsNull(todayTable);

        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    @Test
    void handle_TodayTableIsNull() {
        //given
        Map<String, String> yesterdayTable = getCorrectYesterdayTable();
        Map<String, String> todayTable = null;
        DataForReport dataExpect = getDataForReportTodayTableIsNull(yesterdayTable);

        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    @Test
    void handle_TablesAreNull() {
        //given
        Map<String, String> yesterdayTable = null;
        Map<String, String> todayTable = null;
        DataForReport dataExpect = getDataForReportTablesAreNull();

        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    @Test
    void handle_validTables_validDataForReport() {
        //given
        Map<String, String> yesterdayTable = getCorrectYesterdayTable();
        Map<String, String> todayTable = getCorrectTodayTable();
        DataForReport dataExpect = getCorrectDataForReport();

        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    @Test
    void handle_tablesWithIncorrectUrlAndPages_dataForReportWithErrorInfo() {
        //given
        Map<String, String> yesterdayTable = getYesterdayTableWithIncorrectUrls();
        Map<String, String> todayTable = getTodayTableWithIncorrectUrlsAndPages();
        DataForReport dataExpect = getDataForReportWithErrorInfo();

        //when
        DataForReport dataActual = handler.handle(yesterdayTable, todayTable);

        //then
        assertEquals(dataExpect, dataActual);
    }

    private DataForReport getDataForReportYesterdayTableIsNull(Map<String, String> todayTable) {
        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_YESTERDAY);
        ExceptionData exceptionData = new ExceptionData(incorrectTables, new HashSet<>(), new HashSet<>());

        Set<String> appearedPages = new HashSet<>(todayTable.keySet());
        Set<String> disappearedPages = new HashSet<>();
        Set<String> changedPages = new HashSet<>();

        return new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
    }

    private DataForReport getDataForReportTodayTableIsNull(Map<String, String> yesterdayTable) {
        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_TODAY);
        ExceptionData exceptionData = new ExceptionData(incorrectTables, new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = new HashSet<>();
        Set<String> disappearedPages = new HashSet<>(yesterdayTable.keySet());
        Set<String> changedPages = new HashSet<>();
        return new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
    }

    private DataForReport getDataForReportTablesAreNull() {
        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_TODAY);
        incorrectTables.add(INCORRECT_TABLE_YESTERDAY);
        ExceptionData exceptionData = new ExceptionData(incorrectTables, new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = new HashSet<>();
        Set<String> disappearedPages = new HashSet<>();
        Set<String> changedPages = new HashSet<>();
        return new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
    }

    private DataForReport getDataForReportWithErrorInfo() {
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

        return new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
    }

    private DataForReport getCorrectDataForReport() {
        ExceptionData exceptionData = new ExceptionData(new HashSet<>(), new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = new HashSet<>();
        appearedPages.add("http://example.com/page6");
        appearedPages.add("http://example.com/page4");

        Set<String> disappearedPages = new HashSet<>();
        disappearedPages.add("http://example.com/page2");
        disappearedPages.add("http://example.com/page3");

        Set<String> changedPages = new HashSet<>();
        changedPages.add("http://example.com/page1");

        return new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);
    }

    private Map<String, String> getCorrectTodayTable() {
        Map<String, String> todayTable = new HashMap<>();
        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");
        todayTable.put("http://example.com/page8", "<html>Content 8</html>");
        return todayTable;
    }

    private Map<String, String> getCorrectYesterdayTable() {
        Map<String, String> yesterdayTable = new HashMap<>();
        yesterdayTable.put("http://example.com/page1", "<html>Content 1</html>");
        yesterdayTable.put("http://example.com/page2", "<html>Content 2</html>");
        yesterdayTable.put("http://example.com/page3", "<html>Content 3</html>");
        yesterdayTable.put("http://example.com/page8", "<html>Content 8</html>");

        return yesterdayTable;
    }

    private Map<String, String> getYesterdayTableWithIncorrectUrls() {
        Map<String, String> yesterdayTable = new HashMap<>();
        yesterdayTable.put("http://example.com/page1", "<html>Content 1</html>");
        yesterdayTable.put("http://example.com/page2", "<html>Content 2</html>");
        yesterdayTable.put("http://example.com/page3", "<html>Content 3</html>");
        yesterdayTable.put("htt://example.com/page7", "<html>Content 7</html>");
        yesterdayTable.put("htt://example.com/page9", "<html>Content 9</html>");

        return yesterdayTable;
    }

    private Map<String, String> getTodayTableWithIncorrectUrlsAndPages() {
        Map<String, String> todayTable = new HashMap<>();
        todayTable.put("http://example.com/page1", "<html>New content 1</html>");
        todayTable.put("http://example.com/page6", "<html>Old content 2</html>");
        todayTable.put("http://example.com/page4", "<html>New content 4</html>");
        todayTable.put("http://example.com/page8", null);
        todayTable.put("htt://example.com/page9", "<html>New content 9</html>");
        return todayTable;
    }

}