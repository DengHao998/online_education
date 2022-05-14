package com.dhao.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhao.commonutils.JwtUtils;
import com.dhao.commonutils.R;
import com.dhao.commonutils.order.EduCourseVo;
import com.dhao.eduservice.client.OrderClient;
import com.dhao.eduservice.entity.EduChapter;
import com.dhao.eduservice.entity.EduCourse;
import com.dhao.eduservice.entity.frontvo.CourseFrontVo;
import com.dhao.eduservice.entity.frontvo.CourseWebVo;
import com.dhao.eduservice.entity.vo.chapter.ChapterVo;
import com.dhao.eduservice.service.EduChapterService;
import com.dhao.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: dhao
 */

@RestController
@RequestMapping("eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable("page") long page, @PathVariable("limit") long limit,
                                @RequestBody(required = false)CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(page,limit);
        Map<String,Object> map=courseService.getCourseFrontList(coursePage,courseFrontVo);
        return R.ok().data(map);
    }


    //课程详情的方法
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        boolean isBuyCourse=false;
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id，查询章节和小节信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        if (!StringUtils.isEmpty(memberId)){
            //根据课程id、用户id，查询课程是否已经购买
            isBuyCourse = orderClient.isBuyCourse(memberId, courseId);
        }

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",isBuyCourse);

    }

    //根据课程id，查询课程信息【订单】
    @PostMapping("/getCourseInfoByIdOrder/{courseId}")
    public EduCourseVo getCourseInfoByIdOrder(@PathVariable String courseId){
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(courseId);
        EduCourseVo eduCourseVo = new EduCourseVo();
        BeanUtils.copyProperties(courseInfo,eduCourseVo);
        return eduCourseVo;
    }

    //购买课程后，课程购买数加一
    @GetMapping("/updateBuyCount/{courseId}")
    public void updateBuyCount(@PathVariable("courseId") String courseId){
        EduCourse course = courseService.getById(courseId);
        course.setBuyCount(course.getBuyCount()+1);
        courseService.updateById(course);
    }
}
