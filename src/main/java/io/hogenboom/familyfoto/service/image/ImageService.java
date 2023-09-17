package io.hogenboom.familyfoto.service.image;

import io.hogenboom.familyfoto.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    ImageRepository repository;

}
