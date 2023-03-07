package com.xxx.crm.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/7 15:32
 * @description：
 */

/**
 * 非法访问拦截
 *      继承HandlerInterceptorAdapter适配器
 */
@Component
public class NoLoginInterceptor extends HandlerInterceptorAdapter {
}
