package com.dhao.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhao.commonutils.R;
import com.dhao.eduorder.client.EduClient;
import com.dhao.eduorder.client.UcenterClient;
import com.dhao.eduorder.entity.Order;
import com.dhao.eduorder.entity.PayLog;
import com.dhao.eduorder.service.OrderService;
import com.dhao.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author dhao
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EduClient eduClient;


    //根据订单号，生成微信支付二维码的接口
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回信息，包含二维码地址、其他信息
        Map<String, Object> map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }


    //根据订单号查询订单支付状态并更新状态添加记录
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){

        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map==null){
            return R.error().message("支付出错了");
        }
        //如果返回的map不为空，通过map获取订单的状态
        if (map.get("trade_state").equals("SUCCESS")){ //支付成功
            //添加记录到支付表里，并更新订单表的状态
            payLogService.updateOrderStatus(map);
            //课程购买数加一
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);
            eduClient.updateBuyCount(order.getCourseId());
            return R.ok().message("支付成功");
        }

        return R.ok().code(25000).message("支付中");
    }
}

