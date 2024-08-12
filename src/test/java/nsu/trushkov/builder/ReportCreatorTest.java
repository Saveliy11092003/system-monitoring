package nsu.trushkov.builder;

import nsu.trushkov.model.DataForReport;
import nsu.trushkov.model.ExceptionData;
import nsu.trushkov.model.enumeration.IncorrectTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_TODAY;
import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_YESTERDAY;
import static org.junit.jupiter.api.Assertions.*;

class ReportCreatorTest {

    private ReportCreator reportCreator;

    @BeforeEach
    public void setUp() {
        reportCreator = new ReportCreator();
    }

    @Test
    void buildReport_correctWork() {
        //given
        Set<String> incorrectUrls = new HashSet<>();
        Set<String> urlsWithIncorrectPages = new HashSet<>();
        Set<IncorrectTable> incorrectTables = new HashSet<>();

        ExceptionData exceptionData = new ExceptionData(incorrectTables, incorrectUrls, urlsWithIncorrectPages);
        Set<String> appearedPages = new HashSet<>();
        appearedPages.add("http://example.com/page6");
        appearedPages.add("http://example.com/page4");

        Set<String> disappearedPages = new HashSet<>();
        disappearedPages.add("http://example.com/page2");
        disappearedPages.add("http://example.com/page3");

        Set<String> changedPages = new HashSet<>();
        changedPages.add("http://example.com/page1");

        DataForReport data = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);


        StringBuilder report = new StringBuilder();
        report.append("Здравствуйте, дорогая и.о. секретаря\n\n");
        report.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n");

        report.append("Исчезли следующие страницы:\n");
        report.append("http://example.com/page2").append("\n");
        report.append("http://example.com/page3").append("\n");

        report.append("\nПоявились следующие новые страницы:\n");
        report.append("http://example.com/page6").append("\n");
        report.append("http://example.com/page4").append("\n");

        report.append("\nИзменились следующие страницы:\n");
        report.append("http://example.com/page1").append("\n");
        report.append("\nС уважением,\nавтоматизированная система мониторинга.");

        String expect = report.toString();
        //when

        String actual = reportCreator.buildReport(data);

        //then
        assertEquals(expect, actual);
    }

    @Test
    public void buildReport_noPages() {
        //given
        Set<String> incorrectUrls = new HashSet<>();
        Set<String> urlsWithIncorrectPages = new HashSet<>();
        Set<IncorrectTable> incorrectTables = new HashSet<>();

        ExceptionData exceptionData = new ExceptionData(incorrectTables, incorrectUrls, urlsWithIncorrectPages);
        Set<String> appearedPages = new HashSet<>();
        appearedPages.add("http://example.com/page6");
        appearedPages.add("http://example.com/page4");

        Set<String> disappearedPages = null;

        Set<String> changedPages = new HashSet<>();

        DataForReport data = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);


        StringBuilder report = new StringBuilder();
        report.append("Здравствуйте, дорогая и.о. секретаря\n\n");
        report.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n");

        report.append("Исчезли следующие страницы:\n");
        report.append("Нет\n");

        report.append("\nПоявились следующие новые страницы:\n");
        report.append("http://example.com/page6").append("\n");
        report.append("http://example.com/page4").append("\n");

        report.append("\nИзменились следующие страницы:\n");
        report.append("Нет\n");
        report.append("\nС уважением,\nавтоматизированная система мониторинга.");

        String expect = report.toString();

        //when
        String actual = reportCreator.buildReport(data);

        //then
        assertEquals(expect, actual);
    }

    @Test
    public void buildReport_withTableErrorInformation() {
        //given
        Set<String> incorrectUrls = new HashSet<>();
        Set<String> urlsWithIncorrectPages = new HashSet<>();

        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_TODAY);
        incorrectTables.add(INCORRECT_TABLE_YESTERDAY);

        ExceptionData exceptionData = new ExceptionData(incorrectTables, incorrectUrls, urlsWithIncorrectPages);
        Set<String> appearedPages = new HashSet<>();

        Set<String> disappearedPages = new HashSet<>();

        Set<String> changedPages = new HashSet<>();

        DataForReport data = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);

        StringBuilder report = new StringBuilder();
        report.append("Здравствуйте, дорогая и.о. секретаря\n\n");
        report.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n");

        report.append("Исчезли следующие страницы:\n");
        report.append("Нет\n");

        report.append("\nПоявились следующие новые страницы:\n");
        report.append("Нет\n");

        report.append("\nИзменились следующие страницы:\n");
        report.append("Нет\n");

        report.append("К сожалению, мне были переданы некорректные таблицы с данными\n");
        report.append("\nС уважением,\nавтоматизированная система мониторинга.");

        String expect = report.toString();

        //when
        String actual = reportCreator.buildReport(data);

        //then
        assertEquals(expect, actual);
    }

    @Test
    public void buildReport_withErrorInformation() {
        //given
        Set<String> incorrectUrls = new HashSet<>();
        incorrectUrls.add("htt://example.com/page7");
        incorrectUrls.add("htt://example.com/page9");
        Set<String> urlsWithIncorrectPages = new HashSet<>();
        urlsWithIncorrectPages.add("http://example.com/page8");

        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_YESTERDAY);

        ExceptionData exceptionData = new ExceptionData(incorrectTables, incorrectUrls, urlsWithIncorrectPages);
        Set<String> appearedPages = new HashSet<>();

        Set<String> disappearedPages = new HashSet<>();

        Set<String> changedPages = new HashSet<>();

        DataForReport data = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);

        StringBuilder report = new StringBuilder();
        report.append("Здравствуйте, дорогая и.о. секретаря\n\n");
        report.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n");

        report.append("Исчезли следующие страницы:\n");
        report.append("Нет\n");

        report.append("\nПоявились следующие новые страницы:\n");
        report.append("Нет\n");

        report.append("\nИзменились следующие страницы:\n");
        report.append("Нет\n");

        report.append("Были найдены следующие несуществующие страницы:\n");
        report.append("htt://example.com/page7").append("\n");
        report.append("htt://example.com/page9").append("\n");

        report.append("Были переданы следующие страницы с некорректным содержимыи:\n");
        report.append("http://example.com/page8").append("\n");

        report.append("\nС уважением,\nавтоматизированная система мониторинга.");

        String expect = report.toString();

        //when
        String actual = reportCreator.buildReport(data);

        //then
        assertEquals(expect, actual);
    }

}