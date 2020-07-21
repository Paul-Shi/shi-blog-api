package com.example.blog.portal.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.operation.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService extends IService<Category> {
    /**
     * 获取categoryList
     *
     * @param params
     * @return
     */
    List<Category> listCategory(Map<String, Object> params);
}
