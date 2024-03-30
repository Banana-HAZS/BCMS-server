package com.banana.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_member_card")
public class MemberCard implements Serializable {

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
     * 卡号
     */
    private Integer cardId;

    /**
     * 会员卡类型
     */
    private String cardType;

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

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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
        return "MemberCard{" +
                "memberId = " + memberId +
                ", memberName = " + memberName +
                ", cardId = " + cardId +
                ", cardType = " + cardType +
                ", actualPay = " + actualPay +
                ", state = " + state +
                ", startDate = " + startDate +
                ", endDate = " + endDate +
                "}";
    }
}
