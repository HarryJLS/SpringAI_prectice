package com.example.domain.service;

import com.example.domain.model.TimeInfo;
import com.example.domain.model.ThreadInfo;

/**
 * 时间服务接口
 * 
 * @author Claude
 * @since 1.0.0
 */
public interface TimeService {
    
    /**
     * 获取当前时间信息
     * 
     * @return 时间信息值对象
     */
    TimeInfo getCurrentTimeInfo();
    
    /**
     * 获取线程时间信息
     * 
     * @param threadIndex 线程索引
     * @return 线程信息值对象
     */
    ThreadInfo getThreadTimeInfo(int threadIndex);
    
    /**
     * 模拟工作负载
     * 根据线程索引睡眠不同时间，模拟不同的工作负载
     * 
     * @param threadIndex 线程索引，用于计算睡眠时间
     * @throws RuntimeException 当线程被中断时抛出
     */
    void simulateWork(int threadIndex);
}