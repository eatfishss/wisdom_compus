package com.hjx.wisdom_compus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.Clazz;
import com.hjx.wisdom_compus.pojo.Teacher;
import com.hjx.wisdom_compus.service.TeacherService;
import com.hjx.wisdom_compus.utils.MD5;
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
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@ApiParam("当前页") @PathVariable("pageNo") Integer pageNo,
                              @ApiParam("每页显示的数量") @PathVariable("pageSize") Integer pageSize,
                              @ApiParam("模糊查询的条件") Teacher teacher) {
        IPage<Teacher> page = teacherService.getTeachers(pageNo, pageSize, teacher);
        return Result.ok(page);
    }

    @ApiOperation("添加或修改学生信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {
        // 如果添加学生，将密码转为密文
        if (teacher.getId() == null) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids) {
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}
