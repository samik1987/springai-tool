package com.example.springai_tool.configs;

import com.example.springai_tool.services.ChatService;
import com.example.springai_tool.tools.HelperTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class ClientConfig {

    public ClientConfig() {
    }

    @Bean(name= "OpenAiChatClient")
    public ChatClient openAiClient(OpenAiChatModel model , ChatMemory chatMemory , HelperTool helperTool) {

        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return ChatClient.builder(model)
                .defaultTools(helperTool)
                .defaultAdvisors(
                messageChatMemoryAdvisor,
                new SimpleLoggerAdvisor(),
                new SafeGuardAdvisor(List.of("sex" ,"porn"))
        ).build();
    }

    @Bean
    public RestClient getRestClient()
    {
        return RestClient.builder()
                .baseUrl("http://api.weatherapi.com/v1")
                .build();
    }

}
