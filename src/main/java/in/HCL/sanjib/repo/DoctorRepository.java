package in.HCL.sanjib.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HCL.sanjib.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
