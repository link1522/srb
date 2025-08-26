package com.example.srb.core.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.srb.core.pojo.entity.IntegralGrade;
import com.example.srb.core.service.IntegralGradeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author Terry
 * @since 2025-08-25
 */
@Api(tags = "積分等級管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {
    @Resource
    private IntegralGradeService integralGradeService;

    @ApiOperation("積分等級列表")
    @GetMapping("/list")
    public List<IntegralGrade> listAll() {
        return integralGradeService.list();
    }

    @ApiOperation(value = "根據 id 刪除積分等級", notes = "邏輯刪除")
    @DeleteMapping("/remove/{id}")
    public boolean removeById(@ApiParam(value = "數據 ID", example = "100", required = true) @PathVariable Long id) {
        return integralGradeService.removeById(id);
    }
}
