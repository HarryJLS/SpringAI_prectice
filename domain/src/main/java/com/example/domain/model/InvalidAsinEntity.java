package com.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Invalid ASIN 领域实体
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvalidAsinEntity {

    /**
     * 主键
     */
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
    private LocalDateTime createTime;

    /**
     * 时间
     */
    private String ds;

    /**
     * 验证租户ID和ASIN是否有效
     * 
     * @return 是否有效
     */
    public boolean isValid() {
        return tenantId != null && !tenantId.trim().isEmpty() 
               && sellerAsin != null && !sellerAsin.trim().isEmpty();
    }

    /**
     * 设置创建时间为当前时间
     */
    public void setCurrentCreateTime() {
        this.createTime = LocalDateTime.now();
    }
}