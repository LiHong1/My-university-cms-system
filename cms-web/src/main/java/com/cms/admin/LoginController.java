package com.cms.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cms.entity.Role;
import com.cms.entity.RoleType;
import com.cms.entity.User;
import com.cms.service.UserService;
import com.cms.utils.DrawCodeUtil;

@Controller
public class LoginController {
    @Inject
	private UserService userService;

	
	private boolean isAdmin(List<Role> rs) {
		for(Role r:rs) {
			if(r.getRoleType()==RoleType.ROLE_ADMIN) return true;
			
		}
		return false;
	}
    @RequestMapping(value="/login",method=RequestMethod.GET)
    public String login(){
        return "admin/login";
    }
	  @RequestMapping(value="/login",method=RequestMethod.POST)
	    public String login(String username,String password,String validateCode,HttpServletRequest req,Model model){
	        boolean loginAble=false;
	        HttpSession session=req.getSession();
	        User user = null;

	        if(validateCode==null||!validateCode.equalsIgnoreCase((String)session.getAttribute("checkcode") ))
	        {
				System.out.println("in session="+session.getAttribute("checkcode"));
				System.out.println("submit="+validateCode);
				model.addAttribute("error", "你输入的验证码有错");
	           
	        }
	        else{
	            System.out.println(username);
	            System.out.println(password);
	            user=userService.login(username,password);
	            if(user==null){
	                model.addAttribute("error", "你输入的用户名或密码有错");
	            }else if(user.getStatus()==0){
	                model.addAttribute("error", "该用户已经被停用");
	            }else{
	                loginAble=true;
	                List<Role> roles=user.getRoles();
	                boolean isAdmin = isAdmin(roles);
	                req.getSession().setAttribute("isAdmin", isAdmin);
	                if(!isAdmin)
	                    session.setAttribute("allActions", getAllActions(roles, session));
	            }
	        }
	        if(loginAble==true)
	        {
	            req.getSession().setAttribute("user", user);
	            return "redirect:admin/index";
	        }
	        else return "admin/login";
	    }
	  @SuppressWarnings("unchecked")
    private Set<String> getAllActions(List<Role> rs,HttpSession session) {
        Set<String> actions = new HashSet<String>();
        Map<String,Set<String>> allAuths = (Map<String,Set<String>>)session.getServletContext().getAttribute("allAuths");
        actions.addAll(allAuths.get("base"));
        for(Role r:rs) {
            actions.addAll(allAuths.get(r.getRoleType().name()));
        }
        return actions;
    }
	    
	    
	@RequestMapping("/drawCode")
    public void drawCode(HttpServletResponse resp,HttpSession session){
        int width=100;
        int height=25;
       
        resp.setContentType("image/jpg");
        OutputStream out = null;
           try {
           out=resp.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DrawCodeUtil drawCodeUtil=DrawCodeUtil.getInstance();
        drawCodeUtil.set(width, height);
        String checkcode = drawCodeUtil.generateCheckcode();
        session.setAttribute("checkcode", checkcode);
        BufferedImage image=drawCodeUtil.generateCheckImg(checkcode);
        
        try {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
