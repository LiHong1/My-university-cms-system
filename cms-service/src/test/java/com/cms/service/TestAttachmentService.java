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
public class TestAttachmentService extends AbstractDbUnitTestCase{
    @Autowired
    private AttachmentService attachmentService;
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
    public void getGroupDaoTest(){
        attachmentService.changeIsAttach(15);
         //attachmentService.get(15);
    }
    @After
    public  void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
        //resumeTable();
    }
}
