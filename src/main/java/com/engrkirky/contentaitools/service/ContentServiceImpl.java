package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.ContentParams;
import com.engrkirky.contentaitools.dto.GeneratedContent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Autowired
    public ContentServiceImpl(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
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
    public String generateFormattedContent(ContentParams contentParams) {
        String promptMessage = """
                Generate {contentType} rich text content in the same yaml format on the given documents about the topic: {topic},
                with a character lenght of {length}.
                Use the information from the DOCUMENTS section to provide accurate rich text format and answers.
                Only return the yaml format similar to the following format, nothing else:
                richText: <generated content here>
                
                DOCUMENTS:
                {documents}
                """;

        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(promptMessage).withTopK(2));
        List<String> contentList = similarDocuments.stream().map(Document::getContent).toList();

        Map<String, Object> promptParams = getPromptParams(contentParams, "");
        promptParams.put("documents", String.join("\n", contentList));

        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, promptParams);
        Prompt prompt = promptTemplate.create();

        return chatClient.prompt()
                .user(prompt.toString())
                .call()
                .content();
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
