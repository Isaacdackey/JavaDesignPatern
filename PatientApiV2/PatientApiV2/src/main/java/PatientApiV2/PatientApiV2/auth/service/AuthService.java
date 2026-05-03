package PatientApiV2.PatientApiV2.auth.service;

import PatientApiV2.PatientApiV2.auth.jwt.JwtTokenProvider;
import PatientApiV2.PatientApiV2.auth.repository.UserRepository;
import PatientApiV2.PatientApiV2.auth.web.dto.LoginRequestDTO;
import PatientApiV2.PatientApiV2.auth.web.dto.LoginResponseDTO;
import PatientApiV2.PatientApiV2.shared.exceptions.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'email: " + request.email()));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new EntityNotFoundException("Mot de passe incorrect pour l'email: " + request.email());
        }

        String tokenJwt = jwtTokenProvider.generateToken(user);

        var roles = user.getRoles().stream()
                .map(role -> role.getRole().name())
                .toList();

        return new LoginResponseDTO(
                tokenJwt,
                user.getUsername(),
                roles
        );
    }
}