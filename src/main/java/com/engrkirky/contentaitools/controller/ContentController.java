package com.engrkirky.contentaitools.controller;

import com.engrkirky.contentaitools.dto.*;
import com.engrkirky.contentaitools.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {
    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping()
    public ResponseEntity<GeneratedContent> getContent(@RequestBody ContentParams contentParams) {
        GeneratedContent result = contentService.generateContent(contentParams);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/formatted")
    public ResponseEntity<GeneratedContent> getContentFormatted(@RequestBody ContentParams contentParams) {
        GeneratedContent result = contentService.generateFormattedContent(contentParams);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
