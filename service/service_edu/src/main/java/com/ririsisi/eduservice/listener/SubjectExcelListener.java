package com.ririsisi.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ririsisi.eduservice.entity.EduSubject;
import com.ririsisi.eduservice.entity.excel.SubjectData;
import com.ririsisi.eduservice.service.EduSubjectService;
import com.ririsisi.servicebase.excptionHandler.GuliException;

/**
 * @Author ririsisi
 * @Version 1.0
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // SubjectExcelListener不能交由spring进行管理，需要自己创建对象，不能注入其他对象
    // 不能通过service，mapper实现数据库操作
    // 利用有参构造手动注入设值
    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 一行行读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {

        if (subjectData == null) {
            throw new GuliException(20001,"文件数据为空");
        }

        // 一行一行读取，每次读取两个值，第一个值，一级分类，第二个值，二级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());

        // 判断一级分类是否重复
        if (existOneSubject == null) { // 没有一级分类，进行添加

            existOneSubject = new EduSubject(); // 创建对象
            existOneSubject.setParentId("0"); // 设置一级分类id
            existOneSubject.setTitle(subjectData.getOneSubjectName()); // 设置一级id名称
            subjectService.save(existOneSubject); // 将一级分类存入数据库

        }

        // 获取一级分类对象的id值
        String pid = existOneSubject.getId();
        // 一行一行读取，每次读取两个值，第一个值，一级分类，第二个值，二级分类
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);

        // 判断二级分类是否重复
        if (existTwoSubject == null) { // 没有一级分类，进行添加

            existTwoSubject = new EduSubject(); // 创建对象
            existTwoSubject.setParentId(pid); // 设置一级分类对应的id
            existTwoSubject.setTitle(subjectData.getTwoSubjectName()); // 设置一级id名称
            subjectService.save(existTwoSubject); // 将一级分类存入数据库

        }

    }

    // 判断一级分类不能重复添加
    private EduSubject existOneSubject( EduSubjectService subjectService, String name) {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;

    }

    // 判断二级分类不能重复添加
    private EduSubject existTwoSubject( EduSubjectService subjectService, String name, String pid) {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
