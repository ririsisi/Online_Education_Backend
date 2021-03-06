package com.ririsisi.eduservice.service;

import com.ririsisi.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ririsisi.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-29
 */
public interface EduCourseService extends IService<EduCourse> {

    // 添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
}
