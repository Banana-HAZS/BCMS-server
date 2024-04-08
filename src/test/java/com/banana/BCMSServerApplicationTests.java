package com.banana;


import com.banana.info.mapper.EmployeeMapper;
import com.banana.tool.UniqueCodeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BCMSServerApplicationTests {

    @Resource
    private UniqueCodeGenerator uniqueCodeGenerator;

    @Test
    void test() {
        String s = uniqueCodeGenerator.generateUniqueCode();
        System.out.println(s);
    }
}
