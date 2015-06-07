package com.cms.serviceImpl;

import com.cms.dao.AttachmentDao;
import com.cms.entity.Attachment;
import com.cms.service.AttachmentService;
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
@Service
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment> implements AttachmentService{
   @Inject
   private AttachmentDao attachmentDao;
   private static final int IMG_WIDTH=900;
   public final static int THUMBNAIL_WIDTH = 100;
   public final static int THUMBNAIL_HEIGHT = 80;
   public void add(String path,Attachment attachment, InputStream inputStream) throws IOException {
        attachmentDao.save(attachment);
        File file=new File(path);
        if(!file.isDirectory())
            file.mkdir();
        if(attachment.getIsImg()==1){
            String thumbnailPath=path+"/thumbnails";
            File thumbnailFile=new File(thumbnailPath);
            if(!thumbnailFile.isDirectory())
                 thumbnailFile.mkdir();
            BufferedImage originalImage = ImageIO.read(inputStream);
            Builder<BufferedImage> bf=Thumbnails.of(originalImage);//BufferedImage thumbnailImage = 
            int width=originalImage.getWidth();
            if(width>IMG_WIDTH) {
                bf.scale((double)IMG_WIDTH/(double)width);
            } else {
                bf.scale(1.0f);
            }
            bf.toFile(path+attachment.getNewName());
            //缩略图的处理
            //1、将原图进行压缩
            BufferedImage tbi = Thumbnails.of(originalImage)
                        .scale((THUMBNAIL_WIDTH*1.2)/width).asBufferedImage();
            //2、进行切割并且保持
            Thumbnails.of(tbi).scale(1.0f)
                .sourceRegion(Positions.CENTER, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                .toFile(thumbnailPath+"/"+attachment.getNewName());
        }else{
            FileUtils.copyInputStreamToFile(inputStream, new File(path+attachment.getNewName())); 
        }
       
   }
    public Long[] getIds(Long id) {
        return attachmentDao.getIds(id);
    }
    public void deleteAttachFiles(Attachment a,String path) {
            new File(path+a.getNewName()+"."+a.getSuffix()).delete();
            if(a.getIsImg()==1)
            new File(path+"thumbnails/"+a.getNewName()+"."+a.getSuffix()).delete();
    }
    public void changeIsAttach(long aId) {
        Attachment attachment=attachmentDao.get(aId);
        if(attachment!=null){
            int isAttach=attachment.getIsAttach();
            if(isAttach==0)
                isAttach=1;
            else isAttach=0;
            attachment.setIsAttach(isAttach);
            attachmentDao.update(attachment);
        }
    }
    public void changeIsIndex(long aId) {
        Attachment attachment=attachmentDao.get(aId);
        if(attachment!=null){
            Boolean isIndexPic=attachment.getIsIndexPic();
            if(isIndexPic==null)
                isIndexPic=false;
            attachment.setIsIndexPic(!isIndexPic);
            attachmentDao.update(attachment);
        }        
    }
    public Integer findNoUseAttachmentNum(){
        return attachmentDao.findNoUseAttachmentNum();
    }
    public void clearNoUseAttachment(String path){
        List<Attachment> attachments=attachmentDao.getNoUseAttachments();
        for(Attachment attachment:attachments){
            deleteAttachFiles(attachment,path);
        }
        attachmentDao.deleteNoUseAttachments();
    }
}
