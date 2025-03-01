package com.engrkirky.contentaitools.controller;

import com.engrkirky.contentaitools.dto.Translation;
import com.engrkirky.contentaitools.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/translate")
public class TranslationController {
    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping()
    public ResponseEntity<Translation> getMachineTranslation(@RequestBody Translation translation) {
        Translation result = translationService.translate(translation);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
