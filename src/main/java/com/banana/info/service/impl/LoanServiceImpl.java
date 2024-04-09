package com.banana.info.service.impl;

import com.alibaba.fastjson2.JSON;
import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.info.entity.Customer;
import com.banana.info.entity.Employee;
import com.banana.info.entity.Loan;
import com.banana.info.entity.param.LoanApplyParam;
import com.banana.info.entity.param.LoanSaveParam;
import com.banana.info.entity.vo.LoanApplyVO;
import com.banana.info.mapper.CustomerMapper;
import com.banana.info.mapper.LoanMapper;
import com.banana.info.service.IEmployeeService;
import com.banana.info.service.ILoanService;
import com.banana.tool.UniqueCodeGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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

    @Resource
    private UniqueCodeGenerator uniqueCodeGenerator;

    @Override
    public Map<String, Object> getLoanList(LoanApplyParam param) {

        // 时间初始化
        param.dateTimeInit();

        Page<LoanApplyVO> page = new Page<>(param.getPageNo(), param.getPageSize());

        loanMapper.getLoanPage(param, page);

        List<LoanApplyVO> records = page.getRecords();

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", records);
        return data;
    }

    @Override
    public void addLoan(String token, LoanSaveParam param) {

        Customer customer = getCustomerByIdCard(param.getIdCard());

        Employee employee = employeeService.getUserInfo(token);

        Loan loan = param.toLoan();
        loan.setLoanNo(uniqueCodeGenerator.generateUniqueCode());
        loan.setCustomerId(customer.getId());
        loan.setApplyExecutorId(employee.getId());

        loanMapper.insert(loan);
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
