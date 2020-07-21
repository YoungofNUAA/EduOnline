package com.young.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //实现Excel写操作
        String filename = "F:\\EduOnLine\\write.xlsx";
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());

        EasyExcel.read(filename,DemoData.class,new ExcelListenner()).sheet().doRead();
    }

    public static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("Tom"+i);
            list.add(data);
        }
        return list;
    }
}
