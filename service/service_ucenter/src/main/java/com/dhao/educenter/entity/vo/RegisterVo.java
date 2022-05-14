package com.dhao.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterVo {

    private String nickname;

    private String mobile;

    private String password;

    @ApiModelProperty("验证码")
    private String code;

}
