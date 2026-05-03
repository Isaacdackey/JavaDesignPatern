package PatientApiV2.PatientApiV2.auth.service;

import PatientApiV2.PatientApiV2.auth.repository.UserRepository;
import PatientApiV2.PatientApiV2.shared.exceptions.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'email: " + username));
    }
}