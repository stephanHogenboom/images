package io.hogenboom.familyfoto.rest;

import io.hogenboom.familyfoto.service.sorting.SorterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sort")
public class SortingController {
    private static final Logger logger = LoggerFactory.getLogger(SortingController.class);

    @Autowired
    SorterService service;
    @PostMapping("")
    public void sort() {
        service.renameFiles().errorReasons().forEach(logger::warn);
        service.moveVideos().errorReasons().forEach(logger::warn);
        service.sortAllInUnsortedDirectoryAndCopyToTargetDirectory().errorReasons().forEach(logger::warn);
    }
}
