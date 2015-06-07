package com.cms.dao;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础dao接口
 * @author li hong
 *
 */
@Transactional
public interface BaseDao<T> extends BaseSimpleDao{
      /**
       * 删除实体
       * @param id
       */
      public void delete(long id);
      /**
       * 保存实体
       * @param t
       */
      public void save(T t);
      /**
       * 更新实体
       * @param t
       */
      public void update(T t);
      /**
       * 获取实体
       * @param id
       * @return
       */
      public T get(long id);
      /**
       * 获取所有实体
       * @return
       */
      public List<T> findAll();
      /**
       * 获取所有实体
       * @return
       */
      public List<T> findAll(String property, Order order);
      /**
       * 获取所有实体
       * @return
       */
      public List<T> findAll(String property, Object value);
      
      /**
       * 根据属性值获取实体
       * @param property
       * @return
       */
      public T find(String property, Object value);
      
      
     
      /**
       * 根据hql与参数查找实体
       * @param hql
       * @param values
       * @return
       */
      public List<T> findAllByHql(String hql, Object... values);
      /**
       * 根据sql与参数查找实体
       * @param sql
       * @param values
       * @return
       */
      public List findAllBySql(String sql, Object... values);
      
      /**
       * 根据sql与参数查找实体
       * @param sql
       * @param values
       * @return
       */
      public List findAllBySql(String sql, Class clazz, Object... values);
      /**
       * 根据sql与参数查找实体
       * @param values
       * @return
       */
      public List findAllByHql(String hql, Class clazz, Object... values);
      /**
       * 根据hql与参数查找唯一实体
       * @param hql
       * @param values
       * @return
       */
      public Object findByHql(String hql, Object... values);
      /**
       * 根据sql与参数查找唯一实体
       * @param sql
       * @param values
       * @return
       */
      public Object findBySql(String sql, Object... values);
      /**
       * 根据id查找实体
       * @param ids
       * @return
       */
      public List<T> findByIds(Long ids[]);
}
