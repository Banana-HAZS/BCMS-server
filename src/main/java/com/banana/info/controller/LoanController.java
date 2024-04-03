package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Loan;
import com.banana.info.service.ILoanService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 贷款表 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@RestController
@RequestMapping("/info/loan")
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @GetMapping("all")
    public Result<List<Loan>> getAllLoan(){
        return null;
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getLoanList(){

        return null;
    }

    @PostMapping
    public Result<?> addLoan(@RequestBody Loan loan){
        return null;
    }

    @PutMapping
    public Result<?> updateLoan(@RequestBody Loan loan){
        return null;
    }

    @GetMapping("/{id}")
    public Result<Loan> getLoanById(@PathVariable("id") Integer id){
        return null;
    }

    @DeleteMapping("/{id}")
    public Result<Loan> deleteById(@PathVariable("id") Integer id){
        return null;
    }
}
