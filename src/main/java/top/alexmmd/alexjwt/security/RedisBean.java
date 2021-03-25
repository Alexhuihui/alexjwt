package top.alexmmd.alexjwt.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.alexmmd.alexjwt.util.GenerateVerificationCode;

import javax.annotation.PostConstruct;

/**
 * @author 汪永晖
 * @creatTime 2021/3/25 11:18
 */
@Component
@Slf4j
public class RedisBean {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private GenerateVerificationCode generateVerificationCode;

    public static RedisTemplate redisTemplate;

    public static GenerateVerificationCode generateCode;

    @PostConstruct
    public void getRedisTemplate() {
        redisTemplate = stringRedisTemplate;
    }

    @PostConstruct
    public void getGenerateVerificationCode() {
        generateCode = generateVerificationCode;
    }
}
