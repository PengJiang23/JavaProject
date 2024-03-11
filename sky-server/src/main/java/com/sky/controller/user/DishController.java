package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "分类相关接口")
public class DishController {


    @Autowired
    DishService dishService;

    @Autowired
    RedisTemplate redisTemplate;

    // todo 需要看一下具体逻辑
    @GetMapping("/list")
    @ApiOperation("根据类型查询菜品及对应口味")

    public Result<List<DishVO>> searchDishByType(Long categoryId) {

        /**
         * 1.查询redis缓存是否有数据
         * 2.不存在，则数据库查询，存入缓存
         * 3.存在，缓存查询
         */
         String key = "dish_" + categoryId;

        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (list != null && list.size() > 0) {
            return Result.success(list);
        } else {
            Dish dish = new Dish();
            dish.setCategoryId(categoryId);
            dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品
            list = dishService.listWithFlavor(dish);

            //存入redis缓存
            redisTemplate.opsForValue().set(key, list);
            return Result.success(list);
        }

    }

}
