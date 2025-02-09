package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.Summary;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SummaryServiceImpl implements SummaryService {
    private static final String DEFAULT_SYSTEM = "You are a helpful AI Assistant that summarizes a piece of content extracted from a JSON output.";
    private static final String DEFAULT_FUNCTION = "summarizeArticleFunction";
    private final ChatClient chatClient;

    @Autowired
    public SummaryServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem(DEFAULT_SYSTEM)
                .defaultFunctions(DEFAULT_FUNCTION)
                .build();
    }

    @Override
    public Summary summarizeArticle(String region, String title) {
        String content = chatClient.prompt()
                .user(String.valueOf(Map.of("region", region, "title", title)))
                .call()
                .content();

        return new Summary(content);
    }
}
