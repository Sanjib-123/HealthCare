package in.HCL.sanjib.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.HCL.sanjib.entity.Doctor;
import in.HCL.sanjib.exception.DoctorNotFoundException;
import in.HCL.sanjib.repo.DoctorRepository;
import in.HCL.sanjib.service.IDoctorService;
import in.HCL.sanjib.util.MyCollectionsUtil;

@Service

public class DoctorServiceImpl implements IDoctorService  {
	
	@Autowired
	private DoctorRepository repo;

	@Override
	public Long saveDoctor(Doctor doc) {
		
		return repo.save(doc).getId();
	}

	@Override
	public List<Doctor> getAllDoctors() {
		
		return repo.findAll();
	}

	@Override
	public void removeDoctor(Long id) {
		
		repo.delete(getOneDoctor(id));

	}

	@Override
	public Doctor getOneDoctor(Long id) {
		
		return repo.findById(id).orElseThrow(
				()->new DoctorNotFoundException(id+", not exist")
				);
	}

	@Override
	public void updateDoctor(Doctor doc) {
		if(repo.existsById(doc.getId()))
		    repo.save(doc);	
		else throw new DoctorNotFoundException(doc.getId()+", not exists");
			

	}

	@Override
	public Map<Long, String> getDoctorIdAndNames() {
		List<Object[]> list = repo.getDoctorIdAndNames();
		return MyCollectionsUtil.convertToMapIndex(list);
		
	}

}
