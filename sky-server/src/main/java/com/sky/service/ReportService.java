package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


public interface ReportService {
    TurnoverReportVO TurnOverStatistics(LocalDate begin, LocalDate end);

    UserReportVO UserStatistics(LocalDate begin, LocalDate end);

    OrderReportVO OrderStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO top10Statistics(LocalDate begin, LocalDate end);
}
