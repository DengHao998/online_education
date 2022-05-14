package com.dhao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhao.commonutils.R;
import com.dhao.eduservice.client.VodClient;
import com.dhao.eduservice.entity.EduVideo;
import com.dhao.eduservice.mapper.EduVideoMapper;
import com.dhao.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhao.servicebase.ExceptionHandler.HaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author dhao
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;

    //根据课程id删除小节，视频
    @Override
    public void removeVideoByCourseId(String id) {
        //根据课程id查询课程里面的所有视频
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        List<String> list = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            if(!StringUtils.isEmpty(eduVideo)){
                String sourceId = eduVideo.getVideoSourceId();
                if (!StringUtils.isEmpty(sourceId)){
                    list.add(sourceId);
                }
                //根据多个视频id，删除多个视频
                if (list.size()>0){
                    R result = vodClient.removeBatch(list);
                    if(result.getCode()==20001){
                        throw new HaoException(20001,"删除视频失败，请检查服务器运行状态");
                    }
                }
            }
         }


        //删除小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",id);
        baseMapper.delete(videoWrapper);
    }

}
