package com.dhao.eduservice.client;

import com.dhao.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: dhao
 */

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAliyunVideoById/{id}")
    R removeAliyunVideoById(@PathVariable("id") String id);

    //删除多个视频
    @DeleteMapping("/eduvod/video/removeBatch")
    R removeBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
