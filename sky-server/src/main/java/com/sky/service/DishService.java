package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    void addDish(DishDTO dishDTO);


    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void updateStatus(Integer status, Long id);

    void deleteBatch(List<Long> ids);

    DishVO DishById(Long id);

    void updateWithFlavor(DishDTO dishDTO);
}
