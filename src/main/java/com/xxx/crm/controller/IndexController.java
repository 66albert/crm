package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/6 17:20
 * @description：
 */
@Controller
public class IndexController extends BaseController {
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
    public String main() {
        return "main";
    }
}
