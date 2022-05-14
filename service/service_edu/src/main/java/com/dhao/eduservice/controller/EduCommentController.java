package com.dhao.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhao.commonutils.JwtUtils;
import com.dhao.commonutils.R;
import com.dhao.commonutils.UcenterMemberVo;
import com.dhao.eduservice.client.UcenterClient;
import com.dhao.eduservice.entity.EduComment;
import com.dhao.eduservice.service.EduCommentService;
import com.dhao.servicebase.ExceptionHandler.HaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author dhao
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/comment")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;


    @GetMapping("/getCommentPage/{page}/{limit}")
    public R getCommentPage(@PathVariable("page") long page,@PathVariable("limit") long limit,String courseId){
        Page<EduComment> commentPage = new Page<>(page, limit);
        Map<String,Object> map=commentService.getCommentPage(commentPage,courseId);
        return R.ok().data(map);
    }


    //添加评论
    @PostMapping("/addComment")
    public R addComment(HttpServletRequest request, @RequestBody EduComment eduComment){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //判断用户是否登录
        if (StringUtils.isEmpty(memberId)){
            throw new HaoException(20001,"请先登录");
        }
        eduComment.setMemberId(memberId);

        //远程调用ucenter根据用户id获取用户信息
        UcenterMemberVo memberVo = ucenterClient.getMemberInfoById(memberId);

        eduComment.setAvatar(memberVo.getAvatar());
        eduComment.setNickname(memberVo.getNickname());

        //保存评论
        commentService.save(eduComment);

        return R.ok();
    }
}

