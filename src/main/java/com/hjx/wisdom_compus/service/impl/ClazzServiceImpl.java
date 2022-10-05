package com.hjx.wisdom_compus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjx.wisdom_compus.pojo.Clazz;
import com.hjx.wisdom_compus.pojo.Grade;
import com.hjx.wisdom_compus.service.ClazzService;
import com.hjx.wisdom_compus.mapper.ClazzMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Admin
 * @description 针对表【tb_clazz】的数据库操作Service实现
 * @createDate 2022-10-02 09:37:29
 */
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
        implements ClazzService {

    @Override
    public IPage<Clazz> getClazzsByOpr(Integer pageNo, Integer pageSize, Clazz clazz) {
        Page<Clazz> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        if (Strings.isNotBlank(clazz.getName())) {
            queryWrapper.like("name", "__"+clazz.getName());
        }
        if (Strings.isNotBlank(clazz.getGradeName())) {
            queryWrapper.eq("grade_name", clazz.getGradeName());
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<Clazz> getClazzs() {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct name");
        return list(queryWrapper);
    }
}




