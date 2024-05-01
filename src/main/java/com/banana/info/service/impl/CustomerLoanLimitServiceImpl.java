package com.banana.info.service.impl;

import com.banana.info.entity.CustomerLoanLimit;
import com.banana.info.entity.Employee;
import com.banana.info.entity.commonEnum.EvaluateStatusEnum;
import com.banana.info.entity.param.AddCustomerLoanLimitParam;
import com.banana.info.entity.param.CustomerLoanLimitSearchParam;
import com.banana.info.entity.param.EvaluateCustomerLoanLimitParam;
import com.banana.info.entity.vo.CustomerLoanLimitSearchVO;
import com.banana.info.entity.vo.RepayRecordsSearchVO;
import com.banana.info.mapper.CustomerLoanLimitMapper;
import com.banana.info.service.ICustomerLoanLimitService;
import com.banana.info.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 客户贷款额度表 服务实现类
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-05-01
 */
@Service
public class CustomerLoanLimitServiceImpl extends ServiceImpl<CustomerLoanLimitMapper, CustomerLoanLimit> implements ICustomerLoanLimitService {

    @Resource
    private CustomerLoanLimitMapper customerLoanLimitMapper;

    @Resource
    private IEmployeeService employeeService;

    @Override
    public Map<String, Object> getCustomerLoanLimitList(CustomerLoanLimitSearchParam param) {

        // 时间初始化
        param.dateTimeInit();

        Page<CustomerLoanLimitSearchVO> page = customerLoanLimitMapper
                .getCustomerLoanLimitList(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }

    @Override
    public void addCustomerLoanLimit(AddCustomerLoanLimitParam param) {
        CustomerLoanLimit customerLoanLimit = param.toCustomerLoanLimit();
        customerLoanLimitMapper.insert(customerLoanLimit);
    }

    @Override
    public void evaluateCustomerLoanLimit(String token, EvaluateCustomerLoanLimitParam param) {
        CustomerLoanLimit customerLoanLimit = customerLoanLimitMapper.selectById(param.getId());
        customerLoanLimit.setLoanLimitLevel(param.getLoan_limit_level());
        customerLoanLimit.setEvaluateStatus(EvaluateStatusEnum.EVALUATED.getV());
        customerLoanLimit.setEvaluateDate(LocalDateTime.now());

        Employee employee = employeeService.getUserInfo(token);
        customerLoanLimit.setEvaluatorId(employee.getId());

        customerLoanLimitMapper.updateById(customerLoanLimit);
    }
}
