package com.dhao.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhao.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author dhao
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getCommentPage(Page<EduComment> commentPage,String courseId);
}
