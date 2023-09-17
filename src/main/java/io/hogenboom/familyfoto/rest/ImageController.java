package io.hogenboom.familyfoto.rest;

import io.hogenboom.familyfoto.entity.Image;
import io.hogenboom.familyfoto.repository.ImageRepository;
import io.hogenboom.familyfoto.repository.PersonRepository;
import io.hogenboom.familyfoto.service.image.file.ImageFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    ImageFileService imageFileService;

    @GetMapping("")
    public Page<Image> getImage(Pageable pageable) throws IOException {
        return imageRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public void getImageById(@PathVariable("id") UUID id, HttpServletResponse response) {
        var image =  imageRepository.findById(id).orElseThrow();
        var imageFile = imageFileService.getFileByImage(image);
        try {
            response.getOutputStream().write(Files.readAllBytes(imageFile));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus(500);
        }
    }

    @PutMapping("/{id}/nickName/{nickName}")
    public void editNickName(@PathVariable("id") UUID id, @PathVariable("nickName") String nickName, HttpServletResponse response) {
        var image = imageRepository.findById(id)
                .orElseThrow();

        image.setNickName(nickName);

        imageRepository.save(image);
        response.setStatus(200);
    }

    @PatchMapping("/{id}/persons/{personId}")
    public void addUser(@PathVariable("id") UUID id, @PathVariable("personId") UUID personId) {
        var image = imageRepository.findById(id).orElseThrow();
        var person = personRepository.findById(personId).orElseThrow();

        image.getPersons().add(person);
        imageRepository.save(image);
    }


}
