package com.young.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.young.commonutils.R;
import com.young.eduservice.entity.EduCourse;
import com.young.eduservice.entity.EduTeacher;
import com.young.eduservice.service.EduCourseService;
import com.young.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("index")
    public R index(){
       List<EduCourse> eduList = eduCourseService.getHotCourse();
       List<EduTeacher> teacherList = eduTeacherService.getHotTeacher();
        //查询前8个热门课程
//        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("id");
//        wrapper.last("limit 8");
//        List<EduCourse> eduList = eduCourseService.list(wrapper);

        //查询前4个讲师
//        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
//        wrapper1.orderByDesc("id");
//        wrapper1.last("limit 4");
//        List<EduTeacher> teacherList = eduTeacherService.list(wrapper1);
        return R.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
