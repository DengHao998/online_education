package com.dhao.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhao.commonutils.R;
import com.dhao.eduservice.entity.EduCourse;
import com.dhao.eduservice.entity.vo.CourseInfoVo;
import com.dhao.eduservice.entity.vo.CoursePublishVo;
import com.dhao.eduservice.entity.vo.CourseQuery;
import com.dhao.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dhao
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //课程列表 基本实现
    @GetMapping("/getCourseList")
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //多条件查询课程带分页
    @ApiOperation(value = "多条件查询课程带分页")
    @PostMapping("/pageCourseCondition/{page}/{limit}")
    public R pageCourseCondition(@ApiParam(name = "page", value = "当前页码", required = true)@PathVariable Long page,
                                 @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable Long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery){//通过封装courseQuery对象来直接传递查询条件
        //创建分页page对象
        Page<EduCourse> pageParam = new Page<>(page, limit);
        //调用方法实现多条件分页查询
        courseService.pageQuery(pageParam,courseQuery);
        //获取查询到的数据
        List<EduCourse> records = pageParam.getRecords();
        //获取总记录数
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }


    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.saveCourseInfo(courseInfoVo);
        //返回课程id，为添加课程大纲使用
        return R.ok().data("courseId",id);
    }


    //根据课程id查询课程基本信息
    @GetMapping("/getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }


    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }


    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo publishCourseInfo = courseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse",publishCourseInfo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus("Normal"); //设置课程发布状态
        eduCourse.setId(id);
        boolean flag = courseService.updateById(eduCourse);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


    //课程列表中删除课程方法
    @DeleteMapping("/deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable String id){
        boolean flag = courseService.removeCourse(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

