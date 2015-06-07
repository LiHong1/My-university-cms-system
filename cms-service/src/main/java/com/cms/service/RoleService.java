package com.cms.service;

import java.util.List;

import com.cms.dao.BaseDao;
import com.cms.entity.Role;

/**
 * 用户接口
 * @author li hong
 *
 */
public interface RoleService extends BaseDao<Role>{
    /**
     * 获取用户
     * @param uid
     * @return
     */
    public List<Long> getRoleIds(long uid);
  
   
}
