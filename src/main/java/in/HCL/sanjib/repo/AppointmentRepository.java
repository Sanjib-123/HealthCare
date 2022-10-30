package in.HCL.sanjib.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HCL.sanjib.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
