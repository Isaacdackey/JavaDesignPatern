package PatientApiV2.PatientApiV2.client.web.mapper;

import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;

public final class PatientCreateMapper {

    public static Patient toEntity(PatientCreateRequestDto patientCreateDto) {
        if (patientCreateDto == null) {
            return null;
        }
        return Patient.builder()
                .numero(patientCreateDto.numero())
                .nom(patientCreateDto.nom())
                .prenom(patientCreateDto.prenom())
                .adresse(patientCreateDto.adresse())
                .telephone(patientCreateDto.telephone())
                .antecedents(patientCreateDto.antecedent())
                .build();
    }

    public static PatientCreateResponseDto toDto(Patient patient) {
        if (patient == null) {
            return null;
        }
        return new PatientCreateResponseDto(
                patient.getId(),
                patient.getNumero(),
                patient.getNom(),
                patient.getPrenom(),
                patient.getAdresse(),
                patient.getTelephone(),
                patient.getAntecedents()
        );
    }
}