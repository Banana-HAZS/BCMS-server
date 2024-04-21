package com.banana;


import com.banana.info.mapper.LoanMapper;
import com.banana.tool.LoanNoGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BCMSServerApplicationTests {

    @Resource
    private LoanNoGenerator loanNoGenerator;

    @Resource
    private LoanMapper loanMapper;

    @Test
    void test() {
        String s = loanNoGenerator.generateUniqueCode();
        System.out.println(s);
    }
}
