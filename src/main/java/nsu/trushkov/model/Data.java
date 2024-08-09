package nsu.trushkov.model;

import java.util.Set;

public record Data(Set<String> disappearedUrls,
                   Set<String> appearedUrls,
                   Set<String> changedUrls,
                   ExceptionData exceptionData) {
}
