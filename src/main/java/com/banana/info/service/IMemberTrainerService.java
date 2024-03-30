package com.banana.info.service;

import com.banana.info.entity.MemberTrainer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
public interface IMemberTrainerService extends IService<MemberTrainer> {

    String refund(MemberTrainer memberTrainer);

    String expiredOrNot();
}
