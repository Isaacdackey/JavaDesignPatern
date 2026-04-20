package PatientApiV2.PatientApiV2.patient.data.repository;

import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.nom LIKE %:nom%")
    List<Patient> searchPatientByNom(String nom);

}
