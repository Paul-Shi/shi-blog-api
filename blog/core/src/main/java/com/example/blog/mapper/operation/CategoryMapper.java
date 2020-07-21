package com.example.blog.mapper.operation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.operation.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询所有类别
     *
     * @param params
     * @return
     */
    List<Category> queryAll(Map<String, Object> params);
}
