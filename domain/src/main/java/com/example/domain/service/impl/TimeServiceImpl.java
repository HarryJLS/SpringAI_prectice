package com.example.domain.service.impl;

import com.example.domain.constant.TimeConstants;
import com.example.domain.model.TimeInfoEntity;
import com.example.domain.model.ThreadInfoEntity;
import com.example.domain.service.TimeService;
import org.springframework.stereotype.Service;

/**
 * 时间服务实现类
 * 提供时间相关的业务逻辑实现
 * 
 * @author Claude
 * @since 1.0.0
 */
@Service
public class TimeServiceImpl implements TimeService {
    
    /**
     * 获取当前时间信息
     * 
     * @return 时间信息值对象
     */
    @Override
    public TimeInfoEntity getCurrentTimeInfo() {
        return TimeInfoEntity.now(TimeConstants.TIME_FORMATTER);
    }
    
    /**
     * 获取线程时间信息
     * 
     * @param threadIndex 线程索引
     * @return 线程信息值对象
     */
    @Override
    public ThreadInfoEntity getThreadTimeInfo(int threadIndex) {
        return ThreadInfoEntity.current(threadIndex, TimeConstants.PRECISE_TIME_FORMATTER);
    }
    
    /**
     * 模拟工作负载
     * 根据线程索引计算睡眠时间：基础时间 + 线程索引 * 倍数
     * 
     * @param threadIndex 线程索引，用于计算睡眠时间
     * @throws RuntimeException 当线程被中断时抛出运行时异常
     */
    @Override
    public void simulateWork(int threadIndex) {
        try {
            Thread.sleep(TimeConstants.BASE_SLEEP_TIME + (threadIndex * TimeConstants.THREAD_SLEEP_MULTIPLIER));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted", e);
        }
    }
    
    /**
     * 执行任务并获取线程信息
     * 在当前线程中执行模拟工作，然后获取线程信息
     * 确保获取到正确的虚拟线程名称
     * 
     * @param threadIndex 线程索引
     * @return 线程信息值对象
     * @throws RuntimeException 当线程被中断时抛出运行时异常
     */
    @Override
    public ThreadInfoEntity executeTaskAndGetThreadInfo(int threadIndex) {
        // 先执行模拟工作
        simulateWork(threadIndex);
        // 然后在当前线程（虚拟线程）中获取线程信息
        return ThreadInfoEntity.current(threadIndex, TimeConstants.PRECISE_TIME_FORMATTER);
    }
}