package com.cms.dao;

import java.math.BigInteger;
import java.util.List;

import com.cms.beans.ChannelTree;
import com.cms.entity.Group;


public interface GroupDao extends BaseDao<Group>{

    List<ChannelTree> getChannelTree(Long id);

    List<Long> listChannelIds(Long id);

    public void addGroupChannel(int gid, int cid);

    public void deleteGroupChannel(int gid, int cid);
    
    public Integer getGroupChannel(int gid, int cid);

    List<Long> getGroupIds(long id);

}
