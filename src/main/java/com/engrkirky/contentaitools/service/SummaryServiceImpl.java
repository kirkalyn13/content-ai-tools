package com.engrkirky.contentaitools.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryServiceImpl implements SummaryService {
    private static final String DEFAULT_SYSTEM = "You are a helpful AI Assistant that summarizes a piece of text extracted from the body field of the JSON output.";
    private static final String DEFAULT_FUNCTION = "summarizeFunction";
    private final ChatClient chatClient;

    @Autowired
    public SummaryServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem(DEFAULT_SYSTEM)
                .defaultFunctions(DEFAULT_FUNCTION)
                .build();
    }

    @Override
    public String summarize(int id) {
        return chatClient.prompt()
                .user(String.valueOf(id))
                .call()
                .content();
    }
}
