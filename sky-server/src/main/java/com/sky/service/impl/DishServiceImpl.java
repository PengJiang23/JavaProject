package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishMapper;


    @Override
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        DishFlavor dishFlavor = new DishFlavor();
        BeanUtils.copyProperties(dishDTO, dish);
        BeanUtils.copyProperties(dishDTO, dishFlavor);

//        dishMapper.insertDish(dish);
//        dishMapper.insertDishFlavor(dishFlavor);

    }
}
