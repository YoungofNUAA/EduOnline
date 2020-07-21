package com.young.eduservice.controller;


import com.young.commonutils.R;
import com.young.eduservice.client.VodClient;
import com.young.eduservice.entity.EduVideo;
import com.young.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;


    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //删除小节对应的阿里云视频TODO-------
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id--->视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoId)){
            vodClient.removeAliyunVideo(videoId); //删除视频
        }
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
}

