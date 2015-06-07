package com.cms.daoImpl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cms.dao.AttachmentDao;
import com.cms.entity.Attachment;
@Repository
public class AttachmentDaoImpl extends BaseDaoImpl<Attachment> implements AttachmentDao{

    public Long[] getIds(Long id) {
        List<BigInteger> ids=this.getSession().createSQLQuery("select id from attachment where topicId=?").setParameter(0, id).list();
        Long[] attachmentIds=new Long[ids.size()];
        for(int i=0;i<ids.size();i++)
            attachmentIds[i]=ids.get(i).longValue();
        return attachmentIds;
    }
    public Integer findNoUseAttachmentNum(){
        Query query=this.createQuery("select count(*) from Attachment a where a.topic is null");
        Integer count=((Long) query.uniqueResult()).intValue();
        return count;
    }
    public List<Attachment> getNoUseAttachments(){
         Query query=this.createQuery("from Attachment a where a.topic is null");
        return query.list();
    }
    public void deleteNoUseAttachments(){
        Query query=this.createQuery("delete from Attachment a where a.topic is null");
        query.executeUpdate();
    }
}
