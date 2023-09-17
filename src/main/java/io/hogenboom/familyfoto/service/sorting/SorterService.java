package io.hogenboom.familyfoto.service.sorting;

public interface SorterService {
    SortingResult sortAllInUnsortedDirectoryAndCopyToTargetDirectory();
    SortingResult  moveVideos();
    SortingResult renameFiles();
}
