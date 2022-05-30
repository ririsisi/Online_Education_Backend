package com.ririsisi.eduservice.service;

import com.ririsisi.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ririsisi.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    // 添加课程分类
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    // 课程分类列表
    List<OneSubject> getAllOneTwoSubjects();
}
