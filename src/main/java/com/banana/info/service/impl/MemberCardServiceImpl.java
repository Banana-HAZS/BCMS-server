package com.banana.info.service.impl;

import com.banana.common.Result;
import com.banana.info.entity.MemberCard;
import com.banana.info.mapper.MemberCardMapper;
import com.banana.info.service.IMemberCardService;
import com.banana.tool.State;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@Service
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCard> implements IMemberCardService {

    @Override
    public String refund(MemberCard memberCard) {
        String message = "";
        if (memberCard.getState() == State.NORMAL.getIndex()) {
            memberCard.setState(State.REFUNDING.getIndex());
            message = "退款中";
        } else if (memberCard.getState() == State.REFUNDING.getIndex()) {
            memberCard.setState(State.REFUND_SUCCESS.getIndex());
            message = "退款成功";
        } else if (memberCard.getState() == State.REFUND_SUCCESS.getIndex()) {
            message = "用户已退款，不可重复退款";
        } else if (memberCard.getState() == State.EXPIRED.getIndex()) {
            message = "会员卡已过期，无法退款";
        }
        this.baseMapper.updateById(memberCard);
        return message;
    }

    @Override
    public String expiredOrNot() {
        String message;
        List<MemberCard> memberCardList = this.baseMapper.selectList(null);
        String expiredMembers = "";
        for (MemberCard memberCard : memberCardList) {
            if (memberCard.getEndDate().isBefore(LocalDateTime.now())) {
                memberCard.setState(State.EXPIRED.getIndex());
                this.baseMapper.updateById(memberCard);
                expiredMembers = expiredMembers + memberCard.getMemberName() + "、";
            }
        }
        if (!expiredMembers.isEmpty()) {
            message = expiredMembers.substring(0, expiredMembers.length() - 1) + "会员卡已过期";
        } else {
            message = "会员卡均在有效期内";
        }
        return message;
    }
}
