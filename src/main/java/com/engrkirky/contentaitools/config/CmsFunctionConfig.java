package com.engrkirky.contentaitools.config;

import com.engrkirky.contentaitools.service.CmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class CmsFunctionConfig {
    private final CmsConfigProperties props;

    @Autowired
    public CmsFunctionConfig(CmsConfigProperties props) {
        this.props = props;
    }

    @Bean
    @Description("Parse the context of the content and get the summary based on the resulting json output.")
    public Function<CmsService.Request, CmsService.Response> summarizeArticleFunction() {
        return new CmsService(props);
    }
}
