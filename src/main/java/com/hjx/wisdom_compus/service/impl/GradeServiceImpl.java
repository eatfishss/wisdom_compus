package com.hjx.wisdom_compus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjx.wisdom_compus.pojo.Admin;
import com.hjx.wisdom_compus.pojo.Grade;
import com.hjx.wisdom_compus.service.GradeService;
import com.hjx.wisdom_compus.mapper.GradeMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Admin
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-10-02 09:37:29
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{

    @Override
    public IPage<Grade> getGrades(Integer pageNo, Integer pageSize, String gradeName) {
        Page<Grade> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (Strings.isNotEmpty(gradeName)) {
            queryWrapper.like("name", gradeName);
        }
        // 根据id降序显示
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<Grade> getAllGrade() {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct name");
        return list(queryWrapper);
    }


}




