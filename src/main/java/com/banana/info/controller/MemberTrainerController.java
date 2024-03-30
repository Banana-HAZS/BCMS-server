package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Course;
import com.banana.info.entity.MemberTrainer;
import com.banana.info.entity.Trainer;
import com.banana.info.service.ICourseService;
import com.banana.info.service.IMemberTrainerService;
import com.banana.info.service.ITrainerService;
import com.banana.tool.CardType;
import com.banana.tool.State;
import com.banana.tool.TimeCalculator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
@RequestMapping("/consumption/memberTrainer")
public class MemberTrainerController {

    @Autowired
    private ITrainerService trainerService;

    @Autowired
    private IMemberTrainerService memberTrainerService;

    @GetMapping("/all")
    public Result<List<MemberTrainer>> getAllMemberTrainer() {
        List<MemberTrainer> memberTrainerList = memberTrainerService.list();
        return Result.success("查询成功", memberTrainerList);
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getMemberTrainerList(@RequestParam(value = "memberId", required = false) String memberId,
                                                            @RequestParam(value = "memberName", required = false) String memberName,
                                                            @RequestParam(value = "trainerName", required = false) String trainerName,
                                                            @RequestParam(value = "pageNo") Long pageNo,
                                                            @RequestParam(value = "pageSize") Long pageSize) {
        LambdaQueryWrapper<MemberTrainer> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StringUtils.hasLength(memberId), MemberTrainer::getMemberId, memberId);
        wrapper.likeRight(StringUtils.hasLength(memberName), MemberTrainer::getMemberName, memberName);
        wrapper.likeRight(StringUtils.hasLength(trainerName), MemberTrainer::getTrainerName, trainerName);

        Page<MemberTrainer> page = new Page<>(pageNo, pageSize);
        memberTrainerService.page(page, wrapper);

        //展示结果要有会员号、会员姓名、教练名、费用、实际支付、开始时间、结束时间、状态
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addMemberTrainer(@RequestBody MemberTrainer memberTrainer) {
        for (Trainer trainer : trainerService.list()) {
            if (trainer.getName().equals(memberTrainer.getTrainerName())) {
                memberTrainer.setTrainerId(trainer.getId());
                break;
            }
        }
        if (memberTrainer.getTrainerId() == null) {
            return Result.fail("教练不存在");
        }
        memberTrainer.setStartDate(LocalDateTime.now());
        memberTrainer.setState(1);
        memberTrainerService.save(memberTrainer);
        return Result.success("新增成功");
    }

    @PutMapping
    public Result<?> updateMemberTrainer(@RequestBody MemberTrainer memberTrainer) {
        memberTrainerService.updateById(memberTrainer);
        return Result.success("修改成功");
    }

    @GetMapping("/{memberId}")
    public Result<MemberTrainer> getMemberTrainerById(@PathVariable("memberId") Integer memberId) {
        MemberTrainer memberTrainer = memberTrainerService.getById(memberId);
        return Result.success(memberTrainer);
    }

    @DeleteMapping("/{memberId}")
    public Result<MemberTrainer> deleteById(@PathVariable("memberId") Integer memberId) {
        memberTrainerService.removeById(memberId);
        return Result.success("删除成功");
    }

    @PutMapping("/refund")
    public Result<?> refund(@RequestBody MemberTrainer memberTrainer) {
        String message = memberTrainerService.refund(memberTrainer);
        return Result.success(message);
    }

    @PutMapping("/expiredOrNot")
    public Result<?> expiredOrNot() {
        String message = memberTrainerService.expiredOrNot();
        return Result.success(message);
    }
}
