package com.hjx.wisdom_compus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.Clazz;
import com.hjx.wisdom_compus.pojo.Student;
import com.hjx.wisdom_compus.service.StudentService;
import com.hjx.wisdom_compus.utils.MD5;
import com.hjx.wisdom_compus.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : hjx
 * @Date : 2022/10/2 9:40
 * @Version : 1.0
 **/
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ApiOperation("分页查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@ApiParam("当前页") @PathVariable("pageNo") Integer pageNo,
                                  @ApiParam("每页显示的数量") @PathVariable("pageSize") Integer pageSize,
                                  @ApiParam("模糊查询的条件") Student student) {
        IPage<Student> page = studentService.getStudentByOpr(pageNo, pageSize, student);
        return Result.ok(page);
    }

    @ApiOperation("添加和修改学生信息，id为空为添加，否则为修改")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@ApiParam("学生信息") @RequestBody Student student) {
        // 如果添加学生，将密码转为密文
        if (student.getId() == null) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    @ApiOperation("根据id删除学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@ApiParam("删除集合内的id对应的学生信息") @RequestBody List<Integer> ids) {
        studentService.removeByIds(ids);
        return Result.ok();
    }
}
