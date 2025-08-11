package com.example.interfaces.exception;

import com.example.domain.exception.BusinessException;
import com.example.interfaces.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 全局异常处理器
 * 统一处理应用程序中的各种异常，返回标准的错误响应格式
 * 确保所有异常都能被正确捕获并返回统一的Result结构
 * 
 * @author Claude
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 处理业务异常
     * 
     * @param e 业务异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleBusinessException(BusinessException e, HttpServletRequest request) {
        logger.warn("业务异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage(), request.getRequestURI());
    }
    
    /**
     * 处理参数校验异常 - @Valid
     * 
     * @param e 方法参数校验异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.warn("参数校验异常 [{}]: {}", request.getRequestURI(), e.getMessage());
        StringBuilder errorMsg = new StringBuilder("参数校验失败: ");
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMsg.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()).append("; ");
        }
        return Result.badRequest(errorMsg.toString(), request.getRequestURI());
    }
    
    /**
     * 处理参数校验异常 - @Validated路径变量和请求参数
     * 
     * @param e 约束违反异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        logger.warn("约束违反异常 [{}]: {}", request.getRequestURI(), e.getMessage());
        StringBuilder errorMsg = new StringBuilder("参数校验失败: ");
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            errorMsg.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; ");
        }
        return Result.badRequest(errorMsg.toString(), request.getRequestURI());
    }
    
    /**
     * 处理绑定异常 - @ModelAttribute
     * 
     * @param e 绑定异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(BindException.class)
    public Result<Object> handleBindException(BindException e, HttpServletRequest request) {
        logger.warn("绑定异常 [{}]: {}", request.getRequestURI(), e.getMessage());
        StringBuilder errorMsg = new StringBuilder("参数绑定失败: ");
        for (FieldError fieldError : e.getFieldErrors()) {
            errorMsg.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()).append("; ");
        }
        return Result.badRequest(errorMsg.toString(), request.getRequestURI());
    }
    
    /**
     * 处理缺少请求参数异常
     * 
     * @param e 缺少参数异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        logger.warn("缺少请求参数异常 [{}]: {}", request.getRequestURI(), e.getMessage());
        return Result.badRequest("缺少必要参数: " + e.getParameterName(), request.getRequestURI());
    }
    
    /**
     * 处理参数类型转换异常
     * 
     * @param e 参数类型不匹配异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        logger.warn("参数类型转换异常 [{}]: {}", request.getRequestURI(), e.getMessage());
        return Result.badRequest(String.format("参数类型错误: %s，期望类型: %s", 
                        e.getName(), e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "unknown"), 
                        request.getRequestURI());
    }
    
    /**
     * 处理HTTP请求方法不支持异常
     * 
     * @param e 请求方法不支持异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        logger.warn("不支持的请求方法 [{}]: {}", request.getRequestURI(), e.getMessage());
        return Result.methodNotAllowed(String.format("不支持的请求方法: %s，支持的方法: %s", 
                        e.getMethod(), e.getSupportedMethods() != null ? String.join(", ", e.getSupportedMethods()) : "none"), 
                        request.getRequestURI());
    }
    
    /**
     * 处理404异常
     * 
     * @param e 找不到处理器异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        logger.warn("找不到处理器 [{}]: {}", request.getRequestURI(), e.getMessage());
        return Result.notFound("接口不存在", request.getRequestURI());
    }
    
    /**
     * 处理HTTP消息不可读异常（JSON格式错误等）
     * 
     * @param e 消息不可读异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        logger.warn("HTTP消息不可读异常 [{}]: {}", request.getRequestURI(), e.getMessage());
        return Result.badRequest("请求体格式错误，请检查JSON格式", request.getRequestURI());
    }
    
    /**
     * 处理文件上传大小超限异常
     * 
     * @param e 文件大小超限异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        logger.warn("文件上传大小超限 [{}]: {}", request.getRequestURI(), e.getMessage());
        return Result.badRequest("上传文件大小超出限制", request.getRequestURI());
    }
    
    /**
     * 处理线程中断异常
     * 
     * @param e 中断异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(InterruptedException.class)
    public Result<Object> handleInterruptedException(InterruptedException e, HttpServletRequest request) {
        logger.error("线程中断异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        Thread.currentThread().interrupt(); // 恢复中断状态
        return Result.error(Result.INTERNAL_SERVER_ERROR, "任务执行被中断", request.getRequestURI());
    }
    
    /**
     * 处理异步任务执行异常
     * 
     * @param e 执行异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(ExecutionException.class)
    public Result<Object> handleExecutionException(ExecutionException e, HttpServletRequest request) {
        logger.error("异步任务执行异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        Throwable cause = e.getCause();
        if (cause instanceof BusinessException) {
            return handleBusinessException((BusinessException) cause, request);
        }
        return Result.error(Result.INTERNAL_SERVER_ERROR, "异步任务执行失败", request.getRequestURI());
    }
    
    /**
     * 处理异步任务完成异常
     * 
     * @param e 完成异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(CompletionException.class)
    public Result<Object> handleCompletionException(CompletionException e, HttpServletRequest request) {
        logger.error("异步任务完成异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        Throwable cause = e.getCause();
        if (cause instanceof BusinessException) {
            return handleBusinessException((BusinessException) cause, request);
        }
        return Result.error(Result.INTERNAL_SERVER_ERROR, "异步任务完成失败", request.getRequestURI());
    }
    
    /**
     * 处理超时异常
     * 
     * @param e 超时异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(TimeoutException.class)
    public Result<Object> handleTimeoutException(TimeoutException e, HttpServletRequest request) {
        logger.error("任务超时异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.timeout("请求超时，请稍后重试", request.getRequestURI());
    }
    
    /**
     * 处理参数异常
     * 
     * @param e 参数异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Object> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        logger.warn("参数异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.badRequest("参数错误: " + e.getMessage(), request.getRequestURI());
    }
    
    /**
     * 处理空指针异常
     * 
     * @param e 空指针异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<Object> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        logger.error("空指针异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(Result.INTERNAL_SERVER_ERROR, "系统内部错误，请联系管理员", request.getRequestURI());
    }
    
    /**
     * 处理运行时异常
     * 
     * @param e 运行时异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<Object> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        logger.error("运行时异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(Result.INTERNAL_SERVER_ERROR, "系统内部错误", request.getRequestURI());
    }
    
    /**
     * 处理所有未捕获的异常
     * 
     * @param e 异常
     * @param request HTTP请求
     * @return 错误响应结果
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e, HttpServletRequest request) {
        logger.error("未知异常 [{}]: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(Result.INTERNAL_SERVER_ERROR, "系统异常，请联系管理员", request.getRequestURI());
    }
}