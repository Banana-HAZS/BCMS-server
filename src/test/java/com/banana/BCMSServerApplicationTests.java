package com.banana;


import com.banana.info.entity.CustomerCredit;
import com.banana.info.entity.CustomerLoanLimit;
import com.banana.info.entity.commonEnum.EvaluateStatusEnum;
import com.banana.info.entity.commonEnum.LoanLimitEnum;
import com.banana.info.entity.commonEnum.SysConfig;
import com.banana.info.mapper.CustomerCreditMapper;
import com.banana.info.mapper.CustomerLoanLimitMapper;
import com.banana.info.mapper.LoanMapper;
import com.banana.tool.LoanNoGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class BCMSServerApplicationTests {

    @Resource
    private CustomerLoanLimitMapper customerLoanLimitMapper;

    @Resource
    private CustomerCreditMapper customerCreditMapper;

    @Test
    void test() {
        for(int i=1;i<=4;++i){
            // 同步创建客户信用评级
            CustomerCredit customerCredit = new CustomerCredit();
            customerCredit.setCustomerId(i);
            // 默认信用分600，评级C
            customerCreditMapper.insert(customerCredit);
        }
    }
}
