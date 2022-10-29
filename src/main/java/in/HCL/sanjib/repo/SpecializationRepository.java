package in.HCL.sanjib.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.HCL.sanjib.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
	
	@Query("SELECT COUNT(specCode) FROM Specialization WHERE specCode=:specCode")
	Integer getSpecCodeCount(String specCode);
	
	@Query("SELECT COUNT(specCode) FROM Specialization WHERE specCode=:specCode AND id!=:id")
	Integer getSpecCodeCountForEdit(String specCode,Long id);
	
	@Query("SELECT id,specName FROM Specialization")
	List<Object[]> getSpecIdAndName();
	

}

//HQL -- Query
//: means input comes at run time
//Parameter----->
//a.Positional Parameters ?1,?2,?3...
//b.Named Parameters :a,:b,:mno