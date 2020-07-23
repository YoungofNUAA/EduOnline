package com.young.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.young.eduservice.client.VodClient;
import com.young.eduservice.entity.EduVideo;
import com.young.eduservice.mapper.EduVideoMapper;
import com.young.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;
    //TODO删除小节  对应删除视频文件
    @Override
    public void removeVideoByCourseId(String courseId) {
        //查询课程的所有视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        List<String> videoIds = new ArrayList<>();
        for(int i=0;i<eduVideoList.size();i++){
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSoyrceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSoyrceId)){
                videoIds.add(videoSoyrceId);
            }
        }
        if(videoIds.size()>0){
            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
