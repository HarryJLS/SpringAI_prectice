package com.example.application.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * 广告智能创建无效ASIN DTO
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Data
public class InvalidAsinDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 租户id
     */
    @NotBlank(message = "租户ID不能为空")
    @Size(max = 255, message = "租户ID长度不能超过255")
    private String tenantId;

    /**
     * 应用实例id
     */
    @Size(max = 255, message = "应用实例ID长度不能超过255")
    private String applyInstanceId;

    /**
     * 模板id
     */
    @Size(max = 255, message = "模板ID长度不能超过255")
    private String templateId;

    /**
     * profile id
     */
    @Size(max = 255, message = "Profile ID长度不能超过255")
    private String profileId;

    /**
     * 站点
     */
    @Size(max = 255, message = "站点ID长度不能超过255")
    private String marketplaceId;

    /**
     * 卖家id
     */
    @Size(max = 255, message = "卖家ID长度不能超过255")
    private String sellerId;

    /**
     * 子asin
     */
    @NotBlank(message = "卖家ASIN不能为空")
    @Size(max = 255, message = "卖家ASIN长度不能超过255")
    private String sellerAsin;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 时间
     */
    @Size(max = 50, message = "时间字段长度不能超过50")
    private String ds;
}