package com.xxx.crm.service;

import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.RoleMapper;
import com.xxx.crm.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/13 18:18
 * @description：
 */
@Service
public class RoleService extends BaseService<Role, Integer> {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有的角色列表
     * @return
     */
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleMapper.queryAllRoles(userId);
    }
}
