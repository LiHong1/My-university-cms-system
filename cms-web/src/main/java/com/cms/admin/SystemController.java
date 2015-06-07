package com.cms.admin;

import com.cms.Util.BaseInfoUtil;
import com.cms.auth.AuthClass;
import com.cms.auth.AuthMethod;
import com.cms.beans.BaseInfo;
import com.cms.entity.Attachment;
import com.cms.entity.IndexPic;
import com.cms.service.AttachmentService;
import com.cms.service.IndexPicService;
import com.cms.utils.QueryHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/system")
@AuthClass
public class SystemController {
    @Inject
    private AttachmentService attachmentService;
    @Inject
    private IndexPicService indexPicService;
    @RequestMapping("/baseInfo")
    public String showBaseInfo() {
        return "system/showBaseInfo";
    }
    @RequestMapping(value="/baseInfo/update",method=RequestMethod.GET)
    public String updateBaseInfo(HttpSession session,Model model) {
        model.addAttribute("baseInfo", session.getServletContext().getAttribute("baseInfo"));
        return "system/updateBaseInfo";
    }
    @RequestMapping(value="/baseInfo/update",method=RequestMethod.POST)
    public String updateBaseInfo(@Validated BaseInfo baseInfo,BindingResult br,HttpSession session) {
        if(br.hasErrors()) {
            return "system/updateBaseInfo";
        }
        BaseInfo bi = BaseInfoUtil.getInstance().setBaseInfo(baseInfo);
        session.getServletContext().setAttribute("baseInfo", bi);
        return "redirect:/admin/system/baseInfo";
    }
    @RequestMapping(value="/cleans")
    public String listCleans(Model model,HttpSession session) {
        model.addAttribute("attNums", attachmentService.findNoUseAttachmentNum());
        model.addAttribute("indexPics", listNoUseIndexPicNum(indexPicService.listAllIndexPicName(), session.getServletContext().getRealPath("")));
        return "system/cleans";
    }
    @RequestMapping(value="/cleanList/atts",method = RequestMethod.GET)
    public String listAttachments(Model model) {
        QueryHelper queryHelper=new QueryHelper(Attachment.class,"a")
                                    .addValueIsNullCondition("a.topic")
                                    .addOrderProperty("a.id", true);
        model.addAttribute("datas",attachmentService.getPageBean(1, 5, queryHelper));
        return "system/cleanAtts";
    }


    @RequestMapping(value="/cleanList/pics",method = RequestMethod.GET)
    public String listPics(Model model,HttpSession session) {
        List<String> pics=indexPicService.listAllIndexPicName();
        File[] fs = listPicFile(session.getServletContext().getRealPath(""));
        List<String> noUsePics=new ArrayList<String>();
        for(File file:fs) {
            if(!pics.contains(file.getName()))
                noUsePics.add(file.getName());
        }
        model.addAttribute("datas", noUsePics);
        return "system/cleanPics";
    }
    @RequestMapping(value="/clean/{name}",method = RequestMethod.GET)
    public String cleanPics(@PathVariable String name, HttpSession session) {
        if(name.equals("pics")){
            String path=session.getServletContext().getRealPath("");
            List<String> names=listNoUseIndexPic(indexPicService.listAllIndexPicName(),path);
            indexPicService.cleanNoUseIndexPics(path,names);
        }else if(name.equals("atts")){
            attachmentService.clearNoUseAttachment(session.getServletContext().getRealPath("")+"/resources/indexPic/");
        }
        return "redirect:/admin/system/cleans";
    }

    private int listNoUseIndexPicNum(List<String> pics,String path){
        File[] fs = listPicFile(path);
        int count = 0;
        for(File file:fs) {
            if(!pics.contains(file.getName())) count++;
        }
        return count;
    }
    private File[] listPicFile(String path){
        File f = new File(path+"/resources/indexPic");
        File[] files = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.isDirectory())
                    return false;
                return true;
            }
        });
        return files;
    }
    /**
     * 获取没有使用的首页图片列表
     * @param pics
     * @return
     */
    private List<String> listNoUseIndexPic(List<String> pics,String path) {
        File[] fs = listPicFile(path);
        List<String> npics = new ArrayList<String>();
        if (fs!=null)
        for(File f:fs) {
            if(!pics.contains(f.getName())) npics.add(f.getName());
        }
        return npics;
    }
}
