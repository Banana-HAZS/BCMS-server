package com.banana.info.service.impl;

import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.info.entity.Customer;
import com.banana.info.mapper.CustomerMapper;
import com.banana.info.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public void addCustomer(Customer customer) {
        customer.setRegistrationDate(LocalDateTime.now());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        try {
            baseMapper.insert(customer);
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNTDUPLICATION);
        }
    }
}
