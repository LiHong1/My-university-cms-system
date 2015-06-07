package com.cms.serviceImpl;

import java.util.List;

import javax.inject.Inject;
import com.cms.dao.TopicDao;
import com.cms.entity.Attachment;
import com.cms.entity.Topic;
import com.cms.service.AttachmentService;
import com.cms.service.TopicService;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl extends BaseServiceImpl<Topic> implements TopicService{
    @Inject
    private TopicDao topicDao;
    @Inject
    private AttachmentService attachmentService;
    public void setAttachments(Topic topic, Long[] attachIds) {
        topicDao.setAttachments(topic,attachIds);
    }

    public void changeStatus(Long id) {
        Topic topic=topicDao.get(id);
        if(topic!=null)
        {
            if(topic.getStatus()==0)
                topic.setStatus(1);
            else 
                topic.setStatus(0);
            topicDao.update(topic);
        }
    }

    public void delete(Long id,String path) {
        Topic topic=topicDao.get(id);
        List<Attachment> attachments=topic.getAttachments();
      //删除硬盘上面的文件
        for(Attachment a:attachments) {
            attachmentService.deleteAttachFiles(a,path);
        }
        topicDao.delete(id);
        
    }

    public void save(Topic topic, Long[] attachIds){
        super.save(topic);
        this.setAttachments(topic, attachIds);
    }

    public void update(Topic topic,Long [] attachIds){
        super.update(topic);
        this.setAttachments(topic,attachIds);
    }
}
