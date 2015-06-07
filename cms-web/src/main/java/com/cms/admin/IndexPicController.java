package com.cms.admin;

import com.cms.beans.AjaxObj;
import com.cms.beans.BaseInfo;
import com.cms.beans.CmsException;
import com.cms.beans.PageBean;
import com.cms.dto.IndexPicDto;
import com.cms.entity.IndexPic;
import com.cms.service.IndexPicService;
import com.cms.utils.QueryHelper;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/pic")
public class IndexPicController {
    @Inject
    private IndexPicService indexPicService;
    private static final String upladPath="/resources/indexPic";
    public final static int T_W = 120;
    @RequestMapping("/indexPics")
    public String listIndexPic(@RequestParam(required = false) Integer currentPage,Model model) {
        Map<String,Integer> mm = indexPicService.getMinAndMaxPos();
            model.addAttribute("min", mm.get("min"));
            model.addAttribute("max", mm.get("max"));
            QueryHelper queryHelper=new QueryHelper(IndexPic.class,"pic").addOrderProperty("pic.pos",true);
            PageBean pageBean;
            if(currentPage==null){
            pageBean=indexPicService.getPageBean(1,5,queryHelper);
        }else{
            pageBean=indexPicService.getPageBean(currentPage,5,queryHelper);
        }
        model.addAttribute("datas",pageBean);
        return "pic/listIndexPic";
    }
    @RequestMapping(value = "/addIndexPic",method = RequestMethod.GET)
    public String addIndexPic(Model model) {
        IndexPic indexPic=new IndexPic();
        indexPic.setStatus(1);
        model.addAttribute("indexPic",indexPic);
        return "pic/addIndexPic";
    }
    @RequestMapping(value = "/addIndexPic",method = RequestMethod.POST)
    public String addIndexPic(IndexPic indexPic,BindingResult br) {
        if(br.hasErrors()) return "pic/addIndexPic";
        indexPicService.save(indexPic);
        return "redirect:/amdin/pic/indexPics";
    }
    @RequestMapping(value = "/updateIndexPic/{id}",method = RequestMethod.GET)
      public String updateIndexPic(@PathVariable Long id,Model model) {
        IndexPic indexPic=indexPicService.get(id);
        if(indexPic==null)
            throw new CmsException("你要更新的首页图片不存在");
        model.addAttribute("indexPic", indexPic);
        return "pic/updateIndexPic";
    }
    @RequestMapping(value = "/updateIndexPic/{id}",method = RequestMethod.POST)
    public String updateIndexPic(@PathVariable Long id,IndexPic indexPic,BindingResult br,Model model) {
        IndexPic oldIndexPic=indexPicService.get(id);
        if(oldIndexPic==null)
            throw new CmsException("你要更新的首页图片不存在");
        if(br.hasErrors())
        {
            model.addAttribute("indexPic",indexPic);
            return "pic/updateIndexPic";
        }
        oldIndexPic.setLinkType(indexPic.getLinkType());
        oldIndexPic.setLinkUrl(indexPic.getLinkUrl());
        oldIndexPic.setStatus(indexPic.getStatus());
        oldIndexPic.setNewName(indexPic.getNewName());
        oldIndexPic.setOldName(indexPic.getOldName());
        oldIndexPic.setSubTitle(indexPic.getSubTitle());
        oldIndexPic.setTitle(indexPic.getTitle());
        indexPicService.update(oldIndexPic);
        return "redirect:/jsp/common/updateSuc.jsp";
    }
    @RequestMapping(value = "uploadIndexPic",method = RequestMethod.POST)
    public void uploadIndexPic(HttpSession session,HttpServletResponse resp,MultipartFile pic) {
        resp.setContentType("text/plain;charset=utf-8");
        PrintWriter out = null;
        AjaxObj ao=null;
        BufferedImage image;
        try {
            out=resp.getWriter();
            ao= new AjaxObj();
            String oldName=pic.getOriginalFilename();
            String newName=String.valueOf(new Date().getTime())+ "."+ FilenameUtils.getExtension(oldName);

            String root=session.getServletContext().getRealPath("");
            String tempFolder=root+upladPath+"/temp";
            File file=new File(tempFolder);
            if(!file.exists()){
                file.mkdir();
            }
            image=ImageIO.read(pic.getInputStream());
            BaseInfo baseInfo= (BaseInfo) session.getServletContext().getAttribute("baseInfo");
            double width= baseInfo.getIndexPicWidth();
            double height=baseInfo.getIndexPicHeight();
            double imageWidth=image.getWidth();
            double imageHeight=image.getHeight();
            if(imageHeight>height&&imageWidth>height){
                //图片的大小符合要求
                //判断是否进行缩放
                Builder<BufferedImage> b = Thumbnails.of(image);
                if(imageWidth-width>150) {
                    b.scale((width+150)/imageHeight);
                } else {
                    b.scale(1.0);
                }
                BufferedImage bi2 = b.asBufferedImage();
                System.out.println(root+upladPath+"/temp/"+newName);
                b.toFile(root+upladPath+"/temp/"+newName);
                IndexPicDto ipd = new IndexPicDto();
                ipd.setNewName(newName);
                ipd.setOldName(oldName);
                ipd.setIndexPicHeight(new Double(height).intValue());
                ipd.setIndexPicWidth(new Double(width).intValue());
                ipd.setImgWidth(bi2.getWidth());
                ipd.setImgHeight(bi2.getHeight());
                ao.setObj(ipd);
                ao.setResult(1);
            }else {
                ao.setResult(0);
                ao.setMsg("图片的尺寸不在有效范围中");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jo = new JSONObject();
        jo.put("indexPic", ao);
        out.write(jo.toString());
        out.flush();
    }
    @RequestMapping(value="/confirmPic",method=RequestMethod.POST)
    public @ResponseBody AjaxObj confirmPic(HttpSession session,int x,int y,int w,int h,String newName) {
        AjaxObj ao = new AjaxObj();
        try {
            BaseInfo baseInfo = (BaseInfo)session.getServletContext().getAttribute("baseInfo");
            int pw = baseInfo.getIndexPicWidth();
            int ph = baseInfo.getIndexPicHeight();
            String path = session.getServletContext().getRealPath("");
            String tpath = path+upladPath+"/temp/"+newName;
            File tf = new File(tpath);
            BufferedImage bi = ImageIO.read(tf);
            String npath = path+upladPath+"/"+newName;
            String ttpath = path+upladPath+"/thumbnail/"+newName;
            Builder<BufferedImage> b = Thumbnails.of(bi);
            //写原图
            BufferedImage bi2 = b.sourceRegion(x, y, w, h).size(pw, ph).asBufferedImage();
            b.toFile(npath);
            //写缩略图
            Thumbnails.of(bi2).scale((double)T_W/(double)pw).toFile(ttpath);
            tf.delete();
            ao.setResult(1);
            return ao;
        } catch (IOException e) {
            e.printStackTrace();
            ao.setResult(0);
            ao.setMsg(e.getMessage());
        }
        return ao;
    }
    @RequestMapping(value = "updateIndexPicStatus/{id}",method = RequestMethod.GET)
    public String updateIndexPicStatus(@PathVariable Long id){
        indexPicService.updateStatus(id);
        return "redirect:/admin/pic/indexPics";
    }
    @RequestMapping(value = "indexPic/{id}",method = RequestMethod.GET)
    public String showIndexPic(@PathVariable Long id,Model model){
        IndexPic indexPic=indexPicService.get(id);
        if(indexPic==null)
            throw new CmsException("首页图片不存在");
        model.addAttribute("indexPic", indexPic);
        return "pic/showIndexPic";
    }
    @RequestMapping(value="/deleteIndexPic/{id}")
    public String deleteIndexPic(@PathVariable Long id,HttpServletRequest request) {
        indexPicService.delete(id, request.getSession().getServletContext().getRealPath(""));
        return "redirect:/admin/pic/indexPics";
    }

}
