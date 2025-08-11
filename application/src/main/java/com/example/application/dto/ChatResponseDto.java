package com.example.application.dto;

import java.time.LocalDateTime;

/**
 * 聊天响应DTO
 * 
 * @author Gemini
 * @since 1.0.0
 */
public class ChatResponseDto {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * AI回复的消息
     */
    private String message;

    /**
     * 消息创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 是否使用了上下文
     */
    private Boolean contextUsed;

    public ChatResponseDto() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatResponseDto(String sessionId, String message, Boolean contextUsed) {
        this.sessionId = sessionId;
        this.message = message;
        this.contextUsed = contextUsed;
        this.timestamp = LocalDateTime.now();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getContextUsed() {
        return contextUsed;
    }

    public void setContextUsed(Boolean contextUsed) {
        this.contextUsed = contextUsed;
    }

    @Override
    public String toString() {
        return "ChatResponseDto{" +
                "sessionId='" + sessionId + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", contextUsed=" + contextUsed +
                '}';
    }
}
