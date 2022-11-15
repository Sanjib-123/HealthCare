package in.HCL.sanjib.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.HCL.sanjib.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	@Query("SELECT aptm.date, aptm.noOfSlots, aptm.free, aptm.id FROM Appointment aptm INNER JOIN aptm.doctor as doctor WHERE doctor.id=:docId AND aptm.noOfSlots>0")
	public List<Object[]> getAppointmentsByDoctor(Long docId);
	
	

	@Query("SELECT aptm.date, aptm.noOfSlots, aptm.free, aptm.details FROM Appointment aptm INNER JOIN aptm.doctor as doctor WHERE doctor.email=:userName")
	public List<Object[]> getAppointmentsByDoctorEmail(String userName);
	
	@Modifying
	@Query("UPDATE Appointment SET noOfSlots = noOfSlots + :count WHERE id=:id")
	void updateSlotCountForAppointment(Long id, int count);
	

}


