package com.cms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cms.beans.ChannelTree;
import com.cms.dao.ChannelDao;
import com.cms.entity.Channel;
import com.cms.service.ChannelService;
@Service
public class ChannelServiceImpl extends BaseServiceImpl<Channel> implements ChannelService{
   @Inject
   private ChannelDao channelDao;

    public List<Channel> findChilds(Integer parentId) {
        return channelDao.findChilds(parentId);
    }
    
    public List<ChannelTree> findChannelTree() {
        
        return channelDao.findChannelTree();
    }
    public List<ChannelTree> findChannelTreeByParent(Integer pid) {
        
        return channelDao.findChannelTreeByParent(pid);
    }

    public void updateOrder(Long[] ids) {
        channelDao.updateOrder(ids);      
    }

    public void add(Channel channel, Long pid) {
        channel.setParent(channelDao.get(pid));
        channel.setOrders(channelDao.getMaxOrderByParent(pid)+1);
        channelDao.save(channel);
    }
}
