package com.dhao.staservice.client;

import com.dhao.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @Author: dhao
 */

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public R countRegister(String day) {
        return R.error().code(2001).message("获取注册人数失败");
    }
}
