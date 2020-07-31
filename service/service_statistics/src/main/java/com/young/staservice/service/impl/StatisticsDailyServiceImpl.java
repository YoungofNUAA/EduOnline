package com.young.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.young.commonutils.R;
import com.young.staservice.client.UcenterClient;
import com.young.staservice.entity.StatisticsDaily;
import com.young.staservice.mapper.StatisticsDailyMapper;
import com.young.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-30
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(RandomUtils.nextInt(50,100));
        sta.setDateCalculated(day);
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(sta);
    }

    //图表显示  日期json数组  数据json数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);  //指定显示结果的字段

        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        System.out.println("*************"+staList);

        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        //遍历staList
        for(int i=0;i<staList.size();i++){
            StatisticsDaily daily = staList.get(i);
            date_calculatedList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
