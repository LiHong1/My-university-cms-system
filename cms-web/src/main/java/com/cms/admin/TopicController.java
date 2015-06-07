package com.cms.admin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cms.auth.AuthClass;
import com.cms.auth.AuthMethod;
import com.cms.beans.AjaxObj;
import com.cms.beans.CmsException;
import com.cms.beans.PageBean;
import com.cms.dto.TopicDto;
import com.cms.entity.Attachment;
import com.cms.entity.Channel;
import com.cms.entity.Topic;
import com.cms.entity.User;
import com.cms.service.AttachmentService;
import com.cms.service.ChannelService;
import com.cms.service.KeywordService;
import com.cms.service.TopicService;
import com.cms.utils.QueryHelper;

@Controller
@AuthClass("login")
@RequestMapping("/admin/topic")
public class TopicController {
    @Inject
    private TopicService topicService;
    @Inject
    private ChannelService channelService;
    @Inject
    private AttachmentService attachmentService;
    @Inject
    private KeywordService keywordService;
    private static final List<String> ImageType = Arrays.asList("jpg", "gif", "jpeg", "png");


    @RequestMapping("/delete/{id}")
    @AuthMethod(role = "ROLE_PUBLISH")
    public String delete(@PathVariable Long id, Integer status, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/") + "/resources/upload/";
        topicService.delete(id, path);
        if (status == 0) {
            return "redirect:/admin/topic/unaudits";
        } else {
            return "redirect:/admin/topic/audits";
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @AuthMethod(role = "ROLE_PUBLISH,ROLE_AUDIT")
    public String list(@RequestParam(required = false) String con, @RequestParam(required = false) Integer status, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false) Long cid, Model model) {
        this.initList(currentPage, status, con, cid, model);
        model.addAttribute("cid", cid);
        return "topic/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @AuthMethod(role = "ROLE_PUBLISH")
    public String add(Model model) {
        model.addAttribute("topicDto", new TopicDto());
        return "topic/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(TopicDto topicDto, HttpSession session) {
        Topic topic = topicDto.getTopic(null);
        Channel channel = channelService.get(topicDto.getCid());
        topic.setChannel(channel);
        topic.setCname(channel.getName());
        User user = (User) session.getAttribute("user");
        topic.setUser(user);
        topic.setAuthor(user.getUsername());
        topicService.save(topic, topicDto.getAttachIds());
        keywordService.addOrUpdate(topicDto.getAks());
        return "topic/add";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)//返回的是json类型的值，而uploadify只能接受字符串
    @AuthMethod(role = "ROLE_PUBLISH")
    public void addAttachment(MultipartFile attach, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        String path = request.getSession().getServletContext().getRealPath("/") + "/resources/upload/";
        String suffix = FilenameUtils.getExtension(attach.getOriginalFilename());
        Attachment attachment = new Attachment();
        attachment.setOldName(FilenameUtils.getBaseName(attach.getOriginalFilename()));
        attachment.setNewName(String.valueOf(new Date().getTime()) + "." + suffix);
        attachment.setSuffix(suffix);
        if (ImageType.contains(suffix))
            attachment.setIsImg(1);
        else attachment.setIsImg(0);
        attachment.setSize((int) attach.getSize() / 1000);
        attachment.setType(attach.getContentType());
        AjaxObj ao = null;
        try {
            attachmentService.add(path, attachment, attach.getInputStream());
            ao = new AjaxObj(1, "");
            ao.setObj(attachment);
        } catch (IOException e) {
            e.printStackTrace();
            ao = new AjaxObj(0, e.getMessage());
        }
        JSONObject jo = new JSONObject();
        jo.put("ajaxObj", ao);
        response.getWriter().write(jo.toString());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @AuthMethod(role = "ROLE_PUBLISH,ROLE_AUDIT")
    public String showTopic(@PathVariable Long id, Model model) {
        Topic topic = topicService.get(id);
        model.addAttribute("topic", topic);
        return "topic/show";
    }

    @RequestMapping(value = "/audits", method = RequestMethod.GET)
    @AuthMethod(role = "ROLE_PUBLISH,ROLE_AUDIT")
    public String auditsList(@RequestParam(required = false) String con, @RequestParam(required = false) Integer status, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false) Long cid, Model model) {
        this.initList(currentPage, 1, con, cid, model);
        return "topic/audits";
    }

    @RequestMapping(value = "/unaudits", method = RequestMethod.GET)
    @AuthMethod(role = "ROLE_PUBLISH,ROLE_AUDIT")
    public String unauditsList(@RequestParam(required = false) String con, @RequestParam(required = false) Integer status, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false) Long cid, Model model) {
        this.initList(currentPage, 0, con, cid, model);
        return "topic/unaudits";
    }

    /*	@RequestMapping(value="searchKeyword",method=RequestMethod.GET)
        public @ResponseBody List<String> searchKeyword(String term){
            List<String> list=new ArrayList<String>();
            list.add("name");
            list.add("username");
            return list;
        }*/
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @AuthMethod(role = "ROLE_PUBLISH,ROLE_AUDIT")
    public String update(@PathVariable Long id, Model model) {
        Topic topic = topicService.get(id);
        Channel channel = topic.getChannel();
        TopicDto topicDto = new TopicDto(topic);
        topicDto.setChannel(channel);
        List<Attachment> atts = topic.getAttachments();
        String[] keywords = topicDto.getKeywords();
        model.addAttribute("atts", atts);
        model.addAttribute("topicDto", topicDto);
        model.addAttribute("keywords", keywords);
        return "topic/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @AuthMethod(role = "ROLE_PUBLISH,ROLE_AUDIT")
    public String update(@PathVariable Long id, TopicDto topicDto) {
        Topic topic = topicService.get(id);
        if (topic == null)
            throw new CmsException("要更新的文章不存在");
        Long attachIds[] = topicDto.getAttachIds();
        int channelPicId = topicDto.getChannelPicId();
        String[] keywords = topicDto.getAks();
        Long channelId = topicDto.getCid();
        keywordService.addOrDelete(topic.getKeyword(), keywords);

        Channel channel = channelService.get(channelId);
        topic = topicDto.getTopic(topic);
        topic.setChannel(channel);
        topicService.update(topic,attachIds);
        return "redirect:/jsp/common/addSuc.jsp";
    }

    @RequestMapping(value = "changeStatus/{id}", method = RequestMethod.GET)
    @AuthMethod(role = "ROLE_AUDIT")
    public String changeStatus(@PathVariable Long id, Integer status) {
        topicService.changeStatus(id);
        if (status == 0) {
            return "redirect:/admin/topic/unaudits";
        } else {
            return "redirect:/admin/topic/audits";
        }
    }

    private void initList(Integer currentPage, Integer status, String con, Long cid, Model model) {
        QueryHelper queryHelper = new QueryHelper(Topic.class, "t");
        if (cid != null && cid != 0)
            queryHelper.addCondition("t.channel.id", cid);
        if (con != null && !con.trim().equals(""))
            queryHelper.addLikeProperty("t.keyword", "'%" + con + "%'");
        if (status != null)
            queryHelper.addCondition("t.status", status);
        queryHelper.addOrderProperty("t.publishDate", true);
        if (currentPage == null)
            currentPage = 1;
        PageBean pageBean = topicService.getPageBean(currentPage, 3, queryHelper);
        List<Channel> channels = channelService.findAll();
        model.addAttribute("cs", channels);
        model.addAttribute("datas", pageBean);
    }

}
