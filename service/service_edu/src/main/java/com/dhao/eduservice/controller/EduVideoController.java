package com.dhao.eduservice.controller;


import com.dhao.commonutils.R;
import com.dhao.eduservice.client.VodClient;
import com.dhao.eduservice.entity.EduVideo;
import com.dhao.eduservice.service.EduVideoService;
import com.dhao.servicebase.ExceptionHandler.HaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author dhao
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService videoService;

    @Autowired
    VodClient vodClient;


    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节，视频(添加课程大纲中)
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteById(@PathVariable("id") String id){
        //查询云端视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节中是否有对应的视频文件
        if (!StringUtils.isEmpty(videoSourceId)){
            //有就删除
           R result= vodClient.removeAliyunVideoById(videoSourceId);
           if(result.getCode()==20001){
               throw new HaoException(20001,"删除视频失败，请检查服务器运行状态");
           }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }


    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //根据小节id查询
    @GetMapping("/getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }

}

