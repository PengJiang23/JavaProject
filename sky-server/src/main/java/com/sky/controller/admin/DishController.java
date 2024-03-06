package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.impl.DishServiceImpl;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {


    @Autowired
    DishService dishService;


    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        dishService.addDish(dishDTO);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("菜单分页")
    public Result<PageResult> dishPageQuery(DishPageQueryDTO dishPageQueryDTO) {

        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("status/{status}")
    @ApiOperation("菜单状态设置")
    public Result dishSetStatus(@PathVariable Integer status, Long id) {
        dishService.updateStatus(status, id);
        return Result.success();
    }

    /**
     * 修改菜品前置，需要根据id获得菜品
     * @param
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> searchDishById(@PathVariable Long id){
        DishVO dish = dishService.DishById(id);
    return Result.success(dish);
    }


    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO) {
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }


    @DeleteMapping
    @ApiOperation("菜品批量删除")

    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除：{}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
}
