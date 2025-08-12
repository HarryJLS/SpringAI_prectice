package com.example.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.model.InvalidAsinEntity;
import com.example.domain.model.InvalidAsinQuery;
import com.example.domain.repository.InvalidAsinRepository;
import com.example.infrastructure.persistence.DO.InvalidAsinDO;
import com.example.infrastructure.persistence.converter.InvalidAsinConvert;
import com.example.infrastructure.persistence.mapper.InvalidAsinMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Invalid ASIN 仓储实现
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Repository
public class InvalidAsinRepositoryImpl implements InvalidAsinRepository {

    @Resource
    private InvalidAsinMapper invalidAsinMapper;

    @Override
    public InvalidAsinEntity save(InvalidAsinEntity invalidAsinEntity) {
        InvalidAsinDO invalidAsinDO = InvalidAsinConvert.INSTANCE.entityToDo(invalidAsinEntity);
        invalidAsinMapper.insert(invalidAsinDO);
        return InvalidAsinConvert.INSTANCE.doToEntity(invalidAsinDO);
    }

    @Override
    public int batchSave(List<InvalidAsinEntity> invalidAsinEntities) {
        List<InvalidAsinDO> poList = InvalidAsinConvert.INSTANCE.entityToDOList(invalidAsinEntities);
        return invalidAsinMapper.batchInsert(poList);
    }

    @Override
    public Optional<InvalidAsinEntity> findById(Long id) {
        InvalidAsinDO po = invalidAsinMapper.selectById(id);
        return Objects.nonNull(po) ? 
               Optional.of(InvalidAsinConvert.INSTANCE.doToEntity(po)) :
               Optional.empty();
    }

    @Override
    public List<InvalidAsinEntity> findByCondition(InvalidAsinQuery query) {
        LambdaQueryWrapper<InvalidAsinDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(query.getTenantId()), InvalidAsinDO::getTenantId, query.getTenantId())
               .eq(StringUtils.isNotBlank(query.getProfileId()), InvalidAsinDO::getProfileId, query.getProfileId())
               .eq(StringUtils.isNotBlank(query.getMarketplaceId()), InvalidAsinDO::getMarketplaceId, query.getMarketplaceId())
               .eq(StringUtils.isNotBlank(query.getSellerAsin()), InvalidAsinDO::getSellerAsin, query.getSellerAsin())
               .orderByDesc(InvalidAsinDO::getCreateTime);

        // 如果需要分页
        if (query.needPaging()) {
            Page<InvalidAsinDO> page = new Page<>(query.getPageNum(), query.getPageSize());
            Page<InvalidAsinDO> result = invalidAsinMapper.selectPage(page, wrapper);
            return InvalidAsinConvert.INSTANCE.doToEntityList(result.getRecords());
        } else {
            List<InvalidAsinDO> poList = invalidAsinMapper.selectList(wrapper);
            return InvalidAsinConvert.INSTANCE.doToEntityList(poList);
        }
    }

    @Override
    public List<InvalidAsinEntity> findByTenantIdAndSellerAsin(String tenantId, String sellerAsin) {
        List<InvalidAsinDO> poList = invalidAsinMapper.selectByTenantIdAndSellerAsin(tenantId, sellerAsin);
        return InvalidAsinConvert.INSTANCE.doToEntityList(poList);
    }

    @Override
    public InvalidAsinEntity updateById(InvalidAsinEntity invalidAsinEntity) {
        InvalidAsinDO po = InvalidAsinConvert.INSTANCE.entityToDo(invalidAsinEntity);
        invalidAsinMapper.updateById(po);
        return InvalidAsinConvert.INSTANCE.doToEntity(po);
    }

    @Override
    public boolean deleteById(Long id) {
        return invalidAsinMapper.deleteById(id) > 0;
    }

    @Override
    public int deleteByCondition(String tenantId, String profileId, String marketplaceId) {
        return invalidAsinMapper.deleteByCondition(tenantId, profileId, marketplaceId);
    }

    @Override
    public long countByCondition(InvalidAsinQuery query) {
        LambdaQueryWrapper<InvalidAsinDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(query.getTenantId()), InvalidAsinDO::getTenantId, query.getTenantId())
               .eq(StringUtils.isNotBlank(query.getProfileId()), InvalidAsinDO::getProfileId, query.getProfileId())
               .eq(StringUtils.isNotBlank(query.getMarketplaceId()), InvalidAsinDO::getMarketplaceId, query.getMarketplaceId())
               .eq(StringUtils.isNotBlank(query.getSellerAsin()), InvalidAsinDO::getSellerAsin, query.getSellerAsin());

        return invalidAsinMapper.selectCount(wrapper);
    }
}