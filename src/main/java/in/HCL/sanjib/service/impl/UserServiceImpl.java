package in.HCL.sanjib.service.impl;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.HCL.sanjib.entity.User;
import in.HCL.sanjib.repo.UserRepository;
import in.HCL.sanjib.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repo;

	@Override
	public Long saveUser(User user) {
		//read generated pwd
		String pwd = user.getPassword();
		//encode password
		String encPwd = passwordEncoder.encode(pwd);
		//set back to object
		user.setPassword(encPwd);
		
		return repo.save(user).getId();
	}
	
	
	

	@Override
	public Optional<User> findByUsername(String username) {
		
		return repo.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			                  throws UsernameNotFoundException {
		
		Optional<User> opt =  findByUsername(username);
		if(!opt.isPresent())
			throw new  UsernameNotFoundException(username);
		else {
			User user = opt.get();
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
				);
		}
	}




	@Transactional
	public void updateUserPwd(String pwd, Long userId) {
		 String encPwd = passwordEncoder.encode(pwd);
		 repo.updateUserPwd(encPwd, userId);
		
	}

}
//control + option + h = to call hierarchy