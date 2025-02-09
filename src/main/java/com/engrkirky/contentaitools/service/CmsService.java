package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.config.CmsConfigProperties;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;

import java.util.function.Function;


public class CmsService implements Function<CmsService.Request, CmsService.Response> {
    private static final Logger log = LoggerFactory.getLogger(CmsService.class);
    private final RestClient restClient;
    private final CmsConfigProperties props;

    @Autowired
    public CmsService(CmsConfigProperties props) {
        this.props = props;
        log.debug("CMS URL: {}", this.props.url());
        this.restClient = RestClient.create(this.props.url());
    }
    @Override
    public CmsService.Response apply(CmsService.Request request) {
        log.info("CMS Summary Request: {}", request);

        JsonNode response = restClient.get()
                .uri("/{region}/{title}", request.region(), request.title())
                .headers(headers -> headers.setBasicAuth(this.props.username(), this.props.password()))
                .retrieve()
                .body(JsonNode.class);

        log.info("CMS Summary Response: {}", response);

        return new Response(response);
    }

    public record Request(String region, String title) {}
    public record Response(JsonNode content) {}
}
