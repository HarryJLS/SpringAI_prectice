package com.example.interfaces.common;

import java.io.Serializable;

/**
 * 统一返回结果类
 * 所有API接口都使用此类作为返回值，包括异常情况
 * 
 * @param <T> 数据类型
 * @author Claude
 * @since 1.0.0
 */
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 状态码常量
     */
    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
    
    private int code;
    private String message;
    private T data;
    private long timestamp;
    private String path;
    
    public Result() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public Result(int code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public Result(int code, String message, T data, String path) {
        this(code, message, data);
        this.path = path;
    }
    
    /**
     * 成功返回，带数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS, "操作成功", data);
    }
    
    /**
     * 成功返回，无数据
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS, "操作成功", null);
    }
    
    /**
     * 成功返回，自定义消息
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(SUCCESS, message, data);
    }
    
    /**
     * 错误返回，指定状态码和消息
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 错误返回，指定状态码、消息和路径
     */
    public static <T> Result<T> error(int code, String message, String path) {
        return new Result<>(code, message, null, path);
    }
    
    /**
     * 内部服务器错误
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(INTERNAL_SERVER_ERROR, message, null);
    }
    
    /**
     * 参数错误
     */
    public static <T> Result<T> badRequest(String message) {
        return new Result<>(BAD_REQUEST, message, null);
    }
    
    /**
     * 参数错误，带路径
     */
    public static <T> Result<T> badRequest(String message, String path) {
        return new Result<>(BAD_REQUEST, message, null, path);
    }
    
    /**
     * 未授权
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(UNAUTHORIZED, message, null);
    }
    
    /**
     * 禁止访问
     */
    public static <T> Result<T> forbidden(String message) {
        return new Result<>(FORBIDDEN, message, null);
    }
    
    /**
     * 资源未找到
     */
    public static <T> Result<T> notFound(String message) {
        return new Result<>(NOT_FOUND, message, null);
    }
    
    /**
     * 资源未找到，带路径
     */
    public static <T> Result<T> notFound(String message, String path) {
        return new Result<>(NOT_FOUND, message, null, path);
    }
    
    /**
     * 方法不允许
     */
    public static <T> Result<T> methodNotAllowed(String message) {
        return new Result<>(METHOD_NOT_ALLOWED, message, null);
    }
    
    /**
     * 方法不允许，带路径
     */
    public static <T> Result<T> methodNotAllowed(String message, String path) {
        return new Result<>(METHOD_NOT_ALLOWED, message, null, path);
    }
    
    /**
     * 请求超时
     */
    public static <T> Result<T> timeout(String message) {
        return new Result<>(REQUEST_TIMEOUT, message, null);
    }
    
    /**
     * 请求超时，带路径
     */
    public static <T> Result<T> timeout(String message, String path) {
        return new Result<>(REQUEST_TIMEOUT, message, null, path);
    }
    
    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this.code == SUCCESS;
    }
    
    /**
     * 判断是否失败
     */
    public boolean isError() {
        return this.code != SUCCESS;
    }
    
    // Getters and Setters
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return String.format("Result{code=%d, message='%s', data=%s, timestamp=%d, path='%s'}", 
                           code, message, data, timestamp, path);
    }
}