package com.banana.info.service.impl;

import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.OverdueRecords;
import com.banana.info.entity.RepayRecords;
import com.banana.info.entity.commonEnum.RemindStatusEnum;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.banana.info.entity.vo.RepayRecordsSearchVO;
import com.banana.info.mapper.LoanRecoverMapper;
import com.banana.info.mapper.OverdueRecordsMapper;
import com.banana.info.mapper.RepayRecordsMapper;
import com.banana.info.service.IOverdueRecordsService;
import com.banana.info.service.IRepayRecordsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 逾期记录表 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-04-27
 */
@Service
public class OverdueRecordsServiceImpl extends ServiceImpl<OverdueRecordsMapper, OverdueRecords> implements IOverdueRecordsService {

    @Resource
    private OverdueRecordsMapper overdueRecordsMapper;

    @Resource
    private LoanRecoverMapper loanRecoverMapper;

    private static int MILLIS_OF_DAY = 86_400_000;

    /**
     * 更新逾期记录(同步逾期天数、逾期罚息、提醒状态)
     */
    @Override
    public void updateOverdueRecords() {
        // 获取还在逾期中的记录
        List<OverdueRecords> overdueingRecords = overdueRecordsMapper.selectList(
                new LambdaQueryWrapper<OverdueRecords>()
                        .isNull(OverdueRecords::getOverdueEndDate));
        // 今日零点(多一毫米才能判定为1天)
        LocalDateTime today = LocalDateTime.now()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(1000 * 1000);
        overdueingRecords.forEach(or->{
            LoanRecover loanRecover = loanRecoverMapper.selectById(or.getLoanRecoverId());
            // 不满一天按一天算，以毫秒为基准
            long millis = Duration.between(or.getOverdueStartDate(),today)
                    .toMillis();
            or.setOverdueDays((int)((millis + MILLIS_OF_DAY - 1) / MILLIS_OF_DAY));
            BigDecimal dayInterestRate = loanRecover.getInterestRate()
                    .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP);
            // 逾期罚息计算：逾期罚息 = 逾期金额 * 利率 * 逾期罚息基数 * 逾期时间
            or.setLateCharge(or.getOverduePrice()
                    .multiply(dayInterestRate)
                    .multiply(or.getLateChargeBase())
                    .multiply(BigDecimal.valueOf(or.getOverdueDays())));
            or.setRemindStatus(
                    or.getNextRemindTime().isBefore(today) ?
                            RemindStatusEnum.REMIND_RECOVER.getV() : or.getRemindStatus());
            overdueRecordsMapper.updateById(or);

            // 同步更新loanRecover当期还款金额、逾期罚息
            loanRecover.setLateCharge(or.getLateCharge());
            loanRecover.setTermRepayPrice(loanRecover.getTermRepayPrincipal()
                    .add(loanRecover.getTermRepayInterest())
                    .add(loanRecover.getLateCharge()));
            loanRecoverMapper.updateById(loanRecover);
        });
    }
}
