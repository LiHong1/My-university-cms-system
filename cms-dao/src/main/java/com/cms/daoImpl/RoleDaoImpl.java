package com.cms.daoImpl;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cms.dao.RoleDao;
import com.cms.entity.Role;
@Repository("roleDao")
@Transactional
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{

    public List<Long> getRoleIds(long uid) {
        List<BigInteger> roles=this.getSession().createSQLQuery("select roleId from users_roles where userId=?")
                          .setParameter(0, uid).list();
        List<Long> roleIds=new ArrayList<Long>();
        for(int i=0;i<roles.size();i++){
            roleIds.add(new Long(roles.get(i).longValue()));
        }
        return roleIds;
    }

  


}
