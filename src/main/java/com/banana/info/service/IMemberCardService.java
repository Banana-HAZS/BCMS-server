package com.banana.info.service;

import com.banana.info.entity.MemberCard;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
public interface IMemberCardService extends IService<MemberCard> {

    String refund(MemberCard memberCard);

    String expiredOrNot();
}
