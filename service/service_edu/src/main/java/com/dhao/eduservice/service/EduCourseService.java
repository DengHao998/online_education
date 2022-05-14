package com.dhao.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhao.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhao.eduservice.entity.frontvo.CourseFrontVo;
import com.dhao.eduservice.entity.frontvo.CourseWebVo;
import com.dhao.eduservice.entity.vo.CourseInfoVo;
import com.dhao.eduservice.entity.vo.CoursePublishVo;
import com.dhao.eduservice.entity.vo.CourseQuery;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dhao
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    boolean removeCourse(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
