package com.banana.info.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zjy
 * @since 2023-12-23
 */
@TableName("t_member_trainer")
public class MemberTrainer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    @TableId(value = "member_id")
    private Integer memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 教练id
     */
    private Integer trainerId;

    /**
     * 教练名称
     */
    private String trainerName;

    /**
     * 费用(元)
     */
    private Integer fee;

    /**
     * 实际支付(元)
     */
    private Integer actualPay;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 开始日期
     */
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    private LocalDateTime endDate;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getActualPay() {
        return actualPay;
    }

    public void setActualPay(Integer actualPay) {
        this.actualPay = actualPay;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "MemberTrainer{" +
                "memberId = " + memberId +
                ", memberName = " + memberName +
                ", trainerId = " + trainerId +
                ", trainerName = " + trainerName +
                ", fee = " + fee +
                ", actualPay = " + actualPay +
                ", state = " + state +
                ", startDate = " + startDate +
                ", endDate = " + endDate +
                "}";
    }
}
