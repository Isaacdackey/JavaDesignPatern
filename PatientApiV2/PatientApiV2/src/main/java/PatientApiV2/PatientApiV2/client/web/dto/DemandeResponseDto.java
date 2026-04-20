package PatientApiV2.PatientApiV2.client.web.dto;

import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import java.time.LocalDateTime;

public record DemandeResponseDto(
        Long id,
        Long patientId,
        String patientNom,
        String patientPrenom,
        LocalDateTime dateDemande,
        String motif,
        StatutDemande statut,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}