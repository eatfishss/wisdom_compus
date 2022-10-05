package com.hjx.wisdom_compus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjx.wisdom_compus.pojo.Admin;
import com.hjx.wisdom_compus.pojo.Grade;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Admin
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2022-10-02 09:37:29
*/

public interface GradeService extends IService<Grade> {

    IPage<Grade> getGrades(Integer pageNo, Integer pageSize, String gradeName);


    List<Grade> getAllGrade();
}
