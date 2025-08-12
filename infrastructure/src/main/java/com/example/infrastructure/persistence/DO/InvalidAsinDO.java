package com.example.infrastructure.persistence.DO;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 广告智能创建无效ASIN数据对象 (DO)
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Data
@TableName("fenghuo_ad_smart_creation_invalid_asin")
public class InvalidAsinDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 应用实例id
     */
    private String applyInstanceId;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * profile id
     */
    private String profileId;

    /**
     * 站点
     */
    private String marketplaceId;

    /**
     * 卖家id
     */
    private String sellerId;

    /**
     * 子asin
     */
    private String sellerAsin;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 时间
     */
    private String ds;
}