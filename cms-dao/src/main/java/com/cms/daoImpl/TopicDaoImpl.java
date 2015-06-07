package com.cms.daoImpl;

import com.cms.entity.Attachment;
import org.springframework.stereotype.Repository;
import com.cms.dao.TopicDao;
import com.cms.entity.Topic;
@Repository
public class TopicDaoImpl extends BaseDaoImpl<Topic> implements TopicDao{
    public void setAttachments(Topic topic, Long[] attachIds) {
        if(attachIds.length!=0)
        this.getSession().createQuery("update Attachment c set c.topic=? where c.id in (:ids)").setParameter(0, topic)
                         .setParameterList("ids", attachIds)
                         .executeUpdate();
    }

}
