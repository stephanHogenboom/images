package io.hogenboom.familyfoto.service.initialize;

import io.hogenboom.familyfoto.entity.Image;
import io.hogenboom.familyfoto.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdopterService {

    private static final Logger logger = LoggerFactory.getLogger(AdopterService.class);
    private final Path adoptionPath;
    private final ImageRepository repository;

    public AdopterService(@Value("${adoption.path}") String path,
                          ImageRepository imageRepository) {
        this.adoptionPath = Paths.get(path);
        this.repository = imageRepository;
    }


    public AdoptionResult adoptSortedImages() {
        try {
            logger.info("adoption path {}", adoptionPath);
           return Files.walk(adoptionPath)
                   .filter(Predicate.not(Files::isDirectory))
                   .map(this::toImageAndPersist)
                    .reduce(new AdoptionSuccessResult(), AdoptionResult::combine);


        } catch (Exception e) {
        logger.error("error while adopting: ", e);
        return new AdoptionFailureResult(Set.of(e.getMessage()));
        }

    }

    private AdoptionResult toImageAndPersist(Path file) {
       var image = new Image(
               UUID.randomUUID(),
               file.getFileName().toString(),
               adoptionPath.relativize(file).toString()
       );
    try {
        var day = Integer.parseInt(image.getName().substring(0,2));
        var month = Integer.parseInt(file.getParent().getFileName().toString());
        var year = Integer.parseInt(file.getParent().getParent().getFileName().toString());

        image.setDay(day);
        image.setMonth(month);
        image.setYear(year);

        logger.info("persisting image {}", image);
        repository.save(image);
    } catch (Exception e) {
        return new AdoptionFailureResult(Set.of(e.getMessage()));
    }
     return new AdoptionSuccessResult();
    }


    public sealed interface AdoptionResult {

        static AdoptionResult combine(AdoptionResult a, AdoptionResult b) {
            if (a instanceof AdoptionFailureResult || b instanceof AdoptionFailureResult) {
                return AdoptionFailureResult.ofMessages(a.errors(), b.errors());
            }
            return a;
        }
        default boolean succeeded() {
            return false;
        }

        default Set<String> errors() {
            return Set.of();
        }
    }

    public static final class AdoptionSuccessResult implements AdoptionResult {
        @Override
        public boolean succeeded() {
            return true;
        }
    }

    public record AdoptionFailureResult(Set<String> errors) implements AdoptionResult {
        public static AdoptionFailureResult ofMessages(Set<String> a, Set<String> b) {
            var messages = Stream.concat(a.stream(), b.stream())
                    .collect(Collectors.toSet());

            return new AdoptionFailureResult(messages);
        }
    }
}
