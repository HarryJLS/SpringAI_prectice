package com.example.domain.constant;

import java.time.format.DateTimeFormatter;

/**
 * 时间相关常量类
 * 定义时间格式化器和时间相关的常量值
 * 
 * @author Claude
 * @since 1.0.0
 */
public class TimeConstants {
    
    /**
     * 私有构造函数防止实例化
     */
    private TimeConstants() {
        // 私有构造函数防止实例化
    }
    
    /**
     * 标准时间格式化器
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 精确时间格式化器
     * 格式：yyyy-MM-dd HH:mm:ss.SSS（包含毫秒）
     */
    public static final DateTimeFormatter PRECISE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    /**
     * 基础睡眠时间（毫秒）
     * 线程模拟工作时的基础等待时间
     */
    public static final long BASE_SLEEP_TIME = 100L;
    
    /**
     * 线程睡眠时间倍数（毫秒）
     * 用于计算不同线程的额外睡眠时间：threadIndex * THREAD_SLEEP_MULTIPLIER
     */
    public static final long THREAD_SLEEP_MULTIPLIER = 50L;
    
    /**
     * 线程信息格式化字符串
     * 格式：Thread-{索引}: {线程名} at {时间}
     */
    public static final String THREAD_INFO_FORMAT = "Thread-%d: %s at %s";
}