package com.banana.info.service.impl;

import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.info.entity.Customer;
import com.banana.info.entity.CustomerCredit;
import com.banana.info.entity.CustomerLoanLimit;
import com.banana.info.entity.commonEnum.EvaluateStatusEnum;
import com.banana.info.entity.commonEnum.LoanLimitEnum;
import com.banana.info.entity.commonEnum.SysConfig;
import com.banana.info.mapper.CustomerCreditMapper;
import com.banana.info.mapper.CustomerLoanLimitMapper;
import com.banana.info.mapper.CustomerMapper;
import com.banana.info.service.ICustomerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerCreditMapper customerCreditMapper;

    @Resource
    private CustomerLoanLimitMapper customerLoanLimitMapper;

    @Override
    public void addCustomer(Customer customer) {
        customer.setRegistrationDate(LocalDateTime.now());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        try {
            baseMapper.insert(customer);
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNTDUPLICATION);
        }
        // 同步创建客户信用评级
        CustomerCredit customerCredit = new CustomerCredit();
        customerCredit.setCustomerId(customer.getId());
        // 默认信用分600，评级C
        customerCreditMapper.insert(customerCredit);

        // 同步创建客户贷款额度
        CustomerLoanLimit customerLoanLimit = new CustomerLoanLimit();
        customerLoanLimit.setCustomerId(customer.getId());
        // 第一次由系统自动评估
        customerLoanLimit.setEvaluatorId(SysConfig.SYSTEM.getV().intValue());
        customerLoanLimit.setEvaluateStatus(EvaluateStatusEnum.EVALUATED.getV());
        customerLoanLimit.setEvaluateDate(LocalDateTime.now());
        customerLoanLimit.setLoanLimitLevel(LoanLimitEnum.F_PLUS.getCode());
        customerLoanLimit.setCreateDate(LocalDateTime.now());
        // 初始贷款额度等级F+(3千元)
        customerLoanLimitMapper.insert(customerLoanLimit);
    }

    @Override
    public Customer getCustomerByIdCard(String idCard) {
        if (Objects.isNull(idCard)) {
            throw new BusinessException(BusinessExceptionEnum.IDCARD_NOT_EXIST);
        }
        Customer customer = customerMapper.selectOne(new LambdaQueryWrapper<Customer>()
                .eq(Customer::getIdCard, idCard)
                .last("limit 1")
        );
        if (Objects.isNull(customer)) {
            throw new BusinessException(BusinessExceptionEnum.IDCARD_NOT_EXIST);
        }
        return customer;
    }
}
