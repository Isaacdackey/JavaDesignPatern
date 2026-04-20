package PatientApiV2.PatientApiV2.patient.service;

import PatientApiV2.PatientApiV2.client.web.dto.RendezVousCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousResponseDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousUpdateRequestDto;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutRdv;

import java.util.List;
import java.util.Optional;

public interface RendezVousService {
    RendezVousResponseDto creerRendezVous(RendezVousCreateRequestDto requestDto);
    Optional<RendezVousResponseDto> getRendezVousById(Long id);
    List<RendezVousResponseDto> getRendezVousByPatient(Long patientId);
    List<RendezVousResponseDto> getAllRendezVous();
    RendezVousResponseDto annulerRendezVous(Long id);
    RendezVousResponseDto confirmerRendezVous(Long id);
    RendezVousResponseDto modifierRendezVous(Long id, RendezVousUpdateRequestDto updateDto);
    List<RendezVousResponseDto> getRendezVousByStatut(StatutRdv statut);
    List<RendezVousResponseDto> getRendezVousByPatientAndStatut(Long patientId, StatutRdv statut);
}