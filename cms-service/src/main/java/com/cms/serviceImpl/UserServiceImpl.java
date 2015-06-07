package com.cms.serviceImpl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.beans.ChannelTree;
import com.cms.beans.CmsException;
import com.cms.beans.PageBean;
import com.cms.dao.UserDao;
import com.cms.entity.User;
import com.cms.service.UserService;
import com.cms.utils.MDCoder;
import com.cms.utils.QueryHelper;

@Service("userService") 
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Inject
    public UserDao userDao;
 
    public void updateStatus(long id) {
        User user=userDao.get(id);
        if(user.getStatus()==1)
            user.setStatus(0);
        else user.setStatus(1);
        userDao.update(user);
       // throw new CmsException("dfds");
    }

    public User login(String username, String password) {
        User user=userDao.find("username", username);
        String truePassword=MDCoder.encodeMD5Hex(password);
        if(user!=null&&user.getPassword().equals(truePassword))
            return user;
        else return null;
    }
    public void save(User user){
        user.setPassword(MDCoder.encodeMD5Hex(user.getPassword()));
        if(userDao.find("username", user.getUsername())!=null)
            throw new CmsException("该用户名已经存在");
        userDao.save(user);
    }

    public Boolean updatePwd(Long id, String oldPwd, String password) {
        User user=userDao.get(id);
        if(user!=null&&user.getPassword().equals(MDCoder.encodeMD5Hex(oldPwd)))
        {
            user.setPassword(MDCoder.encodeMD5Hex(password));
            userDao.update(user);
            return true;
        }
        else return false;
    }


    public List<ChannelTree> getChannelTree(Long uid) {
        return userDao.getChannelTree(uid);
    }

}
