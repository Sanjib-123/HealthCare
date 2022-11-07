package in.HCL.sanjib.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.HCL.sanjib.entity.User;
import in.HCL.sanjib.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService service;
	
	public String showLogin() {
		return "UserLogin";
		
		
	}
	@GetMapping("/setup")
	public String setup(HttpSession session, Principal p) {
		
		//read current user-name
		String username = p.getName();
		//load user object
		User user = service.findByUsername(username).get();
		
		//store in HttpSession
		session.setAttribute("userOb", user);
		return "UserHome";
	}
	
	@GetMapping("/showPwdUpdate")
	public String showPwdUpdate() {
		return "UserPwdUpdate";
	}
	
	@PostMapping("/pwdUpdate")
	public String updatePwd(
			@RequestParam String password,
			HttpSession session,
			Model model) {
		
		//read current user form session
		User user = (User)session.getAttribute("userOb");
		
		//read userId
		Long userId = user.getId();
		
		//make service call
		service.updateUserPwd(password, userId);
		
		//TODO : EMAIL TASK
		model.addAttribute("message", "Password Updated!");
		return "UserPwdUpdate";
		//return "redirect:logout"
		
	}

}

