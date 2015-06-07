package com.cms.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dao.RoleDao;
import com.cms.entity.Role;
import com.cms.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
     @Autowired
     private RoleDao roleDao;
    public List<Long> getRoleIds(long uid) {
        return roleDao.getRoleIds(uid);
    }
   

}
