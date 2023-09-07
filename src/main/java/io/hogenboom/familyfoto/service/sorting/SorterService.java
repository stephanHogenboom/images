package io.hogenboom.familyfoto.service.sorting;

import java.nio.file.Path;

public interface SorterService {
    SortingResult sortAllInUnsortedDirectoryAndCopyToTargetDirectory(Path source, Path target);
}
