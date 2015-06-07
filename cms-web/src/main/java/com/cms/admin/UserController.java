package com.cms.admin;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cms.auth.AuthClass;
import com.cms.auth.AuthMethod;
import com.cms.beans.ChannelTree;
import com.cms.beans.CmsException;
import com.cms.beans.PageBean;
import com.cms.dto.UserDto;
import com.cms.entity.Group;
import com.cms.entity.Role;
import com.cms.entity.User;
import com.cms.service.GroupService;
import com.cms.service.RoleService;
import com.cms.service.UserService;
import com.cms.utils.QueryHelper;

@Controller
@RequestMapping("/admin/user")
@AuthClass("login")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private GroupService groupService;
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String list(Model model) {
	    PageBean pageBean=userService.getPageBean(1,2,new QueryHelper(User.class));
	    model.addAttribute("pageBean",pageBean);
		return "user/list";
	} 
	@RequestMapping(value="/users",method=RequestMethod.POST)
    public String list(int pageNum,Model model) {
        PageBean pageBean=userService.getPageBean(pageNum,2,new QueryHelper(User.class));
        model.addAttribute("pageBean",pageBean);
        return "user/list";
    } 
	@RequestMapping(value="/updateStatus/{id}",method=RequestMethod.GET)
    public String updateStatus(@PathVariable int id) {
        userService.updateStatus(id);
        return "redirect:/admin/user/users";
    } 
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
    public String delete(@PathVariable int id) {
	    userService.delete(id);
	    System.out.println(userService.get(id));
        return "redirect:/admin/user/users";
    } 
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String update(@PathVariable long id,Model model) {
        User user=userService.get(id);
        UserDto userDto=new UserDto(user,roleService.getRoleIds(id),groupService.getGroupIds(id));
        
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute(userDto);
        return "user/update";
    } 
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
    public String update(@PathVariable int id,HttpSession session,@Valid UserDto userDto,BindingResult br,Model model) {
        if(br.hasErrors()) {
            initAddUser(model);
            return "user/update";
        }
        User ou = userService.get(id);
        ou.setNickname(userDto.getNickname());
        ou.setPhone(userDto.getPhone());
        ou.setEmail(userDto.getEmail());
        ou.setStatus(userDto.getStatus());
        ou.setGroups(groupService.findByIds(userDto.getGroupIds()));
        ou.setRoles(roleService.findByIds(userDto.getRoleIds()));
        userService.update(ou);
        return "redirect:/admin/user/users";
    }
	   @RequestMapping(value="/updateSelf",method=RequestMethod.GET)
	    public String updateSelf(Model model,HttpSession session) {
	        User user=getSessionUser(session);
            user=userService.get(user.getId());
	        model.addAttribute(user);
	        return "user/updateSelf";
	    } 
	    @RequestMapping(value="/updateSelf",method=RequestMethod.POST)
	    public String updateSelf(@Valid User user,BindingResult br,HttpSession session) {
	        if(br.hasErrors()) {
	            System.out.println(br.getAllErrors().get(0));
	            return "user/updateSelf";
	        }
	        User ou=getSessionUser(session);
	        ou=userService.get(ou.getId());
	        ou.setNickname(user.getNickname());
	        ou.setPhone(user.getPhone());
	        ou.setEmail(user.getEmail());
	        System.out.println(user.getPhone());
	        userService.update(ou);
	        return "redirect:/admin/user/users";
	    }
	    @RequestMapping(value="/showSelf",method=RequestMethod.GET)
	    @AuthMethod
        public String showSelf(Model model,HttpSession session) {
	        User user=getSessionUser(session);
	        user=userService.get(user.getId());
            model.addAttribute(user);
            return "user/showSelf";
        } 
	@RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model) {
        
        List<Role> roles=roleService.findAll();
        List<Group> groups=groupService.findAll();
        model.addAttribute("userDto",new UserDto());
        model.addAttribute("roles",roles);
        model.addAttribute("groups",groups);
        return "user/add";
    } 
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@Valid UserDto userDto,BindingResult br,Model model) {
        if(br.hasErrors()) {
            initAddUser(model);
            return "user/add";
        }
        User u=new User(userDto.getUsername(),userDto.getPassword(),userDto.getNickname(),userDto.getEmail(),
                userDto.getPhone(),userDto.getStatus(),new Date());
        u.setGroups(groupService.findByIds(userDto.getGroupIds()));
        u.setRoles(roleService.findByIds(userDto.getRoleIds()));
        userService.save(u);
        return "redirect:/admin/user/users";
    }
    @RequestMapping(value="/updatePwd",method=RequestMethod.GET)
    @AuthMethod
    public String updatePwd(Model model,HttpSession session) {
        User user=getSessionUser(session);
        user=userService.get(user.getId());
        model.addAttribute(user);
        return "user/updatePwd";
    } 
    @RequestMapping(value="/updatePwd/{id}",method=RequestMethod.POST)
    @AuthMethod
    public String updatePwd(@PathVariable Long id,String oldPwd,String password) {
        if(userService.updatePwd(id,oldPwd,password))
        return "redirect:/admin/user/showSelf/"+id;
        else return "redirect:/admin/user/updatePwd";
    }
    @RequestMapping(method=RequestMethod.GET)
    public String show(HttpSession session,Model model) {
        User user=getSessionUser(session);
        user=userService.get(user.getId());
        model.addAttribute(user);
        model.addAttribute("gs",user.getGroups());
        model.addAttribute("rs",user.getRoles());
        return "user/show";
    }
    @RequestMapping(value="/listChannels/{uid}",method=RequestMethod.GET)
    public String listChannels(@PathVariable Long uid,Model model){
        User user=userService.get(uid);
        model.addAttribute(user);
        return "user/listChannel";
    }
    @RequestMapping(value="/userTree/{uid}",method=RequestMethod.POST)
    public @ResponseBody List<ChannelTree> listChannels(@PathVariable Long uid){
        User user=userService.get(uid);
        List<ChannelTree> channelTree=userService.getChannelTree(uid);
        return channelTree;
       
    }
    
    private User getSessionUser(HttpSession session){
        User user=((User)session.getAttribute("user"));
        if(user==null)
            throw new CmsException("请重新登录");
        return user;
    }
    private void initAddUser(Model model) {
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("groups", groupService.findAll());
    }
}
