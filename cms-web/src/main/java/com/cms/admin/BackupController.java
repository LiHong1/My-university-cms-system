package com.cms.admin;

import com.cms.auth.AuthClass;
import com.cms.utils.BackupFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@AuthClass
@Controller
@RequestMapping("/admin")
public class BackupController {

	@RequestMapping(value="/backup/add",method=RequestMethod.GET)
	public String backup() {
		return "backup/add";
	}
	
	@RequestMapping(value="/backup/add",method=RequestMethod.POST)
	public String backup(String backupFilename,HttpServletRequest request ) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(getRealPath(request));
		bfu.backup(backupFilename);
		return "redirect:/admin/backups";
	}
	
	@RequestMapping(value="/backups")
	public String list(Model model,HttpServletRequest request) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(getRealPath(request));
		model.addAttribute("backups", bfu.listBackups());
		return "backup/list";
	}
	
	@RequestMapping("delete/{name}")
	public String delete(@PathVariable String name,String type,HttpServletRequest request) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(getRealPath(request));
		bfu.delete(name + "." + type);
		return "redirect:/admin/backups";
	}
	
	@RequestMapping("resume/{name}")
	public String resume(@PathVariable String name,String type,HttpServletRequest request) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(getRealPath(request));
		bfu.resume(name+"."+type);
		return "redirect:/admin/backups";
	}
	private String getRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("");
	}
}
