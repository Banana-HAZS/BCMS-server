package com.banana.info.service;

import com.banana.info.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
public interface ICustomerService extends IService<Customer> {
    @Transactional(rollbackFor = Exception.class)
    void addCustomer(Customer customer);

    Customer getCustomerByIdCard(String idCard);
}
