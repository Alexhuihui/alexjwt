package top.alexmmd.alexjwt.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 生成和校验验证码
 *
 * @author 汪永晖
 * @creatTime 2021/3/25 16:11
 */
@Component
public class GenerateVerificationCode {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${message.timeout}")
    private long timeout;

    /**
     * 生成验证码，并根据用途存入 redis 中
     *
     * @param use    用途
     * @param mobile 手机号
     * @return 6 位数的验证码
     */
    public String generateVerification(String use, String mobile) {
        String identifyCode = this.getRandom();
        stringRedisTemplate.opsForValue().set(use + mobile, identifyCode, timeout, TimeUnit.MINUTES);
        return identifyCode;
    }

    /**
     * 随机生成 6 位数的验证码
     *
     * @return 6 位数的验证码
     */
    public String getRandom() {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            num = num.append(String.valueOf((int) Math.floor(Math.random() * 9 + 1)));
        }
        return num.toString();
    }

    /**
     * 校验短信验证码
     *
     * @param use       用途
     * @param mobile    手机号
     * @param inputCode 用户输入的验证码
     * @return true or false
     */
    public boolean checkVerificationCode(String use, String mobile, String inputCode) {
        String smsCode = stringRedisTemplate.opsForValue().get(use + mobile);
        if (smsCode == null || !smsCode.equals(inputCode)) {
            return false;
        }
        return true;
    }

}
