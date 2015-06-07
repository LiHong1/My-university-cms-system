package com.cms.serviceImpl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cms.dao.KeywordDao;
import com.cms.entity.Keyword;
import com.cms.service.KeywordService;
@Service
public class KeywordServiceImpl extends BaseServiceImpl<Keyword> implements KeywordService{
    @Inject
    private KeywordDao keywordDao;
    public void addOrUpdate(String[] aks) {
           for(String name:aks){
               keywordDao.addOrUpdate(name);
           }
    }
    public void addOrDelete(String [] originalKeywords,String [] newKeywords){
        keywordDao.addOrDelete(originalKeywords, newKeywords);
    }
    public void addOrDelete(String string, String[] newKeywords) {
        String [] originalKeywords=null;
        if(string!=null)
        originalKeywords=string.split("\\|");
        keywordDao.addOrDelete(originalKeywords,newKeywords);
    }
  
 
}
