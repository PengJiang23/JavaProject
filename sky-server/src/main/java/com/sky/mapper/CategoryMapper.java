package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category (name, type, sort, status, create_time, update_time, create_user, update_user)" +
            "values " +
            "(#{name}, #{type}, #{sort}, #{status} ,#{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);


    Page<Category> queryCategoryPage(CategoryPageQueryDTO categoryPageQueryDTO);


    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    void delete(Long id);

    List<Category> queryByType(Integer type);
}
