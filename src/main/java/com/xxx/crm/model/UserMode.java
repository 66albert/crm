package com.xxx.crm.model;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/6 22:01
 * @description：
 */
public class UserMode {

    // private Integer userId;
    private String userName;
    private String trueName;

    private String userIdStr;   // 加密后的用户id

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }
    /*public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
