package com.cms.dao;


import java.util.List;

import com.cms.entity.Role;

public interface RoleDao extends BaseDao<Role>{
    /**
     * 根据用户id获取角色id
     * @param uid
     * @return
     */
    public List<Long> getRoleIds(long uid);

}
