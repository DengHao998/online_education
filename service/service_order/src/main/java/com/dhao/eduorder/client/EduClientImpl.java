package com.dhao.eduorder.client;

import com.dhao.commonutils.order.EduCourseVo;
import org.springframework.stereotype.Component;

/**
 * @Author: dhao
 */
@Component
public class EduClientImpl implements EduClient {
    @Override
    public EduCourseVo getCourseInfoByIdOrder(String courseId) {
        return null;
    }

    @Override
    public void updateBuyCount(String courseId) {

    }
}
