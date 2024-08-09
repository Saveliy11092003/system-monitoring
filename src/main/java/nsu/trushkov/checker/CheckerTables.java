package nsu.trushkov.checker;

import nsu.trushkov.model.ExceptionData;
import nsu.trushkov.model.enumeration.IncorrectTable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_TODAY;
import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_YESTERDAY;

public class CheckerTables {

    public ExceptionData check(Map<String, String> yesterdayTable, Map<String, String> todayTable) {

        Set<IncorrectTable> incorrectTables = new HashSet<>();
        Set<String> incorrectUrls = new HashSet<>();
        Set<String> urlsWithIncorrectPages = new HashSet<>();

        if (checkTables(yesterdayTable, todayTable, incorrectTables)) {
            checkUrls(yesterdayTable, incorrectUrls);
            checkUrls(todayTable, incorrectUrls);
            checkPages(yesterdayTable, urlsWithIncorrectPages);
            checkPages(todayTable, urlsWithIncorrectPages);
        }
        return new ExceptionData(incorrectTables, incorrectUrls, urlsWithIncorrectPages);
    }

    private boolean checkTables(Map<String, String> yesterdayTable, Map<String, String> todayTable,
                             Set<IncorrectTable> incorrectTables) {
        boolean isCorrectTables = true;
        if (yesterdayTable == null) {
            incorrectTables.add(INCORRECT_TABLE_YESTERDAY);
            isCorrectTables = false;
        }
        if (todayTable == null) {
            incorrectTables.add(INCORRECT_TABLE_TODAY);
            isCorrectTables = false;
        }
        return isCorrectTables;
    }


    private void checkUrls(Map<String, String> table, Set<String> incorrectUrls) {
        for (String url : table.keySet()) {
            try {
                URL checkUrl = new URL(url);
            } catch (MalformedURLException exception) {
                incorrectUrls.add(url);
            }
        }
    }

    private void checkPages(Map<String, String> table, Set<String> urlsWithIncorrectPages) {
        for (Map.Entry<String, String> entry : table.entrySet()) {
            if (entry.getValue() == null) {
                urlsWithIncorrectPages.add(entry.getKey());
            }
        }
    }
}