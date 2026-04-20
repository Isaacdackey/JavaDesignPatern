package PatientApiV2.PatientApiV2.patient.service;

import PatientApiV2.PatientApiV2.client.web.dto.DemandeCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.DemandeResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import java.time.LocalDate;
import java.util.List;

public interface DemandeService {
    DemandeResponseDto creerDemande(DemandeCreateRequestDto requestDto);
    List<DemandeResponseDto> getDemandesDuJour();
    DemandeResponseDto annulerDemande(Long id);
    DemandeResponseDto validerDemande(Long id);
    List<DemandeResponseDto> getDemandesWithFilters(LocalDate date, Long patientId, StatutDemande statut);
}