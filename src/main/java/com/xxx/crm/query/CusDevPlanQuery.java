package com.xxx.crm.query;

import com.xxx.crm.base.BaseQuery;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/10 22:01
 * @description：
 */
public class CusDevPlanQuery extends BaseQuery {

    private Integer saleChanceId;   // 营销机会主键

    public Integer getSaleChanceId() {
        return saleChanceId;
    }

    public void setSaleChanceId(Integer saleChanceId) {
        this.saleChanceId = saleChanceId;
    }
}
