package com.young.eduservice.service;

import com.young.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.young.eduservice.entity.vo.CourseInfoVo;
import com.young.eduservice.entity.vo.CoursePublishVo;

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
}
