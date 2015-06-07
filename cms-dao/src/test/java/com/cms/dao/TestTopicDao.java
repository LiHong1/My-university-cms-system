package com.cms.dao;

import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
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
public class TestTopicDao extends AbstractDbUnitTestCase{
    @Inject
    private ChannelDao channelDao;
    @Inject
    private TopicDao topicDao;
    private Session s;
    @Inject
    private SessionFactory sessionFactory;
    @Before
    public void setUp() throws SQLException, IOException, DatabaseUnitException {
    s= sessionFactory.openSession();
    this.backupAllTable();
    //IDataSet ds = createDateSet("channel");
    //DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
    if(!TransactionSynchronizationManager.hasResource(sessionFactory))
    TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
       
       
    }
    @Test
    public void addTopicDaoTest(){
        QueryHelper queryHelper=new QueryHelper(Topic.class);
        PageBean pageBean=topicDao.getPageBean(1, 10, queryHelper);
    }
    @Test
    public void addChannelDaoTest(){
        Channel channel=new Channel();
        channel.setName("发布栏目");
        channelDao.save(channel);
    }
    @Test
    public void getChannelDaoTest(){
        List<Channel> channel=channelDao.findChilds(0);
        assertNull(channel);
       // assertEquals(channel.getName(), "发布栏目");
        
    }
    @Test
    public void deleteChannelDaoTest(){
         channelDao.delete(21);
    }
    @Test
    public void getPageBeanTest(){
        String t="请";
        QueryHelper queryHelper=new QueryHelper(Topic.class,"t")
        .addLikeProperty("t.keyword", "'%请%'");
        PageBean pageBean=topicDao.getPageBean(1, 3, queryHelper);
        System.out.println(pageBean.getDatas().size());
        System.out.println(topicDao.findAllByHql("FROM Topic t WHERE t.keyword LIKE '%请%'").size());
    }
    @Test
    public void findChildsTest(){
        //List <Channel> channels=channelDao.findChilds(null);
        List <ChannelTree> s=channelDao.findChannelTree();
        System.out.println(s.size());
        //assertEquals(4, channels.size());
    }
    @Test
    public void getMaxOrderByParentTest(){
       int count=channelDao.getMaxOrderByParent(1l);
       System.out.println(count);
    }
   @Test
   public void getTopic(){
       Topic topic=topicDao.get(4l);
       System.out.println(topic.getAttachments().size());
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
