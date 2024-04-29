package com.banana.info.mapper;

import com.banana.info.entity.CustomerCredit;
import com.banana.info.entity.param.CustomerCreditSearchParam;
import com.banana.info.entity.vo.CustomerCreditSearchVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 客户信用表 Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
public interface CustomerCreditMapper extends BaseMapper<CustomerCredit> {

    Page<CustomerCreditSearchVO> getCustomerCreditList(@Param("param") CustomerCreditSearchParam param, Page<CustomerCreditSearchVO> objectPage);
}
