package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Course;
import com.banana.info.entity.MemberCourse;
import com.banana.info.service.ICourseService;
import com.banana.info.service.IMemberCourseService;
import com.banana.tool.State;
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
@RequestMapping("/consumption/memberCourse")
public class MemberCourseController {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IMemberCourseService memberCourseService;

    @GetMapping("/all")
    public Result<List<MemberCourse>> getAllMemberCourse(){
        List<MemberCourse> memberCourseList = memberCourseService.list();
        return Result.success("查询成功",memberCourseList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getMemberCourseList(@RequestParam(value = "memberId",required = false) String memberId,
                                                        @RequestParam(value = "memberName",required = false) String memberName,
                                                        @RequestParam(value = "courseName",required = false) String courseName,
                                                        @RequestParam(value = "pageNo") Long pageNo,
                                                        @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<MemberCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StringUtils.hasLength(memberId),MemberCourse::getMemberId,memberId);
        wrapper.likeRight(StringUtils.hasLength(memberName),MemberCourse::getMemberName,memberName);
        wrapper.likeRight(StringUtils.hasLength(courseName),MemberCourse::getCourseName,courseName);

        Page<MemberCourse> page = new Page<>(pageNo,pageSize);
        memberCourseService.page(page,wrapper);

        //展示结果要有会员号、会员姓名、课程名、实际支付、状态、注册时间
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addMemberCourse(@RequestBody MemberCourse memberCourse){
        for(Course course:courseService.list()){
            if(course.getName().equals(memberCourse.getCourseName())){
                memberCourse.setCourseId(course.getId());
                break;
            }
        }
        if(memberCourse.getCourseId()==null){
            return Result.fail("课程不存在");
        }
        memberCourse.setRegisterDate(LocalDateTime.now());
        memberCourse.setState(1);
        memberCourseService.save(memberCourse);
        return Result.success("新增成功");
    }

    @PutMapping
    public Result<?> updateMemberCourse(@RequestBody MemberCourse memberCourse){
        memberCourseService.updateById(memberCourse);
        return Result.success("修改成功");
    }

    @GetMapping("/{memberId}")
    public Result<MemberCourse> getMemberCourseById(@PathVariable("memberId") Integer memberId){
        MemberCourse memberCourse = memberCourseService.getById(memberId);
        return Result.success(memberCourse);
    }

    @DeleteMapping("/{memberId}")
    public Result<MemberCourse> deleteById(@PathVariable("memberId") Integer memberId){
        memberCourseService.removeById(memberId);
        return Result.success("删除成功");
    }

    @PutMapping("/refund")
    public Result<?> refund(@RequestBody MemberCourse memberCourse){
        String message = memberCourseService.refund(memberCourse);
        return Result.success(message);
    }
}
