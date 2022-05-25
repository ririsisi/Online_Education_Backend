package com.ririsisi.eduservice.controller;


import com.ririsisi.eduservice.entity.EduTeacher;
import com.ririsisi.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    // 注入service
    @Autowired
    private EduTeacherService teacherService;

    // 查询讲师表的所有数据
    // rest风格
    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher() {
        // 调用Service里方法
        List<EduTeacher> list = teacherService.list(null);
        return list;
    }

}

