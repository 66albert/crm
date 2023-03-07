package com.xxx.crm.config;

import com.xxx.crm.interceptor.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/7 15:48
 * @description：
 */
@Configuration  // 表示是一个配置类
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean   // 将方法的返回值交给IOC实例化
    public NoLoginInterceptor noLoginInterceptor() {
        return new NoLoginInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 需要一个实现了拦截器功能的实例对象，这里使用noLoginInterceptor
        registry.addInterceptor(noLoginInterceptor())
                // 设置需要拦截的资源
                .addPathPatterns("/**")     // "/**"表示默认拦截所有资源
                // 设置需要放行的资源
                .excludePathPatterns("/css/**","/images/**","/lib/**","/index","/user/login","/js/**");
    }
}
