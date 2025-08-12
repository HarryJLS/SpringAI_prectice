package com.example.application.mapper;

import com.example.domain.model.ThreadInfoEntity;
import com.example.application.dto.ThreadInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 线程信息映射器
 * 使用MapStruct进行Domain对象与DTO对象之间的转换
 * 
 * @author Claude
 * @since 1.0.0
 */
@Mapper
public interface ThreadInfoMapper {
    
    ThreadInfoMapper INSTANCE = Mappers.getMapper(ThreadInfoMapper.class);
    
    /**
     * Domain对象转换为DTO
     * 
     * @param threadInfoEntity Domain层线程信息对象
     * @return DTO线程信息对象
     */
    ThreadInfoDto toDto(ThreadInfoEntity threadInfoEntity);
    
    /**
     * DTO转换为Domain对象
     * 
     * @param threadInfoDto DTO线程信息对象
     * @return Domain层线程信息对象
     */
    ThreadInfoEntity toDomain(ThreadInfoDto threadInfoDto);
    
    /**
     * Domain对象列表转换为DTO列表
     * 
     * @param threadInfoEntities Domain层线程信息对象列表
     * @return DTO线程信息对象列表
     */
    List<ThreadInfoDto> toDtoList(List<ThreadInfoEntity> threadInfoEntities);
    
    /**
     * DTO列表转换为Domain对象列表
     * 
     * @param threadInfoDtos DTO线程信息对象列表
     * @return Domain层线程信息对象列表
     */
    List<ThreadInfoEntity> toDomainList(List<ThreadInfoDto> threadInfoDtos);
}