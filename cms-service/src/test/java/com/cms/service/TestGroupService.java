package com.cms.service;

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

import com.cms.dao.GroupDao;
import com.cms.dao.UserDao;
import com.cms.entity.User;
import com.cms.util.AbstractDbUnitTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestGroupService extends AbstractDbUnitTestCase{
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupDao groupDao;
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
    public void getGroupChannel(){
       // System.out.println(groupDao.getGroupChannel(1,1));
        groupService.addGroupChannel(1, 1);
    }
    @Test
    public void getGroupTest(){
        // System.out.println(groupDao.getGroupChannel(1,1));
        groupService.get(1);
    }
    @Test
    public void getGroupDaoTest(){
        // System.out.println(groupDao.getGroupChannel(1,1));
        groupDao.get(1);
    }
    @After
    public  void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
        //resumeTable();
    }
}
