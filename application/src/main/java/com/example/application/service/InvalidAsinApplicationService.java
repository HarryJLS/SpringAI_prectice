package com.example.application.service;

import com.example.application.mapper.InvalidAsinConvert;
import com.example.domain.repository.InvalidAsinRepository;
import com.example.domain.model.InvalidAsinEntity;
import com.example.domain.model.InvalidAsinQuery;
import com.example.application.dto.InvalidAsinDTO;
import com.example.application.dto.InvalidAsinQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Invalid ASIN 应用服务
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InvalidAsinApplicationService {

    private final InvalidAsinRepository invalidAsinRepository;

    /**
     * 创建无效ASIN
     * 
     * @param invalidAsinDTO 无效ASIN DTO
     * @return 创建后的对象
     */
    @Transactional(rollbackFor = Exception.class)
    public InvalidAsinDTO createInvalidAsin(InvalidAsinDTO invalidAsinDTO) {
        log.info("创建无效ASIN: {}", invalidAsinDTO.getSellerAsin());
        
        // 转换为Domain实体
        InvalidAsinEntity entity = InvalidAsinConvert.INSTANCE.dtoToEntity(invalidAsinDTO);
        
        // 设置创建时间
        if (Objects.isNull(entity.getCreateTime())) {
            entity.setCurrentCreateTime();
        }
        
        // 验证领域实体
        if (!entity.isValid()) {
            throw new IllegalArgumentException("无效ASIN数据不完整");
        }
        
        // 保存并返回
        InvalidAsinEntity saved = invalidAsinRepository.save(entity);
        return InvalidAsinConvert.INSTANCE.entityToDto(saved);
    }

    /**
     * 批量创建无效ASIN
     * 
     * @param invalidAsinDTOs 无效ASIN DTO列表
     * @return 创建成功的条数
     */
    @Transactional(rollbackFor = Exception.class)
    public int batchCreateInvalidAsin(List<InvalidAsinDTO> invalidAsinDTOs) {
        log.info("批量创建无效ASIN: {} 条", invalidAsinDTOs.size());
        
        // 转换为Domain实体列表
        List<InvalidAsinEntity> entities = InvalidAsinConvert.INSTANCE.dtoListToEntityList(invalidAsinDTOs);
        
        // 设置创建时间并验证
        LocalDateTime now = LocalDateTime.now();
        entities.forEach(entity -> {
            if (Objects.isNull(entity.getCreateTime())) {
                entity.setCreateTime(now);
            }
            if (!entity.isValid()) {
                throw new IllegalArgumentException("无效ASIN数据不完整: " + entity.getSellerAsin());
            }
        });
        
        return invalidAsinRepository.batchSave(entities);
    }

    /**
     * 根据ID查询无效ASIN
     * 
     * @param id 主键ID
     * @return 无效ASIN DTO
     */
    public InvalidAsinDTO getInvalidAsinById(Long id) {
        log.debug("根据ID查询无效ASIN: {}", id);
        return invalidAsinRepository.findById(id)
                .map(InvalidAsinConvert.INSTANCE::entityToDto)
                .orElse(null);
    }

    /**
     * 根据条件查询无效ASIN列表
     * 
     * @param queryDTO 查询条件
     * @return 无效ASIN列表
     */
    public List<InvalidAsinDTO> getInvalidAsinList(InvalidAsinQueryDTO queryDTO) {
        log.debug("根据条件查询无效ASIN列表: {}", queryDTO);
        
        // 转换查询条件
        InvalidAsinQuery query = InvalidAsinConvert.INSTANCE.queryDtoToQuery(queryDTO);
        
        // 查询Domain实体列表
        List<InvalidAsinEntity> entities = invalidAsinRepository.findByCondition(query);
        
        // 转换为DTO列表
        return InvalidAsinConvert.INSTANCE.entityListToDtoList(entities);
    }

    /**
     * 根据租户ID和ASIN查询
     * 
     * @param tenantId 租户ID
     * @param sellerAsin 卖家ASIN
     * @return 无效ASIN列表
     */
    public List<InvalidAsinDTO> getInvalidAsinByTenantAndAsin(String tenantId, String sellerAsin) {
        log.debug("根据租户ID和ASIN查询: tenantId={}, sellerAsin={}", tenantId, sellerAsin);
        
        List<InvalidAsinEntity> entities = invalidAsinRepository.findByTenantIdAndSellerAsin(tenantId, sellerAsin);
        return InvalidAsinConvert.INSTANCE.entityListToDtoList(entities);
    }

    /**
     * 更新无效ASIN
     * 
     * @param invalidAsinDTO 无效ASIN DTO
     * @return 更新后的对象
     */
    @Transactional(rollbackFor = Exception.class)
    public InvalidAsinDTO updateInvalidAsin(InvalidAsinDTO invalidAsinDTO) {
        log.info("更新无效ASIN: id={}", invalidAsinDTO.getId());
        
        // 验证记录是否存在
        if (!invalidAsinRepository.findById(invalidAsinDTO.getId()).isPresent()) {
            throw new IllegalArgumentException("无效ASIN记录不存在: " + invalidAsinDTO.getId());
        }
        
        // 转换并验证Domain实体
        InvalidAsinEntity entity = InvalidAsinConvert.INSTANCE.dtoToEntity(invalidAsinDTO);
        if (!entity.isValid()) {
            throw new IllegalArgumentException("无效ASIN数据不完整");
        }
        
        InvalidAsinEntity updated = invalidAsinRepository.updateById(entity);
        return InvalidAsinConvert.INSTANCE.entityToDto(updated);
    }

    /**
     * 根据ID删除无效ASIN
     * 
     * @param id 主键ID
     * @return 删除是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteInvalidAsinById(Long id) {
        log.info("根据ID删除无效ASIN: {}", id);
        
        // 验证记录是否存在
        if (!invalidAsinRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("无效ASIN记录不存在: " + id);
        }
        
        return invalidAsinRepository.deleteById(id);
    }

    /**
     * 根据条件删除无效ASIN
     * 
     * @param tenantId 租户ID
     * @param profileId Profile ID
     * @param marketplaceId 站点ID
     * @return 删除的条数
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteInvalidAsinByCondition(String tenantId, String profileId, String marketplaceId) {
        log.info("根据条件删除无效ASIN: tenantId={}, profileId={}, marketplaceId={}", 
                tenantId, profileId, marketplaceId);
        return invalidAsinRepository.deleteByCondition(tenantId, profileId, marketplaceId);
    }

    /**
     * 统计符合条件的记录数
     * 
     * @param queryDTO 查询条件
     * @return 记录数
     */
    public long countInvalidAsin(InvalidAsinQueryDTO queryDTO) {
        log.debug("统计无效ASIN记录数: {}", queryDTO);
        
        InvalidAsinQuery query = InvalidAsinConvert.INSTANCE.queryDtoToQuery(queryDTO);
        return invalidAsinRepository.countByCondition(query);
    }
}