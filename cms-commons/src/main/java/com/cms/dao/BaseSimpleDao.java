package com.cms.dao;

import com.cms.beans.PageBean;
import com.cms.utils.QueryHelper;
/**
 * 与具体实现类无关的基础dao
 * @author li hong
 *
 */
public interface  BaseSimpleDao {
    public int pageSize=10;
    public PageBean getPageBean(int currentPage, int pageSize, QueryHelper queryHelper);
    public PageBean getPageBean(int currentPage, QueryHelper queryHelper);
}
