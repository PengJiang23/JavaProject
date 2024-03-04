package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    void insertCategory(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void setStatus(Long id, Integer status);

    void editCategory(CategoryDTO categoryDTO);

    void deleteCategory1(Long id);

    List<Category> searchByType(Integer type);
}
