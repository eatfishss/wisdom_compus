package com.hjx.wisdom_compus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.Admin;
import com.hjx.wisdom_compus.service.AdminService;
import com.hjx.wisdom_compus.utils.JwtHelper;
import com.hjx.wisdom_compus.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : hjx
 * @Date : 2022/10/2 9:39
 * @Version : 1.0
 **/
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // 分页查询
    @ApiOperation("该方法用于分页查询")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@ApiParam("当前页") @PathVariable("pageNo") Integer pageNo,
                              @ApiParam("每页显示的数量") @PathVariable("pageSize") Integer pageSize,
                              @ApiParam("模糊查询的名字") String adminName) {
        // 根据指定条件进行分页查询
        IPage<Admin> iPage = adminService.getAllAdmin(pageNo, pageSize, adminName);
        return Result.ok(iPage);
    }

    // 修改和添加用户信息
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin) {
        if (admin.getId() == null) {
            // id 不为null，执行添加
            adminService.addAdmin(admin);
        } else {
            // id 为null，执行更新
            adminService.updateAdmin(admin);
        }
        return Result.ok();
    }

    // 根据id删除用户
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids) {
        adminService.removeByIds(ids);
        return Result.ok();
    }


}
