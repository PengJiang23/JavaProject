package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);



    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} where id = #{id}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, Long id);


    @Select("select * from orders where id = #{id}")
    Orders GetById(Long id);


    Page<Orders> GetPageQuery(OrdersPageQueryDTO ordersPageQueryDTO);


    @Select("select count(*) from orders where status = #{status}")
    Integer StatisticsStatus(Integer status);


    /**
     * 定时任务
     */

    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getBystatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);


}
