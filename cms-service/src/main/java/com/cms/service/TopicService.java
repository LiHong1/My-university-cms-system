package com.cms.service;

import com.cms.dao.BaseDao;
import com.cms.entity.Topic;

public interface TopicService extends BaseDao<Topic>{

    /**
     * 设置附件
     * @param topic
     * @param attachIds
     */
    public void setAttachments(Topic topic, Long[] attachIds);

    /**
     * 更改发布状态
     * @param id
     */
    public void changeStatus(Long id);

    /**
     * 删除文章
     * @param id
     * @param path
     */
    public void delete(Long id, String path);

    /**
     * 保存文章
     * @param topic
     * @param attachIds
     */
    public void save(Topic topic, Long[] attachIds);

    /**
     * 修改文章
     * @param topic
     * @param attachIds
     */
    public void update(Topic topic,Long [] attachIds);
   
}
