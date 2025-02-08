package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.config.SummaryConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;

import java.util.function.Function;


public class SummarizeService implements Function<SummarizeService.Request, SummarizeService.Response> {
    private static final Logger log = LoggerFactory.getLogger(SummarizeService.class);
    private final RestClient restClient;
    private final SummaryConfigProperties props;

    @Autowired
    public SummarizeService(SummaryConfigProperties props) {
        this.props = props;
        log.debug("CMS URL: {}", this.props.url());
        this.restClient = RestClient.create(this.props.url());
    }
    @Override
    public SummarizeService.Response apply(SummarizeService.Request request) {
        log.info("Summary Request: {}", request);
        Response response = restClient.get()
                .uri("/{id}", request.id())
                .retrieve()
                .body(Response.class);
        log.info("Summary Response: {}", response);
        return response;
    }

    public record Request(int id) {}
    public record Response(Object content) {}
}
