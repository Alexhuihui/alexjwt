package top.alexmmd.alexjwt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.alexmmd.alexjwt.service.RedisIncrService;

/**
 * @author 汪永晖
 */
@Slf4j
@Service
public class RedisIncrServiceImpl implements RedisIncrService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Long incr(String code) {
        String key = "vote" + code;
        log.info("first value = {}", stringRedisTemplate.opsForValue().get(key));
        Long value = stringRedisTemplate.opsForValue().increment(key);
        log.info("last value = {}", value);
        return value;
    }
}
