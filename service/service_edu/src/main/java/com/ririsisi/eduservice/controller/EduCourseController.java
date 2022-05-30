package com.ririsisi.eduservice.controller;


import com.ririsisi.commonutils.R;
import com.ririsisi.eduservice.entity.vo.CourseInfoVo;
import com.ririsisi.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-29
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    // 注入service
    @Autowired
    private EduCourseService courseService;

    // 添加课程基本信息方法
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    // 根据课程id查询课程基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {

        CourseInfoVo courseInfo = courseService.getCourseInfo(courseId);

        return R.ok().data("courseInfo",courseInfo);

    }

    // 修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        courseService.updateCourseInfo(courseInfoVo);

        return R.ok();

    }
}

