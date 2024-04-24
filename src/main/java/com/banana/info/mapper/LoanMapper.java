package com.banana.info.mapper;

import com.banana.info.entity.Loan;
import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.param.LoanApplyParam;
import com.banana.info.entity.vo.LoanApplyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 * 贷款表 Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
public interface LoanMapper extends BaseMapper<Loan> {

    Page<LoanApplyVO> getLoanPage(@Param("param") LoanApplyParam param, Page<LoanApplyVO> page);

}
