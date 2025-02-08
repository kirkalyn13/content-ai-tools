package com.engrkirky.contentaitools.controller;

import com.engrkirky.contentaitools.dto.SeoRecommendation;
import com.engrkirky.contentaitools.service.SeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seo")
public class SeoController {
    private final SeoService seoService;

    @Autowired
    public SeoController(SeoService seoService) {
        this.seoService = seoService;
    }

    @GetMapping()
    public ResponseEntity<SeoRecommendation> getSeoRecommendations(@RequestBody String topic) {
        SeoRecommendation result = seoService.recommendSeoMetadata(topic);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
