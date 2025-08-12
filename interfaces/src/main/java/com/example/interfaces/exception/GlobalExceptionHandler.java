package com.example.interfaces.exception;

import com.common.exception.BaseGlobalExceptionHandler;
import com.example.domain.exception.BusinessException;
import com.common.response.JlsResponse;
import com.common.enums.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

/**
 * 全局异常处理器
 * 继承 BaseGlobalExceptionHandler，专门处理业务特有的异常
 * 
 * @author Claude
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 处理业务异常
     * 
     * @param e 业务异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(BusinessException.class)
    public JlsResponse<Object> handleBusinessException(BusinessException e, HttpServletRequest request) {
        logger.warn("业务异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        return JlsResponse.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 处理异步任务执行异常，特别处理包含业务异常的情况
     * 
     * @param e 执行异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(ExecutionException.class)
    public JlsResponse<Object> handleExecutionException(ExecutionException e, HttpServletRequest request) {
        logger.error("异步任务执行异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        Throwable cause = e.getCause();
        if (cause instanceof BusinessException) {
            return handleBusinessException((BusinessException) cause, request);
        }
        return JlsResponse.error(ResultCode.INTERNAL_SERVER_ERROR, "异步任务执行失败");
    }
    
    /**
     * 处理异步任务完成异常，特别处理包含业务异常的情况
     * 
     * @param e 完成异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(CompletionException.class)
    public JlsResponse<Object> handleCompletionException(CompletionException e, HttpServletRequest request) {
        logger.error("异步任务完成异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        Throwable cause = e.getCause();
        if (cause instanceof BusinessException) {
            return handleBusinessException((BusinessException) cause, request);
        }
        return JlsResponse.error(ResultCode.INTERNAL_SERVER_ERROR, "异步任务完成失败");
    }
}