package com.hjx.wisdom_compus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.Clazz;
import com.hjx.wisdom_compus.service.ClazzService;
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
@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @ApiOperation("查询所有班级信息")
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> clazzes = clazzService.getClazzs();
        return Result.ok(clazzes);
    }

    @ApiOperation("分页查询所有班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@ApiParam("当前页") @PathVariable("pageNo") Integer pageNo,
                                 @ApiParam("每页显示的数量") @PathVariable("pageSize") Integer pageSize,
                                 @ApiParam("模糊查询的条件") Clazz clazz) {
        IPage<Clazz> page = clazzService.getClazzsByOpr(pageNo, pageSize, clazz);
        return Result.ok(page);
    }

    @ApiOperation("修改或添加班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("用户为null，执行添加，否则进行修改") @RequestBody Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @ApiOperation("根据id删除班级")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@ApiParam("根据id集合删除班级") @RequestBody List<Integer> ids) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }

}
