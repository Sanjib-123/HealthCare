package in.HCL.sanjib.service.impl;

import java.util.List;

//import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.HCL.sanjib.entity.Patient;
import in.HCL.sanjib.repo.PatientRepository;
import in.HCL.sanjib.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {
	
	@Autowired
	private PatientRepository repo;

	@Override
	public Long savePatient(Patient patient) {
		
		return repo.save(patient).getId();
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
