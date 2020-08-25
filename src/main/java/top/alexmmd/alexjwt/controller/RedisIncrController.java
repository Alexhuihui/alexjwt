package top.alexmmd.alexjwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.alexjwt.model.ResultUtils;

/**
 * @author 汪永晖
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisIncrController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/incr/{code}")
    public ResultUtils incr(@PathVariable String code) {
        String key = "vote" + code;
        log.info("last value = {}", stringRedisTemplate.opsForValue().get(key));
        Long value = stringRedisTemplate.opsForValue().increment(key);
        log.info("last value = {}", value);

        return new ResultUtils(100, "成功返回", value);
    }
}
