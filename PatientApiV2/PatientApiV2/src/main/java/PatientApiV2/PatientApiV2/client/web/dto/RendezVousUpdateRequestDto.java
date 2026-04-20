package PatientApiV2.PatientApiV2.client.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public record RendezVousUpdateRequestDto(

        @Future(message = "La date doit être dans le futur")
        LocalDateTime dateRendezVous,

        String motif,

        @Pattern(regexp = "^(EN_ATTENTE|CONFIRMÉ|ANNULÉ)$",
                message = "Le statut doit être: EN_ATTENTE, CONFIRMÉ ou ANNULÉ")
        String statut

) {}