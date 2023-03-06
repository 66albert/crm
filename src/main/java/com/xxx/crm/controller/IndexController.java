package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.service.UserService;
import com.xxx.crm.utils.LoginUserUtil;
import com.xxx.crm.vo.User;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/6 17:20
 * @description：
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 系统登录页
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * 后端管理主页面
     *
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request) {

        // 通过获取cookie中的用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 查询用户对象，设置session作用域
        User user = userService.selectByPrimaryKey(userId);
        request.getSession().setAttribute("user", user);

        return "main";
    }
}
