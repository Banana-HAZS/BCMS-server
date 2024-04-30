package com.banana.info.service.impl;

import com.banana.info.entity.CustomerCredit;
import com.banana.info.entity.commonEnum.CreditScoreChangeTypeEnum;
import com.banana.info.entity.param.CustomerCreditSearchParam;
import com.banana.info.entity.param.LoanApplySearchParam;
import com.banana.info.entity.vo.CustomerCreditSearchVO;
import com.banana.info.entity.vo.LoanApplyVO;
import com.banana.info.mapper.CustomerCreditMapper;
import com.banana.info.service.ICustomerCreditService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 客户信用表 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@Service
public class CustomerCreditServiceImpl extends ServiceImpl<CustomerCreditMapper, CustomerCredit> implements ICustomerCreditService {

    @Resource
    private CustomerCreditMapper customerCreditMapper;

    @Override
    public Map<String, Object> getCustomerCreditList(CustomerCreditSearchParam param) {

        Page<CustomerCreditSearchVO> page = customerCreditMapper
                .getCustomerCreditList(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }

    public void updateCustomerCreditScore(Integer customerId, CreditScoreChangeTypeEnum typeEnum) {
        CustomerCredit customerCredit = customerCreditMapper.selectOne(new LambdaQueryWrapper<CustomerCredit>()
                .eq(CustomerCredit::getCustomerId, customerId));
        customerCreditMapper.update(null, new LambdaUpdateWrapper<CustomerCredit>()
                .eq(CustomerCredit::getId, customerId)
                .set(CustomerCredit::getCreditScore, customerCredit.getCreditScore() + typeEnum.getValue())
        );
    }
}
