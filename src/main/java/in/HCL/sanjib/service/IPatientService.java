package in.HCL.sanjib.service;

import java.util.List;

import in.HCL.sanjib.entity.Patient;

public interface IPatientService {
	
	Long savePatient(Patient patient);
	
	void updatePatient(Patient patient);
	
	void deletePatient(Long id);
	
	Patient getOnePatient(Long id);
	
	List<Patient> getAllPatients();

}
