package com.dhao.staservice.schedule;

import com.dhao.staservice.service.StatisticsDailyService;
import com.dhao.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: dhao
 */

@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService staService;

    //在每天凌晨1点执行方法，把前一天的数据查询进行添加
    @Scheduled(cron = "0 0 1 * * ? ")//指定cron表达式规则
    public void task02(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }

}

