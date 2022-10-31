package in.HCL.sanjib.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HCL.sanjib.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
