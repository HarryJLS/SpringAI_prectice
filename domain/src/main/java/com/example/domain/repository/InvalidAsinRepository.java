package com.example.domain.repository;

import com.example.domain.model.InvalidAsinEntity;
import com.example.domain.model.InvalidAsinQuery;

import java.util.List;
import java.util.Optional;

/**
 * Invalid ASIN 仓储接口
 * 
 * @author Gemini
 * @since 1.0.0
 */
public interface InvalidAsinRepository {

    /**
     * 保存无效ASIN
     * 
     * @param invalidAsinEntity 无效ASIN领域对象
     * @return 保存后的对象
     */
    InvalidAsinEntity save(InvalidAsinEntity invalidAsinEntity);

    /**
     * 批量保存无效ASIN
     * 
     * @param invalidAsinEntities 无效ASIN列表
     * @return 保存成功的条数
     */
    int batchSave(List<InvalidAsinEntity> invalidAsinEntities);

    /**
     * 根据ID查询无效ASIN
     * 
     * @param id 主键ID
     * @return 无效ASIN领域对象
     */
    Optional<InvalidAsinEntity> findById(Long id);

    /**
     * 根据条件查询无效ASIN列表
     * 
     * @param query 查询条件
     * @return 无效ASIN列表
     */
    List<InvalidAsinEntity> findByCondition(InvalidAsinQuery query);

    /**
     * 根据租户ID和ASIN查询
     * 
     * @param tenantId 租户ID
     * @param sellerAsin 卖家ASIN
     * @return 无效ASIN列表
     */
    List<InvalidAsinEntity> findByTenantIdAndSellerAsin(String tenantId, String sellerAsin);

    /**
     * 根据ID更新无效ASIN
     * 
     * @param invalidAsinEntity 无效ASIN领域对象
     * @return 更新后的对象
     */
    InvalidAsinEntity updateById(InvalidAsinEntity invalidAsinEntity);

    /**
     * 根据ID删除无效ASIN
     * 
     * @param id 主键ID
     * @return 删除是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据条件删除无效ASIN
     * 
     * @param tenantId 租户ID
     * @param profileId Profile ID
     * @param marketplaceId 站点ID
     * @return 删除的条数
     */
    int deleteByCondition(String tenantId, String profileId, String marketplaceId);

    /**
     * 统计符合条件的记录数
     * 
     * @param query 查询条件
     * @return 记录数
     */
    long countByCondition(InvalidAsinQuery query);
}