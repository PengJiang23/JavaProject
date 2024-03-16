package com.sky.controller.admin;


import com.sky.dto.*;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "订单相关接口")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;


    /**
     * 订单搜索
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> SerchOrders(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult result = orderService.PageQuery(ordersPageQueryDTO);
        return Result.success(result);
    }


    /**
     * 查询订单
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查询订单")
    public Result GetOrders(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.GetById(id);
        return Result.success(orderVO);
    }


    /**
     * 统计各个状态的订单数量
     */

    @GetMapping("/statistics")
    @ApiOperation("统计订单")
    public Result<OrderStatisticsVO> statisticsVOResult(){
       OrderStatisticsVO orderStatisticsVO  =orderService.StatisticsOrder();
        return Result.success(orderStatisticsVO);
    }


    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result ConfirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result RejectionOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO){
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation("拒单")
    public Result CancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO){
        orderService.Cancel(ordersCancelDTO);
        return Result.success();
    }



    @PutMapping("/delivery/{id}")
    @ApiOperation("派送")
    public Result delivery(@PathVariable("id") Long id) {
        orderService.delivery(id);
        return Result.success();
    }


    /**
     * 完成订单
     *
     * @return
     */
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result complete(@PathVariable("id") Long id) {
        orderService.complete(id);
        return Result.success();
    }

}
