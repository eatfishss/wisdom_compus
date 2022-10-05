package com.hjx.wisdom_compus.pojo;

import lombok.Data;

/**
 * @Author : hjx
 * @Date : 2022/10/2 17:21
 * @Version : 1.0
 **/
// 该类为收集登录表单信息的实体类
@Data
public class LoginForm {
    private String username;
    private String password;
    private Integer userType;
    private String verifiCode;
}
