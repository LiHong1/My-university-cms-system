package com.cms.daoImpl;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.cms.dao.BaseDao;
@SuppressWarnings("unchecked")
@Transactional
public abstract class BaseDaoImpl<T> extends BaseSimpleDaoImpl implements BaseDao<T>{
   
    private Class<T> clazz; 
    public BaseDaoImpl(){
        System.out.println(this.getClass().getGenericSuperclass());
        ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz=(Class<T>) pt.getActualTypeArguments()[0];
    }
    public void delete(long id) {
        //T t=get(id);
        Object t= getSession().get(getEntityClass(), id);
        if(t!=null){
            Session session=getSession();
            session.delete(t);  
            //session.flush();
        }
    }

    public void save(T t) {
        getSession().save(t);
    }
    public void update(T t) {
         Session session=getSession();
         getSession().update(t);
         //session.flush();
    }

    public T get(long id) {
        return get(id,false);
    }
    public List<T> findAll(String property,Order order){
       return  getSession().
               createCriteria(getEntityClass())
               .addOrder(order)
               .list();  
    }
    public List<T> findAll(String property,Object value){
        Criterion criterion;
 
        if(value==null)
            criterion=Restrictions.isNull(property);
        else criterion=Restrictions.eq(property, value);
        return getSession().createCriteria(getEntityClass()).add(criterion).list();
    }
    public T find(String property,Object value){
        Criterion criterion;
        if(value==null)
            criterion=Restrictions.isNull(property);
        else criterion=Restrictions.eq(property, value);
        return (T) getSession().createCriteria(getEntityClass()).add(criterion).uniqueResult();
    }
    public List<T> findAll(){
        return  getSession().
                createCriteria(getEntityClass())
                .list();
     }
   
    /**
     * @see Session.get(Class,Serializable,LockMode)
     * @param id
     *            对象ID
     * @param lock
     *            是否锁定，使用LockMode.UPGRADE
     * @return 持久化对象
     */
  
    protected T get(long id, boolean lock) {
        T entity;
        if (lock) {
            entity = (T) getSession().get(getEntityClass(), id, LockOptions.UPGRADE);
        } else {
            entity = (T) getSession().get(getEntityClass(), id);
        }
        return entity;
    }
   
    private Class<T> getEntityClass(){
        return clazz;
    }
    protected Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }


    public List<T> findAllByHql(String hql,Object...values){
        return createQuery(hql,values).list(); 
    }
    
    public List<Object> findAllBySql(String sql,Object...values){
        return createSqlQuery(sql,values).list();
    }
   
    public Object findByHql(String hql,Object...values){
        return createQuery(hql,values).uniqueResult();
    }
    public Object findBySql(String sql,Object...values){
        return createSqlQuery(sql,values).uniqueResult();
    }
   
    public Query createQuery(String hql,Object...values){
        Assert.hasText(hql);
        Query query=getSession().createQuery(hql);
        if(values!=null){
            for(int i=0;i<values.length;i++){
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }
    public Query createSqlQuery(String sql,Object...values){
        Assert.hasText(sql);
        SQLQuery query=getSession().createSQLQuery(sql);
        if(values!=null){
            for(int i=0;i<values.length;i++){
                query.setParameter(i, values[i]);
            }
        }
        query.addEntity(this.getEntityClass());
        return query;
    }

    public List<T> findByIds(Long[] ids) {
        if(ids==null||ids.length==0)
            return null;
        return getSession().createCriteria(getEntityClass())
                .add(Restrictions.in("id", ids))
                .list();
    }

    @SuppressWarnings("rawtypes")
    public List findAllBySql(String sql, Class clazz, Object... values) {
        SQLQuery query=getSession().createSQLQuery(sql);
        if(values!=null){
            for(int i=0;i<values.length;i++){
                query.setParameter(i, values[i]);
            }
        }
        query.setResultTransformer(Transformers.aliasToBean(clazz));
        return query.list();
    }
    @SuppressWarnings("rawtypes")
    public List findAllByHql(String hql, Class clazz, Object... values) {
        Query query=getSession().createQuery(hql);
        if(values!=null){
            for(int i=0;i<values.length;i++){
                query.setParameter(i, values[i]);
            }
        }
        query.setResultTransformer(Transformers.aliasToBean(clazz));
        return query.list();
    }

   

  
}
