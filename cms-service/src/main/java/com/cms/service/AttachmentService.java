package com.cms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.cms.dao.BaseDao;
import com.cms.entity.Attachment;

public interface AttachmentService extends BaseDao<Attachment>{
    /**
     * 添加附件
     * @param path 
     * @param attachment
     * @param inputStream
     * @throws IOException 
     */
    public void add(String path, Attachment attachment, InputStream inputStream) throws IOException;
    /**
     * 获取文章的附件id
     * @param id
     * @return
     */
    public Long[] getIds(Long id);
    /**
     * 删除文件
     * @param a
     * @param path 
     */
    public void deleteAttachFiles(Attachment a, String path);

    /**
     * 改变图片是否是附件
     * @param aId
     */
    public void changeIsAttach(long aId);
    /**
     * 改变图片是否是首页图片
     * @param aId
     */
    public void changeIsIndex(long aId);
   
    public Integer findNoUseAttachmentNum();

    /**
     * 清除没有使用的附件
     */
    public void clearNoUseAttachment(String path);

}
