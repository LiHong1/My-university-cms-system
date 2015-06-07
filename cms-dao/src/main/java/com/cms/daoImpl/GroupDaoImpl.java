package com.cms.daoImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cms.beans.ChannelTree;
import com.cms.dao.GroupDao;
import com.cms.entity.Group;

import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
@Repository("groupDao")
@Transactional
public class GroupDaoImpl extends BaseDaoImpl<Group> implements GroupDao{

    public List<ChannelTree> getChannelTree(Long id) {
        return this.findAllBySql("select c.id as id,c.name as name,c.pid as pid from  channels_groups cg left join Channels c on(cg.channelId=c.id)where cg.GroupId="+id,ChannelTree.class);
        
    }

    public List<Long> listChannelIds(Long id) {
        return this.getSession().createSQLQuery("select cg.channelId from channels_groups cg where cg.groupId=?").setParameter(0, id).list();
    }

    public void addGroupChannel(int gid, int cid) {
      if(gid!=0&&cid!=0||getGroupChannel(gid, cid)!=0)
      {
      }
      
    }

    public void deleteGroupChannel(int gid, int cid) {
        getSession().createSQLQuery("delete  from channels_groups  where groupId=? and channelId=?").setParameter(0, gid).setParameter(1, cid).executeUpdate();
    }

    public Integer getGroupChannel(int gid, int cid) { 
        return (Integer) ((SQLQuery) getSession().createSQLQuery("select count(*) as count from channels_groups cg where cg.groupId=? and cg.channelId=?").setParameter(0, gid).setParameter(1, cid))
                .addScalar("count",IntegerType.INSTANCE).uniqueResult();
    }

    public List<Long> getGroupIds(long id) {
        List<BigInteger> groups=this.getSession().createSQLQuery("select groupId from groups_users where userId=?")
                          .setParameter(0, id).list();
        List<Long> groupIds=new ArrayList<Long>();
        for(int i=0;i<groups.size();i++){
            groupIds.add(new Long(groups.get(i).longValue()));
        }
        return groupIds;
    }

}
