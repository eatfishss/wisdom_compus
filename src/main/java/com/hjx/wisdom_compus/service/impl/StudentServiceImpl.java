package com.hjx.wisdom_compus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjx.wisdom_compus.pojo.Admin;
import com.hjx.wisdom_compus.pojo.LoginForm;
import com.hjx.wisdom_compus.pojo.Student;
import com.hjx.wisdom_compus.service.StudentService;
import com.hjx.wisdom_compus.mapper.StudentMapper;
import com.hjx.wisdom_compus.utils.MD5;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 * @description 针对表【tb_student】的数据库操作Service实现
 * @createDate 2022-10-02 09:37:29
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Override
    public Student getStudent(LoginForm loginForm) {
        // 根据用户名和密码查询用户
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        return getOne(queryWrapper);
    }

    @Override
    public Student getUserById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return getOne(queryWrapper);
    }

    @Override
    public IPage<Student> getStudentByOpr(Integer pageNo, Integer pageSize, Student student) {
        Page<Student> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (Strings.isNotEmpty(student.getName())) {
            queryWrapper.like("name", student.getName());
        }
        if (Strings.isNotEmpty(student.getClazzName())) {
            queryWrapper.eq("clazz_name", student.getClazzName());
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void updatePwd(Long userId, String newPwd) {
        Student student = new Student();
        student.setId(userId.intValue());
        student.setPassword(MD5.encrypt(newPwd));
        baseMapper.updateById(student);
    }
}




