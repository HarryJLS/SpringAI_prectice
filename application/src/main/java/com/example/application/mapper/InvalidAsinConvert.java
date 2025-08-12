package com.example.application.mapper;

import com.example.application.dto.InvalidAsinDTO;
import com.example.application.dto.InvalidAsinQueryDTO;
import com.example.domain.model.InvalidAsinEntity;
import com.example.domain.model.InvalidAsinQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Invalid ASIN Application层转换器
 * 负责DTO与Domain Entity之间的转换
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Mapper
public interface InvalidAsinConvert {

    InvalidAsinConvert INSTANCE = Mappers.getMapper(InvalidAsinConvert.class);

    /**
     * DTO 转 Entity
     * 
     * @param dto 数据传输对象
     * @return 领域实体
     */
    InvalidAsinEntity dtoToEntity(InvalidAsinDTO dto);

    /**
     * Entity 转 DTO
     * 
     * @param entity 领域实体
     * @return 数据传输对象
     */
    InvalidAsinDTO entityToDto(InvalidAsinEntity entity);

    /**
     * QueryDTO 转 Query Entity
     * 
     * @param queryDTO 查询DTO
     * @return 领域查询对象
     */
    InvalidAsinQuery queryDtoToQuery(InvalidAsinQueryDTO queryDTO);

    /**
     * Entity列表 转 DTO列表
     * 
     * @param entityList 领域实体列表
     * @return 数据传输对象列表
     */
    List<InvalidAsinDTO> entityListToDtoList(List<InvalidAsinEntity> entityList);

    /**
     * DTO列表 转 Entity列表
     * 
     * @param dtoList 数据传输对象列表
     * @return 领域实体列表
     */
    List<InvalidAsinEntity> dtoListToEntityList(List<InvalidAsinDTO> dtoList);
}