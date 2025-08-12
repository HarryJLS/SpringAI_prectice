package com.example.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Configuration
@MapperScan(basePackages = "com.example.infrastructure.persistence.mapper",
           markerInterface = org.apache.ibatis.annotations.Mapper.class)
public class MyBatisPlusConfig {

}