package com.young.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.young.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.young.eduservice.entity.frontvo.CourseFrontVo;
import com.young.eduservice.entity.frontvo.CourseWebVo;
import com.young.eduservice.entity.vo.CourseInfoVo;
import com.young.eduservice.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    List<EduCourse> getHotCourse();

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
