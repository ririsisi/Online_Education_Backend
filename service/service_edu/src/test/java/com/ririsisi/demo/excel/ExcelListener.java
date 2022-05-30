package com.ririsisi.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @Author ririsisi
 * @Version 1.0
 */
public class ExcelListener extends AnalysisEventListener<DemoDate> {

    // 一行行读取excel内容
    @Override
    public void invoke(DemoDate demoDate, AnalysisContext analysisContext) {
        System.out.println("***" + demoDate);
    }

    // 读取表头内容
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }

    // 读取完成后操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
