package io.hogenboom.familyfoto.webcontroller;

import io.hogenboom.familyfoto.repository.ImageRepository;
import io.hogenboom.familyfoto.service.ImageFile.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
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
        var imageWrappers = new ArrayList<ImageUrlWrapper>();
        images.forEach(image -> imageWrappers.add(
                new ImageUrlWrapper(image)
        ));


        ModelAndView mav = new ModelAndView("view-images");
        mav.addObject("images", images);
        mav.addObject("imageWrappers", imageWrappers);

        return mav;
    }

    @GetMapping("/{imageId}")
    public ModelAndView viewSpecificImage(@PathVariable("imageId") UUID imageId) {
        var image = imageRepository.findById(imageId)
                .map(ImageUrlWrapper::new)
                .orElseThrow();

        ModelAndView mav = new ModelAndView("view-specific-image");
        mav.addObject("image", image);

        return mav;
    }

}
