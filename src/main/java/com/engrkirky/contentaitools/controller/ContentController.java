package com.engrkirky.contentaitools.controller;

import com.engrkirky.contentaitools.dto.ContentParams;
import com.engrkirky.contentaitools.dto.GeneratedContent;
import com.engrkirky.contentaitools.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping()
    public ResponseEntity<GeneratedContent> getContent(@RequestBody ContentParams contentParams) {
        GeneratedContent result = contentService.generateContent(contentParams);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/formatted")
    public ResponseEntity<String> getContentFormatted(@RequestBody ContentParams contentParams) {
        String result = contentService.generateFormattedContent(contentParams);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
