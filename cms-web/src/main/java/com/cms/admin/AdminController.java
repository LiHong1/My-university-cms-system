package com.cms.admin;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cms.auth.AuthClass;
import com.cms.auth.AuthMethod;

@Controller
@RequestMapping("/admin")
@AuthClass("login")
public class AdminController {
    
    @RequestMapping("index")
    @AuthMethod
	public String index() {
		return "admin/index";
	}

  
    @AuthMethod
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
