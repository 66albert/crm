package com.xxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.CusDevPlanMapper;
import com.xxx.crm.query.CusDevPlanQuery;
import com.xxx.crm.query.SaleChanceQuery;
import com.xxx.crm.vo.CusDevPlan;
import com.xxx.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/10 21:59
 * @description：
 */
@Service
public class CusDevPlanService extends BaseService<CusDevPlan, Integer> {

    @Autowired
    private CusDevPlanMapper cusDevPlanMapper;

    /**
     * 多条件分页查询客户开发计划（返回的数据格式必须满足layui中数据表格要求的格式）
     * @param cusDevPlanQuery
     * @return
     */
    public Map<String, Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery) {
        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(cusDevPlanQuery.getPage(), cusDevPlanQuery.getLimit());
        // 得到分页对象
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(cusDevPlanMapper.selectByParams(cusDevPlanQuery));

        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data", pageInfo.getList());

        return map;
    }
}
