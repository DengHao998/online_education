package com.dhao.msmservice.service;

import java.util.HashMap;

/**
 * @Author: dhao
 */
public interface MsmService {
    boolean sendMsm(HashMap<String, Object> map, String phone);
}
