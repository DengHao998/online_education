package com.dhao.eduservice.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dhao
 */
public class TestEasyExcel {

    public static void main(String[] args) {
        String fileName="F:\\write.xlsx";
//        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getLists());
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }
    //创建方法返回List集合
    private static List<DemoData> getLists(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("dhao ："+ i);
            list.add(demoData);
        }
        return list;
    }
}
