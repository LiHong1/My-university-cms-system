package com.cms.service;

import com.cms.dao.BaseDao;
import com.cms.entity.Topic;

public interface TopicService extends BaseDao<Topic>{

    /**
     * ���ø���
     * @param topic
     * @param attachIds
     */
    public void setAttachments(Topic topic, Long[] attachIds);

    /**
     * ���ķ���״̬
     * @param id
     */
    public void changeStatus(Long id);

    /**
     * ɾ������
     * @param id
     * @param path
     */
    public void delete(Long id, String path);

    /**
     * ��������
     * @param topic
     * @param attachIds
     */
    public void save(Topic topic, Long[] attachIds);

    /**
     * �޸�����
     * @param topic
     * @param attachIds
     */
    public void update(Topic topic,Long [] attachIds);
   
}
