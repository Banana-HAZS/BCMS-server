package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Card;
import com.banana.info.service.ICardService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/info/card")
public class CardController {

    @Autowired
    private ICardService cardService;

    @GetMapping("all")
    public Result<List<Card>> getAllCard(){
        List<Card> cardList = cardService.list();
        return Result.success("查询成功",cardList);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getCardList(@RequestParam(value = "id",required = false) String id,
                                                    @RequestParam(value = "type",required = false) String type,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Card> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StringUtils.hasLength(id),Card::getId,id);
        wrapper.likeRight(StringUtils.hasLength(type),Card::getType,type);

        Page<Card> page = new Page<>(pageNo,pageSize);
        cardService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addCard(@RequestBody Card card){
        cardService.save(card);
        return Result.success("新增成功");
    }

    @PutMapping
    public Result<?> updateCard(@RequestBody Card card){
        cardService.updateById(card);
        return Result.success("修改成功");
    }

    @GetMapping("/{id}")
    public Result<Card> getCardById(@PathVariable("id") Integer id){
        Card card = cardService.getById(id);
        return Result.success(card);
    }

    @DeleteMapping("/{id}")
    public Result<Card> deleteById(@PathVariable("id") Integer id){
        cardService.removeById(id);
        return Result.success("删除成功");
    }
}
