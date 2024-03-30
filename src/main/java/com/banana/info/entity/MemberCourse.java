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
@TableName("t_member_course")
public class MemberCourse implements Serializable {

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
     * 课程id
     */
    private Integer courseId;

    /**
     * 会员名称
     */
    private String courseName;

    /**
     * 实际支付(元)
     */
    private Integer actualPay;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 注册日期
     */
    private LocalDateTime registerDate;

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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "MemberCourse{" +
                "memberId = " + memberId +
                ", memberName = " + memberName +
                ", courseId = " + courseId +
                ", courseName = " + courseName +
                ", actualPay = " + actualPay +
                ", state = " + state +
                ", registerDate = " + registerDate +
                "}";
    }
}
