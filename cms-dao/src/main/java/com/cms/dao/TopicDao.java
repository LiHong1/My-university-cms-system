package com.cms.dao;

import com.cms.entity.Topic;

public interface TopicDao extends BaseDao<Topic>{

    public void setAttachments(Topic topic, Long[] attachIds);

}
