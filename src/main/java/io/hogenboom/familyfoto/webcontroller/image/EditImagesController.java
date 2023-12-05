package io.hogenboom.familyfoto.webcontroller.image;

import io.hogenboom.familyfoto.mapper.entity.ImageMapper;
import io.hogenboom.familyfoto.repository.GroupRepository;
import io.hogenboom.familyfoto.repository.ImageRepository;
import io.hogenboom.familyfoto.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/edit-images")
public class EditImagesController {

    private static Logger LOGGER = LoggerFactory.getLogger(EditImagesController.class);
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GroupRepository groupRepository;


    @GetMapping("/{id}")
    public ModelAndView editImage(@PathVariable("id") UUID id) {
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

    @PostMapping("/{id}")
    public ModelAndView updateImage(@PathVariable("id") UUID id, @ModelAttribute("image")
    ImageView imageView) {

        var image = imageRepository.findById(id).orElseThrow();
        LOGGER.info(imageView.toString());
        image.setNickName(imageView.getNickName());

        var groups = groupRepository.findByIdIn(imageView.groupIds);
        var persons = personRepository.findByIdIn(imageView.personIds);

        image.setPersons(persons);
        image.setGroups(groups);

        imageRepository.save(image);

        //todo duplicate code
        var images = imageRepository.findAll();

        ModelAndView mav = new ModelAndView("image/view-images");
        mav.addObject("images", images);


        return mav;
    }
}
