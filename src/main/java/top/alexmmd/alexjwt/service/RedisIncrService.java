package top.alexmmd.alexjwt.service;

/**
 * @author 汪永晖
 */
public interface RedisIncrService {

    Long incr(String code);
}
