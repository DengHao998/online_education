package com.dhao.educms.controller;

import com.dhao.commonutils.R;
import com.dhao.educms.entity.CrmBanner;
import com.dhao.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: dhao
 */
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有幻灯片
    @GetMapping("getAllBanner")
    public R getAll() {
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("list", list);
    }
}
