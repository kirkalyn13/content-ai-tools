package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.SeoRecommendation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SeoServiceImpl implements SeoService {
    private final ChatClient chatClient;

    @Autowired
    public SeoServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public SeoRecommendation recommendSeoMetadata(String topic) {
        String promptMessage = """
                Suggest the SEO Title Tag, Meta Description, Abstract, and Target Keyword based on the following topic: {topic}
                {format}
                """;

        BeanOutputConverter<SeoRecommendation> outputParser = new BeanOutputConverter<>(SeoRecommendation.class);
        String format = outputParser.getFormat();

        Map<String, Object> promptParams = getPromptParams(topic, format);
        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, promptParams);

        Prompt prompt = promptTemplate.create();
        String content = chatClient.prompt()
                .user(prompt.toString())
                .call()
                .content();

        return outputParser.convert(content);
    }

    private static Map<String, Object> getPromptParams(String topic, String format) {
        Map<String, Object> promptParams = new HashMap<>();
        promptParams.put("topic", topic);
        promptParams.put("format", format);

        return promptParams;
    }
}
