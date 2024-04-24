package com.banana.info.service;

import com.banana.info.entity.Loan;
import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.param.GrantLoanParam;
import com.banana.info.entity.param.LoanRecoverRepayParam;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 贷款收回表 服务类
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
public interface ILoanRecoverService extends IService<LoanRecover> {

    Map<String, Object> getLoanRecoverList(LoanRecoverSearchParam param);

    void addLoanRecover(Loan loan);

    @Transactional(rollbackFor = Exception.class)
    void repay(LoanRecoverRepayParam param);

    @Transactional(rollbackFor = Exception.class)
    void earlyPayoff(LoanRecoverRepayParam param);
}
