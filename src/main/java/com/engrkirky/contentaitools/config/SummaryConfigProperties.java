package com.engrkirky.contentaitools.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "summary")
public record SummaryConfigProperties(String url) {}
