package com.example.springai_tool.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class ChatService {

    @Autowired
    @Qualifier("OpenAiChatClient")
    private ChatClient chatClient;


    @Autowired
    private RestClient restClient;

    public String getChat(String message)
    {

        PromptTemplate template = new PromptTemplate(
                new ClassPathResource("prompts/user-template.st")
        );

        String renderTemplate = template.render(Map.of(
                "role","Travel AI Agent",
                "audience-role","Tourist"
        ));


        return chatClient.prompt()
                .system(renderTemplate)
                .user(message)
                .call()
                .content();

    }


}
