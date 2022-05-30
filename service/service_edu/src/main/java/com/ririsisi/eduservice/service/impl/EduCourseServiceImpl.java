package com.ririsisi.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ririsisi.eduservice.entity.EduCourse;
import com.ririsisi.eduservice.entity.EduCourseDescription;
import com.ririsisi.eduservice.entity.vo.CourseInfoVo;
import com.ririsisi.eduservice.mapper.EduCourseMapper;
import com.ririsisi.eduservice.service.EduCourseService;
import com.ririsisi.servicebase.excptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionServiceImpl eduCourseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        // 1. 向课程表中添加课程基本信息
        EduCourse eduCourse = new EduCourse();

        // 属性复制
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse); // 返回影响行数

        if (insert == 0) {
            // 添加失败
            throw new GuliException(20001,"添加课程信息失败");
        }

        // 获取添加之后的课程id
        String cid = eduCourse.getId();

        // 2. 向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);


        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        // 1. 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        // 2. 查询课程简介表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);

        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoVo,eduCourse);

        // 修改课程表
        int update = baseMapper.updateById(eduCourse);

        if (update == 0) {
            throw new GuliException(20001,"修改课程信息失败");
        }

        // 修改课程简介表
        EduCourseDescription description = new EduCourseDescription();

        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());

        eduCourseDescriptionService.updateById(description);

    }

}
