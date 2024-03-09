package com.sky.controller.admin;


import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺操作相关接口")
@Slf4j
public class ShopController {

    @Autowired
    RedisTemplate redisTemplate;



    @GetMapping("/status")
    @ApiOperation("获取店铺状态")
    public Result<Integer> getShopStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(StatusConstant.KEY);
        return Result.success(status);
    }


    @PutMapping("/{status}")
    @ApiOperation("设置店铺状态")
    public Result setShopStatus(@PathVariable Integer status){
        redisTemplate.opsForValue().set(StatusConstant.KEY, status);
        return Result.success();
    }





}
