package PatientApiV2.PatientApiV2.patient.data.repository;

import PatientApiV2.PatientApiV2.patient.data.entity.Demande;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {


    List<Demande> findByDateDemandeBetween(LocalDateTime debut, LocalDateTime fin);
    List<Demande> findByPatientId(Long patientId);

    List<Demande> findByStatut(StatutDemande statut);


    List<Demande> findByPatientIdAndStatut(Long patientId, StatutDemande statut);

    List<Demande> findByPatientIdAndDateDemandeBetween(Long patientId, LocalDateTime debut, LocalDateTime fin);

    List<Demande> findByDateDemandeBetweenAndStatut(LocalDateTime debut, LocalDateTime fin, StatutDemande statut);

    List<Demande> findByPatientIdAndDateDemandeBetweenAndStatut(Long patientId, LocalDateTime debut, LocalDateTime fin, StatutDemande statut);
}