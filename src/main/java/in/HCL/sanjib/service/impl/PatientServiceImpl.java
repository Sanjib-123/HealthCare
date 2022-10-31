package in.HCL.sanjib.service.impl;

import java.util.List;

//import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.HCL.sanjib.constants.UserRoles;
import in.HCL.sanjib.entity.Patient;
import in.HCL.sanjib.entity.User;
import in.HCL.sanjib.repo.PatientRepository;
import in.HCL.sanjib.service.IPatientService;
import in.HCL.sanjib.service.IUserService;
import in.HCL.sanjib.util.UserUtil;

@Service
public class PatientServiceImpl implements IPatientService {
	
	@Autowired
	private PatientRepository repo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil util;

	@Override
	@Transactional
	public Long savePatient(Patient patient) {
		Long id = repo.save(patient).getId();
		if(id != null) {
			User user = new User();
			user.setDisplayName(patient.getFirstName()+" "+patient.getLastName());
			user.setUsername(patient.getEmail());
			user.setPassword(util.genPwd());
			user.setRole(UserRoles.PATIENT.name());
			userService.saveUser(user);
			//TODO :Email part is pending 
		}
		return id;
	}

	@Override
	@Transactional
	public void updatePatient(Patient patient) {
		repo.save(patient);

	}

	@Override
	@Transactional
	public void deletePatient(Long id) {
		repo.deleteById(id);

	}

	@Override
	@Transactional(
			readOnly = true
			)
	public Patient getOnePatient(Long id) {
		
		return repo.findById(id).get();
	}

	@Override
	@Transactional(
			readOnly = true
			)
	public List<Patient> getAllPatients() {
		
		return repo.findAll();
	}

}
