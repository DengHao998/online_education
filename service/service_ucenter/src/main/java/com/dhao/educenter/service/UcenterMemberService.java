package com.dhao.educenter.service;

import com.dhao.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhao.educenter.entity.vo.LoginVo;
import com.dhao.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author dhao
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember getMemberByOpenId(String openid);

    Integer getCountRegister(String day);
}
