package com.sky.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserMapper userMapper;


    /**
     * 营业额统计
     * 1.查询给定日期内的每一天内所有已完成状态的订单，统计金额
     * 2.返回结果日期list和营业额list
     */

    public TurnoverReportVO TurnOverStatistics(LocalDate begin, LocalDate end) {

        List<LocalDate> localDateList = DateList(begin, end);

        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : localDateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("status", Orders.COMPLETED);
            map.put("begin", beginTime);
            map.put("end", endTime);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(localDateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }


    /**
     * 用户统计
     */

    public UserReportVO UserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> localDateList = DateList(begin, end);

        List<Integer> userList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        for (LocalDate date : localDateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Integer newUser = getUserCount(beginTime, endTime);
            Integer totalUser = getUserCount(null, endTime);

            userList.add(newUser);
            totalUserList.add(totalUser);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(localDateList, ","))
                .newUserList(StringUtils.join(userList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }

    private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
        Map map = new HashMap();
        map.put("begin", beginTime);
        map.put("end", endTime);
        return userMapper.countByMap(map);
    }

    /**
     * 订单统计
     */
    public OrderReportVO OrderStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> localDateList = DateList(begin, end);
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();

        for (LocalDate date : localDateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Integer orderCount = getOrderCount(beginTime, endTime, null);
            Integer validCount = getOrderCount(beginTime, endTime, Orders.COMPLETED);

            orderCountList.add(orderCount);
            validOrderCountList.add(validCount);
        }

        //total计算采用stream
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        Integer totalValidCount = validOrderCountList.stream().reduce(Integer::sum).get();

        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = totalValidCount.doubleValue() / totalOrderCount;
        }
        return OrderReportVO.builder()
                .dateList(StringUtils.join(localDateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(totalValidCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }


    private Integer getOrderCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status) {
        Map map = new HashMap();
        map.put("begin", beginTime);
        map.put("end", endTime);
        map.put("status", status);
        return orderMapper.countByMap(map);
    }

    /**
     * top10
     */

    public SalesTop10ReportVO top10Statistics(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);

        String nameList = StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList()), ",");
        String numberList = StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList()), ",");

        return SalesTop10ReportVO.builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 构造日期list
     *
     * @param begin
     * @param end
     * @return
     */
    public List<LocalDate> DateList(LocalDate begin, LocalDate end) {
        List<LocalDate> datelist = new ArrayList<>();
        datelist.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            datelist.add(begin);
        }

        return datelist;
    }


}



