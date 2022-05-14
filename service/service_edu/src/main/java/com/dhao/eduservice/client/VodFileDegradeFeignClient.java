package com.dhao.eduservice.client;

import com.dhao.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: dhao
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAliyunVideoById(String id) {
        return R.error().code(20001).message("删除单个视频失败");
    }

    @Override
    public R removeBatch(List<String> videoIdList) {
        return R.error().code(20001).message("删除多个视频失败");
    }
}
