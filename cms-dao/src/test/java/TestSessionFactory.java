import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cms.dao.UserDao;
import com.cms.daoImpl.UserDaoImpl;

public class TestSessionFactory {
    @Test 
    public void getSessionFactoryTest(){
         ApplicationContext app=new ClassPathXmlApplicationContext("applicationContext.xml");
         SessionFactory session=app.getBean(SessionFactory.class);
         UserDao userdao=(UserDao) app.getBean("userDao");
         assertNotNull(userdao);
         assertNotNull(session);
     }
    @Test 
    public void getUserDaoImplTest(){
         ApplicationContext app=new ClassPathXmlApplicationContext("applicationContext.xml");
         UserDao userdao= app.getBean(UserDao.class);
        // userdao.findAll("id", Order.asc("id"));
         assertNotNull(userdao);
     }
}
