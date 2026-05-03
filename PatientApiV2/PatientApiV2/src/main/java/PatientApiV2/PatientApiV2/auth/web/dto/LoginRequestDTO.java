package PatientApiV2.PatientApiV2.auth.web.dto;

public record LoginRequestDTO(
        String email,
        String password
) {}