package com.ririsisi.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ririsisi.commonutils.R;
import com.ririsisi.eduservice.entity.EduTeacher;
import com.ririsisi.eduservice.entity.vo.TeacherQuery;
import com.ririsisi.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@CrossOrigin // 解决跨域
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

    @ApiOperation(value = "查询所有讲师，带分页")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current,
                             @PathVariable Long limit) {

        // 创建配置对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 调用方法实现分页
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal(); // 总记录数

        List<EduTeacher> records = pageTeacher.getRecords(); // 每页数据的list集合

//        Map map = new HashMap();
//        map.put("total", total);
//        map.put("rows", records);
//
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);

    }

    // 4. 条件查询带分页的方法
    @ApiOperation(value = "条件查找所有讲师，带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {

        // 创建配置对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        // 构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        // 多条件组合查询 （类似Mybatis中学过的动态sql）
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 判断条件值是否为空，如果不为空，则拼接条件(数据库字段)
        if (!StringUtils.isEmpty(name)) {
            // 构建条件（模糊查询）
            wrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)) {
            // 构建条件（比较相等）
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            // 构建条件(大于等于)
            wrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)) {
            // 构建条件（小于等于）
            wrapper.le("gmt_create", end);
        }

        // 排序条件
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal(); // 总记录数

        List<EduTeacher> records = pageTeacher.getRecords(); // 每页数据的list集合

        return R.ok().data("total",total).data("rows",records);

    }

    // 5.添加讲师接口的方法
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean save = teacherService.save(eduTeacher);

        if (save) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    // 6.根据讲师id进行查询
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {

        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);

    }

    // 7.讲师修改功能
    @ApiOperation(value = "更新讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher (@RequestBody EduTeacher eduTeacher) {

        boolean flag = teacherService.updateById(eduTeacher);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

