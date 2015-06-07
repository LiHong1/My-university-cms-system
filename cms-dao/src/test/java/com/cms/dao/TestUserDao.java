package com.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Source;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.cms.entity.User;
import com.cms.util.AbstractDbUnitTestCase;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestUserDao extends AbstractDbUnitTestCase{
    @Inject
    private UserDao userDao;
    

    @Inject
    private SessionFactory sessionFactory;
    @Before
    public void setUp() throws SQLException, IOException, DatabaseUnitException {
    Session s = sessionFactory.openSession();
//    IDataSet ds = createDateSet("t_user");
//    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
    if(!TransactionSynchronizationManager.hasResource(sessionFactory))
    TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
        this.backupAllTable();
       
    }
    
   @Test
   public void addUserTest() throws DatabaseUnitException, SQLException{
       assertNotNull(userDao);
       User user=new User("lihong","921026","小黎","1255239473@qq.com","18270837982"
               ,1,new Date());
       userDao.save(user);
       //userDao.delete(1l);
       User u=userDao.find("username", "lihong");
       assertEquals(user.getStatus(), u.getStatus());
       User u2=userDao.get(2l);
       assertNotNull(u2);
   }

   
   @Test
   public void findUserBySqlTest() throws DatabaseUnitException, SQLException{
       List <User> users=userDao.findAllBySql("select * from user",null);
       assertEquals(users.get(1).getUsername(), "admin1");
       //User user=userDao.findBySql("select * from user where id=?",1l);
       //assertEquals(user.getUsername(),"admin2");
       
   }
   @Test
   public void findUserByHqlTest() throws DatabaseUnitException, SQLException{
       
       User u=(User) userDao.findByHql("from User where id=?", 2l);
       assertEquals(u.getUsername(), "admin2");
   }
   @Test
   public void listTest() throws DatabaseUnitException, SQLException{
       
//       PageBean pageBean=userDao.getPageBean(0, 2, new QueryHelper(User.class, ""));
//       assertEquals(pageBean.getDatas().size(), 2);
       
   }
   @Test
   public void deleteTest(){
       User user=userDao.get(1l);
       assertNotNull(user);
       userDao.delete(1l);
       user=userDao.get(1l);
       System.out.println(user);
       //assertNotNull(user);
               
   }
   @Test
   public void updateTest(){
       User user=userDao.get(3l);
       System.out.println(user);
       user.setPhone("1230");
       userDao.update(user);
       user =userDao.get(3l);
       assertEquals("1230", user.getPhone());
   }
   @After
   public  void tearDown() throws DatabaseUnitException, SQLException, IOException{
     //  resumeTable();
   }
}
