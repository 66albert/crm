package com.xxx.crm.query;

import com.xxx.crm.base.BaseQuery;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/7 17:36
 * @description：
 */

/**
 * 营销机会查询类
 */
public class SaleChanceQuery extends BaseQuery {

    // 分页参数
    /**
     * 继承的BaseQuery类，BaseQuery类中以定义分页参数
     */

    // 营销机会管理 条件查询
    private String customerName;    // 客户名
    private String createMan;   // 创建人
    private Integer state;  // 分配状态 0=未分配，1=已分配

    // 客户开发计划  条件查询
    private String devResult;   // 开发状态
    private Integer assignMan;   // 指派人

    public String getDevResult() {
        return devResult;
    }

    public void setDevResult(String devResult) {
        this.devResult = devResult;
    }

    public Integer getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(Integer assignMan) {
        this.assignMan = assignMan;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
