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
        ExceptionData exceptionData = new ExceptionData(new HashSet<>(), new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = testDataOfAppearedPages();
        Set<String> disappearedPages = testDataOfDisappearedPages();
        Set<String> changedPages = testDataOfChangedPages();

        DataForReport data = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);

        String expect = getCorrectReport();
        //when

        String actual = reportCreator.buildReport(data);

        //then
        assertEquals(expect, actual);
    }

    @Test
    public void buildReport_noPages() {
        //given
        ExceptionData exceptionData = new ExceptionData(new HashSet<>(), new HashSet<>(), new HashSet<>());
        Set<String> appearedPages = new HashSet<>();
        Set<String> disappearedPages = null;
        Set<String> changedPages = new HashSet<>();

        DataForReport data = new DataForReport(disappearedPages, appearedPages, changedPages, exceptionData);

        String expect = getReportNoPages();

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
        Set<IncorrectTable> incorrectTables = testDataOfIncorrectTables();

        ExceptionData exceptionData = new ExceptionData(incorrectTables, incorrectUrls, urlsWithIncorrectPages);
        DataForReport data = new DataForReport(new HashSet<>(), new HashSet<>(), new HashSet<>(), exceptionData);

        String expect = getReportWithTablesErrorInfo();

        //when
        String actual = reportCreator.buildReport(data);

        //then
        assertEquals(expect, actual);
    }

    @Test
    public void buildReport_withErrorInformation() {
        //given
        Set<String> incorrectUrls = testDataOfIncorrectUrls();

        Set<String> urlsWithIncorrectPages = testDataOfUrlsWithIncorrectPages();

        Set<IncorrectTable> incorrectTables = testDataOfIncorrectTable();

        ExceptionData exceptionData = new ExceptionData(incorrectTables, incorrectUrls, urlsWithIncorrectPages);
        DataForReport data = new DataForReport(new HashSet<>(), new HashSet<>(), new HashSet<>(), exceptionData);

        String expect = getReportWithErrorInformation();

        //when
        String actual = reportCreator.buildReport(data);

        //then
        assertEquals(expect, actual);
    }

    private String getCorrectReport() {
        StringBuilder report = new StringBuilder();
        addGreeting(report);

        addInformationAboutDisappearedPages(report);
        addInformationAboutAppearedPages(report);
        addInformationAboutChangedPages(report);

        addGoodbye(report);
        return report.toString();
    }

    private String getReportNoPages() {
        StringBuilder report = new StringBuilder();
        addGreeting(report);
        addNoPages(report);
        addGoodbye(report);

        return report.toString();
    }

    private String getReportWithTablesErrorInfo() {
        StringBuilder report = new StringBuilder();
        addGreeting(report);

        addNoPages(report);

        addMessageIncorrectTables(report);
        addGoodbye(report);

        return report.toString();
    }

    private String getReportWithErrorInformation() {
        StringBuilder report = new StringBuilder();
        addGreeting(report);

        addNoPages(report);
        addErrorInformation(report);

        addGoodbye(report);
        return report.toString();
    }

    private void addNoPages(StringBuilder report) {
        report.append("Исчезли следующие страницы:\n");
        report.append("Нет\n");

        report.append("\nПоявились следующие новые страницы:\n");
        report.append("Нет\n");

        report.append("\nИзменились следующие страницы:\n");
        report.append("Нет\n");
    }

    private void addGreeting(StringBuilder report) {
        report.append("Здравствуйте, дорогая и.о. секретаря\n\n");
        report.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n");
    }

    private void addGoodbye(StringBuilder report) {
        report.append("\nС уважением,\nавтоматизированная система мониторинга.");
    }

    private Set<String> testDataOfAppearedPages() {
        Set<String> appearedPages = new HashSet<>();
        appearedPages.add("http://example.com/page6");
        appearedPages.add("http://example.com/page4");
        return appearedPages;
    }

    private Set<String> testDataOfDisappearedPages() {
        Set<String> disappearedPages = new HashSet<>();
        disappearedPages.add("http://example.com/page2");
        disappearedPages.add("http://example.com/page3");
        return disappearedPages;
    }

    private Set<String> testDataOfChangedPages() {
        Set<String> changedPages = new HashSet<>();
        changedPages.add("http://example.com/page1");
        return changedPages;
    }

    private Set<IncorrectTable> testDataOfIncorrectTables() {
        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_TODAY);
        incorrectTables.add(INCORRECT_TABLE_YESTERDAY);
        return incorrectTables;
    }

    private Set<String> testDataOfIncorrectUrls() {
        Set<String> incorrectUrls = new HashSet<>();
        incorrectUrls.add("htt://example.com/page7");
        incorrectUrls.add("htt://example.com/page9");
        return incorrectUrls;
    }

    private Set<String> testDataOfUrlsWithIncorrectPages() {
        Set<String> urlsWithIncorrectPages = new HashSet<>();
        urlsWithIncorrectPages.add("http://example.com/page8");
        return urlsWithIncorrectPages;
    }

    private Set<IncorrectTable> testDataOfIncorrectTable() {
        Set<IncorrectTable> incorrectTables = new HashSet<>();
        incorrectTables.add(INCORRECT_TABLE_YESTERDAY);
        return incorrectTables;
    }

    private void addInformationAboutDisappearedPages(StringBuilder report) {
        report.append("Исчезли следующие страницы:\n");
        report.append("http://example.com/page2").append("\n");
        report.append("http://example.com/page3").append("\n");
    }

    private void addInformationAboutAppearedPages(StringBuilder report) {
        report.append("\nПоявились следующие новые страницы:\n");
        report.append("http://example.com/page6").append("\n");
        report.append("http://example.com/page4").append("\n");
    }

    private void addInformationAboutChangedPages(StringBuilder report) {
        report.append("\nИзменились следующие страницы:\n");
        report.append("http://example.com/page1").append("\n");
    }

    private void addMessageIncorrectTables(StringBuilder report) {
        report.append("\nК сожалению, мне были переданы некорректные таблицы с данными\n");
    }

    private void addErrorInformation(StringBuilder report) {
        report.append("\nБыли найдены следующие несуществующие страницы:\n");
        report.append("htt://example.com/page7").append("\n");
        report.append("htt://example.com/page9").append("\n");

        report.append("\nБыли переданы следующие страницы с некорректным содержимыи:\n");
        report.append("http://example.com/page8").append("\n");
    }

}