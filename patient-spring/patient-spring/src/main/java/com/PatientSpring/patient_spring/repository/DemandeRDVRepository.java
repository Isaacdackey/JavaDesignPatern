package com.PatientSpring.patient_spring.repository;

import com.PatientSpring.patient_spring.entity.DemandeRDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRDVRepository extends JpaRepository<DemandeRDV, Long> {
    List<DemandeRDV> findByPatientId(Long patientId);

    @Query("SELECT d FROM DemandeRDV d ORDER BY d.dateDemande DESC")
    List<DemandeRDV> findEnAttente();
}