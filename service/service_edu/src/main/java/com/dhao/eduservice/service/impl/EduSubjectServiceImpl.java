package com.dhao.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhao.eduservice.entity.EduSubject;
import com.dhao.eduservice.entity.excel.SubjectData;
import com.dhao.eduservice.entity.vo.subject.OneSubject;
import com.dhao.eduservice.entity.vo.subject.TwoSubject;
import com.dhao.eduservice.listener.SubjectExcelListener;
import com.dhao.eduservice.mapper.EduSubjectMapper;
import com.dhao.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhao.servicebase.ExceptionHandler.HaoException;
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
 * @author dhao
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new HaoException(20002,"添加课程分类失败");
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);
        //查询二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);
        //封装一级分类
        ArrayList<OneSubject> finalOneSubjectList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject oneSubject = oneSubjectList.get(i);
            OneSubject finalOneSubject = new OneSubject();
            BeanUtils.copyProperties(oneSubject,finalOneSubject);
            finalOneSubjectList.add(finalOneSubject);
            //封装二级分类
            ArrayList<TwoSubject> finalTwoSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject twoSubject = twoSubjectList.get(m);
                if(twoSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject finalTwoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject,finalTwoSubject);
                    finalTwoSubjectList.add(finalTwoSubject);
                }
            }
            finalOneSubject.setChildren(finalTwoSubjectList);
        }
        return finalOneSubjectList;
    }
}
