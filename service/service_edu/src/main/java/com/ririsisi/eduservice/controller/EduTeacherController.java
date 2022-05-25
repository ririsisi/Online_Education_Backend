package com.ririsisi.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ririsisi.commonutils.R;
import com.ririsisi.eduservice.entity.EduTeacher;
import com.ririsisi.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ririsisi
 * @since 2022-05-25
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    // 注入service
    @Autowired
    private EduTeacherService teacherService;

    // 1. 查询讲师表的所有数据
    // rest风格
    @ApiOperation(value = "查找所有讲师")
    @GetMapping("findAll")
    public R findAllTeacher() {

        // 调用Service里方法
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);

    }

    // 2. 逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {

        boolean flag = teacherService.removeById(id);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current,
                             @PathVariable Long limit) {

        // 创建配置对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 调用方法实现分页
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal(); // 总记录数

        List<EduTeacher> records = pageTeacher.getRecords(); // 每页数据的list集合

        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);

        return R.ok().data(map);

//        return R.ok().data("total",total).data("rows",records);

    }

}

