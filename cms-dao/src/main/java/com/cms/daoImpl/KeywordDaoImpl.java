package com.cms.daoImpl;

import org.springframework.stereotype.Repository;

import com.cms.dao.KeywordDao;
import com.cms.entity.Keyword;
import com.cms.utils.PinyinUtil;
@Repository
@SuppressWarnings("unchecked")
public class KeywordDaoImpl extends BaseDaoImpl<Keyword> implements KeywordDao{

    public void addOrUpdate(String name) {
        Keyword k = (Keyword)this.createQuery("from Keyword where name=?", name).uniqueResult();
        if(k==null) {
            k = new Keyword();
            k.setName(name);
            k.setNameFullPy(PinyinUtil.str2Pinyin(name, null));
            k.setNameShortPy(PinyinUtil.strFirst2Pinyin(name));
            k.setTimes(1);
            this.save(k);
        } else {
            k.setTimes(k.getTimes()+1);
        }
    }

    public void addOrDelete(String[] originalKeyword, String[] nowKeyword) {
        String originalKeywords ="",nowKeywords="";
        if(originalKeyword!=null){
            for(String s:originalKeyword)
                originalKeywords+=s;
        }

        if(nowKeyword!=null)
        for(String s:nowKeyword)
            nowKeywords+=s;
        //在原有的中但不在现在的
        if(originalKeyword!=null)
        for(String s:originalKeyword){
            if(!nowKeywords.contains(s)){
                delete(s);
            }
        }
        //在新在的但不在原有的中
        if(nowKeyword!=null)
        for(String s:nowKeyword){
            if(!originalKeywords.contains(s)){
                add(s);
            }
        }
        //原有的与现在都有
        
       
    }

    private void add(String s) {
        addOrUpdate(s);//关键字存在就引用次数加一，若关键字不存在就save 
    }

    private void delete(String s) {
        Keyword k = (Keyword)this.createQuery("from Keyword where name=?", s).uniqueResult();    
        k.setTimes(k.getTimes()-1);
    }

}
