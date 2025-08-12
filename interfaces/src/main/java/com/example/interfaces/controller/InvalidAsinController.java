package com.example.interfaces.controller;

import com.example.application.service.InvalidAsinApplicationService;
import com.example.domain.exception.BusinessException;
import com.common.response.JlsResponse;
import com.example.application.dto.InvalidAsinDTO;
import com.example.application.dto.InvalidAsinQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Invalid ASIN 控制器
 * 提供无效ASIN的增删改查接口
 * 
 * @author Gemini
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/invalid-asin")
@Validated
@RequiredArgsConstructor
public class InvalidAsinController {

    private final InvalidAsinApplicationService invalidAsinApplicationService;

    /**
     * 创建无效ASIN
     * 
     * @param invalidAsin 无效ASIN DTO
     * @return 创建后的对象
     */
    @PostMapping
    public JlsResponse<InvalidAsinDTO> createInvalidAsin(@Valid @RequestBody InvalidAsinDTO invalidAsin) {
        try {
            InvalidAsinDTO result = invalidAsinApplicationService.createInvalidAsin(invalidAsin);
            return JlsResponse.success(result);
        } catch (Exception e) {
            log.error("创建无效ASIN失败", e);
            throw BusinessException.threadError("创建无效ASIN失败", e);
        }
    }

    /**
     * 批量创建无效ASIN
     * 
     * @param invalidAsins 无效ASIN列表
     * @return 创建成功的条数
     */
    @PostMapping("/batch")
    public JlsResponse<Integer> batchCreateInvalidAsin(@Valid @RequestBody @NotEmpty(message = "无效ASIN列表不能为空") 
                                                  List<InvalidAsinDTO> invalidAsins) {
        try {
            int count = invalidAsinApplicationService.batchCreateInvalidAsin(invalidAsins);
            return JlsResponse.success(count);
        } catch (Exception e) {
            log.error("批量创建无效ASIN失败", e);
            throw BusinessException.threadError("批量创建无效ASIN失败", e);
        }
    }

    /**
     * 根据ID查询无效ASIN
     * 
     * @param id 主键ID
     * @return 无效ASIN DTO
     */
    @GetMapping("/{id}")
    public JlsResponse<InvalidAsinDTO> getInvalidAsinById(@PathVariable @NotNull(message = "ID不能为空") Long id) {
        try {
            InvalidAsinDTO result = invalidAsinApplicationService.getInvalidAsinById(id);
            return JlsResponse.success(result);
        } catch (Exception e) {
            log.error("查询无效ASIN失败", e);
            throw BusinessException.threadError("查询无效ASIN失败", e);
        }
    }

    /**
     * 根据条件查询无效ASIN列表
     * 
     * @param queryDTO 查询条件
     * @return 无效ASIN列表
     */
    @GetMapping("/list")
    public JlsResponse<List<InvalidAsinDTO>> getInvalidAsinList(@Valid InvalidAsinQueryDTO queryDTO) {
        try {
            List<InvalidAsinDTO> result = invalidAsinApplicationService.getInvalidAsinList(queryDTO);
            return JlsResponse.success(result);
        } catch (Exception e) {
            log.error("查询无效ASIN列表失败", e);
            throw BusinessException.threadError("查询无效ASIN列表失败", e);
        }
    }

    /**
     * 根据租户ID和ASIN查询
     * 
     * @param tenantId 租户ID
     * @param sellerAsin 卖家ASIN
     * @return 无效ASIN列表
     */
    @GetMapping("/tenant/{tenantId}/asin/{sellerAsin}")
    public JlsResponse<List<InvalidAsinDTO>> getInvalidAsinByTenantAndAsin(
            @PathVariable String tenantId,
            @PathVariable String sellerAsin) {
        try {
            List<InvalidAsinDTO> result = invalidAsinApplicationService
                    .getInvalidAsinByTenantAndAsin(tenantId, sellerAsin);
            return JlsResponse.success(result);
        } catch (Exception e) {
            log.error("根据租户和ASIN查询无效ASIN失败", e);
            throw BusinessException.threadError("根据租户和ASIN查询无效ASIN失败", e);
        }
    }

    /**
     * 更新无效ASIN
     * 
     * @param id 主键ID
     * @param invalidAsin 无效ASIN DTO
     * @return 更新后的对象
     */
    @PutMapping("/{id}")
    public JlsResponse<InvalidAsinDTO> updateInvalidAsin(@PathVariable @NotNull(message = "ID不能为空") Long id,
                                                    @Valid @RequestBody InvalidAsinDTO invalidAsin) {
        try {
            invalidAsin.setId(id);
            InvalidAsinDTO result = invalidAsinApplicationService.updateInvalidAsin(invalidAsin);
            return JlsResponse.success(result);
        } catch (Exception e) {
            log.error("更新无效ASIN失败", e);
            throw BusinessException.threadError("更新无效ASIN失败", e);
        }
    }

    /**
     * 根据ID删除无效ASIN
     * 
     * @param id 主键ID
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public JlsResponse<Boolean> deleteInvalidAsinById(@PathVariable @NotNull(message = "ID不能为空") Long id) {
        try {
            boolean result = invalidAsinApplicationService.deleteInvalidAsinById(id);
            return JlsResponse.success(result);
        } catch (Exception e) {
            log.error("删除无效ASIN失败", e);
            throw BusinessException.threadError("删除无效ASIN失败", e);
        }
    }

    /**
     * 根据条件删除无效ASIN
     * 
     * @param tenantId 租户ID
     * @param profileId Profile ID
     * @param marketplaceId 站点ID
     * @return 删除的条数
     */
    @DeleteMapping("/condition")
    public JlsResponse<Integer> deleteInvalidAsinByCondition(
            @RequestParam String tenantId,
            @RequestParam(required = false) String profileId,
            @RequestParam(required = false) String marketplaceId) {
        try {
            int count = invalidAsinApplicationService.deleteInvalidAsinByCondition(tenantId, profileId, marketplaceId);
            return JlsResponse.success(count);
        } catch (Exception e) {
            log.error("根据条件删除无效ASIN失败", e);
            throw BusinessException.threadError("根据条件删除无效ASIN失败", e);
        }
    }

    /**
     * 统计符合条件的记录数
     * 
     * @param queryDTO 查询条件
     * @return 记录数
     */
    @GetMapping("/count")
    public JlsResponse<Long> countInvalidAsin(@Valid InvalidAsinQueryDTO queryDTO) {
        try {
            long count = invalidAsinApplicationService.countInvalidAsin(queryDTO);
            return JlsResponse.success(count);
        } catch (Exception e) {
            log.error("统计无效ASIN记录数失败", e);
            throw BusinessException.threadError("统计无效ASIN记录数失败", e);
        }
    }
}