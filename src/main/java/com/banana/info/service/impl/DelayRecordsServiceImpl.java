package com.banana.info.service.impl;

import com.banana.info.entity.DelayRecords;
import com.banana.info.entity.param.DelayRecordsSearchParam;
import com.banana.info.entity.vo.DelayRecordsSearchVO;
import com.banana.info.entity.vo.OverdueRecordsSearchVO;
import com.banana.info.mapper.DelayRecordsMapper;
import com.banana.info.service.IDelayRecordsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 延期记录表 服务实现类
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
@Service
public class DelayRecordsServiceImpl extends ServiceImpl<DelayRecordsMapper, DelayRecords> implements IDelayRecordsService {

    @Resource
    private DelayRecordsMapper delayRecordsMapper;

    @Override
    public Map<String, Object> getDelayRecordsList(DelayRecordsSearchParam param) {

        Page<DelayRecordsSearchVO> page = delayRecordsMapper
                .getDelayRecordsList(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }
}
