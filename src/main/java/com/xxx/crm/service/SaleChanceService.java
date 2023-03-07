package com.xxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.SaleChanceMapper;
import com.xxx.crm.query.SaleChanceQuery;
import com.xxx.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/7 17:19
 * @description：
 */
@Service
public class SaleChanceService extends BaseService<SaleChance, Integer> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    /**
     * 多条件分页查询营销机会（返回的数据格式必须满足layui中数据表格要求的格式）
     * @param saleChanceQuery
     * @return
     */
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery) {
        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getLimit());
        // 得到分页对象
        PageInfo<SaleChance> pageInfo = new PageInfo<SaleChance>(saleChanceMapper.selectByParams(saleChanceQuery));

        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data", pageInfo.getList());

        return map;
    }
}
