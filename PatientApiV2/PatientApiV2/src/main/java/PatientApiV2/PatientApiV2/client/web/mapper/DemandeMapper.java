package PatientApiV2.PatientApiV2.client.web.mapper;

import PatientApiV2.PatientApiV2.client.web.dto.DemandeCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.DemandeResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.Demande;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import PatientApiV2.PatientApiV2.shared.mapper.DateMapper;
import org.springframework.stereotype.Component;

@Component
public final class DemandeMapper {

    private final DateMapper dateMapper;

    public DemandeMapper(DateMapper dateMapper) {
        this.dateMapper = dateMapper;
    }

    public Demande toEntity(DemandeCreateRequestDto dto, Patient patient) {
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

    public DemandeResponseDto toDto(Demande demande) {
        if (demande == null) {
            return null;
        }
        return new DemandeResponseDto(
                demande.getId(),
                demande.getPatient().getId(),
                demande.getPatient().getNom(),
                demande.getPatient().getPrenom(),
                dateMapper.formatLocalDate(demande.getDateDemande().toLocalDate(), "dd-MM-yyyy"),
                demande.getMotif(),
                demande.getStatut(),
                demande.getCreatedAt(),
                demande.getUpdatedAt()
        );
    }
}