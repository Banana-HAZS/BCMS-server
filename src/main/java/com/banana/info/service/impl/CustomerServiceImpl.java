package com.banana.info.service.impl;

import com.banana.info.entity.Customer;
import com.banana.info.mapper.CustomerMapper;
import com.banana.info.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {



}
