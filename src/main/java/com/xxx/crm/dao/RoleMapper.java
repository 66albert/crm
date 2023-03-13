package com.xxx.crm.dao;

import com.xxx.crm.base.BaseMapper;
import com.xxx.crm.vo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper extends BaseMapper<Role, Integer> {

    /**
     * 查询所有的角色列表，返回集合
     *      只需要id与roleName
     */
    public List<Map<String, Object>> queryAllRoles();
}