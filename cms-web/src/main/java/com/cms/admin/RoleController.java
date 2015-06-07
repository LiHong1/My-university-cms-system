package com.cms.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cms.auth.AuthClass;
import com.cms.beans.PageBean;
import com.cms.entity.Role;
import com.cms.entity.User;
import com.cms.service.RoleService;
import com.cms.utils.QueryHelper;

@Controller
@RequestMapping("/admin/role")
@AuthClass
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping(value="/roles",method=RequestMethod.GET)
    public String list(Model model) {
        List<Role> roles=roleService.findAll();
        model.addAttribute("roles", roles);
        return "role/list";
    } 
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(Role role) {
        roleService.save(role);
        return "redirect:/admin/role/roles";
    } 
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Role());
        return "role/add";
    } 
    @RequestMapping(value="/update/{id}",method=RequestMethod.POST)
    public String update(Role role) {
        roleService.update(role);
        return "redirect:/admin/role/roles";
    } 
    @RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String update(@PathVariable long id,Model model) {
        Role role=roleService.get(id);
        model.addAttribute(role);
        return "role/update";
    } 
    @RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
    public String delete(@PathVariable long id,Model model) {
        roleService.delete(id);
        return "redirect:/admin/role/roles";
    } 
    @RequestMapping("/{id}")
    public String show(@PathVariable int id,Model model) {
        Role role=roleService.get(id);
        model.addAttribute(role);
        model.addAttribute("us",role.getUsers());
        return "role/show";
    }
}
