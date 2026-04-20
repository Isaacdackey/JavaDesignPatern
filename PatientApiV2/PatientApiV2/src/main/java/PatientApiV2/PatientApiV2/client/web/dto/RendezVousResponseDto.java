package PatientApiV2.PatientApiV2.client.web.dto;

import PatientApiV2.PatientApiV2.patient.data.entity.StatutRdv;
import java.time.LocalDateTime;

public record RendezVousResponseDto(
        Long id,
        Long patientId,
        String patientNom,
        String patientPrenom,
        LocalDateTime dateRendezVous,
        String motif,
        StatutRdv statut,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}