package com.example.application.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

/**
 * Invalid ASIN 查询条件 DTO
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Data
public class InvalidAsinQueryDTO {

    /**
     * 租户id
     */
    @NotBlank(message = "租户ID不能为空")
    @Size(max = 255, message = "租户ID长度不能超过255")
    private String tenantId;

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
     * 卖家ASIN
     */
    @Size(max = 255, message = "卖家ASIN长度不能超过255")
    private String sellerAsin;

    /**
     * 页码，从1开始
     */
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    @Min(value = 1, message = "每页大小必须大于0")
    private Integer pageSize = 20;
}