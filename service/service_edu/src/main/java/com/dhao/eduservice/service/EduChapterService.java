package com.dhao.eduservice.service;

import com.dhao.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhao.eduservice.entity.vo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dhao
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeVideoByCourseId(String id);
}
