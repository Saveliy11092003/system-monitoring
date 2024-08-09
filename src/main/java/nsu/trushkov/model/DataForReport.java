package nsu.trushkov.model;

import java.util.Set;

/**
 * This record that contains data for report
 * <p>
 * This record that contains disappeared, appeared, changed urls and error information
 * @param disappearedUrls urls that disappeared from yesterday's table
 * @param appearedUrls    urls that appeared in today's table
 * @param changedUrls     urls whose pages have changed
 * @param exceptionData   record that contains error information
 */
public record DataForReport(Set<String> disappearedUrls,
                            Set<String> appearedUrls,
                            Set<String> changedUrls,
                            ExceptionData exceptionData) {
}
