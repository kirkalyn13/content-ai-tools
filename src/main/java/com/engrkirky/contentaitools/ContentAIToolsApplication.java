package com.engrkirky.contentaitools;

import com.engrkirky.contentaitools.config.CmsConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(CmsConfigProperties.class)
@SpringBootApplication
public class ContentAIToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentAIToolsApplication.class, args);
	}

}
