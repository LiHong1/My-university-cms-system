package com.cms.daoImpl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cms.beans.ChannelTree;
import com.cms.dao.ChannelDao;
import com.cms.entity.Channel;
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class ChannelDaoImpl extends BaseDaoImpl<Channel> implements ChannelDao{
    
    public List<Channel> findChilds(Integer parentId) {
       String hql;
        if(parentId==null||parentId==0)
          hql="from Channel where pid is null order by orders";
        else hql="from Channel where pid="+parentId+" order by orders";   
          return   this.findAllByHql(hql);
    }

   
    public List<ChannelTree> findChannelTree() {
        return this.findAllBySql("select id,name,pid from Channels order by orders",ChannelTree.class);
    }

    public List<ChannelTree> findChannelTreeByParent(Integer pid) {
        return this.findAllBySql("select id,name,pid from channels where pid="+pid+" order by orders",ChannelTree.class);
    }


    public int getMaxOrderByParent(Long pid) {
        String hql;
        if(pid==null||pid==0){
            hql="select max(c.orders) from Channel c where c.parent is null";
        }else
            hql="select max(c.orders) from Channel c where c.parent.id="+pid;
        return (Integer) this.findByHql(hql);
    }


    public void updateOrder(Long[] ids) {
        int index = 1;
        String hql = "update Channel c set c.orders=? where c.id=?";
        for(long id:ids) {
            this.getSession().createQuery(hql).setParameter(0, index++).setParameter(1, id).executeUpdate();
        }
    }




}
