package com.ririsisi.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ririsisi.eduservice.entity.EduSubject;
import com.ririsisi.eduservice.entity.excel.SubjectData;
import com.ririsisi.eduservice.entity.subject.OneSubject;
import com.ririsisi.eduservice.entity.subject.TwoSubject;
import com.ririsisi.eduservice.listener.SubjectExcelListener;
import com.ririsisi.eduservice.mapper.EduSubjectMapper;
import com.ririsisi.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-28
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {

        try {
            // 获取文件输入流
            InputStream in = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubjects() {

        // 查询所有一级分类 SELECT * FROM edu_subject WHERE parent_id = ?
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();

        wrapperOne.eq("parent_id","0");

        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();

        wrapperTwo.ne("parent_id","0");

        List<EduSubject> twoSubjectList = this.list(wrapperTwo);

        // 创建一个集合，承载要返回的封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        // 封装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 得到每个对象
            EduSubject oneSubject = oneSubjectList.get(i);

            // 创建一个一级分类对象
            OneSubject oSubject = new OneSubject();
            // 将eduSubject里面的值取出，存入oneSubject对象中
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(oneSubject, oSubject);

            // 将一级分类添加到最终要返回的数据集合
            finalSubjectList.add(oSubject);

            // 在一级分类循环中遍历二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            // 遍历二级分类list集合
            for (int j = 0; j < twoSubjectList.size(); j++) {
                // 获取每个二级分类
                EduSubject twoSubject = twoSubjectList.get(j);

                // 如果二级分类的Id值与一级分类的id值一致，那么把该二级分类放在一级分类下
                // 判断二级分类和一级分类是否一致
                if (twoSubject.getParentId().equals(oSubject.getId())) {

                    // 创建一个二级分类对象
                    TwoSubject tSubject = new TwoSubject();
                    // 将EduSubject twoSubject的值复制给TwoSubject tSubject
                    BeanUtils.copyProperties(twoSubject,tSubject);

                    // 将TwoSubject tSubject 添加到List<TwoSubject> twoFinalSubjectList中
                    twoFinalSubjectList.add(tSubject);

                }
            }
            // 将一级分类所有的二级分类放到一级分类下面去
            oSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }
}
