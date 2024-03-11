package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {


    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("6根据类型查询分类")

    public Result<List<Category>> searchCategoryByType(Integer type) {

            List<Category> category = categoryService.searchByType(type);
            return Result.success(category);

    }
}
