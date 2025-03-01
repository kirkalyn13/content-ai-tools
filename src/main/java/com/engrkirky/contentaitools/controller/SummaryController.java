package com.engrkirky.contentaitools.controller;

import com.engrkirky.contentaitools.dto.Summary;
import com.engrkirky.contentaitools.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/summary")
public class SummaryController {
    private final SummaryService summaryService;

    @Autowired
    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping("/{region}/{title}")
    public ResponseEntity<Summary> getSummary(@PathVariable("region") String region, @PathVariable("title") String title) {
        Summary result = summaryService.summarizeArticle(region, title);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
