package in.HCL.sanjib.service;

import java.util.List;

import in.HCL.sanjib.entity.Appointment;

public interface IAppointmentService {
	
	Long saveAppointment(Appointment appointment);
	
	void updateAppointment(Appointment appointment);
	
	void deleteAppointment(Long id);
	
	Appointment getOneAppointment(Long id);
	
	List<Appointment> getAllAppointments();
	
	List<Object[]> getAppointmentsByDoctor(Long docId);
	
	public List<Object[]> getAppointmentsByDoctorEmail(String userName);
	
	void updateSlotCountForAppointment(Long id, int count);

}
