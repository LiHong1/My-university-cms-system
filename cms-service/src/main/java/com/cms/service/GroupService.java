package com.cms.service;

import java.util.List;

import com.cms.beans.ChannelTree;
import com.cms.dao.BaseDao;
import com.cms.entity.Group;

/**
 * 用户接口
 * @author li hong
 *
 */
public interface GroupService extends BaseDao<Group>{
    /**
     * 获取组中的栏目树
     * @param id
     * @return
     */
    List<ChannelTree> getChannelTree(Long id);
    /**
     * 获取组中的栏目树id
     * @param id
     * @return
     */
    List<Long> listChannelIds(Long id);
    /**
     * 增加组栏目
     * @param gid
     * @param cid
     */
    public void addGroupChannel(int gid, int cid);
    /**
     * 删除组栏目
     * @param gid
     * @param cid
     */
    public void deleteGroupChannel(int gid, int cid);
    List<Long> getGroupIds(long id);
 
   
}
