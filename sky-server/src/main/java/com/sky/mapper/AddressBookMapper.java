package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    @Insert("insert into address_book (user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label,is_default) " +
            "value (#{userId},#{consignee},#{sex}, #{phone}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label},#{isDefault})")
    void insertAddress(AddressBook addressBook);

    List<AddressBook> GetByUserId(Long userId);



    @Select("select * from address_book where user_id = #{userId} and is_default = #{isDefault}")
    AddressBook GetDefaultById(AddressBook addressBook);


    @Select("select * from address_book where id = #{id}")
    AddressBook GetById(Long id);


    void updateById(AddressBook addressBook);

    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);


    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateDefault(AddressBook build);
}
