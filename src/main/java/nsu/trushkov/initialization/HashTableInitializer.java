package nsu.trushkov.initialization;

import java.util.Map;

/**
 * Interface for initializing two hash tables.
 * <p>
 * Classes that implement this interface must implement initialization logic two hash tables.
 */
public interface HashTableInitializer {

    /**
     * This method is needed to init two hash table (key - url, value - html code of the matching url)
     * @param yesterdayTable yesterday's hash table
     * @param todayTable     today's hash table
     */
    void init(Map<String, String> yesterdayTable, Map<String, String> todayTable);
}
