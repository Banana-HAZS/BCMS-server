package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Customer;
import com.banana.info.service.ICustomerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@RestController //RestController注解会将返回结果自动转JSon
@RequestMapping("/info/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("all")
    public Result<List<Customer>> getAllCustomer(){
        List<Customer> customerList = customerService.list();
        return Result.success("查询成功",customerList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getCustomerList(@RequestParam(value = "idCard",required = false) String idCard,
                                                @RequestParam(value = "name",required = false) String name,
                                                @RequestParam(value = "phone",required = false) String phone,
                                                @RequestParam(value = "pageNo") Long pageNo,
                                                @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasLength(idCard),Customer::getIdCard,idCard);
        wrapper.like(StringUtils.hasLength(name),Customer::getName,name);
        wrapper.like(StringUtils.hasLength(phone),Customer::getPhone,phone);

        Page<Customer> page = new Page<>(pageNo,pageSize);
        customerService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addCustomer(@RequestBody Customer customer){
        customer.setRegistrationDate(LocalDateTime.now());
        customerService.save(customer);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateCustomer(@RequestBody Customer customer){
        customerService.updateById(customer);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Customer> getCustomerById(@PathVariable("id") Integer id){
        Customer customer = customerService.getById(id);
        return Result.success(customer);
    }

    @DeleteMapping("/{id}")
    public Result<Customer> deleteById(@PathVariable("id") Integer id){
        customerService.removeById(id);
        return Result.success("用户已删除");
    }
}
