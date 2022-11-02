package in.HCL.sanjib.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.HCL.sanjib.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	@Query("SELECT aptm.date, aptm.noOfSlots, aptm.free FROM Appointment aptm INNER JOIN aptm.doctor as doctor WHERE doctor.id=:docId")
	public List<Object[]> getAppointmentsByDoctor(Long docId);

}
