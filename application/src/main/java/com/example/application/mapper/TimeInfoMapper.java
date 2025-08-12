package com.example.application.mapper;

import com.example.domain.model.TimeInfoEntity;
import com.example.application.dto.TimeInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 时间信息映射器
 * 使用MapStruct进行Domain对象与DTO对象之间的转换
 * 
 * @author Claude
 * @since 1.0.0
 */
@Mapper
public interface TimeInfoMapper {
    
    TimeInfoMapper INSTANCE = Mappers.getMapper(TimeInfoMapper.class);
    
    /**
     * Domain对象转换为DTO
     * 
     * @param timeInfoEntity Domain层时间信息对象
     * @return DTO时间信息对象
     */
    TimeInfoDto toDto(TimeInfoEntity timeInfoEntity);
    
    /**
     * DTO转换为Domain对象
     * 
     * @param timeInfoDto DTO时间信息对象
     * @return Domain层时间信息对象
     */
    TimeInfoEntity toDomain(TimeInfoDto timeInfoDto);
}
