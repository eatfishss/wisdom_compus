package com.hjx.wisdom_compus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.LoginForm;
import com.hjx.wisdom_compus.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author Admin
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-10-02 09:37:29
*/

public interface StudentService extends IService<Student> {

    Student getStudent(LoginForm loginForm);

    Student getUserById(Long userId);

    IPage<Student> getStudentByOpr(Integer pageNo, Integer pageSize, Student student);

    void updatePwd(Long userId, String newPwd);
}
