package io.hogenboom.familyfoto.service.sorting;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface SortingResult {

    default boolean successful() {
        return false;
    }

    Set<String> errorReasons();


    static SortingResult combine(SortingResult a, SortingResult b) {
        if (a instanceof FailedSortingResult || b instanceof  FailedSortingResult) {
            return FailedSortingResult.ofMessages(a.errorReasons(), b.errorReasons());
        }
        return a;
    }
    static class SuccessfulSortingResult implements SortingResult {
        public boolean successful() {
            return true;
        }

        @Override
        public Set<String> errorReasons() {
            return Collections.emptySet();
        }
    }

    static record FailedSortingResult(Set<String> errorReasons) implements SortingResult {
       public static FailedSortingResult ofMessages(Set<String> a, Set<String> b) {
            var messages = Stream.concat(a.stream(), b.stream())
                    .collect(Collectors.toSet());

            return new FailedSortingResult(messages);
        }

    }
}
