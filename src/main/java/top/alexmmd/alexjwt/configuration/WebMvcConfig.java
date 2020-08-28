package top.alexmmd.alexjwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.alexmmd.alexjwt.annotation.AutoIdempotent;
import top.alexmmd.alexjwt.interceptor.IdempotentInterceptor;

/**
 * @author 汪永晖
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    public IdempotentInterceptor idempotentInterceptor;

    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations and resource handler requests.
     * Interceptors can be registered to apply to all requests or be limited
     * to a subset of URL patterns.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**");
    }
}
