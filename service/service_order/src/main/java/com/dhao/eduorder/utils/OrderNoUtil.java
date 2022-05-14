package com.dhao.eduorder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: dhao
 */
public class OrderNoUtil {

    public static String getOrderNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
        String newData = sdf.format(new Date());
        String result="";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result+=random.nextInt(10);
        }
        return newData+result;
    }
}
