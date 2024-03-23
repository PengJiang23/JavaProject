package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Mapper
public interface DishMapper {


    @AutoFill(OperationType.INSERT)
    void insertDish(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);


    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    Dish getById(Long id);

    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    @Select("select * from dish where id = #{id}")
    Dish searchById(Long id);


    @Select("select count(id) from dish where category_id = #{id}")
    Integer countByCategoryId(Long id);

    List<Dish> list(Dish dish);


    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long id);

    Integer countByMap(Map map);
}
