package com.cms.dao;

import com.cms.entity.Attachment;
import com.cms.entity.IndexPic;

import java.util.List;
import java.util.Map;

public interface IndexPicDao extends BaseDao<IndexPic>{
    public Map<String,Integer> getMinAndMaxPos();
    public void save(IndexPic indexPic);
    public void delete(IndexPic indexPic);
    public void updatePicPos(Long id, int oldPos, int newPos);
    public List<String> listAllIndexPicName();
    public void deleteNoUseIndexPics(List<String> names);
}
