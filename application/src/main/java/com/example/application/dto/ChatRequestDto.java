package com.example.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 聊天请求DTO
 * 
 * @author Gemini
 * @since 1.0.0
 */
public class ChatRequestDto {

    /**
     * 会话ID，用于维护上下文
     */
    private String sessionId;

    /**
     * 用户输入的消息
     */
    @NotBlank(message = "消息内容不能为空")
    @Size(max = 1000, message = "消息内容不能超过1000个字符")
    private String message;

    /**
     * 是否开启上下文模式，默认为true
     */
    private Boolean enableContext = true;

    public ChatRequestDto() {
    }

    public ChatRequestDto(String sessionId, String message, Boolean enableContext) {
        this.sessionId = sessionId;
        this.message = message;
        this.enableContext = enableContext;
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

    public Boolean getEnableContext() {
        return enableContext;
    }

    public void setEnableContext(Boolean enableContext) {
        this.enableContext = enableContext;
    }

    @Override
    public String toString() {
        return "ChatRequestDto{" +
                "sessionId='" + sessionId + '\'' +
                ", message='" + message + '\'' +
                ", enableContext=" + enableContext +
                '}';
    }
}
