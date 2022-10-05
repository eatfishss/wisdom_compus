package com.hjx.wisdom_compus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Admin
* @description 针对表【tb_clazz】的数据库操作Service
* @createDate 2022-10-02 09:37:29
*/

public interface ClazzService extends IService<Clazz> {

    IPage<Clazz> getClazzsByOpr(Integer pageNo, Integer pageSize, Clazz clazz);

    List<Clazz> getClazzs();

}
