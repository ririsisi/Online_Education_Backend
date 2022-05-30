package com.ririsisi.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ririsisi
 * @Version 1.0
 */
public class TestEasyExcel {

    public static void main(String[] args) {

        // 实现excel 写 操作
        // 1 设置写入文件夹地址 与 excel文件的名称
        String fileName = "E:\\sucai\\write.xlsx";

        // 调用easyExcel实现 写 操作
        // 第一个参数，文件路径名称，第二个参数实体类class
        EasyExcel.write(fileName,DemoDate.class).sheet("学生列表").doWrite(getData());

    }

    // 创建方法返回List集合
    private static List<DemoDate> getData() {

        ArrayList<DemoDate> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            DemoDate demo = new DemoDate();
            demo.setSname("Nini" + i);
            demo.setSno(i);
            list.add(demo);

        }

        return list;
    }
}
