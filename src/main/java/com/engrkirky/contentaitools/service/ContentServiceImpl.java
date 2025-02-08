package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.ContentParams;
import com.engrkirky.contentaitools.dto.GeneratedContent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/sample-content.txt")
    private Resource sampleContent;

    @Autowired
    public ContentServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public GeneratedContent generateContent(ContentParams contentParams) {
        String promptMessage = """
                Generate {contentType} content in rich text format about {topic} with a length of {length} words. Only return the content.
                {format}
                """;

        BeanOutputConverter<GeneratedContent> outputParser = new BeanOutputConverter<>(GeneratedContent.class);
        String format = outputParser.getFormat();

        Map<String, Object> promptParams = getPromptParams(contentParams, format);
        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, promptParams);

        Prompt prompt = promptTemplate.create();
        String content = chatClient.prompt()
                .user(prompt.toString())
                .call()
                .content();

        return outputParser.convert(content);
    }

    @Override
    public GeneratedContent generateFormattedContent(ContentParams contentParams) {
        String promptMessage = """
                Generate {contentType} content in the same yaml format on the given context about {topic}.
                Use the following context as reference on how to format the returned content data.
                
                {context}
                
                {format}
                """;

        BeanOutputConverter<GeneratedContent> outputParser = new BeanOutputConverter<>(GeneratedContent.class);
        String format = outputParser.getFormat();

        Map<String, Object> promptParams = getPromptParams(contentParams, format);
        promptParams.put("context", sampleContent);
        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, promptParams);

        Prompt prompt = promptTemplate.create();
        String content = chatClient.prompt()
                .user(prompt.toString())
                .call()
                .content();

        return outputParser.convert(content);
    }

    private static Map<String, Object> getPromptParams(ContentParams contentParams, String format) {
        Map<String, Object> promptParams = new HashMap<>();
        promptParams.put("contentType", contentParams.contentType());
        promptParams.put("topic", contentParams.topic());
        promptParams.put("length", contentParams.length());
        promptParams.put("format", format);

        return promptParams;
    }

}
