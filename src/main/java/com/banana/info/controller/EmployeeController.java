package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Employee;
import com.banana.info.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RestController
@RequestMapping("/info/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("all")
    public Result<List<Employee>> getAllEmployee(){
        List<Employee> employeeList = employeeService.list();
        return Result.success("查询成功",employeeList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getEmployeeList(@RequestParam(value = "id",required = false) String id,
                                                    @RequestParam(value = "name",required = false) String name,
                                                    @RequestParam(value = "phone",required = false) String phone,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StringUtils.hasLength(id),Employee::getId,id);
        wrapper.likeRight(StringUtils.hasLength(name),Employee::getName,name);
        wrapper.likeRight(StringUtils.hasLength(phone ),Employee::getPhone,phone);

        Page<Employee> page = new Page<>(pageNo,pageSize);
        employeeService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addEmployee(@RequestBody Employee employee){
        employee.setHireDate(LocalDateTime.now());
        employee.setPicture("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeService.save(employee);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateEmployee(@RequestBody Employee employee){
        employeeService.updateById(employee);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Employee> getEmployeeById(@PathVariable("id") Integer id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @DeleteMapping("/{id}")
    public Result<Employee> deleteById(@PathVariable("id") Integer id){
        employeeService.removeById(id);
        return Result.success("用户已删除");
    }
}
