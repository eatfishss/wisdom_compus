package com.hjx.wisdom_compus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjx.wisdom_compus.pojo.Admin;
import com.hjx.wisdom_compus.pojo.LoginForm;
import com.hjx.wisdom_compus.pojo.Student;
import com.hjx.wisdom_compus.pojo.Teacher;
import com.hjx.wisdom_compus.service.AdminService;
import com.hjx.wisdom_compus.service.ClazzService;
import com.hjx.wisdom_compus.service.StudentService;
import com.hjx.wisdom_compus.service.TeacherService;
import com.hjx.wisdom_compus.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.schema.MultiPartName;
import org.apache.tomcat.util.descriptor.tld.TldRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author : hjx
 * @Date : 2022/10/2 9:41
 * @Version : 1.0
 **/
@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    // 注入service组件
    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;


    @ApiOperation("生成获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerify(HttpSession session, HttpServletResponse resp) {
        // 生成验证码图片和验证码字符串
        BufferedImage codeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        String codeString = new String(CreateVerifiCodeImage.getVerifiCode());
        // 将验证码字符串放入session域中，便于下一次验证
        session.setAttribute("code", codeString);

        try {
            // 将图片以流的形式响应给浏览器
            ImageIO.write(codeImage, "JPEG", resp.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("对登录进行校验")
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpSession session) {
        String formCode = loginForm.getVerifiCode();
        String sessionCode = (String) session.getAttribute("code");
        // 取出验证码后，将session域中的验证码移除
        session.removeAttribute("code");
        // 验证两个验证码是否为空
        if (formCode == null || "".equals(formCode)) {
            return Result.fail().message("验证码已过期，请刷新重试");
        }
        if (sessionCode == null || "".equals(sessionCode)) {
            return Result.fail().message("请输入验证码！");
        }
        // 判断验证码是否相等
        if (!formCode.equalsIgnoreCase(sessionCode)) {
            return Result.fail().message("验证码输入有误，请重新输入");
        }
        // 该map集合用户存放响应的数据
        Map<String, Object> map = new HashMap<>();
        // 对不同类型的用户分类查询数据库
        switch (loginForm.getUserType()) {
            case 1:
                // 当前用户为管理员
                Admin admin = adminService.getAdmin(loginForm);
                if (admin != null) {
                    // 调用工具类生成token
                    map.put("token", JwtHelper.createToken(admin.getId().longValue(), loginForm.getUserType()));
                    return Result.ok(map);
                } else {
                    try {
                        throw new RuntimeException("用户名或密码输入有误！");
                    } catch (Exception e) {
                        e.printStackTrace();
                        // 将异常信息响应给浏览器
                        return Result.fail().message(e.getMessage());
                    }
                }
            case 2:
                // 当前用户为学生
                Student student = studentService.getStudent(loginForm);
                if (student != null) {
                    // 调用工具类生成token
                    map.put("token", JwtHelper.createToken(student.getId().longValue(), loginForm.getUserType()));
                    return Result.ok(map);
                } else {
                    try {
                        throw new RuntimeException("用户名或密码输入有误！");
                    } catch (Exception e) {
                        e.printStackTrace();
                        // 将异常信息响应给浏览器
                        return Result.fail().message(e.getMessage());
                    }
                }
            case 3:
                // 当前用户为教师
                Teacher teacher = teacherService.getTeacher(loginForm);
                if (teacher != null) {
                    // 调用工具类生成token
                    map.put("token", JwtHelper.createToken(teacher.getId().longValue(), loginForm.getUserType()));
                    return Result.ok(map);
                } else {
                    try {
                        throw new RuntimeException("用户名或密码输入有误！");
                    } catch (Exception e) {
                        e.printStackTrace();
                        // 将异常信息响应给浏览器
                        return Result.fail().message(e.getMessage());
                    }
                }
        }
        // 数据库未查询到用户
        return Result.fail().message("查无此用户！");
    }

    @ApiOperation("解析token查询用户的详细信息")
    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token) {
        // 判断token是否过期
        if (JwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        // 从 token 中获取用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        // 创建一个map集合，用于存放用户信息
        Map<String, Object> map = new HashMap<>();
        // 判断用户的类型，查询不同的表
        switch (userType) {
            case 1:
                // 管理员
                Admin admin = adminService.getUserById(userId);
                map.put("user", admin);
                map.put("userType", userType);
                break;
            case 2:
                // 学生
                // 管理员
                Student student = studentService.getUserById(userId);
                map.put("user", student);
                map.put("userType", userType);
                break;
            case 3:
                // 教师
                // 管理员
                Teacher teacher = teacherService.getUserById(userId);
                map.put("user", teacher);
                map.put("userType", userType);
                break;
        }

        return Result.ok(map);
    }

    @ApiOperation("将上传的图片保存到服务器")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) {
        // 生成唯一的文件名
        String preName = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = preName + originalFilename.substring(originalFilename.lastIndexOf("."));
        File file = new File("E:\\wisdom_compus\\target\\classes\\public\\upload\\" + fileName);
        // 保存图片，实际生产中有一台专门的图片服务器
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String path = "upload/" + fileName;
        return Result.ok(path);
    }


    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@RequestHeader("token") String token,
                            @PathVariable("oldPwd") String oldPwd,
                            @PathVariable("newPwd") String newPwd) {
        // 从token中取出用户信息
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        // 对明文密码进行加密
        String pwd = MD5.encrypt(oldPwd);

        // 根据id的类型更新不同表
        switch (userType) {
            case 1:
                // 查询原密码是否输入正确
                QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", userId).eq("password", pwd);
                if (adminService.getOne(queryWrapper1) == null) {
                    return Result.fail().message("用户密码输入有误~");
                } else {
                    // 更新管理员密码
                    adminService.updatePwd(userId, newPwd);
                }
                break;
            case 2:
                // 查询原密码是否输入正确
                QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id", userId).eq("password", pwd);
                if (studentService.getOne(queryWrapper2) == null) {
                    return Result.fail().message("用户密码输入有误~");
                } else {
                    // 更新学生密码
                    studentService.updatePwd(userId, newPwd);
                }
                break;
            case 3:
                // 查询原密码是否输入正确
                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("id", userId).eq("password", pwd);
                if (teacherService.getOne(queryWrapper3) == null) {
                    return Result.fail().message("用户密码输入有误~");
                } else {
                    // 更新教师密码
                    teacherService.updatePwd(userId, newPwd);
                }
                break;
        }
        return Result.ok();
    }
}
