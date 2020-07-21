package com.young.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.young.eduservice.entity.EduSubject;
import com.young.eduservice.entity.excel.SubjectData;
import com.young.eduservice.entity.subject.OneSubject;
import com.young.eduservice.entity.subject.TwoSubject;
import com.young.eduservice.listenner.SubjectExcelListenner;
import com.young.eduservice.mapper.EduSubjectMapper;
import com.young.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListenner(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");

        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //查询二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");

        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //封装一级分类
        List<OneSubject> finalSubjectList = new ArrayList<>();

        for(int i=0;i<oneSubjectList.size();i++){
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            //封装二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for(int m=0;m<twoSubjectList.size();m++){
                EduSubject tSubject = twoSubjectList.get(m);
                //判断二级分类和一级分类的id
                if(tSubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
