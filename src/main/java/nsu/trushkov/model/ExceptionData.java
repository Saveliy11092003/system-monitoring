package nsu.trushkov.model;

import nsu.trushkov.model.enumeration.IncorrectTable;
import java.util.Set;

/**
 * This record is needed to store erroneous data
 * <p>
 * This class is used to display incorrect data to the user. The creation of this class is done for user convenience.
 * If we throw exceptions, the user may not understand what happened.
 * @param incorrectTables        the set of {@link IncorrectTable}
 * @param incorrectUrls          the set of incorrect url
 * @param urlsWithIncorrectPages the set of url with incorrect page
 */
public record ExceptionData(
        Set<IncorrectTable> incorrectTables,
        Set<String> incorrectUrls,
        Set<String> urlsWithIncorrectPages
) {
}
