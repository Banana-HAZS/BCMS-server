package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Member;
import com.banana.info.service.IMemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RestController //RestController注解会将返回结果自动转JSon
@RequestMapping("/info/member")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("all")
    public Result<List<Member>> getAllMember(){
        List<Member> memberList = memberService.list();
        return Result.success("查询成功",memberList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getMemberList(@RequestParam(value = "id",required = false) String id,
                                                @RequestParam(value = "name",required = false) String name,
                                                @RequestParam(value = "phone",required = false) String phone,
                                                @RequestParam(value = "pageNo") Long pageNo,
                                                @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StringUtils.hasLength(id),Member::getId,id);
        wrapper.likeRight(StringUtils.hasLength(name),Member::getName,name);
        wrapper.likeRight(StringUtils.hasLength(phone),Member::getPhone,phone);

        Page<Member> page = new Page<>(pageNo,pageSize);
        memberService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addMember(@RequestBody Member member){
        member.setJoinDate(LocalDateTime.now());
        member.setPicture("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberService.save(member);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateMember(@RequestBody Member member){
        memberService.updateById(member);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Member> getMemberById(@PathVariable("id") Integer id){
        Member member = memberService.getById(id);
        return Result.success(member);
    }

    @DeleteMapping("/{id}")
    public Result<Member> deleteById(@PathVariable("id") Integer id){
        memberService.removeById(id);
        return Result.success("用户已删除");
    }
}
