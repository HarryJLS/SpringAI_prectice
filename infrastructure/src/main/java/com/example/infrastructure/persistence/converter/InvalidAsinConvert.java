package com.example.infrastructure.persistence.converter;

import com.example.domain.model.InvalidAsinEntity;
import com.example.infrastructure.persistence.DO.InvalidAsinDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Invalid ASIN Infrastructure层转换器
 * 负责Domain Entity与DO对象之间的转换
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Mapper
public interface InvalidAsinConvert {

    InvalidAsinConvert INSTANCE = Mappers.getMapper(InvalidAsinConvert.class);

    /**
     * Entity 转 DO
     * 
     * @param entity 领域实体
     * @return 数据对象
     */
    InvalidAsinDO entityToDo(InvalidAsinEntity entity);

    /**
     * DO 转 Entity
     * 
     * @param dataObject 数据对象
     * @return 领域实体
     */
    InvalidAsinEntity doToEntity(InvalidAsinDO dataObject);

    List<InvalidAsinDO> entityToDOList(List<InvalidAsinEntity> invalidAsinEntities);


    List<InvalidAsinEntity> doToEntityList(List<InvalidAsinDO> records);
}