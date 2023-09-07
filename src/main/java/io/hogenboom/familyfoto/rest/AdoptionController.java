package io.hogenboom.familyfoto.rest;

import io.hogenboom.familyfoto.service.initialize.AdopterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adopt")
public class AdoptionController {
    private static final Logger logger = LoggerFactory.getLogger(AdoptionController.class);

    @Autowired
    AdopterService service;
    @PostMapping
    public void adopt() {
        var result = service.adoptSortedImages();
        result.errors().forEach(logger::warn);
    }
}
