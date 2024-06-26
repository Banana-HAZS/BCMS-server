package com.banana.info.entity.param;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OverdueRecordsSearchParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 贷款收回流水号
     */
    private String loanRecoverNo;

    /**
     * 客户账号
     */
    private String customerAccount;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 逾期时长类型(1普通逾期、2较长逾期、3严重逾期)
     */
    private Integer overdueDurationType;

    /**
     * 提醒状态(1暂无、2还款提醒、3已提醒)
     */
    private Integer remindStatus;

    /**
     * 客户联系方式
     */
    private String customerPhone;

}
