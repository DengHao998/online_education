package com.dhao.eduservice.service;

import com.dhao.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhao.eduservice.entity.vo.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author dhao
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
