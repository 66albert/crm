package com.xxx.crm.dao;

import com.xxx.crm.base.BaseMapper;
import com.xxx.crm.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User, Integer> {

    public User queryUserByName(String userName);

    /**
     * 查询所有的销售人员
     * @return
     */
    List<Map<String, Object>> queryAllSales();

}