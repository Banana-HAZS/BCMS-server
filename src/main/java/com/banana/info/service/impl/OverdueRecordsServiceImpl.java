package com.banana.info.service.impl;

import com.banana.info.entity.Loan;
import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.OverdueRecords;
import com.banana.info.entity.RepayRecords;
import com.banana.info.entity.commonEnum.OverdueDurationTypeEnum;
import com.banana.info.entity.commonEnum.OverdueTypeEnum;
import com.banana.info.entity.commonEnum.RemindStatusEnum;
import com.banana.info.entity.commonEnum.SysConfig;
import com.banana.info.entity.param.ConfirmRemindParam;
import com.banana.info.entity.param.OverdueRecordsSearchParam;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.banana.info.entity.vo.OverdueRecordsSearchVO;
import com.banana.info.entity.vo.RepayRecordsSearchVO;
import com.banana.info.mapper.LoanMapper;
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
import java.util.stream.Collectors;

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

    @Resource
    private LoanMapper loanMapper;

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

        // 更新逾期天数
        // 今日零点(多一毫米才能判定为1天)
        LocalDateTime today = LocalDateTime.now()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(1000 * 1000);
        overdueingRecords.forEach(or -> {
            LoanRecover loanRecover = loanRecoverMapper.selectById(or.getLoanRecoverId());
            // 不满一天按一天算，以毫秒为基准
            long millis = Duration.between(or.getOverdueStartDate(), today)
                    .toMillis();
            or.setOverdueDays((int) ((millis + MILLIS_OF_DAY - 1) / MILLIS_OF_DAY));

            // 更新逾期时长类型
            OverdueDurationTypeEnum overdueDurationType = OverdueDurationTypeEnum.getOverdueDurationType(or.getOverdueDays());
            or.setOverdueDurationType(overdueDurationType.getV());

            // 逾期罚息计算：逾期罚息 = 逾期金额 * 利率 * 逾期罚息基数 * 逾期时间
            BigDecimal dayInterestRate = loanRecover.getInterestRate()
                    .divide(BigDecimal.valueOf(12), 16, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(30), 16, RoundingMode.HALF_UP);
            or.setLateCharge(or.getOverduePrice()
                    .multiply(dayInterestRate)
                    .multiply(or.getLateChargeBase())
                    .multiply(BigDecimal.valueOf(or.getOverdueDays())));

            // 更新提醒状态
            or.setRemindStatus(
                    or.getNextRemindTime().isBefore(today) ?
                            RemindStatusEnum.REMIND_RECOVER.getV() : or.getRemindStatus());
            overdueRecordsMapper.updateById(or);

            // 同步更新loanRecover当期还款金额、逾期罚息、剩余待还金额
            // 在总贷款处获取最初的还款金额，避免重复累加
            Loan loan = loanMapper.selectById(loanRecover.getLoanId());
            loanRecover.setLateCharge(or.getLateCharge());
            loanRecover.setTermRepayInterest(loan.getTermRepayInterest()
                    .add(loanRecover.getLateCharge()));
            loanRecover.setTermRepayPrice(loanRecover.getTermRepayPrincipal()
                    .add(loanRecover.getTermRepayInterest()));
            loanRecover.setRemainRepayPrice(loanRecover.getRemainRepayPrice()
                    .add(loanRecover.getLateCharge()));
            loanRecoverMapper.updateById(loanRecover);
        });
    }

    @Override
    public void batchAddOverdueRecords(List<Integer> overdueRecoverIds) {
        List<LoanRecover> loanRecovers = loanRecoverMapper.selectList(new LambdaQueryWrapper<LoanRecover>()
                .in(LoanRecover::getId, overdueRecoverIds));
        loanRecovers.forEach(loanRecover -> {
            OverdueRecords overdueRecords = new OverdueRecords();
            overdueRecords.setLoanId(loanRecover.getLoanId());
            overdueRecords.setLoanRecoverId(loanRecover.getId());
            overdueRecords.setCustomerId(loanRecover.getCustomerId());
            overdueRecords.setLoanNo(loanRecover.getLoanNo());
            overdueRecords.setLoanRecoverNo(loanRecover.getLoanRecoverNo());
            overdueRecords.setOverduePrice(loanRecover.getRemainRepayPrice());
            overdueRecords.setOverdueType(loanRecover.getRemainRepayPrice()
                    .equals(loanRecover.getTermRepayPrice()) ?
                    OverdueTypeEnum.COMPLETE_OVERDUE.getV() :
                    OverdueTypeEnum.PART_OVERDUE.getV());
            overdueRecords.setOverdueDurationType(OverdueDurationTypeEnum.ORDINARY_OVERDUE.getV());
            overdueRecords.setOverdueStartDate(loanRecover.getRepayDate());
            overdueRecords.setOverdueDays(1);
            overdueRecords.setLateCharge(BigDecimal.ZERO);
            overdueRecords.setLateChargeBase(loanMapper.selectById(loanRecover.getLoanId())
                    .getLateChargeBase());
            overdueRecords.setRemindStatus(RemindStatusEnum.CURRENT_NOT.getV());
            // 逾期7天后进行第一次提醒
            overdueRecords.setNextRemindTime(overdueRecords.getOverdueStartDate()
                    .plusDays(SysConfig.OVERDUE_GRACE_PERIOD.getV()));

            overdueRecordsMapper.insert(overdueRecords);
        });
    }

    @Override
    public Map<String, Object> getOverdueRecordsList(OverdueRecordsSearchParam param) {

        // 更新记录
        updateOverdueRecords();

        Page<OverdueRecordsSearchVO> page = overdueRecordsMapper
                .getOverdueRecordsList(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }

    @Override
    public OverdueRecords getOverdueRecordsByLoanRecover(Integer loanRecoverId) {
        return overdueRecordsMapper.selectOne(new LambdaQueryWrapper<OverdueRecords>()
                .eq(OverdueRecords::getLoanRecoverId, loanRecoverId));
    }

    @Override
    public void confirmRemind(ConfirmRemindParam param) {
        OverdueRecords overdueRecords = overdueRecordsMapper.selectById(param.getId());
        overdueRecords.setRemindStatus(RemindStatusEnum.REMINDED.getV());
        overdueRecords.setNextRemindTime(overdueRecords.getNextRemindTime().plusDays(SysConfig.REMIND_INTERVAL.getV()));
        overdueRecordsMapper.updateById(overdueRecords);
    }
}
