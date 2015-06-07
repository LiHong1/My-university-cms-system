package com.cms.daoImpl;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cms.beans.ChannelTree;
import com.cms.dao.UserDao;
import com.cms.entity.User;
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

    public List<ChannelTree> getChannelTree(Long uid) {
        return this.findAllBySql("select distinct c.id as id,c.name as name,c.pid as pid from  channels_groups cg left join Channels c on(cg.channelId=c.id)"
                + "  where cg.GroupId in (select groupid from groups_users where userid="+uid+")",ChannelTree.class);
    }
}
