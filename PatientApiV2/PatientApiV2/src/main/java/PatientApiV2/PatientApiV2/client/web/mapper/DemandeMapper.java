package PatientApiV2.PatientApiV2.client.web.mapper;

import PatientApiV2.PatientApiV2.client.web.dto.DemandeCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.DemandeResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.Demande;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;

public final class DemandeMapper {

    private DemandeMapper() {}

    public static Demande toEntity(DemandeCreateRequestDto dto, Patient patient) {
        if (dto == null || patient == null) {
            return null;
        }
        return Demande.builder()
                .patient(patient)
                .dateDemande(dto.dateDemande())
                .motif(dto.motif())
                .statut(StatutDemande.EN_ATTENTE)
                .build();
    }

    public static DemandeResponseDto toDto(Demande demande) {
        if (demande == null) {
            return null;
        }
        return new DemandeResponseDto(
                demande.getId(),
                demande.getPatient().getId(),
                demande.getPatient().getNom(),
                demande.getPatient().getPrenom(),
                demande.getDateDemande(),
                demande.getMotif(),
                demande.getStatut(),
                demande.getCreatedAt(),
                demande.getUpdatedAt()
        );
    }
}