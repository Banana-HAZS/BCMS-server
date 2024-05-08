package com.banana.info.service;

import com.banana.info.entity.Customer;
import com.banana.info.entity.Loan;
import com.banana.info.entity.param.AuditLoanParam;
import com.banana.info.entity.param.GrantLoanParam;
import com.banana.info.entity.param.LoanApplySearchParam;
import com.banana.info.entity.param.LoanSaveParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 贷款表 服务类
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
public interface ILoanService extends IService<Loan> {

    Map<String, Object> getLoanList(LoanApplySearchParam param);

    void addLoan(String token, LoanSaveParam param);

    void auditLoan(String token, AuditLoanParam param);

    void rejectLoan(String token, AuditLoanParam param);

    @Transactional(rollbackFor = Exception.class)
    void grantLoan(String token, GrantLoanParam param);
}
