package com.xxx.crm.service;

import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.UserMapper;
import com.xxx.crm.model.UserMode;
import com.xxx.crm.utils.AssertUtil;
import com.xxx.crm.utils.Md5Util;
import com.xxx.crm.utils.PhoneUtil;
import com.xxx.crm.utils.UserIDBase64;
import com.xxx.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * 修改密码的操作
     * 1. 接收四个参数（用户ID，原始密码，新密码，确认密码）
     * 2. 通过用户ID查询用户记录，返回用户对象
     * 3. 参数校验
     *    - 待更新用户记录是否存在（用户对象是否为空）
     *    - 原始密码是否为空
     *    - 原始密码是否正确（查询的用户对象中的用户密码是否与原始密码一致）
     *    - 判断新密码是否为空
     *    - 判断新密码是否与原始密码一致（不允许新密码与原始密码一致）
     *    - 判断确认密码是否为空
     *    - 判断确认密码是否与新密码一致
     * 4. 设置用户新密码
     *    - 需要将新密码通过指定算法进行加密（md5加密）
     * 5. 执行更新操作，判断受影响的行数
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassword(Integer userId, String oldPwd, String newPwd, String repeatPwd) {
        // 通过用户ID查询用户记录，返回用户对象
        User user = userMapper.selectByPrimaryKey(userId);
        // 判断用户记录是否存在
        AssertUtil.isTrue(null == user, "待更新记录不存在！");

        // 参数校验
        checkPasswordParams(user, oldPwd, newPwd, repeatPwd);

        // 设置用户新密码
        user.setUserPwd(Md5Util.encode(newPwd));

        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "用户修改密码失败！");
    }

    /**
     * 修改密码的参数校验
     * - 待更新用户记录是否存在（用户对象是否为空）
     * - 原始密码是否为空
     * - 原始密码是否正确（查询的用户对象中的用户密码是否与原始密码一致）
     * - 判断新密码是否为空
     * - 判断新密码是否与原始密码一致（不允许新密码与原始密码一致）
     * - 判断确认密码是否为空
     * - 判断确认密码是否与新密码一致
     * @param user
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     */
    private void checkPasswordParams(User user, String oldPwd, String newPwd, String repeatPwd) {
        // 原始密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd), "原始密码不能为空！");
        // 原始密码是否正确（查询的用户对象中的用户密码是否与原始密码一致）
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)), "原始密码不正确！");

        // 判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd), "新密码不能为空！");
        // 判断新密码是否与原始密码一致（不允许新密码与原始密码一致）
        AssertUtil.isTrue(oldPwd.equals(newPwd), "新密码不能与原始密码相同！");
        // 判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd), "确认密码不能为空!");
        // 判断确认密码是否与新密码一致
        AssertUtil.isTrue(!newPwd.equals(repeatPwd), "确认密码与新密码不一致！");
    }

    /**
     * 构建需要返回给客户端的用户对象
     * @param user
     */
    private UserMode buildUserInfo(User user) {
        UserMode userMode = new UserMode();
        // userMode.setUserId(user.getId());
        // 设置加密过得用户id
        userMode.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
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

    /**
     * 查询所有的销售人员
     * @return
     */
    public List<Map<String, Object>> queryAllSales() {
        return userMapper.queryAllSales();
    }

    /**
     * 1. 参数校验
     *    - 用户名userName   非空，且唯一
     *    - 邮箱email    非空
     *    - 手机号码phone    非空，且格式正确
     * 2. 设置参数的默认值
     *    - is_valid是否有效     1=有效
     *    - createDate创建时间     系统当前时间
     *    - updateDate更新时间    系统当前时间
     *    - userPwd密码     默认密码   123456 -> md5加密
     * 3. 执行添加操作，判断受影响的行数
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        // 1. 参数校验
        checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(), null);

        // 2. 设置参数的默认值
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        // 设置默认密码
        user.setUserPwd(Md5Util.encode("123456"));

        // 3. 执行添加操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.insertSelective(user) != 1, "用户添加失败！");
    }

    /**
     * 1. 参数校验
     *      - 用户名userName   非空，且唯一
     *      - 邮箱email    非空
     *      - 手机号码phone    非空，且格式正确
     * @param userName
     * @param email
     * @param phone
     */
    private void checkUserParams(String userName, String email, String phone, Integer userId) {
        // 判断用户名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        // 判断用户名的唯一性
        // 通过用户名查询用户对象
        User temp = userMapper.queryUserByName(userName);
        // 如果用户对象为空，则表示用户名可用；如果对象不为空，则表示用户名不可用
        /**
         * 如果是添加操作，数据库中无数据，只要通过名称查到数据，则表示用户名被占用
         * 如果是修改操作，数据库中有对应的记录，通过用户名查询到记录，可能是当前记录本身，也可能是别的记录
         * 如果用户名存在，且与当前修改记录不是同一个，则表示其它记录占用了该用户名，不可用
         */
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(userId)), "用户名已存在，请重试！");
        // 邮箱非空
        AssertUtil.isTrue(StringUtils.isBlank(email), "用户邮箱不能为空！");
        // 手机号非空
        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号不能为空！");
        // 手机号格式判断
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "手机号格式错误，请重试！");
    }

    /**
     * 更新用户
     *  1.参数校验
     *      判断用户id是否为空，且数据存在
     *      - 用户名userName   非空，且唯一
     *      - 邮箱email    非空
     *      - 手机号码phone    非空，且格式正确
     *  2.设置参数的默认值
     *      updateDate  系统当前时间
     *  3.执行更新操作，判断受影响的行数
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        // 判断用户id是否为空，且数据存在
        AssertUtil.isTrue(null == user.getId(), "待更新记录不存在！");
        // 通过id查询数据
        User temp = userMapper.selectByPrimaryKey(user.getId());
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");
        // 参数校验
        checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(), temp.getId());

        // 设置默认值
        user.setUpdateDate(new Date());

        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) != 1, "用户更新失败！");
    }

}
