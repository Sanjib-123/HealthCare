package in.HCL.sanjib.service;

import java.util.Optional;

import in.HCL.sanjib.entity.User;

public interface IUserService {
	
	Long saveUser(User user);
	Optional<User> findByUsername(String username);

}
