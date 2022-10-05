package com.hjx.wisdom_compus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjx.wisdom_compus.pojo.Admin;
import com.hjx.wisdom_compus.pojo.LoginForm;
import com.hjx.wisdom_compus.pojo.Teacher;
import com.hjx.wisdom_compus.service.TeacherService;
import com.hjx.wisdom_compus.mapper.TeacherMapper;
import com.hjx.wisdom_compus.utils.MD5;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 * @description 针对表【tb_teacher】的数据库操作Service实现
 * @createDate 2022-10-02 09:37:29
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
        implements TeacherService {

    @Override
    public Teacher getTeacher(LoginForm loginForm) {
        // 根据用户名和密码查询用户
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        return getOne(queryWrapper);
    }

    @Override
    public Teacher getUserById(Long userId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return getOne(queryWrapper);
    }

    @Override
    public IPage<Teacher> getTeachers(Integer pageNo, Integer pageSize, Teacher teacher) {
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (Strings.isNotEmpty(teacher.getName())) {
            queryWrapper.like("name", teacher.getName());
        }
        if (Strings.isNotEmpty(teacher.getClazzName())) {
            queryWrapper.eq("clazz_name", teacher.getClazzName());
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void updatePwd(Long userId, String newPwd) {
        Teacher teacher = new Teacher();
        teacher.setId(userId.intValue());
        teacher.setPassword(MD5.encrypt(newPwd));
        baseMapper.updateById(teacher);
    }
}




