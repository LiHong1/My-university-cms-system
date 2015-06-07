package com.cms.service;

import com.cms.dao.BaseDao;
import com.cms.entity.Keyword;

/**
 * 关键字接口
 * @author li hong
 *
 */
public interface KeywordService extends BaseDao<Keyword>{
    /**
     * 增加或更新关键字
     * @param aks
     */
    public void addOrUpdate(String[] aks);
    /**
     * 增加或删除关键字
     * @param string
     * @param nowKeywords
     */
    public void addOrDelete(String string, String[] nowKeywords);
   
}
