package com.young.eduorder.service.impl;

import com.young.eduorder.entity.Order;
import com.young.eduorder.mapper.OrderMapper;
import com.young.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    //生成订单方法
    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {

        return null;
    }
}
