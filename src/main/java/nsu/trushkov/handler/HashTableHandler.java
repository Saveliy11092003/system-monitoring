package nsu.trushkov.handler;

import nsu.trushkov.checker.CheckerTables;
import nsu.trushkov.model.Data;
import nsu.trushkov.model.ExceptionData;
import nsu.trushkov.model.enumeration.IncorrectTable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_TODAY;
import static nsu.trushkov.model.enumeration.IncorrectTable.INCORRECT_TABLE_YESTERDAY;

public class HashTableHandler {

    public Data handle(Map<String, String> yesterdayTable, Map<String, String> todayTable) {

        ExceptionData exceptionData = new CheckerTables().check(yesterdayTable, todayTable);

        if (!exceptionData.incorrectTables().isEmpty()) {
            if (exceptionData.incorrectTables().contains(INCORRECT_TABLE_YESTERDAY) &&
            exceptionData.incorrectTables().contains(INCORRECT_TABLE_TODAY)) {
                return new Data(null, null, null, exceptionData);
            }
            if (exceptionData.incorrectTables().contains(INCORRECT_TABLE_TODAY)) {
                return new Data(yesterdayTable.keySet(), null, null, exceptionData);
            }
            if (exceptionData.incorrectTables().contains(INCORRECT_TABLE_YESTERDAY)){
                return new Data(null, todayTable.keySet(), null, exceptionData);
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
        return new Data(disappearedUrls, appearedUrls,changedUrls, exceptionData);
    }

}
