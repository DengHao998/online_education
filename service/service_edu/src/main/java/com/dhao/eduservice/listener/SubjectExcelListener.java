package com.dhao.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhao.eduservice.entity.EduSubject;
import com.dhao.eduservice.entity.excel.SubjectData;
import com.dhao.eduservice.service.EduSubjectService;
import com.dhao.servicebase.ExceptionHandler.HaoException;

/**
 * @Author: dhao
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new HaoException(20001,"数据为空");
        }
        EduSubject oneSubject = existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(oneSubject==null){
            oneSubject=new EduSubject();
            oneSubject.setTitle(subjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            subjectService.save(oneSubject);
        }
        String pId = oneSubject.getId();
        EduSubject twoSubject = existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pId);
        if(twoSubject==null){
            twoSubject=new EduSubject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(pId);
            subjectService.save(twoSubject);
        }

    }

    private EduSubject existOneSubject(EduSubjectService subjectService,String subjectName){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",subjectName);
        queryWrapper.eq("parent_id","0");
        EduSubject eduSubject = subjectService.getOne(queryWrapper);
        return eduSubject;
    }

    private EduSubject existTwoSubject(EduSubjectService subjectService,String subjectName,String pId){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",subjectName)
                    .eq("parent_id",pId);
        EduSubject eduSubject = subjectService.getOne(queryWrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
