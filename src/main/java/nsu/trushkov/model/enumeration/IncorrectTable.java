package nsu.trushkov.model.enumeration;

import nsu.trushkov.model.ExceptionData;

/**
 * This enum is required to mark the incorrect table in {@link ExceptionData}
 */
public enum IncorrectTable {
    /**
     * Yesterday's table is null
     */
    INCORRECT_TABLE_YESTERDAY,
    /**
     * Today's table is null
     */
    INCORRECT_TABLE_TODAY
}
