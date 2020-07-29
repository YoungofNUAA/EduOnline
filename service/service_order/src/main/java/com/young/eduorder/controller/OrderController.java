package com.young.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.young.commonutils.JwtUtils;
import com.young.commonutils.R;
import com.young.eduorder.entity.Order;
import com.young.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        String orderNo = orderService.createOrders(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }


    //根据订单id查询订单信息
    @GetMapping("getOrderInfo/{id}")
    public R getOrderInfo(@PathVariable String id){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",id);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }
}

