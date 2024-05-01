package com.banana.info.entity.param;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerLoanLimitSearchParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户账号
     */
    private String customerAccount;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户联系方式
     */
    private String customerPhone;

    /**
     * 贷款额度等级S(100亿元)、A+(10亿元)、A(1亿元)、A-(1千万元)、B(1百万元)、C+(50万元)、C(10万元)、D(1万元)、E(5千元)、F(2千元)、G(0元)
     */
    private String loanLimitLevel;

    /**
     * 评估状态(1待评估 2已评估)
     */
    private Integer evaluateStatus;

    /**
     * 评估日期
     */
    private List<LocalDateTime> evaluateDate;

    /**
     * 评估开始日期
     */
    private LocalDateTime evaluateStartDate;

    /**
     * 评估结束日期
     */
    private LocalDateTime evaluateEndDate;

    public void dateTimeInit() {
        if (CollectionUtils.isNotEmpty(evaluateDate)) {
            evaluateStartDate = evaluateDate.get(0);
            evaluateEndDate = evaluateDate.get(1);
        }
    }
}
