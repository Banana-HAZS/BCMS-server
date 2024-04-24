package com.banana.info.service.impl;

import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.info.entity.Loan;
import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.RepayRecords;
import com.banana.info.entity.commonEnum.RepayMethodEnum;
import com.banana.info.entity.commonEnum.TermStatusEnum;
import com.banana.info.entity.param.LoanRecoverRepayParam;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.banana.info.entity.vo.LoanRecoverSearchVO;
import com.banana.info.entity.vo.RepayRecordsSearchVO;
import com.banana.info.mapper.LoanMapper;
import com.banana.info.mapper.LoanRecoverMapper;
import com.banana.info.mapper.RepayRecordsMapper;
import com.banana.info.service.ILoanRecoverService;
import com.banana.info.service.IRepayRecordsService;
import com.banana.tool.LoanRecoverNoGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 还款记录表 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-04-24
 */
@Service
public class RepayRecordsServiceImpl extends ServiceImpl<RepayRecordsMapper, RepayRecords> implements IRepayRecordsService {

    @Resource
    private RepayRecordsMapper repayRecordsMapper;

    @Override
    public Map<String, Object> getRepayRecordsList(RepayRecordsSearchParam param) {

        // 时间初始化
        param.dateTimeInit();

        Page<RepayRecordsSearchVO> page = repayRecordsMapper
                .getRepayRecordsList(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }
}
