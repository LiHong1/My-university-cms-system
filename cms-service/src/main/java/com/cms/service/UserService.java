package com.cms.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cms.beans.ChannelTree;
import com.cms.dao.BaseDao;
import com.cms.entity.User;

/**
 * 用户接口
 * @author li hong
 *
 */
public interface UserService extends BaseDao<User>{
   /**
    * 更改状态
    */
   public void updateStatus(long id);
   /**
    * 判断是否登陆成功
    * @param username
    * @param password
    * @return
    */
   public User login(String username, String password);
   /**
    * 修改密码
    * @param id
    * @param oldPwd
    * @param password
    * @return
    */
   public Boolean updatePwd(Long id, String oldPwd, String password);
   /**
    * 获取用户的栏目树
    * @param uid
    * @return
    */
   public List<ChannelTree> getChannelTree(Long uid);
   
}
