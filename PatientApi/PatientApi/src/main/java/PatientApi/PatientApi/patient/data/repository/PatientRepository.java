package PatientApi.PatientApi.patient.data.repository;

import PatientApi.PatientApi.patient.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.nom LIKE %:nom%")
    List<Patient> searchPatientByNom(String nom);

}
