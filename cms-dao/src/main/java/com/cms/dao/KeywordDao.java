package com.cms.dao;

import com.cms.entity.Keyword;

public interface KeywordDao extends BaseDao<Keyword>{

    public void addOrUpdate(String name);
    /**
     * 增加或删除关键字
     * @param originalKeywords
     * @param nowKeywords
     */
    public void addOrDelete(String[] originalKeywords, String[] nowKeywords);
}
