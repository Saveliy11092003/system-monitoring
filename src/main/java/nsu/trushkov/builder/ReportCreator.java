package nsu.trushkov.builder;

import nsu.trushkov.model.DataForReport;
import nsu.trushkov.model.enumeration.IncorrectTable;

import java.util.Set;

import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_TODAY;
import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_YESTERDAY;

/**
 * This class is needed to create a report.
 * <p>
 * This class creates report that contains disappeared, appeared, changed urls and error information.
 */
public class ReportCreator {

    /**
     * This method create a report based {@link DataForReport}
     * @param data data for report
     * @return report
     */
    public String buildReport(DataForReport data) {
        StringBuilder report = new StringBuilder();
        report.append("Здравствуйте, дорогая и.о. секретаря\n\n");
        report.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n");

        addPageInformation(data.disappearedUrls(), report, "Исчезли следующие страницы:\n");
        addPageInformation(data.appearedUrls(), report, "\nПоявились следующие новые страницы:\n");
        addPageInformation(data.changedUrls(), report, "\nИзменились следующие страницы:\n");

        addTableErrorInformation(data.exceptionData().incorrectTables(), report,
                "К сожалению, мне были переданы не корректные данные\n");
        addErrorInformation(data.exceptionData().incorrectUrls(), report,
                "Также были найдены следующие несуществующие страницы:\n");
        addErrorInformation(data.exceptionData().urlsWithIncorrectPages(), report,
                "Мне были переданы следующие страницы с некорректным содержимыи:\n");

        report.append("\nС уважением,\nавтоматизированная система мониторинга.");

        return report.toString();
    }

    private void addPageInformation(Set<String> urls, StringBuilder report, String message) {
        report.append(message);
        if (urls == null || urls.isEmpty()) {
            report.append("Нет\n");
        } else {
            for (String url : urls) {
                report.append(url).append("\n");
            }
        }
    }

    private void addErrorInformation(Set<String> urls, StringBuilder report, String message) {
        if (!urls.isEmpty()) {
            report.append(message);
            for (String url : urls) {
                report.append(url).append("\n");
            }
        }
    }

    private void addTableErrorInformation(Set<IncorrectTable> incorrectTables, StringBuilder report, String message) {
        if (incorrectTables.contains(INCORRECT_TABLE_YESTERDAY) && incorrectTables.contains(INCORRECT_TABLE_TODAY)) {
            report.append(message);
        }
    }

}
