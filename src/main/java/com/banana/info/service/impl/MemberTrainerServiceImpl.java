package com.banana.info.service.impl;

import com.banana.info.entity.MemberTrainer;
import com.banana.info.entity.MemberTrainer;
import com.banana.info.mapper.MemberTrainerMapper;
import com.banana.info.service.IMemberTrainerService;
import com.banana.tool.State;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@Service
public class MemberTrainerServiceImpl extends ServiceImpl<MemberTrainerMapper, MemberTrainer> implements IMemberTrainerService {

    @Override
    public String refund(MemberTrainer memberTrainer) {
        String message = "";
        if (memberTrainer.getState() == State.NORMAL.getIndex()) {
            memberTrainer.setState(State.REFUNDING.getIndex());
            message = "退款中";
        } else if (memberTrainer.getState() == State.REFUNDING.getIndex()) {
            memberTrainer.setState(State.REFUND_SUCCESS.getIndex());
            message = "退款成功";
        } else if (memberTrainer.getState() == State.REFUND_SUCCESS.getIndex()) {
            message = "用户已退款，不可重复退款";
        } else if (memberTrainer.getState() == State.EXPIRED.getIndex()) {
            message = "私教已过期，无法退款";
        }
        this.baseMapper.updateById(memberTrainer);
        return message;
    }

    @Override
    public String expiredOrNot() {
        String message;
        List<MemberTrainer> memberTrainerList = this.baseMapper.selectList(null);
        String expiredMembers = "";
        for (MemberTrainer memberTrainer : memberTrainerList) {
            if (memberTrainer.getEndDate().isBefore(LocalDateTime.now())) {
                memberTrainer.setState(State.EXPIRED.getIndex());
                this.baseMapper.updateById(memberTrainer);
                expiredMembers = expiredMembers + memberTrainer.getMemberName() + "、";
            }
        }
        if (!expiredMembers.isEmpty()) {
            message = expiredMembers.substring(0, expiredMembers.length() - 1) + "私教已过期";
        } else {
            message = "私教均在有效期内";
        }
        return message;
    }
}
