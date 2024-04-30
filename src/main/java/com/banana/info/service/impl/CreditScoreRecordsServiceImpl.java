package com.banana.info.service.impl;

import com.banana.info.entity.CreditScoreRecords;
import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.commonEnum.CreditScoreChangeTypeEnum;
import com.banana.info.entity.param.CreditScoreRecordsSearchParam;
import com.banana.info.entity.vo.CreditScoreRecordsSearchVO;
import com.banana.info.entity.vo.RepayRecordsSearchVO;
import com.banana.info.mapper.CreditScoreRecordsMapper;
import com.banana.info.service.ICreditScoreRecordsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 信用分变动记录表 服务实现类
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
@Service
public class CreditScoreRecordsServiceImpl extends ServiceImpl<CreditScoreRecordsMapper, CreditScoreRecords> implements ICreditScoreRecordsService {

    @Resource
    private CreditScoreRecordsMapper creditScoreRecordsMapper;

    @Override
    public Map<String, Object> getCreditScoreRecordsList(CreditScoreRecordsSearchParam param) {

        // 时间初始化
        param.dateTimeInit();

        Page<CreditScoreRecordsSearchVO> page = creditScoreRecordsMapper
                .getCreditScoreRecordsList(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }

    void addCreditScoreRecords(LoanRecover loanRecover, CreditScoreChangeTypeEnum typeEnum){
        CreditScoreRecords creditScoreRecord = new CreditScoreRecords();
        creditScoreRecord.setCustomerId(loanRecover.getCustomerId());
        creditScoreRecord.setLoanId(loanRecover.getLoanId());
        creditScoreRecord.setLoanRecoverId(loanRecover.getId());
        creditScoreRecord.setLoanNo(loanRecover.getLoanNo());
        creditScoreRecord.setLoanRecoverNo(loanRecover.getLoanRecoverNo());
        creditScoreRecord.setChangeType(typeEnum.getCode());
        creditScoreRecord.setChangeValue(typeEnum.getValue());
        creditScoreRecord.setChangeDescription(typeEnum.getDescription());
        creditScoreRecord.setChangeDate(LocalDateTime.now());

        creditScoreRecordsMapper.insert(creditScoreRecord);
    }
}
