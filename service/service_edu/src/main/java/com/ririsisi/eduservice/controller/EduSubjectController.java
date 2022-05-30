package com.ririsisi.eduservice.controller;


import com.ririsisi.commonutils.R;
import com.ririsisi.eduservice.entity.subject.OneSubject;
import com.ririsisi.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-28
 */
@Api(description = "课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {

        // 上传的excel文件
        subjectService.saveSubject(file,subjectService);

        return R.ok();

    }

    // 课程分类列表（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject() {

        List<OneSubject> list = subjectService.getAllOneTwoSubjects();

        return R.ok().data("list",list);
    }











}

