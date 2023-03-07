package com.xxx.crm.interceptor;

import com.xxx.crm.dao.UserMapper;
import com.xxx.crm.exceptions.NoLoginException;
import com.xxx.crm.utils.AssertUtil;
import com.xxx.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/7 15:32
 * @description：
 */

/**
 * 非法访问拦截
 *      继承HandlerInterceptorAdapter适配器
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    // 注入UserMapper
    @Autowired
    private UserMapper userMapper;

    /**
     *
     * 1. 创建方法并继承HandlerInterceptorAdapter适配器，实现拦截功能
     * 2. 重写preHandle()方法（在目标方法（目标资源）执行前，执行的方法）
     *    - 方法返回boolean类型
     *    - true：表示目标方法可以被执行
     *    - false：表示阻止目标方法执行
     * 3. 如何判断用户是否是登录状态
     *    - 判断cookie中是否存在用户信息（获取用户ID）
     *    - 数据库中是否存在指定用户ID的值
     * 4. 如果用户是登录状态，则允许目标方法执行
     * 5. 如果用户是非登陆状态，则抛出未登录异常
     *    - 在全局异常中做判断
     *    - 如果是未登录异常，则跳转到登录页面
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取cookie中的用户ID
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 判断用户ID是否为空，且数据库中存在该ID的 用户记录
        if (null == userId || userMapper.selectByPrimaryKey(userId) == null) {
            // 抛出未登录异常
            throw new NoLoginException();
        }

        return true;
    }
}
