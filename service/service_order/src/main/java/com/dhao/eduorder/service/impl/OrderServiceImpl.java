package com.dhao.eduorder.service.impl;

import com.dhao.commonutils.UcenterMemberVo;
import com.dhao.commonutils.order.EduCourseVo;
import com.dhao.eduorder.client.EduClient;
import com.dhao.eduorder.client.UcenterClient;
import com.dhao.eduorder.entity.Order;
import com.dhao.eduorder.mapper.OrderMapper;
import com.dhao.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhao.eduorder.utils.OrderNoUtil;
import com.dhao.servicebase.ExceptionHandler.HaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author dhao
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用：根据用户id获取用户信息
        if(StringUtils.isEmpty(memberId)){
            throw  new HaoException(20001,"请先登录");
        }
        UcenterMemberVo userInfoOrder = ucenterClient.getMemberInfoById(memberId);

        //通过远程调用：根据课程id获取课程信息
        EduCourseVo courseInfoOrder = eduClient.getCourseInfoByIdOrder(courseId);

        //创建order对象，向order对象里面设置数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());//课程名称
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());

        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1

        baseMapper.insert(order);

        //返回订单号
        return order.getOrderNo();
    }
}

