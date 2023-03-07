package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.query.SaleChanceQuery;
import com.xxx.crm.service.SaleChanceService;
import com.xxx.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/7 17:21
 * @description：
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    /**
     * 营销机会数据查询（分页多条件查询）
     * @param saleChanceQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery) {
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }
}
