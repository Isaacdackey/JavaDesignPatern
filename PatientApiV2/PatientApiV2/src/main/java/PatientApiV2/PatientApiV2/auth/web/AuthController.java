package PatientApiV2.PatientApiV2.auth.web;


import PatientApiV2.PatientApiV2.auth.service.AuthService;
import PatientApiV2.PatientApiV2.auth.web.dto.LoginRequestDTO;
import PatientApiV2.PatientApiV2.auth.web.dto.LoginResponseDTO;
import PatientApiV2.PatientApiV2.shared.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<RestResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(response));
    }
}
