package com.banana;


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
    private LoanNoGenerator loanNoGenerator;

    @Resource
    private LoanMapper loanMapper;

    @Test
    void test() {
        LocalDateTime dateTime1 = LocalDateTime.of(2022, 1, 1, 12, 0, 0,1000000);
        System.out.println(dateTime1);
        LocalDateTime dateTime2 = LocalDateTime.of(2022, 1, 1, 12, 0);
        System.out.println(dateTime2);
        long millis = Duration.between(dateTime1, dateTime2)
                .toMillis();
        System.out.println(millis);
        long days = (millis + 86_400_000 - 1) / 86_400_000;
        // long days = ChronoUnit.DAYS.between(dateTime1, dateTime2);
        System.out.println(days);
    }
}
