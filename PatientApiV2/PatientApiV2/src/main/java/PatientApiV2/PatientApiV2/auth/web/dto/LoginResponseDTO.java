package PatientApiV2.PatientApiV2.auth.web.dto;

import java.util.List;

public record LoginResponseDTO(
        String token,
        String email,
        List<String> roles
) {}