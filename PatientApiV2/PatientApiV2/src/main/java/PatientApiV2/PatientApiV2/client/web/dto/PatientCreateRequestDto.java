package PatientApiV2.PatientApiV2.client.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PatientCreateRequestDto(
        @NotBlank(message = "Le numero est obligatoire")
        @Size(max = 20, message = "Le numero du patient ne doit pas dépasser 20 caractères")
        String numero,

        @NotBlank(message = "Le nom est obligatoire") String nom,

        @NotBlank(message = "Le prenom est obligatoire") String prenom,

        @NotBlank(message = "L'adresse est obligatoire") String adresse,

        @NotBlank(message = "Le telephone est obligatoire") String telephone,

        String antecedent
) {}