package com.example.interfaces.controller;

import com.example.application.dto.ChatRequestDto;
import com.example.application.dto.ChatResponseDto;
import com.example.application.service.ChatApplicationService;
import com.example.domain.exception.BusinessException;
import com.example.interfaces.common.Result;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * AI 相关接口控制器
 * 提供与 Spring AI 模型交互的REST接口
 * 
 * @author Gemini
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/ai")
@Validated
public class AiController {

    @Autowired
    private ChatClient statelessChatClient;

    @Autowired
    private ChatApplicationService chatApplicationService;


    /**
     * 与AI模型进行无状态聊天的接口
     * 该接口不保存任何上下文信息，每次请求都是独立的
     *
     * @param request 聊天请求，sessionId和enableContext字段将被忽略
     * @return 统一响应结果，包含AI的回复内容
     */
    @PostMapping("/chat")
    public Result<ChatResponseDto> chat(@Valid @RequestBody ChatRequestDto request) {
        try {
            String response = statelessChatClient.prompt()
                    .user(request.getMessage())
                    .call()
                    .content();
            
            // 创建响应DTO，标记为无上下文
            ChatResponseDto chatResponse = new ChatResponseDto(
                null, // 无状态聊天不返回sessionId
                response,
                false // 未使用上下文
            );
            
            return Result.success(chatResponse);
        } catch (Exception e) {
            throw BusinessException.threadError("AI 服务调用失败", e);
        }
    }

    /**
     * 与AI模型进行上下文聊天的接口
     * 支持会话级别的上下文记忆功能
     *
     * @param request 聊天请求，包含消息内容、会话ID和上下文设置
     * @return 统一响应结果，包含AI的回复内容和会话信息
     */
    @PostMapping("/chat/context")
    public Result<ChatResponseDto> chatWithContext(@Valid @RequestBody ChatRequestDto request) {
        ChatResponseDto response = chatApplicationService.chat(request);
        return Result.success(response);
    }

    /**
     * 清除指定会话的上下文记忆
     *
     * @param sessionId 会话ID
     * @return 操作结果
     */
    @DeleteMapping("/chat/context/{sessionId}")
    public Result<String> clearContext(@PathVariable String sessionId) {
        chatApplicationService.clearContext(sessionId);
        return Result.success("会话上下文已清除");
    }

    /**
     * 获取指定会话的上下文信息
     *
     * @param sessionId 会话ID
     * @return 会话中的消息数量
     */
    @GetMapping("/chat/context/{sessionId}/info")
    public Result<Integer> getContextInfo(@PathVariable String sessionId) {
        int contextSize = chatApplicationService.getContextSize(sessionId);
        return Result.success(contextSize);
    }

}