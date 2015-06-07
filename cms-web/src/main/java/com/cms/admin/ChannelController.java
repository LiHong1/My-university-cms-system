package com.cms.admin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cms.auth.AuthClass;
import com.cms.auth.AuthMethod;
import com.cms.beans.ChannelTree;
import com.cms.entity.Channel;
import com.cms.entity.ChannelType;
import com.cms.service.ChannelService;
import com.cms.utils.EnumUtil;

@Controller
@RequestMapping("/admin/channel")
@AuthClass()
public class ChannelController {
    @Inject
    private ChannelService channelService;
	@RequestMapping("/channels")
	public String list(Model model) {
		return "channel/list";
	}
	@RequestMapping("/treeAll")
	@AuthMethod(role="ROLE_PUBLISH,ROLE_AUDIT")
	public @ResponseBody List<ChannelTree> getChannelTree(){
	    List<ChannelTree> channelTree=channelService.findChannelTree();
	    System.out.println(channelTree.size());
	    channelTree.add(new ChannelTree(0l, "网站根栏目", -1l));
      for(ChannelTree ct:channelTree) {
          if(ct.getPid()==null)
            ct.setPid(0l);
      }
	    return channelTree;
	}
	@RequestMapping(value="/treeAs",method=RequestMethod.POST)
	@AuthMethod(role="ROLE_PUBLISH,ROLE_AUDIT")
    public @ResponseBody List<ChannelTree> tree(@Param Integer pid) {
        List<ChannelTree> tds = new ArrayList<ChannelTree>();
        if(pid==null||pid<=0) {
            tds.add(new ChannelTree(0l,"网站根栏目",1l));
            return tds;
        }
        return tds;
    }
	@RequestMapping("/channels/updateSort")
    public @ResponseBody void updateSort(Long[] ids) {
	    channelService.updateOrder(ids);
    }
	@RequestMapping("/channels/{pid}")
    public String listChild(@PathVariable Integer pid,@Param Integer refresh,Model model) {
        Channel pc = null;
        if(refresh==null) {
            model.addAttribute("refresh",0);
        } else {
            model.addAttribute("refresh",1);
        }
        if(pid==null||pid<=0) {
            pc = new Channel();
            pc.setName(Channel.ROOT_NAME);
            pc.setId(Channel.ROOT_ID);
        } else
            pc = channelService.get(pid);
        model.addAttribute("pc", pc);
        model.addAttribute("channels",channelService.findChilds(pid));
        return "channel/list_child";
    }
	@RequestMapping(value="/add/{pid}",method=RequestMethod.POST)
    public String add(@PathVariable long pid,Channel channel,BindingResult br,Model model) {
	    if(br.hasErrors()) {
            initAdd(model, pid);
            return "channel/add";
        }
        channelService.add(channel, pid);
        return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
    } 
	
    @RequestMapping(value="/add/{id}",method=RequestMethod.GET)
    public String add(@PathVariable Long id,Model model) {
        initAdd(model, id);
        return "channel/add";
    } 
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public String update(Channel channel) {
        return "redirect:/admin/channel/channels";
    } 
    @RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String update(@PathVariable long id,Model model) {
        Channel channel=channelService.get(id);
        model.addAttribute("channel",channel);
        Channel pc;
        if(channel.getParent()==null) {
            pc = new Channel();
            pc.setId(Channel.ROOT_ID);
            pc.setName(Channel.ROOT_NAME);
        } else {
            pc = channel.getParent();
        }
        model.addAttribute("pc",pc);
        model.addAttribute("types", EnumUtil.enumProp2Name(ChannelType.class, "name"));
        return "channel/update";
    } 
    @RequestMapping(value="/delete/{pid}/{id}",method=RequestMethod.GET)
    public String delete(@PathVariable Long pid,@PathVariable Long id,Model model) {
        channelService.delete(id);
        return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
    } 
    @RequestMapping("/{id}")
    public String show(@PathVariable int id,Model model) {
        return "channel/show";
    }
    private void initAdd(Model model,Long pid){
        model.addAttribute("types",EnumUtil.enumProp2Name(ChannelType.class, "name"));
        Channel pc;
        if(pid==null||pid==0){
            pc=new Channel();
            pc.setId(Channel.ROOT_ID);
            pc.setName(Channel.ROOT_NAME);
        }
        else pc=channelService.get(pid); 
            model.addAttribute("pc",pc);
        model.addAttribute(new Channel());
    }
}
