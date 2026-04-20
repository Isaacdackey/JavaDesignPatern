package PatientApiV2.PatientApiV2.client.web.mapper;

import PatientApiV2.PatientApiV2.client.web.dto.RendezVousCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousResponseDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousUpdateRequestDto;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.data.entity.RendezVous;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutRdv;

public final class RendezVousMapper {

    private RendezVousMapper() {}

    public static RendezVous toEntity(RendezVousCreateRequestDto dto, Patient patient) {
        if (dto == null || patient == null) {
            return null;
        }
        return RendezVous.builder()
                .patient(patient)
                .dateRendezVous(dto.dateRendezVous())
                .motif(dto.motif())
                .statut(StatutRdv.EN_ATTENTE)
                .build();
    }


    public static void updateEntity(RendezVous existingRendezVous, RendezVousUpdateRequestDto dto) {
        if (existingRendezVous == null || dto == null) {
            return;
        }

        if (dto.dateRendezVous() != null) {
            existingRendezVous.setDateRendezVous(dto.dateRendezVous());
        }

        if (dto.motif() != null && !dto.motif().isBlank()) {
            existingRendezVous.setMotif(dto.motif());
        }

        if (dto.statut() != null && !dto.statut().isBlank()) {
            try {
                StatutRdv statut = StatutRdv.valueOf(dto.statut().toUpperCase());
                existingRendezVous.setStatut(statut);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Statut invalide. Valeurs acceptées: EN_ATTENTE, CONFIRMÉ, ANNULÉ");
            }
        }
    }

    public static RendezVousResponseDto toDto(RendezVous rendezVous) {
        if (rendezVous == null) {
            return null;
        }
        return new RendezVousResponseDto(
                rendezVous.getId(),
                rendezVous.getPatient().getId(),
                rendezVous.getPatient().getNom(),
                rendezVous.getPatient().getPrenom(),
                rendezVous.getDateRendezVous(),
                rendezVous.getMotif(),
                rendezVous.getStatut(),
                rendezVous.getCreatedAt(),
                rendezVous.getUpdatedAt()
        );
    }
}