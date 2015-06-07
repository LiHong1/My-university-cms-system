package com.cms.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

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

import com.cms.beans.ChannelTree;
import com.cms.beans.PageBean;
import com.cms.entity.Channel;
import com.cms.entity.Topic;
import com.cms.util.AbstractDbUnitTestCase;
import com.cms.utils.QueryHelper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestAttachmentDao extends AbstractDbUnitTestCase{
    @Inject
    private AttachmentDao attachmentDao;
    private Session s;
    @Inject
    private SessionFactory sessionFactory;
    @Before
    public void setUp() throws SQLException, IOException, DatabaseUnitException {
    s= sessionFactory.openSession();
    this.backupAllTable();
    IDataSet ds = createDateSet("channel");
    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
    if(!TransactionSynchronizationManager.hasResource(sessionFactory))
    TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
       
       
    }
    @Test
    public void getAttachmentDaoTest(){
         //attachmentDao.getIds(new Long(9));
    }


   @After
   public  void tearDown() throws DatabaseUnitException, SQLException, IOException{
       SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
       Session s = holder.getSession(); 
       s.flush();
       TransactionSynchronizationManager.unbindResource(sessionFactory);
       //resumeTable();
   }
}
