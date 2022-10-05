package com.hjx.wisdom_compus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjx.wisdom_compus.pojo.Admin;
import com.hjx.wisdom_compus.pojo.LoginForm;
import com.hjx.wisdom_compus.pojo.Student;
import com.hjx.wisdom_compus.pojo.Teacher;
import com.hjx.wisdom_compus.service.AdminService;
import com.hjx.wisdom_compus.mapper.AdminMapper;
import com.hjx.wisdom_compus.utils.MD5;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 * @description 针对表【tb_admin】的数据库操作Service实现
 * @createDate 2022-10-02 09:37:29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {

    @Override
    public Admin getAdmin(LoginForm loginForm) {
        // 根据用户名和密码查询用户
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        return getOne(queryWrapper);
    }

    @Override
    public Admin getUserById(Long userId) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return getOne(queryWrapper);
    }

    @Override
    public IPage<Admin> getAllAdmin(Integer pageNo, Integer pageSize, String adminName) {
        Page<Admin> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (Strings.isNotEmpty(adminName)) {
            queryWrapper.like("name", adminName);
        }
        // 根据id降序显示
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void updateAdmin(Admin admin) {
        updateById(admin);
    }

    @Override
    public void addAdmin(Admin admin) {
        save(admin);
    }

    @Override
    public void updatePwd(Long userId, String pwd) {
        Admin admin = new Admin();
        admin.setId(userId.intValue());
        admin.setPassword(MD5.encrypt(pwd));
        baseMapper.updateById(admin);
    }

}




