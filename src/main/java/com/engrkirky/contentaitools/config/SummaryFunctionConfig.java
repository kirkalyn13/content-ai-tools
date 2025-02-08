package com.engrkirky.contentaitools.config;

import com.engrkirky.contentaitools.service.SummarizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class SummaryFunctionConfig {
    private final SummaryConfigProperties props;

    @Autowired
    public SummaryFunctionConfig(SummaryConfigProperties props) {
        this.props = props;
    }

    @Bean
    @Description("Parse the content and get the summary of a text based on the body field of the resulting json output.")
    public Function<SummarizeService.Request, SummarizeService.Response> summarizeFunction() {
        return new SummarizeService(props);
    }
}
