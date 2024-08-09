package nsu.trushkov.handler;

import nsu.trushkov.checker.CheckerTables;
import nsu.trushkov.model.DataForReport;
import nsu.trushkov.model.ExceptionData;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_TODAY;
import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_YESTERDAY;

/**
 * This class is used to generate report based on yesterday's and today's tables.
 * <p>
 * This class is needed to collect a report, which consists of pages that have appeared, disappeared, and changed.
 */
public class HashTableHandler {

    /**
     * This method generates {@link DataForReport}.
     * <p>
     * The method generates {@link DataForReport} that containing appeared, disappeared, changed pages and error info.
     * Incorrect pages are removed from these pages to return correct information.
     * @param yesterdayTable yesterday's hash table
     * @param todayTable     today's hash table
     * @return data that containing appeared, disappeared, changed pages and error information
     */
    public DataForReport handle(Map<String, String> yesterdayTable, Map<String, String> todayTable) {

        ExceptionData exceptionData = new CheckerTables().check(yesterdayTable, todayTable);

        if (!exceptionData.incorrectTables().isEmpty()) {
            if (exceptionData.incorrectTables().contains(INCORRECT_TABLE_YESTERDAY) &&
            exceptionData.incorrectTables().contains(INCORRECT_TABLE_TODAY)) {
                return new DataForReport(null, null, null, exceptionData);
            }
            if (exceptionData.incorrectTables().contains(INCORRECT_TABLE_TODAY)) {
                return new DataForReport(yesterdayTable.keySet(), null, null, exceptionData);
            }
            if (exceptionData.incorrectTables().contains(INCORRECT_TABLE_YESTERDAY)){
                return new DataForReport(null, todayTable.keySet(), null, exceptionData);
            }
        }

        Set<String> disappearedUrls = new HashSet<>(yesterdayTable.keySet());
        disappearedUrls.removeAll(todayTable.keySet());
        disappearedUrls.removeAll(exceptionData.incorrectUrls());
        disappearedUrls.removeAll(exceptionData.urlsWithIncorrectPages());

        Set<String> appearedUrls = new HashSet<>(todayTable.keySet());
        appearedUrls.removeAll(yesterdayTable.keySet());
        appearedUrls.removeAll(exceptionData.incorrectUrls());
        appearedUrls.removeAll(exceptionData.urlsWithIncorrectPages());

        Set<String> changedUrls = new HashSet<>();
        for (String url : yesterdayTable.keySet()) {
            if (todayTable.containsKey(url) && !todayTable.get(url).equals(yesterdayTable.get(url))) {
                changedUrls.add(url);
            }
        }
        changedUrls.removeAll(exceptionData.incorrectUrls());
        changedUrls.removeAll(exceptionData.urlsWithIncorrectPages());
        return new DataForReport(disappearedUrls, appearedUrls,changedUrls, exceptionData);
    }

}
