package com.example.application.service;

import com.example.application.dto.ChatRequestDto;
import com.example.application.dto.ChatResponseDto;
import com.example.domain.exception.BusinessException;
import com.example.domain.client.ExternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天应用服务
 * 提供带上下文记忆的AI聊天功能
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Service
public class ChatApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ChatApplicationService.class);

    @Autowired
    private ChatClient statelessChatClient;

    @Autowired
    @Qualifier("contextualChatClient")
    private ChatClient contextualChatClient;

    @Autowired
    private ExternalService externalService;

    /**
     * 内存中的聊天会话存储，生产环境建议使用Redis等持久化存储
     */
    private final ConcurrentHashMap<String, java.util.List<String>> chatSessions = new ConcurrentHashMap<>();

    /**
     * 处理聊天请求
     *
     * @param request 聊天请求
     * @return 聊天响应
     */
    public ChatResponseDto chat(ChatRequestDto request) {
        try {
            logger.debug("处理聊天请求: {}", request);

            // 如果没有sessionId，生成一个新的
            String sessionId = request.getSessionId();
            if (!StringUtils.hasText(sessionId)) {
                sessionId = generateSessionId();
            }

            String response;
            boolean contextUsed = false;

            // 根据是否启用上下文选择不同的处理方式
            if (Boolean.TRUE.equals(request.getEnableContext())) {
                response = chatWithContext(sessionId, request.getMessage());
                contextUsed = true;
            } else {
                response = chatWithoutContext(request.getMessage());
            }

            logger.debug("AI响应: {}", response);

            return new ChatResponseDto(sessionId, response, contextUsed);

        } catch (Exception e) {
            logger.error("聊天处理失败", e);
            throw BusinessException.threadError("AI聊天服务调用失败: " + e.getMessage(), e);
        }
    }

    /**
     * 带上下文的聊天
     *
     * @param sessionId 会话ID
     * @param message   用户消息
     * @return AI回复
     */
    private String chatWithContext(String sessionId, String message) {
        // 获取或创建该会话的历史记录
        java.util.List<String> sessionHistory = chatSessions.computeIfAbsent(sessionId, 
            k -> new java.util.ArrayList<>());

        // 构建包含历史上下文的消息
        StringBuilder contextMessage = new StringBuilder();
        if (!sessionHistory.isEmpty()) {
            contextMessage.append("以下是之前的对话历史：\n");
            for (int i = Math.max(0, sessionHistory.size() - 10); i < sessionHistory.size(); i++) {
                contextMessage.append(sessionHistory.get(i)).append("\n");
            }
            contextMessage.append("当前用户问题：");
        }
        contextMessage.append(message);

        // 使用带记忆的聊天客户端
        String response = contextualChatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param("chat_memory_conversation_id", sessionId))
                .user(contextMessage.toString())
                .call()
                .content();

        // 保存对话历史
        sessionHistory.add("用户: " + message);
        sessionHistory.add("助手: " + response);

        return response;
    }

    /**
     * 无上下文的聊天
     *
     * @param message 用户消息
     * @return AI回复
     */
    private String chatWithoutContext(String message) {
        return statelessChatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    /**
     * 清除会话上下文
     *
     * @param sessionId 会话ID
     */
    public void clearContext(String sessionId) {
        if (StringUtils.hasText(sessionId)) {
            chatSessions.remove(sessionId);
            logger.debug("已清除会话{}的上下文", sessionId);
        }
    }

    /**
     * 获取会话信息
     *
     * @param sessionId 会话ID
     * @return 会话中的消息数量
     */
    public int getContextSize(String sessionId) {
        java.util.List<String> sessionHistory = chatSessions.get(sessionId);
        return sessionHistory != null ? sessionHistory.size() : 0;
    }

    /**
     * 生成新的会话ID
     *
     * @return 会话ID
     */
    private String generateSessionId() {
        return "chat_" + UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Example method demonstrating the use of the abstracted external service.
     *
     * @param id The id to query.
     * @return The result from the external service.
     */
    public String getInfoFromExternalService(String id) {
        logger.debug("调用外部服务，ID: {}", id);
        return externalService.getExternalInfo(id);
    }
}
