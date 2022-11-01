package in.HCL.sanjib.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import in.HCL.sanjib.constants.UserRoles;
import in.HCL.sanjib.entity.User;
import in.HCL.sanjib.service.IUserService;
import in.HCL.sanjib.util.MyMailUtil;
import in.HCL.sanjib.util.UserUtil;

@Component
public class MasterAccountSetupRunner implements CommandLineRunner {
	
	@Value("${master.user.name}")
	private String displayName;
	
	@Value("${master.user.email}")
	private String username;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private MyMailUtil mailUtil;

	@Override
	public void run(String... args) throws Exception {
		if(!userService.findByUsername(username).isPresent()) {
			String pwd = userUtil.genPwd();
			User user = new User();
			user.setDisplayName(displayName);
			user.setUsername(username);
			user.setPassword(pwd);
			user.setRole(UserRoles.ADMIN.name());
			//userService.saveUser(user);
			//TODO : EMAIL SERVICE
			Long genId = userService.saveUser(user);
			if(genId!=null)
				new Thread(new Runnable() {	
				public void run() {
			String text  = "Your uname is " + username + ", password is "+ pwd;
			mailUtil.send(username, "ADMIN ADDED", text);
				}
		}).start();
			
		}

	}

}
