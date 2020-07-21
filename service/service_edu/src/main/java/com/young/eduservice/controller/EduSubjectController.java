package com.young.eduservice.controller;


import com.young.commonutils.R;
import com.young.eduservice.entity.subject.OneSubject;
import com.young.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){

        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类列表 树形结构
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

