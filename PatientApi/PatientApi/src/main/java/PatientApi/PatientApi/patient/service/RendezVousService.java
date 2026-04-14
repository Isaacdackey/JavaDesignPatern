package PatientApi.PatientApi.patient.service;

import PatientApi.PatientApi.patient.data.entity.RendezVous;
import PatientApi.PatientApi.patient.data.entity.StatutRdv;
import java.util.List;
import java.util.Optional;

public interface RendezVousService {
    RendezVous creerRendezVous(RendezVous rendezVous);
    Optional<RendezVous> getRendezVousById(Long id);
    List<RendezVous> getRendezVousByPatient(Long patientId);
    List<RendezVous> getAllRendezVous();
    RendezVous annulerRendezVous(Long id);
    RendezVous confirmerRendezVous(Long id);
    RendezVous modifierRendezVous(Long id, RendezVous rendezVous);
    List<RendezVous> getRendezVousByStatut(StatutRdv statut);
    List<RendezVous> getRendezVousByPatientAndStatut(Long patientId, StatutRdv statut);
}