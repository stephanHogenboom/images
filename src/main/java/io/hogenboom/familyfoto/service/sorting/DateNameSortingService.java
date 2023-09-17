package io.hogenboom.familyfoto.service.sorting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DateNameSortingService implements SorterService {

    private static Logger LOGGER = LoggerFactory.getLogger(SorterService.class);
    private Path unsortedPath;
    private Path sortedPath;



    public DateNameSortingService(
            @Value("${sorting.path}") String unsortedPath,
            @Value("${image.basepath}") String sortedPath) {
        this.unsortedPath = Paths.get(unsortedPath);
        this.sortedPath = Paths.get(sortedPath);
    }

    @Override
    public SortingResult sortAllInUnsortedDirectoryAndCopyToTargetDirectory() {
        try (var files = Files.walk(unsortedPath)) {
            return files.map(path -> {

                var name = path.getFileName().toString();
                if (name.matches("[0-9]{8}.*") && name.endsWith(".jpg")) {

                    System.out.println("moving file " +  path.getFileName());
                    var sorter = Sorter.fromNameString(name);
                    sorter.createYearAndMonthDirectories(sortedPath);

                    return copyFiles(path, sortedPath, sorter);

                }
                return new SortingResult.SuccessfulSortingResult();
            }).reduce(new SortingResult.SuccessfulSortingResult(), successOrFailedResultWithAllMessages());

        } catch (IOException ex) {
            return new SortingResult.FailedSortingResult(Set.of(ex.getMessage()));
        }
    }

    private static BinaryOperator<SortingResult> successOrFailedResultWithAllMessages() {
        return (a, b) -> {
            if (a instanceof SortingResult.FailedSortingResult || b instanceof SortingResult.FailedSortingResult) {
                return new SortingResult.FailedSortingResult(collectErrorMessages(a, b));
            }
            return new SortingResult.SuccessfulSortingResult();
        };
    }

    /**
     * for files in the format YYMMDD_hhmmss
     */
    @Override
    public SortingResult moveVideos() {
        try (var files = Files.walk(unsortedPath)) {
            return files.map(file -> {
                var name = file.toString();
                if (name.matches("[0-9]{8}.*") && name.endsWith(".mp4")) {
                    System.out.println(name);
                    var sorter = Sorter.fromNameString(file.getFileName().toString());
                    var videoDir = sortedPath.resolve(Paths.get("videos"));
                    var videoDirCratedSuccessFully = sorter.createVideoDirectory(sortedPath);
                    if (!videoDirCratedSuccessFully.successful()) {
                        return new SortingResult.FailedSortingResult(Set.of("video dir could not be created"));
                    }

                    return copyFiles(file, videoDir, sorter);
                }
                return new SortingResult.SuccessfulSortingResult();
            }).reduce(new SortingResult.SuccessfulSortingResult(), successOrFailedResultWithAllMessages());
        } catch (Exception e) {
            return new SortingResult.FailedSortingResult(Set.of(e.getMessage()));
        }
    }
    @Override
    public SortingResult renameFiles() {
        try (var files = Files.walk(unsortedPath)) {
            return files.map(file -> {
                var validSibling = file.resolveSibling(Paths.get(file.toString().replace("IMG-", "")));
                try {
                    Files.move(file, validSibling);
                    return new SortingResult.SuccessfulSortingResult();
                } catch (IOException e) {
                    return new SortingResult.FailedSortingResult(Set.of(e.getMessage()));
                }
            }).reduce(new SortingResult.SuccessfulSortingResult(), successOrFailedResultWithAllMessages());
        } catch (Exception e) {
            return new SortingResult.FailedSortingResult(Set.of(e.getMessage()));
        }
    }

    private static Set<String> collectErrorMessages(SortingResult a, SortingResult b) {
        return Stream.of(a.errorReasons(), b.errorReasons()).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private SortingResult copyFiles(Path source, Path target, Sorter sorter) {
        try {
            if (!Files.exists(target.resolve(sorter.newFilePath()))) {
                Files.copy(source, Files.newOutputStream(target.resolve(sorter.newFilePath()), StandardOpenOption.CREATE_NEW));
               LOGGER.info("created file " + sorter.newJpegName());
            }
            return new SortingResult.SuccessfulSortingResult();
        } catch (IOException e) {
            LOGGER.error("Unable to move file {} to {}", source, target.resolve(sorter.newFilePath()), e);
            return new SortingResult.FailedSortingResult(Set.of(e.getMessage()));
        }
    }


    public static final class Sorter {
        private final String day;
        private final String month;
        private final String year;

        private final String hours;
        private final String minutes;
        private final String seconds;

        public Sorter(String year, String month, String day, String hours, String minutes, String seconds) {
            this.day = day;
            this.month = month;
            this.year = year;
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        static Sorter fromNameString(String nameString) {
            var year = nameString.substring(0, 4);
            var month = nameString.substring(4, 6);
            var day = nameString.substring(6, 8);
            var time = nameString.substring(nameString.indexOf("_") + 1);
            var hours = time.substring(0, 2);
            var minutes = time.substring(2, 4);
            var seconds = time.substring(4, 6);
            return new Sorter(year, month, day, hours, minutes, seconds);
        }

        private String newJpegName() {
            return "%s_%s%s%s".formatted(
                    day, hours, minutes, seconds
            );
        }

        public Path newFilePath() {
            return Paths.get(year).resolve(Paths.get(month)).resolve(Paths.get(newJpegName()));
        }

        public SortingResult createYearAndMonthDirectories(Path basePath) {
            var yearTo = basePath.resolve(Paths.get(year));
            var monthTo = yearTo.resolve(Paths.get(month));
            LOGGER.info("creating year %s and month %s".formatted(yearTo, monthTo));

            var createdYear =  createDir(yearTo);
            if (!createdYear.successful()) {
                return createdYear;
            }
            return createDir(monthTo);
        }

        public SortingResult createVideoDirectory(Path basePath) {
            return createDir(basePath.resolve("videos"));
        }

        private SortingResult createDir(Path yearTo) {
            try {
                if (!Files.exists(yearTo)) {
                    Files.createDirectories(yearTo);
                }
                return new SortingResult.SuccessfulSortingResult();
            } catch (Exception e) {
                return new SortingResult.FailedSortingResult(Set.of(e.getMessage()));
            }
        }
    }
}
