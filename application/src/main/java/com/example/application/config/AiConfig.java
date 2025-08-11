package com.example.application.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Spring AI 相关配置
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Configuration
public class AiConfig {

    /**
     * 创建并配置一个无状态的 ChatClient Bean
     * 
     * @param builder 自动注入的 ChatClient.Builder
     * @return 配置好的无状态 ChatClient Bean
     */
    @Bean
    @Primary
    public ChatClient statelessChatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    /**
     * 创建并配置一个有状态的 ChatClient Bean，支持上下文记忆
     * 
     * @param builder 自动注入的 ChatClient.Builder
     * @return 配置好的有状态 ChatClient Bean
     */
    @Bean
    @Qualifier("contextualChatClient") 
    public ChatClient contextualChatClient(ChatClient.Builder builder) {
        return builder.build();
    }

}
