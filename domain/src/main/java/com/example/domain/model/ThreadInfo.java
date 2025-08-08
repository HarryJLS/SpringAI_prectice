package com.example.domain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 线程信息值对象
 * 在DDD中作为值对象，用于封装线程执行相关信息
 * 
 * @author Claude
 * @since 1.0.0
 */
public class ThreadInfo {
    
    /**
     * 线程索引
     */
    private final int threadIndex;
    
    /**
     * 线程名称
     */
    private final String threadName;
    
    /**
     * 执行时间
     */
    private final String executionTime;
    
    /**
     * 构造函数
     * 
     * @param threadIndex 线程索引
     * @param threadName 线程名称
     * @param executionTime 执行时间
     */
    public ThreadInfo(int threadIndex, String threadName, String executionTime) {
        this.threadIndex = threadIndex;
        this.threadName = Objects.requireNonNull(threadName, "线程名称不能为空");
        this.executionTime = Objects.requireNonNull(executionTime, "执行时间不能为空");
    }
    
    /**
     * 创建当前线程信息的静态工厂方法
     * 
     * @param threadIndex 线程索引
     * @param formatter 时间格式化器
     * @return ThreadInfo实例
     */
    public static ThreadInfo current(int threadIndex, DateTimeFormatter formatter) {
        return new ThreadInfo(
            threadIndex,
            Thread.currentThread().getName(),
            LocalDateTime.now().format(formatter)
        );
    }
    
    /**
     * 格式化线程信息
     * 
     * @param format 格式化模板
     * @return 格式化后的线程信息字符串
     */
    public String format(String format) {
        return String.format(format, threadIndex, threadName, executionTime);
    }
    
    /**
     * 获取线程索引
     * 
     * @return 线程索引
     */
    public int getThreadIndex() {
        return threadIndex;
    }
    
    /**
     * 获取线程名称
     * 
     * @return 线程名称
     */
    public String getThreadName() {
        return threadName;
    }
    
    /**
     * 获取执行时间
     * 
     * @return 执行时间
     */
    public String getExecutionTime() {
        return executionTime;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadInfo that = (ThreadInfo) o;
        return threadIndex == that.threadIndex &&
               Objects.equals(threadName, that.threadName) &&
               Objects.equals(executionTime, that.executionTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(threadIndex, threadName, executionTime);
    }
    
    @Override
    public String toString() {
        return String.format("ThreadInfo{threadIndex=%d, threadName='%s', executionTime='%s'}", 
                           threadIndex, threadName, executionTime);
    }
}
