package com.sky.controller.user;


import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址薄相关接口")
@Slf4j
public class AddressBookController {


    @Autowired
    AddressBookService addressBookService;



    @PostMapping
    @ApiOperation("新增地址")
    public Result AddAddress(@RequestBody AddressBook addressBook){

        addressBookService.addAddressBook(addressBook);
        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation("查询用户的所有地址")
    public Result<List<AddressBook>> GetUersAllAddress(){
        List<AddressBook> list = addressBookService.GetUersAllAddress(BaseContext.getCurrentId());
        return Result.success(list);
    }


    @GetMapping("default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> GetDafaultAddress(){
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        return Result.success(addressBookService.GetDefaultById(addressBook));
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> GetById(@PathVariable Long id){
        AddressBook addressBook = addressBookService.GetById(id);
        return Result.success(addressBook);
    }


    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result updateById(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return Result.success();
    }


    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteById(Long id){
        addressBookService.deleteById(id);
        return Result.success();
    }


    @PutMapping("default")
    @ApiOperation("设置默认地址")
    public Result SetDefault(@RequestBody AddressBook addressBook) {
        addressBookService.SetDafaultById(addressBook);
        return Result.success();
    }
}
