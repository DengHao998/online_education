package com.dhao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhao.eduservice.entity.EduChapter;
import com.dhao.eduservice.entity.EduVideo;
import com.dhao.eduservice.entity.vo.chapter.ChapterVo;
import com.dhao.eduservice.entity.vo.chapter.VideoVo;
import com.dhao.eduservice.mapper.EduChapterMapper;
import com.dhao.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhao.eduservice.service.EduVideoService;
import com.dhao.servicebase.ExceptionHandler.HaoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author dhao
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> videoList = videoService.list(videoWrapper);
        ArrayList<ChapterVo> chapterVoList = new ArrayList<>();
        for (int i = 0; i < chapterList.size(); i++) {
            EduChapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            ArrayList<VideoVo> videoVoList = new ArrayList<>();
            for (int m = 0; m < videoList.size(); m++) {
                EduVideo video = videoList.get(m);
                if(chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapter章节id 查询查询小节表，如果查询有数据，则不删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if (count>0){
            //能查询出来小节，不进行删除
            throw new HaoException(20001,"还有小节数据，不能删除");
        }else {
            //不能查询出小节，进行删除
            int delete = baseMapper.deleteById(chapterId);
            //没有小节的情况下是否成功删除了
            return delete>0;
        }
    }

    @Override
    public void removeVideoByCourseId(String id) {
        QueryWrapper<EduChapter> chapterWrapper=new QueryWrapper<>();
        chapterWrapper.eq("course_id",id);
        baseMapper.delete(chapterWrapper);
    }

}
