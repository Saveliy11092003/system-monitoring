package nsu.trushkov.writer;

import nsu.trushkov.model.Data;

public class WriterReport {

    public void write(Data data) {
        StringBuilder report = new StringBuilder();
        report.append("Здравствуйте, дорогая и.о. секретаря\n\n");

        report.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n");

        report.append("Исчезли следующие страницы:\n");
        if (data.disappearedUrls() == null || data.disappearedUrls().isEmpty()) {
            report.append("Нет\n");
        } else {
            for (String url : data.disappearedUrls()) {
                report.append(url).append("\n");
            }
        }

        report.append("\nПоявились следующие новые страницы:\n");
        if (data.appearedUrls() == null || data.appearedUrls().isEmpty()) {

            report.append("Нет\n");
        } else {
            for (String url : data.appearedUrls()) {
                report.append(url).append("\n");
            }
        }

        report.append("\nИзменились следующие страницы:\n");
        if (data.changedUrls() == null || data.changedUrls().isEmpty()) {
            report.append("Нет\n");
        } else {
            for (String url : data.changedUrls()) {
                report.append(url).append("\n");
            }
        }

        if (!data.exceptionData().incorrectUrls().isEmpty()) {
            report.append("Также были найдены следующие несуществующие страницы:\n");
            for (String url : data.exceptionData().incorrectUrls()) {
                report.append(url).append("\n");
            }
        }

        if (!data.exceptionData().urlsWithIncorrectPages().isEmpty()) {
            report.append("Мне были переданы следующие страницы с некорректным содержимыи\n");
            for (String url : data.exceptionData().urlsWithIncorrectPages()) {
                report.append(url).append("\n");
            }
        }

        report.append("\nС уважением,\nавтоматизированная система мониторинга.");

        System.out.println(report);
    }

}
