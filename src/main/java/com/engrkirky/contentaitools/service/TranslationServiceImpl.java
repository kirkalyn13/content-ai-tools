package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.Translation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationServiceImpl implements TranslationService {
    private final ChatClient chatClient;

    @Autowired
    public TranslationServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public Translation translate(Translation translation) {
        String promptMessage = """
                You are a machine translator. Your task is to only translate a text to the specified language.
                Only return the translated text. Translate the following text to {language}: {text}
                {format}
                """;

        BeanOutputConverter<Translation> outputParser = new BeanOutputConverter<>(Translation.class);
        String format = outputParser.getFormat();

        Map<String, Object> promptParams = new HashMap<>();
        promptParams.put("language", translation.language());
        promptParams.put("text", translation.text());
        promptParams.put("format", format);

        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, promptParams);
        Prompt prompt = promptTemplate.create();
        String content = chatClient.prompt()
                .user(prompt.toString())
                .call()
                .content();

        return outputParser.convert(content);
    }
}
