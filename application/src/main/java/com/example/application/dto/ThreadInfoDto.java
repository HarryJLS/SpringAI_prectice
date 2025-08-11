package com.example.application.dto;

/**
 * 线程信息数据传输对象
 * 用于接口层与外部系统交互的数据传输
 * 
 * @author Claude
 * @since 1.0.0
 */
public class ThreadInfoDto {
    
    /**
     * 线程索引
     */
    private Integer threadIndex;
    
    /**
     * 线程名称
     */
    private String threadName;
    
    /**
     * 执行时间
     */
    private String executionTime;
    
    /**
     * 默认构造函数
     */
    public ThreadInfoDto() {
    }
    
    /**
     * 构造函数
     * 
     * @param threadIndex 线程索引
     * @param threadName 线程名称
     * @param executionTime 执行时间
     */
    public ThreadInfoDto(Integer threadIndex, String threadName, String executionTime) {
        this.threadIndex = threadIndex;
        this.threadName = threadName;
        this.executionTime = executionTime;
    }
    

    
    public Integer getThreadIndex() {
        return threadIndex;
    }
    
    public void setThreadIndex(Integer threadIndex) {
        this.threadIndex = threadIndex;
    }
    
    public String getThreadName() {
        return threadName;
    }
    
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
    
    public String getExecutionTime() {
        return executionTime;
    }
    
    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }
    
    @Override
    public String toString() {
        return String.format("ThreadInfoDto{threadIndex=%d, threadName='%s', executionTime='%s'}", 
                           threadIndex, threadName, executionTime);
    }
}
