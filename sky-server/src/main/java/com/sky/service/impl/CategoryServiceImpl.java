package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;



    @Override
    public void insertCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(StatusConstant.DISABLE);

        categoryMapper.insert(category);

    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        Page<Category> result = categoryMapper.queryCategoryPage(categoryPageQueryDTO);
        long total = result.getTotal();
        List<Category> records = result.getResult();

        return new PageResult(total,records);
    }

    @Override
    public void setStatus(Long id, Integer status) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();

        categoryMapper.update(category);

    }

    @Override
    public void editCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.update(category);
    }

    @Override
    public void deleteCategory1(Long id) {
        categoryMapper.delete(id);
    }

    @Override
    public List<Category> searchByType(Integer type) {
        return categoryMapper.queryByType(type);
    }


}
