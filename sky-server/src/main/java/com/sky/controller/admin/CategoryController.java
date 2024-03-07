package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    /**
     * 添加分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("1.新增菜品/套餐分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.insertCategory(categoryDTO);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("2.分类分页查询")
    public Result<PageResult> pageCategory(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("3.启用/禁用分类")
    public Result startOrStop(@PathVariable Integer status, Long id){
        categoryService.setStatus(id ,status);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("4.修改分类")
    public Result editCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.editCategory(categoryDTO);
        return Result.success();
    }


    @DeleteMapping
    @ApiOperation("5.删除分类")
    public Result deleteCategory(Long id){

        categoryService.deleteCategory1(id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("6.根据类型查询分类")
    // TODO 类型查询有点问题
    public Result<List<Category>> searchCategoryByType(Integer type){

        List<Category> category = categoryService.searchByType(type);
        return Result.success(category);
    }

}
