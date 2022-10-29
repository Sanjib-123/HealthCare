package in.HCL.sanjib.service;

import java.util.List;
import java.util.Map;

import in.HCL.sanjib.entity.Specialization;

public interface ISpecializationService {
	
	public Long saveSpecialization(Specialization spec);
	public List<Specialization> getAllSpecializations();
	public void removeSpecialization(Long id);
	public Specialization getOneSpecialization(Long id);
	public void updateSpecialization(Specialization spec);
	
	public boolean isSpecCodeExist(String specCode);
	public boolean isSpecCodeExistForEdit(String specCode,Long id);
	
	public Map<Long,String> getSpecIdAndName();
	

}
