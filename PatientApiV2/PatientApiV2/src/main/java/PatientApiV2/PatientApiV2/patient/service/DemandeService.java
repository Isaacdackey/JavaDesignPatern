package PatientApiV2.PatientApiV2.patient.service;

import PatientApiV2.PatientApiV2.client.web.dto.DemandeCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.DemandeResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface DemandeService {
    DemandeResponseDto creerDemande(DemandeCreateRequestDto requestDto);
    List<DemandeResponseDto> getDemandesDuJour();
    List<DemandeResponseDto> getDemandesByPatientId(Long patientId);
    DemandeResponseDto annulerDemande(Long id);
    DemandeResponseDto validerDemande(Long id);
    List<DemandeResponseDto> getDemandesWithFilters(LocalDate date, Long patientId, StatutDemande statut);

    List<DemandeResponseDto> getAllDemandes();

    Page<DemandeResponseDto> getAllDemandesPaged(Pageable pageable);
    DemandeResponseDto getDemandeById(Long id);

}