package com.example.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 时间信息值对象
 * 在DDD中作为值对象，用于封装时间相关信息
 * 
 * @author Claude
 * @since 1.0.0
 */
public class TimeInfoEntity {
    
    /**
     * 格式化的当前时间
     */
    private final String currentTime;
    
    /**
     * 时间戳
     */
    private final Long timestamp;
    
    /**
     * 构造函数
     * 
     * @param currentTime 格式化的当前时间
     * @param timestamp 时间戳
     */
    public TimeInfoEntity(String currentTime, Long timestamp) {
        this.currentTime = Objects.requireNonNull(currentTime, "当前时间不能为空");
        this.timestamp = Objects.requireNonNull(timestamp, "时间戳不能为空");
    }
    
    /**
     * 创建当前时间信息的静态工厂方法
     * 
     * @param formatter 时间格式化器
     * @return TimeInfo实例
     */
    public static TimeInfoEntity now(java.time.format.DateTimeFormatter formatter) {
        LocalDateTime now = LocalDateTime.now();
        return new TimeInfoEntity(
            now.format(formatter),
            System.currentTimeMillis()
        );
    }
    
    /**
     * 获取格式化的当前时间
     * 
     * @return 格式化的当前时间字符串
     */
    public String getCurrentTime() {
        return currentTime;
    }
    
    /**
     * 获取时间戳
     * 
     * @return 时间戳
     */
    public Long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInfoEntity timeInfoEntity = (TimeInfoEntity) o;
        return Objects.equals(currentTime, timeInfoEntity.currentTime) &&
               Objects.equals(timestamp, timeInfoEntity.timestamp);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(currentTime, timestamp);
    }
    
    @Override
    public String toString() {
        return String.format("TimeInfo{currentTime='%s', timestamp=%d}", currentTime, timestamp);
    }
}
