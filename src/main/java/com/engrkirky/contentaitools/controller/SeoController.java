package com.engrkirky.contentaitools.controller;

import com.engrkirky.contentaitools.dto.SeoParams;
import com.engrkirky.contentaitools.dto.SeoResponse;
import org.springframework.ai.chat.client.ChatClient;
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
    private final ChatClient chatClient;

    @Autowired
    public SeoController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping()
    public ResponseEntity<SeoResponse> getSeoCompliance(@RequestBody SeoParams seoParams) {
        String prompt = String.format("Is this text good for SEO or SEO Compliant? Reply only true or false, no other explanation: %s", seoParams.text());
        String result = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return new ResponseEntity<>(new SeoResponse(seoParams.text(), result.toLowerCase().equals("true")), HttpStatus.OK);
    }
}
