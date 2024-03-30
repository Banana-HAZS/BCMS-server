package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Course;
import com.banana.info.service.ICourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/info/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping("all")
    public Result<List<Course>> getAllCourse(){
        List<Course> courseList = courseService.list();
        return Result.success("查询成功",courseList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getCourseList(@RequestParam(value = "id",required = false) String id,
                                                    @RequestParam(value = "name",required = false) String name,
                                                    @RequestParam(value = "trainers",required = false) String trainers,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StringUtils.hasLength(id),Course::getId,id);
        wrapper.likeRight(StringUtils.hasLength(name),Course::getName,name);
        wrapper.likeRight(StringUtils.hasLength(trainers),Course::getTrainers,trainers);

        Page<Course> page = new Page<>(pageNo,pageSize);
        courseService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addCourse(@RequestBody Course course){
        course.setCreateDate(LocalDateTime.now());
        courseService.save(course);
        return Result.success("新增课程成功");
    }

    @PutMapping
    public Result<?> updateCourse(@RequestBody Course course){
        courseService.updateById(course);
        return Result.success("修改课程成功");
    }

    @GetMapping("/{id}")
    public Result<Course> getCourseById(@PathVariable("id") Integer id){
        Course course = courseService.getById(id);
        return Result.success(course);
    }

    @DeleteMapping("/{id}")
    public Result<Course> deleteById(@PathVariable("id") Integer id){
        courseService.removeById(id);
        return Result.success("课程已删除");
    }
}
