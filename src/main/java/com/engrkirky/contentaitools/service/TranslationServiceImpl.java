package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.Translation;
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
public class TranslationServiceImpl implements TranslationService {
    @Value("classpath:/prompts/translation.st")
    private Resource translationResource;
    private final ChatClient chatClient;

    @Autowired
    public TranslationServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public Translation translate(Translation translation) {
        PromptTemplate promptTemplate = new PromptTemplate(translationResource);
        Map<String, Object> promptParams = new HashMap<>();

        promptParams.put("language", translation.language());
        promptParams.put("text", translation.text());

        Prompt prompt = promptTemplate.create(promptParams);
        String content = chatClient.prompt()
                .user(prompt.toString())
                .call()
                .content();

        return new Translation(translation.language(), content);
    }
}
