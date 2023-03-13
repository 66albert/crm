package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.exceptions.ParamsException;
import com.xxx.crm.model.UserMode;
import com.xxx.crm.query.UserQuery;
import com.xxx.crm.service.UserService;
import com.xxx.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/6 21:23
 * @description：
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录功能
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd) {

        ResultInfo resultInfo = new ResultInfo();

        // 调用service层的登录方法
        UserMode userMode = userService.userLogin(userName, userPwd);

        // 设置resultInfo的result（将数据返回给请求）
        resultInfo.setResult(userMode);

       /* // 通过try catch铺货service层的异常，如果service层抛出异常，则登陆失败，否则登录成功
        try {
            // 调用service层的登录方法
            UserMode userMode = userService.userLogin(userName, userPwd);

            // 设置resultInfo的result（将数据返回给请求）
            resultInfo.setResult(userMode);

        } catch (ParamsException e) {
            resultInfo.setCode(e.getCode());
            resultInfo.setMsg(e.getMsg());
            e.printStackTrace();
        } catch (Exception e) {
            resultInfo.setCode(500);
            resultInfo.setMsg("登录失败！");
            e.printStackTrace();
        }*/

        return resultInfo;
    }

    /**
     * 用户修改密码
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param repeatPassword
     * @return
     */
    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
                                         String oldPassword, String newPassword, String repeatPassword) {

        ResultInfo resultInfo = new ResultInfo();
        // 获取cookie中的用户ID
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 调用service层的修改密码方法
        userService.updatePassword(userId, oldPassword, newPassword, repeatPassword);


        /*ResultInfo resultInfo = new ResultInfo();
        try {
            // 获取cookie中的用户ID
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            // 调用service层的修改密码方法
            userService.updatePassword(userId, oldPassword, newPassword, repeatPassword);
        } catch (ParamsException p) {
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        } catch (Exception e) {
            resultInfo.setCode(500);
            resultInfo.setMsg("修改密码失败！");
            e.printStackTrace();
        }*/
        return resultInfo;
    }

    /**
     * 进入修改密码的页面
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {

        return "user/password";
    }

    /**
     * 查询所有的销售人员
     * @return
     */
    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales(){
        return userService.queryAllSales();
    }

    /**
     * 分页多条件查询用户列表
     *
     * @param userQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> selectByParams(UserQuery userQuery) {
        return userService.queryByParamsForTable(userQuery);
    }

    /**
     * 进入用户列表页面
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "user/user";
    }
}
