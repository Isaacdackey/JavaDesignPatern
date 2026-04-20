package PatientApiV2.PatientApiV2.client.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record DemandeCreateRequestDto(
        @NotNull(message = "L'ID du patient est obligatoire")
        Long patientId,

        @NotNull(message = "La date est obligatoire")
        @Future(message = "La date doit être dans le futur")
        LocalDateTime dateDemande,

        @NotBlank(message = "Le motif est obligatoire")
        String motif
) {}