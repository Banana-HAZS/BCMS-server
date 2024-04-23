package com.banana;


import com.banana.info.mapper.LoanMapper;
import com.banana.tool.LoanNoGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
class BCMSServerApplicationTests {

    @Resource
    private LoanNoGenerator loanNoGenerator;

    @Resource
    private LoanMapper loanMapper;

    @Test
    void test() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 1, 2, 0, 0);
        System.out.println(dateTime.getDayOfMonth());
    }
}
