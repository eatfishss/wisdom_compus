package com.hjx.wisdom_compus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.Admin;
import com.hjx.wisdom_compus.pojo.Grade;
import com.hjx.wisdom_compus.service.GradeService;
import com.hjx.wisdom_compus.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : hjx
 * @Date : 2022/10/2 9:40
 * @Version : 1.0
 **/
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GraderController {
    @Autowired
    private GradeService gradeService;

    @ApiOperation("查询所有年级信息")
    @GetMapping("/getGrades")
    public Result getAllGrade() {
        List<Grade> grades = gradeService.getAllGrade();
        return Result.ok(grades);
    }

    @ApiOperation("执行分页查询所有年级信息")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@ApiParam("当前页") @PathVariable("pageNo") Integer pageNo,
                            @ApiParam("每页显示的数量") @PathVariable("pageSize") Integer pageSize,
                            @ApiParam("模糊查询的名字") String gradeName) {
        // 根据指定条件进行分页查询
        IPage<Grade> iPage = gradeService.getGrades(pageNo, pageSize, gradeName);
        return Result.ok(iPage);
    }

    @ApiOperation("修改或删除年级信息，id为null执行添加，否则执行修改")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("年级信息") @RequestBody Grade grade) {
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("id集合") @RequestBody List<Integer> ids) {
        gradeService.removeByIds(ids);
        return Result.ok();
    }
}
