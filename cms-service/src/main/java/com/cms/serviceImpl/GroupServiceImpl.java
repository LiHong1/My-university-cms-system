package com.cms.serviceImpl;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.beans.ChannelTree;
import com.cms.dao.ChannelDao;
import com.cms.dao.GroupDao;
import com.cms.entity.Channel;
import com.cms.entity.Group;
import com.cms.service.GroupService;

@Service("groupService")
@Transactional
public class GroupServiceImpl extends BaseServiceImpl<Group> implements GroupService{
    @Inject
    private GroupDao groupDao;
    @Inject
    private ChannelDao channelDao;
    public List<ChannelTree> getChannelTree(Long id) {
        return groupDao.getChannelTree(id);
    }
    public List<Long> listChannelIds(Long id) {
        return groupDao.listChannelIds(id);
    }
    public void addGroupChannel(int gid, int cid) {
        Group group=groupDao.get(gid);
        System.out.println(group);
        Channel channel=channelDao.get(cid);
        
        if(group!=null&&channel!=null){
            Set<Channel> channels=group.getChannels();
            if(!channels.contains(channel)){
                channels.add(channel);
            }
            this.update(group);
        }
        groupDao.addGroupChannel(gid,cid);
    }
    public void deleteGroupChannel(int gid, int cid) {
        groupDao.deleteGroupChannel(gid,cid);
    }
    public List<Long> getGroupIds(long id) {
        return groupDao.getGroupIds(id);
    }
}
