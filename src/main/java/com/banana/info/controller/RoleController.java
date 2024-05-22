package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Employee;
import com.banana.info.entity.param.RoleParam;
import com.banana.info.service.IEmployeeService;
import com.banana.info.service.IRoleVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/info/role")
public class RoleController {

    @Resource
    private IRoleVOService roleService;

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/list")
    public Result<Map<String,Object>> getRoleList(@RequestBody RoleParam param){
        Map<String, Object> data = roleService.getRolePage(param);
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result<Employee> getEmployeeById(@PathVariable("id") Integer id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @PutMapping
    public Result<?> updateRole(@RequestBody Employee employee){
        employeeService.updateById(employee);
        return Result.success("修改权限成功");
    }
}
