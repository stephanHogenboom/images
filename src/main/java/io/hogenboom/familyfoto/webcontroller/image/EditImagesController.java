package io.hogenboom.familyfoto.webcontroller.image;

import io.hogenboom.familyfoto.mapper.entity.ImageMapper;
import io.hogenboom.familyfoto.repository.GroupRepository;
import io.hogenboom.familyfoto.repository.ImageRepository;
import io.hogenboom.familyfoto.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/edit-images")
public class EditImagesController {
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GroupRepository groupRepository;


    @GetMapping("/{id}")
    public ModelAndView getImage(@PathVariable("id")UUID id) {
        var image = imageRepository.findById(id).orElseThrow();
        var groups = groupRepository.findAll(Pageable.ofSize(100)).stream().toList();
        var persons = personRepository.findAll(Pageable.ofSize(100)).stream().toList();

        var imageView = ImageMapper.toImageview(image);


        var mav = new ModelAndView("image/edit-image");

        mav.addObject("image", imageView);
        mav.addObject("persons", persons);
        mav.addObject("groups", groups);

        return mav;
    }
}
