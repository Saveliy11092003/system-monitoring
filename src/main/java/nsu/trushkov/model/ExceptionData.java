package nsu.trushkov.model;

import nsu.trushkov.model.enumeration.IncorrectTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record ExceptionData(
        Set<IncorrectTable> incorrectTables,
        Set<String> incorrectUrls,
        Set<String> urlsWithIncorrectPages
) {
}
