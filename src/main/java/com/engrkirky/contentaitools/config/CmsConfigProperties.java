package com.engrkirky.contentaitools.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "cms")
public record CmsConfigProperties(String url, String username, String password) {}
