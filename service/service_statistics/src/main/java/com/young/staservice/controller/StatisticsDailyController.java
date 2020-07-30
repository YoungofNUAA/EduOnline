package com.young.staservice.controller;


import com.young.commonutils.R;
import com.young.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-30
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        staService.registerCount(day);
        return R.ok();
    }
}

