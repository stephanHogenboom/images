package io.hogenboom.familyfoto.webcontroller.image;

import io.hogenboom.familyfoto.repository.ImageRepository;
import io.hogenboom.familyfoto.service.image.file.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
@RequestMapping("/view-images")
public class ViewImagesController {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    ImageFileService imageFileService;

    @GetMapping("")
    public ModelAndView viewImages() {
        var images = imageRepository.findAll();

        ModelAndView mav = new ModelAndView("image/view-images");
        mav.addObject("images", images);

        return mav;
    }

    @GetMapping("/{imageId}")
    public ModelAndView viewSpecificImage(@PathVariable("imageId") UUID imageId) {
        var image = imageRepository.findById(imageId)
                .orElseThrow();

        ModelAndView mav = new ModelAndView("image/view-specific-image");
        mav.addObject("image", image);

        return mav;
    }

}
