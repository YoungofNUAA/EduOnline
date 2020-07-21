package com.young.eduservice.listenner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.young.eduservice.entity.EduSubject;
import com.young.eduservice.entity.excel.SubjectData;
import com.young.eduservice.service.EduSubjectService;
import com.young.servicebase.exceptionhandler.GuliException;

public class SubjectExcelListenner extends AnalysisEventListener<SubjectData> {

    //SubjectExcelListenner无法交给spring管理
    // 不能实现数据库操作
    public EduSubjectService subjectService;

    public SubjectExcelListenner(){}
    public SubjectExcelListenner(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }

        //第一个值1级分类  第二个值 2级分类
        EduSubject exitOneSubject = this.exitOneSubject(subjectService, subjectData.getOneSubjectName());
        if(exitOneSubject == null){
            exitOneSubject = new EduSubject();
            exitOneSubject.setParentId("0");
            exitOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(exitOneSubject);
        }

        //二阶分类添加
        String pid = exitOneSubject.getId();
        EduSubject exitTwoSubject = this.exitTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(exitTwoSubject == null){
            exitTwoSubject = new EduSubject();
            exitTwoSubject.setParentId(pid);
            exitTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(exitTwoSubject);
        }
    }

    //判断1级分类不能重复添加
    private EduSubject exitOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断2级分类

    private EduSubject exitTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
