package io.hogenboom.familyfoto.service.image.file;

import io.hogenboom.familyfoto.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageFileService {
    private final String imageBasePath;
    public ImageFileService(@Value("${image.basepath}") String imageBasePath) {
        this.imageBasePath = imageBasePath;

    }

    public Path getFileByImage(Image image) {
        return Paths.get(imageBasePath).resolve(image.path());
    }

}
