package in.HCL.sanjib.controller;

import java.security.Principal;
import java.util.Optional;

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
import in.HCL.sanjib.util.MyMailUtil;
import in.HCL.sanjib.util.UserUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService service;
	
	@Autowired
	private UserUtil util;
	
	@Autowired
	private MyMailUtil mailUtil;
	
	@GetMapping("/login")
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
	
	@GetMapping("/showForgot")
	public String showForgot() {
		return "UserNewPwdGen";
		
		
	}
	
	@PostMapping("/genNewPwd")
	public String genNewPwdGen(
			Model model,
			@RequestParam String email) {
		
		Optional<User> opt = service.findByUsername(email);
		if(opt.isPresent()) {
			User user = opt.get();
			//Generate new password
			String pwd = util.genPwd();
			//encode and update in db
			service.updateUserPwd(pwd, user.getId());
			
			//send message to UI
			model.addAttribute("message", "Password Updated! Check your Inbox! !");
			//send email to user
			if(user.getId()!=null)
				new Thread(new Runnable() {
					public void run() {
						String text = "YOUR USERNAME IS: "+ user.getUsername()+", AND NEW PASSWORD IS"+pwd;
						mailUtil.send(user.getUsername(),"PASSWORD UPDATED!",text);
					}
					
				
			}).start();
		}else { //if user is not present
			model.addAttribute("message", "User Not Found!");
		}
		return "UserNewPwdGen";
	}

}

