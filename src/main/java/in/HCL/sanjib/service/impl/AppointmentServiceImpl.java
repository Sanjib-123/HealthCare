package in.HCL.sanjib.service.impl;

import java.util.List;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.HCL.sanjib.entity.Appointment;
import in.HCL.sanjib.repo.AppointmentRepository;
import in.HCL.sanjib.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
	
	@Autowired
	private AppointmentRepository repo;

	@Override
	@Transactional
	public Long saveAppointment(Appointment appointment) {
		
		return repo.save(appointment).getId();
	}

	@Override
	@Transactional
	public void updateAppointment(Appointment appointment) {
		repo.save(appointment);

	}

	@Override
	@Transactional
	public void deleteAppointment(Long id) {
		repo.deleteById(id);

	}

	@Override
	@Transactional(
			readOnly = true)
	public Appointment getOneAppointment(Long id) {
		
		return repo.findById(id).get();
	}

	@Override
	@Transactional(
			readOnly = true)
	public List<Appointment> getAllAppointments() {
		
		return repo.findAll();
	}

	@Override
	public List<Object[]> getAppointmentsByDoctor(Long docId) {
		
		return repo.getAppointmentsByDoctor(docId);
	}

}
