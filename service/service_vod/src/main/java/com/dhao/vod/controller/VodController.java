package com.dhao.vod.controller;

import com.dhao.servicebase.ExceptionHandler.HaoException;
import com.dhao.vod.service.VodService;
import com.dhao.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags="阿里云视频管理")
@RestController
//@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file){
        //返回上传视频的id
        String videoId = vodService.uploadVideoAliyun(file);
        return R.ok().data("videoId",videoId);
    }


    //根据视频id删除阿里云视频
    @DeleteMapping("/removeAliyunVideoById/{id}")
    public R removeAliyunVideoById(@PathVariable String id){
        vodService.removeAliyunVideoById(id);
        return R.ok();
    }

    //根据id删除多个阿里云视频
    @DeleteMapping("/removeBatch")
    public R removeBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreVideo(videoIdList);
        return R.ok();
    }


    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid){
        try {
            String playAuth = vodService.getPlayAuth(vid);
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HaoException(20001,"获取视频凭证失败");
        }

    }




}
