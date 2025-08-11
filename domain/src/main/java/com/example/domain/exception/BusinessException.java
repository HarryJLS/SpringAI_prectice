package com.example.domain.exception;

/**
 * 业务异常类
 * 用于封装业务逻辑中的异常情况
 * 
 * @author Claude
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {
    
    /**
     * 错误码
     */
    private final int code;
    
    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误信息
     * @param cause 原因异常
     */
    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    /**
     * 获取错误码
     * 
     * @return 错误码
     */
    public int getCode() {
        return code;
    }
    
    /**
     * 创建参数错误异常
     * 
     * @param message 错误信息
     * @return 业务异常实例
     */
    public static BusinessException paramError(String message) {
        return new BusinessException(400, message);
    }
    
    /**
     * 创建线程执行异常
     * 
     * @param message 错误信息
     * @return 业务异常实例
     */
    public static BusinessException threadError(String message) {
        return new BusinessException(500, message);
    }
    
    /**
     * 创建线程执行异常
     * 
     * @param message 错误信息
     * @param cause 原因异常
     * @return 业务异常实例
     */
    public static BusinessException threadError(String message, Throwable cause) {
        return new BusinessException(500, message, cause);
    }
}