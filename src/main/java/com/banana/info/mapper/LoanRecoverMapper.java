package com.banana.info.mapper;

import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.entity.vo.LoanRecoverSearchVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 贷款收回表 Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
public interface LoanRecoverMapper extends BaseMapper<LoanRecover> {

    Page<LoanRecoverSearchVO> getLoanRecoverPage(@Param("param") LoanRecoverSearchParam param, Page<LoanRecoverSearchVO> page);
}
