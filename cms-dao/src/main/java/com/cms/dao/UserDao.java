package com.cms.dao;


import java.util.List;

import com.cms.beans.ChannelTree;
import com.cms.entity.User;

public interface UserDao extends BaseDao<User>{
    /**
     * 列出用户的栏目树
     * @param uid
     * @return
     */
    List<ChannelTree> getChannelTree(Long uid);

}
