package com.banana.info.mapper;

import com.banana.info.entity.CustomerLoanLimit;
import com.banana.info.entity.param.CustomerLoanLimitSearchParam;
import com.banana.info.entity.vo.CustomerLoanLimitSearchVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 客户贷款额度表 Mapper 接口
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
public interface CustomerLoanLimitMapper extends BaseMapper<CustomerLoanLimit> {

    Page<CustomerLoanLimitSearchVO> getCustomerLoanLimitList(@Param("param") CustomerLoanLimitSearchParam param, Page<CustomerLoanLimitSearchVO> objectPage);
}
