package com.cms.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cms.auth.AuthClass;
import com.cms.beans.ChannelTree;
import com.cms.beans.PageBean;
import com.cms.entity.Group;
import com.cms.service.GroupService;
import com.cms.utils.QueryHelper;

@Controller
@RequestMapping("/admin/group")
@AuthClass
public class GroupController {
    @Autowired
    private GroupService groupService;
	@RequestMapping("/groups")
	public String list(Model model) {
	     PageBean pageBean=groupService.getPageBean(1,2, new QueryHelper(Group.class));
	     model.addAttribute(pageBean);
		return "group/list";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(Group group) {
        groupService.save(group);
        return "redirect:/admin/group/groups";
    } 
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Group());
        return "group/add";
    } 
    @RequestMapping(value="/update/{id}",method=RequestMethod.POST)
    public String update(@PathVariable Long id,Group g) {
        Group group=groupService.get(id);
        group.setDescr(g.getDescr());
        group.setName(g.getName());
        groupService.update(group);
        return "redirect:/admin/group/groups";
    } 
    @RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String update(@PathVariable Long id,Model model) {
        Group group=groupService.get(id);
        model.addAttribute(group);
        return "group/update";
    } 
    @RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
    public String delete(@PathVariable Long id,Model model) {
        groupService.delete(id);
        return "redirect:/admin/group/groups";
    } 
    @RequestMapping("/{id}")
    public String show(@PathVariable Long id,Model model) {
        Group group=groupService.get(id);
        model.addAttribute(group);
        model.addAttribute("us", group.getUsers());
        return "group/show";
    }
    @RequestMapping(value="/listChannels/{id}",method=RequestMethod.GET)
    public String listChannels(@PathVariable Long id,Model model){
        Group group=groupService.get(id);
        model.addAttribute(group);
        return "group/listChannel";
    }
    @RequestMapping(value="/setChannels/{id}",method=RequestMethod.GET)
    public String setChannels(@PathVariable Long id,Model model){
        model.addAttribute(groupService.get(id));
        model.addAttribute("cids",groupService.listChannelIds(id));
        return "/group/setChannel";
    }
    @RequestMapping(value="/groupTree/{id}",method=RequestMethod.POST)
    public @ResponseBody List<ChannelTree> groupTree(@PathVariable Long id,Model model){
        return groupService.getChannelTree(id);
    }
}
