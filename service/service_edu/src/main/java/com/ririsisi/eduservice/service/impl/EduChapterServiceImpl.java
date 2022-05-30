package com.ririsisi.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ririsisi.eduservice.entity.EduChapter;
import com.ririsisi.eduservice.entity.EduVideo;
import com.ririsisi.eduservice.entity.chapter.ChapterVo;
import com.ririsisi.eduservice.entity.chapter.VideoVo;
import com.ririsisi.eduservice.mapper.EduChapterMapper;
import com.ririsisi.eduservice.service.EduChapterService;
import com.ririsisi.eduservice.service.EduVideoService;
import com.ririsisi.servicebase.excptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        // 创建查询条件,查询Edu_Chapter表,泛型为表对应实体类
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        // 调用eduChapterService方法根据courseId查询章节信息
        List<EduChapter> eduChapterList = eduChapterService.list(chapterQueryWrapper);

        // 在每个ChapterVo下还有List<VideoVo> 小节
        // 根据courseId查询EduVideo集合
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        // 调用eduVideoService方法根据courseId查询小节信息
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);

        // 创建接收最终结果的集合
        List<ChapterVo> finalChapterVideoList = new ArrayList<>();

        // 循环遍历eduChapterList,得到每个单独的EduChapter
        for (EduChapter eduChapter : eduChapterList) {
            // 将eduChapter对应属性赋值给ChapterVo
            // 创建ChapterVo对象
            ChapterVo chapterVo = new ChapterVo();
            // 完成属性赋值
            BeanUtils.copyProperties(eduChapter,chapterVo);

            // 创建一个集合接收List<VideoVo>
            List<VideoVo> videoVoList = new ArrayList<>();

            // 循环遍历videoVoList得到每一个小节对象VideoVo
            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {

                    // 创建videoVo对象
                    VideoVo videoVo = new VideoVo();
                    // 完成属性赋值
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    // 将videoVo添加到集合中
                    videoVoList.add(videoVo);
                    // 将videoVoList添加到chapterVo
                    chapterVo.setChildren(videoVoList);

                }
            }

            // 将chapterVo添加到返回的集合中
            finalChapterVideoList.add(chapterVo);

        }

        return finalChapterVideoList;
    }

    // 根据章节Id删除章节
    @Override
    public boolean deleteChapter(String chapterId) {

        // 查询章节下是否有小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(videoQueryWrapper);

        if (count > 0) {
            throw new GuliException(20001,"章节中有小节，不能直接删除");
        } else {
            int result = baseMapper.deleteById(chapterId);

            return result > 0;
        }

    }
}
