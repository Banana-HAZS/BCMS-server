package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Trainer;
import com.banana.info.service.ITrainerService;
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
@RequestMapping("/info/trainer")
public class TrainerController {

    @Autowired
    private ITrainerService trainerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("all")
    public Result<List<Trainer>> getAllTrainer(){
        List<Trainer> trainerList = trainerService.list();
        return Result.success("查询成功",trainerList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getTrainerList(@RequestParam(value = "id",required = false) String id,
                                                    @RequestParam(value = "name",required = false) String name,
                                                    @RequestParam(value = "phone",required = false) String phone,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Trainer> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StringUtils.hasLength(id),Trainer::getId,id);
        wrapper.likeRight(StringUtils.hasLength(name),Trainer::getName,name);
        wrapper.likeRight(StringUtils.hasLength(phone ),Trainer::getPhone,phone);

        Page<Trainer> page = new Page<>(pageNo,pageSize);
        trainerService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addTrainer(@RequestBody Trainer trainer){
        trainer.setHireDate(LocalDateTime.now());
        trainer.setPicture("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        trainer.setPassword(passwordEncoder.encode(trainer.getPassword()));
        trainerService.save(trainer);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateTrainer(@RequestBody Trainer trainer){
        trainerService.updateById(trainer);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<Trainer> getTrainerById(@PathVariable("id") Integer id){
        Trainer trainer = trainerService.getById(id);
        return Result.success(trainer);
    }

    @DeleteMapping("/{id}")
    public Result<Trainer> deleteById(@PathVariable("id") Integer id){
        trainerService.removeById(id);
        return Result.success("用户已删除");
    }
}
