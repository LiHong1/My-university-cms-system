package com.cms.service;

import java.util.List;

import com.cms.beans.ChannelTree;
import com.cms.dao.BaseDao;
import com.cms.entity.Channel;

public interface ChannelService extends BaseDao<Channel>{
    /**
     * 根据父栏目查找子栏目
     * @param pid
     * @return
     */
    public List<Channel> findChilds(Integer pid);
    /**
     * 查找栏目树
     * @param id
     * @return
     */
    public List<ChannelTree> findChannelTree();
    /**
     * 根据父栏目查找子树
     * @return
     */
    public List<ChannelTree> findChannelTreeByParent(Integer pid);
    /**
     * 更新实体的排序
     * @param ids
     */
    public void updateOrder(Long[] ids);
    /**
     * 增加栏目
     * @param channel
     * @param pid
     */
    public void add(Channel channel, Long pid);
}
