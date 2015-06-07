package com.cms.service;

import com.cms.dao.BaseDao;
import com.cms.entity.Attachment;
import com.cms.entity.IndexPic;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IndexPicService extends BaseDao<IndexPic>{
    /**
     * 改变状态
     * @param id
     */
    public void updateStatus(Long id);

    /**
     * 获得首页图片的最大及最小位置
     * @return
     */
    public Map<String,Integer> getMinAndMaxPos();

    public void delete(Long id, String relPath);

    public void save(IndexPic indexPic);
    /**
     * 更新首页图片的顺序
     * @param id
     * @param oldPos
     * @param newPos
     */
    public void updatePicPos(Long id, int oldPos, int newPos);

    /**
     * 获取所有的首页图片名字
     * @return
     */
    public List<String> listAllIndexPicName();

    /**
     * 清除没有使用的首页图片
     */
    public void cleanNoUseIndexPics(String realPath, List<String> names);
}
