package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.MemberCard;
import com.banana.info.service.IMemberCardService;
import com.banana.tool.CardType;
import com.banana.tool.TimeCalculator;
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
@RequestMapping("/consumption/memberCard")
public class MemberCardController {

    @Autowired
    private IMemberCardService memberCardService;

    @GetMapping("/all")
    public Result<List<MemberCard>> getAllMemberCard(){
        List<MemberCard> memberCardList = memberCardService.list();
        return Result.success("查询成功",memberCardList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getMemberCardList(@RequestParam(value = "memberId",required = false) String memberId,
                                                    @RequestParam(value = "memberName",required = false) String memberName,
                                                    @RequestParam(value = "cardType",required = false) String cardType,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<MemberCard> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StringUtils.hasLength(memberId),MemberCard::getMemberId,memberId);
        wrapper.likeRight(StringUtils.hasLength(memberName),MemberCard::getMemberName,memberName);
        wrapper.likeRight(StringUtils.hasLength(cardType),MemberCard::getCardType,cardType);

        Page<MemberCard> page = new Page<>(pageNo,pageSize);
        memberCardService.page(page,wrapper);

        //展示结果要有会员号、会员姓名、会员卡类型、实际支付、开始时间、结束时间、状态
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addMemberCard(@RequestBody MemberCard memberCard){
        memberCard.setCardId(CardType.getIndex(memberCard.getCardType()));
        memberCard.setStartDate(LocalDateTime.now());
        memberCard.setEndDate(TimeCalculator.endDate(memberCard.getStartDate(),memberCard.getCardId()));
        memberCard.setState(1);
        memberCardService.save(memberCard);
        return Result.success("新增成功");
    }

    @PutMapping
    public Result<?> updateMemberCard(@RequestBody MemberCard memberCard){
        memberCardService.updateById(memberCard);
        return Result.success("修改成功");
    }

    @GetMapping("/{memberId}")
    public Result<MemberCard> getMemberCardById(@PathVariable("memberId") Integer memberId){
        MemberCard memberCard = memberCardService.getById(memberId);
        return Result.success(memberCard);
    }

    @DeleteMapping("/{memberId}")
    public Result<MemberCard> deleteById(@PathVariable("memberId") Integer memberId){
        memberCardService.removeById(memberId);
        return Result.success("删除成功");
    }

    @PutMapping("/refund")
    public Result<?> refund(@RequestBody MemberCard memberCard){
        String message = memberCardService.refund(memberCard);
        return Result.success(message);
    }

    @PutMapping("/expiredOrNot")
    public Result<?> expiredOrNot(){
        String message = memberCardService.expiredOrNot();
        return Result.success(message);
    }
}
