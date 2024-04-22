package com.banana.info.service.impl;

import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.info.entity.Customer;
import com.banana.info.entity.Employee;
import com.banana.info.entity.Loan;
import com.banana.info.entity.commonEnum.AuditTypeEnum;
import com.banana.info.entity.commonEnum.LoanStatusEnum;
import com.banana.info.entity.param.AuditLoanParam;
import com.banana.info.entity.param.GrantLoanParam;
import com.banana.info.entity.param.LoanApplyParam;
import com.banana.info.entity.param.LoanSaveParam;
import com.banana.info.entity.vo.LoanApplyVO;
import com.banana.info.mapper.CustomerMapper;
import com.banana.info.mapper.LoanMapper;
import com.banana.info.service.IEmployeeService;
import com.banana.info.service.ILoanRecoverService;
import com.banana.info.service.ILoanService;
import com.banana.tool.LoanNoGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 贷款表 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@Service
public class LoanServiceImpl extends ServiceImpl<LoanMapper, Loan> implements ILoanService {

    @Resource
    private LoanMapper loanMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ILoanRecoverService loanRecoverService;

    @Resource
    private LoanNoGenerator loanNoGenerator;

    @Override
    public Map<String, Object> getLoanList(LoanApplyParam param) {

        // 时间初始化
        param.dateTimeInit();

        Page<LoanApplyVO> page = loanMapper
                .getLoanPage(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }

    @Override
    public void addLoan(String token, LoanSaveParam param) {

        Customer customer = getCustomerByIdCard(param.getIdCard());

        Employee employee = employeeService.getUserInfo(token);

        Loan loan = param.toLoan();
        loan.setLoanNo(loanNoGenerator.generateUniqueCode());
        loan.setCustomerId(customer.getId());
        loan.setApplyExecutorId(employee.getId());

        loanMapper.insert(loan);
    }

    @Override
    public void auditLoan(String token, AuditLoanParam param) {
        Loan checkLoan = loanMapper.selectById(param.getId());
        if (checkLoan.getAuditType() != AuditTypeEnum.WAIT_AUDIT.getV()) {
            throw new BusinessException(BusinessExceptionEnum.AUDIT_TYPE_ERROR);
        }

        Employee employee = employeeService.getUserInfo(token);

        Loan loan = param.toLoan();
        loan.setAuditType(AuditTypeEnum.PASSED.getV());
        loan.setLoanStatus(LoanStatusEnum.WAIT_GRANT.getV());
        loan.setAuditorId(employee.getId());

        loanMapper.updateById(loan);
    }

    @Override
    public void rejectLoan(String token, AuditLoanParam param) {
        Loan checkLoan = loanMapper.selectById(param.getId());
        if (checkLoan.getAuditType() == AuditTypeEnum.REJECTED.getV()) {
            return;
        }

        Employee employee = employeeService.getUserInfo(token);

        Loan loan = param.toLoan();
        loan.setAuditType(AuditTypeEnum.REJECTED.getV());
        loan.setLoanStatus(LoanStatusEnum.REJECTED.getV());
        loan.setAuditorId(employee.getId());

        loanMapper.updateById(loan);
    }

    @Override
    public void grantLoan(String token, GrantLoanParam param) {
        Loan checkLoan = loanMapper.selectById(param.getId());
        if (checkLoan.getLoanStatus() != LoanStatusEnum.WAIT_GRANT.getV()) {
            throw new BusinessException(BusinessExceptionEnum.GTANT_TYPE_ERROR);
        }

        Employee employee = employeeService.getUserInfo(token);

        Loan loan = param.toLoan();
        loan.setLoanStatus(LoanStatusEnum.GRANTED.getV());
        loan.setGrantExecutorId(employee.getId());

        loanMapper.updateById(loan);

        // 贷款发放后，开启贷款收回
        loanRecoverService.addLoanRecover(loanMapper.selectById(param.getId()));
    }

    @Override
    public Customer getCustomerByIdCard(String idCard) {
        if (Objects.isNull(idCard)) {
            return new Customer();
        }

        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getIdCard, idCard);
        Customer customer = customerMapper.selectOne(wrapper);

        if (Objects.isNull(customer)) {
            throw new BusinessException(BusinessExceptionEnum.IDCARD_NOT_EXIST);
        }
        return customer;
    }
}
