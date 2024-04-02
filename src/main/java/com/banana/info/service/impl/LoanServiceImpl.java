package com.banana.info.service.impl;

import com.banana.info.entity.Loan;
import com.banana.info.mapper.LoanMapper;
import com.banana.info.service.ILoanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 贷款表 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@Service
public class LoanServiceImpl extends ServiceImpl<LoanMapper, Loan> implements ILoanService {

}
