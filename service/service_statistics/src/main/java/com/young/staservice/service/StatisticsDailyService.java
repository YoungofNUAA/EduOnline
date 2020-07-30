package com.young.staservice.service;

import com.young.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-30
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);
}
