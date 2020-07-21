package com.example.blog.portal.operation.controller;

import com.example.blog.common.Result;
import com.example.blog.common.constants.RedisCacheNames;
import com.example.blog.entity.operation.Category;
import com.example.blog.portal.operation.service.CategoryService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController("categoryPortalController")
@CacheConfig(cacheNames = RedisCacheNames.CATEGORY)
@RequestMapping("/operation")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    @Cacheable
    public Result listCategory(@RequestParam Map<String, Object> params) {
        List<Category> categoryList = categoryService.listCategory(params);
        return Result.ok().put("categoryList", categoryList);
    }
}
