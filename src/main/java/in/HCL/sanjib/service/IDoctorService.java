package in.HCL.sanjib.service;

import java.util.List;

import in.HCL.sanjib.entity.Doctor;
import in.HCL.sanjib.entity.Specialization;

public interface IDoctorService {
	
	public Long saveDoctor(Doctor doc);
	public List<Doctor> getAllDoctors();
	public void removeDoctor(Long id);
	public Doctor getOneDoctor(Long id);
	public void updateDoctor(Doctor doc);

}
