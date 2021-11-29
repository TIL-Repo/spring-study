package me.hajoo.java.config;

import me.hajoo.java.interceptor.FirstInterceptor;
import me.hajoo.java.interceptor.SecondInterceptor;
import me.hajoo.java.interceptor.ThirdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FirstInterceptor())
                .order(1)
                .addPathPatterns("/*")
                .excludePathPatterns("/second-interceptor");
        registry.addInterceptor(new SecondInterceptor())
                .order(0)
                .addPathPatterns("/", "/second-interceptor");
        registry.addInterceptor(new ThirdInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/second-interceptor");
    }
}
