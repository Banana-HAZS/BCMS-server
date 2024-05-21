package com.banana.info.service.impl;

import com.banana.info.entity.DelayRecords;
import com.banana.info.entity.Loan;
import com.banana.info.entity.OverdueRecords;
import com.banana.info.entity.commonEnum.LoanStatusEnum;
import com.banana.info.entity.commonEnum.OverdueDurationTypeEnum;
import com.banana.info.entity.vo.GetFinancialSummaryVO;
import com.banana.info.mapper.DelayRecordsMapper;
import com.banana.info.mapper.LoanMapper;
import com.banana.info.mapper.OverdueRecordsMapper;
import com.banana.info.service.IFinancialSummaryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>
 * 财务报表汇总 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-04-24
 */
@Service
public class FinancialSummaryServiceImpl implements IFinancialSummaryService {

    @Resource
    private LoanMapper loanMapper;

    @Resource
    private DelayRecordsMapper delayRecordsMapper;

    @Resource
    private OverdueRecordsMapper overdueRecordsMapper;

    @Override
    public GetFinancialSummaryVO getFinancialSummary() {
        GetFinancialSummaryVO getFinancialSummaryVO = new GetFinancialSummaryVO();
        // 1. 统计利息收入
        BigDecimal interest = loanMapper.selectList(null).stream()
                .map(Loan::getRecoveredInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        getFinancialSummaryVO.setInterest(interest);
        // 2. 统计手续费及佣金收入(当前系统没有佣金只统计手续费，当前会产生手续费的地方只有贷款展期)
        BigDecimal fee = delayRecordsMapper.selectList(null).stream()
                .map(DelayRecords::getDelayCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        getFinancialSummaryVO.setFee(fee);
        // 3. 统计其他收入(当前系统不存在其它收入)
        getFinancialSummaryVO.setOther(BigDecimal.ZERO);
        // 4. 计算不良贷款率(已逾期90天以上的贷款即严重逾期)
        Integer badLoanNum = overdueRecordsMapper.selectCount(new LambdaQueryWrapper<OverdueRecords>()
                .eq(OverdueRecords::getOverdueDurationType, OverdueDurationTypeEnum.SERIOUS_OVERDUE.getV())
                .groupBy(OverdueRecords::getLoanId)
        ); // 这段代码统计的不是分组后每组的数量，而是不同LoanId满足条件的数量
        Integer totalLoanNum = loanMapper.selectCount(new LambdaQueryWrapper<Loan>()
                .eq(Loan::getLoanStatus, LoanStatusEnum.GRANTED.getV())
                .or()
                .eq(Loan::getLoanStatus, LoanStatusEnum.SETTLED.getV())
        );
        if (totalLoanNum.equals(0)) {
            getFinancialSummaryVO.setBadLoanRatio(BigDecimal.ZERO);
        } else {
            BigDecimal badLoanRatio = BigDecimal.valueOf(badLoanNum)
                    .divide(BigDecimal.valueOf(totalLoanNum), 4, RoundingMode.HALF_UP);
            getFinancialSummaryVO.setBadLoanRatio(badLoanRatio);
        }

        return getFinancialSummaryVO;
    }
}
