package com.hjx.wisdom_compus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hjx.wisdom_compus.pojo.LoginForm;
import com.hjx.wisdom_compus.pojo.Student;
import com.hjx.wisdom_compus.pojo.Teacher;
import org.springframework.stereotype.Service;

/**
* @author Admin
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-10-02 09:37:29
*/

public interface AdminService extends IService<Admin> {

    Admin getAdmin(LoginForm loginForm);

    Admin getUserById(Long userId);

    IPage<Admin> getAllAdmin(Integer pageNo, Integer pageSize, String adminName);

    void updateAdmin(Admin admin);

    void addAdmin(Admin admin);

    void updatePwd(Long userId, String pwd);
}
