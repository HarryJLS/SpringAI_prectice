package com.example.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.infrastructure.persistence.DO.InvalidAsinDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 广告智能创建无效ASIN Mapper接口
 * 
 * @author Gemini  
 * @since 1.0.0
 */
@Mapper
public interface InvalidAsinMapper extends BaseMapper<InvalidAsinDO> {

    /**
     * 根据租户ID和ASIN查询无效ASIN列表
     * 
     * @param tenantId 租户ID
     * @param sellerAsin 卖家ASIN
     * @return 无效ASIN列表
     */
    List<InvalidAsinDO> selectByTenantIdAndSellerAsin(@Param("tenantId") String tenantId,
                                                    @Param("sellerAsin") String sellerAsin);

    /**
     * 根据租户ID和Profile ID查询无效ASIN列表
     * 
     * @param tenantId 租户ID
     * @param profileId Profile ID
     * @return 无效ASIN列表
     */
    List<InvalidAsinDO> selectByTenantIdAndProfileId(@Param("tenantId") String tenantId,
                                                   @Param("profileId") String profileId);

    /**
     * 批量插入无效ASIN
     * 
     * @param invalidAsins 无效ASIN列表
     * @return 插入条数
     */
    int batchInsert(@Param("list") List<InvalidAsinDO> invalidAsins);

    /**
     * 根据条件删除无效ASIN
     * 
     * @param tenantId 租户ID
     * @param profileId Profile ID
     * @param marketplaceId 站点ID
     * @return 删除条数
     */
    int deleteByCondition(@Param("tenantId") String tenantId,
                         @Param("profileId") String profileId,
                         @Param("marketplaceId") String marketplaceId);
}