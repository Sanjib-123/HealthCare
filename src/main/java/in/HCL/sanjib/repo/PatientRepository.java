package in.HCL.sanjib.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HCL.sanjib.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
