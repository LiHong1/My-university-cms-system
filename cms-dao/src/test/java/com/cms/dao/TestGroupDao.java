package com.cms.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import static org.junit.Assert.*;
import com.cms.entity.Group;
import com.cms.util.AbstractDbUnitTestCase;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestGroupDao extends AbstractDbUnitTestCase{
    @Inject
    private UserDao userDao;
    @Inject
    private GroupDao groupDao;
    Session s ;
    @Inject
    private SessionFactory sessionFactory;
    @Before
    public void setUp() throws SQLException, IOException, DatabaseUnitException {
    s= sessionFactory.openSession();
    //IDataSet ds = createDateSet("t_user");
    //DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
    if(!TransactionSynchronizationManager.hasResource(sessionFactory))
    TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
        this.backupAllTable();
       
    }
    @Test
    public void addGroupTest(){
       groupDao.save(new Group());
    }
    @Test
    public void saveGroupTest(){
       Group group=groupDao.get(1l);
      // group.setName("tgg");
      // groupDao.update(group);
    }
   
   public void listGroupTest() throws DatabaseUnitException, SQLException{
       System.out.println(groupDao.get(2l));
   groupDao.delete(2l);
       System.out.println(groupDao.get(2l));
       
   }
   @Test
   public void listChannelIdsTest(){
       groupDao.listChannelIds(1l);
   }
   @Test
   public void getChannelTree(){
       System.out.println(groupDao.getChannelTree(1l).size());
   }
   @Test
   public void getGroupChannel(){
      // System.out.println(groupDao.getGroupChannel(1,1));
       groupDao.addGroupChannel(1, 1);
   }
   @After
   public  void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
     //  resumeTable();
   }
}
