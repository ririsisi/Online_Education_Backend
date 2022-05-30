package com.ririsisi.demo.excel;

import com.alibaba.excel.EasyExcel;

/**
 * @Author ririsisi
 * @Version 1.0
 */
public class TestExcelRead {

    public static void main(String[] args) {

        // 实现excel 读 操作
        // 1 设置写入文件夹地址 与 excel文件的名称
        String fileName = "E:\\sucai\\write.xlsx";
        EasyExcel.read(fileName,DemoDate.class,new ExcelListener()).sheet().doRead();

    }


}
