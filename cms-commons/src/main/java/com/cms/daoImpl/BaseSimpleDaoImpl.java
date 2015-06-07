package com.cms.daoImpl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.cms.beans.PageBean;
import com.cms.dao.BaseSimpleDao;
import com.cms.utils.QueryHelper;
/**
 * 与具体实现类无关的基础dao
 * @author li hong
 *
 */

public abstract class BaseSimpleDaoImpl implements BaseSimpleDao{
    @Inject
    public SessionFactory sessionFactory;
  
    public PageBean getPageBean(int currentPage, int pageSize, QueryHelper queryHelper){
     // 参数列表
        List<Object> parameters = queryHelper.getParameters();
        if (parameters != null) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                System.out.println(parameters.get(i));
            }
        }
        // 查询本页的数据列表
        Query listQuery = getSession().createQuery(queryHelper.getListQueryHql()); // 创建查询对象
        if (parameters != null) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                listQuery.setParameter(i, parameters.get(i));
            }
        }
        listQuery.setFirstResult((currentPage - 1) * pageSize);
        listQuery.setMaxResults(pageSize);
        List<Object> list = listQuery.list(); // 执行查询

        // 查询总记录数量
        Query countQuery = getSession().createQuery(queryHelper.getCountQueryHql());
        if (parameters != null) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                countQuery.setParameter(i, parameters.get(i));
            }
        }
        Long recordCount = (Long) countQuery.uniqueResult(); // 执行查询
        return new PageBean(currentPage, pageSize, recordCount, list);
    }
    public PageBean getPageBean(int currentPage, QueryHelper queryHelper) {
        
        return this.getPageBean(currentPage, pageSize, queryHelper);
    }
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
