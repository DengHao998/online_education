package com.dhao.eduorder.client;

import com.dhao.commonutils.order.EduCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: dhao
 */

@FeignClient(name = "service-edu",fallback = EduClientImpl.class)
public interface EduClient {

    //根据课程id，查询课程信息【订单】
    @PostMapping("/eduservice/coursefront/getCourseInfoByIdOrder/{courseId}")
    public EduCourseVo getCourseInfoByIdOrder(@PathVariable("courseId") String courseId);


    //购买课程后，课程购买数加一
    @GetMapping("/eduservice/coursefront/updateBuyCount/{courseId}")
    public void updateBuyCount(@PathVariable("courseId") String courseId);
}
