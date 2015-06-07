package com.cms.dao;

import java.util.List;

import com.cms.entity.Attachment;

public interface AttachmentDao extends BaseDao<Attachment>{
    /**
     * 获取文章附件的id
     * @param id
     * @return
     */
    public Long[] getIds(Long id);

    /**
     * 获取没有被引用的附件数
     */
    public Integer findNoUseAttachmentNum();
    /**
     * 获取没有被引用的附件
     */
    public List<Attachment> getNoUseAttachments();

    /**
     * 删除没有被引用的文件
     */
    public void deleteNoUseAttachments();

}
