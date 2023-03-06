package com.xxx.crm.service;

import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.UserMapper;
import com.xxx.crm.model.UserMode;
import com.xxx.crm.utils.AssertUtil;
import com.xxx.crm.utils.Md5Util;
import com.xxx.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/6 21:22
 * @description：
 */
@Service
public class UserService extends BaseService<User, Integer> {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录操作
     * 1. 参数判断，判断用户姓名、用户密码非空
     *    - 如果参数为空，抛出异常（异常会被控制层捕获并处理）
     * 2. 调用数据访问层，通过用户名查询用户记录，返回用户对象
     * 3. 判断用户对象是否为空
     *    - 如果用户对象为空，抛出异常（异常会被控制层捕获并处理）
     * 4. 判断密码是否正确，比较客户端传递的用户密码与数据库中查询到的用户对象中的用户密码是否相等
     *    - 如果密码不相等，抛出异常（异常会被控制层捕获并处理）
     * 5. 如果密码正确，则登录成功
     * @param userName
     * @param userPwd
     */
    public UserMode userLogin(String userName, String userPwd) {
        // 1. 参数判断，判断用户姓名、用户密码非空
        checkLoginParams(userName,userPwd);

        // 2. 调用数据访问层，通过用户名查询用户记录，返回用户对象
        User user = userMapper.queryUserByName(userName);

        // 3. 判断用户对象是否为空
        AssertUtil.isTrue(user == null, "用户姓名不存在！");

        // 4. 判断密码是否正确，比较客户端传递的用户密码与数据库中查询到的用户对象中的用户密码是否相等
        checkUserPwd(userPwd, user.getUserPwd());

        // 返回构建的用户对象
        return buildUserInfo(user);
    }

    /**
     * 构建需要返回给客户端的用户对象
     * @param user
     */
    private UserMode buildUserInfo(User user) {
        UserMode userMode = new UserMode();
        userMode.setUserId(user.getId());
        userMode.setUserName(user.getUserName());
        userMode.setTrueName(user.getTrueName());
        return userMode;
    }

    /**
     * 密码判断
     *      先将客户端传递的面加密，再与数据库中查询到的密码做比较
     * @param userPwd
     * @param pwd
     */
    private void checkUserPwd(String userPwd, String pwd) {
        // 先将客户端传递的面加密
        userPwd = Md5Util.encode(userPwd);
        // 与数据库中查询到的密码做比较
        AssertUtil.isTrue(!userPwd.equals(pwd), "用户密码不正确！");
    }

    /**
     * 参数校验
     *      如果参数为空，抛出异常（异常会被控制层捕获并处理）
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        // 判断用户姓名
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户姓名不能为空！");
        // 验证用户密码
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空！");
    }
}
