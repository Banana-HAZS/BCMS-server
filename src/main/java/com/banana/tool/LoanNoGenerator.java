package com.banana.tool;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class LoanNoGenerator {
    private JedisPool jedisPool;
    private static final String COUNTER_KEY = "my_bank";

    public LoanNoGenerator() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    }

    public String generateUniqueCode() {
        try (Jedis jedis = jedisPool.getResource()) {
            if (!jedis.exists(COUNTER_KEY)) {
                jedis.set(COUNTER_KEY, "0");
            }

            Long counter = jedis.incr(COUNTER_KEY);
            if (counter > 99999999L) {
                // 处理计数器达到上限的情况
                // 可以重新初始化计数器值或进行其他处理
                throw new RuntimeException("计数器以达到最大值！");
            }

            jedis.save(); // 持久化计数器值到磁盘

            String uniqueCode = String.format("%s%08d", COUNTER_KEY, counter);
            return uniqueCode;
        }
    }

    public void close() {
        jedisPool.close();
    }
}