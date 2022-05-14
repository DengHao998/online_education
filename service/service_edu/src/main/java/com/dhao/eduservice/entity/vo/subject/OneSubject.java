package com.dhao.eduservice.entity.vo.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dhao
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    private List<TwoSubject> children=new ArrayList<>();
}
