package nsu.trushkov.handler;

import nsu.trushkov.checker.CheckerTables;
import nsu.trushkov.model.DataForReport;
import nsu.trushkov.model.ExceptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(HashTableHandler.class);

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
        log.debug("ExceptionData - {}", exceptionData);

        if (!exceptionData.incorrectTables().isEmpty()) {
            return handleIncorrectTable(yesterdayTable, todayTable, exceptionData);
        }

        Set<String> disappearedUrls = getDisappearedUrls(yesterdayTable, todayTable, exceptionData);
        Set<String> appearedUrls = getAppearedUrls(yesterdayTable, todayTable, exceptionData);
        Set<String> changedUrls = getChangedUrls(yesterdayTable, todayTable, exceptionData);

        return new DataForReport(disappearedUrls, appearedUrls,changedUrls, exceptionData);
    }

    private Set<String> getDisappearedUrls(Map<String, String> yesterdayTable, Map<String, String> todayTable,
                                           ExceptionData exceptionData) {
        Set<String> disappearedUrls = new HashSet<>(yesterdayTable.keySet());
        disappearedUrls.removeAll(todayTable.keySet());
        removeIncorrectData(disappearedUrls, exceptionData);
        return disappearedUrls;
    }

    private Set<String> getAppearedUrls(Map<String, String> yesterdayTable, Map<String, String> todayTable,
                                        ExceptionData exceptionData) {
        Set<String> appearedUrls = new HashSet<>(todayTable.keySet());
        appearedUrls.removeAll(yesterdayTable.keySet());
        removeIncorrectData(appearedUrls, exceptionData);
        return appearedUrls;
    }

    private Set<String> getChangedUrls(Map<String, String> yesterdayTable, Map<String, String> todayTable,
                                       ExceptionData exceptionData) {
        Set<String> changedUrls = new HashSet<>();
        for (String url : yesterdayTable.keySet()) {
            if (todayTable.containsKey(url) && !todayTable.get(url).equals(yesterdayTable.get(url))) {
                changedUrls.add(url);
            }
        }
        removeIncorrectData(changedUrls, exceptionData);
        return changedUrls;
    }

    private void removeIncorrectData(Set<String> urls, ExceptionData exceptionData) {
        urls.removeAll(exceptionData.incorrectUrls());
        urls.removeAll(exceptionData.urlsWithIncorrectPages());
    }

    private DataForReport handleIncorrectTable(Map<String, String> yesterdayTable, Map<String, String> todayTable,
                                               ExceptionData exceptionData) {
        if (exceptionData.incorrectTables().contains(INCORRECT_TABLE_YESTERDAY) &&
                exceptionData.incorrectTables().contains(INCORRECT_TABLE_TODAY)) {
            return new DataForReport(null, null, null, exceptionData);
        } else if (exceptionData.incorrectTables().contains(INCORRECT_TABLE_TODAY)) {
            return new DataForReport(yesterdayTable.keySet(), null, null, exceptionData);
        } else {
            return new DataForReport(null, todayTable.keySet(), null, exceptionData);
        }
    }

}
