package com.banana.info.service.impl;

import com.banana.info.entity.MemberCourse;
import com.banana.info.mapper.MemberCourseMapper;
import com.banana.info.service.IMemberCourseService;
import com.banana.tool.State;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@Service
public class MemberCourseServiceImpl extends ServiceImpl<MemberCourseMapper, MemberCourse> implements IMemberCourseService {

    @Override
    public String refund(MemberCourse memberCourse) {
        String message = "";
        if (memberCourse.getState() == State.NORMAL.getIndex()) {
            memberCourse.setState(State.REFUNDING.getIndex());
            message = "退款中";
        } else if (memberCourse.getState() == State.REFUNDING.getIndex()) {
            memberCourse.setState(State.REFUND_SUCCESS.getIndex());
            message = "退款成功";
        } else if (memberCourse.getState() == State.REFUND_SUCCESS.getIndex()) {
            message = "用户已退款，不可重复退款";
        } else if (memberCourse.getState() == State.EXPIRED.getIndex()) {
            message = "课程已过期，无法退款";
        }
        this.baseMapper.updateById(memberCourse);
        return message;
    }
}
