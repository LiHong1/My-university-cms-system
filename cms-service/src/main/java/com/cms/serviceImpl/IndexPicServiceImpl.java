package com.cms.serviceImpl;

import com.cms.beans.CmsException;
import com.cms.dao.AttachmentDao;
import com.cms.dao.IndexPicDao;
import com.cms.entity.Attachment;
import com.cms.entity.IndexPic;
import com.cms.service.AttachmentService;
import com.cms.service.IndexPicService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class IndexPicServiceImpl extends BaseServiceImpl<IndexPic> implements IndexPicService {
   @Inject
   private IndexPicDao indexPicDao;

   public void updateStatus(Long id){
      IndexPic indexPic=indexPicDao.get(id);
      if(indexPic==null){
         throw new CmsException("你要更改的首页图片不存在");
      }
      if(indexPic.getStatus()==0)
         indexPic.setStatus(1);
      else indexPic.setStatus(0);
      indexPicDao.update(indexPic);
   }
   public Map<String,Integer> getMinAndMaxPos(){
      return indexPicDao.getMinAndMaxPos();
   }
   public void delete(Long id,String relPath){
      IndexPic indexPic=indexPicDao.get(id);
      if(indexPic==null)
         throw new CmsException("要删除的首页图片不存在");
      cleanIndexpics(relPath,indexPic.getNewName());
      indexPicDao.delete(indexPic);
   }
   public void save(IndexPic indexPic){
      indexPicDao.save(indexPic);
   }
   public void updatePicPos(Long id, int oldPos, int newPos){
      if(id!=null&&oldPos!=newPos)
      indexPicDao.updatePicPos(id,oldPos,newPos);
   }
   public List<String> listAllIndexPicName(){
      return indexPicDao.listAllIndexPicName();
   }
   public void cleanNoUseIndexPics(String realPath,List<String> names){
      if(names!=null){
         for(String name:names){
            cleanIndexpics(realPath,name);
         }
         indexPicDao.deleteNoUseIndexPics(names);
      }

   }
   private void cleanIndexpics(String relPath,String name){
      String tp = relPath+"/resources/indexPic/thumbnail/"+name;
      String pp = relPath+"/resources/indexPic/"+name;
      String te = relPath+"/resources/indexPic/temp/"+name;
      new File(tp).delete();
      new File(pp).delete();
      new File(te).delete();
   }
}
