package PatientApiV2.PatientApiV2.patient.data.repository;

import PatientApiV2.PatientApiV2.patient.data.entity.RendezVous;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutRdv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByStatut(StatutRdv statut);
    List<RendezVous> findByDateRendezVousBetween(LocalDateTime debut, LocalDateTime fin);
    List<RendezVous> findByPatientIdAndStatut(Long patientId, StatutRdv statut);

    @Query("SELECT r FROM RendezVous r WHERE r.patient.id = :patientId " +
            "AND r.dateRendezVous BETWEEN :debut AND :fin " +
            "AND r.statut != :statutAnnule")
    List<RendezVous> findConflitsRendezVous(@Param("patientId") Long patientId,
                                            @Param("debut") LocalDateTime debut,
                                            @Param("fin") LocalDateTime fin,
                                            @Param("statutAnnule") StatutRdv statutAnnule);
}