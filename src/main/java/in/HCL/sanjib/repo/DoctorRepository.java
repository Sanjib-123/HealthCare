package in.HCL.sanjib.repo;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import antlr.collections.List;
//import antlr.collections.List;
import in.HCL.sanjib.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	
	@Query("SELECT id, firstName, lastName FROM Doctor")
	public List<Object[]> getDoctorIdAndNames();

}
