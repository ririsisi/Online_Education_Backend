package com.ririsisi.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ririsisi
 * @Version 1.0
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    // 一对多，将小节放入List集合中
    private List<VideoVo> children = new ArrayList<>();
}
