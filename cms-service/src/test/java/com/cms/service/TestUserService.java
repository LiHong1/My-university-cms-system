package com.cms.service;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.cms.entity.User;
import com.cms.util.AbstractDbUnitTestCase;
import com.cms.utils.MDCoder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestUserService extends AbstractDbUnitTestCase{
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private RoleService roleService;
    @Autowired
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
    public void getUserTest(){
      // User u=userService.get(1);
      // System.out.println(u);
   }
    @Test
    public void updateUserTest(){
        User u=userService.get(2);
        u.setPhone("12");
        userService.update(u);
        u=userService.get(2);
        assertEquals("12", u.getPhone());
    }
    @Test
    public void updateUserStatusTest(){
      userService.updateStatus(2);
    }
    @Test
    public void userLoginTest(){
        User u1=userService.get(1);
        System.out.println(u1.getPassword());
        User u=userService.login("lihong", "000000");
        System.out.println(u);
    }
    @Test
    public void updateUserGroupTest(){
         User user=userService.get(2);
         Long []ids={1l,2l};
         user.setGroups(groupService.findByIds(ids));
         user.setRoles(roleService.findByIds(ids));
         userService.update(user);
    }
    @After
    public  void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
        //resumeTable();
    }
}
