package com.hjx.wisdom_compus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.LoginForm;
import com.hjx.wisdom_compus.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author Admin
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-10-02 09:37:29
*/

public interface TeacherService extends IService<Teacher> {

    Teacher getTeacher(LoginForm loginForm);

    Teacher getUserById(Long userId);

    IPage<Teacher> getTeachers(Integer pageNo, Integer pageSize, Teacher teacher);

    void updatePwd(Long userId, String newPwd);
}
