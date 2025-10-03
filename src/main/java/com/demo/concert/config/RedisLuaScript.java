package com.demo.concert.config;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisLuaScript {
    private final StringRedisTemplate redisTemplate;

    private static final String RESERVE_LUA =
        "local stock = tonumber(redis.call('GET', KEYS[1])) " +
            "if stock == nil or stock <= 0 then return 0 end " +
            "if redis.call('SISMEMBER', KEYS[2], ARGV[1]) == 1 then return -1 end " +
            "redis.call('DECR', KEYS[1]) " +
            "redis.call('SADD', KEYS[2], ARGV[1]) " +
            "return 1 ";

    public Long tryReserve(String concertId, String userId) {
        String stockKey = "concert:" + concertId + ":stock";
        String userKey = "concert:" + concertId + ":users";
        return redisTemplate.execute(
            new DefaultRedisScript<>(RESERVE_LUA, Long.class),
            Arrays.asList(stockKey, userKey),
            userId
        );
    }
}
