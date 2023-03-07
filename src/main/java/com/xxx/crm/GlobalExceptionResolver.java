package com.xxx.crm;

import com.alibaba.fastjson.JSON;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/7 14:29
 * @description：
 */

/**
 * 全局异常统一处理
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    /**
     * 异常处理方法
     *  方法的返回值
     *      1.返回视图
     *      2.返回数据（JSON数据）
     *  如何判断方法的返回值？
     *      通过方法上是否声明@ResponseBody注解
     *          如果未声明，则表示返回视图
     *          如果声明了，则表示返回数据
     * @param request    request请求对象
     * @param response   response响应对象
     * @param handler 方法对象
     * @param e 异常对象
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

        /**
         * 设置默认的异常处理（返回视图）
         */
        ModelAndView modelAndView = new ModelAndView("error");
        // 设置异常信息
        modelAndView.addObject("code", 500);
        modelAndView.addObject("msg","系统异常请重试！");

        // 判断HandlerMethod
        if (handler instanceof HandlerMethod) {
            // 类型转换
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 通过反射获取方法上声明的@ResponseBode注解对象
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            // 判断ResponseBody 对象是否为空（如果对象为空，则表示返回的是视图；如果不为空，则表示返回的是数据）
            if (responseBody == null) {
                /**
                 * 方法返回视图
                 */
                // 判断异常类型
                if (e instanceof ParamsException) {
                    ParamsException p = (ParamsException) e;
                    // 设置异常信息
                    modelAndView.addObject("code",p.getCode());
                    modelAndView.addObject("msg", p.getMsg());
                }
                return modelAndView;
            } else {
                /**
                 * 方法返回数据
                 */
                // 设置默认的异常处理
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("系统异常请重试！");

                // 判断异常类型是否是自定义异常
                if (e instanceof ParamsException) {
                    // 设置异常信息
                    ParamsException p = (ParamsException) e;
                    resultInfo.setCode(p.getCode());
                    resultInfo.setMsg(p.getMsg());
                }
                // 使用流将数据响应出去
                // 设置响应类型及编码格式（响应JSON格式的数据）
                response.setContentType("application/json;charset=UTF-8");
                // 得到字符输出流
                PrintWriter out = null;
                try {
                    // 得到输出流
                    out = response.getWriter();
                    // 将需要返回的对象转换成JSON格式的字符串
                    String json = JSON.toJSONString(resultInfo);
                    // 输出数据
                    out.write(json);

                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    // 如果对象不为空，则关闭对象
                    if (out != null) {
                        out.close();
                    }
                }
                return null;
            }
        }

        return modelAndView;
    }
}
