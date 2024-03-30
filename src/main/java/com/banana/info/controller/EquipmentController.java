package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Equipment;
import com.banana.info.service.IEquipmentService;
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
@RestController
@RequestMapping("/info/equipment")
public class EquipmentController {

    @Autowired
    private IEquipmentService equipmentService;

    @GetMapping("all")
    public Result<List<Equipment>> getAllEquipment(){
        List<Equipment> equipmentList = equipmentService.list();
        return Result.success("查询成功",equipmentList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getEquipmentList(@RequestParam(value = "id",required = false) String id,
                                                    @RequestParam(value = "name",required = false) String name,
                                                    @RequestParam(value = "purchaseDate",required = false) String purchaseDate,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Equipment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(id),Equipment::getId,id);
        wrapper.eq(StringUtils.hasLength(name),Equipment::getName,name);
        wrapper.likeRight(StringUtils.hasLength(purchaseDate),Equipment::getPurchaseDate,purchaseDate);

        Page<Equipment> page = new Page<>(pageNo,pageSize);
        equipmentService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addEquipment(@RequestBody Equipment equipment){
        equipment.setPurchaseDate(LocalDateTime.now());
        equipmentService.save(equipment);
        return Result.success("新增成功");
    }

    @PutMapping
    public Result<?> updateEquipment(@RequestBody Equipment equipment){
        equipmentService.updateById(equipment);
        return Result.success("修改成功");
    }

    @GetMapping("/{id}")
    public Result<Equipment> getEquipmentById(@PathVariable("id") Integer id){
        Equipment equipment = equipmentService.getById(id);
        return Result.success(equipment);
    }

    @DeleteMapping("/{id}")
    public Result<Equipment> deleteById(@PathVariable("id") Integer id){
        equipmentService.removeById(id);
        return Result.success("设备记录已删除");
    }
}
