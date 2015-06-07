package com.cms.dao;

import java.util.List;

import com.cms.beans.ChannelTree;
import com.cms.entity.Channel;

public interface ChannelDao extends BaseDao<Channel>{
    /**
     * 根据父栏目查找子栏目
     * @param parentId
     * @return
     */
    public List<Channel> findChilds(Integer parentId);
    /**
     * 查找栏目树
     * @param id
     * @return
     */
    public List<ChannelTree> findChannelTree();
    /**
     * 根据父栏目查找子树
     * @param pid
     * @return
     */
    public List<ChannelTree> findChannelTreeByParent(Integer pid);
    /**
     * 获取子栏目的数量
     * @param pid
     * @return
     */
    public int getMaxOrderByParent(Long pid);
    /**
     * 跟新顺序
     * @param ids
     */
    public void updateOrder(Long[] ids);
}
