package in.HCL.sanjib.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		//read currect username
		String username = p.getName();
		//load user object
		User user = service.findByUsername(username).get();
		
		//store in HttpSession
		session.setAttribute("userOb", user);
		return "UserHome";
	}

}
