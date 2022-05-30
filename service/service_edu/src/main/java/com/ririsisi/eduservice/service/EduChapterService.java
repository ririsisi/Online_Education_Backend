package com.ririsisi.eduservice.service;

import com.ririsisi.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ririsisi.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-29
 */
public interface EduChapterService extends IService<EduChapter> {

    // 根据课程id进行查询章节小节
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    // 根据章节id删除章节
    boolean deleteChapter(String chapterId);
}
