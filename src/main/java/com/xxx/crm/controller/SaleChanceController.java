package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.query.SaleChanceQuery;
import com.xxx.crm.service.SaleChanceService;
import com.xxx.crm.utils.CookieUtil;
import com.xxx.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 进入营销机会管理页面
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "/saleChance/sale_chance";
    }

    /**
     * 添加营销机会
     * @param saleChance
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addSalaChance(SaleChance saleChance, HttpServletRequest request) {
        // 从cookie中获取当前登录用户名
        String userName = CookieUtil.getCookieValue(request, "userName");
        // 设置用户名到营销机会对象中
        saleChance.setCreateMan(userName);
        // 调用Service层的添加方法
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会数据添加成功！");
    }

    /**
     * 更新营销机会
     * @param saleChance
     * @param request
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance, HttpServletRequest request) {
        // 从cookie中获取当前登录用户名
        String userName = CookieUtil.getCookieValue(request, "userName");
        // 设置用户名到营销机会对象中
        saleChance.setCreateMan(userName);
        // 调用Service层的添加方法
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功！");
    }

    /**
     * 进入添加或者修改营销机会数据页面
     * @return
     */
    @RequestMapping("toSaleChancePage")
    public String toSaleChancePage(Integer saleChanceId, HttpServletRequest request) {
        // 判断saleChance是否为空
        if (saleChanceId != null) {
            // 通过id查询营销机会对象
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
            // 将数据设置到请求中
            request.setAttribute("saleChance", saleChance);
        }
        return "saleChance/add_update";
    }
}
