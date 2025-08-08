package com.example.interfaces.dto;

/**
 * 时间信息数据传输对象
 * 用于接口层与外部系统交互的数据传输
 * 
 * @author Claude
 * @since 1.0.0
 */
public class TimeInfoDto {
    
    /**
     * 格式化的当前时间
     */
    private String currentTime;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    /**
     * 默认构造函数
     */
    public TimeInfoDto() {
    }
    
    /**
     * 构造函数
     * 
     * @param currentTime 格式化的当前时间
     * @param timestamp 时间戳
     */
    public TimeInfoDto(String currentTime, Long timestamp) {
        this.currentTime = currentTime;
        this.timestamp = timestamp;
    }
    

    
    public String getCurrentTime() {
        return currentTime;
    }
    
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
    
    public Long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return String.format("TimeInfoDto{currentTime='%s', timestamp=%d}", currentTime, timestamp);
    }
}
