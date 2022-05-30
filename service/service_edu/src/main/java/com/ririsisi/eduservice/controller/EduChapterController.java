package com.ririsisi.eduservice.controller;


import com.ririsisi.commonutils.R;
import com.ririsisi.eduservice.entity.EduChapter;
import com.ririsisi.eduservice.entity.chapter.ChapterVo;
import com.ririsisi.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-29
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    // 课程大纲列表，根据课程id进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {

        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("AllChapterVideo",list);

    }

    // 添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {

        chapterService.save(eduChapter);
        return R.ok();

    }

    // 根据章节id查询(回显)
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {

        EduChapter eduChapter = chapterService.getById(chapterId);

        return R.ok().data("chapter",eduChapter);

    }

    // 修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {

        chapterService.updateById(eduChapter);

        return R.ok();

    }

    // 根据章节id删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {

        boolean flag = chapterService.deleteChapter(chapterId);

        return flag ? R.ok() : R.error();
    }

}

