package com.dhao.eduservice.entity.vo.chapter;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String videoSourceId;

    private String title;

    private Boolean isFree;

}
