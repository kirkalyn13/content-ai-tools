package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.ContentParams;
import com.engrkirky.contentaitools.dto.GeneratedContent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {
    @Value("classpath:/prompts/content.st")
    private Resource contentResource;
    private final ChatClient chatClient;

    @Autowired
    public ContentServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public GeneratedContent generateContent(ContentParams contentParams) {
        PromptTemplate promptTemplate = new PromptTemplate(contentResource);
        Map<String, Object> promptParams = new HashMap<>();

        promptParams.put("contentType", contentParams.contentType());
        promptParams.put("topic", contentParams.topic());
        promptParams.put("length", contentParams.length());

        Prompt prompt = promptTemplate.create(promptParams);
        String content = chatClient.prompt()
                .user(prompt.toString())
                .call()
                .content();

        return new GeneratedContent(contentParams.contentType(), content);
    }
}
