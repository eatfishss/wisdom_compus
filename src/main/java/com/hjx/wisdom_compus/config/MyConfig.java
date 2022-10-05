package com.hjx.wisdom_compus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : hjx
 * @Date : 2022/10/2 8:49
 * @Version : 1.0
 **/
@Configuration
@MapperScan("com.hjx.wisdom_compus.mapper")
public class MyConfig {
    // 添加分页插件拦截器到ioc容器中
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
