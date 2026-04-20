package PatientApiV2.PatientApiV2.client.web.dto;

public record PatientCreateResponseDto(
        Long id,
        String numero,
        String nom,
        String prenom,
        String adresse,
        String telephone,
        String antecedent
) {}