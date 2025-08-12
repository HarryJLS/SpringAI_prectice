package com.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Invalid ASIN 查询条件
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvalidAsinQuery {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * profile id
     */
    private String profileId;

    /**
     * 站点
     */
    private String marketplaceId;

    /**
     * 卖家ASIN
     */
    private String sellerAsin;

    /**
     * 页码，从1开始
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 是否需要分页
     * 
     * @return 是否分页
     */
    public boolean needPaging() {
        return pageNum != null && pageSize != null && pageNum > 0 && pageSize > 0;
    }
}